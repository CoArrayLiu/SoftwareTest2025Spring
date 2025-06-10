<template>
  <h2 style="text-align: center">未调整功能点计算表</h2>
  <a-table
    :pagination="false"
    :columns="columns5"
    :data-source="tableData5"
    bordered
    size="middle"
    style="word-break: break-all"
  >
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.dataIndex === 'A'">
        <!-- <a-input v-model:value="record.A" style="width: 100px" /> -->
        {{ record.A }}
      </template>
      <template v-if="column.dataIndex === 'D'">
        <!-- <a-input v-model:value="record.D" style="width: 100px" /> -->
        {{ record.D }}
      </template>
      <template v-if="column.dataIndex === 'G'">
        <!-- <a-input v-model:value="record.G" style="width: 100px" /> -->
        {{ record.G }}
      </template>
      <template v-if="column.dataIndex === 'C'">
        {{ c(index) }}
      </template>
      <template v-if="column.dataIndex === 'F'">
        {{ f(index) }}
      </template>
      <template v-if="column.dataIndex === 'I'">
        {{ i(index) }}
      </template>
      <template v-if="column.dataIndex === 'unchanged'">
        {{ unchanged(index) }}
      </template>
      <template v-if="column.dataIndex === 'operation'">
        <a-button size="small" @click="modifyRow(record.component)">返回修改</a-button>
      </template>
    </template>
  </a-table>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { useStore } from '../stores';
  import { storeToRefs } from 'pinia';

  const { tableData5, c, f, i, unchanged } = storeToRefs(useStore());
  const columns5 = ref([
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
        {
          title: '中',
          align: 'center',
          children: [
            {
              title: '计数',
              align: 'center',
              children: [
                {
                  align: 'center',
                  dataIndex: 'D',
                  title: 'D',
                },
              ],
            },
            {
              title: '权重',
              align: 'center',
              children: [
                {
                  dataIndex: 'E',
                  align: 'center',
                  title: 'E',
                },
              ],
            },
            {
              title: '功能点数',
              align: 'center',
              children: [
                {
                  dataIndex: 'F',
                  title: 'F=D*E',
                  align: 'center',
                },
              ],
            },
          ],
        },
        {
          title: '高',
          align: 'center',
          children: [
            {
              title: '计数',
              align: 'center',
              children: [
                {
                  dataIndex: 'G',
                  align: 'center',
                  title: 'G',
                },
              ],
            },
            {
              title: '权重',
              align: 'center',
              children: [
                {
                  dataIndex: 'H',
                  align: 'center',
                  title: 'H',
                },
              ],
            },
            {
              title: '功能点数',
              align: 'center',
              children: [
                {
                  dataIndex: 'I',
                  title: 'I=G*H',
                  align: 'center',
                },
              ],
            },
          ],
        },
      ],
    },
    {
      title: '未调整功能点数',
      dataIndex: 'unchanged',
      key: 'unchanged',
      align: 'center',
    },
    {
      title: '操作',
      dataIndex: 'operation',
      key: 'operation',
      align: 'center'
    }
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
