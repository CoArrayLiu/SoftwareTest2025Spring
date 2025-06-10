<template>
  <!-- 实验步骤导航 -->
  <a-steps v-model:current="current" size="small">
    <a-step v-for="item in steps" :key="item.title" :title="item.title" />
  </a-steps>

  <!-- 实验内容区域 -->
  <div class="steps-content">
    <!--<h2> 实验目的</h2>-->
    <div class="exp-step-card" v-show="current == 0" ref="content1">
      <h2>一、实验目的</h2>
      <p>
        1. 本实验旨在通过动态投资回收期的计算方法，评估软件开发项目的投资回收期，并分析项目的经济效益和风险。<br />
        2.
        本实验帮助同学理解国家标准《软件测试成本度量规范》中软件测试成本度量原理，通过实验操作，掌握软件测试成本度量过程。<br />
        3.
        以小组为单位，根据本组"软件工程管理与经济"课设项目架构等设计成果，使用《软件测试成本度量规范》中的方法，估算该项目的测试成本。<br />
        4. 本实验为课内设计性实验项目，实验学时1小时，完成实验报告1学时。
      </P>
    </div>

    <!-- 实验原理 -->
    <div class="exp-step-card" v-show="current == 1" ref="content2">
      <h2>二、实验原理</h2>
      <p>
        <b>动态投资回收期</b>（Dynamic Payback Period, DPP）是一种用于评估投资回报率的方法，
        其基本原理是将投资成本与现金流量相比较，计算出从投资开始到投资收回的时间，即投资回收期。
        动态投资回收期相较于传统的投资回收期更加精细，能够考虑现金流量的时间价值因素，能够更准确地评估投资的回报率和风险。
      </p>

      <p>
        动态投资回收期的计算过程中，需要考虑以下几个关键因素：<br />
        1. 现金流量的时间价值：通过折现率将不同时期的现金流量折算为同一时点的现值，使得计算结果更加准确。<br />
        2. 累计净现金流量：通过计算各期现金流入与流出的差额，并进行累计，判断投资回收的时间点。<br />
        3. 折现系数：根据给定的折现率，计算各期现金流量的现值系数，反映货币的时间价值。
      </p>

      <p >动态投资回收期 DPP 的计算公式：</p>

      <!-- 按年显示的 DPP 计算公式 -->
      <div class="formula-container indented" v-if="dppUnit === 'year'">
        <span>DPP = </span>
        <span>(累计折现值出现正值的年数 - 1) + </span>
        <div>
          <div class="numerator">上年累计折现值的绝对值</div>
          <div class="denominator">当年净现金流量的折现值</div>
        </div>
      </div>

      <!-- 按月显示的 DPP 计算公式 -->
<div class="formula-container indented" v-else>
  <span>DPP（月） = </span>
  <span>(累计折现值出现正值的月份数 - 1) + </span>
  <div class="fraction">
    <div class="numerator">上月累计折现值的绝对值</div>
    <div class="denominator">当月净现金流量的折现值</div>
  </div>
</div>

      <p>折现系数的计算公式为：</p>

      <div>
        <span>P = </span>
        <div class="fraction">
          <div class="numerator">1</div>
          <div class="denominator">(1 + r)<sup>n</sup></div>
        </div>
        <span style="margin-left: 8px">其中 r 为折现率，n 为年份</span>
      </div>
    </div>

    <!-- 实验内容 -->
    <div class="exp-step-card" v-show="current == 2" ref="content3">
      <h2>三、实验内容</h2>
      <h2>1. 制定项目计划和预算</h2>
      <p>
        （1）根据市场调研和项目需求，制定详细的项目计划和预算，包括项目的开发周期、人力资源投入、硬件和软件设备购置、项目运营成本等。<br />
        （2）确定回收期的时间范围，决定要计算的投资回收期的时间范围。实验中假设投资回收期的时间范围为5年。<br />
        （3）根据项目计划和预算，计算出项目的总投资成本，包括项目开发过程中的各种成本和运营成本。<br />
      </p>

      <h2>2. 计算项目现金流</h2>
      <p>
        （1）根据项目计划和预算，计算出项目的现金流量，包括项目开发过程中的各种收益和运营收益。<br />
        （2）时点0表示项目开始的时间，时点1~时点6代表各年终止时点。（单位：千元）
      </p>

      <h2>3. 计算净现金流量及其现值</h2>
      <p>
        (1)根据现金流入和现金流出，计算净现金流量:<br />
        净现金流量 = 现金流入 - 现金流出。<br />
        累计净现金流量 = 年初净现金流量 + 当年产生的净现金流量。<br /><br />
        (2)根据净现金流量和现值系数，计算净现金流量现值。<br />
        净现金流量现值 = 各时点净现金流量 × 现值系数。<br />
        累计净现金流量现值 = 年初净现金流量现值 + 当年产生的净现金流量现值。
      </p>

      <h2>4. 确定投资回收点</h2>
      <p>
        找到累计净现金流量现值变为正值的时间点，即投资开始后第一个时间点的累计净现金流量现值为正。
      </p>

      <h2>5. 计算动态投资回收期</h2>
      <p>动态投资回收期 DPP 的计算公式：</p>

      <div class="formula-container indented">
        <span>DPP = </span>
        <span>(累计折现值出现正值的年数 - 1) + </span>
        <div class="fraction">
          <div class="numerator">上年累计折现值的绝对值</div>
          <div class="denominator">当年净现金流量的折现值</div>
        </div>
      </div>
    </div>

    <!-- 实验小结 -->
    <div class="exp-step-card" v-show="current == 3" ref="content4">
      <a-steps v-model:current="subCurrent" size="small">
        <a-step v-for="item in substeps" :key="item.title" :title="item.title" :disabled="item.disabled" />
      </a-steps>

      <!--步骤1 计算项目现金流-->
      <div class="step-card" v-show="subCurrent == 0">
      <h2>步骤一：填写现金流数据</h2>
      <p>输入该项目各年的收入与支出数据，计算每年的现金流入与现金流出结果。</p>
 
        <h3>现金流入</h3>
        <a-table :dataSource="cashInflowData" :columns="columns" :pagination="false" bordered>
          <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex !== 'item'">
              <a-input v-if="record.editable" v-model:value="record[column.dataIndex]" style="width: 100%" />
              <span v-else>{{ record[column.dataIndex] }}</span>
            </template>
            <template v-else>
              <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
            </template>
          </template>
        </a-table>

        <div style="margin-top: 20px">
          <h3>现金流出</h3>
          <a-table :dataSource="cashOutflowData" :columns="columns" :pagination="false" bordered>
            <template #bodyCell="{ column, record }">
              <template v-if="column.dataIndex !== 'item'">
                <a-input v-if="record.editable" v-model:value="record[column.dataIndex]" style="width: 100%" />
                <span v-else>{{ record[column.dataIndex] }}</span>
              </template>
              <template v-else>
                <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
              </template>
            </template>
          </a-table>
        </div>
      </div>

      <!-- 步骤2：计算净现金流量 -->
<div class="step-card" v-show="subCurrent == 1">
  <h2>步骤二：计算净现金流量</h2>
  <p>通过比较每年现金流入与流出，得出每年的净现金收益。</p>
  <a-table :dataSource="netCashFlowData" :columns="columns" :pagination="false" bordered>
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex !== 'item'">
        <span>{{ record[column.dataIndex] }}</span>
      </template>
    </template>
  </a-table>
</div>

<!-- 步骤3：计算净现金流量现值 -->
<div class="step-card" v-show="subCurrent == 2">
  <h2>步骤三：计算净现金流量现值</h2>
  <p>通过设定折现率，系统计算出各年净现金流量和累计净现金流量，从而反映资金的时间价值。</p>

  <div style="margin-bottom: 20px">
    <div style="display: flex; align-items: center; margin-bottom: 16px">
      <span>折现率 = </span>
      <a-input-number
        v-model:value="discount_rate"
        style="width: 100px; margin: 0 8px"
        :min="0"
        :max="100"
        :step="1.0"
        :formatter="(value) => `${value}%`"
        :parser="(value) => value.replace('%', '')"
      />
    </div>

    <div class="formula-container">
      <span>现值系数 = </span>
      <div class="fraction">
        <div class="numerator">1</div>
        <div class="denominator">(1 + r)<sup>n</sup></div>
      </div>
      <span style="margin-left: 8px">其中 r 为折现率，n 为年份</span>
    </div>
  </div>

  <a-table :dataSource="presentValueData" :columns="columns" :pagination="false" bordered>
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex !== 'item'">
        <span>{{ record[column.dataIndex] }}</span>
      </template>
    </template>
  </a-table>
