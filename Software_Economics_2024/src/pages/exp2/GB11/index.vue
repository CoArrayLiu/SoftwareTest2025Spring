<template>
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-if="current === 0" ref="content1">
        <h2 style="text-align: left;">实验介绍</h2>
        <p style="text-align: left;">本实验为课内设计性实验项目，实验学时1学时，完成实验报告1学时。</p>
        <p style="text-align: left;">
          通过本实验，学生将理解软件开发成本度量中
          <strong>国标法</strong>的原理与方法。学生应以小组为单位，结合本组"软件工程管理与经济"课程设计项目的架构与组件设计成果，采用国标功能点法的综合模型，测量并估算项目的开发规模及成本。
        </p>
        <p style="text-align: left;">
          建议选用综合法作为主方法进行成本估算，并可辅以直接法或间接法对综合法结果进行对比和验证。
        </p>
        <h3 style="text-align: left;">实验目的</h3>
        <ul style="text-align: left;">
          <li>理解国标功能点法综合模型中开发成本构成及计算方法；</li>
          <li>掌握如何根据项目参数（开发人时、非功能工时、集成与安装工时）计算总开发成本；</li>
          <li>通过实际操作，提升学生的软件规模估算及成本分析能力；</li>
          <li>培养学生对软件项目结构和功能的深入理解与评估能力。</li>
        </ul>
      </div>

      <div class="exp-step-card" v-if="current == 1" ref="content2">
        <h2 style="text-align: left;">实验原理</h2>
        <h3 style="text-align: left;">一、说明</h3>
        <p class="content" style="text-align: left;">
          本实验基于《GB/T 36964-2018 软件开发成本度量指南》中的综合法模型，介绍软件开发成本的构成及其计算方法。
          综合法将开发成本细分为开发人力成本、非功能补充工时、系统集成工时和安装部署工时，能够全面反映软件项目的成本结构。
        </p>
        <p class="content" style="text-align: left;">
          软件开发成本度量是项目预算、进度管理和合同制定的重要基础。准确度量能够帮助合理分配资源，优化开发流程，提高项目管理水平。
        </p>
        <h3 style="text-align: left;">二、术语和定义</h3>
        <p class="content" style="text-align: left;">
          详见《GB/T 36964-2018》标准，主要术语包括：
        </p>
        <ul class="content" style="text-align: left;">
          <li>开发人力成本（DHC）：软件开发过程中直接投入的人力工作量。</li>
          <li>非功能补充工时（DNC）：为满足性能、安全、可维护性等非功能需求所增加的工作量。</li>
          <li>系统集成人时（IHC）：不同模块或子系统集成过程中的人力投入。</li>
          <li>安装部署工时（INC）：软件部署、安装及交付过程中的工作量。</li>
          <li>软件开发成本（SDC）：上述各项成本的总和。</li>
        </ul>
      </div>
      <div class="exp-step-card" v-if="current == 2" ref="content3">
        <h2>实验方法</h2>
        <p class="content">本标准建议的成本估算的基本流程、遵循原则、规模估算、工作量估算、成本估算、成本测量与分析等内容。</p>
        <li><strong>综合法</strong>：先分别估算开发人力成本（DHC）、非功能补充工时（DNC）、系统集成工时（IHC）和安装部署工时（INC），再汇总计算总开发成本。</li>
        <li><strong>功能点法（直接法）</strong>：识别软件功能，分类为外部输入、输出、查询等，根据复杂度赋权计算功能点总数，进而估算工作量和成本。</li>
        <li><strong>估算模型法（间接法）</strong>：基于历史项目数据和关键参数，通过参数拟合或专家评估推断软件规模和开发成本。</li>

        <h3>请选择一种度量方法进入实验步骤：</h3>
        <div class="emphasize">注意：最终提交的实验报告将根据本页选择的方法生成</div>
        <div>
          <a-radio-group class="radio" v-model:value="selectFormula" buttonStyle="solid" style="margin-bottom: 20px;">
            <a-radio-button value="No1">综合法</a-radio-button>
            <a-radio-button value="No2">功能点法</a-radio-button>
            <a-radio-button value="No3">估算模型法</a-radio-button>
          </a-radio-group>
        </div>
      </div>
      <div class="exp-step-card" v-if="current == 3" ref="content4">
        <h2>实验步骤</h2>
        <One1 ref="OneRef1" v-if="selectFormula == 'No1'"></One1>
        <Two2 ref="OneRef2" v-if="selectFormula == 'No2'"></Two2>
        <Three3 ref="OneRef3" v-if="selectFormula == 'No3'"></Three3>
      </div>
      <div class="exp-step-card" v-if="current == 4" ref="content5">
        <h2>实验总结</h2>
        <div class="summary-highlight">
          请注意：本次报告将使用 <span style="color: #fa541c; font-weight: bold;">{{ formulaName }} </span>进行生成
        </div>
        <br>
        <a-form :model="experimentSummary" name="nest-messages">
          <a-form-item label="请输入实验总结" name="summary">
            <a-textarea v-model:value="experimentSummary" />
          </a-form-item>

        </a-form>
      </div>
    </div>
  </div>
  <div class="steps-action">
    <a-button v-if="current > 0" style="margin-left: 8px; float: left;" @click="prev"><step-backward-outlined />上一步</a-button>
    <a-button v-if="current < steps.length - 1" type="primary" style="float: right;" @click="next"><step-forward-outlined />下一步</a-button>
    <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
      <EyeOutlined />
      预览报告
    </a-button>
    <a-button v-if="current == steps.length - 1" type="primary" style="float: right; margin-right: 7px" @click="() => submit()">
      提交
    </a-button>
    <br v-if="current > 2" />
    <div v-if="current > 2" style="display: flex; justify-content: flex-end; margin-top: 24px;">
      <a-button danger @click="clearData">清空数据</a-button>
    </div>
    <div>
      <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" style="width:300px"
        okText="确认" cancelText="取消">
        <p>确认清空实验数据？</p>
      </a-modal>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import { useStore } from './stores';
