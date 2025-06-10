<template>
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-show="current == 0" ref="content1">
        <h2>一、实验目的</h2>
        <p>
          这个实验的目的是通过实践理解和应用两种重要的金融分析工具 - 净现值（NPV）和内部回报率（IRR）。
          这两种工具被广泛应用于金融领域，是对投资项目进行评估的关键指标。
          通过深入学习并实践这两种工具的使用，学习者将能够掌握评估投资项目的基本技能，
          学会如何从数据的角度来理解和判断一个投资项目的价值。
        </p>
        <p>
          在这个实验中，我们不仅希望学习者能理解和应用NPV和IRR这两种工具，更希望他们能够学会如何将理论知识应用到实际的金融分析中。
          这对于学习者来说，不仅能够提升他们的金融分析能力，同时也能提高他们的问题解决能力和决策能力。
        </p>
      </div>
      <div class="exp-step-card" v-show="current == 1" ref="content2">
        <h2>二、实验原理</h2>
        <p>
          1. <b>净现值</b>（Net Present Value, NPV）是一种投资决策工具，它的主要作用是评估和比较投资项目的经济效益。
          它是通过将预期的现金流按照某个折现率折现到现在，然后减去初始投资来计算的。
          NPV是一个十分重要的指标，它能够量化未来收益的当前价值，帮助我们更好地评估和选择投资项目。
        </p>
        <p>
          公式表示为：<vue-latex expression="NPV=\sum_{t=1}^{n}{\frac{C_t}{(1+r)^t}-C_0}"></vue-latex>，其中<vue-latex
            expression="C_t"
          />代表第 <vue-latex expression="t" /> 期的现金流，<vue-latex expression="r" /> 是折现率，<vue-latex
            expression="C_0"
          />
          是初始投资。
        </p>
        <p>
          2. <b>内部回报率</b>（Internal Rate of Return, IRR）也是一种重要的投资决策工具，
          它是使投资项目的NPV为零的折现率。换句话说，IRR 是投资者实际得到的年回报率， 如果一个项目的 IRR
          高于投资者的期望回报率，那么投资者通常会选择投资，反之则会放弃。
        </p>
        <p>IRR的计算公式是：<vue-latex expression="IRR=\arg_{r}{\sum_{t=1}^{n}{\frac{C_t}{(1+r)^t}-C_0}=0}" /></p>
      </div>
      <div class="exp-step-card" v-show="current == 2" ref="content3">
        <h2>三、实验内容</h2>
        <p><strong>一：输入实验参数</strong></p>
        <p>
          首先，您需要在"投资期间"、"折现率"和"现金流"输入框中分别输入投资的持续期间、您对未来现金流的预估折现率以及每个期间的现金流。
          请您按照从早期到晚期的顺序输入现金流，以确保数据的正确性。
        </p>
        <p><strong>二：核实输入参数的准确性</strong></p>
        <p>
          在完成所有必要的输入后，我们的系统将自动对输入的数据进行核实。
          如果您输入的数据无法被我们的系统识别，或者有某个输入框留空，系统将提醒您修改相关数据。
          请您仔细检查这些提示，并根据系统的反馈对输入数据进行相应的调整。
        </p>
        <p><strong>三：计算投资的内部收益率和净现值</strong></p>
        <p>
          现在，您可以点击"计算"按钮，我们的系统将基于您的输入计算投资的内部收益率和净现值。
          在这个过程中，我们使用了科学的计算方法，保证了结果的准确性和公正性。
        </p>
        <p><strong>四：检查计算结果</strong></p>
        <p>
          计算完成后，我们的系统将对结果进行检查。如果计算过程中出现了错误，例如内部收益率的计算无法收敛，
          系统将提醒您并建议您使用其他投资评估方法，例如修正内部收益率（MIRR）或净现值（NPV）。
        </p>
        <p><strong>五：创建并查看结果图表</strong></p>
        <p>
          您可以点击"创建图表"按钮，我们的系统将为您创建一个图表，直观地展示出投资的内部收益率和净现值随折现率变化的趋势。
          这可以帮助您更直观地理解投资的收益情况，从而做出更明智的投资决策。
        </p>
        <p><strong>六：理解并分析实验结果</strong></p>
        <p>
          在查看了计算结果和图表之后，您需要对这些结果进行分析。内部收益率和净现值是投资决策的重要依据，
          它们反映了投资的盈利能力和价值。您需要根据这些结果，分析投资的盈利情况，预估未来的现金流，并据此调整您的投资策略。
        </p>
      </div>

      <div class="exp-step-card" v-show="current == 3" ref="content4">
        <a-steps v-model:current="subCurrent" size="small">
          <a-step v-for="item in substeps" :key="item.title" :title="item.title" :disabled="item.disabled" />
        </a-steps>
        <!-- 步骤简介 -->
        <div style="margin: 30px 0; color: #888;">
        </div>
        <div class="step-card" v-show="subCurrent == 0">
        <h3>步骤一：输入计算期数和现金流</h3>
    <p>在输入框中填写计算期数，并在下方表格中输入每一期的现金流数据。</p>
        <h3>步骤二：设定折现率</h3>
    <p>设定相应的折现率，用于计算未来现金流现值的比率。</p>
          <div class="input-section">
            <!-- 选择计算周期 -->
        <div class="input-group">
          <label for="periodType">计算周期：</label>
          <a-select 
            id="periodType" 
            v-model:value="periodType" 
            style="width: 120px; margin-right: 20px"
            @change="handlePeriodTypeChange"
          >
            <a-select-option value="year">年</a-select-option>
            <a-select-option value="quarter">季度</a-select-option>
            <a-select-option value="month">月</a-select-option>

          </a-select>
        </div>

            <!-- 输入期数 -->
        <div class="input-group">
          <label for="inputNumber">请输入计算期数：</label>
          <a-input-number 
            id="inputNumber" 
            v-model:value="periodvalue" 
            :min="1" 
            :max="100" 
          />
          <a-button type="primary" @click="refreshData()" style="margin-left: 10px">确认</a-button>
        </div>

            <!-- 输入折现率 -->
            <div class="input-group">
              <label for="discountRate">设定折现率为：</label>
              <a-input-number
                id="discountRate"
                v-model="discount_rate1"
                :min="0"
                :max="100"
                @change="updateDiscountRate"
                :formatter="(value) => `${value}%`"
                :parser="(value) => parseFloat(value.replace('%', ''))"
              />
               <a-button type="primary" style="margin-left: 10px" @click="confirmDiscountRate">确认</a-button>
            </div>
          </div>
          
          <div class="table-container">
            <a-table
              :dataSource="dataSource"
              :columns="columns"
              :pagination="false"
              bordered
              class="a-table"
            >
              <template #bodyCell="{ column, record }">
                <!-- 可编辑的净现金流字段 -->
                <template v-if="column.dataIndex === 'cashflow'">
                  <a-input-number
                    v-model:value="record.cashflow"
                    :min="-999999"
                    :max="999999"
                    :controls="false"
                    style="width: 100%"
                    @change="onCashflowChange(record)"
                  />
                </template>

                <!-- 折现后现金流只读 -->
                <template v-else-if="column.dataIndex === 'cashflow_discounted'">
                  <span>{{ record.cashflow_discounted }}</span>
                </template>

                <!-- 其他字段 -->
                <template v-else>
                  <span>{{ record[column.dataIndex] }}</span>
                </template>
              </template>
            </a-table>
          </div>
        </div>
        
        <!-- 步骤二 -->
        <div class="step-card" v-show="subCurrent == 1">
        <h3>步骤三：计算折现后每年现金流量</h3>
    <p>系统将根据设定的折现率，自动计算各期现金流的现值并以图表形式展示，便于理解未来收益的当前价值。</p>
          <a-button type="primary" @click="drawCharts('chart1Ref')" class="calculate-result"
            >计算折现后每年现金流量并绘制相应图表</a-button
          >
          <div v-show="ifshow_npv">
            <div class="chart1Ref-container">
              <div id="chart1Ref" style="width: 600px; height: 400px"></div>
            </div>
          </div>
        </div>

        <!-- 步骤三 -->
        <div class="step-card" v-show="subCurrent == 2">
        <h3>步骤四：计算 NPV 和 IRR</h3>
    <p>点击下方"计算NPV和IRR"按钮后，系统将基于前面输入的现金流数据计算项目在当前折现率下的净现值（NPV）和内部收益率（IRR），以量化项目的财务回报。</p>
          <a-button
            type="primary"
            @click="
              calculateNPV();
              substeps[3].disabled = false;
            "
            class="calculate-result"
            >计算NPV和IRR</a-button
          >
          <div class="result-item">
            <span class="result-label">当折现率为</span>
            <label>{{ discount_rate1 }}</label>
            <span class="result-label">%时的净现值（NPV）: </span>
            <span class="result-value"> {{ npv.toFixed(2) }} </span>
            <span class="result-label"> 万元</span>
          </div>
        