</div>


      <!--步骤4 完成实验数据统计-->
      <div class="step-card" v-show="subCurrent == 3">
      <h2>步骤四：完成实验数据统计</h2>
  <p>通过汇总所有的计算数据，生成完整的现金流量与现值统计表以进行动态投资回收期分析。</p>
        <p>依照先前实验步骤中输入的数据，计算得出累计净现值现值。</p>

        <h3>现金流入</h3>
        <a-table :dataSource="finalCashInflowData" :columns="columns" :pagination="false" bordered>
          <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex !== 'item'">
              <span>{{ record[column.dataIndex] }}</span>
            </template>
            <template v-else>
              <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
            </template>
          </template>
        </a-table>

        <div style="margin-top: 20px">
          <h3>现金流出</h3>
          <a-table :dataSource="finalCashOutflowData" :columns="columns" :pagination="false" bordered>
            <template #bodyCell="{ column, record }">
              <template v-if="column.dataIndex !== 'item'">
                <span>{{ record[column.dataIndex] }}</span>
              </template>
              <template v-else>
                <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
              </template>
            </template>
          </a-table>
        </div>

        <div style="margin-top: 20px">
          <h3>现金流量统计</h3>
          <a-table :dataSource="finalStatisticsData" :columns="columns" :pagination="false" bordered>
            <template #bodyCell="{ column, record }">
              <template v-if="column.dataIndex !== 'item'">
                <span>{{ record[column.dataIndex] }}</span>
              </template>
              <template v-else>
                <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
              </template>
            </template>
          </a-table>
        </div>
      </div>

      <!--步骤5 计算动态投资回收期-->
      <div class="step-card" v-show="subCurrent == 4">
      <h2>步骤五：计算动态投资回收期</h2>
  <p>根据累计净现值首次转正的时间点，系统计算出动态投资回收期（DPP）值。</p>
        <b>根据公式计算动态投资回收期</b>
        <!-- DPP 计算方式选择按钮 -->
<div style="margin: 16px 40px;">
  <a-radio-group v-model:value="dppUnit">
    <a-radio-button value="year">年</a-radio-button>
    <a-radio-button value="month">月</a-radio-button>
  </a-radio-group>
</div>

<!-- 年的 DPP 公式，仅在选择"按年"时显示 -->
<div class="formula-container indented" v-if="dppUnit === 'year'">
  <span>DPP = </span>
  <span>(累计折现值出现正值的年数 - 1) + </span>
  <div class="fraction">
    <div class="numerator">上年累计折现值的绝对值</div>
    <div class="denominator">当年净现金流量的折现值</div>
  </div>
</div>

<!-- 月的 DPP 公式，仅在选择"按月"时显示 -->
<div class="formula-container indented" v-else-if="dppUnit === 'month'">
  <span>DPP（月） = </span>
  <span>(累计折现值出现正值的月份数 - 1) + </span>
  <div class="fraction">
    <div class="numerator">上月累计折现值的绝对值</div>
    <div class="denominator">当月净现金流量的折现值</div>
  </div>
</div>
        <div style="margin-top: 20px">
          <!-- 仅在“按年”时显示年份 -->
          <p v-if="dppUnit === 'year'">
            累计净现金流量现值，由负转正的年份是第
          <span class="result-text">{{ positiveYear }}</span> 年
        </p>

        <!-- 仅在“按月”时显示月份 -->
        <p v-if="dppUnit === 'month'">
          累计净现金流量现值由负转正的月份是第
          <span class="result-text">{{ positiveMonth }}</span> 月
        </p>

        <!-- 动态投资回收期 DPP 显示 -->
        <p v-if="dppUnit === 'year'">
          动态投资回收期 DPP = <span class="result-text">{{ calculatedDPP }}</span> 年
        </p>
        <p v-else>
          动态投资回收期 DPP = <span class="result-text">{{ monthlyDPP }}</span> 个月
        </p>
      </div>
      </div>
    </div>

    <div class="exp-step-card" v-if="current == 4" ref="content5">
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
    <a-button v-if="current == steps.length - 1" type="primary" style="float: right;margin-right: 7px;" @click="submit"> 提交 </a-button>
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

  <!--<a-layout-footer> By QPH </a-layout-footer>-->
</template>

<script lang="ts" setup>
import { message } from 'ant-design-vue';
import html2canvas from 'html2canvas';
import { toRaw, ref, onMounted } from 'vue';
import { useStore } from './stores';
import { useAccountStore } from '@/store';
import { useExperimentStore } from '@/store/experiment';
import { storeToRefs } from 'pinia';
import { reactive, computed } from 'vue';
import * as echarts from 'echarts';
import { useRouter } from 'vue-router';

const showvalueform = ref(false);
const chart = ref(null);
const current = ref(0);
const subCurrent = ref(0);
const store = useStore();
const dppUnit = ref<'year' | 'month'>('year'); // 默认为按"年"
const nominal_monthly_rate = computed(() => {
  return discount_rate.value / 100 / 12;
});


const steps = ref([
  { title: '实验目的', content: '0-content' },
  { title: '实验原理', content: '1-content' },
  { title: '实验内容', content: '2-content' },
  { title: '实验步骤', content: '3-content' },
  { title: '实验小结', content: '4-content' },
]);

const substeps = ref([
  { title: '计算项目现金流', disabled: false },
  { title: '计算净现金流量', disabled: false },
  { title: '计算净现金流量现值', disabled: false },
  { title: '完成实验数据统计', disabled: false },
  { title: '计算动态投资回收期', disabled: false },
]);

