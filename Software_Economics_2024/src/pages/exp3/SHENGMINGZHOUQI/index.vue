<template>

  <h2>一、实验目的 </h2>
  <p class="text">软件经济生命周期计算是一种用于评估软件产品在其整个生命周期中的经济效益和成本的方法。该实验的目的是通过进行软件经济生命周期计算，
    以评估软件产品的经济可行性，并为软件开发和维护过程中的决策提供依据。
  </P>
  <h2>二、实验原理 </h2>
  <p class="secondtitle">1.软件生命周期</p>
  <p class="text">软件生命周期(Software Life Cycle,SLC)是软件的产生直到报废或停止使用的生命周期。周期内有问题定义、可行性分析、总体描述、系统设计、编码、调试和测试、
    验收与运行、维护升级到废弃等阶段，这种按时间分程的思想方法是软件工程中的一种思想原则，即按部就班、逐步推进，每个阶段都要有定义、工作、审查、
    形成文档以供交流或备查，以提高软件的质量。典型的几种生命周期模型包括瀑布模型、快速原型模型、迭代模型。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    软件的成本主要包括两部分：资本回收CR(i)与运维成本O&M Cost。CR(i)通常会在开始时会很高，并随着时间的推移而减少。资产的运营和维护成本通常一开始较低，并随着时间的推移而增加。
    本实验就是要计算软件测试开始后，成本最低的年份，在该年停止使用软件可以获得的利润最大。
  </P>
  <p class="secondtitle">2.经济学相关概念</p>
  <p class="text">2.1 残值salvage value：是指在一项资产使用期满时预计能够回收到的残余价值，也就是在固定资产使用期满报废时处置资产所能收取的价款。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    2.2
    (A/P,i,n)：表示资金回收系数。与年金现值系数(P/A,i,n)互为倒数。年资本回收额：指在约定年限内等额回收初始投入资本或清偿所欠债务的金额。年资本回收额的计算实际上是已知普通年金现值P，求年金A。A=P*(A/P,i,n)
    =P/(P/A,i,n) 
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    2.3 (P/F,i,n)：复利现值系数，(P/F,i,n) = 1 / (1 + i)^n。复利现值的计算公式为：P = F * (P/F,i,n)。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    2.4 CR(i)：资本回收。由两部分组成：（1）资产价值随着时间流逝的下降值；（2）如果把资产残值存入银行中在第i年会得到的利息值（隐形成本）。计算方式为：P * (A/P,i,n) - F * (A/F,i,n) + F *
    i，为了简化计算，可化为(P - F) * (A/P,i,n) + F * i
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    2.5 Present worth: 费用现值，用于计算回报率；AE: anual equivalent，每年折算值。
  </p>
  <p class="secondtitle">3.相关计算方法</p>
  <p class="text">3.1 CR(i)：资本回收，计算公式：(P - F) * (A/P,i,n) + F * i = (P - F) / (P/A,i,n) + F * i；
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    3.2 PW(i) of O&M for Year n in Year 0：将第n年的运维金额折现成第0年的运维费用，计算公式：O&M * (P/F,i,n)；
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    3.3 AE(i) Cost of Operating for n Years：n年运维的成本折现到第n年，计算公式：Sum * (A/P,i,n) 或 Sum / (P/A,i,n)；
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    3.4 Total AE(i) If Retired at Year n：运维成本与资本回收之和，最小值所在年份即为要求年份。
  </p>
  <p class="secondtitle">4.年金现值系数(P/A,i,n)表</p>
  <a-table :columns="columns_PA" :data-source="dataSource_PA" bordered size="middle" :pagination="false" />
  <br />
  <p class="secondtitle">5.复利现值系数(P/F,i,n)表</p>
  <a-table :columns="columns_PA" :data-source="dataSource_PF" bordered size="middle" :pagination="false" />
  <br />
  <h2>三、实验步骤和实验内容 </h2>
  <p class="text">步骤1: 确定每个年份的残值（Salvage Value If Retired at Year n）、运维费用 O&M Cost 的相关值和对应系数，填写表格。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    请输入年收益率 i =
    <a-input v-model:value="RATE" style="width:100px;" />
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    步骤2: 使用公式计算资本回收 CR(i)、折现的运维费用 PW(i) of O&M for Year n in Year 0。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    步骤3: 计算每个年份的运维成本的累加值（Sum of Year 0 O&Ms through Year n）和运维成本的折现值 AE(i) Cost of Operating for n Years。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    步骤4: 计算每个年份的总经济成本（Total AE(i) If Retired at Year n），即运维成本和资本回收的和 。
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    以下表格为步骤1-4的具体展示：
  </p>
  <!-- 在了解软件生命周期定义的基础上，理解软件的成本与利润如何受到影响，根据相关值与对应系数，计算出资本回收与运维费用，填入表格，
                         找到和为最小值，即最佳收益的年份，得到所求软件的生命周期。 -->
  <a-table :pagination="false" :columns="columns" :data-source="tableData" bordered size="middle"
    style="word-break: break-all;">
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.dataIndex === 'A' && tableData !== undefined">
        <a-input v-model:value="record.A" style="width:100px; " />
      </template>
      <template v-if="column.dataIndex === 'D' && tableData !== undefined">
        <a-input v-model:value="record.D" style="width:100px;" />
      </template>
      <template v-if="column.dataIndex === 'G' && tableData !== undefined">
        {{ g(index) }}
      </template>
      <template v-if="column.dataIndex === 'C' && tableData !== undefined">
        {{ c(index) }}
      </template>
      <template v-if="column.dataIndex === 'E' && tableData !== undefined">
        {{ e(index) }}
      </template>
      <template v-if="column.dataIndex === 'F' && tableData !== undefined">
        {{ f(index) }}
      </template>
      <template v-if="column.dataIndex === 'I' && tableData !== undefined">
        {{ i(index) }}
      </template>
      <template v-if="column.dataIndex === 'number' && tableData !== undefined">
        {{ number(index) }}
      </template>
      <template v-if="column.dataIndex === 'unchanged' && tableData !== undefined">
        {{ unchanged(index) }}
      </template>
    </template>
  </a-table>
  <br>

  <p class="text">
    步骤5: 分析表格，找出产品生命周期。年最低成本:
    <a-input v-model:value="sum" style="width:100px;" />
    ，产品生命周期:
    <a-input v-model:value="year" style="width:100px;" />
    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    步骤6: 分析实验结果，根据产品生命周期，评估其经济可行性；撰写实验报告，包括实验目的、原理、步骤、结果和思考。
  </p>

  <h2>四、实验结果 </h2>
  <p class="text">年最低成本: {{ SUM }} 元
  </P>
  <p class="text">产品生命周期: {{ Year }} 年
  </P>

  <h2>五、实验心得 </h2>
  <div>
    <a-textarea v-model:value="summary" :rows="4" />
  </div>

  <div style="margin-top: 20px; display: flex;justify-content: flex-end;">
    <a-spin :spinning="spinning">
      <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
        <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button>
      </div>
    </a-spin>
  </div>
