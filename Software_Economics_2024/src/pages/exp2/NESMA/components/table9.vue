<template>
    <h2 style="text-align: center"> EI, EO 和 EQ 复杂度分类统计表</h2>
    <a-table
      :pagination="false"
      :columns="columns8"
      :data-source="tableData8"
      bordered
      size="middle"
      style="word-break: break-all"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'simple'">
          <a-input v-model:value="record.A" style="width: 100px" />
        </template>
        <template v-if="column.dataIndex === 'average'">
          <a-input v-model:value="record.D" style="width: 100px" />
        </template>
        <template v-if="column.dataIndex === 'complex'">
          <a-input v-model:value="record.G" style="width: 100px" />
        </template>
      </template>
    </a-table>
  </template>
  
  <script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import { useStore } from '../stores';
    import { storeToRefs } from 'pinia';
  
    const { tableData5 } = storeToRefs(useStore());
    // 获取要显示的行
    const tableData8 = ref([]);
    onMounted(()=>{
      for(let i = 0; i < tableData5.value.length; i++){
        if(tableData5.value[i].component === 'EI' || tableData5.value[i].component === 'EO' || tableData5.value[i].component === 'EQ'){
          tableData8.value.push(tableData5.value[i]);
        }
      }
    });
  
    const columns8 = ref([
      {
        title: '组件',
        dataIndex: 'component',
        key: 'component',
        align: 'center',
      },
      {
        title: '复杂度',
        children: [
          {
            title: '低',
            align: 'center',
            dataIndex: 'simple'
          },
          {
            title: '中',
            align: 'center',
            dataIndex: 'average'
          },
          {
            title: '高',
            align: 'center',
            dataIndex: 'complex'
          },
        ],
      }
    ]);
  </script>
  
  <style scoped>
    :deep(.ant-table .ant-table-thead > tr > th) {
      border-width: 1px;
    }
  </style>
  