<template>
  <div>
    <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
  <div id="pdfDom" style="margin-left: 10px;">
   <div class="steps-content">
     <div class="exp-step-card" v-if="current == 0" ref="content1">
    <h2><b>一、实验目的 </b></h2>
    <p class="content">1. 理解盈亏平衡分析的意义</p>
    <p class="content">2. 掌握盈亏平衡分析的基本程序</p>
    <p class="content">3. 掌握盈亏平衡分析的一般方法，为企业预测和决策提供依据</p>
        
   </div>
     <div class="exp-step-card" v-if="current == 1" ref="content2">
    <h2><b>二、实验原理 </b></h2>
    <p class="secondtitle">1. 简介</p>
    <p class="content">
      盈亏平衡法是基于“成本-销量-利润”相互关系的一种分析方法。
      该方法首先将项目成本分成固定成本和变动成本两部分，固定成本是指在一定业务范围和一定时间范围内，
      不随业务总量变化而发生变化的成本，例如资产的折旧和摊销、软件项目间接的人力成本。变动成本是指一定
      业务范围和一定时间范围内，随着业务总量变化成比例相应变化的成本，如软件项目直接人力成本、直接非人力成本等。
    </p>
    <p class="secondtitle">2. 计算方法</p>
    <p class="content">盈亏平衡法的计算关系式：</p>
    <p class="content">总成本=变动成本＋固定成本=单位变动成本×销量+固定成本</p>
    <p class="content">利润=销售收入-总成本=单价×销量-单位变动成本×销量-固定成本</p>
    <p class="content">上式中，令利润定于“零”，此时的销量称为盈亏平衡点销量。即：盈亏平衡点销量=固定成本÷(单价-单位变动成本)</p>
    <p class="content">实践中，常用销售金额表示盈亏平衡点，则整理成上述公式得到：盈亏平衡点销售额=固定成本÷(1-(单位变动成本/单价))</p>

    <p class="secondtitle">3. 风险分析</p>
    <p class="content">盈亏平衡法定义了盈亏平衡点作业率、安全边际和安全边际率三个指标，可用于项目法人风险分析。指标定义:</p>
    <p class="content">盈亏平衡点作业率=盈亏平衡点销量÷正常销量×100%</p>
    <p class="content">安全边际=正常销量-盈亏平衡点销量</p>
    <p class="content">安全边际率=安全边际÷正常销量×100%</p>
    <p class="content">盈亏平衡点作业率是企业保本销量中所占的比重，也是企业在不亏本情况下生产经营能力利用率的百分比。
      安全边际率越大，企业发生亏损可能性越小。</p>
    </div>
     <div class="exp-step-card" v-if="current == 2" ref="content3">
    <h2><b>三、实验步骤</b></h2>

      <p style="margin-right: 20px;margin-left: 20px;text-indent: 2em;">项目不同阶段的成本、单价、销量、单位变动成本不同，该阶段的盈亏平衡点与安全边际率也不相同，
        计算不同阶段项目的盈亏平衡点与安全边际率可用于项目的风险分析。</p>
      <a-form>
        <p class="content_procedure">步骤一、选择实验阶段，本实验阶段以月为单位</p>
        <a-form-item has-feedback label="阶段名称" name="phase">
          <a-date-picker v-model:value="month" picker="month" style="margin-left: 30px;" format='YYYY-MM'
            placeholder="选择阶段" />

        </a-form-item>
        <p class="content_procedure">步骤二、填写固定成本</p>
        <a-form-item has-feedback label="固定成本" name="fixed_cost">
          <a-input-number class="inputItem" v-model:value="fixed_cost" />
        </a-form-item>
        <p class="content_procedure">步骤三、填写产品单价</p>
        <a-form-item has-feedback label="单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价" name="single_price">
          <a-input-number style="width:150px;margin-left: 31px;" v-model:value="single_price" />
        </a-form-item>
        <p class="content_procedure">步骤四、填写此阶段的销量</p>
        <a-form-item has-feedback label="阶段销量" name="sales">
          <a-input-number class="inputItem" v-model:value="sales" />
        </a-form-item>
        <p class="content_procedure">步骤五、填写产品单位变动成本</p>
        <a-form-item has-feedback label="单位变动成本" name="variable_cost">
          <a-input-number style="width:150px;margin-left: 2px;" v-model:value="variable_cost" />
        </a-form-item>
        <p class="content_procedure" style="margin-bottom:20px">步骤六、计算盈亏平衡点</p>
        <a-form-item>
          <a-button type="primary" style="margin-left: 0px" @click=calculate>计算盈亏平衡点</a-button>
          <a-button style="margin-left: 54px" @click=myStore.clear>重置</a-button>
        </a-form-item>
      </a-form>

      <a-table :data-source="tableData" :columns="columns" bordered>
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.dataIndex === 'phase'">
            <a>{{ text }}</a>
          </template>
          <template v-if="column.dataIndex === 'delete'">
            <a-popconfirm v-if="tableData.length" title="确认删除？" @confirm="myStore.onDelete(record.key)">
              <a>删除</a>
            </a-popconfirm>
            <a>&nbsp;|&nbsp;</a>
            <a @click="drawChart(record.key)">生成图表</a>
          </template>
        </template>
        <template #title>实验结果</template>
      </a-table>
      <div class="avg_rate">

        <b>平均安全边际率:&nbsp;</b><b>{{ avg_safety_rate }}</b>
      </div>
      <div id="chart" style="width: 550px;height:400px;margin-top: 10px;">

      </div>
      <div style="margin-top: 30px;">
        <a-table :columns="column_safety" :data-source="dataSource" bordered>
          <template #bodyCell="{ column, text }">
            <template v-if="column.dataIndex === 'safety_rate'">
              <a>{{ text }}</a>
            </template>
          </template>
          <template #title>安全边际率经验值表</template>
        </a-table>
      </div>
    </div>
    <div class="exp-step-card" v-if="current == 3" ref="content4">
      <div id="feeling" style="margin-top: 10px;margin-bottom: 10px;">
        <h2><b>四、实验心得</b></h2>
        <a-textarea style="margin-top: 5px;" v-model:value="summary" placeholder="写下你的实验心得" :rows="4" show-count: maxlength="1000" />
      </div>
    </div>
  </div>
  <div style="margin-top: 20px; display: flex;justify-content: flex-end;">
    <a-spin :spinning="spinning">
      <div style="display: flex; justify-content: flex-end;">
        <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button>
      </div>
    </a-spin>
  </div>
  </div>