<!-- 季度IRR -->
<div class="result-item" v-if="periodType === 'quarter'">
  <span class="result-label">季度内部收益率（Quarterly IRR）：</span>
  <span class="result-value">{{ irr_quarter.toFixed(2) }}%</span>
</div>

<!-- 月度 IRR -->
<div class="result-item" v-if="periodType === 'month'">
  <span class="result-label">月度内部收益率（Monthly IRR）：</span>
  <span class="result-value">{{ irr_month.toFixed(2) }}%</span>
</div>
<!-- IRR -->
<div class="result-item">
  <span class="result-label">内部收益率（IRR）：</span>
  <span class="result-value">{{ irr_annual.toFixed(2) }}%</span>
</div>

        </div>
        <!-- 步骤四 -->
        <div class="step-card" v-show="subCurrent == 3">
          <a-button type="primary" @click="drawCharts('chart2Ref')" class="calculate-result">
            画出 NPV-折现率 图
          </a-button>
          <h3>步骤五：画出 NPV-折现率 图</h3>
    <p>点击下方"画出NPV-折现率图"按钮后，系统将绘制净现值与折现率之间的关系图，展示NPV如何随折现率变化。该图用于观察项目在不同折现率下的经济性，有助于判断项目的敏感性和最优收益区间。</p>
          <div v-show="ifshow_irr">
            <div class="chart2Ref-container">
              <div id="chart2Ref" style="width: 600px; height: 400px"></div>
            </div>
          </div>
        </div>
      </div>

      <div class="exp-step-card" v-show="current == 4" ref="content5">
        <h2 style="margin-top: 20px">实验心得</h2>
        <a-form :model="summary" name="nest-massages">
          <a-form-item label="请输入实验总结" name="summary">
            <a-textarea v-model:value="summary" />
          </a-form-item>
        </a-form>
      </div>
    </div>
    <div class="steps-action">
      <a-button v-show="current < steps.length - 1" type="primary" style="float: right" @click="next"
        ><step-forward-outlined />下一步</a-button
      >
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
        <EyeOutlined />
        预览报告
      </a-button>
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right; margin-right: 7px" @click="submit"> 提交 </a-button>

      <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
      <div v-if="current > 2" style="display: flex; justify-content: flex-end; margin-top: 20px">
        <a-button danger @click="clearData">清空数据</a-button>
      </div>
      <div>
        <a-modal
          title="提示"
          v-model:visible="clearMsg"
          @Ok="clearConfirm"
          @Cancel="clearCancel"
          style="width: 300px"
          okText="确认"
          cancelText="取消"
        >
          <p>确认清空实验数据？</p>
        </a-modal>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, nextTick, watch } from 'vue';
