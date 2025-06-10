<template>
  <div id="two">
    <!-- <h2>三、实验方法简介</h2>
    <p class="content" style="margin-bottom: 20px">
      估算功能点分析方法主要用于执行阶段，此时需求文件较为完善，故需要关注逻辑文件和相应的操作。估算功能点分析是指在确定每个功能部件（数据功能或事务功能）的复杂性程度时使用标准值：数据功能全部采用“低”级复杂性程度，事务功能全部采用“中”级复杂性程度计量。该方法与详细功能点分析的唯一区别是不用为每个功能识别分配复杂性程度，而是采用“默认值”。
    </p> -->
    <!-- <h2>四、实验步骤</h2> -->
    <h2 style="text-align: center;">估算功能点分析方法</h2>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-if="current == 0" ref="content1">
        <!-- <div class="title">第一步：识别数据功能点和事务处理功能点</div> -->
        <h2>识别数据功能点和事务处理功能点</h2>
        <p class="content">
          数据功能是指更新、引用和检索而储存的可用的逻辑数据。数据块及控制信息是逻辑上的并且用户可确认的。数据功能分为内部逻辑文件(ILF)和外部接口文件(EIF)。事务处理是指外部输入、外部输出、外部查询、完成更新、检索和输出等操作，分为外部输入(EI)、外部输出(EO)和外部查询(EQ)。
        </p>
        <p class="content italic">请详细阅读文档中提供的系统设计模型。</p>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 1" ref="content2">
        <h2>测量内部逻辑文件(ILF)</h2>
        <p class="content">
          内部逻辑文件(ILF)是用户可确认的，在应用程序内部维护、逻辑上相关的数据块或控制信息。内部逻辑文件(ILF)用来保存经由应用程序的一个或多个处理后的数据。一旦应用程序内部的一个数据块被标识为
          ILF，即使它被另一个事务处理所引用，它也不能再被同一个应用程序当作 EIF。
        </p>
        <p class="content italic">实验操作：清点实验案例中 ILF 数量。</p>
        <br />
        <div style="margin-left: 30px">
          ILF数量为：
          <a-input v-model:value="tableData7[table7Index['ILF']].A" placeholder="NroILFs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 2" ref="content3">
        <h2>测量外部接口文件(EIF)</h2>
        <p class="content">
          外部接口文件(EIF)是用户可确认的、由被测应用程序引用，但在其他应用程序内部维护的、逻辑上相关的数据块或控制信息。外部接口文件(EIF)用来存放被测应用程序中的一个或多个基本处理所引用的数据。数据或控制数据通过诸如增加、变更、更新等事务来维护，一个
          EIF 可以被多个应用程序引用和计算，但是对于一个应用程序来讲，一个 EIF 只应被计算一次。
        </p>
        <p class="content italic">实验操作：清点实验案例中 EIF 数量。</p>
        <br />
        <div style="margin-left: 30px">
          EIF数量为：
          <a-input v-model:value="tableData7[table7Index['EIF']].A" placeholder="NroEIFs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 3" ref="content4">
        <h2>测量外部输入(EI)</h2>
        <p class="content">
          外部输入(EI)是应用程序处理来自系统边界以外的数据或控制信息的基本过程。EI 的作用是维护一个或多个 ILF
          以及通过其处理逻辑来改变系统的行为。
        </p>
        <p class="content italic">实验操作：清点实验案例中 EI 数量。</p>
        <br />
        <div style="margin-left: 30px">
          EI数量为：
          <a-input v-model:value="tableData7[table7Index['EI']].A" placeholder="NroEIs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 4" ref="content5">
        <h2>测量外部输出(EO)</h2>
        <p class="content">
          外部输出(EO)是应用程序向其边界之外提供数据或控制信息的基本处理。EO的作用是向用户提供经过处理逻辑加工的，除了检索信息或控制信息之外的信息或附加信息。处理逻辑中必须至少包含一个数学公式或者计算，创建导出数据或者维护一个或多个
          ILF，并且改变系统的行为。
        </p>
        <p class="content italic">实验操作：清点实验案例中 EO 数量。</p>
        <br />
        <div style="margin-left: 30px">
          EO数量为：
          <a-input v-model:value="tableData7[table7Index['EO']].A" placeholder="NroEOs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 5" ref="content6">
        <h2>测量外部查询(EQ)</h2>
        <p class="content">
          外部查询(EQ)是应用程序向其边界之外提供数据或控制信息查询的基本处理。EQ的作用是通过查询数据或控制信息来为用户提供信息，处理逻辑中既不包含数学公式或计算，也不产生导出数据。处理过程中不维护
          ILF，系统行为不受影响。
        </p>
        <p class="content italic">实验操作：清点实验案例中 EQ 数量。</p>
        <br />
        <div style="margin-left: 30px">
          EQ数量为：
          <a-input v-model:value="tableData7[table7Index['EQ']].A" placeholder="NroEQs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 6" ref="content7">
        <h2>明确 ILF、EIF、EI、EO、EQ 的复杂度</h2>
        <p class="content">
          数据功能的复杂度级别选择“低”，事务处理功能的复杂度级别选择“中”，也就是 ILF、EIF
          的复杂度级别都为“低”，EI、EO、EQ 的复杂度级别都为“中”
        </p>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 7" ref="content8">
        <h2>计算未调整功能点</h2>
        <p class="content">
          按照 NESMA 功能点计算实践手册(4.1 版)组件复杂度等级与功能点数对应关系表，计算得到未调整功能点数(UFP)。
        </p>
        <!-- 每个组件复杂度等级与功能点数对应关系表 -->
        <table4></table4>
        <br />
        <p class="content italic">
          实验操作：将上述各步得到的数据，填写在下面的“未调整功能点计算表”中的合适位置，计算本实验案例的未调整功能点。
        </p>
        <p class="content italic">
          下面表格中的数据已经与前面几步的测量结果对应。
        </p>
        <!-- 未调整功能点计算表 -->
        <table7 @stepLink='stepLink'></table7>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 8" ref="content9">
        <h2>计算调整后功能点</h2>
        <p class="content">
          考虑本实验案例的非功能性，从系统特征因子表及计算表采集相对复杂度调整因子(标红数值)，得到本实验案例的功能点调整因子(VAF)为
          41。将 VAF 数值代入(NESMA 法)功能点计算公式，计算得到本实验案例的功能点。
        </p>
        <p class="content italic">实验操作：运用 NESMA 标准规则，计算实验案例的调整后功能点。</p>
        <!-- 系统特征因子表及计算表 -->
        <table6></table6>
        <br />
        <span class="title">本实验功能点调整因子(VAF)为 </span>
        <span style="font-size: 20px">{{ VAF }}</span>
        <span style="margin-left: 220px" class="title">本实验未调整功能点数总计为 </span>
        <span style="font-size: 20px">{{ SUM }}</span>
        <span style="margin-left: 220px" class="title">本实验的功能点数为 </span>
        <span style="font-size: 20px">{{ ALL }}</span>
        <br /><br />
      </div>
    </div>
    <!-- <a-button class="button3" type="primary" shape="round">实验报告提交</a-button> -->
  </div>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useStore } from '../stores';
