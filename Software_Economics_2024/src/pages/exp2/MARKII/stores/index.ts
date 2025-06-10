// src/stores/tableDataStore.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'


export const useDataStore = defineStore('storageData', () => {
  const review = ref('');
  const checked = ref(false);
  const dataAdjust2 = ref([
      {index: '1', title: '数据通讯—使用通信设施传输数据和控制信息', grade: '0'},
      {index: '2', title: '分布式功能—应用分布在两个或更多的处理器上执行', grade: '0'},
      {index: '3', title: '性能—应用程序的响应/吞吐率', grade: '0'},
      {index: '4', title: '超负荷使用配置—运行应用程序的响应/吞吐率', grade: '0'},
      {index: '5', title: '事务到达率—高事务到达率会引发的除性能以外的问题', grade: '0'},
      {index: '6', title: '在线数据录入—用于输入的终端设备', grade: '0'},
      {index: '7', title: '为终端用户使用效率进行设计—在设计中考虑人的因素', grade: '0'},
      {index: '8', title: '在线更新—数据实时更新', grade: '0'},
      {index: '9', title: '处理的复杂性—除了简单实体数据转换之外的内部处理', grade: '0'},
      {index: '10', title: '代码复用性—代码被设计来与其他应用共享或重复使用', grade: '0'},
      {index: '11', title: '易安装—数据转换和易安装需要在设计时考虑', grade: '0'},
      {index: '12', title: '操作易用性—在设计时考虑方便的操作', grade: '0'},
      {index: '13', title: '多节点—应用程序在多个节点和/或多个组织中使用', grade: '0'},
      {index: '14', title: '支持变化—在设计时考虑支持未来的变化', grade: '0'},
      {index: '15', title: '其他应用程序的需求—接口', grade: '0'},
      {index: '16', title: '安全、隐私、可审计—保密/安全专有特性', grade: '0'},
      {index: '17', title: '用户培训需要—特定需求', grade: '0'},
      {index: '18', title: '第三方直接使用—应用程序的使用/关联度', grade: '0'},
      {index: '19', title: '文档化', grade: '0'},
  ]);
  const tableData = ref([
    {
      index: 'T01',
      name: '',
      type: undefined,
      inputNum: '',
      outputNum: '',
      entityNum: '',
      FP: ''
    }
  ]);
  const unadjusted = ref(0.0);
  const adjustedRate = ref(0.0);
  const adjusted = ref(0.0);

  return { review, tableData, unadjusted, adjustedRate, adjusted, dataAdjust2, checked}
},

{
  persist: true, 
}
);
