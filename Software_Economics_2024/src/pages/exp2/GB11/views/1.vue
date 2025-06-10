<template>
    <div id="one1">
      <h2 style="text-align: center;">综合法</h2>
      <a-steps v-model:current="current" size="small">
        <a-step v-for="item in steps" :key="item.title" :title="item.title" />
      </a-steps>
      <div class="steps-content">
        <div class="exp-step-card" v-if="current == 0" ref="content1">
          <p class="content">软件开发成本的计算如下式所示：</p>
                <p class="content" style="text-align: center;">SDC = DHC + DNC + IHC + INC</p>
                <p class="content">式中：</p>
                <p class="content">SDC —— 软件开发成本，单位为<span style="font-weight: bold;">元</span>；</p>
                <p class="content">DHC —— 直接人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
                <p class="content">DNC —— 直接非人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
                <p class="content">IHC —— 间接人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
                <p class="content">INC —— 间接非人力成本，单位为<span style="font-weight: bold;">元</span>。</p>
        </div>
        <div class="exp-step-card" v-if="current == 1" ref="content2">
          <p class="content">DHC —— 直接人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <div style="margin-left: 48px">
          DHC值为：
          <a-input v-model:value="DHC" placeholder="DHC" style="width: 100px" />
        </div>
        </div>
        <div class="exp-step-card" v-if="current == 2" ref="content3">
          <p class="content">DNC —— 直接非人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <div style="margin-left: 48px">
          DNC值为：
          <a-input v-model:value="DNC" placeholder="DNC" style="width: 100px" />
        </div>
        </div>
        <div class="exp-step-card" v-if="current == 3" ref="content4">
          <p class="content">IHC —— 间接人力成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <div style="margin-left: 48px">
          IHC值为：
          <a-input v-model:value="IHC" placeholder="IHC" style="width: 100px" />
        </div>
        </div>
        <div class="exp-step-card" v-if="current == 4" ref="content5">
          <p class="content">INC —— 间接非人力成本，单位为<span style="font-weight: bold;">元</span>。</p>
          <div style="margin-left: 48px">
          INC值为：
          <a-input v-model:value="INC" placeholder="INC" style="width: 100px" />
        </div>
        </div>
        <div class="exp-step-card" v-if="current == 5" ref="content6">
          <p class="content">SDC —— 软件开发成本，单位为<span style="font-weight: bold;">元</span>；</p>
          <p class="content">SDC = DHC + DNC + IHC + INC</p>
          <p class="content">经过计算，SDC = {{SDC_1}}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { useStore } from '../stores/index';
  import { ref, computed } from 'vue';
  
  const { DHC, DNC, IHC,INC,SDC_1 } = storeToRefs(useStore());
  
  const current = ref(0);
  const steps = ref([
    {
      title: '估算方法介绍',
      content: '0-content',
    },
    {
      title: '测量DHC',
      content: '1-content',
    },
    {
      title: '测量DNC',
      content: '2-content',
    },
    {
      title: '测量IHC',
      content: '3-content'
    },
    {
      title: '测量INC',
      content: '4-content',
    },
    {
      title: '确定软件开发成本',
      content: '5-content',
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
  