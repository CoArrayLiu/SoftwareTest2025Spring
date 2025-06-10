<template>
    <div id="pdf-content">
        <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        <br />
        <div class="steps-content">
            <div class="exp-step-card" v-if="current == 0" ref="content1">
                <h2>一、实验目的</h2>
                <p class="recontent">
                    本实验旨在使用不同方法评估软件项目/产品开发中的不确定性，并探讨不同因素对于不确定性的影响。 本实验为课内设计性实验项目，实验学时 1 学时，完成实验报告 1 学时。
                </p>
            </div>
            
            <div class="exp-step-card" v-if="current == 1" ref="content2">
                <h2>二、实验内容</h2>
                <p class="recontent">
                    探究不同估计方法下的方法选择。具体实验步骤如下：
                </p>
                <p class="content">1.获取实验指导书</p>
                <li class="recontent">
                    学生通过下载实验指导书选项获取指导书文件

                </li>
                <br />
                <p class="content">
                    2. 阅读实验指导书
                </p>
                <li class="recontent">
                    学生熟悉不确定性实验方法，并获取相应实验数据
                </li>
                <br />
                <p class="content">
                    4. 填写实验数据
                </p>
                <li class="recontent">
                    学生将所给实验数据填写在对应表格
                </li>
                <br />
                <p class="content">
                    5. 根据对应的方法，选择你所认为的最佳方案
                </p>
                <li class="recontent">
                    学生将选择的方案填写在对应位置
                </li>
                <br />
                <p class="content">
                    6. 总结和讨论
                </p>
                <li class="recontent">
                    实验结果总结：总结该种方法对不确定性方法选择的影响。
                </li>
                <br />
            </div>
            
            <div class="exp-step-card" v-if="current == 2" ref="content3">
                <h2>三、实验原理</h2>
                <h3 class="subtitle-content">1.赫维斯基法</h3>
                <p class="content">
                    赫维斯基法是对最大最小法和最大最大法两个方法进行折中的方法。决策者同时抱有乐观和悲观的态度，对每个方案的最好结果和最差结果进行加权平均计算，然后选取加权平均收益最大的方案作为最优行动方案。
                    采用该方法时，需要选取一个乐观系数入，该系数取值在0和1之间。如，风险厌恶型决策者会给与最好结果0.3的权重，而给予最差结果0.7的权重。
                </p>
                <h3 class="subtitle-content">2.拉普拉斯法</h3>
                <p class="content">
                    该方法的出发点是，既然不能肯定哪一种状态比另一种状态更可能出现，只好认为所有状态出现的概率是相等的。每个状态的权重值1/n。
                </p>
                <h3 class="subtitle-content">3.最大最大法</h3>
                <p class="content">
                    最大最大法是决策者抱着最乐观的想法，从各方案最有利结果中选取最大值，该数值所对应的行动作为最优行动方案。

                </p>
                <h3 class="subtitle-content">4.最大最小法</h3>
                <p class="content">
                    最大最小法是决策者从最不利结果着眼，将在最不利结果中取得的最有利结果之行动作为最优行动方案。

                </p>
                <h3 class="subtitle-content">5.最小最大后悔值法</h3>
                <p class="content">
                    该方法考虑了决策者在做完决定，知道实际结果时的态度，考虑到没有选取最优方案作为行动方案时的机会成本。
                    该方法计算过程是:先计算各方案在各种状态下的后悔值，选出各方案的最大后悔值，然后从各方案最大后悔值中选取最小值，该数值对应的方案就是决策者没有选择最优方案时后悔值最小的方案。
                </p>
            </div>
            
            <div class="exp-step-card" v-if="current == 3" ref="content4">
                <h2>四、实验步骤</h2>
                <p class="content">
                    根据实验指导书，填写下表
                </p>
                <a-steps v-model:current="currentmethod" size="small">
                    <a-step v-for="item in methods" :key="item.title" :title="item.title" />
                </a-steps>
                <div id="datatable">
                    <div class="exp-step-card" v-if="currentmethod == 0" ref="method1">
                        <p class="tabletitle">
                            表1:赫维斯基法
                        </p>
                        <Hurwicz @update="updateTable1"></Hurwicz>
                    </div>
                    <div class="exp-step-card" v-if="currentmethod == 1" ref="method2">
                        <p class="tabletitle">
                            表2:拉普拉斯法
                        </p>
                        <Laplace @update="updateTable2"></Laplace>
                    </div>
                    <div class="exp-step-card" v-if="currentmethod == 2" ref="method3">
                        <p class="tabletitle">
                            表3:最大最大法
                        </p>
                        <Maxmax @update="updateTable3"></Maxmax>
                    </div>
                    <div class="exp-step-card" v-if="currentmethod == 3" ref="method4">
                        <p class="tabletitle">
                            表4:最大最小法
                        </p>
                        <Maxmin @update="updateTable4"></Maxmin>
                    </div>
                    <div class="exp-step-card" v-if="currentmethod == 4" ref="method5">
                        <p class="tabletitle">
                            表5:最小最大后悔值法
                        </p>
                        <Regret @update="updateTable5"></Regret>
                    </div>
                    <br />
                </div>
                <div class="steps-action">
                    <a-button v-if="currentmethod < methods.length - 1" type="primary" style="float: right;"
                        @click="nextmethod"><step-forward-outlined />下一方法</a-button>
                    <a-button v-if="currentmethod > 0" style="margin-left: 8px" @click="prevmethod"><step-backward-outlined />上一方法</a-button>
                    <br />
                    <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
                    <a-button danger @click="clearData">清空数据</a-button>
                    </div>
                    <div>
                        <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
                        style="width:300px" okText="确认" cancelText="取消">
                        <p>确认清空实验数据？</p>
                        </a-modal>
                    </div>
                </div>
                <br />
            </div>

            <div class="exp-step-card" v-if="current == 4" ref="content5">
                <h2>五、实验结果</h2>
                1. 根据赫维斯基法，最好方案为
                <a-input v-model:value="bestplan1" style="width: 200px" placeholder="方案xx" />
                <br /><br />
                2. 根据拉普拉斯法，最好方案为
                <a-input v-model:value="bestplan2" style="width: 200px" placeholder="方案xx" />
                <br /><br />
                3. 根据最大最大法，最好方案为
                <a-input v-model:value="bestplan3" style="width: 200px" placeholder="方案xx" />
                <br /><br />
                4. 根据最大最小法，最好方案为
                <a-input v-model:value="bestplan4" style="width: 200px" placeholder="方案xx" />
                <br /><br />
                5. 根据最小最大后悔值法，最好方案为
                <a-input v-model:value="bestplan5" style="width: 200px" placeholder="方案xx" />
                <br /><br />
            </div>
            <div class="exp-step-card" v-if="current == 5" ref="content6">
                <h2>六、实验心得</h2>
                <a-textarea v-model:value="summary" placeholder="写下你的实验思考" :rows="4" />
            </div>
        </div>
    </div>
    <br />
    <div class="steps-action">
        <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
            @click="next"><step-forward-outlined />下一步</a-button>
        <a-spin :spinning="spinning">
            <div  v-if="current == steps.length - 1" style="display: flex; justify-content: flex-end;margin-top: 20px;">
                <a-button style="margin-right: 10px;" @click="submit">提交</a-button>
            </div>
        </a-spin>
        <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
    </div>