function next() {
  if (current.value == 3) {
    // 如果在步骤栏下
    if (subCurrent.value >= 0 && subCurrent.value <= 3) {
      if (substeps.value[subCurrent.value + 1].disabled == false) {
        subCurrent.value += 1;
      }
    } else {
      current.value = 4;
    }
  } else {
    // current.value = Math.min(steps.value.length - 1, current.value + 1);
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

const clearMsg = ref(false);
const clearData = () => {
  clearMsg.value = true;
};

const clearConfirm = () => {
  // store.clear();
  message.warning('已清空数据');
  clearMsg.value = false;
};

const clearCancel = () => {
  clearMsg.value = false;
};
const experimentStore = useExperimentStore();
const accountStore = useAccountStore();
const spinning = ref<boolean>(false);

const experimentDate = new Date();
const router = useRouter();
const myStore = useStore();

async function downLoadFile() {
  // 先准备基础数据
  const finalData = {
    experiment_id: "72",
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
    // 添加实验相关数据
    discount_rate: discount_rate.value,
    cash_flow0: cash_flow0.value,
    cash_flow1: cash_flow1.value,
    cash_flow2: cash_flow2.value,
    cash_flow3: cash_flow3.value,
    cash_flow4: cash_flow4.value,
    cash_flow5: cash_flow5.value,
    cash_outflow0: cash_outflow0.value,
    cash_outflow1: cash_outflow1.value,
    cash_outflow2: cash_outflow2.value,
    cash_outflow3: cash_outflow3.value,
    cash_outflow4: cash_outflow4.value,
    cash_outflow5: cash_outflow5.value,
    net_cash_flow0: net_cash_flow0.value,
    net_cash_flow1: net_cash_flow1.value,
    net_cash_flow2: net_cash_flow2.value,
    net_cash_flow3: net_cash_flow3.value,
    net_cash_flow4: net_cash_flow4.value,
    net_cash_flow5: net_cash_flow5.value,
    accumulated_net_cash_flow0: accumulated_net_cash_flow0.value,
    accumulated_net_cash_flow1: accumulated_net_cash_flow1.value,
    accumulated_net_cash_flow2: accumulated_net_cash_flow2.value,
    accumulated_net_cash_flow3: accumulated_net_cash_flow3.value,
    accumulated_net_cash_flow4: accumulated_net_cash_flow4.value,
    accumulated_net_cash_flow5: accumulated_net_cash_flow5.value,
    present_value_factor0: present_value_factor0.value,
    present_value_factor1: present_value_factor1.value,
    present_value_factor2: present_value_factor2.value,
    present_value_factor3: present_value_factor3.value,
    present_value_factor4: present_value_factor4.value,
    present_value_factor5: present_value_factor5.value,
    present_net_cash_flow0: present_net_cash_flow0.value,
    present_net_cash_flow1: present_net_cash_flow1.value,
    present_net_cash_flow2: present_net_cash_flow2.value,
    present_net_cash_flow3: present_net_cash_flow3.value,
    present_net_cash_flow4: present_net_cash_flow4.value,
    present_net_cash_flow5: present_net_cash_flow5.value,
    pre_accumulated_net_cash_flow0: pre_accumulated_net_cash_flow0.value,
    pre_accumulated_net_cash_flow1: pre_accumulated_net_cash_flow1.value,
    pre_accumulated_net_cash_flow2: pre_accumulated_net_cash_flow2.value,
    pre_accumulated_net_cash_flow3: pre_accumulated_net_cash_flow3.value,
    pre_accumulated_net_cash_flow4: pre_accumulated_net_cash_flow4.value,
    pre_accumulated_net_cash_flow5: pre_accumulated_net_cash_flow5.value,
    DPP: dpp.value,
    year: year.value,
    summary: summary.value,
    // 添加现金流入数据
    operating_revenue0: operating_revenue0.value,
    operating_revenue1: operating_revenue1.value,
    operating_revenue2: operating_revenue2.value,
    operating_revenue3: operating_revenue3.value,
    operating_revenue4: operating_revenue4.value,
    operating_revenue5: operating_revenue5.value,
    recovery_of_residual_value_of_fixed_assets0: recovery_of_residual_value_of_fixed_assets0.value,
    recovery_of_residual_value_of_fixed_assets1: recovery_of_residual_value_of_fixed_assets1.value,
    recovery_of_residual_value_of_fixed_assets2: recovery_of_residual_value_of_fixed_assets2.value,
    recovery_of_residual_value_of_fixed_assets3: recovery_of_residual_value_of_fixed_assets3.value,
    recovery_of_residual_value_of_fixed_assets4: recovery_of_residual_value_of_fixed_assets4.value,
    recovery_of_residual_value_of_fixed_assets5: recovery_of_residual_value_of_fixed_assets5.value,
    recovery_of_residual_value_of_intangible_assets0: recovery_of_residual_value_of_intangible_assets0.value,
    recovery_of_residual_value_of_intangible_assets1: recovery_of_residual_value_of_intangible_assets1.value,
    recovery_of_residual_value_of_intangible_assets2: recovery_of_residual_value_of_intangible_assets2.value,
    recovery_of_residual_value_of_intangible_assets3: recovery_of_residual_value_of_intangible_assets3.value,
    recovery_of_residual_value_of_intangible_assets4: recovery_of_residual_value_of_intangible_assets4.value,
    recovery_of_residual_value_of_intangible_assets5: recovery_of_residual_value_of_intangible_assets5.value,
    working_capital0: working_capital0.value,
    working_capital1: working_capital1.value,
    working_capital2: working_capital2.value,
    working_capital3: working_capital3.value,
    working_capital4: working_capital4.value,
    working_capital5: working_capital5.value,
    // 添加现金流出数据
    construction_investment0: construction_investment0.value,
    construction_investment1: construction_investment1.value,
    construction_investment2: construction_investment2.value,
    construction_investment3: construction_investment3.value,
    construction_investment4: construction_investment4.value,
    construction_investment5: construction_investment5.value,
    liquidityF0: liquidityF0.value,
    liquidityF1: liquidityF1.value,
    liquidityF2: liquidityF2.value,
    liquidityF3: liquidityF3.value,
    liquidityF4: liquidityF4.value,
    liquidityF5: liquidityF5.value,
    operation_cost0: operation_cost0.value,
    operation_cost1: operation_cost1.value,
    operation_cost2: operation_cost2.value,
    operation_cost3: operation_cost3.value,
    operation_cost4: operation_cost4.value,
    operation_cost5: operation_cost5.value,
    maintain_operational_investment0: maintain_operational_investment0.value,
    maintain_operational_investment1: maintain_operational_investment1.value,
    maintain_operational_investment2: maintain_operational_investment2.value,
    maintain_operational_investment3: maintain_operational_investment3.value,
    maintain_operational_investment4: maintain_operational_investment4.value,
    maintain_operational_investment5: maintain_operational_investment5.value,
    taxes_and_surcharges0: taxes_and_surcharges0.value,
    taxes_and_surcharges1: taxes_and_surcharges1.value,
    taxes_and_surcharges2: taxes_and_surcharges2.value,
    taxes_and_surcharges3: taxes_and_surcharges3.value,
    taxes_and_surcharges4: taxes_and_surcharges4.value,
    taxes_and_surcharges5: taxes_and_surcharges5.value,
    // 添加报告模板需要的数据
    positiveYear: positiveYear.value,
    calculatedDPP: calculatedDPP.value,
    monthlyDPP: monthlyDPP.value,
  };

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('dongtaitouzi_page_state', JSON.stringify({
      current: current.value,
      hasSubmitted: true,
      discount_rate: discount_rate.value,
      summary: summary.value
    }));
  } catch (e) {
    console.error('保存页面状态失败', e);
  }

  // 跳转到报告预览页面
  try {
    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(finalData))
      }
    });
  } catch (error) {
    message.error('预览报告失败，请稍后重试');
  }
}
const year = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_net_cash_flow0.value;
  var pre_accumulated_net_cash_flow1 = present_net_cash_flow1.value + pre_accumulated_net_cash_flow0;
  var pre_accumulated_net_cash_flow2 = present_net_cash_flow2.value + pre_accumulated_net_cash_flow1;
  var pre_accumulated_net_cash_flow3 = present_net_cash_flow3.value + pre_accumulated_net_cash_flow2;
  var pre_accumulated_net_cash_flow4 = present_net_cash_flow4.value + pre_accumulated_net_cash_flow3;
  var pre_accumulated_net_cash_flow5 = present_net_cash_flow5.value + pre_accumulated_net_cash_flow4;
  if (pre_accumulated_net_cash_flow0 >= 0) {
    return 0;
  } else if (pre_accumulated_net_cash_flow1 >= 0) {
    return 1;
  } else if (pre_accumulated_net_cash_flow2 >= 0) {
    return 2;
  } else if (pre_accumulated_net_cash_flow3 >= 0) {
    return 3;
  } else if (pre_accumulated_net_cash_flow4 >= 0) {
    return 4;
  } else if (pre_accumulated_net_cash_flow5 >= 0) {
    return 5;
  }
  return -1;
});

const dpp = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_net_cash_flow0.value;
  var pre_accumulated_net_cash_flow1 = present_net_cash_flow1.value + pre_accumulated_net_cash_flow0;
  var pre_accumulated_net_cash_flow2 = present_net_cash_flow2.value + pre_accumulated_net_cash_flow1;
  var pre_accumulated_net_cash_flow3 = present_net_cash_flow3.value + pre_accumulated_net_cash_flow2;
  var pre_accumulated_net_cash_flow4 = present_net_cash_flow4.value + pre_accumulated_net_cash_flow3;
  var pre_accumulated_net_cash_flow5 = present_net_cash_flow5.value + pre_accumulated_net_cash_flow4;
  if (pre_accumulated_net_cash_flow0 >= 0) {
    return 0;
  } else if (pre_accumulated_net_cash_flow1 >= 0) {
    return parseFloat(Math.abs(pre_accumulated_net_cash_flow0 / present_net_cash_flow1.value).toFixed(4));
  } else if (pre_accumulated_net_cash_flow2 >= 0) {
    return 1 + parseFloat(Math.abs(pre_accumulated_net_cash_flow1 / present_net_cash_flow2.value).toFixed(4));
  } else if (pre_accumulated_net_cash_flow3 >= 0) {
    return 2 + parseFloat(Math.abs(pre_accumulated_net_cash_flow2 / present_net_cash_flow3.value).toFixed(4));
  } else if (pre_accumulated_net_cash_flow4 >= 0) {
    return 3 + parseFloat(Math.abs(pre_accumulated_net_cash_flow3 / present_net_cash_flow4.value).toFixed(4));
  } else if (pre_accumulated_net_cash_flow5 >= 0) {
    return 4 + parseFloat(Math.abs(pre_accumulated_net_cash_flow4 / present_net_cash_flow5.value).toFixed(4));
  }
  return -1;
});

const summary = ref<string>('');
const discount_rate = ref(0);

// 现金流
const cash_flow0 = computed(() => {
  return (
    1 * operating_revenue0.value +
    1 * recovery_of_residual_value_of_fixed_assets0.value +
    1 * recovery_of_residual_value_of_intangible_assets0.value +
    1 * working_capital0.value
  );
});

