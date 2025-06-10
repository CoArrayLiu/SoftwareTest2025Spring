<template>
    <div>

        <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">Add</a-button>
        <a-button class="insure" type="primary" :loading="loading" @click="start">
            结果
        </a-button>
    </div>

    <a-table bordered :data-source="table2" :columns="columns" :pagination="false">
        <template #bodyCell="{ column, text, record }">
            <template v-if="['plan', 'cost1', 'cost2', 'cost3', 'cost4',].includes(column.dataIndex)">
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
                <a-popconfirm v-if="table2.length" title="Sure to delete?" @confirm="onDelete(record.key)">
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
import { message, Modal } from 'ant-design-vue';
import { LaplaceData } from './stores';
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
                filename: '拉普拉斯法.pdf',
                image: { type: 'jpeg', quality: 1 },
                html2canvas: { scale: 5 },
                jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
            };

            // 调用html2pdf库的方法生成PDF文件并下载
            html2pdf().from(element).set(opt).save();
        }
    },
    setup(props, { emit }) {
        const LaplaceDataStore = LaplaceData();
        const data = storeToRefs(LaplaceDataStore);
        const bestplan = data.bestplan;
        const reflection = data.reflection;
        const plantext = data.plantext;
        const table2 = data.table2;
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
            onDelete("0")
            // ajax request after empty completing
            setTimeout(() => {
                let assign_num = []
                let row_temp = new Array([], [], [], []);
                table2.value.forEach(function (item) {
                    row_temp[0].push(item.cost1)
                    row_temp[1].push(item.cost2)
                    row_temp[2].push(item.cost3)
                    row_temp[3].push(item.cost4)
                })
                for (let i = 0; i < row_temp.length; i++) {
                    let sum = 0
                    for (let j = 0; j < row_temp[i].length; j++) {
                        sum += parseFloat(row_temp[i][j])

                    }
                    //console.log(sum)
                    assign_num.push(sum / row_temp[i].length)
                }
                onDelete("0")
                let text = ""
                bestplan.value = ''
                text = "因为期望值的最大值是" + Math.max(...assign_num).toFixed(2) + ",所以最佳方案是"
                const newData = {
                    key: "0",
                    state: "期望值",
                    cost1: assign_num[0].toFixed(2),
                    cost2: assign_num[1].toFixed(2),
                    cost3: assign_num[2].toFixed(2),
                    cost4: assign_num[3].toFixed(2),
                };
                for (let i = 0; i < assign_num.length; i++) {
                    if (assign_num[i] == Math.max(...assign_num)) {
                        text = text + "方案" + String.fromCharCode(65 + i) + "。"
                        bestplan.value = bestplan.value + String.fromCharCode(65 + i);
                    }
                }
                plantext.value = text
                table2.value.push(newData);
            }, 1000);
            emit("update", table2.value)

        };
        const onSelectChange = (selectedRowKeys: Key[]) => {
            // console.log('selectedRowKeys changed: ', selectedRowKeys);
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
            editableData[key] = cloneDeep(table2.value.filter(item => key === item.key)[0]);
        };
        const save = (key: string) => {
            Object.assign(table2.value.filter(item => key === item.key)[0], editableData[key]);
            delete editableData[key];
        };
        const cancel = (key: string) => {
            delete editableData[key];
        };
        const onDelete = (key: string) => {
            table2.value = table2.value.filter(item => item.key !== "0");
            table2.value = table2.value.filter(item => item.key !== key);
            emit('update', table2.value)

        };
        const handleAdd = () => {
            onDelete("0")
            const newData = {
                key: `${count.value}` + (new Date().getTime() / 1000 + ""),
                state: "0" + count.value.toString(),
                cost1: 0,
                cost2: 0,
                cost3: 0,
                cost4: 0,
            };
            table2.value.push(newData);

        };
        watch(table2.value, () => {
            emit('update', table2.value)
            // console.log(table1.value)
        })
        return {
            plantext,
            reflection,
            bestplan,
            columns,
            onDelete,
            handleAdd,
            table2,
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

.input-number {
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