<template>
  <div>
    <!--实验步骤-->
    <a-steps v-model:current="current" size="small">
      <a-step v-for="step in steps" :key="step.title" :title="step.title" />
    </a-steps>
    <!--具体实验区域-->
    <a-collapse class="steps-content">
      <!--每个实验步骤对应的页面-->
      <div class="exp-step-card" v-if="current == 0">
        <Step_0/>
      </div>
      <div class="exp-step-card" v-if="current == 1">
        <Step_1/>
      </div>
      <div class="exp-step-card" v-if="current == 2">
        <Step_2/>
      </div>
      <div class="exp-step-card" v-if="current == 3">
        <Step_3/>
      </div>
      <div class="exp-step-card" v-if="current == 4">
        <Step_4/>
      </div>
      <div class="exp-step-card" v-if="current == 5">
        <Step_5/>
      </div>
    </a-collapse>
    <!--步骤切换与实验报告提交-->
    <div class="steps-action">
      <!--切换步骤-->
      <a-button v-if="current > 0" style="margin-left: 8px" @click="prevStep">上一步</a-button>
      <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
        @click="nextStep">下一步</a-button>
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
        <EyeOutlined />
        预览报告
      </a-button>
      <!--提交-->
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right;margin-right: 8px;" @click="submit">
        提交
      </a-button>

      <!--清空数据-->
      <div v-if="current > 0" style="display: flex; justify-content: flex-end; margin-top: 20px">
        <a-button danger @click="clearData">清空数据</a-button>
      </div>
    </div>
    <!--清空实验数据提示-->
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
</template>

<script setup lang="ts">
  import Step_0 from './steps/Step_0.vue'
  import Step_1 from './steps/Step_1.vue'
  import Step_2 from './steps/Step_2.vue'
  import Step_3 from './steps/Step_3.vue'
  import Step_4 from './steps/Step_4.vue'
  import Step_5 from './steps/Step_5.vue'
  import steps_logic from './hooks/steps_logic'
  import data_logic from './hooks/data_logic'
  import { message } from 'ant-design-vue';
  import { ref, onMounted } from 'vue';
  import { useAccountStore, useExperimentStore } from '@/store';
  import { useRouter } from 'vue-router';
  import { EyeOutlined } from '@ant-design/icons-vue';
  import { useDecisionTreeStore } from './stores';

  const experimentStore = useExperimentStore();
  const accountStore = useAccountStore();
  const router = useRouter();
  const receivedData = ref(null);
  const experimentDate = new Date();
  const decisionTreeStore = useDecisionTreeStore();
  
  // 实验逻辑处理
  // 实验步骤
  let {current,steps,nextStep,prevStep} = steps_logic()
  //实验数据处理与提交
  let {clear, submit}=data_logic()

  let clearMsg = ref(false)

  function clearData(){
    clearMsg.value = true
  }

  function clearConfirm(){
    clear()
    current.value = 0
    message.warning("已清空数据").then()
    clearMsg.value = false
  }

  function clearCancel(){
    clearMsg.value = false
  }

  async function downLoadFile() {
    // 准备报告数据
    const reportData = {
      experiment_id: "84",  // 决策树实验ID
      reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
      reporter_name: accountStore.account.name,
      reporterId: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
      reporterName: accountStore.account.name,
      course_name: accountStore.course.courseName,
      course_id: accountStore.course.courseId,
      submit_time: experimentDate.getTime(),
      
      // 从决策树Store获取数据
      answer_1: decisionTreeStore.principleBlankAnswer,
      answer_2: decisionTreeStore.baseQuestionBlankAnswer,
      answer_3: decisionTreeStore.advancedQuestionBlankAnswer,

      table_1: decisionTreeStore.Q2traditionTreeTableAnswer,
      table_2: decisionTreeStore.Q3traditionTreeTableAnswer,
      table_3: decisionTreeStore.compareTableAnswer,
      table_4: decisionTreeStore.MLTreeTableAnswer,

      MLTreeSummary: decisionTreeStore.MLTreeSummary,
      summary: decisionTreeStore.summary,
    };
    
    // 将时间戳转换为可读格式
    if (reportData.submit_time) {
      // @ts-ignore - 故意将数字时间戳转换为字符串格式，用于显示
      reportData.submit_time = new Date(reportData.submit_time).toLocaleString();
    }
    
    // 保存数据供报告预览使用
    receivedData.value = reportData;
    localStorage.setItem('jueceshuData', JSON.stringify(reportData));
    
    // 在跳转前保存当前页面状态到 sessionStorage
    try {
      sessionStorage.setItem('jueceshu_page_state', JSON.stringify({
        current: current.value,
        hasSubmitted: true
      }));
    } catch (e) {
      console.error('保存页面状态失败', e);
    }
    
    try {
      // 将数据转为URL参数格式
      const reportDataStr = encodeURIComponent(JSON.stringify(reportData));
      
      // 跳转到报告预览页面
      router.push({
        path: '/report-preview',
        query: { 
          reportData: reportDataStr
        }
      });
    } catch (err) {
      console.error('路由跳转错误:', err);
      message.error('预览报告时出错: ' + err.message);
    }
  }
  
  // 页面加载时恢复状态
  onMounted(() => {
    // 从sessionStorage恢复页面状态
    try {
      const savedState = sessionStorage.getItem('jueceshu_page_state');
      if (savedState) {
        const state = JSON.parse(savedState);
        // 恢复状态
        current.value = state.current;
        
        // 清除保存的状态，以便下次重新进入
        sessionStorage.removeItem('jueceshu_page_state');
      }
    } catch (e) {
      console.error('恢复页面状态失败', e);
    }
    
    // 加载保存的实验数据
    const savedData = localStorage.getItem('jueceshuData');
    if (savedData) {
      receivedData.value = JSON.parse(savedData);
    }
  });

</script>

<style>
  .node circle {
    fill: #fff;
    stroke: steelblue;
    stroke-width: 1.5px;
  }

  .node text {
    font: 10px sans-serif;
  }

  .steps-content {
    margin-top: 16px;
    border-style: dashed;
    border-width:2px;
    border-radius: 6px;
    border-color: rgba(0,0,0,0.15);
    min-height: 200px;
    text-align: left;
    padding-top: 10px;
  }

  .exp-step-card {
    padding-inline: 14px;
    margin-bottom: 16px;
  }

  .steps-action {
    margin-top: 24px;
  }
</style>

