<template>
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
     <!-- 步骤内容 -->
    <div class="steps-content" v-if="current != 3">
      <div class="exp-step-card" v-if="current == 0" ref="content1">
         <!-- 步骤内容 -->
        <h2>实验目的</h2>
        <p class="content">
          理解软件项目规模度量功能点法原理，通过实验操作掌握功能点法。
          学生应以小组为单位，根据本小组"软件工程管理与经济"课程设计项目架构及组件等设计成果，以功能点方法测量该项目的规模(功能点数量)。
          建议选用某一种功能点方法度量课程设计项目的功能点，并采用另外一种功能点方法或其他的软件规模度量方法对前一种方法的度量结果进行验证。
        </p>
        <p style="margin-bottom: 20px; text-indent: 2em">本实验为课内设计性实验项目，实验学时1学时，完成实验报告1学时。</p>
      </div>
      <div class="exp-step-card" v-if="current == 1" ref="content2">

          <!-- 实验原理 -->
        <h2>实验原理</h2>
        <p class="content">
          软件规模度量是软件项目成本估算以及软件项目经济评价的基础。软件规模度量是软件项目成本估算以及软件项目经济评价的基础。软件规模度的方法主要有代码行法、功能点法、对象点法和用例点法等。
        </p>
        <p class="content">
          功能点法(Function
          point,简称FP)，是从软件系统功能特征的角度，测量软件规模的方法，以期克服代码行法的弱点。功能点法基于软件系统需求和设计模型分析，得到软件系统实现功能所具备的功能点，功能点的定义明确，功能点不仅数量反映了软件系统的复杂度及规模，也有利于软件系统间规模的比较。软件系统的功能点也可以转换为代码行，转换系数则取决于特定开发语言。
          功能点法在演进过程中，先后有5种功能点法成为ISO国际标准，即IFPUG、MARKⅡ、COSMIC、NESMA和FiSMA方法。目前在全球使用功能点方法的企业中，超过90%使用IFPUG或NESMA方法，而NESMA方法中详细功能点方法与IFPUG方法基本等效。
        </p>
        <p class="content">建议按照以下步骤执行度量过程。</p>
        <p class="content">1.判定软件系统的工程类型</p>
        <p class="content">
          功能点对软件规模的测量可包含软件生命周期的整个过程，从软件需求、架构设计、构件设计、测试直至软件部署与维护，功能点测量软件系统规模的准确度逐步提高。功能点测量软件系统的工程类型有三种：新开发项目、增强(开发)项目以及应用程序功能点测量。
        </p>
        <p class="content">2.识别和确定系统的边界和范围</p>
        <p class="content">
          在功能点测量中，边界划分和确定是正确估算所测软件系统规模的重点和难点。依据测量工作目的确定测量范围，通常认为测量边界是指被测程序与外部使用者、其他应用系统或程序的接口或边界。
        </p>
        <p class="content">3.功能点分析</p>
        <p class="content">
          功能点分析将软件系统的功能需求分为数据功能需求和处理数据的事务功能需求。数据功能需求由数据类型功能点描述，包含应用程序的内部逻辑数据和应用程序的外部接口数据，事务功能需求由反应人机交互(事务处理)类型的功能点描述，包含对数据的外部输入、输出和查询。
        </p>
        <p class="content">4.测量数据功能点和事务功能点</p>
        <p class="content">(1) 数据类型功能点</p>
        <p class="content">ILF 内部逻辑文件（Internal Logical File） EIF 外部接口文件（External Interface File）</p>
        <p class="content">
          ILF内部逻辑文件是用户可识别的系统边界以内的一组逻辑关联的数据或者控制信息，ILF由系统的基本处理过程来维护。
          EIF外部接口文件是用户可以识别的，由其它系统维护，在本系统引用的一组逻辑相关数据或控制信息，由本系统的基本处理过程引用，是其它系统的内部逻辑文件。
        </p>
        <p class="content">(2) 人机交互类型（事务）的功能点</p>
        <p class="content">EI 外部输入（External Input） EO 外部输出（External Output） EQ 外部查询（External Inquiry）</p>
        <p class="content">
          EI外部输入是指一个处理来自本应用边界之外的一组数据或者控制信息的基本处理过程。外部输入的基本目的是为/了维护（包括增加、修改及删除数据等）一个内部逻辑文件（ILF）或者改变系统的行为。
          EO外部输出是指一个向应用边界之外或用户提供经过加工处理的数据或者控制信息的基本处理过程
        </p>
        <p class="content">5.计算未调整功能点数</p>
        <p class="content">
          根据软件系统的需求模型和设计模型，计数ILF、EIF、EI、EO和EQ组件的数量，确定每个组件的复杂度等级。按照国际功能点用户组(IFPUG方法)标准的计算方法，计算软件系统的未调整功能点数(UFP)，公式如下：
        </p>
        <p class="content">UFP = ΣILF +ΣEIF +Σ𝐸𝐸𝐸 +ΣEI +ΣEO +ΣEQ</p>
        <p class="content">6.计算调整后功能点</p>
        <p class="content">
          为了有效反应软件系统非功能因素对软件项目工作量的影响，IFPUG方法使用14个通用系统特征因子修正未调整功能点数，这些特征因子包括数据通信、分布式数据处理、性能、复杂处理、可重用性等。将这14个因子根据其对软件系统影响程度的不同分别赋予0～5数值中的某个权重值，按照以下公式对应用系统的功能点进行调整，最终得到软件系统工作量的功能点数。功能点公式如下：
        </p>
        <p class="content">
          FP = UFP x VAF &nbsp;&nbsp;&nbsp;&nbsp;上式中，UFP是未调整功能点数，VAF是功能点调整因子，FP是功能点数。
        </p>
        <p class="content">
          VAF计算式： VAF = 0.65 +0.01x ΣAi(1-14)
          &nbsp;&nbsp;&nbsp;&nbsp;上式中，Ai的取值0～5，因此VAF的取值范围为0.65～1.35。
        </p>
      </div>
      <div class="exp-step-card" v-if="current == 2" ref="content3">

           <!-- 实验方法简介 -->
        <h2>实验方法简介</h2>
        <!-- <h2 style="margin-top: 50px">下面具体区分三种方法</h2> -->
             <!-- 选择功能点分析方法 -->
        <div class="title">下面具体区分三种方法</div>
        <div class="title">1. 预估功能点分析方法</div>
        <p class="content">
          预估功能点分析方法主要用于计划阶段，因为此阶段需求文件大多不完善，故而只需关注逻辑文件即可。预估功能点分析是指在度量时，只识别出软件需求的数据功能数量，根据经验公式得出软件规模。
        </p>
        <p class="content">
          当有概念模型或具有类似详细程度的模型可用时，采用公式(1)计算：
        </p>
        <div style="margin-left: 30px; text-align: center;">
          FP = 35 × CILF + 15 × CEIF
        </div>
        <p class="content" style="margin-bottom: 20px">
          其中FP为预估功能点计数，CILF为概念数据模型中内部逻辑文件数量，CEIF为概念数据模型中外部逻辑文件数量。
        </p>
        <p class="content">
          当有第三范式模型或具有类似详细程度的模型可用时，采用公式(2)计算：
        </p>
        <div style="margin-left: 30px; text-align: center;">
          FP = 25 × NETM + 15 × NETE
        </div>
        <p class="content" style="margin-bottom: 20px">
          其中FP为预估功能点计数，NETM为形式化数据模型中维护的实体类型数量，NETE为形式化数据模型中引用的实体类型数量。
        </p>
        <div class="title">2. 估算功能点分析方法</div>
        <p class="content" style="margin-bottom: 20px">
          估算功能点分析方法主要用于执行阶段，此时需求文件较为完善，故需要关注逻辑文件和相应的操作。估算功能点分析是指在确定每个功能部件（数据功能或事务功能）的复杂性程度时使用标准值：数据功能全部采用"低"级复杂性程度，事务功能全部采用"中"级复杂性程度计量。该方法与详细功能点分析的唯一区别是不用为每个功能识别分配复杂性程度，而是采用"默认值"。
        </p>
        <div class="title">3. 详细功能点分析方法</div>
        <p class="content" style="margin-bottom: 20px">
          详细功能点分析方法主要用于事后评估阶段，此时功能需求非常详细，可关注逻辑文件、相应操作和复杂度。预估功能点分析方法与估算功能点分析方法的计算结果，与详细功能点分析方法的计算结果有很强的相关性和一致性。在软件项目早期，选择预估功能点分析方法较好。
        </p>
        <br />
        <div class="title">请从上述三种方法中选择一种方法进行实验：</div>
        <div>
          <a-radio-group class="radio" v-model:value="selectMethod" buttonStyle="solid">
            <a-radio-button value="indicative">预估功能点分析方法</a-radio-button>
            <a-radio-button value="estimated">估算功能点分析方法</a-radio-button>
            <a-radio-button value="detailed">详细功能点分析方法</a-radio-button>
          </a-radio-group>
        </div>
        <br />
        <div v-if="getSelectMethod() == 'indicative'">
          <div class="title">请选择预估功能点分析方法使用的公式：</div>
          <div>
            <a-radio-group class="radio" v-model:value="indicativeAlg" buttonStyle="solid">
              <a-radio-button value="1">概念模型公式</a-radio-button>
              <a-radio-button value="2">第三范式模型公式</a-radio-button>
            </a-radio-group>
          </div>
          <br />
        </div>
        <div class="emphasize">注意：最终提交的实验报告将根据本页选择的方法生成</div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 4" ref="content5">
        <h2 style="margin-top: 20px">实验心得</h2>
        <a-form :model="summary" name="nest-messages">
          <a-form-item label="请输入实验总结" name="summary">
            <a-textarea v-model:value="summary" />
          </a-form-item>
        </a-form>
        <div class="emphasize">
          <span style="color: black">请确认将要提交的实验方法：</span>
          <span>{{ methodInfo[getSelectMethod()] }}</span>
          <span v-if="getSelectMethod() == 'indicative'">-{{ algInfo[+getIndicativeAlg() - 1] }}</span>
        </div>
        <br />
      </div>
    </div>
    <div>
      <!-- <a-tabs v-if="current == 3" v-model:activeKey="activeKey" :tabBarGutter="100" ref="content4">
        <a-tab-pane key="1" tab="预估功能点分析方法">
          <One ref="OneRef"></One>
        </a-tab-pane>
        <a-tab-pane key="2" tab="估算功能点分析方法" force-render>
          <Two ref="TwoRef"></Two>
        </a-tab-pane>
        <a-tab-pane key="3" tab="详细功能点分析方法">
          <Three ref="ThreeRef"></Three>
        </a-tab-pane>
      </a-tabs> -->
      <div v-if="current == 3">
        <One1 ref="OneRef1" v-if="getSelectMethod() == 'indicative' && getIndicativeAlg() == '1'"></One1>
        <One2 ref="OneRef2" v-if="getSelectMethod() == 'indicative' && getIndicativeAlg() == '2'"></One2>
        <Two ref="TwoRef" v-if="getSelectMethod() == 'estimated'"></Two>
        <Three ref="ThreeRef" v-if="getSelectMethod() == 'detailed'"></Three>
      </div>
    </div>
    <div class="steps-action">
      <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
        @click="next"><step-forward-outlined />下一步</a-button>
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
        <EyeOutlined />
        预览报告
      </a-button>
      <a-button v-if="current == steps.length - 1" type="primary" style="float: right; margin-right: 7px" @click="submit">
        提交
      </a-button>
      <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
    </div>
  </div>
  <!-- <br> -->
  <a-spin :spinning="spinning">
    <div v-if="current > 0" style="display: flex; justify-content: flex-end;margin-top: 20px;">
      <!-- <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button> -->
      <a-button danger @click="clearData">清空数据</a-button>
    </div>
  </a-spin>
  <div>
    <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
    style="width:300px" okText="确认" cancelText="取消">
      <p>确认清空实验数据？</p>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, toRaw, computed } from 'vue';