const cash_flow1 = computed(() => {
  return (
    1 * operating_revenue1.value +
    1 * recovery_of_residual_value_of_fixed_assets1.value +
    1 * recovery_of_residual_value_of_intangible_assets1.value +
    1 * working_capital1.value
  );
});

const cash_flow2 = computed(() => {
  return (
    1 * operating_revenue2.value +
    1 * recovery_of_residual_value_of_fixed_assets2.value +
    1 * recovery_of_residual_value_of_intangible_assets2.value +
    1 * working_capital2.value
  );
});

const cash_flow3 = computed(() => {
  return (
    1 * operating_revenue3.value +
    1 * recovery_of_residual_value_of_fixed_assets3.value +
    1 * recovery_of_residual_value_of_intangible_assets3.value +
    1 * working_capital3.value
  );
});

const cash_flow4 = computed(() => {
  return (
    1 * operating_revenue4.value +
    1 * recovery_of_residual_value_of_fixed_assets4.value +
    1 * recovery_of_residual_value_of_intangible_assets4.value +
    1 * working_capital4.value
  );
});

const cash_flow5 = computed(() => {
  return (
    1 * operating_revenue5.value +
    1 * recovery_of_residual_value_of_fixed_assets5.value +
    1 * recovery_of_residual_value_of_intangible_assets5.value +
    1 * working_capital5.value
  );
});

// 现金流出
const cash_outflow0 = computed(() => {
  return (
    1 * construction_investment0.value +
    1 * liquidityF0.value +
    1 * operation_cost0.value +
    1 * maintain_operational_investment0.value +
    1 * taxes_and_surcharges0.value
  );
});

const cash_outflow1 = computed(() => {
  return (
    1 * construction_investment1.value +
    1 * liquidityF1.value +
    1 * operation_cost1.value +
    1 * maintain_operational_investment1.value +
    1 * taxes_and_surcharges1.value
  );
});

const cash_outflow2 = computed(() => {
  return (
    1 * construction_investment2.value +
    1 * liquidityF2.value +
    1 * operation_cost2.value +
    1 * maintain_operational_investment2.value +
    1 * taxes_and_surcharges2.value
  );
});

const cash_outflow3 = computed(() => {
  return (
    1 * construction_investment3.value +
    1 * liquidityF3.value +
    1 * operation_cost3.value +
    1 * maintain_operational_investment3.value +
    1 * taxes_and_surcharges3.value
  );
});

const cash_outflow4 = computed(() => {
  return (
    1 * construction_investment4.value +
    1 * liquidityF4.value +
    1 * operation_cost4.value +
    1 * maintain_operational_investment4.value +
    1 * taxes_and_surcharges4.value
  );
});

const cash_outflow5 = computed(() => {
  return (
    1 * construction_investment5.value +
    1 * liquidityF5.value +
    1 * operation_cost5.value +
    1 * maintain_operational_investment5.value +
    1 * taxes_and_surcharges5.value
  );
});

// 净现金流
const net_cash_flow0 = computed(() => {
  return cash_flow0.value - cash_outflow0.value;
});

const net_cash_flow1 = computed(() => {
  return cash_flow1.value - cash_outflow1.value;
});

const net_cash_flow2 = computed(() => {
  return cash_flow2.value - cash_outflow2.value;
});

const net_cash_flow3 = computed(() => {
  return cash_flow3.value - cash_outflow3.value;
});

const net_cash_flow4 = computed(() => {
  return cash_flow4.value - cash_outflow4.value;
});

const net_cash_flow5 = computed(() => {
  return cash_flow5.value - cash_outflow5.value;
});

const accumulated_net_cash_flow0 = computed(() => {
  return cash_flow0.value - cash_outflow0.value;
  // return cash_flow0.value;
});

// 累积现金流
const accumulated_net_cash_flow1 = computed(() => {
  let accumulated_net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  let accumulated_net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  return accumulated_net_cash_flow0 + accumulated_net_cash_flow1;
});

const accumulated_net_cash_flow2 = computed(() => {
  let accumulated_net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  let accumulated_net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  let accumulated_net_cash_flow2 = cash_flow2.value - cash_outflow2.value;
  return accumulated_net_cash_flow0 + accumulated_net_cash_flow1 + accumulated_net_cash_flow2;
});

const accumulated_net_cash_flow3 = computed(() => {
  let accumulated_net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  let accumulated_net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  let accumulated_net_cash_flow2 = cash_flow2.value - cash_outflow2.value;
  let accumulated_net_cash_flow3 = cash_flow3.value - cash_outflow3.value;
  return (
    accumulated_net_cash_flow0 + accumulated_net_cash_flow1 + accumulated_net_cash_flow2 + accumulated_net_cash_flow3
  );
});

const accumulated_net_cash_flow4 = computed(() => {
  let accumulated_net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  let accumulated_net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  let accumulated_net_cash_flow2 = cash_flow2.value - cash_outflow2.value;
  let accumulated_net_cash_flow3 = cash_flow3.value - cash_outflow3.value;
  let accumulated_net_cash_flow4 = cash_flow4.value - cash_outflow4.value;
  return (
    accumulated_net_cash_flow0 +
    accumulated_net_cash_flow1 +
    accumulated_net_cash_flow2 +
    accumulated_net_cash_flow3 +
    accumulated_net_cash_flow4
  );
});

const accumulated_net_cash_flow5 = computed(() => {
  let accumulated_net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  let accumulated_net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  let accumulated_net_cash_flow2 = cash_flow2.value - cash_outflow2.value;
  let accumulated_net_cash_flow3 = cash_flow3.value - cash_outflow3.value;
  let accumulated_net_cash_flow4 = cash_flow4.value - cash_outflow4.value;
  let accumulated_net_cash_flow5 = cash_flow5.value - cash_outflow5.value;
  return (
    accumulated_net_cash_flow0 +
    accumulated_net_cash_flow1 +
    accumulated_net_cash_flow2 +
    accumulated_net_cash_flow3 +
    accumulated_net_cash_flow4 +
    accumulated_net_cash_flow5
  );
});

// 营业收入
const operating_revenue0 = ref(0);
const operating_revenue1 = ref(0);
const operating_revenue2 = ref(0);
const operating_revenue3 = ref(0);
const operating_revenue4 = ref(0);
const operating_revenue5 = ref(0);

// 回收固定资产余值
const recovery_of_residual_value_of_fixed_assets0 = ref(0);
const recovery_of_residual_value_of_fixed_assets1 = ref(0);
const recovery_of_residual_value_of_fixed_assets2 = ref(0);
const recovery_of_residual_value_of_fixed_assets3 = ref(0);
const recovery_of_residual_value_of_fixed_assets4 = ref(0);
const recovery_of_residual_value_of_fixed_assets5 = ref(0);

// 回收无形资产余值
const recovery_of_residual_value_of_intangible_assets0 = ref(0);
const recovery_of_residual_value_of_intangible_assets1 = ref(0);
const recovery_of_residual_value_of_intangible_assets2 = ref(0);
const recovery_of_residual_value_of_intangible_assets3 = ref(0);
const recovery_of_residual_value_of_intangible_assets4 = ref(0);
const recovery_of_residual_value_of_intangible_assets5 = ref(0);

// 回收流动资金
const working_capital0 = ref(0);
const working_capital1 = ref(0);
const working_capital2 = ref(0);
const working_capital3 = ref(0);
const working_capital4 = ref(0);
const working_capital5 = ref(0);

// 建设投资
const construction_investment0 = ref(0);
const construction_investment1 = ref(0);
const construction_investment2 = ref(0);
const construction_investment3 = ref(0);
const construction_investment4 = ref(0);
const construction_investment5 = ref(0);

// 流动资金
const liquidityF0 = ref(0);
const liquidityF1 = ref(0);
const liquidityF2 = ref(0);
const liquidityF3 = ref(0);
const liquidityF4 = ref(0);
const liquidityF5 = ref(0);

// 经营成本
const operation_cost0 = ref(0);
const operation_cost1 = ref(0);
const operation_cost2 = ref(0);
const operation_cost3 = ref(0);
const operation_cost4 = ref(0);
const operation_cost5 = ref(0);

// 维持运营投资
const maintain_operational_investment0 = ref(0);
const maintain_operational_investment1 = ref(0);
const maintain_operational_investment2 = ref(0);
const maintain_operational_investment3 = ref(0);
const maintain_operational_investment4 = ref(0);
const maintain_operational_investment5 = ref(0);