import * as echarts from 'echarts';
import { useStore } from './stores';
import type { TableColumnType } from 'ant-design-vue';
import html2canvas from 'html2canvas';
import { message } from 'ant-design-vue';
import { storeToRefs } from 'pinia';
import { useExperimentStore } from '@/store/experiment';
import { useAccountStore } from '@/store';
import { VueLatex } from 'vatex';
import { toFixed } from 'ant-design-vue/lib/input-number/src/utils/MiniDecimal';
import { useRouter } from 'vue-router';
import { toRaw } from 'vue';

const current = ref(0);
const subCurrent = ref(0);
const store = useStore();
const experimentStore = useExperimentStore();
const chartContainer1Ref = ref(null);
const chartContainer2Ref = ref(null);
const tableRef = ref(null);
const EXPERIMENT_ID = '71';
const imgUrl1 = ref('');
const imgUrl2 = ref('');
const { account } = useAccountStore();
const { uploadImg } = useExperimentStore();
const discountRateApplied = ref(false); 
const irr_quarter = ref(0);
const irr_annual = ref(0);

interface DataRecord {
  key: string;
  period: string;
  cashflow: number;
  cashflow_discounted: number;
  attentioninfo?: string;
}
const { spinning, ifshow_irr, ifshow_npv, summary, discount_rate1, periodvalue, dataSource, editableData, npv, irr } =
  storeToRefs(useStore());
const clearMsg = ref(false);
let chart1Ref, chart2Ref;
const periodType = ref<'year' | 'quarter' | 'month'>('year');

// 添加图片缓存
const chartImageCache = ref({
  chart1: '',
  chart2: '',
  table: ''
});

const generateCashflowTableHtml = (dataSource: DataRecord[]): string => {
  let tableHtml = '<div ref="tableRef"><table border="1" style="border-collapse: collapse; width: 100%;">';
  tableHtml += '<thead><tr>';
  tableHtml += '<th style="padding: 8px; border: 1px solid black; text-align: left;">时间点</th>';
  tableHtml += '<th style="padding: 8px; border: 1px solid black; text-align: left;">净现金流/万元</th>';
  tableHtml += '<th style="padding: 8px; border: 1px solid black; text-align: left;">折现后现金流/万元</th>';
  tableHtml += '</tr></thead>';
  tableHtml += '<tbody>';

  dataSource.forEach(item => {
    tableHtml += '<tr>';
    tableHtml += `<td style="padding: 8px; border: 1px solid black;">${item.period}</td>`;
    tableHtml += `<td style="padding: 8px; border: 1px solid black;">${Number(item.cashflow).toFixed(2)}</td>`;
    tableHtml += `<td style="padding: 8px; border: 1px solid black;">${Number(item.cashflow_discounted).toFixed(2)}</td>`;
    tableHtml += '</tr>';
  });

  tableHtml += '</tbody></table></div>';
  return tableHtml;
};

