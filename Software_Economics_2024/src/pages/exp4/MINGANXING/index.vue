<template>
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step title="实验目的" />
      <a-step title="实验原理" />
      <a-step title="实验步骤" />
      <a-step title="实验过程" />
      <a-step title="实验小结与心得" />
    </a-steps>
    <div class="steps-content">
      <div v-if="current === 0">
        <h2>一、实验目的 </h2>
        <p class="content">研究相关因素的变动对项目经济效果评价指标的影响程度</p>
        <p class="content">找出影响项目经济效果的敏感因素</p>
        <p class="content">区分敏感性大的方案和敏感性小的方案，以便选出风险小的方案</p>
        <p class="content">找出项目方案的最好与最坏的经济效果的变化范围，对不确定性因素实施控制</p>
      </div>
      <div v-if="current === 1">
        <h2>二、实验原理</h2>
        <p class="secondtitle">敏感性分析：</p>
        <p class="content">
          敏感性分析是指从定量分析的角度研究有关因素发生某种变化对某一个或一组关键指标影响程度的一种不确定分析技术。其实质是通过逐一改变相关变量数值的方法来解释关键指标受这些因素变动影响大小的规律。
        </p>
        <p class="content">
          敏感性因素一般可选择主要参数（如销售收入、经营成本、生产能力、初始投资、寿命期、建设期、达产期等）进行分析。若某参数的小幅度变化能导致经济效果指标的较大变化，则称此参数为敏感性因素，反之则称其为非敏感性因素。
        </p>
        <p class="secondtitle">利润的敏感性分析：</p>
        <p class="content">
          利润的敏感性分析是指专门研究制约利润的有关因素在特定条件下发生变化时对利润所产生影响的一种敏感性的分析方法。进行利润敏感性分析的主要目的是计算有关因素的利润灵敏度指标，揭示利润与有关因素之间的相对关系，并利用灵敏度指标进行利润预测。
        </p>
        <p class="secondtitle">计算公式：</p>
        <p class="content">
          任意第1个因素的利润灵敏度指标=该因素的中间变量基数÷利润基数×100%
        </p>
      </div>
      <div v-if="current === 2">
        <h2>三、实验步骤</h2>
        <p class="secondtitle">第一步：</p>
        <p class="content">选定需要分析的不确定因素。</p>
        <p class="content">不确定因素通过分析系统模型获得。确定不确定因素后，将其填写在分析变量表中。</p>
        <p class="secondtitle">第二步：</p>
        <p class="content">确定进行敏感性分析经济评价指标。</p>
        <p class="content">确定经济评价指标后，将其填写在变化幅度表中。</p>
        <p class="secondtitle">第三步：</p>
        <p class="content">计算不确定因素变动对指标影响的数量结果。</p>
        <p class="content">将设计好的不确定因素变化情况填入分析变量表，然后将对应的经济评价指标变化结果填入变化幅度表。</p>
        <p class="secondtitle">第四步：</p>
        <p class="content">绘制敏感性分析统计图。</p>
        <p class="content">点击“更新敏感性分析结果”，将根据填写的敏感性因素、经济评价指标变化程度，绘制用于敏感性分析的龙卷风图和折线图。</p>
        <p class="secondtitle">第五步：</p>
        <p class="content">确定敏感因素。</p>
        <p class="content">通过绘制的敏感性分析图表，结合相关计算结果，确定出各敏感因素的敏感程度。</p>
      </div>
      <div v-if="current === 3">
        <h2>四、实验过程</h2>
        <InputChart :dataSource="dataSource1" :columns="columns1" @updateData="handleDataSource1Update">选择分析变量</InputChart>
        <div class="container" style="margin-top: 20px;">
          <a-table :dataSource="dataSource" :columns="columns" bordered :pagination="false" />
        </div>
        <InputChart :dataSource="dataSource1" :columns="columns2" @updateData="handleDataSource1Update">选择变化幅度</InputChart>
        <form style="text-align: center;">
          <a-button type="primary" @click="updateAnalysis">更新敏感性分析结果</a-button>
        </form>
        <div>
          <h2>敏感性分析龙卷风图</h2>
          <div id="sensitivity-chart" style="height:100%;min-height:400px;text-align: center;"></div>
        </div>
        <div>
          <h2>敏感性分析折线图</h2>
          <div id="line-chart" style="height:100%;min-height:400px;text-align: center;"></div>
        </div>
      </div>
      <div v-if="current === 4">
        <h2>五、实验小结与心得</h2>
        <p class="content">结合实验内容和结果，总结实验，并在实验报告中填写实验小结和心得。</p>
        <a-textarea v-model:value=review :autoSize="{ minRows: 7}"
                    style="margin-top: 10px; margin-bottom: 10px"></a-textarea>
      </div>
    </div>
    <div class="steps-action">
      <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
        @click="next"><step-forward-outlined />下一步</a-button>
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" @click="submit">
        提交
      </a-button>
      <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
    </div>
  </div>
  <div v-if="current > 0" style="margin-top: 20px; display: flex;justify-content: flex-end;">
        <a-button  @click="clearData" >清空数据</a-button>
    </div>
