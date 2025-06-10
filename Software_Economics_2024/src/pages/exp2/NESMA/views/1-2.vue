<template>
  <div id="one2">
    <!-- <h2>三、实验方法简介</h2>
    <p class="content" style="margin-bottom: 20px">
      预估功能点分析方法主要用于计划阶段，因为此阶段需求文件大多不完善，故而只需关注逻辑文件即可。预估功能点分析是指在度量时，只识别出软件需求的数据功能数量，根据经验公式得出软件规模。
    </p> -->
    <h2 style="text-align: center;">预估功能点分析方法-第三范式模型公式</h2>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-if="current == 0" ref="content1">
        <!-- <div class="title">识别数据功能点和事务处理功能点</div> -->
        <h2>识别数据功能点和事务处理功能点</h2>
        <p class="content">
          第三范式(Third Normal Form,3rd NF)就是指表中的所有数据元素不但要能唯一地被主关键字所标识,而且它们之间还必须相互独立,不存在其他的函数关系。
          也就是说，对于一个满足2nd NF 的数据结构来说，表中有可能存在某些数据元素依赖于其他非关键字数据元素的现象,必须消除。
          在第三范式模型中，数据功能分为维护的实体类型(NETM)和和引用的实体类型(NETE)。
        </p>
        <p class="content italic">请详细阅读文档中提供的系统设计模型。</p>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 1" ref="content2">
        <h2>测量维护的实体类型(NETM)</h2>
        <p class="content">
          维护的实体类型(NETM)是用户可确认的、在应用程序内部维护、逻辑上相关的数据块或控制信息，用来保存经由应用程序的一个或多个处理后的数据。
          当来自功能点分析(FPA)表类型的实体类型由被计数应用程序维护，就将其作为维护的实体类型计数。
        </p>
        <p class="content italic">实验操作：清点实验案例中 NETM 数量。</p>
        <br />
        <div style="margin-left: 30px">
          NETM数量为：
          <a-input v-model:value="ETM" placeholder="NroNETMs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 2" ref="content3">
        <h2>测量引用的实体类型(NETE)</h2>
        <p class="content">
          引用的实体类型(NETE)是用户可确认的、由被测应用程序引用，但在其他应用程序内部维护的、逻辑上相关的数据块或控制信息，用来存放被测应用程序中的一个或多个基本处理所引用的数据。
          当来自功能点分析(FPA)表类型的实体类型由其他应用程序维护，就将其作为引用的实体类型计数。
        </p>
        <p class="content italic">实验操作：清点实验案例中 NETE 数量。</p>
        <br />
        <div style="margin-left: 30px">
          NETE数量为：
          <a-input v-model:value="ETE" placeholder="NroNETEs" style="width: 100px" />
        </div>
        <br />
      </div>
      <div class="exp-step-card" v-if="current == 3" ref="content4">
        <h2>计算未调整功能点</h2>
        <p class="content">
          使用公式 25 × NroNETMs + 10 × NroNETEs 直接计算未调整功能点数(UFP)。其中，NroNETMs 表示 NETM 的数量，NroNETEs 表示 NETE
          的数量
        </p>
        <br />
        <div style="margin-left: 30px">
          25 × <a-input v-model:value="ETM" placeholder="NroNETMs" style="width: 100px" /> + 10 ×
          <a-input v-model:value="ETE" placeholder="NroNETEs" style="width: 100px" /> =
          {{ SUM_1 }}
        </div>
        <br />
        <p class="content italic">
          上述输入框中NETM与NETE的值已与第二步和第三步的测量值对应，如有需要请在此处修改
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

const { VAF, ETM, ETE } = storeToRefs(useStore());

const SUM_1 = computed(() => 25 * (ETM.value ? ETM.value : 0) + 10 * (ETE.value ? ETE.value : 0));
const ALL_1 = computed(() => (SUM_1.value * VAF.value).toFixed(2));

const current = ref(0);
const steps = ref([
  {
    title: '识别数据功能点和事务处理功能点',
    content: '0-content',
  },
  {
    title: '测量NETM',
    content: 'First-content',
  },
  {
    title: '测量NETE',
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
#one2 {
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