// 现金流变化处理函数
const onCashflowChange = (record: any) => {
  const index = dataSource.value.findIndex((item: any) => item.key === record.key);
  if (index !== -1) {
    const rate = periodType.value === 'quarter'
      ? Math.pow(1 + discount_rate1.value / 100, 1 / 4) - 1
      : discount_rate1.value / 100;

    if (index === 0) {
      // 初始投资不折现
      dataSource.value[index].cashflow_discounted = record.cashflow;
    } else {
      const discounted = Number(record.cashflow) / Math.pow(1 + rate, index);
      dataSource.value[index].cashflow_discounted = parseFloat(discounted.toFixed(2));
    }
  }
};

// NPV计算函数
const calculateNPV = () => {
  let npvSum = 0;

 const annualRate = discount_rate1.value / 100;
 
  // 根据周期类型设定实际折现率
  const rate = periodType.value === 'month'
    ? Math.pow(1 + annualRate, 1 / 12) - 1
    : periodType.value === 'quarter'
      ? Math.pow(1 + annualRate, 1 / 4) - 1
      : annualRate;
  
  dataSource.value.forEach((item, index) => {
    const cf = item.cashflow;
    if (index === 0) {
      // 初始投资不折现
      npvSum += cf;
      item.cashflow_discounted = cf;
    } else {
      const discounted = cf / Math.pow(1 + rate, index);
      npvSum += discounted;
      item.cashflow_discounted = parseFloat(discounted.toFixed(2));
    }
  });

  npv.value = parseFloat(npvSum.toFixed(2));
  calculateIRR(); // 重新计算 IRR
};

// IRR计算函数
const irr_month = ref(0);

const calculateIRR = () => {
  const cashFlows = dataSource.value.map(item => item.cashflow);
  const initialGuess = 0.1;
  const rawIRR = calculateIRRNewton(cashFlows, initialGuess); // raw period IRR

  if (periodType.value === 'quarter') {
    irr_quarter.value = rawIRR * 100;
    irr_annual.value = ((1 + rawIRR) ** 4 - 1) * 100;
    irr.value = irr_quarter.value;
  } else if (periodType.value === 'month') {
    irr_month.value = rawIRR * 100;
    irr_annual.value = ((1 + rawIRR) ** 12 - 1) * 100;
    irr.value = irr_month.value;
  } else {
    irr_annual.value = rawIRR * 100;
    irr.value = irr_annual.value;
  }
};


const calculateIRRNewton = (cashFlows: number[], initialGuess: number = 0.1): number => {
  let x = initialGuess;
  const epsilon = 0.0000001;
  const maxIterations = 100;

  for (let i = 0; i < maxIterations; i++) {
    let npv = 0;
    let derivative = 0;

    for (let t = 0; t < cashFlows.length; t++) {
      const cf = cashFlows[t];
      npv += cf / Math.pow(1 + x, t);
      derivative -= (t * cf) / Math.pow(1 + x, t + 1);
    }

    const newX = x - npv / derivative;

    if (Math.abs(newX - x) < epsilon) {
      return newX;
    }

    x = newX;
  }

  // 如果未收敛，返回当前估计值
  return x;
};



// 季度IRR计算函数
const calculateQuarterlyIRR = (cashFlows: number[], initialGuess: number): number => {
  let x = initialGuess;
  let prevX = 0;
  const epsilon = 0.0001;
  const maxIterations = 100;
  
  for (let i = 0; i < maxIterations; i++) {
    let npv = 0;
    let derivative = 0;
    
    cashFlows.forEach((cf, index) => {
      const t = (index + 1) / 4;
      const denominator = Math.pow(1 + x, t);
      npv += cf / denominator;
      derivative -= t * cf / Math.pow(1 + x, t + 1);
    });
    
    if (Math.abs(npv) < epsilon) break;
    
    prevX = x;
    x = x - npv / derivative;
    
    if (Math.abs(x - prevX) < epsilon) break;
  }
  
  return x;
};

// 年IRR计算函数
const calculateAnnualIRR = (cashFlows: number[], initialGuess: number): number => {
  let x = initialGuess;
  let prevX = 0;
  const epsilon = 0.0001;
  const maxIterations = 100;
  
  for (let i = 0; i < maxIterations; i++) {
    let npv = 0;
    let derivative = 0;
    
    cashFlows.forEach((cf, index) => {
      const t = index + 1;
      npv += cf / Math.pow(1 + x, t);
      derivative -= t * cf / Math.pow(1 + x, t + 1);
    });
    
    if (Math.abs(npv) < epsilon) break;
    
    prevX = x;
    x = x - npv / derivative;
    
    if (Math.abs(x - prevX) < epsilon) break;
  }
  
  return x;
};