import './styles/main.css';
// import table6 from './components/table6.vue'
import One1 from './views/1-1.vue';
import One2 from './views/1-2.vue';
import Two from './views/2.vue';
import Three from './views/3.vue';
import { message } from 'ant-design-vue';
import { useStore } from './stores';
import { storeToRefs } from 'pinia';
import { useExperimentStore } from '@/store/experiment';
import { useAccountStore } from '@/store/account';
import { useRouter } from 'vue-router';
import { 
  StepForwardOutlined, 
  StepBackwardOutlined, 
  EyeOutlined 
} from '@ant-design/icons-vue';

const experimentStore = useExperimentStore();
const accountStore = useAccountStore();
const router = useRouter();

// @ts-ignore 忽略TypeScript类型错误
const { summary, selectMethod, indicativeAlg } = storeToRefs(useStore());
// const activeKey = ref('1');
const myStore = useStore();
const spinning = ref<boolean>(false);
// 数据定义
const current = ref(0) // 当前步骤
const steps = ref([
  {
    title: '实验目的',
    content: '0-content',
  },
  {
    title: '实验原理',
    content: 'First-content',
  },
  {
    title: '实验方法简介',
    content: 'Second-content',
  },
  {
    title: '实验步骤',
    content: 'Third-content'
  },
  {
    title: '实验总结',
    content: 'Fourth-content',
  }
]);