// 营业税金及附加
const taxes_and_surcharges0 = ref(0);
const taxes_and_surcharges1 = ref(0);
const taxes_and_surcharges2 = ref(0);
const taxes_and_surcharges3 = ref(0);
const taxes_and_surcharges4 = ref(0);
const taxes_and_surcharges5 = ref(0);

const onChange = (link: string) => {
  // console.log('Anchor:OnChange', link);
};

// 计算现值系数，并保留两位小数
const present_value_factor0 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 0)).toFixed(2));
});

const present_value_factor1 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 1)).toFixed(2));
});

const present_value_factor2 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 2)).toFixed(2));
});

const present_value_factor3 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 3)).toFixed(2));
});

const present_value_factor4 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 4)).toFixed(2));
});

const present_value_factor5 = computed(() => {
  return Number((1 / Math.pow(1 + discount_rate.value / 100, 5)).toFixed(2));
});

// 净现金流量
const present_net_cash_flow0 = computed(() => {
  var net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  return parseFloat((present_value_factor0.value * net_cash_flow0).toFixed(4));
});
const present_net_cash_flow1 = computed(() => {
  var net_cash_flow1 = cash_flow1.value - cash_outflow1.value;
  return parseFloat((present_value_factor1.value * net_cash_flow1).toFixed(4));
});
const present_net_cash_flow2 = computed(() => {
  var net_cash_flow2 = cash_flow2.value - cash_outflow2.value;
  return parseFloat((present_value_factor2.value * net_cash_flow2).toFixed(4));
});
const present_net_cash_flow3 = computed(() => {
  var net_cash_flow3 = cash_flow3.value - cash_outflow3.value;
  return parseFloat((present_value_factor3.value * net_cash_flow3).toFixed(4));
});
const present_net_cash_flow4 = computed(() => {
  var net_cash_flow4 = cash_flow4.value - cash_outflow4.value;
  return parseFloat((present_value_factor4.value * net_cash_flow4).toFixed(4));
});
const present_net_cash_flow5 = computed(() => {
  var net_cash_flow5 = cash_flow5.value - cash_outflow5.value;
  return parseFloat((present_value_factor5.value * net_cash_flow5).toFixed(4));
});

// 已累积现金流
const pre_accumulated_net_cash_flow0 = computed(() => {
  var net_cash_flow0 = cash_flow0.value - cash_outflow0.value;
  return parseFloat((present_value_factor0.value * net_cash_flow0).toFixed(4));
});

const pre_accumulated_net_cash_flow1 = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_value_factor0.value * (cash_flow0.value - cash_outflow0.value);
  let pre_accumulated_net_cash_flow1 = present_value_factor1.value * (cash_flow1.value - cash_outflow1.value);
  return parseFloat((pre_accumulated_net_cash_flow0 + pre_accumulated_net_cash_flow1).toFixed(4));
});

const pre_accumulated_net_cash_flow2 = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_value_factor0.value * (cash_flow0.value - cash_outflow0.value);
  let pre_accumulated_net_cash_flow1 = present_value_factor1.value * (cash_flow1.value - cash_outflow1.value);
  var pre_accumulated_net_cash_flow2 = present_value_factor2.value * (cash_flow2.value - cash_outflow2.value);
  return parseFloat(
    (pre_accumulated_net_cash_flow0 + pre_accumulated_net_cash_flow1 + pre_accumulated_net_cash_flow2).toFixed(4)
  );
});

const pre_accumulated_net_cash_flow3 = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_value_factor0.value * (cash_flow0.value - cash_outflow0.value);
  let pre_accumulated_net_cash_flow1 = present_value_factor1.value * (cash_flow1.value - cash_outflow1.value);
  var pre_accumulated_net_cash_flow2 = present_value_factor2.value * (cash_flow2.value - cash_outflow2.value);
  var pre_accumulated_net_cash_flow3 = present_value_factor3.value * (cash_flow3.value - cash_outflow3.value);
  return parseFloat(
    (
      pre_accumulated_net_cash_flow0 +
      pre_accumulated_net_cash_flow1 +
      pre_accumulated_net_cash_flow2 +
      pre_accumulated_net_cash_flow3
    ).toFixed(4)
  );
});

const pre_accumulated_net_cash_flow4 = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_value_factor0.value * (cash_flow0.value - cash_outflow0.value);
  let pre_accumulated_net_cash_flow1 = present_value_factor1.value * (cash_flow1.value - cash_outflow1.value);
  var pre_accumulated_net_cash_flow2 = present_value_factor2.value * (cash_flow2.value - cash_outflow2.value);
  var pre_accumulated_net_cash_flow3 = present_value_factor3.value * (cash_flow3.value - cash_outflow3.value);
  var pre_accumulated_net_cash_flow4 = present_value_factor4.value * (cash_flow4.value - cash_outflow4.value);
  return parseFloat(
    (
      pre_accumulated_net_cash_flow0 +
      pre_accumulated_net_cash_flow1 +
      pre_accumulated_net_cash_flow2 +
      pre_accumulated_net_cash_flow3 +
      pre_accumulated_net_cash_flow4
    ).toFixed(4)
  );
});

const pre_accumulated_net_cash_flow5 = computed(() => {
  var pre_accumulated_net_cash_flow0 = present_value_factor0.value * (cash_flow0.value - cash_outflow0.value);
  let pre_accumulated_net_cash_flow1 = present_value_factor1.value * (cash_flow1.value - cash_outflow1.value);
  var pre_accumulated_net_cash_flow2 = present_value_factor2.value * (cash_flow2.value - cash_outflow2.value);
  var pre_accumulated_net_cash_flow3 = present_value_factor3.value * (cash_flow3.value - cash_outflow3.value);
  var pre_accumulated_net_cash_flow4 = present_value_factor4.value * (cash_flow4.value - cash_outflow4.value);
  var pre_accumulated_net_cash_flow5 = present_value_factor5.value * (cash_flow5.value - cash_outflow5.value);
  return parseFloat(
    (
      pre_accumulated_net_cash_flow0 +
      pre_accumulated_net_cash_flow1 +
      pre_accumulated_net_cash_flow2 +
      pre_accumulated_net_cash_flow3 +
      pre_accumulated_net_cash_flow4 +
      pre_accumulated_net_cash_flow5
    ).toFixed(4)
  );
});

const emit = defineEmits({
  submit: (data) => {
    return true;
  },
});