// 处理周期类型变化
const handlePeriodTypeChange = () => {
  refreshData();
  npv.value = 0;
  irr.value = 0;

  if (periodType.value === 'quarter') {
    if (periodvalue.value > 200) periodvalue.value = 200;
  } else if (periodType.value === 'month') {
    if (periodvalue.value > 600) periodvalue.value = 600;
  } else {
    if (periodvalue.value > 50) periodvalue.value = 50;
  }
  irr_month.value = 0;

};


// 修改后的折现率更新函数
const updateDiscountRate = (value: number | null) => {
  if (value === null) return;

  discount_rate1.value = value;
  npv.value = 0;
  irr.value = 0;
  substeps.value[3].disabled = false;

  const annualRate = Number(discount_rate1.value) / 100;

  dataSource.value.forEach((record: any, index: number) => {
    if (index === 0) {
      // 第0期为初始投资，不进行折现
      record.cashflow_discounted = record.cashflow;
      return;
    }

    let effectiveRate;
    let t = index;

    if (periodType.value === 'quarter') {
      // 每季度：折现率转换为季度利率
      effectiveRate = Math.pow(1 + annualRate, 1 / 4) - 1;
    } else if (periodType.value === 'month') {
      // 每月：折现率转换为月利率
      effectiveRate = Math.pow(1 + annualRate, 1 / 12) - 1;
    } else {
      // 每年
      effectiveRate = annualRate;
    }

    const discounted = Number(record.cashflow) / Math.pow(1 + effectiveRate, t);
    record.cashflow_discounted = parseFloat(discounted.toFixed(2));
  });
};

// 添加确认折现率函数
const confirmDiscountRate = () => {
  updateDiscountRate(discount_rate1.value);
  message.success('折现率已更新');
};

const clearData = () => {
  clearMsg.value = true;
};

const clearConfirm = () => {
  store.clear();
  message.warning('已清空数据');
  clearMsg.value = false;
  current.value = 0;
  ifshow_irr.value = false;
  ifshow_npv.value = false;
};

const clearCancel = () => {
  clearMsg.value = false;
};

const columns: TableColumnType[] = [
  {
    title: '时间点',
    dataIndex: 'period',
    width: '20%',
    align: 'center',
  },
  {
    title: '净现金流/万元',
    dataIndex: 'cashflow',
    width: '30%',
    align: 'center',
  },
  {
    title: '折现后现金流/万元',
    dataIndex: 'cashflow_discounted',
    width: '30%',
    align: 'center',
  },

  {
    title: '注意事项', // Attention info
    dataIndex: 'attentioninfo',
    customCell: (_, index) => {
      if (index === 0) {
        return { rowSpan: dataSource.value.length  };
      }
      if (index > 0) {
        return { rowSpan: 0 };
      }
    },
  },
];

onMounted(() => {
  chart1Ref = echarts.init(chartContainer1Ref.value);
  chart2Ref = echarts.init(chartContainer2Ref.value);
  refreshData();

});


const accountStore = useAccountStore();
const router = useRouter();

const experimentDate = new Date();
const myStore = useStore();

async function downLoadFile() {
  spinning.value = true;
  try {
    // 确保图表已显示
    ifshow_irr.value = true;
    ifshow_npv.value = true;
    await nextTick();

    // 生成或获取表格图片
    if (!chartImageCache.value.table) {
      const canvasTable = drawTableToCanvas(toRaw(dataSource.value));
      if (!canvasTable) {
        throw new Error('表格图片生成失败');
      }
      chartImageCache.value.table = canvasTable.toDataURL('image/png', 1.0);
    }

    // 确保图表已经生成
    await Promise.all([
      drawCharts('chart1Ref'),
      drawCharts('chart2Ref')
    ]);

    const finalData = {
      experiment_id: "71",
      reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
      reporter_name: accountStore.account.name,
      course_name: accountStore.course.courseName,
      course_id: accountStore.course.courseId,
      submit_time: experimentDate.getTime(),
      summary: myStore.summary,
      periodvalue: periodvalue.value,
      discount_rate1: discount_rate1.value,
      NPV: npv.value.toFixed(2),
      IRR_annual: irr_annual.value.toFixed(2),
      IRR_quarter: irr_quarter.value.toFixed(2),
      IRR_month: irr_month.value.toFixed(2),
      cashflowTable_html: chartImageCache.value.table,
      charts1: imgUrl1.value,
      charts2: imgUrl2.value
    };

    // 在跳转前保存当前页面状态
    try {
      sessionStorage.setItem('jinxianzhi_page_state', JSON.stringify({
        current: current.value,
        hasSubmitted: true,
        dataSource: toRaw(dataSource.value),
        discountRate: discount_rate1.value,
        summary: myStore.summary
      }));
    } catch (e) {
      console.error('保存页面状态失败', e);
    }

    // 添加日志以检查图片数据
    console.log('Preview data with images:', {
      hasTable: !!finalData.cashflowTable_html,
      hasChart1: !!finalData.charts1,
      hasChart2: !!finalData.charts2
    });

    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(finalData))
      }
    });
  } catch (error) {
    console.error('预览报告生成失败:', error);
    message.error('预览报告失败，请稍后重试');
  } finally {
    spinning.value = false;
  }
}