const methodInfo = ref({
  indicative: '预估功能点分析方法',
  estimated: '估算功能点分析方法',
  detailed: '详细功能点分析方法',
});

const algInfo = ref(['概念模型公式', '第三范式模型公式']);

// 创建计算属性来解决类型问题
const getSelectMethod = () => selectMethod.value as string;
const getIndicativeAlg = () => indicativeAlg.value as string;

// const selectMethod = ref("indicative");
// const indicativeAlg = ref("1");

const emit = defineEmits({
  submit: (data) => {
    return true
  }
});

const receivedData = ref(null);
const experimentDate = new Date();

// 1. 定义 formulaType 及 experimentId 计算逻辑
const computedFormulaType = computed(() => {
  if (selectMethod.value === 'indicative') {
    if (indicativeAlg.value === '1') return 'No1';
    if (indicativeAlg.value === '2') return 'No2';
  } else if (selectMethod.value === 'estimated') {
    return 'No3';
  } else if (selectMethod.value === 'detailed') {
    return 'No4';
  }
  return '';
});

const experimentId = computed(() => {
  if (selectMethod.value === 'indicative') {
    if (indicativeAlg.value === '1') {
      return '31'; // 1-1 概念模型公式法
    } else if (indicativeAlg.value === '2') {
      return '13'; // 1-2 第三范式法
    }
  } else if (selectMethod.value === 'estimated') {
    return '32'; // 2 估算功能分析法
  } else if (selectMethod.value === 'detailed') {
    return '33'; // 3 详细功能分析法
  }
  return '';
});