</template>


<script lang="ts">
import { Ref, defineComponent, ref, toRaw } from 'vue';
import Hurwicz from "./Hurwicz.vue"
import Laplace from "./Laplace.vue"
import Maxmax from "./Maxmax.vue"
import Maxmin from "./Maxmin.vue"
import Regret from "./Regret.vue"
import { HurwiczData } from './stores';
import { LaplaceData } from './stores';
import { MaxmaxData } from './stores';
import { MaxminData } from './stores';
import { RegretData } from './stores';
import { storeToRefs } from 'pinia';

// import html2pdf from 'html2pdf.js';
interface DataItem {
    key: string;
    state: string;
    cost1: number;
    cost2: number;
    cost3: number;
    cost4: number;
}
export default defineComponent({
    data(){
        return{
            current: 0,
            currentmethod: 0,
            clearMsg: 0,
            steps: [{
                title: '实验目的',
                content: '0-content',
            }, {
                title: '实验内容',
                content: 'First-content',
            }, {
                title: '实验原理',
                content: 'Second-content',
            }, {
                title: '实验步骤',
                content: 'Third-content',
            }, {
                title: '实验结果',
                content: 'Fourth-content',
            }, {
                title: '实验心得',
                content: 'Fifth-content',
            },
            ],
            methods: [{
                title: '赫维斯基法',
                content: 'First-method',
            }, {
                title: '拉普拉斯法',
                content: 'Second-method',
            }, {
                title: '最大最大法',
                content: 'Third-method',
            }, {
                title: '最大最小法',
                content: 'Fourth-method',
            }, {
                title: '最小最大后悔值法',
                content: 'Fifth-method',
            },
            ]
        }
    },
    methods: {
        next() {
            this.current++;
        },
        prev() {
            this.current--;
        },
        nextmethod() {
            this.currentmethod++;
        },
        prevmethod() {
            this.currentmethod--;
        },
        clearData () {
            this.clearMsg = true;
        },
        clearConfirm () {

        },
        clearCancel () {
            this.clearMsg = false;
        }

    },
    components: {
        'Hurwicz': Hurwicz,
        'Laplace': Laplace,
        'Maxmax': Maxmax,
        'Maxmin': Maxmin,
        'Regret': Regret,
    },
    setup(props, { emit }) {
        const spinning = ref<boolean>(false)

        const HurwiczDataStore = HurwiczData();
        const LaplaceDataStore = LaplaceData();
        const MaxmaxDataStore = MaxmaxData();
        const MaxminDataStore = MaxminData();
        const RegretDataStore = RegretData();
        const hurwiczdata = storeToRefs(HurwiczDataStore);
        const laplacedata = storeToRefs(LaplaceDataStore);
        const maxmaxdata = storeToRefs(MaxmaxDataStore);
        const maxmindata = storeToRefs(MaxminDataStore);
        const regretdata = storeToRefs(RegretDataStore);
        const table1 = hurwiczdata.table1;
        const table2 = laplacedata.table2;
        const table3 = maxmaxdata.table3;
        const table4 = maxmindata.table4;
        const table5 = regretdata.table5;
        const bestplan1 = hurwiczdata.bestplan;
        const bestplan2 = laplacedata.bestplan;
        const bestplan3 = maxmaxdata.bestplan;
        const bestplan4 = maxmindata.bestplan;
        const bestplan5 = regretdata.bestplan;

        const summary = ref<string>('');
        const updateTable1 = (params) => {
            table1.value = toRaw(params)
            // console.log(table1.value);
        }
        const updateTable2 = (params) => {
            table2.value = toRaw(params)
            // console.log(table2.value);
        }
        const updateTable3 = (params) => {
            table3.value = toRaw(params)
            // console.log(table3.value);
        }
        const updateTable4 = (params) => {
            table4.value = toRaw(params)
            // console.log(table4.value);
        }
        const updateTable5 = (params) => {
            table5.value = toRaw(params)
            // console.log(table5.value);
        }
        const submit = () => {
            spinning.value = true;
            // console.log(table1.value);
            const data = {
                // 此处为实验数据
                table1: table1.value,
                table2: table2.value,
                table3: table3.value,
                table4: table4.value,
                table5: table5.value,
                bestplan1: bestplan1.value,
                bestplan2: bestplan2.value,
                bestplan3: bestplan3.value,
                bestplan4: bestplan4.value,
                bestplan5: bestplan5.value,
                summary:summary.value
            }
            // 数据提取完毕以后调用父组件的submit方法，并传递数据
            emit('submit', data)
            // 关闭加载动画
            spinning.value = false
        }
        return {
            spinning,
            table1,
            summary,
            bestplan1,
            bestplan2,
            bestplan3,
            bestplan4,
            bestplan5,
            updateTable1,
            updateTable2,
            updateTable3,
            updateTable4,
            updateTable5,
            submit
        };
    },
})
</script>


<style lang="less" scoped>
.content {
    text-indent: 2em;
    margin-left: 20px;
    margin-right: 20px;
}
.steps-content {
    margin-top: 16px;
    border: 1px dashed #e9e9e9;
    border-radius: 6px;
    background-color: #fafafa;
    min-height: 200px;
    text-align: left;
    padding-top: 10px;
}
.tabletitle {
    text-align: center;
    font-family: sans-serif;
    font-size: 20px;
}
[data-theme='dark'] .steps-content {
    background-color: #2f2f2f;
    border: 1px dashed #404040;
}
.exp-step-card {
    padding-inline: 14px;
}

</style>