const drawCharts = (chartType: 'chart1Ref' | 'chart2Ref'): Promise<void> => {
  return new Promise(async (resolve, reject) => {
    // 如果已经有缓存的图片数据，直接使用
    if (chartType === 'chart1Ref' && chartImageCache.value.chart1) {
      imgUrl1.value = chartImageCache.value.chart1;
      resolve();
      return;
    }
    if (chartType === 'chart2Ref' && chartImageCache.value.chart2) {
      imgUrl2.value = chartImageCache.value.chart2;
      resolve();
      return;
    }

    if (!store.dataSource.length) {
      console.warn('No data available to draw charts.');
      resolve();
      return;
    }

    let chartElementId: string;
    let options: any;
    let imgUrlRef: typeof imgUrl1 | typeof imgUrl2;
    let ifShowRef: typeof ifshow_npv | typeof ifshow_irr;

    if (chartType === 'chart1Ref') {
      ifshow_npv.value = true;
      chartElementId = 'chart1Ref';
      imgUrlRef = imgUrl1;
      ifShowRef = ifshow_npv;
      options = {
        title: { text: '折现后年现金流量' },
        tooltip: { trigger: 'axis', axisPointer: { type: 'cross' } },
        xAxis: {
          data: store.dataSource.map((_, index) => `第${index}期`),
          name: '时间点',
        },
        yAxis: {
          type: 'value',
          name: '折现后年现金流量/万元',
        },
        series: [
          {
            data: store.dataSource.map((item) => item.cashflow_discounted),
            type: 'line',
            smooth: true,
          },
        ],
      };
    } else if (chartType === 'chart2Ref') {
      ifshow_irr.value = true;
      chartElementId = 'chart2Ref';
      imgUrlRef = imgUrl2;
      ifShowRef = ifshow_irr;
      const discount_rate = discount_rate1.value;
      let dataPoints = [];
      const maxDiscountRate = 1;
      let irr_rate = 0;
      let irr_found = false;

      for (let rate = 0; rate <= maxDiscountRate; rate += 0.01) {
        let npvVal = 0;
        dataSource.value.forEach((item, index) => {
          const discountedCashFlow = item.cashflow / Math.pow(1 + rate, index);
          npvVal += discountedCashFlow;
        });
        dataPoints.push([rate * 100, npvVal]);
        if (Math.abs(npvVal) < 0.01 && !irr_found) {
          irr_rate = rate * 100;
          irr_found = true;
        }
      }
      let IRRx = 0;
      let IRRy = 0;
      for (let i = 0; i < dataPoints.length - 1; i++) {
        if (dataPoints[i][1] * dataPoints[i + 1][1] < 0) {
          IRRx =
            dataPoints[i][0] +
            ((0 - dataPoints[i][1]) / (dataPoints[i + 1][1] - dataPoints[i][1])) *
            (dataPoints[i + 1][0] - dataPoints[i][0]);
          break;
        }
      }
      const reducedDataPoints = dataPoints.filter((_, index) => index % 5 === 0);
      options = {
        title: { text: 'NPV-折现率' },
        tooltip: { trigger: 'axis', axisPointer: { type: 'cross' } },
        xAxis: {
          type: 'value', name: '折现率 (%)', boundaryGap: [0, 0.01], min: 0, max: 100, axisLabel: { formatter: '{value} %' },
        },
        yAxis: {
          type: 'value', name: '净现值/万元', axisLabel: { formatter: '{value} 万元' },
        },
        series: [
          {
            data: reducedDataPoints, type: 'line', smooth: true,
            markPoint: {
              data: [
                {
                  coord: [IRRx, IRRy], name: 'IRR',
                  label: {
                    show: true,
                    formatter: () => {
                      if (periodType.value === 'quarter') return 'IRR: ' + irr_quarter.value.toFixed(2) + '%';
                      if (periodType.value === 'month') return 'IRR: ' + irr_month.value.toFixed(2) + '%';
                      return 'IRR: ' + irr_annual.value.toFixed(2) + '%';
                    },
                  },
                  itemStyle: { color: 'red' },
                },
              ],
            },
          },
        ],
      };
    } else {
      return reject(new Error('Invalid chart type'));
    }

    ifShowRef.value = true;
    await nextTick();

    const chartElement = document.getElementById(chartElementId);
    if (!chartElement) {
      console.error(`${chartElementId} element not found!`);
      reject(new Error(`${chartElementId} element not found`));
      return;
    }

    let chartInstance = echarts.getInstanceByDom(chartElement);
    if (!chartInstance) {
      chartInstance = echarts.init(chartElement);
    }
    
    chartInstance.setOption(options, true);

    chartInstance.on('finished', () => {
      (async () => {
        try {
          await nextTick();
          const canvas = await html2canvas(chartElement, {
            backgroundColor: '#ffffff',
            scale: 2,
            useCORS: true,
            logging: false,
            width: chartElement.offsetWidth,
            height: chartElement.offsetHeight,
            onclone: (clonedDoc) => {
              const clonedElement = clonedDoc.querySelector(`#${chartElementId}`) as HTMLElement;
              if (clonedElement) {
                clonedElement.style.background = '#ffffff';
              }
            }
          });
          const imageData = canvas.toDataURL('image/png', 1.0);
          
          // 缓存图片数据
          if (chartType === 'chart1Ref') {
            chartImageCache.value.chart1 = imageData;
            imgUrl1.value = imageData;
          } else {
            chartImageCache.value.chart2 = imageData;
            imgUrl2.value = imageData;
          }
          
          console.log(`${chartElementId} image generated and stored.`);
          resolve();
        } catch (e) {
          console.error(`Error generating ${chartElementId} image:`, e);
          reject(e);
        }
      })();
    });
  });
};

