<template>
    <a-steps v-model:current="current" size="small">
        <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
        <div class="exp-step-card" v-if="current == 0" ref="content1">
            <h2>一、实验目的 </h2>
            <p class="content"> 本实验使用蒙特卡洛模拟方法对项目进行定量风险分析，通过模拟大量实验并统计实验结果，最终得出概率与累积概率分布图，帮助合理规划项目进度。
            </p>
        </div>
        <div class="exp-step-card" v-if="current == 1" ref="content2">
            <h2>二、实验原理 </h2>
            <p class="content">
                蒙特卡洛模拟是一种基于随机抽样的数学方法，用于模拟和评估不确定性和风险。在软件风险评估中，蒙特卡洛模拟可以用来模拟不同风险因素的潜在影响，并估计可能的风险程度和概率分布。该方法通过生成大量的随机样本，并基于这些样本的结果进行统计分析，以提供对风险的定量评估。
            </p>
        </div>
        <div class="exp-step-card" v-if="current == 2" ref="content3">
            <h2>三、实验内容 </h2>
            <p class="content"> 1. 确定风险因素：首先，需要确定对软件开发项目的风险因素。本次实验主要考虑项目进度风险。</p>
            <p class="content">2. 确定概率分布：对于每个风险因素，需要确定其可能的概率分布。这可以通过历史数据、专家意见或其他相关信息来确定。</p>
            <p class="content">3. 参数估计：对于每个风险因素的概率分布，需要估计其参数，如均值、方差等。这些参数将用于生成随机样本。</p>
            <p class="content">4. 随机样本生成：使用确定的概率分布和参数，生成大量的随机样本。这些样本将模拟不同风险因素的可能情况。</p>
            <p class="content">5. 风险评估：对于每个随机样本，通过模拟软件开发项目的潜在结果，评估风险的程度和影响。可以使用统计指标如均值、标准差、百分位数等来衡量风险。</p>
            <p class="content">6. 结果分析：对生成的大量样本进行统计分析，得出风险的概率分布和关键指标。这些结果可以用于决策制定、风险管理和项目规划。</p>
        </div>
        <div class="exp-step-card" v-if="current == 3" ref="content4">
            <h2>四、实验步骤 </h2>
            <!-- <p class="content" > -->
            <p class="secondtitle">第一步：确定平均工期、标准差和估计工期</p>
            <p class="content">假设该软件项目共有三个WBS要素，分别是设计、建造和测试。假设这三个WBS要素的
                预估的工期概率分布都呈标准正态分布，请填写各自的平均工期、标准差以及最悲观、最可能和最乐观的
                估计工期。
                <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：将项目平均工期、标准差以及最悲观、最可能和最乐观的估计工期填入表中。
            </p>
            <h2 style="text-align: center">工期估计表</h2>
            <a-table :pagination="false" :columns="columns1" :dataSource="tableData1" bordered size="middle" style="word-break: break-all;">
                <template #bodyCell="{ column, text, record }">
                    <template v-if="['optimistic', 'probably', 'pessimistic', 'average', 'sd'].includes(column.dataIndex)">
                        <div>
                            <a-input v-if="editableData[record.key]" v-model:value="editableData[record.key][column.dataIndex]"
                                style="margin: -5px 0" />
                            <template v-else>
                                {{ text }}
                            </template>
                        </div>
                    </template>
                    <template v-else-if="column.dataIndex === 'distribution'">
                        <a-input-group compact>
                            <a-select v-model:value="record.distribution">
                                <a-select-option value="normal">正态分布</a-select-option>
                                <a-select-option value="poisson">泊松分布</a-select-option>
                                <a-select-option value="uniform">均匀分布</a-select-option>
                            </a-select>
                        </a-input-group>
                    </template>
                    <template v-else-if="column.dataIndex === 'stage'">
                        {{ text }}
                    </template>
                    <template v-else-if="column.dataIndex === 'operation'">
                        <div class="editable-row-operations">
                            <span v-if="editableData[record.key]">
                                <a @click="save(record.key)" style="margin: 10px">保存</a>
                                <a-popconfirm title="确定取消保存吗？" @confirm="cancel(record.key)">
                                    <a>取消</a>
                                </a-popconfirm>
                            </span>
                            <span v-else>
                                <a @click="edit(record.key)" style="margin: 10px">编辑</a>
                            </span>
                        </div>
                    </template>
                </template>
            </a-table>
        </div>
        <div class="exp-step-card" v-if="current == 4" ref="content5">
            <h2>四、实验步骤 </h2>
            <p class="secondtitle">第二步：随机生成工期值</p>
            <p class="content">蒙特卡洛模拟定量分析整个项目的工期进度风险，可以用计算机来模拟项目的实施。
                需要为每个WBS要素随机生成400次工期值，此工期值满足该工期规定均值、标准差的正态分布。模拟结束后，
                得到总计400*3=1200个呈正态分布的随机工期值，并计算出三个要素相加的总工期。
                <br />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：随机生成每个要素的工期值并计算总工期。
            </p>
            <a-button type="primary" @click="createRandomData">生成随机工期值</a-button>
            <h2 style="text-align: center">随机工期值</h2>
            <a-table :pagination="{ }" :columns="columns2" :data-source="tableData2" :scroll="{ y: 240 }" bordered
                size="middle" style="word-break: break-all;">
            </a-table>
            <div style="width:100%;text-align:left">
                <span style="width:30%; text-indent: 2em; font-weight: bold;">总工期最大值: </span>
                <span style="display:inline-block;font-size:20px;">{{ maxSum
                    }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="width:30%; text-indent: 2em; font-weight: bold;">总工期最小值: </span>
                <span style="display:inline-block;font-size:20px;">{{ minSum }}</span>
            </div>
        </div>
        <div class="exp-step-card" v-if="current == 5" ref="content6">
            <h2>四、实验步骤 </h2>
            <p class="secondtitle">第三步：统计总工期的概率和累积概率分布</p>
            <p class="content">计算得出总工期的最小值与最大值作为范围，统计该范围内每个总工期值的概率和累积概率。 <br />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：统计总工期值的概率和累积概率</p>
            <a-button type="primary" @click="calculateStatistics">计算总工期值的概率与累积概率</a-button>
            <h2 style="text-align: center">总工期值、概率与累积概率</h2>
            <a-table :pagination="false" :columns="statsColumns" :data-source="sumStats" :scroll="{ y: 240 }"
                bordered size="middle" style="word-break: break-all;"></a-table>
        </div>
        <div class="exp-step-card" v-if="current == 6" ref="content7">
            <h2>四、实验步骤 </h2>
            <p class="secondtitle">第四步：绘制总工期的概率和累积概率分布图</p>
            <p class="content">结合上面表格的数据，以分组数据为X轴，出现概率和累积概率的值为Y轴，
                绘制总工期的概率和累积概率分布图。红色柱状图是整个项目估计刚好多少天完工的概率数据，
                蓝线是整个项目在多少天内完工的概率，也就是蒙特卡洛模拟的结果。<br />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：绘制总工期的概率和累积概率分布图。 </p>
            <a-button type="primary" @click="renderCombinedChart">绘制图表</a-button>
            <h2 style="text-align: center">总工期概率、累积概率分布图</h2>
            <div class="chart-container" id="chart">
                <div ref="combinedChart" class="chart"></div>
            </div>
            <p class="secondtitle">第五步：合理规划项目进度</p>
            <p class="content">通过累积概率分布图，可以得出项目进度规划为x天时，项目按时完工的概率。
                (1)假设最低允许30%不能按时完工的风险，得出项目应规划为多少日内完成。
                (2)假设最低允许15%不能按时完工的风险，得出项目应规划为多少日内完成。<br />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：计算项目进度规划。 </p>
            <br>
            <span class="result">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低允许30%不能按时完工的风险，项目规划为</span>
            <a-input-number id="inputNumber1" v-model:value="percent30" min="1" />
            <span class="result">&nbsp;&nbsp;日内完成</span>
            <br /><br />
            <span class="result">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低允许15%不能按时完工的风险，项目规划为</span>
            <a-input-number id="inputNumber2" v-model:value="percent15" min="1" />
            <span class="result">&nbsp;&nbsp;日内完成</span>
            <br /><br />
            <h2>五、实验心得 </h2>
            <a-textarea style="margin-top: 5px;" id="summary_text" v-model:value="summary" placeholder="写下你的实验心得" :rows="4" show-count:
                :maxlength= "maxLengthValue" />
            <div style="margin-top: 20px; display: flex;justify-content: flex-end;">
                <a-spin :spinning="spinning">
                    <!-- <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
                        <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button>
                    </div> -->
                </a-spin>
            </div>
        </div>
        <!-- <div class="exp-step-card" v-if="current == 7" ref="content8">
            <h2>四、实验步骤 </h2>
            <p class="secondtitle">第五步：合理规划项目进度</p>
            <p class="content">通过累积概率分布图，可以得出项目进度规划为x天时，项目按时完工的概率。
                (1)假设最低允许30%不能按时完工的风险，得出项目应规划为多少日内完成。
                (2)假设最低允许15%不能按时完工的风险，得出项目应规划为多少日内完成。<br />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：计算项目进度规划。 </p>
            <br>
            <span class="result">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低允许30%不能按时完工的风险，项目规划为</span>
            <a-input-number id="inputNumber1" v-model:value="percent30" min="1" />
            <span class="result">&nbsp;&nbsp;日内完成</span>
            <br /><br />
            <span class="result">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低允许15%不能按时完工的风险，项目规划为</span>
            <a-input-number id="inputNumber2" v-model:value="percent15" min="1" />
            <span class="result">&nbsp;&nbsp;日内完成</span>
            <br /><br />
        </div>
        <div class="exp-step-card" v-if="current == 8" ref="content9">
            <h2>五、实验心得 </h2>
            <a-button type="primary" @click="renderCombinedChart">绘制图表</a-button>
            <div class="chart-container" id="chart">
                <div ref="combinedChart" class="chart"></div> 
            </div>
            <a-textarea style="margin-top: 5px;" id="summary_text" v-model:value="summary" placeholder="写下你的实验心得" :rows="4" show-count:
                :maxlength= "maxLengthValue" />
            <div style="margin-top: 20px; display: flex;justify-content: flex-end;">
                <a-spin :spinning="spinning">
                </a-spin>
            </div>
        </div> -->
    </div>
    <!-- <p class="secondtitle">第二步：绘制工期的正态分布图</p>
    <p class="content">以三个WBS要素的工期的分布为输入，模拟得到整个项目的工期概率分布图。
        由于设计、建造和测试这三个要素都是呈标准正态分布，可以根据上面表格中的各自的均值
        和标准差数据大致画出三要素工期的正态分布图。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：绘制三阶段工期的概率分布图。</p> -->
    <div class="steps-action">
        <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
            @click="next"><step-forward-outlined />下一步</a-button>
        <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" @click="submit">
            提交
        </a-button>
        <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
        <div v-if="current > 2" style="display: flex; justify-content: flex-end;margin-top: 20px;">
            <a-button danger @click="clearData">清空数据</a-button>
        </div>
        <div>
            <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
            style="width:300px" okText="确认" cancelText="取消">
            <p>确认清空实验数据？</p>
            </a-modal>
        </div>
    </div>
</template>



<script setup lang="ts">
import { cloneDeep } from 'lodash-es';
import { reactive, ref, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import html2canvas from 'html2canvas';
import { message } from 'ant-design-vue';
import { useStore } from './index';
import { storeToRefs } from 'pinia';

const localStore = useStore()
const current = ref(0) // 当前步骤
const currentPage = ref(0)
const maxLengthValue = ref(1000)
const steps = ref([
  {
    title: '实验目的',
    content: 'content1',
  },
  {
    title: '实验原理',
    content: 'content2',
  },
  {
    title: '实验内容',
    content: 'content3',
  },
  {
    title: '确定工期',
    content: 'content4',
  },
  {
    title: '随机生成工期值',
    content: 'content5',
  },
  {
    title: '统计概率',
    content: 'content6',
  },
  {
    title: '绘制概率分布图',
    content: 'content7',
  },
//   {
//     title: '规划项目进度',
//     content: 'content8',
//   },
//   {
//     title: '实验总结',
//     content: 'content9',
//   }
]);

const spinning = ref<boolean>(false)
const emit = defineEmits({
    submit: (data) => {
        return true
    }
});

const { maxSum,minSum,percent30,percent15,tableData1,tableData2,sumStats,summary } = storeToRefs(useStore());

const submit = () => {
    spinning.value = true;
    let imgUrl1 = ""
    const experiment_id = "85"
    const { account } = useAccountStore()
    const { uploadImg } = useExperimentStore()
    html2canvas(document.getElementById("chart")).then(function (canvas) {
        canvas.toBlob(
            function (blob) {
                imgUrl1 = uploadImg(blob, experiment_id, account.email.slice(0, account.email.indexOf("@")), "mengtekaluo");
                const data = {
                    image:{
                        chart:imgUrl1
                    },
                    // 此处为实验数据
                    // table1: tableData1.value,
                    // table2: tableData2.value,
                    // table3: sumStats.value,
                    // maxSum: maxSum.value,
                    // minSum: minSum.value,
                    // percent15: percent15.value,
                    // percent30: percent30.value,
                    // summary: summary.value
                    table1: localStore.tableData1,
                    table2: localStore.tableData2,
                    table3: localStore.sumStats,
                    maxSum: localStore.maxSum,
                    minSum: localStore.minSum,
                    percent15: localStore.percent15,
                    percent30: localStore.percent30,
                    summary: localStore.summary
                }
                // 数据提取完毕以后调用父组件的submit方法，并传递数据
                emit('submit', data)
                // 关闭加载动画
                spinning.value = false
            });
    })

}

const editableData = ref({})
const editingKey = ref('')
const columns1 = ([
    {
        title: '',
        dataIndex: 'stage',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '最乐观',
        dataIndex: 'optimistic',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '最可能',
        dataIndex: 'probably',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '最悲观',
        dataIndex: 'pessimistic',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '平均工期',
        dataIndex: 'average',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '标准差',
        dataIndex: 'sd',
        key: 'type',
        align: 'center',
        width: 400,
        // fixed: 'left',
    },
    {
        title: '分布',
        dataIndex: 'distribution',
        key: 'component',
        align: 'center',
        width: 400,
    },
    {
        title: '操作',
        dataIndex: 'operation',
        align: 'center',
        width: 400,
    },

])
const columns2 = ([
    {
        title: '设计',
        dataIndex: 'design',
        align: 'center',
    },
    {
        title: '开发',
        dataIndex: 'build',
        align: 'center',
    },
    {
        title: '测试',
        dataIndex: 'test',
        align: 'center',
    },
    {
        title: '总工期',
        dataIndex: 'sum',
        align: 'center',
    },

])
const statsColumns = ref([
    {
        title: '总工期',
        dataIndex: 'sum',
        key: 'sum',
        align: 'center',
    },
    {
        title: '出现概率',
        dataIndex: 'probability',
        key: 'probability',
        align: 'center',
    },
    {
        title: '累积概率',
        dataIndex: 'cumulativeProbability',
        key: 'cumulativeProbability',
        align: 'center',
    },
])

// function updated() {
//     // 用于防止表格合计行不显示
//     this.$nextTick(() => {
//         this.$refs['detailTable'].doLayout();
//     })
// }
const detailTable = ref(null);

onMounted(() => {
  // 用于防止表格合计行不显示
  nextTick(() => {
    if (detailTable.value) {
      detailTable.value.doLayout();
    }
  });
});

function edit(key) {
    editableData.value[key] = cloneDeep(tableData1.value.filter((item) => key === item.key)[0]);
    // console.log(this.editableData)
}
function save(key) {
    Object.assign(tableData1.value.filter((item) => key === item.key)[0], editableData.value[key]);
    delete editableData.value[key];
}
function cancel(key) {
    delete editableData.value[key];
}
function pdfHandle() {
    window.open('/#/show', "_blank")
}
function pdfHandle2() {
    window.open('/#/show', "_blank")
}
function createRandomData() {
    tableData2.value = Array(400).fill(undefined).map((item) => {
        var design, build, test;
        const d1 = tableData1.value[0].distribution, d2 = tableData1.value[1].distribution, d3 = tableData1.value[2].distribution;
        //design distribution
        if (d1 === 'normal') {
            design = normalDistribution(parseInt(tableData1.value[0].average), parseInt(tableData1.value[0].sd));
        }
        else if (d1 === 'poisson') {
            design = generatePoissonRandom(parseInt(tableData1.value[0].probably));
        }
        else {
            design = generateUniformRandom(parseInt(tableData1.value[0].optimistic), parseInt(tableData1.value[0].pessimistic));
        }

        //build distribution
        if (d2 === 'normal') {
            build = generateUniformRandom(parseInt(tableData1.value[1].optimistic), parseInt(tableData1.value[1].pessimistic));
        }
        else if (d2 === 'poisson') {
            build = generatePoissonRandom(parseInt(tableData1.value[1].probably));
        }
        else {
            build = generateUniformRandom(parseInt(tableData1.value[1].optimistic), parseInt(tableData1.value[1].pessimistic));
        }

        //test distribution
        if (d3 === 'normal') {
            test = generateUniformRandom(parseInt(tableData1.value[2].optimistic), parseInt(tableData1.value[2].pessimistic));
        }
        else if (d3 === 'poisson') {
            test = generatePoissonRandom(parseInt(tableData1.value[2].probably));
        }
        else {
            test = generateUniformRandom(parseInt(tableData1.value[2].optimistic), parseInt(tableData1.value[2].pessimistic));
        }
        return {
            design: Math.round(design),
            build: Math.round(build),
            test: Math.round(test),
        }
    });

    let max = Number.MIN_SAFE_INTEGER;
    let min = Number.MAX_SAFE_INTEGER;
    for (var i = 0; i < 400; i++) {
        tableData2.value[i].sum = tableData2.value[i].design + tableData2.value[i].build + tableData2.value[i].test
        if (tableData2.value[i].sum > max) {
            max = tableData2.value[i].sum;
        }
        if (tableData2.value[i].sum < min) {
            min = tableData2.value[i].sum;
        }
    }
    maxSum.value = max;
    minSum.value = min;
}
function normalDistribution(mean, std) {
    let u = 0;
    let v = 0;
    let s = 0;
    let val = 0;

    do {
        u = Math.random() * 2 - 1;
        v = Math.random() * 2 - 1;
        s = u * u + v * v;
    } while (s >= 1);

    val = u * Math.sqrt((-2 * Math.log(s)) / s);

    return mean + std * val;
}
function generateUniformRandom(min, max) {
    return Math.random() * (max - min) + min;
}
function generatePoissonRandom(lambda) {
    const L = Math.exp(-lambda);
    let k = 0;
    let p = 1;

    do {
        k++;
        p *= Math.random();
    } while (p > L);

    return k - 1;
}
function calculateStatistics() {
    const sumCounts = {};

    tableData2.value.forEach((item) => {
        const sum = item.sum;

        if (sumCounts[sum]) {
            sumCounts[sum]++;
        } else {
            sumCounts[sum] = 1;
        }
    });

    let cumulativeProbability = 0;

    sumStats.value = Object.keys(sumCounts)
        .sort((a, b) => parseInt(a) - parseInt(b)) // 从小到大排序
        .map((sum) => {
            const count = sumCounts[sum];
            const probability = count / tableData2.value.length;

            cumulativeProbability += probability;

            return {
                sum: parseInt(sum),
                probability: probability.toFixed(2),
                cumulativeProbability: cumulativeProbability.toFixed(2),
            };
        });
    // console.log(this.sumStats.length)
}

// 引用定义
const combinedChart = ref(null);

// 渲染图表函数
function renderCombinedChart() {
    // console.log(combinedChart);
    const chart = echarts.init(combinedChart.value);
    // console.dir(chart);
    const option = {
    tooltip: {
        trigger: 'axis',
    },
    legend: {
        data: ['概率', '累积概率'],
    },
    xAxis: {
        type: 'category',
        data: sumStats.value.map(item => item.sum),
    },
    yAxis: [
        {
        type: 'value',
        name: '概率',
        min: 0,
        max: 1,
        },
        {
        type: 'value',
        name: '累积概率',
        min: 0,
        max: 1,
        },
    ],
    series: [
        {
        name: '概率',
        type: 'bar',
        data: sumStats.value.map(item => item.probability),
        yAxisIndex: 0,
        },
        {
        name: '累积概率',
        type: 'line',
        data: sumStats.value.map(item => item.cumulativeProbability),
        yAxisIndex: 1,
        },
    ],
    };
    chart.setOption(option);
};

function next() { //下一步
  current.value = Math.min(steps.value.length - 1, current.value + 1);
}

function prev() { //上一步
   current.value = Math.max(0, current.value - 1);
}

const clearMsg = ref(false);
const clearData = () => {
  clearMsg.value = true;
}

const clearConfirm = () => {
  localStore.clear();
  message.warning('已清空数据');
  clearMsg.value = false;
  current.value = 0;
}

const clearCancel = () => {
  clearMsg.value = false;
}

</script>




<style scoped>
/* .title {
    text-align: center;
    font-family: sans-serif;
    font-size: 30px;
}

.secondtitle {
    text-indent: 2em;
    font-weight: bold;
    margin-left: 30px;
    margin-right: 30px;
} */

.result {
    text-indent: 2em;
    font-weight: bold;
    margin-left: 10px;
    margin-right: 10px;
}

.maintable {
    text-align: center;
    font-family: sans-serif;
    font-size: 30px;
}

.button1 {
    margin-left: 25px;
}

.button2 {
    margin-left: 15px;
}

.button3 {

    float: right;
    margin-top: 10px;
    margin-right: 50px;

}

.button4 {

    margin-left: 250px;

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

.chart-container {
    display: flex;
    justify-content: center;
    align-items: center;

}

.chart {
    width: 80vw;
    /* 设置图表的宽度为容器宽度的 80% */
    height: 600px;
    /* 设置图表的高度 */
}

:deep(.ant-table .ant-table-thead > tr > th) {
    border-width: 1px;
}


/* <style scoped> */
.title {
  font-weight: 700;
  margin-left: 20px;
  margin-right: 20px;
  text-indent: 0em;
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

.steps-content {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: left;
  padding-top: 10px;
}

.image-center {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 16px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 10px;
}

.steps-action {
  margin-top: 24px;
}

[data-theme='dark'] .steps-content {
  background-color: #2f2f2f;
  border: 1px dashed #404040;
}

.exp-step-card {
  padding-inline: 20px;
  margin-bottom: 16px;
}

.emphasize{
  font-weight: 700;
  color: coral;
  margin-left: 20px;
  margin-right: 20px;
}

.radio{
  margin-top: 20px;
  margin-left: 20px;
}

</style>