</div>
</template>
<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import dayjs, { Dayjs } from 'dayjs';
import * as echarts from 'echarts';
import moment from 'moment';
import 'moment/locale/zh-cn';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import { useStore } from './stores';
import { storeToRefs } from 'pinia';
import html2canvas from 'html2canvas';
import { message } from 'ant-design-vue';
moment.locale('zh-cn');

const current = ref(0);

const steps = [
    { title: '实验目的', content: '0-content' },
    { title: '实验原理', content: 'First-content' },
    { title: '实验步骤', content: 'Second-content' },
    {title: '实验心得', content: 'Last-content'}
];


const content1 = ref(null);
const content2 = ref(null);
const content3 = ref(null);
const content4 = ref(null);
const content5 = ref(null);
// const content6 = ref(null);
// const content7 = ref(null);
const contentRecord = ref([]);

function addRecord() {
  if (current.value == 0) {
    //const content1 = this.$refs.content1;
    contentRecord.value.push(content1);
  }
  if (current.value == 1) {
    contentRecord.value.push(content2);
  }
  if (current.value == 2) {
    contentRecord.value.push(content3);
    //this.created();
  }
  if (current.value == 3) {
    contentRecord.value.push(content4);
  }
  if (current.value == 4) {
    contentRecord.value.push(content5);
  }
  // if (current.value == 5) {
  //   //const content6 = this.$refs.content6;
  //   contentRecord.value.push(content6);
  // }
  // if (current.value == 6) {
  //   //const content7 = this.$refs.content7;
  //   contentRecord.value.push(content7);
  // }
}