const steps = ref([
  { title: '实验目的', content: '0-content' },
  { title: '实验原理', content: '1-content' },
  { title: '实验内容', content: '2-content' },
  { title: '实验步骤', content: '3-content' },
  { title: '实验心得', content: '4-content' },
]);

const substeps = ref([
  { title: '输入计算期数和现金流', disabled: false },
  { title: '计算折现后年现金流量', disabled: true },
  { title: '计算 NPV 和 IRR 指标', disabled: true },
  { title: '画出 NPV-折现率 图', disabled: true },
]);

const refreshData = () => {
  const period = periodvalue.value;

  dataSource.value = Array.from({ length: period }, (_, i) => ({
    key: i.toString(), // 从 0 开始
    period: i.toString(), // 显示时间点从 0 开始
    cashflow: i === 0 ? 0 : 0,
    cashflow_discounted: i === 0 ? 0 : 0, // 第0期不参与折现，直接保留原始值
    attentioninfo: i === 0 ? '注意：第0期为初始投资，不参与折现计算。' : '',
  }));

  substeps.value[1].disabled = false;
  substeps.value[2].disabled = false;
  npv.value = 0;
  irr.value = 0;
};

const emit = defineEmits({
  submit: (data) => {
    return true;
  },
});

const drawTableToCanvas = (data: any[]) => {
  // 创建canvas元素
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');
  if (!ctx) return null;

  // 设置canvas大小和样式 - 减小尺寸
  const cellWidth = 120; // 从150减小到120
  const cellHeight = 20; // 从40减小到30
  const padding = 8;  // 从10减小到8
  const headerHeight = 25; // 从40减小到25，与单元格高度更接近
  
  // 计算表格总宽度和高度
  const tableWidth = cellWidth * 3 + padding * 2;
  const tableHeight = headerHeight + (data.length * cellHeight) + padding;
  
  canvas.width = tableWidth;
  canvas.height = tableHeight;
  
  // 设置白色背景
  ctx.fillStyle = '#ffffff';
  ctx.fillRect(0, 0, tableWidth, tableHeight);
  
  // 设置表格样式
  ctx.strokeStyle = '#000000';
  ctx.lineWidth = 1;
  
  // 绘制表头
  ctx.fillStyle = '#f0f2f5';
  ctx.fillRect(0, 0, tableWidth, headerHeight);
  
  // 设置文字样式 - 减小字体大小
  ctx.fillStyle = '#000000';
  ctx.font = 'bold 10px Arial'; // 从14px减小到12px
  ctx.textAlign = 'center';
  ctx.textBaseline = 'middle';
  
  // 绘制表头文字
  const headers = ['时间点', '净现金流/万元', '折现后现金流/万元'];
  headers.forEach((header, index) => {
    ctx.fillText(header, cellWidth * index + cellWidth / 2, headerHeight / 2);
  });
  
  // 绘制数据行
  ctx.font = '12px Arial'; // 从14px减小到12px
  data.forEach((row, rowIndex) => {
    const y = headerHeight + rowIndex * cellHeight;
    
    // 绘制单元格边框
    for (let i = 0; i < 3; i++) {
      ctx.strokeRect(i * cellWidth, y, cellWidth, cellHeight);
    }
    
    // 填充数据
    ctx.fillText(row.period.toString(), cellWidth / 2, y + cellHeight / 2);
    ctx.fillText(Number(row.cashflow).toFixed(2), cellWidth * 1.5, y + cellHeight / 2);
    ctx.fillText(Number(row.cashflow_discounted).toFixed(2), cellWidth * 2.5, y + cellHeight / 2);
  });
  
  // 绘制表格外边框
  ctx.strokeRect(0, 0, tableWidth, tableHeight);
  
  return canvas;
};