// 2. 修改 submit 和 downLoadFile，带上 formulaType 和 experiment_id
const submit = () => {
  spinning.value = true;
  const data = {
    // 实验总结
    summary: myStore.summary,
    // 提交实验方法类型
    methodType: getSelectMethod(),
    VAF: myStore.VAF,
    formulaType: computedFormulaType.value,
    experiment_id: experimentId.value,
  }
  if(getSelectMethod() == 'indicative'){
    data['algType'] = getIndicativeAlg(); // 选择的公式类型
    if(getIndicativeAlg() == '1'){
      const UFP_1 = 35 * (myStore.ILF ? myStore.ILF : 0) + 15 * (myStore.EIF ? myStore.EIF : 0)
      const FP_1 = (UFP_1 * myStore.VAF).toFixed(2)
      data['ILF'] = myStore.ILF;
      data['EIF'] = myStore.EIF;
      data['system_table_1'] = toRaw(myStore.tableData6);
      data['UFP_1'] = UFP_1;
      data['FP_1'] = FP_1;
    }
    else if(getIndicativeAlg() == '2'){
      const UFP_1 = 25 * (myStore.ETM ? myStore.ETM : 0) + 10 * (myStore.ETE ? myStore.ETE : 0);
      const FP_1 = (UFP_1 * myStore.VAF).toFixed(2);
      data['ETM'] = myStore.ETM;
      data['ETE'] = myStore.ETE;
      data['system_table_1'] = toRaw(myStore.tableData6);
      data['UFP_1'] = UFP_1;
      data['FP_1'] = FP_1;
    }
  }
  else if(getSelectMethod() == 'estimated'){
    const UFP_2 = myStore.tableData7.reduce((amt, item) => amt + parseInt(item.C?.toString() || '0'), 0);
    const FP_2 = (UFP_2 * myStore.VAF).toFixed(2);
    data['UFP_table_2'] = toRaw(myStore.tableData7);
    data['system_table_2'] = toRaw(myStore.tableData6);
    data['UFP_2'] = UFP_2;
    data['FP_2'] = FP_2;
  }
  else if(getSelectMethod() == 'detailed'){
    data['UFP_table_3'] = toRaw(myStore.tableData5);
    data['system_table_3'] = toRaw(myStore.tableData6);
    data['UFP_3'] = myStore.SUM;
    data['FP_3'] = myStore.ALL;
  }

  // 添加基础数据
  const baseData = {
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
  };

  // 合并数据
  const finalData = {
    ...baseData,
    ...data
  };

  localStorage.setItem('nesma_experimentData', JSON.stringify(finalData));
  // 保存数据到receivedData
  receivedData.value = finalData;
  
  // 向父组件提交数据
  emit('submit', finalData);
  spinning.value = false;
};