const submit = () => {
  spinning.value = true;
  const data = {
    // 此处为实验数据
    discount_rate: discount_rate.value,

    cash_flow0: cash_flow0.value,
    cash_flow1: cash_flow1.value,
    cash_flow2: cash_flow2.value,
    cash_flow3: cash_flow3.value,
    cash_flow4: cash_flow4.value,
    cash_flow5: cash_flow5.value,

    cash_outflow0: cash_outflow0.value,
    cash_outflow1: cash_outflow1.value,
    cash_outflow2: cash_outflow2.value,
    cash_outflow3: cash_outflow3.value,
    cash_outflow4: cash_outflow4.value,
    cash_outflow5: cash_outflow5.value,

    net_cash_flow0: net_cash_flow0.value,
    net_cash_flow1: net_cash_flow1.value,
    net_cash_flow2: net_cash_flow2.value,
    net_cash_flow3: net_cash_flow3.value,
    net_cash_flow4: net_cash_flow4.value,
    net_cash_flow5: net_cash_flow5.value,
    // 营业收入
    operating_revenue0: operating_revenue0.value,
    operating_revenue1: operating_revenue1.value,
    operating_revenue2: operating_revenue2.value,
    operating_revenue3: operating_revenue3.value,
    operating_revenue4: operating_revenue4.value,
    operating_revenue5: operating_revenue5.value,

    // 回收固定资产余值
    recovery_of_residual_value_of_fixed_assets0: recovery_of_residual_value_of_fixed_assets0.value,
    recovery_of_residual_value_of_fixed_assets1: recovery_of_residual_value_of_fixed_assets1.value,
    recovery_of_residual_value_of_fixed_assets2: recovery_of_residual_value_of_fixed_assets2.value,
    recovery_of_residual_value_of_fixed_assets3: recovery_of_residual_value_of_fixed_assets3.value,
    recovery_of_residual_value_of_fixed_assets4: recovery_of_residual_value_of_fixed_assets4.value,
    recovery_of_residual_value_of_fixed_assets5: recovery_of_residual_value_of_fixed_assets5.value,

    // 回收无形资产余值
    recovery_of_residual_value_of_intangible_assets0: recovery_of_residual_value_of_intangible_assets0.value,
    recovery_of_residual_value_of_intangible_assets1: recovery_of_residual_value_of_intangible_assets1.value,
    recovery_of_residual_value_of_intangible_assets2: recovery_of_residual_value_of_intangible_assets2.value,
    recovery_of_residual_value_of_intangible_assets3: recovery_of_residual_value_of_intangible_assets3.value,
    recovery_of_residual_value_of_intangible_assets4: recovery_of_residual_value_of_intangible_assets4.value,
    recovery_of_residual_value_of_intangible_assets5: recovery_of_residual_value_of_intangible_assets5.value,

    // 回收流动资金
    working_capital0: working_capital0.value,
    working_capital1: working_capital1.value,
    working_capital2: working_capital2.value,
    working_capital3: working_capital3.value,
    working_capital4: working_capital4.value,
    working_capital5: working_capital5.value,

    // 建设投资
    construction_investment0: construction_investment0.value,
    construction_investment1: construction_investment1.value,
    construction_investment2: construction_investment2.value,
    construction_investment3: construction_investment3.value,
    construction_investment4: construction_investment4.value,
    construction_investment5: construction_investment5.value,

    // 流动资金
    liquidityF0: liquidityF0.value,
    liquidityF1: liquidityF1.value,
    liquidityF2: liquidityF2.value,
    liquidityF3: liquidityF3.value,
    liquidityF4: liquidityF4.value,
    liquidityF5: liquidityF5.value,

    // 经营成本
    operation_cost0: operation_cost0.value,
    operation_cost1: operation_cost1.value,
    operation_cost2: operation_cost2.value,
    operation_cost3: operation_cost3.value,
    operation_cost4: operation_cost4.value,
    operation_cost5: operation_cost5.value,

    // 维持运营投资
    maintain_operational_investment0: maintain_operational_investment0.value,
    maintain_operational_investment1: maintain_operational_investment1.value,
    maintain_operational_investment2: maintain_operational_investment2.value,
    maintain_operational_investment3: maintain_operational_investment3.value,
    maintain_operational_investment4: maintain_operational_investment4.value,
    maintain_operational_investment5: maintain_operational_investment5.value,

    // 营业税金及附加
    taxes_and_surcharges0: taxes_and_surcharges0.value,
    taxes_and_surcharges1: taxes_and_surcharges1.value,
    taxes_and_surcharges2: taxes_and_surcharges2.value,
    taxes_and_surcharges3: taxes_and_surcharges3.value,
    taxes_and_surcharges4: taxes_and_surcharges4.value,
    taxes_and_surcharges5: taxes_and_surcharges5.value,

    accumulated_net_cash_flow0: accumulated_net_cash_flow0.value,
    accumulated_net_cash_flow1: accumulated_net_cash_flow1.value,
    accumulated_net_cash_flow2: accumulated_net_cash_flow2.value,
    accumulated_net_cash_flow3: accumulated_net_cash_flow3.value,
    accumulated_net_cash_flow4: accumulated_net_cash_flow4.value,
    accumulated_net_cash_flow5: accumulated_net_cash_flow5.value,

    present_value_factor0: present_value_factor0.value,
    present_value_factor1: present_value_factor1.value,
    present_value_factor2: present_value_factor2.value,
    present_value_factor3: present_value_factor3.value,
    present_value_factor4: present_value_factor4.value,
    present_value_factor5: present_value_factor5.value,

    present_net_cash_flow0: present_net_cash_flow0.value,
    present_net_cash_flow1: present_net_cash_flow1.value,
    present_net_cash_flow2: present_net_cash_flow2.value,
    present_net_cash_flow3: present_net_cash_flow3.value,
    present_net_cash_flow4: present_net_cash_flow4.value,
    present_net_cash_flow5: present_net_cash_flow5.value,

    pre_accumulated_net_cash_flow0: pre_accumulated_net_cash_flow0.value,
    pre_accumulated_net_cash_flow1: pre_accumulated_net_cash_flow1.value,
    pre_accumulated_net_cash_flow2: pre_accumulated_net_cash_flow2.value,
    pre_accumulated_net_cash_flow3: pre_accumulated_net_cash_flow3.value,
    pre_accumulated_net_cash_flow4: pre_accumulated_net_cash_flow4.value,
    pre_accumulated_net_cash_flow5: pre_accumulated_net_cash_flow5.value,

    DPP: dpp.value,
    year: year.value,
    summary: summary.value,
    // Add fields required by the PDF template
    positiveYear: positiveYear.value,
    calculatedDPP: calculatedDPP.value,
    monthlyDPP: monthlyDPP.value,
  };
  emit('submit', data);
  spinning.value = false;
};

// 定义表格列
const columns = [
  {
    title: '项目/年份',
    dataIndex: 'item',
    width: '20%',
  },
  {
    title: '0',
    dataIndex: 'year0',
  },
  {
    title: '1',
    dataIndex: 'year1',
  },
  {
    title: '2',
    dataIndex: 'year2',
  },
  {
    title: '3',
    dataIndex: 'year3',
  },
  {
    title: '4',
    dataIndex: 'year4',
  },
  {
    title: '5',
    dataIndex: 'year5',
  },
];

// 定义表格数据
const cashInflowData = ref([
  {
    key: '1.1',
    item: '营业收入',
    year0: operating_revenue0,
    year1: operating_revenue1,
    year2: operating_revenue2,
    year3: operating_revenue3,
    year4: operating_revenue4,
    year5: operating_revenue5,
    editable: true,
  },
  {
    key: '1.2',
    item: '回收固定资产余值',
    year0: recovery_of_residual_value_of_fixed_assets0,
    year1: recovery_of_residual_value_of_fixed_assets1,
    year2: recovery_of_residual_value_of_fixed_assets2,
    year3: recovery_of_residual_value_of_fixed_assets3,
    year4: recovery_of_residual_value_of_fixed_assets4,
    year5: recovery_of_residual_value_of_fixed_assets5,
    editable: true,
  },
  {
    key: '1.3',
    item: '回收无形资产余值',
    year0: recovery_of_residual_value_of_intangible_assets0,
    year1: recovery_of_residual_value_of_intangible_assets1,
    year2: recovery_of_residual_value_of_intangible_assets2,
    year3: recovery_of_residual_value_of_intangible_assets3,
    year4: recovery_of_residual_value_of_intangible_assets4,
    year5: recovery_of_residual_value_of_intangible_assets5,
    editable: true,
  },
  {
    key: '1.4',
    item: '回收流动资金',
    year0: working_capital0,
    year1: working_capital1,
    year2: working_capital2,
    year3: working_capital3,
    year4: working_capital4,
    year5: working_capital5,
    editable: true,
  },
  {
    key: '1',
    item: '现金流入',
    year0: computed(() => cash_flow0.value),
    year1: computed(() => cash_flow1.value),
    year2: computed(() => cash_flow2.value),
    year3: computed(() => cash_flow3.value),
    year4: computed(() => cash_flow4.value),
    year5: computed(() => cash_flow5.value),
    editable: false,
    isBold: true,
  },
]);

const cashOutflowData = ref([
  {
    key: '2.1',
    item: '建设投资',
    year0: construction_investment0,
    year1: construction_investment1,
    year2: construction_investment2,
    year3: construction_investment3,
    year4: construction_investment4,
    year5: construction_investment5,
    editable: true,
  },
  {
    key: '2.2',
    item: '流动资金',
    year0: liquidityF0,
    year1: liquidityF1,
    year2: liquidityF2,
    year3: liquidityF3,
    year4: liquidityF4,
    year5: liquidityF5,
    editable: true,
  },
  {
    key: '2.3',
    item: '经营成本',
    year0: operation_cost0,
    year1: operation_cost1,
    year2: operation_cost2,
    year3: operation_cost3,
    year4: operation_cost4,
    year5: operation_cost5,
    editable: true,
  },
  {
    key: '2.4',
    item: '维持运营投资',
    year0: maintain_operational_investment0,
    year1: maintain_operational_investment1,
    year2: maintain_operational_investment2,
    year3: maintain_operational_investment3,
    year4: maintain_operational_investment4,
    year5: maintain_operational_investment5,
    editable: true,
  },
  {
    key: '2.5',
    item: '营业税金及附加',
    year0: taxes_and_surcharges0,
    year1: taxes_and_surcharges1,
    year2: taxes_and_surcharges2,
    year3: taxes_and_surcharges3,
    year4: taxes_and_surcharges4,
    year5: taxes_and_surcharges5,
    editable: true,
  },
  {
    key: '2',
    item: '现金流出',
    year0: computed(() => cash_outflow0.value),
    year1: computed(() => cash_outflow1.value),
    year2: computed(() => cash_outflow2.value),
    year3: computed(() => cash_outflow3.value),
    year4: computed(() => cash_outflow4.value),
    year5: computed(() => cash_outflow5.value),
    editable: false,
    isBold: true,
  },
]);