import { ref, computed, onMounted } from 'vue';

import table4 from '../components/table4.vue';
import table6 from '../components/table6.vue';
import table7 from '../components/table7.vue';
import { set } from 'nprogress';

const { VAF, tableData7 } = storeToRefs(useStore());
// 总的未调整功能点数
const SUM = computed(() => {
  return tableData7.value.reduce((amt, item) => amt + parseInt(item.C), 0);
});
const ALL = computed(() => (SUM.value * VAF.value).toFixed(2));

const current = ref(0);
const steps = ref([
  {
    title: '识别数据功能点和事务处理功能点',
    content: '0-content',
  },
  {
    title: '测量ILF',
    content: 'First-content',
  },
  {
    title: '测量EIF',
    content: 'Second-content',
  },
  {
    title: '测量EI',
    content: 'Third-content'
  },
  {
    title: '测量EO',
    content: 'Fourth-content',
  },
  {
    title: '测量EQ',
    content: 'Fifth-content',
  },
  {
    title: '明确 ILF、EIF、EI、EO、EQ 的复杂度',
    content: 'Sixth-content',
  },
  {
    title: '计算未调整功能点',
    content: 'Seventh-content',
  },
  {
    title: '计算调整后功能点',
    content: 'Eighth-content',
  }
]);

const table7Index = ref({});
onMounted(()=>{
  for(let i = 0; i < tableData7.value.length; i++){
    table7Index.value[tableData7.value[i].component] = i;
  }
  console.log(table7Index);
});
const stepIndex = ref({
  'ILF': 1,
  'EIF': 2,
  'EI': 3,
  'EO': 4,
  'EQ': 5
});

const content1 = ref(null);
const content2 = ref(null);
const content3 = ref(null);
const content4 = ref(null);
const content5 = ref(null);
const content6 = ref(null);
const content7 = ref(null);
const content8 = ref(null);
const content9 = ref(null);
const contentRecord = ref([]);

const addRecord = () => {
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
  if (current.value == 5) {
    //const content6 = this.$refs.content6;
    contentRecord.value.push(content6);
  }
  if (current.value == 6) {
    //const content7 = this.$refs.content7;
    contentRecord.value.push(content7);
  }
  if (current.value == 7) {
    //const content7 = this.$refs.content7;
    contentRecord.value.push(content8);
  }
  if (current.value == 8) {
    //const content7 = this.$refs.content7;
    contentRecord.value.push(content9);
  }
}

const next = () => {
  addRecord();
  if (current.value < steps.value.length - 1) {
    current.value++;
    return true;
  }
  else {
    return false;
  }
}

const prev = () => {
  addRecord();
  if (current.value > 0) {
    current.value--;
    return true;
  }
  else {
    return false;
  }
}

const setCurrent = (num) => {
  if(num >= 0){
    current.value = num;
  }
  else{
    current.value = steps.value.length + num;
  }
}

defineExpose({ next, prev, setCurrent });

const stepLink = (target) => {
  setCurrent(stepIndex.value[target]);
}

</script>

<style scoped>
#two {
  margin-top: 20px;
}

/* .title {
  text-align: center;
  font-family: sans-serif;
  font-size: 30px;
} */

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