const submit = async () => {
  spinning.value = true;
  try {
    // 确保图表已显示
    ifshow_irr.value = true;
    ifshow_npv.value = true;
    await nextTick();

    // 生成或获取表格图片
    if (!chartImageCache.value.table) {
      const canvasTable = drawTableToCanvas(dataSource.value);
      if (!canvasTable) {
        throw new Error('表格图片生成失败');
      }
      chartImageCache.value.table = canvasTable.toDataURL('image/png', 1.0);
    }

    // 确保图表已经生成
    await Promise.all([
      drawCharts('chart1Ref'),
      drawCharts('chart2Ref')
    ]);

    // 检查所有必要的图片数据是否存在
    if (!imgUrl1.value || !imgUrl2.value) {
      throw new Error('图表生成失败');
    }

    const data = {
      experiment_id: "71",
      reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
      reporter_name: accountStore.account.name,
      course_name: accountStore.course.courseName,
      course_id: accountStore.course.courseId,
      submit_time: experimentDate.getTime(),
      summary: summary.value,
      periodvalue: periodvalue.value,
      discount_rate1: discount_rate1.value,
      NPV: npv.value.toFixed(2),
      IRR_annual: irr_annual.value.toFixed(2),
      IRR_quarter: irr_quarter.value.toFixed(2),
      IRR_month: irr_month.value.toFixed(2),
      cashflowTable_html: chartImageCache.value.table,
      charts1: imgUrl1.value,
      charts2: imgUrl2.value
    };

    console.log('Submitting data with images:', {
      hasTable: !!data.cashflowTable_html,
      hasChart1: !!data.charts1,
      hasChart2: !!data.charts2
    });

    emit('submit', data);
  } catch (error) {
    console.error('提交失败:', error);
    message.error('提交失败：' + (error instanceof Error ? error.message : String(error)));
  } finally {
    spinning.value = false;
  }
};

function next() {
  if (current.value == 3) {
    if (subCurrent.value < substeps.value.length - 1) {
      // 子步骤还没到最后，继续推进
      if (!substeps.value[subCurrent.value + 1].disabled) {
        subCurrent.value += 1;
      }
    } else {
      // 子步骤到头，进入主步骤下一页
      current.value = 4;
    }
  } else {
    current.value += 1;
  }
}


function prev() {
  if (current.value == 3) {
    // 如果在步骤栏下
    if (subCurrent.value == 0) {
      current.value = 2;
    } else {
      subCurrent.value -= 1;
    }
  } else {
    current.value = Math.max(0, current.value - 1);
  }
}

// 添加清除缓存的函数
const clearImageCache = () => {
  chartImageCache.value = {
    chart1: '',
    chart2: '',
    table: ''
  };
};

// 在数据变化时清除缓存
watch([dataSource, discount_rate1], () => {
  clearImageCache();
}, { deep: true });

</script>

<style scoped>

.container {
  max-width: 100%;
  margin: 0 auto;
  font-family: 'Arial', sans-serif;
}

h2 {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 40px;
}

.report-section {
  margin-bottom: 40px;
}

.report-item {
  text-align: left;
  text-indent: 2em;
}

.report-item h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.report-item p {
  font-size: 18px;
  margin-bottom: 20px;
}

.report-item span {
  font-size: 20px;
}

.report-item label {
  font-size: 20px;
}

.input-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 30px;
}

.input-group {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.input-group label {
  font-size: 16px;
  font-weight: bold;
  margin-right: 10px;
}

.confirm-button {
  margin-left: 20px;
}

.calculate-result {
  text-align: center;
  margin: 20px 25px;
}

.table-container {
  display: flex;
  justify-content: center;
  margin-bottom: 40px;
}

.chart1Ref-container {
  display: flex;
  justify-content: center;
}

.chart2Ref-container {
  display: flex;
  justify-content: center;
}

.result-section {
  display: flex;
  justify-content: center;
}

.result-card {
  background-color: #f0f2f5;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  width: 50%;
  text-align: center;
}

.result-card p {
  font-size: 18px;
  font-weight: bold;
}

.editable-row-operations {
  display: flex;
  justify-content: space-between;
  width: 150px;
}

.operation-btn {
  margin-right: 10px;
}

.a-table {
  width: 100%;
  background-color: #fff;
  border-collapse: collapse;
}

.a-table thead th {
  background-color: #f0f2f5;
  padding: 10px;
  text-align: left;
  font-weight: bold;
  font-size: 40px;
}

.a-table tbody tr:nth-child(even) {
  background-color: #f0f2f5;
}

.a-table tbody tr:hover {
  background-color: #e6f7ff;
}

.a-table tbody td {
  padding: 10px;
}

.result-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 18px;
  margin-top: 30px;
}

.result-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.result-label {
  font-weight: bold;
  margin-right: 5px;
}

.result-value {
  color: #1890ff;
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

.textarea-with-min-height {
  min-height: 300px;
}
</style>
