<template>
  <div id="one1">
    <!-- <h2>三、实验方法简介</h2>
    <p class="content" style="margin-bottom: 20px">
      预估功能点分析方法主要用于计划阶段，因为此阶段需求文件大多不完善，故而只需关注逻辑文件即可。预估功能点分析是指在度量时，只识别出软件需求的数据功能数量，根据经验公式得出软件规模。
    </p> -->
    <h2 style="text-align: center;">预估功能点分析方法-概念模型公式</h2>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-if="current == 0" ref="content1">
        <!-- <div class="title">识别数据功能点和事务处理功能点</div> -->
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
          <a-input v-model:value="ILF" placeholder="NroILFs" style="width: 100px" />
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
          <a-input v-model:value="EIF" placeholder="NroEIFs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 3" ref="content4">
        <h2>计算未调整功能点</h2>
        <p class="content">
          使用公式 35 × NroILFs + 15 × NroEIFs 直接计算未调整功能点数(UFP)。其中，NroILFs 表示 ILF 的数量，NroEIFs 表示 EIF
          的数量
        </p>
        <br />
        <div style="margin-left: 30px">
          35 × <a-input v-model:value="ILF" placeholder="NroILFs" style="width: 100px" /> + 15 ×
          <a-input v-model:value="EIF" placeholder="NroEIFs" style="width: 100px" /> =
          {{ SUM_1 }}
        </div>
        <br />
        <p class="content italic">
          上述输入框中ILF与EIF的值已与第二步和第三步的测量值对应，如有需要请在此处修改
        </p>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 4" ref="content5">
        <h2>计算调整后功能点</h2>
        <p class="content">
          考虑本实验案例的非功能性，从系统特征因子表及计算表采集相对复杂度调整因子(标红数值)，得到本实验案例的功能点调整因子(VAF)为
          41。将 VAF 数值代入(NESMA 法)功能点计算公式，计算得到本实验案例的功能点。
        </p>
        <p class="content italic">实验操作：运用 NESMA 标准规则，计算实验案例的调整后功能点。</p>
        <table6></table6>
        <br />
        <span class="title">本实验功能点调整因子(VAF)为 </span>
        <span style="font-size: 20px">{{ VAF }}</span>
        <span style="margin-left: 220px" class="title">本实验未调整功能点数总计为 </span>
        <span style="font-size: 20px">{{ SUM_1 }}</span>
        <span style="margin-left: 220px" class="title">本实验的功能点数为 </span>
        <span style="font-size: 20px">{{ ALL_1 }}</span>
        <br /><br />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useStore } from '../stores';
import { ref, computed } from 'vue';
import table6 from '../components/table6.vue';

const { VAF, ILF, EIF } = storeToRefs(useStore());

const SUM_1 = computed(() => 35 * (ILF.value ? ILF.value : 0) + 15 * (EIF.value ? EIF.value : 0));
const ALL_1 = computed(() => (SUM_1.value * VAF.value).toFixed(2));

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
    title: '计算未调整功能点',
    content: 'Third-content'
  },
  {
    title: '计算调整后功能点',
    content: 'Fourth-content',
  }
]);

const content1 = ref(null);
const content2 = ref(null);
const content3 = ref(null);
const content4 = ref(null);
const content5 = ref(null);
// const content6 = ref(null);
// const content7 = ref(null);
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
  // if (current.value == 5) {
  //   //const content6 = this.$refs.content6;
  //   contentRecord.value.push(content6);
  // }
  // if (current.value == 6) {
  //   //const content7 = this.$refs.content7;
  //   contentRecord.value.push(content7);
  // }
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

</script>

<style scoped>
#one1 {
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