</template>

<script lang="ts">
import { computed } from 'vue';
import { ref } from 'vue'
const columns = [

];
const data = [...Array(8)].map((_, i) => ({

}));
interface DataType {
  key: string;
  Year_data: GLfloat;
  rate_6: GLfloat;
  rate_7: GLfloat;
  rate_8: GLfloat;
  rate_9: GLfloat;
  rate_10: GLfloat;
  rate_11: GLfloat;
  rate_12: GLfloat;
  rate_13: GLfloat;
  rate_14: GLfloat;
  rate_15: GLfloat;
}
const columns_PA = [
  {
    title: '期数',
    dataIndex: 'Year_data',
    key: 'Year_data',
  },
  {
    title: '6%',
    dataIndex: 'rate_6',
    key: 'rate_6',
  },
  {
    title: '7%',
    dataIndex: 'rate_7',
    key: 'rate_7',
  },
  {
    title: '8%',
    dataIndex: 'rate_8',
    key: 'rate_8',
  },
  {
    title: '9%',
    dataIndex: 'rate_9',
    key: 'rate_9',
  },
  {
    title: '10%',
    dataIndex: 'rate_10',
    key: 'rate_10',
  },
  {
    title: '11%',
    dataIndex: 'rate_11',
    key: 'rate_11',
  },
  {
    title: '12%',
    dataIndex: 'rate_12',
    key: 'rate_12',
  },
  {
    title: '13%',
    dataIndex: 'rate_13',
    key: 'rate_13',
  },
  {
    title: '14%',
    dataIndex: 'rate_14',
    key: 'rate_14',
  },
  {
    title: '15%',
    dataIndex: 'rate_15',
    key: 'rate_15',
  },
];
const dataSource_PA = [
  {
    key: '1',
    Year_data: '1.000',
    rate_6: 0.943,
    rate_7: 0.935,
    rate_8: 0.926,
    rate_9: 0.917,
    rate_10: 0.909,
    rate_11: 0.901,
    rate_12: 0.893,
    rate_13: 0.885,
    rate_14: 0.877,
    rate_15: 0.870,
  },
  {
    key: '2',
    Year_data: '2.000',
    rate_6: 1.833,
    rate_7: 1.808,
    rate_8: 1.783,
    rate_9: 1.759,
    rate_10: 1.736,
    rate_11: 1.713,
    rate_12: 1.690,
    rate_13: 1.668,
    rate_14: 1.647,
    rate_15: 1.626,
  },
  {
    key: '3',
    Year_data: '3.000',
    rate_6: 2.673,
    rate_7: 2.624,
    rate_8: 2.577,
    rate_9: 2.531,
    rate_10: 2.487,
    rate_11: 2.444,
    rate_12: 2.402,
    rate_13: 2.361,
    rate_14: 2.322,
    rate_15: 2.283,
  },
  {
    key: '4',
    Year_data: '4.000',
    rate_6: 3.465,
    rate_7: 3.387,
    rate_8: 3.312,
    rate_9: 3.240,
    rate_10: 3.170,
    rate_11: 3.102,
    rate_12: 3.037,
    rate_13: 2.975,
    rate_14: 2.914,
    rate_15: 2.855,
  },
  {
    key: '5',
    Year_data: '5.000',
    rate_6: 4.212,
    rate_7: 4.100,
    rate_8: 3.993,
    rate_9: 3.890,
    rate_10: 3.791,
    rate_11: 3.696,
    rate_12: 3.605,
    rate_13: 3.517,
    rate_14: 3.433,
    rate_15: 3.352,
  },
  {
    key: '6',
    Year_data: '6.000',
    rate_6: 4.917,
    rate_7: 4.767,
    rate_8: 4.623,
    rate_9: 4.486,
    rate_10: 4.355,
    rate_11: 4.231,
    rate_12: 4.111,
    rate_13: 3.998,
    rate_14: 3.889,
    rate_15: 3.785,
  },
  {
    key: '7',
    Year_data: '7.000',
    rate_6: 5.582,
    rate_7: 5.389,
    rate_8: 5.206,
    rate_9: 5.033,
    rate_10: 4.868,
    rate_11: 4.712,
    rate_12: 4.564,
    rate_13: 4.423,
    rate_14: 4.288,
    rate_15: 4.160,
  },
  {
    key: '8',
    Year_data: '8.000',
    rate_6: 6.210,
    rate_7: 5.971,
    rate_8: 5.747,
    rate_9: 5.535,
    rate_10: 5.335,
    rate_11: 5.146,
    rate_12: 4.968,
    rate_13: 4.799,
    rate_14: 4.639,
    rate_15: 4.487,
  },
];
const dataSource_PF = [
  {
    key: '1',
    Year_data: '1.000',
    rate_6: 0.9434,
    rate_7: 0.9346,
    rate_8: 0.9259,
    rate_9: 0.9174,
    rate_10: 0.9091,
    rate_11: 0.9009,
    rate_12: 0.8929,
    rate_13: 0.8850,
    rate_14: 0.8772,
    rate_15: 0.8696,
  },
  {
    key: '2',
    Year_data: '2.000',
    rate_6: 0.8900,
    rate_7: 0.8734,
    rate_8: 0.8573,
    rate_9: 0.8417,
    rate_10: 0.8264,
    rate_11: 0.8116,
    rate_12: 0.7972,
    rate_13: 0.7831,
    rate_14: 0.7695,
    rate_15: 0.7561,
  },
  {
    key: '3',
    Year_data: '3.000',
    rate_6: 0.8396,
    rate_7: 0.8163,
    rate_8: 0.7938,
    rate_9: 0.7722,
    rate_10: 0.7513,
    rate_11: 0.7312,
    rate_12: 0.7118,
    rate_13: 0.6931,
    rate_14: 0.6750,
    rate_15: 0.6575,
  },
  {
    key: '4',
    Year_data: '4.000',
    rate_6: 0.7921,
    rate_7: 0.7629,
    rate_8: 0.7350,
    rate_9: 0.7084,
    rate_10: 0.6830,
    rate_11: 0.6587,
    rate_12: 0.6355,
    rate_13: 0.6133,
    rate_14: 0.5921,
    rate_15: 0.5718,
  },
  {
    key: '5',
    Year_data: '5.000',
    rate_6: 0.7473,
    rate_7: 0.7130,
    rate_8: 0.6806,
    rate_9: 0.6499,
    rate_10: 0.6209,
    rate_11: 0.5935,
    rate_12: 0.5674,
    rate_13: 0.5428,
    rate_14: 0.5194,
    rate_15: 0.4972,
  },
  {
    key: '6',
    Year_data: '6.000',
    rate_6: 0.7050,
    rate_7: 0.6663,
    rate_8: 0.6302,
    rate_9: 0.5963,
    rate_10: 0.5645,
    rate_11: 0.5346,
    rate_12: 0.5066,
    rate_13: 0.4803,
    rate_14: 0.4556,
    rate_15: 0.4323,
  },
  {
    key: '7',
    Year_data: '7.000',
    rate_6: 0.6651,
    rate_7: 0.6227,
    rate_8: 0.5835,
    rate_9: 0.5470,
    rate_10: 0.5132,
    rate_11: 0.4817,
    rate_12: 0.4523,
    rate_13: 0.4251,
    rate_14: 0.3996,
    rate_15: 0.3759,
  },
  {
    key: '8',
    Year_data: '8.000',
    rate_6: 0.6274,
    rate_7: 0.5820,
    rate_8: 0.5403,
    rate_9: 0.5019,
    rate_10: 0.4665,
    rate_11: 0.4339,
    rate_12: 0.4039,
    rate_13: 0.3762,
    rate_14: 0.3506,
    rate_15: 0.3269,
  },
];
export default {
  setup(props, { emit }) {
    const spinning = ref<boolean>(false)
    const summary = ref<string>("")
    const SUM = ref(0)
    const Year = ref(0)
    const RATE = ref(0)
    const VAF = ref(0)
    const sum = ref(0)
    const year = ref(0)
    const  unchanged = function (index) {
        tableData.value[index].unchanged = ((tableData.value[index].C) ? (tableData.value[index].C) : 0) + ((tableData.value[index].G) ? (tableData.value[index].G) : 0)

        var arr = [tableData.value[0].unchanged, tableData.value[1].unchanged, tableData.value[2].unchanged, tableData.value[3].unchanged,
        tableData.value[4].unchanged, tableData.value[5].unchanged, tableData.value[6].unchanged, tableData.value[7].unchanged];
        SUM.value = Math.min(...arr)

        let flag = 1
        for (let i = 0; i < parseInt(tableData.value[index].B), flag; i++) {
          if (tableData.value[i].unchanged == Math.min(...arr)) {
            flag = 0
            Year.value = i + 1
          }
        }
        return tableData.value[index].unchanged.toFixed(0)
      }
    
    const tableData = ref([
    {
          component: '1',
          number: '',
          A: '',
          B: '1',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,

          unchanged: 0,
        },
        {
          component: '2',
          number: '',
          A: '',
          B: '2',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,

          unchanged: 0,
        },
        {
          component: '3',
          number: '',
          A: '',
          B: '3',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,

          unchanged: 0,
        },
        {
          component: '4',
          number: '',
          A: '',
          B: '4',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,

          unchanged: 0,
        },
        {
          component: '5',
          number: '',
          A: '',
          B: '5',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,

          unchanged: 0,
        },
        {
          component: '6',
          number: '',
          A: '',
          B: '6',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,
          unchanged: 0,
        },
        {
          component: '7',
          number: '',
          A: '',
          B: '7',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,
          unchanged: 0,
        },
        {
          component: '8',
          number: '',
          A: '',
          B: '8',
          C: 0,
          D: '',
          E: 0,
          F: 0,
          G: 0,
          unchanged: 0,
        }
    ])
    const submit = () => {
      spinning.value = true;
      // console.log(tableData.value)
      const retTable = tableData.value.map((item)=>{
        // console.log(">>>");
        // console.log(item.C,item.G)
        // console.log(Math.round(item.C),Math.round(item.G))
        // console.log("<<<");
          return {
            // ...item一定要写在前面，否则后面的字段会覆盖前面的！
            ...item,
            unchanged:Math.round(item.unchanged),
            C:Math.round(item.C),
            G:Math.round(item.G),
           
          }
        })
      // console.log(retTable);
      
      const data = {
        // 此处为实验数据
        SUM:SUM.value,
        Year:Year.value,
        RATE:RATE.value,
        sum:sum.value,
        year:year.value,
        table:retTable,
        summary:summary.value,
      }
      // 数据提取完毕以后调用父组件的submit方法，并传递数据
      emit('submit', data)
      // 关闭加载动画
      spinning.value = false
    }
    return {
      spinning,
      data,
      columns,
      columns_PA,
      dataSource_PA,
      dataSource_PF,
      SUM,
      Year,
      RATE,
      VAF,
      sum,
      year,
      tableData,
      summary,
      unchanged,
      submit,
    };
  },
  data() {
    return {
      columns: [
        {
          title: '序号',
          dataIndex: 'B',
          align: 'center',
          width: 75,
        },
        {

          title: 'Salvage Value If Retired at Year n (step 1)',
          width: 160,
          dataIndex: 'A',
          align: 'center'
        },
        {
          title: 'AE(i) Cost If Retired in Year n [CR(i)] (step 2)',
          dataIndex: 'C',
          width: 160,
          align: 'center',
        },
        {
          align: 'center',
          dataIndex: 'D',
          title: 'O & M Costs for Year n (step 1)',
          width: 100,
        },
        {
          dataIndex: 'E',
          align: 'center',
          title: 'PW(i) of O&M for Year n in Year 0 (step 2)',
          width: 160,
        },
        {

          dataIndex: 'F',
          title: 'Sum of Year 0 O&Ms through Year n (step 3)',
          align: 'center',
          width: 160,
        },
        {
          dataIndex: 'G',
          align: 'center',
          title: 'AE(i) Cost of Operating for n Years (step 3)',
          width: 160,
        },

        {
          title: 'Total AE(i) If Retired at Year n (step 4)',
          dataIndex: 'unchanged',
          key: 'unchanged',
          align: 'center',
          //  width: 100,
          // fixed: 'right',
        },
      ],
    }
  },
  computed: {
    c() {
      return function (index) {
        let PAnum = 0
        let rate = 1 + parseFloat(this.RATE)
        let rate_i = 1
        for (let i = 0; i < parseInt(this.tableData[index].B); i++) {
          rate_i = 1
          for (let j = 0; j <= i; j++) {
            rate_i = rate_i * rate
          }
          PAnum = PAnum + 1 / rate_i
        }
        // console.log(typeof index)
        this.tableData[index].C = ((parseInt(this.tableData[index].D)) ? (10000 - parseInt(this.tableData[index].A)) / PAnum + parseInt(this.tableData[index].A) * (rate - 1) : 0)
        return this.tableData[index].C.toFixed(0)
      }
    },
    e() {
      return function (index) {
        // console.log(typeof index)
        let j = 1 + parseFloat(this.RATE)
        let n = 1
        for (let i = 0; i < parseInt(this.tableData[index].B); i++) {
          n = n * j
        }
        this.tableData[index].E = (parseInt(this.tableData[index].D) ? parseInt(this.tableData[index].D) : 0) / n
        return this.tableData[index].E.toFixed(0)
      }
    },
    f() {
      return function (index) {
        if (parseInt(this.tableData[index].B) == 1) {
          this.tableData[index].F = (this.tableData[index].E ? this.tableData[index].E : 0)
          return this.tableData[index].F.toFixed(0)
        }
        else {
          this.tableData[index].F = (this.tableData[index].D ? this.tableData[index].E : 0) + this.tableData[index - 1].F
          return this.tableData[index].F.toFixed(0)
        }

      }
    },
    g() {
      return function (index) {
        let PAnum = 0
        let rate = 1 + parseFloat(this.RATE)
        let rate_i = 1
        for (let i = 0; i < parseInt(this.tableData[index].B); i++) {
          rate_i = 1
          for (let j = 0; j <= i; j++) {
            rate_i = rate_i * rate
          }
          PAnum = PAnum + 1 / rate_i
        }
        this.tableData[index].G = (this.tableData[index].F ? this.tableData[index].F : 0) / PAnum
        return this.tableData[index].G.toFixed(0)
      }
    },
    i() {
      return function (index) {
        this.tableData[index].I = (parseInt(this.tableData[index].G) ? parseInt(this.tableData[index].G) : 0) * parseInt(this.tableData[index].H)
        return this.tableData[index].I.toFixed(0)
      }
    },
    number() {
      return function (index) {
        this.tableData[index].number = (parseInt(this.tableData[index].A) ? parseInt(this.tableData[index].A) : 0) + (parseInt(this.tableData[index].D) ? parseInt(this.tableData[index].D) : 0) + (parseInt(this.tableData[index].G) ? parseInt(this.tableData[index].G) : 0)
        return this.tableData[index].number
      }
    },
    // unchanged() {
    //   return function (index) {
    //     this.tableData[index].unchanged = (parseInt(this.tableData[index].C) ? parseInt(this.tableData[index].C) : 0) + (parseInt(this.tableData[index].G) ? parseInt(this.tableData[index].G) : 0)

    //     var arr = [this.tableData[0].unchanged, this.tableData[1].unchanged, this.tableData[2].unchanged, this.tableData[3].unchanged,
    //     this.tableData[4].unchanged, this.tableData[5].unchanged, this.tableData[6].unchanged, this.tableData[7].unchanged];
    //     this.SUM = Math.min(...arr)

    //     let flag = 1
    //     for (let i = 0; i < parseInt(this.tableData[index].B), flag; i++) {
    //       if (this.tableData[i].unchanged == Math.min(...arr)) {
    //         flag = 0
    //         this.Year = i + 1
    //       }
    //     }
    //     return this.tableData[index].unchanged.toFixed(0)
    //   }
    // },
  },
}


</script>

<style scoped>

.button3 {
  float: right;
  margin-top: 10px;
  margin-right: 50px;
}


.secondtitle {
  text-indent: 2em;
  /* 首行缩进两字符2em */
  font-weight: bold;
  /* 加粗 */
  margin-left: 20px;
  /* 左边缘50px */
  margin-right: 30px;
  font-size: 17px;
}

.text {
  text-indent: 2em;
  margin-left: 20px;
  margin-right: 20px;
  font-size: 17px;
}
</style>