<template>
    
        <div>

            <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">Add</a-button>
            <a-button class="insure" type="primary" :loading="loading" @click="start">
                结果
            </a-button>
        </div>
        <a-table bordered :data-source="table5" :columns="columns" :pagination="false">
            <template #bodyCell="{ column, text, record }">
                <template
                    v-if="['plan', 'cost1', 'cost2', 'cost3', 'cost4', 'cost5', 'regret1', 'regret2', 'regret3', 'regret4', 'regret5'].includes(column.dataIndex)">
                    <div>
                        <a-input v-if="editableData[record.key]" v-model:value="editableData[record.key][column.dataIndex]"
                            style="margin: -5px 0" />
                        <template v-else>
                            {{ text }}
                        </template>
                    </div>
                </template>
                <template v-else-if="column.dataIndex === 'operation'">
                    <span v-if="editableData[record.key]">
                        <a-typography-link @click="save(record.key)">Save</a-typography-link>
                        <a-popconfirm title="Sure to cancel?" @confirm="cancel(record.key)">
                            <a>&nbsp;&nbsp;&nbsp;Cancel</a>
                        </a-popconfirm>
                    </span>
                    <span v-else>
                        <a @click="edit(record.key)">Edit</a>
                    </span>
                    <span>
                        <a>&nbsp;&nbsp;&nbsp;</a>
                    </span>
                    <a-popconfirm v-if="table5.length" title="Sure to delete?" @confirm="onDelete(record.key)">
                        <a>Delete</a>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
        <br />
        <h3 class="subtitle-content">
        {{ plantext }}
        </h3>
</template>
<script lang="ts">
import { computed, defineComponent, reactive, ref, toRefs, watch } from 'vue';
import type { Ref, UnwrapRef } from 'vue';
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
import { cloneDeep, max } from 'lodash-es';
import { message, Modal } from 'ant-design-vue'
import { RegretData } from './stores';
import { storeToRefs } from 'pinia';
import html2pdf from 'html2pdf.js';

message.config({
    duration: 2, // 提示时常单位为s
    top: '425px', // 距离顶部的位置
    maxCount: 3 // 最多显示提示信息条数(后面的会替换前面的提示)
})
type Key = string | number;
interface DataItem {
    key: string;
    state: string;
    cost1: number;
    cost2: number;
    cost3: number;
    cost4: number;
}