const myStore = useStore();
const {spinning,
    month,
    fixed_cost,
    single_price,
    sales,
    variable_cost,
    safety_rate, // 安全边际率
    summary, // 实验心得// 平均安全边际率
    tableData,} = storeToRefs(useStore());
// Data

const avg_safety_rate = computed(() => {
      var i = 0
      var temp = 0
      if (tableData.value.length >= 1) {
        for (i = 0; i < tableData.value.length; i++) {
          const percentage = parseFloat(tableData.value[i].safety_rate.replace("%", "")) / 100;
          temp += percentage
        }
        var result = temp / tableData.value.length
        return (result * 100).toFixed(2) + "%"
      }
      else {
        return "0"
      }
})//平均安全边际率
function calculate() {
      if (month.value && fixed_cost.value && single_price.value && sales.value && variable_cost.value) {

        //盈亏平衡点（销售额）= 固定成本/(1-单位变动成本/单价)
        const breakeven_point = fixed_cost.value / (1 - (variable_cost.value / single_price.value))
        const _breakeven_point = breakeven_point.toFixed(2)
        //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
        const breakeven_sales = fixed_cost.value / (single_price.value - variable_cost.value)
        const _breakeven_sales = breakeven_sales.toFixed(2);
        //安全边际率 = (正常销量-盈亏平衡点销量)/正常销量
        const _safety_rate = (sales.value - breakeven_sales) / sales.value
        const percentage = (_safety_rate * 100).toFixed(2) + '%';
        // console.log(this.month)
        tableData.value.push({
          key: tableData.value.length + 1,
          phase: month.value.format('YYYY-MM'),
          fixed_cost: fixed_cost.value,
          single_price: single_price.value,
          sales: sales.value,
          variable_cost: variable_cost.value,
          result: _breakeven_point,
          result_sales: _breakeven_sales,
          safety_rate: percentage
        });
      }
      console.log(tableData.value)
    }

function drawChart(index) {
      console.log(index)
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart'));
      // 配置图表选项
      const options = {
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' }
        },
        title: {
          text: tableData.value[parseInt(index) - 1].phase, // 设置标题文本
          left: 'center' // 设置标题位置（居中）
        },
        xAxis: {
          type: 'value',
          name: '销量',
          scale: true,
          axisLabel: {
            formatter: '{value}', // 设置 X 轴刻度格式
          },
          axisLine: {
            onZero: true, // 将 X 轴的原点位置设置为 0
          },
        },
        yAxis: {
          type: 'value',
          name: '单价',
          scale: true,
          min: 0,
          max: tableData.value[parseInt(index) - 1].result * 2,
          axisLabel: {
            formatter: '{value}', // 设置 Y 轴刻度格式
          },
          axisLine: {
            onZero: true, // 将 Y 轴的原点位置设置为 0
          },
        },
        legend: {
          top: 30, // 设置图例位置在上方
          right: 50, // 设置图例位置在右侧
          data: ['销售额', '变动成本', '固定成本'] // 指定每个系列的名称
        },
        series: [
          {
            type: 'line',
            name: '销售额',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            markLine: {
              silent: true,
              data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
            },
            emphasis: {
              label: {
                show: true,
                formatter: '销售额', // 设置强调样式的标签文本
              },
            },
          },
          {
            type: 'line',
            name: '变动成本',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            markLine: {
              silent: true,
              data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
            },
            emphasis: {
              label: {
                show: true,
                formatter: '变动成本', // 设置强调样式的标签文本
              },
            },
          },
          {
            type: 'line',
            name: '固定成本',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            emphasis: {
              label: {
                show: true,
                formatter: '固定成本', // 设置强调样式的标签文本
              },
            },
          },
        ],
      };

  // 填充数据
  const sales = [];
      const variable_cost = [];
      const fixed_cost = [];
      const key = parseInt(index) - 1
      // console.log(tableData.value[key])
      // const _sales = parseInt(tableData.value[key].sales)
      const _single_price = tableData.value[key].single_price
      const _variable_price = tableData.value[key].variable_cost
      const _fixed_cost = tableData.value[key].fixed_cost
      //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
      const breakeven_sales = tableData.value[key].fixed_cost / (tableData.value[key].single_price - tableData.value[key].variable_cost)

      for (let x = 0; x <= breakeven_sales * 2; x++) {
        const y1 = _single_price * x;//销售额
        const y2 = _variable_price * x + _fixed_cost;//变动成本
        const y3 = _fixed_cost;//固定成本
        sales.push([x, y1]);
        // console.log(sales)
        variable_cost.push([x, y2]);
        fixed_cost.push([x, y3])
      }
      console.log(sales)
      options.series[0].data = sales;
      options.series[1].data = variable_cost;
      options.series[2].data = fixed_cost;
      // 使用配置项显示图表
      myChart.setOption(options);
    }