async function downLoadFile() {
  // 如果没有先提交数据，尝试从localStorage恢复
  if (!receivedData.value) {
    const savedData = localStorage.getItem('nesma_experimentData');
    if (savedData) {
      const parsedData = JSON.parse(savedData);
      // 确保恢复的数据与当前选定的实验类型匹配
      if (parsedData.experiment_id === experimentId.value) {
        receivedData.value = parsedData;
      } else {
        message.error('报告数据与当前实验类型不匹配，请重新提交。');
        return;
      }
    } else {
      message.error('报告数据未准备好，请先提交报告数据。');
      return;
    }
  }

  // 确保 formulaType 和 experiment_id 是最新的
  const reportData = {
    ...receivedData.value,
    formulaType: computedFormulaType.value,
    experiment_id: experimentId.value,
  };

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('nesma_page_state', JSON.stringify({
      current: current.value.toString(),
      hasSubmitted: true,
      selectMethod: getSelectMethod(),
      indicativeAlg: getIndicativeAlg()
    }));
  } catch (e) {
    console.error('保存页面状态失败', e);
  }

  console.log('即将发送报告数据:', reportData);

  spinning.value = true;
  try {
    // 路由跳转到报告预览页面，传递报告数据参数
    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(reportData))
      }
    });
  } catch (error) {
    console.error('跳转到报告预览页面失败:', error);
    message.error('预览报告失败，请稍后重试');
  } finally {
    spinning.value = false;
  }
}

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