export default defineComponent({
    components: {
        CheckOutlined,
        EditOutlined,
    },
    methods: {
        downloadPdf() {
            const element = document.getElementById('pdf-content');
            const opt = {
                // 转换后的pdf的外边距分别为：上: 10px、右: 20px、下: 10px、左:20px
                margin: [10, 20, 10, 20],
                filename: '最小最大后悔值法.pdf',
                image: { type: 'jpeg', quality: 1 },
                html2canvas: { scale: 5 },
                jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
            };

            // 调用html2pdf库的方法生成PDF文件并下载
            html2pdf().from(element).set(opt).save();
        }
    },
    setup(props, { emit }) {
        const RegretDataStore = RegretData();
        const data = storeToRefs(RegretDataStore);
        const bestplan = data.bestplan;
        const reflection = data.reflection;
        const plantext = data.plantext;
        const table5 = data.table5;
        const count = data.count;
        
        const state = reactive<{
            selectedRowKeys: Key[];
            loading: boolean;
        }>({
            selectedRowKeys: [], // Check here to configure the default column
            loading: false,
        });
        const hasSelected = computed(() => state.selectedRowKeys.length > 0);

        const start = () => {
            table5.value = table5.value.filter(item => item.key.search('-'));
            let row_temp = new Array([], [], [], []);
            table5.value.forEach(function (item) {
                row_temp[0].push(item.cost1)
                row_temp[1].push(item.cost2)
                row_temp[2].push(item.cost3)
                row_temp[3].push(item.cost4)
            })

            let bestArr = []
            for (let j = 0; j < row_temp[0].length; j++) {
                let temp = row_temp[0][j];
                for (let i = 0; i < row_temp.length; i++) {
                    if (temp < row_temp[i][j]) {
                        temp = row_temp[i][j]
                    }
                }
                bestArr.push(temp)
            }
            // console.log(bestArr)
            let reMatrix = []
            for (let i = 0; i < row_temp.length; i++) {
                reMatrix[i] = []
            }

            for (let i = 0; i < row_temp.length; i++) {
                for (let j = 0; j < row_temp[i].length; j++) {
                    reMatrix[i][j] = bestArr[j] - row_temp[i][j]
                }
            }
            let i = 0
            let p = 0

            // console.log(reMatrix)
            for (let j = 0; j < reMatrix[0].length; j++) {

                const newData = {
                    key: "-" + `${count.value}` + (new Date().getTime() / 1000 + ""),
                    state: "后悔值" + "0" + (j + 1).toString(),
                    cost1: reMatrix[0][j],
                    cost2: reMatrix[1][j],
                    cost3: reMatrix[2][j],
                    cost4: reMatrix[3][j],
                };
                table5.value.push(newData);


            }
            let max_temp=[]

            for (let i = 0; i < reMatrix.length; i++) {

                max_temp.push(Math.max(...reMatrix[i]))
            }
            let text=""
            bestplan.value = ''
            text="因为所有方案的最大后悔值中的最小值是"+Math.min(...max_temp)+",所以最佳方案是"
            for (let i = 0; i < row_temp.length; i++) {

                if(max_temp[i]==Math.min(...max_temp))
                {
                    text=text+"方案"+String.fromCharCode(65+i)+"。"
                    bestplan.value = bestplan.value + String.fromCharCode(65 + i);
                }
            }
            plantext.value=text
            emit("update", table5.value)
        };
        const onSelectChange = (selectedRowKeys: Key[]) => {
            state.selectedRowKeys = selectedRowKeys;
        };
        const columns = [
            {
                title: '状态\\方案',
                dataIndex: 'state',
                width: '10%',
            },
            {
                title: '方案A',
                dataIndex: 'cost1',
            },
            {
                title: '方案B',
                dataIndex: 'cost2',
            },
            {
                title: '方案C',
                dataIndex: 'cost3',
            },
            {
                title: '方案D',
                dataIndex: 'cost4',
            },
            {
                title: 'operation',
                dataIndex: 'operation',
            },
        ];

        const editableData: UnwrapRef<Record<string, DataItem>> = reactive({});

        const edit = (key: string) => {
            editableData[key] = cloneDeep(table5.value.filter(item => key === item.key)[0]);
        };
        const save = (key: string) => {
            Object.assign(table5.value.filter(item => key === item.key)[0], editableData[key]);
            delete editableData[key];
        };
        const cancel = (key: string) => {
            delete editableData[key];
        };
        const onDelete = (key: string) => {
            table5.value = table5.value.filter(item => item.key.search('-'));
            table5.value = table5.value.filter(item => item.key !== key);
            emit('update',table5.value)

        };
        const handleAdd = () => {
            table5.value = table5.value.filter(item => item.key.search('-'));
            const newData = {
                key: `${count.value}` + (new Date().getTime() / 1000 + ""),
                state: "0" + count.value.toString(),
                cost1: 0,
                cost2: 0,
                cost3: 0,
                cost4: 0,
            };
            table5.value.push(newData);

        };
        watch(table5.value, () => {
            emit('update',table5.value)
            // console.log(table1.value)
        })
        return {
            plantext,
            reflection,
            bestplan,
            columns,
            onDelete,
            handleAdd,
            table5,
            editableData,
            count,
            edit,
            save,
            cancel,
            hasSelected,
            ...toRefs(state),

            // func
            start,
            onSelectChange,
        };
    },
});
</script>
<style lang="less">
.title {
    text-align: center;
    font-family: sans-serif;
    font-size: 30px;
}

.secondtitle {
    text-indent: 2em;
    font-weight: bold;
    margin-left: 30px;
    margin-right: 30px;
}

.content {
    text-indent: 2em;
    margin-left: 20px;
    margin-right: 20px;
}

.guidance {
    position: absolute;
    right: 50px;
    font-weight: bold;
}

.insure {
    margin-top: 20px;
    margin-left: 50px;
    font-weight: bold;
}

.editable-cell {
    position: relative;

    .editable-cell-input-wrapper,
    .editable-cell-text-wrapper {
        padding-right: 24px;
    }

    .editable-cell-text-wrapper {
        padding: 5px 24px 5px 5px;
    }

    .editable-cell-icon,
    .editable-cell-icon-check {
        position: absolute;
        right: 0;
        width: 20px;
        cursor: pointer;
    }

    .editable-cell-icon {
        margin-top: 4px;
        display: none;
    }

    .editable-cell-icon-check {
        line-height: 28px;
    }

    .editable-cell-icon:hover,
    .editable-cell-icon-check:hover {
        color: #108ee9;
    }

    .editable-add-btn {
        margin-bottom: 8px;
    }
}

.editable-cell:hover .editable-cell-icon {
    display: inline-block;
}
</style>