</template>

<script setup lang="ts">
import * as echarts from 'echarts'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LegendComponent, TooltipComponent } from 'echarts/components'
import InputChart from '@/pages/exp4/MINGANXING/inputChart.vue';
import { useDataStore } from './stores';
import { storeToRefs } from 'pinia';
// 注册需要使用的 ECharts 组件
use([CanvasRenderer, LegendComponent, TooltipComponent])

const dataStore = useDataStore();
const myData = storeToRefs(dataStore);
const review =myData.review;
const spinning = myData.spinning;
const current = myData.current;
const analysisData = myData.analysisData;
const series = myData.series;
const names = myData.names;
const dataSource1 = myData.dataSource1;
const columns = myData.columns;
const columns1 = myData.columns1;
const columns2= myData.columns2;
const dataSource = myData.dataSource;
const emit = defineEmits({
  submit: (data) => {
    return true;
  }
});

const submit = () => {
  spinning.value = true;
  const data = {
    review: myData.review,
    analysisData: myData.analysisData,
    series: myData.series,
    names: myData.names,
    dataSource1: myData.dataSource1,
    columns: myData.columns,
    columns1: myData.columns1,
    columns2: myData.columns2,
    dataSource: myData.dataSource
  };
  emit('submit', data);
  spinning.value = false;
};

const steps = [
  { title: '实验目的' },
  { title: '实验原理' },
  { title: '实验步骤' },
  { title: '实验过程' },
  { title: '实验小结与心得' }
];

const next = () => {
  if (current.value < steps.length - 1) {
    current.value++;
  }
};

const prev = () => {
  if (current.value > 0) {
    current.value--;
  }
};