const emit = defineEmits({
  submit: (data) => {
    return true
  }
});
const submit = () => {
  spinning.value = true;
  let imgUrl1 = "";
  const experiment_id = "81";
  const { account } = useAccountStore();
  const { uploadImg } = useExperimentStore();
  
  const chartElement = document.getElementById("chart");
  
  if (!chartElement) {
    message.info("请先绘制图表")
    // console.error("Element with ID 'chart' not found.");
    spinning.value = false;
    return;
  }
  
  html2canvas(chartElement).then(function (canvas) {
    canvas.toBlob(function (blob) {
      imgUrl1 = uploadImg(blob, experiment_id, account.email.slice(0, account.email.indexOf("@")), "yinkuipingheng");
      const data = {
        month: month.value,
        fixed_cost: fixed_cost.value,
        single_price: single_price.value,
        sales: sales.value,
        variable_cost: variable_cost.value,
        image: {
          chart: imgUrl1,
        },
        avg_safety_rate: avg_safety_rate.value,
        table: tableData.value,
        summary: summary.value,
      };
      emit('submit', data);
      spinning.value = false;
    });
  });
};
const columns = ref([
        {
          title: '阶段',
          dataIndex: 'phase',
          key: 'phase',
        },
        {
          title: '固定成本',
          dataIndex: 'fixed_cost',
          key: 'fixed_cost',
        },
        {
          title: '单价',
          dataIndex: 'single_price',
          key: 'single_price',
        },
        {
          title: '阶段销量',
          dataIndex: 'sales',
          key: 'sales',
        },
        {
          title: '单价变动成本',
          dataIndex: 'variable_cost',
          key: 'variable_cost',
        },
        {
          title: '盈亏平衡点(单价)',
          dataIndex: 'result',
          key: 'result',
        },
        {
          title: '盈亏平衡点(销量)',
          dataIndex: 'result_sales',
          key: 'result_sales',
        },
        {
          title: '安全边际率',
          dataIndex: 'safety_rate',
          key: 'safety_rate',
        },
        {
          title: '操作',
          dataIndex: 'delete',
          key: 'delete',
        },
      ]);

const column_safety= ref ([
        {
          title: '安全边际率',
          dataIndex: 'safety_rate',
          key: 'safety_rate',
        },
        {
          title: '>40%',
          dataIndex: 'phase1',
          key: '>40%',
        },
        {
          title: '30%~40%',
          dataIndex: 'phase2',
          key: '30%~40%',
        },
        {
          title: '20%~30%',
          dataIndex: 'phase3',
          key: '20%~30%',
        },
        {
          title: '10%~20%',
          dataIndex: 'phase4',
          key: '10%~20%',
        },
        {
          title: '<10%',
          dataIndex: 'phase5',
          key: '<10%',
        },

      ]);

const dataSource= ref ([
        {
          safety_rate: '安全等级',
          phase1: '很安全',
          phase2: '安全',
          phase3: '较安全',
          phase4: '需要关注',
          phase5: '危险',
        }
      ]);