// 添加onMounted生命周期钩子用于恢复页面状态
onMounted(() => {
  let почиталосьSubmittedAfterPreview = false;
  // 尝试从会话存储中恢复页面状态
  try {
    const savedStateRaw = sessionStorage.getItem('nesma_page_state');
    if (savedStateRaw) {
      const savedState = JSON.parse(savedStateRaw);
      // 恢复当前步骤
      if (savedState.current) {
        current.value = parseInt(savedState.current);
      }
      // 恢复选择的方法和公式
      if (savedState.selectMethod) {
        selectMethod.value = savedState.selectMethod;
      }
      if (savedState.indicativeAlg) {
        indicativeAlg.value = savedState.indicativeAlg;
      }
      if (savedState.hasSubmitted) {
        почиталосьSubmittedAfterPreview = true;
      }
      console.log('NESMA 页面状态已恢复:', savedState);
      // 清除会话存储中的状态，避免影响后续操作
      sessionStorage.removeItem('nesma_page_state');
    }
  } catch (e) {
    console.error('NESMA 恢复页面状态失败:', e);
  }

  const uniqueNesmaKey = 'nesma_experimentData';
  const savedData1 = localStorage.getItem(uniqueNesmaKey);
  if (savedData1) {
    const parsedData = JSON.parse(savedData1);
    // experimentId.value should be up-to-date here due to restored selectMethod/indicativeAlg
    if (parsedData.experiment_id === experimentId.value) {
      receivedData.value = parsedData;
      console.log(`NESMA: 已从localStorage ('${uniqueNesmaKey}') 恢复数据 (ID: ${experimentId.value})`);
    } else {
      console.warn(`NESMA: localStorage中的数据ID (${parsedData.experiment_id}) 与当前实验ID (${experimentId.value}) 不匹配。数据未加载。`);
      receivedData.value = null;
      // localStorage.removeItem(uniqueNesmaKey); // 可选：如果ID不匹配，清除旧数据
    }
  } else {
    console.log(`NESMA: localStorage中未找到key为 '${uniqueNesmaKey}' 的数据。`);
    receivedData.value = null;
  }
});

// 下一页和上一页操作
const OneRef1 = ref(null);
const OneRef2 = ref(null);
const TwoRef = ref(null);
const ThreeRef = ref(null);

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

const stepChange = () => {
  current.value = 3;
}

async function next() {
  addRecord();
  if (current.value == 3) {
    if(getSelectMethod() == 'indicative' && getIndicativeAlg() == '1' && !OneRef1.value.next()
      || getSelectMethod() == 'indicative' && getIndicativeAlg() == '2' && !OneRef2.value.next()
      || getSelectMethod() == 'estimated' && !TwoRef.value.next()
      || getSelectMethod() == 'detailed' && !ThreeRef.value.next()){
        current.value = 4;
    }
  }
  else if(current.value == 2)
  {
    await stepChange();
    if(getSelectMethod() == 'indicative'){
      if(getIndicativeAlg() == '1'){
        OneRef1.value.setCurrent(0);
      }
      else if(getIndicativeAlg() == '2'){
        OneRef2.value.setCurrent(0);
      }
    }
    else if(getSelectMethod() == 'estimated'){
      TwoRef.value.setCurrent(0);
    }
    else if(getSelectMethod() == 'detailed'){
      ThreeRef.value.setCurrent(0);
    }
  }
  else {
    current.value++;
  }
}

async function prev() {
  addRecord();
  if (current.value == 3) {
    if(getSelectMethod() == 'indicative' && getIndicativeAlg() == '1' && !OneRef1.value.prev()
      || getSelectMethod() == 'indicative' && getIndicativeAlg() == '2' && !OneRef2.value.prev()
      || getSelectMethod() == 'estimated' && !TwoRef.value.prev()
      || getSelectMethod() == 'detailed' && !ThreeRef.value.prev()){
        current.value = 2;
    }
  }
  else if(current.value == 4)
  {
    await stepChange();
    if(getSelectMethod() == 'indicative'){
      if(getIndicativeAlg() == '1'){
        OneRef1.value.setCurrent(-1);
      }
      else if(getIndicativeAlg() == '2'){
        OneRef2.value.setCurrent(-1);
      }
    }
    else if(getSelectMethod() == 'estimated'){
      TwoRef.value.setCurrent(-1);
    }
    else if(getSelectMethod() == 'detailed'){
      ThreeRef.value.setCurrent(-1);
    }
  }
  else {
    current.value--;
  }
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