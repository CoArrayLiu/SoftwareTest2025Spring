<template>
    <div id="two2">
      <h2 style="text-align: center;">功能点法</h2>
      <a-steps v-model:current="current" size="small">
        <a-step v-for="item in steps" :key="item.title" :title="item.title" />
      </a-steps>
      <div class="steps-content">
        <div class="exp-step-card" v-if="current == 0" ref="content1">
          <p class="content">在估算软件开发成本时，可根据直接人力成本费率估算人力成本费率(即每人月直接人力成本与分摊到每人月的间接成本之和)，计算如下式所示：</p>
                <p class="content" style="text-align: center;">F = IF × (1 + DP)</p>
                <p class="content">式中：</p>
                <p class="content">F —— 人力成本费率，单位为<span style="font-weight: bold;">元每人月</span>；</p>
                <p class="content">IF —— 直接人力成本费率，单位为<span style="font-weight: bold;">元每人月</span>；</p>
                <p class="content">DP —— 间接成本系数，即分摊到每人月的间接成本占每人月直接人力成本的比例。委托方和第三方宜参照行业基准数据确定DP的取值。</p>
          <p class="content">如果已经获得了人力成本费率，则可以依据工作量估算结果和人力成本费率直接计算出直接人力成本和间接成本的总和，然后再计算软件开发成本，计算如下式所示：</p>
                <div class="image-center"><img src="../image/formula_2.png" width="500"></div>
                <p class="content">式中：</p>
                <p class="content">SDC —— 软件开发成本，单位为<span style="font-weight: bold;">元</span>；</p>
                <p class="content">n —— 人员类别数量，取值为不小于1的自然数；</p>
                <p class="content">E —— 第<i>i</i>类人员的工作量，单位为<span style="font-weight: bold;">人月</span>；</p>
                <p class="content">F —— 第<i>i</i>类人员的人力成本费率，单位为<span style="font-weight: bold;">元每人月</span>；</p>
                <p class="content">DNC —— 直接非人力成本，单位为<span style="font-weight: bold;">元</span>。</p>
                <p class="content">委托方可根据行业基准数据确定每人月直接人力成本与分摊到每人月的间接成本的比例，进而估算人力成本费率。</p>
        </div>
        <div class="exp-step-card" v-if="current == 1" ref="content2">
          <p class="content">DNC —— 直接非人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <div style="margin-left: 48px">
          DNC值为：
          <a-input v-model:value="DNC" placeholder="DNC" style="width: 100px" />
          </div>
        </div>
        <div class="exp-step-card" v-if="current == 2" ref="content3">
          <p class="content">F —— 第<i>i</i>类人员的人力成本费率，单位为<span style="font-weight: bold;">元每人月</span>；</p>
          <p class="content">IF —— 直接人力成本费率，单位为<span style="font-weight: bold;">元每人月</span>；</p>
          <div class="container">
            <p class="content">当前为<span style="font-weight: bold;">第{{n}}个类别</span></p>
            <div style="margin-left: 48px">
            第{{n}}个E值为：
            <a-input v-model:value="E" type="number" placeholder="E" style="width: 80px" />
            </div>
            <div style="margin-left: 48px">
            第{{n}}个IF值为：
            <a-input v-model:value="IF" placeholder="IF" style="width: 100px" />        
            </div>
            <p class="content">DP —— 间接成本系数，即分摊到每人月的间接成本占每人月直接人力成本的比例。委托方和第三方宜参照行业基准数据确定DP的取值。</p>
            <div style="margin-left: 48px">
            第{{n}}个DP值为：
            <a-input v-model:value="DP" placeholder="DP" style="width: 80px" />
          </div>
        <div>
          <p class="content">经过计算，第{{n}}个F的值 = {{F}}</p>
          <a-button style="margin-left: 300px" @click="Addnum">下一类别</a-button>
          </div>
        </div>
          <p class="content">SDC —— 软件开发成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <div class="image-center"><img src="../image/formula_2.png" width="500"></div>
          <p class="content">经过计算，当前SDC = {{SDC_2}}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { useStore } from '../stores';
  import { ref, computed } from 'vue';
  
  const { IF,DP,n,E,F,DNC,SDC_2 } = storeToRefs(useStore());
  const myStore = useStore();

  const Addnum = () => {
        myStore.addnum();
    }

  
  const current = ref(0);
  const steps = ref([
    {
      title: '估算方法介绍',
      content: '0-content',
    },
    {
      title: '测量DNC',
      content: '1-content',
    },
    {
      title: '测量并确定开发成本',
      content: '2-content',
    }
  ]);
  
  const content1 = ref(null);
  const content2 = ref(null);
  const content3 = ref(null);
  const content4 = ref(null);
  const content5 = ref(null);
  const content6 = ref(null);
  const contentRecord = ref([]);
  
  const addRecord = () => {
    if (current.value == 0) {
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
       contentRecord.value.push(content6);
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
  
  </script>
  
  <style scoped>
  #two2 {
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
  