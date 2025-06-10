<template>
  <h2 style="text-align: center">EI 复杂度认定表</h2>
  <a-table
    :pagination="false"
    :columns="columns2"
    :data-source="tableData2"
    bordered
    size="middle"
    style="word-break: break-all"
  />
</template>

<script setup lang="ts">
  import { ref } from 'vue';

  const columns2 = ref([
    {
      title: '引用的文件类型个数(FTR) ',
      dataIndex: 'FTR',
      key: 'type',
      align: 'center',
      width: 400,
    },
    {
      title: '数据元素类型(DET)',
      children: [
        {
          title: '1-4',
          dataIndex: 'left',
          key: 'type',
          align: 'center',
        },
        {
          title: '5-15',
          dataIndex: 'mide',
          key: 'type',
          align: 'center',
        },
        {
          title: '16及以上',
          dataIndex: 'right',
          key: 'type',
          align: 'center',
        },
      ],
    },
  ]);
  const tableData2 = ref([
    {
      FTR: '0~1',
      left: '低',
      mide: '低',
      right: '中',
    },
    {
      FTR: '2',
      left: '低',
      mide: '中',
      right: '高',
    },
    {
      FTR: '3及以上',
      left: '中',
      mide: '高',
      right: '高',
    },
  ]);
</script>

<style scoped>
  :deep(.ant-table .ant-table-thead > tr > th) {
    border-width: 1px;
  }
</style>
