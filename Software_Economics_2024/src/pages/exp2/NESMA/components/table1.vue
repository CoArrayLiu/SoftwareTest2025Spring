<template>
  <h2 style="text-align: center">ILF 和 EIF 数据复杂度认定表</h2>
  <a-table
    :pagination="false"
    :columns="columns1"
    :data-source="tableData1"
    bordered
    size="middle"
    style="word-break: break-all"
  />
</template>

<script setup lang="ts">
  import { ref } from 'vue';

  const columns1 = ref([
    {
      title: '记录元素类型(RET) ',
      dataIndex: 'RET',
      key: 'type',
      align: 'center',
      width: 400,
    },
    {
      title: '数据元素类型(DET)',
      children: [
        {
          title: '1-19',
          dataIndex: 'left',
          key: '1-19',
          align: 'center',
        },
        {
          title: '20-50',
          dataIndex: 'mide',
          key: '20-50',
          align: 'center',
        },
        {
          title: '51及以上',
          key: '50',
          dataIndex: 'right',
          align: 'center',
        },
      ],
    },
  ]);

  const tableData1 = ref([
    {
      RET: '1',
      left: '低',
      mide: '低',
      right: '中',
    },
    {
      RET: '2~5',
      left: '低',
      mide: '中',
      right: '高',
    },
    {
      RET: '6及以上',
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