</script>
<!-- <script lang="ts">

import { ref } from 'vue';
import type { Dayjs } from 'dayjs';
import * as echarts from 'echarts';
import moment from 'moment'
import 'moment/locale/zh-cn'
import { computed } from 'vue';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import html2canvas from 'html2canvas';
moment.locale('zh-cn')

export default {
  setup(props, { emit }) {
    const spinning = ref<boolean>(false)
    const phase = ref('')
    const month = ref<Dayjs>()
    const fixed_cost = ref(0)
    const single_price = ref(0)
    const sales = ref(0)
    const variable_cost = ref(0)
    const safety_rate = ref(0)//安全边际率
    const summary = ref('')//实验心得
    const tableData = ref([])
    const avg_safety_rate = computed(() => {
      var i = 0
      var temp = 0
      if (tableData.value.length >= 1) {
        for (i = 0; i < tableData.value.length; i++) {
          const percentage = parseFloat(tableData.value[i].safety_rate.replace("%", "")) / 100;
          temp += percentage
        }
        var result = temp / tableData.value.length
        return (result * 100).toFixed(2) + "%"
      }
      else {
        return "0"
      }
    })//平均安全边际率
    function calculate() {
      if (month.value && fixed_cost.value && single_price.value && sales.value && variable_cost.value) {

        //盈亏平衡点（销售额）= 固定成本/(1-单位变动成本/单价)
        const breakeven_point = fixed_cost.value / (1 - (variable_cost.value / single_price.value))
        const _breakeven_point = breakeven_point.toFixed(2)
        //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
        const breakeven_sales = fixed_cost.value / (single_price.value - variable_cost.value)

        //安全边际率 = (正常销量-盈亏平衡点销量)/正常销量
        const _safety_rate = (sales.value - breakeven_sales) / sales.value
        const percentage = (_safety_rate * 100).toFixed(2) + '%';
        // console.log(this.month)
        tableData.value.push({
          key: tableData.value.length + 1,
          phase: month.value.format('YYYY-MM'),
          fixed_cost: fixed_cost.value,
          single_price: single_price.value,
          sales: sales.value,
          variable_cost: variable_cost.value,
          result: _breakeven_point,
          safety_rate: percentage
        });
      }
      console.log(tableData.value)
    }
    function drawChart(index) {
      console.log(index)
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart'));
      // 配置图表选项
      const options = {
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' }
        },
        title: {
          text: tableData.value[parseInt(index) - 1].phase, // 设置标题文本
          left: 'center' // 设置标题位置（居中）
        },
        xAxis: {
          type: 'value',
          name: '销量',
          scale: true,
          axisLabel: {
            formatter: '{value}', // 设置 X 轴刻度格式
          },
          axisLine: {
            onZero: true, // 将 X 轴的原点位置设置为 0
          },
        },
        yAxis: {
          type: 'value',
          name: '金额',
          scale: true,
          min: 0,
          max: tableData.value[parseInt(index) - 1].result * 2,
          axisLabel: {
            formatter: '{value}', // 设置 Y 轴刻度格式
          },
          axisLine: {
            onZero: true, // 将 Y 轴的原点位置设置为 0
          },
        },
        legend: {
          top: 30, // 设置图例位置在上方
          right: 50, // 设置图例位置在右侧
          data: ['销售额', '变动成本', '固定成本'] // 指定每个系列的名称
        },
        series: [
          {
            type: 'line',
            name: '销售额',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            markLine: {
              silent: true,
              data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
            },
            emphasis: {
              label: {
                show: true,
                formatter: '销售额', // 设置强调样式的标签文本
              },
            },
          },
          {
            type: 'line',
            name: '变动成本',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            markLine: {
              silent: true,
              data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
            },
            emphasis: {
              label: {
                show: true,
                formatter: '变动成本', // 设置强调样式的标签文本
              },
            },
          },
          {
            type: 'line',
            name: '固定成本',
            data: [],
            symbol: 'none', // 设置无数据点的样式
            smooth: true, // 设置平滑曲线
            lineStyle: {
              width: 2,
            },
            // markLine: {
            //   silent: true,
            //   data: [{ yAxis: 5000, label: { show: false } }],
            // },
            emphasis: {
              label: {
                show: true,
                formatter: '固定成本', // 设置强调样式的标签文本
              },
            },
          },
        ],
      };
      // 填充数据
      const sales = [];
      const variable_cost = [];
      const fixed_cost = [];
      const key = parseInt(index) - 1
      // console.log(tableData.value[key])
      // const _sales = parseInt(tableData.value[key].sales)
      const _single_price = tableData.value[key].single_price
      const _variable_price = tableData.value[key].variable_cost
      const _fixed_cost = tableData.value[key].fixed_cost
      //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
      const breakeven_sales = tableData.value[key].fixed_cost / (tableData.value[key].single_price - tableData.value[key].variable_cost)

      for (let x = 0; x <= breakeven_sales * 2; x++) {
        const y1 = _single_price * x;//销售额
        const y2 = _variable_price * x + _fixed_cost;//变动成本
        const y3 = _fixed_cost;//固定成本
        sales.push([x, y1]);
        // console.log(sales)
        variable_cost.push([x, y2]);
        fixed_cost.push([x, y3])
      }
      console.log(sales)
      options.series[0].data = sales;
      options.series[1].data = variable_cost;
      options.series[2].data = fixed_cost;
      // 使用配置项显示图表
      myChart.setOption(options);
    }
    const submit = () => {
      spinning.value = true;
      let imgUrl1 = ""
      const experiment_id = "81"
      const { account } = useAccountStore()
      const { uploadImg } = useExperimentStore()
      html2canvas(document.getElementById("chart")).then(function (canvas) {
        canvas.toBlob(
          function (blob) {
            imgUrl1 = uploadImg(blob, experiment_id, account.email.slice(0, account.email.indexOf("@")), "yinkuipingheng");
            const data = {
              // 此处为实验数据
              month: month.value,
              fixed_cost: fixed_cost.value,
              single_price: single_price.value,
              sales: sales.value,
              variable_cost: variable_cost.value,
              image: {
                  chart: imgUrl1,
                },
              avg_safety_rate: avg_safety_rate.value,
              table: tableData.value,
              summary: summary.value,
            }
            // 数据提取完毕以后调用父组件的submit方法，并传递数据
            emit('submit', data)
            // 关闭加载动画
            spinning.value = false
          });

      })
    }
    return {
      spinning,
      phase,
      month,
      fixed_cost,
      single_price,
      sales,
      variable_cost,
      safety_rate,//安全边际率
      summary,//实验心得
      avg_safety_rate,//平均安全边际率
      tableData,

      drawChart,
      calculate,
      submit
    };
  },
  data() {
    return {
      columns: [
        {
          title: '阶段',
          dataIndex: 'phase',
          key: 'phase',
        },
        {
          title: '固定成本',
          dataIndex: 'fixed_cost',
          key: 'fixed_cost',
        },
        {
          title: '单价',
          dataIndex: 'single_price',
          key: 'single_price',
        },
        {
          title: '阶段销量',
          dataIndex: 'sales',
          key: 'sales',
        },
        {
          title: '单价变动成本',
          dataIndex: 'variable_cost',
          key: 'variable_cost',
        },
        {
          title: '盈亏平衡点',
          dataIndex: 'result',
          key: 'result',
        },
        {
          title: '安全边际率',
          dataIndex: 'safety_rate',
          key: 'safety_rate',
        },
        {
          title: '操作',
          dataIndex: 'delete',
          key: 'delete',
        },

      ],

      column_safety: [
        {
          title: '安全边际率',
          dataIndex: 'safety_rate',
          key: 'safety_rate',
        },
        {
          title: '>40%',
          dataIndex: 'phase1',
          key: '>40%',
        },
        {
          title: '30%~40%',
          dataIndex: 'phase2',
          key: '30%~40%',
        },
        {
          title: '20%~30%',
          dataIndex: 'phase3',
          key: '20%~30%',
        },
        {
          title: '10%~20%',
          dataIndex: 'phase4',
          key: '10%~20%',
        },
        {
          title: '<10%',
          dataIndex: 'phase5',
          key: '<10%',
        },

      ],

      dataSource: [
        {
          safety_rate: '安全等级',
          phase1: '很安全',
          phase2: '安全',
          phase3: '较安全',
          phase4: '需要关注',
          phase5: '危险',
        }
      ]
    }
  },
  // computed: {
  //   avg_safety_rate() {
  //     var i = 0
  //     var temp = 0
  //     if (this.tableData.length >= 1) {
  //       for (i = 0; i < this.tableData.length; i++) {
  //         const percentage = parseFloat(this.tableData[i].safety_rate.replace("%", "")) / 100;
  //         temp += percentage
  //       }
  //       var result = temp / this.tableData.length
  //       return (result * 100).toFixed(2) + "%"
  //     }
  //     else {
  //       return "0"
  //     }
  //   },
  // },
  methods: {
    // calculate() {
    //   if (this.month && this.fixed_cost && this.single_price && this.sales && this.variable_cost) {

    //     //盈亏平衡点（销售额）= 固定成本/(1-单位变动成本/单价)
    //     const breakeven_point = parseInt(this.fixed_cost) / (1 - (parseInt(this.variable_cost) / parseInt(this.single_price)))
    //     const _breakeven_point = breakeven_point.toFixed(2)
    //     //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
    //     const breakeven_sales = parseInt(this.fixed_cost) / (parseInt(this.single_price) - parseInt(this.variable_cost))

    //     //安全边际率 = (正常销量-盈亏平衡点销量)/正常销量
    //     const _safety_rate = (parseInt(this.sales) - breakeven_sales) / parseInt(this.sales)
    //     const percentage = (_safety_rate * 100).toFixed(2) + '%';
    //     console.log(this.month)
    //     this.tableData.push({
    //       key: this.tableData.length + 1,
    //       phase: this.month.format('YYYY-MM'),
    //       fixed_cost: this.fixed_cost,
    //       single_price: this.single_price,
    //       sales: this.sales,
    //       variable_cost: this.variable_cost,
    //       result: _breakeven_point,
    //       safety_rate: percentage
    //     });
    //   }
    //   // console.log(this.tableData.length)
    // },

    // drawChart(index) {
    //   console.log(index)
    //   // 基于准备好的dom，初始化echarts实例
    //   var myChart = echarts.init(document.getElementById('chart'));
    //   // 配置图表选项
    //   const options = {
    //     tooltip: {
    //       trigger: 'axis',
    //       axisPointer: { type: 'cross' }
    //     },
    //     title: {
    //       text: this.tableData[parseInt(index) - 1].phase, // 设置标题文本
    //       left: 'center' // 设置标题位置（居中）
    //     },

    //     xAxis: {
    //       type: 'value',
    //       name: '销量',
    //       scale: true,
    //       axisLabel: {
    //         formatter: '{value}', // 设置 X 轴刻度格式
    //       },
    //       axisLine: {
    //         onZero: true, // 将 X 轴的原点位置设置为 0
    //       },
    //     },
    //     yAxis: {
    //       type: 'value',
    //       name: '金额',
    //       scale: true,
    //       min: 0,
    //       max: parseInt(this.tableData[parseInt(index) - 1].result) * 2,
    //       axisLabel: {
    //         formatter: '{value}', // 设置 Y 轴刻度格式
    //       },
    //       axisLine: {
    //         onZero: true, // 将 Y 轴的原点位置设置为 0
    //       },
    //     },
    //     legend: {
    //       top: 30, // 设置图例位置在上方
    //       right: 50, // 设置图例位置在右侧
    //       data: ['销售额', '变动成本', '固定成本'] // 指定每个系列的名称
    //     },
    //     series: [
    //       {
    //         type: 'line',
    //         name: '销售额',
    //         data: [],
    //         symbol: 'none', // 设置无数据点的样式
    //         smooth: true, // 设置平滑曲线
    //         lineStyle: {
    //           width: 2,
    //         },
    //         markLine: {
    //           silent: true,
    //           data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
    //         },
    //         emphasis: {
    //           label: {
    //             show: true,
    //             formatter: '销售额', // 设置强调样式的标签文本
    //           },
    //         },
    //       },
    //       {
    //         type: 'line',
    //         name: '变动成本',
    //         data: [],
    //         symbol: 'none', // 设置无数据点的样式
    //         smooth: true, // 设置平滑曲线
    //         lineStyle: {
    //           width: 2,
    //         },
    //         markLine: {
    //           silent: true,
    //           data: [{ yAxis: { type: 'average', value: 0 }, label: { show: false } }],
    //         },
    //         emphasis: {
    //           label: {
    //             show: true,
    //             formatter: '变动成本', // 设置强调样式的标签文本
    //           },
    //         },
    //       },
    //       {
    //         type: 'line',
    //         name: '固定成本',
    //         data: [],
    //         symbol: 'none', // 设置无数据点的样式
    //         smooth: true, // 设置平滑曲线
    //         lineStyle: {
    //           width: 2,
    //         },
    //         // markLine: {
    //         //   silent: true,
    //         //   data: [{ yAxis: 5000, label: { show: false } }],
    //         // },
    //         emphasis: {
    //           label: {
    //             show: true,
    //             formatter: '固定成本', // 设置强调样式的标签文本
    //           },
    //         },
    //       },
    //     ],
    //   };
    //   // 填充数据
    //   const sales = [];
    //   const variable_cost = [];
    //   const fixed_cost = [];
    //   const key = parseInt(index) - 1
    //   console.log(this.tableData[key])
    //   const _sales = parseInt(this.tableData[key].sales)
    //   const _single_price = parseInt(this.tableData[key].single_price)
    //   const _variable_price = parseInt(this.tableData[key].variable_cost)
    //   const _fixed_cost = parseInt(this.tableData[key].fixed_cost)
    //   //盈亏平衡点销量 = 固定成本/(单价-单位变动成本)
    //   const breakeven_sales = parseInt(this.tableData[key].fixed_cost) / (parseInt(this.tableData[key].single_price) - parseInt(this.tableData[key].variable_cost))
    //   for (let x = 0; x <= breakeven_sales * 2; x++) {
    //     const y1 = _single_price * x;//销售额
    //     const y2 = _variable_price * x + _fixed_cost;//变动成本
    //     const y3 = _fixed_cost;//固定成本
    //     sales.push([x, y1]);
    //     variable_cost.push([x, y2]);
    //     fixed_cost.push([x, y3])
    //   }
    //   options.series[0].data = sales;
    //   options.series[1].data = variable_cost;
    //   options.series[2].data = fixed_cost;
    //   // 使用配置项显示图表
    //   myChart.setOption(options);
    // },

    clear() {
      this.month = '';
      this.phase = '';
      this.fixed_cost = '';
      this.single_price = '';
      this.sales = '';
      this.variable_cost = ''
    },

    onDelete(key: string) {
      this.tableData.splice(parseInt(key) - 1, 1); // 删除下标为key的元素
    },
  }
}

</script> -->

<style lang="less">
.title {
    text-align: center;
    font-family: sans-serif;
    font-size: 30px;
}
.secondtitle {
  text-indent: 2em;
  font-weight: bold;
  margin-left: 10px;
  margin-right: 30px;
  font-size: 15px;
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

.inputItem {
  width: 150px;
  margin-left: 30px;
}

.content_procedure {
  font-weight: bold;

}

.content {
  text-indent: 2em;
  margin-left: 20px;
  margin-right: 20px;
}

.right-button {
  float: right;
  margin-left: 30px;
}

.avg_rate {
  float: right;
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: medium;
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
    padding-inline: 14px;
}
</style>