import { storeToRefs } from 'pinia';
import One1 from './views/1.vue';
import Two2 from './views/2.vue';
import Three3 from './views/3.vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';

const { experimentSummary, selectFormula } = storeToRefs(useStore());
const current = ref(0);
const myStore = useStore();
const accountStore = useAccountStore();

// 页面加载时恢复状态
onMounted(() => {
  // 从sessionStorage恢复页面状态
  try {
    const savedState = sessionStorage.getItem('gb11_page_state');
    if (savedState) {
      const state = JSON.parse(savedState);
      // 恢复状态
      current.value = state.current;
      selectFormula.value = state.selectFormula;
      if (state.experimentSummary) {
        experimentSummary.value = state.experimentSummary;
      }
      
      // 清除保存的状态，以便下次重新进入
      sessionStorage.removeItem('gb11_page_state');
    }
  } catch (e) {
    console.error('恢复页面状态失败', e);
  }
});

const clearMsg = ref(false);
const clearData = () => {
clearMsg.value = true;
}

const clearConfirm = () => {
myStore.clear();
message.warning('已清空数据');
clearMsg.value = false;
current.value = 0;
}

const clearCancel = () => {
clearMsg.value = false;
}

const steps = ref([
  { title: '实验目标', content: '0-content' },
  { title: '实验原理', content: '1-content' },
  { title: '实验方法', content: '2-content' },
  { title: '实验步骤', content: '3-content' },
  { title: '实验总结', content: '4-content' }
]);

const emit = defineEmits({
submit: (data) => {
  return true
}
});

const submit = () => {
  const data: any = {
    experimentSummary: myStore.experimentSummary,
    formulaType: selectFormula.value,
    course_id: accountStore.course.courseId,
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    submit_time: new Date().getTime(),
  }
  if (selectFormula.value == 'No1') {
    Object.assign(data, {
      experiment_id: "18",
    });
    data['DHC'] = myStore.DHC;
    data['DNC'] = myStore.DNC;
    data['IHC'] = myStore.IHC;
    data['INC'] = myStore.INC;
    data['SDC'] = myStore.SDC_1;
  } else if (selectFormula.value == 'No2') {
    Object.assign(data, {
      experiment_id: "19",
    });
    data['N'] = myStore.n;
    data['DNC'] = myStore.DNC;
    data['SDC'] = myStore.SDC_2;
  } else if (selectFormula.value == 'No3') {
    Object.assign(data, {
      experiment_id: "20",
    });
    data['P'] = myStore.P;
    data['S'] = myStore.S;
    data['DNC'] = myStore.DNC;
    data['SDC'] = myStore.SDC_3;
  }
  receivedData.value = data;
  localStorage.setItem('experimentData', JSON.stringify(data));
  // console.log(JSON.stringify(data));
  // 此处使用 emit，确保在 setup 中定义了 emit

  emit('submit', data)
}

function next() {
current.value = Math.min(steps.value.length - 1, current.value + 1);
}

function prev() {
  current.value = Math.max(0, current.value - 1);
}

const formulaName = computed(() => {
  if (selectFormula.value === 'No1') return '综合法';
  if (selectFormula.value === 'No2') return '功能点法';
  if (selectFormula.value === 'No3') return '估算模型法';
  return '';
});

const router = useRouter();
const spinning = ref(false);
const receivedData = ref(null);
const experimentStore = useExperimentStore();
const experimentDate = new Date();

async function downLoadFile() {
  // 组装数据
  const finalData = {
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: new Date().getTime(),
    experimentSummary: myStore.experimentSummary,
    formulaType: selectFormula.value,
  };

  if (selectFormula.value == 'No1') {
    Object.assign(finalData, {
      experiment_id: "18",
    });
    finalData['DHC'] = myStore.DHC;
    finalData['DNC'] = myStore.DNC;
    finalData['IHC'] = myStore.IHC;
    finalData['INC'] = myStore.INC;
    finalData['SDC'] = myStore.SDC_1;
  } else if (selectFormula.value == 'No2') {
    Object.assign(finalData, {
      experiment_id: "19",
    });
    finalData['N'] = myStore.n;
    finalData['DNC'] = myStore.DNC;
    finalData['SDC'] = myStore.SDC_2;
  } else if (selectFormula.value == 'No3') {
    Object.assign(finalData, {
      experiment_id: "20",
    });
    finalData['P'] = myStore.P;
    finalData['S'] = myStore.S;
    finalData['DNC'] = myStore.DNC;
    finalData['SDC'] = myStore.SDC_3;
  }

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('gb11_page_state', JSON.stringify({
      current: current.value,
      selectFormula: selectFormula.value,
      experimentSummary: myStore.experimentSummary,
      hasSubmitted: true
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

function saveSummary() {
  localStorage.setItem('experimentSummary', experimentSummary.value);
  message.success('实验总结已保存');
}
</script>


<style scoped>
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
</style>