// 定义现值计算表格数据
const presentValueData = ref([
  {
    key: '1',
    item: '现值系数',
    year0: computed(() => present_value_factor0.value),
    year1: computed(() => present_value_factor1.value),
    year2: computed(() => present_value_factor2.value),
    year3: computed(() => present_value_factor3.value),
    year4: computed(() => present_value_factor4.value),
    year5: computed(() => present_value_factor5.value),
    editable: false,
  },
  {
    key: '2',
    item: '净现金流量(现值)',
    year0: computed(() => present_net_cash_flow0.value),
    year1: computed(() => present_net_cash_flow1.value),
    year2: computed(() => present_net_cash_flow2.value),
    year3: computed(() => present_net_cash_flow3.value),
    year4: computed(() => present_net_cash_flow4.value),
    year5: computed(() => present_net_cash_flow5.value),
    editable: false,
  },
  {
    key: '3',
    item: '累计净现金流量(现值)',
    year0: computed(() => pre_accumulated_net_cash_flow0.value),
    year1: computed(() => pre_accumulated_net_cash_flow1.value),
    year2: computed(() => pre_accumulated_net_cash_flow2.value),
    year3: computed(() => pre_accumulated_net_cash_flow3.value),
    year4: computed(() => pre_accumulated_net_cash_flow4.value),
    year5: computed(() => pre_accumulated_net_cash_flow5.value),
    editable: false,
  },
]);

// 定义最终现金流入数据
const finalCashInflowData = ref([
  {
    key: '1.1',
    item: '营业收入',
    year0: computed(() => operating_revenue0.value),
    year1: computed(() => operating_revenue1.value),
    year2: computed(() => operating_revenue2.value),
    year3: computed(() => operating_revenue3.value),
    year4: computed(() => operating_revenue4.value),
    year5: computed(() => operating_revenue5.value),
    editable: false,
  },
  {
    key: '1.2',
    item: '回收固定资产余值',
    year0: computed(() => recovery_of_residual_value_of_fixed_assets0.value),
    year1: computed(() => recovery_of_residual_value_of_fixed_assets1.value),
    year2: computed(() => recovery_of_residual_value_of_fixed_assets2.value),
    year3: computed(() => recovery_of_residual_value_of_fixed_assets3.value),
    year4: computed(() => recovery_of_residual_value_of_fixed_assets4.value),
    year5: computed(() => recovery_of_residual_value_of_fixed_assets5.value),
    editable: false,
  },
  {
    key: '1.3',
    item: '回收无形资产余值',
    year0: computed(() => recovery_of_residual_value_of_intangible_assets0.value),
    year1: computed(() => recovery_of_residual_value_of_intangible_assets1.value),
    year2: computed(() => recovery_of_residual_value_of_intangible_assets2.value),
    year3: computed(() => recovery_of_residual_value_of_intangible_assets3.value),
    year4: computed(() => recovery_of_residual_value_of_intangible_assets4.value),
    year5: computed(() => recovery_of_residual_value_of_intangible_assets5.value),
    editable: false,
  },
  {
    key: '1.4',
    item: '回收流动资金',
    year0: computed(() => working_capital0.value),
    year1: computed(() => working_capital1.value),
    year2: computed(() => working_capital2.value),
    year3: computed(() => working_capital3.value),
    year4: computed(() => working_capital4.value),
    year5: computed(() => working_capital5.value),
    editable: false,
  },
  {
    key: '1',
    item: '现金流入',
    year0: computed(() => cash_flow0.value),
    year1: computed(() => cash_flow1.value),
    year2: computed(() => cash_flow2.value),
    year3: computed(() => cash_flow3.value),
    year4: computed(() => cash_flow4.value),
    year5: computed(() => cash_flow5.value),
    editable: false,
    isBold: true,
  },
]);

// 定义最终现金流出数据
const finalCashOutflowData = ref([
  {
    key: '2.1',
    item: '建设投资',
    year0: computed(() => construction_investment0.value),
    year1: computed(() => construction_investment1.value),
    year2: computed(() => construction_investment2.value),
    year3: computed(() => construction_investment3.value),
    year4: computed(() => construction_investment4.value),
    year5: computed(() => construction_investment5.value),
    editable: false,
  },
  {
    key: '2.2',
    item: '流动资金',
    year0: computed(() => liquidityF0.value),
    year1: computed(() => liquidityF1.value),
    year2: computed(() => liquidityF2.value),
    year3: computed(() => liquidityF3.value),
    year4: computed(() => liquidityF4.value),
    year5: computed(() => liquidityF5.value),
    editable: false,
  },
  {
    key: '2.3',
    item: '经营成本',
    year0: computed(() => operation_cost0.value),
    year1: computed(() => operation_cost1.value),
    year2: computed(() => operation_cost2.value),
    year3: computed(() => operation_cost3.value),
    year4: computed(() => operation_cost4.value),
    year5: computed(() => operation_cost5.value),
    editable: false,
  },
  {
    key: '2.4',
    item: '维持运营投资',
    year0: computed(() => maintain_operational_investment0.value),
    year1: computed(() => maintain_operational_investment1.value),
    year2: computed(() => maintain_operational_investment2.value),
    year3: computed(() => maintain_operational_investment3.value),
    year4: computed(() => maintain_operational_investment4.value),
    year5: computed(() => maintain_operational_investment5.value),
    editable: false,
  },
  {
    key: '2.5',
    item: '营业税金及附加',
    year0: computed(() => taxes_and_surcharges0.value),
    year1: computed(() => taxes_and_surcharges1.value),
    year2: computed(() => taxes_and_surcharges2.value),
    year3: computed(() => taxes_and_surcharges3.value),
    year4: computed(() => taxes_and_surcharges4.value),
    year5: computed(() => taxes_and_surcharges5.value),
    editable: false,
  },
  {
    key: '2',
    item: '现金流出',
    year0: computed(() => cash_outflow0.value),
    year1: computed(() => cash_outflow1.value),
    year2: computed(() => cash_outflow2.value),
    year3: computed(() => cash_outflow3.value),
    year4: computed(() => cash_outflow4.value),
    year5: computed(() => cash_outflow5.value),
    editable: false,
    isBold: true,
  },
]);

// 定义最终统计数据
const finalStatisticsData = ref([
  {
    key: '1',
    item: '净现金流量',
    year0: computed(() => net_cash_flow0.value),
    year1: computed(() => net_cash_flow1.value),
    year2: computed(() => net_cash_flow2.value),
    year3: computed(() => net_cash_flow3.value),
    year4: computed(() => net_cash_flow4.value),
    year5: computed(() => net_cash_flow5.value),
    editable: false,
  },
  {
    key: '2',
    item: '累计净现金流量',
    year0: computed(() => accumulated_net_cash_flow0.value),
    year1: computed(() => accumulated_net_cash_flow1.value),
    year2: computed(() => accumulated_net_cash_flow2.value),
    year3: computed(() => accumulated_net_cash_flow3.value),
    year4: computed(() => accumulated_net_cash_flow4.value),
    year5: computed(() => accumulated_net_cash_flow5.value),
    editable: false,
  },
  {
    key: '3',
    item: '现值系数',
    year0: computed(() => present_value_factor0.value),
    year1: computed(() => present_value_factor1.value),
    year2: computed(() => present_value_factor2.value),
    year3: computed(() => present_value_factor3.value),
    year4: computed(() => present_value_factor4.value),
    year5: computed(() => present_value_factor5.value),
    editable: false,
  },
  {
    key: '4',
    item: '净现金流量(现值)',
    year0: computed(() => present_net_cash_flow0.value),
    year1: computed(() => present_net_cash_flow1.value),
    year2: computed(() => present_net_cash_flow2.value),
    year3: computed(() => present_net_cash_flow3.value),
    year4: computed(() => present_net_cash_flow4.value),
    year5: computed(() => present_net_cash_flow5.value),
    editable: false,
  },
  {
    key: '5',
    item: '累计净现金流量(现值)',
    year0: computed(() => pre_accumulated_net_cash_flow0.value),
    year1: computed(() => pre_accumulated_net_cash_flow1.value),
    year2: computed(() => pre_accumulated_net_cash_flow2.value),
    year3: computed(() => pre_accumulated_net_cash_flow3.value),
    year4: computed(() => pre_accumulated_net_cash_flow4.value),
    year5: computed(() => pre_accumulated_net_cash_flow5.value),
    editable: false,
  },
]);