const updateAnalysis = () => {
      // 在这里执行敏感性分析并更新 analysisData 
      //console.log(typeof(JSON.parse(JSON.stringify(dataSource1))))
      //console.log("ddata",dataSource1)
      analysisData.value = doAnalysis(JSON.parse(JSON.stringify(dataSource1.value)))
      //console.log("san",analysisData)
      series.value = getSeries(JSON.parse(JSON.stringify(dataSource1.value)))
      //console.log("ser",series)
      names.value = getName(series.value)

      // 绘制竖向柱状图
      drawSensitivityChart()

      // 绘制最后的折线图
      drawLineChart()
}
const drawSensitivityChart = () => {
  const chart = echarts.init(document.getElementById("sensitivity-chart"))
      //取第一组数据

      const option = {
        title: {
          text: '敏感性分析结果',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          data: ['亏损', '收益'],
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: {
          type: 'value',
        },
        yAxis: {
          type: 'category',
          inverse: true,
          data: analysisData.value.map((d) => d.name),
        },
        series: [
          {
            name: '收益',
            type: 'bar',
            stack: '2%',
            label: {
              show: true,
              position: 'insideRight',
            },
            data: analysisData.value.map((d) => d.change_2),
            // data:[105,55,55,22]
          },
          {
            name: '亏损',
            type: 'bar',
            stack: '2%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => -d.cost),
            data: analysisData.value.map((d) => -d.change_minus2),
          },
          {
            name: '收益',
            type: 'bar',
            stack: '5%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => d.benefit),
            data: analysisData.value.map((d) => d.change_5),
          },
          {
            name: '亏损',
            type: 'bar',
            stack: '5%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => -d.cost),
            data: analysisData.value.map((d) => -d.change_minus5),
          },
          {
            name: '收益',
            type: 'bar',
            stack: '10%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => d.benefit),
            data: analysisData.value.map((d) => d.change_10),
          },
          {
            name: '亏损',
            type: 'bar',
            stack: '10%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => -d.cost),
            data: analysisData.value.map((d) => -d.change_minus10),
          },
          {
            name: '收益',
            type: 'bar',
            stack: '15%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => d.benefit),
            data: analysisData.value.map((d) => d.change_15),
          },
          {
            name: '亏损',
            type: 'bar',
            stack: '15%',
            label: {
              show: true,
              position: 'insideRight',
            },
            //data: analysisData.map((d) => -d.cost),
            data: analysisData.value.map((d) => -d.change_minus15),
          },
        ],
      }
      chart.clear()
      chart.setOption(option)
}
const drawLineChart = () => {
  const chart = echarts.init(document.getElementById("line-chart"))
      console.log("ser", series.value)

      const option = {
        title: {
          text: '敏感性分析结果',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          data: names.value,
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: ['-15%', '-10%', '-5%', '-2%', 'Base', '2%', '5%', '10%', '15%']
        },
        yAxis: {
          type: 'value'
        },
        series: series.value
      }
      chart.clear()
      chart.setOption(option)
}
const handleDataSource1Update = (newData) =>{
  dataSource1.value = newData;
}

function doAnalysis(dataSource1) {
  class Info {
    name: string
    change_2: number
    change_5: number
    change_10: number
    change_15: number
    changeBasicPlan: number
    change_minus2: number
    change_minus5: number
    change_minus10: number
    change_minus15: number
  }
  var datas = Info[dataSource1.length];
  var datas = []

  for (var i = 0; i < dataSource1.length; i++) {
    var newData = new Info()

    newData.name = dataSource1[i].uncertainty
    newData.change_2 = dataSource1[i].change_2
    newData.change_5 = dataSource1[i].change_5
    newData.change_10 = dataSource1[i].change_10
    newData.change_15 = dataSource1[i].change_15
    newData.changeBasicPlan = dataSource1[i].changeBasicPlan
    newData.change_minus2 = dataSource1[i].change_minus2
    newData.change_minus5 = dataSource1[i].change_minus5
    newData.change_minus10 = dataSource1[i].change_minus10
    newData.change_minus15 = dataSource1[i].change_minus15
    datas.push(newData)
  };
  return datas
}

function getSeries(dataSource1) {
  class Serie {
    name: string
    data: number[]
    type: string
    stack: string
  }

  console.log(dataSource1)
  var datas = [];

  for (var i = 0; i < dataSource1.length; i++) {
    var newSerie = new Serie()
    newSerie.name = dataSource1[i].uncertainty
    newSerie.stack = dataSource1[i].uncertainty
    newSerie.data = []
    newSerie.type = 'line'
    newSerie.data.push(dataSource1[i].change_minus15)
    newSerie.data.push(dataSource1[i].change_minus10)
    newSerie.data.push(dataSource1[i].change_minus5)
    newSerie.data.push(dataSource1[i].change_minus2)
    newSerie.data.push(100)
    newSerie.data.push(dataSource1[i].change_2)
    newSerie.data.push(dataSource1[i].change_5)
    newSerie.data.push(dataSource1[i].change_10)
    newSerie.data.push(dataSource1[i].change_15)

    datas.push(newSerie)
  }
  return datas
}

function getName(dataSource1) {
  var datas = [];
  for (var i = 0; i < dataSource1.length; i++) {
    datas.push(dataSource1[i].name)
  }
  return datas
}

function clearData(){
  review.value = '';
}
</script>

<style scoped>
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
  padding-inline: 14px;
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