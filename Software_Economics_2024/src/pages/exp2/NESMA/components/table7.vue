<template>
  <h2 style="text-align: center">未调整功能点计算表</h2>
  <a-table :pagination="false" :columns="columns7" :data-source="tableData7" bordered size="middle"
    style="word-break: break-all">
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.dataIndex === 'A'">
        <!-- <a-input v-model:value="record.A" style="width: 100px" /> -->
        {{ record.A }}
      </template>
      <template v-if="column.dataIndex === 'C'">
        {{ C(index) }}
      </template>
      <template v-if="column.dataIndex === 'unchanged'">
        {{ C(index) }}
      </template>
      <template v-if="column.dataIndex === 'operations'">
        <a-button size="small" @click="modifyRow(record.component)">返回修改</a-button>
      </template>
    </template>
  </a-table>
</template>

<script setup lang="ts">
import { ref  } from 'vue';
import { useStore } from '../stores';
import { storeToRefs } from 'pinia';

const { tableData7, C } = storeToRefs(useStore());
const columns7 = ref([
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
        title: '低/中',
        align: 'center',
        children: [
          {
            title: '计数',
            align: 'center',
            children: [
              {
                title: 'A',
                dataIndex: 'A',
                align: 'center',
              },
            ],
          },
          {
            title: '权重',
            align: 'center',
            children: [
              {
                title: 'B',
                dataIndex: 'B',
                align: 'center',
              },
            ],
          },
          {
            title: '功能点数',
            align: 'center',
            children: [
              {
                title: 'C=A*B',
                align: 'center',
                dataIndex: 'C',
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: '操作',
    dataIndex: 'operations',
    key: 'operations',
    align: 'center'
  },
  {
    title: '未调整功能点数',
    dataIndex: 'unchanged',
    key: 'unchanged',
    align: 'center',
  },
]);

// 通知父组件跳转步骤
const emit = defineEmits(['stepLink'])

const modifyRow = (element)=>{
  emit('stepLink', element);
};

</script>

<style scoped>
:deep(.ant-table .ant-table-thead > tr > th) {
  border-width: 1px;
}
</style>