// 定义净现金流量表格数据
const netCashFlowData = ref([
  {
    key: '1',
    item: '现金流入',
    year0: computed(() => cash_flow0.value),
    year1: computed(() => cash_flow1.value),
    year2: computed(() => cash_flow2.value),
    year3: computed(() => cash_flow3.value),
    year4: computed(() => cash_flow4.value),
    year5: computed(() => cash_flow5.value),
    editable: false,
  },
  {
    key: '2',
    item: '现金流出',
    year0: computed(() => cash_outflow0.value),
    year1: computed(() => cash_outflow1.value),
    year2: computed(() => cash_outflow2.value),
    year3: computed(() => cash_outflow3.value),
    year4: computed(() => cash_outflow4.value),
    year5: computed(() => cash_outflow5.value),
    editable: false,
  },
  {
    key: '3',
    item: '净现金流量',
    year0: computed(() => net_cash_flow0.value),
    year1: computed(() => net_cash_flow1.value),
    year2: computed(() => net_cash_flow2.value),
    year3: computed(() => net_cash_flow3.value),
    year4: computed(() => net_cash_flow4.value),
    year5: computed(() => net_cash_flow5.value),
    editable: false,
  },
  {
    key: '4',
    item: '累计净现金流量',
    year0: computed(() => accumulated_net_cash_flow0.value),
    year1: computed(() => accumulated_net_cash_flow1.value),
    year2: computed(() => accumulated_net_cash_flow2.value),
    year3: computed(() => accumulated_net_cash_flow3.value),
    year4: computed(() => accumulated_net_cash_flow4.value),
    year5: computed(() => accumulated_net_cash_flow5.value),
    editable: false,
  },
]);

/// 计算累计现值转正的年份
const positiveYear = computed(() => {
  const accumulated = [
    present_net_cash_flow0.value,
    present_net_cash_flow0.value + present_net_cash_flow1.value,
    present_net_cash_flow0.value + present_net_cash_flow1.value + present_net_cash_flow2.value,
    present_net_cash_flow0.value +
      present_net_cash_flow1.value +
      present_net_cash_flow2.value +
      present_net_cash_flow3.value,
    present_net_cash_flow0.value +
      present_net_cash_flow1.value +
      present_net_cash_flow2.value +
      present_net_cash_flow3.value +
      present_net_cash_flow4.value,
    present_net_cash_flow0.value +
      present_net_cash_flow1.value +
      present_net_cash_flow2.value +
      present_net_cash_flow3.value +
      present_net_cash_flow4.value +
      present_net_cash_flow5.value,
  ];

  for (let i = 1; i < accumulated.length; i++) {
    if (accumulated[i - 1] < 0 && accumulated[i] >= 0) {
      return i - 1; // 返回由负转正前的年份
    }
  }

  return '未找到';
});

// 计算DPP
const calculatedDPP = computed(() => {
  const cashFlowArray = [
    present_net_cash_flow0.value,
    present_net_cash_flow1.value,
    present_net_cash_flow2.value,
    present_net_cash_flow3.value,
    present_net_cash_flow4.value,
    present_net_cash_flow5.value,
  ];

  const accumulated = [];
  let sum = 0;
  for (let i = 0; i < cashFlowArray.length; i++) {
    sum += cashFlowArray[i];
    accumulated.push(sum);
  }

  for (let i = 1; i < accumulated.length; i++) {
    if (accumulated[i - 1] < 0 && accumulated[i] >= 0) {
      const prev = accumulated[i - 1];
      const current = cashFlowArray[i];
      const fraction = Math.abs(prev) / current;
      return Number((i - 1 + fraction).toFixed(2));
    }
  }

  return '无法计算';
});

const monthlyDPP = computed(() => {
  if (typeof calculatedDPP.value === 'number') {
    return Number((calculatedDPP.value * 12).toFixed(2));
  }
  return '无法计算';
});

const positiveMonth = computed(() => {
  if (typeof monthlyDPP.value !== 'number') return '未找到';
  return Math.floor(monthlyDPP.value);
});

// 用于DPP计算的现金流数组
const present_net_cash_flow_array = [
  present_net_cash_flow0,
  present_net_cash_flow1,
  present_net_cash_flow2,
  present_net_cash_flow3,
  present_net_cash_flow4,
  present_net_cash_flow5,
];
</script>

<style scoped>
.title {
  text-align: center;
  font-family: sans-serif;
  font-size: 30px;
}

.content {
  text-indent: 2em;
  margin-left: 20px;
  margin-right: 20px;
}

.subtitle-content {
  text-indent: 2em;
  margin-left: 15px;
  margin-right: 20px;
  font-size: large;
  font-weight: bold;
}

.recontent {
  margin-left: 20px;
  margin-right: 20px;
  font-size: 18px;
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

.textarea-with-min-width {
  min-height: 300px;
}

.exp-step-card {
  padding-inline: 14px;
}

/* 添加表格相关样式 */
.step-card {
  padding: 24px;
}

:deep(.ant-table-cell) {
  padding: 8px !important;
}

:deep(.ant-input) {
  padding: 4px 8px;
}

/* 添加分数公式样式 */
.formula-container {
  display: flex;
  align-items: center;
  font-size: 16px;
  margin: 20px 0;
  gap: 8px;
}

.formula-container.indented {
  margin-left: 40px; /* 添加缩进 */
  margin-right: 40px; /* 右侧也添加一些间距 */
}

.fraction {
  display: inline-block;
  text-align: center;
  vertical-align: middle;
}

.numerator {
  border-bottom: 1px solid #000;
  padding: 0 4px;
}

.denominator {
  padding: 0 4px;
}

sup {
  font-size: 12px;
}

/* 添加表格样式 */
.bold-text {
  font-weight: bold;
}

.indent-text {
  padding-left: 24px;
}

:deep(.ant-table-cell) {
  padding: 8px !important;
}

:deep(.ant-table-row-level-0[data-row-key='divider1']) {
  background-color: #f5f5f5;
  height: 1px;
  padding: 0;
}

:deep(.ant-table-row-level-0[data-row-key='divider1'] td) {
  padding: 0 !important;
  border-top: 1px solid #e8e8e8;
  border-bottom: 1px solid #e8e8e8;
}

.step-card h3 {
  margin-bottom: 16px;
}

/* 表格样式优化 */
:deep(.ant-table-thead > tr > th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 600;
  text-align: center;
}

:deep(.ant-table-tbody > tr > td) {
  text-align: center;
}

/* 步骤卡片样式 */
.exp-step-card {
  background: #ffffff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.step-card {
  background: #ffffff;
  padding: 20px;
  border-radius: 6px;
  margin: 16px 0;
}

/* 标题样式优化 */
h2 {
  color: #303133;
  margin-bottom: 20px;
  font-weight: 600;
  position: relative;
  padding-left: 12px;
}

h2::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 18px;
  background-color: #409eff;
  border-radius: 2px;
}

/* 公式容器样式优化 */
.formula-container {
  background-color: #f8f9fa;
  padding: 16px;
  border-radius: 6px;
  margin: 16px 40px;
}

/* 输入框样式 */
:deep(.ant-input-number) {
  margin: 0 8px;
}

/* 表格间距 */
.ant-table {
  margin-bottom: 24px;
}

/* 文本内容样式 */
.recontent {
  color: #606266;
  line-height: 1.8;
  margin-bottom: 16px;
}

/* 步骤导航样式 */
:deep(.ant-steps) {
  margin-bottom: 24px;
}

/* 按钮样式优化 */
.steps-action {
  margin-top: 24px;
  padding: 16px 0;
  border-top: 1px solid #ebeef5;
}

/* 小结文本框样式 */
.textarea-with-min-width {
  min-height: 120px;
  margin-top: 16px;
}

/* 分隔线样式优化 */
:deep(.ant-table-row-level-0[data-row-key='divider1']) {
  background-color: #f5f7fa;
}

/* 响应式布局优化 */
@media screen and (max-width: 768px) {
  .formula-container.indented {
    margin-left: 20px;
    margin-right: 20px;
  }

  .exp-step-card {
    padding: 16px;
  }
}

.result-text {
  color: #409eff;
  font-weight: bold;
  font-size: 16px;
  margin: 0 8px;
}
</style>
