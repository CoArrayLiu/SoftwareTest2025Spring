import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useStore = defineStore(
    'exp8_MENGTEKALUO',
    () => {
      // const summary = ref<string>('');
      // const maxSum = ref(0);
      // const minSum = ref(0);
      // const percent15 = ref(0);
      // const percent30 = ref(0);

      // const table1 = ref([
      //   {
      //     stage:"设计",
      //     optimistic:"8",
      //     probably:"14",
      //     pessimistic:"20",
      //     average:"14",
      //     sd:"2",
      //     distribution:"normal",
      //     key:"1"
      //   },
      //   {
      //     stage:"开发",
      //     optimistic:"14",
      //     probably:"23",
      //     pessimistic:"32",
      //     average:"23",
      //     sd:"3",
      //     distribution:"normal",
      //     key:"2"
      //   },
      //   {
      //     stage:"测试",
      //     optimistic:"10",
      //     probably:"22",
      //     pessimistic:"34",
      //     average:"22",
      //     sd:"4",
      //     distribution:"normal",
      //     key:"3"
      //   }
      // ])

      const maxSum = ref(0)
      const minSum = ref(0)
      const percent30 = ref<number>(1)
      const percent15 = ref<number>(1)
      const tableData1 = ref([
          {
              stage: '设计',
              optimistic: '8',
              probably: '14',
              pessimistic: '20',
              average: '14',
              sd: '2',
              distribution: 'normal',
              key: '1',
          },
          {
              stage: '开发',
              optimistic: '14',
              probably: '23',
              pessimistic: '32',
              average: '23',
              sd: '3',
              distribution: 'normal',
              key: '2',
          },
          {
              stage: '测试',
              optimistic: '10',
              probably: '22',
              pessimistic: '34',
              average: '22',
              sd: '4',
              distribution: 'normal',
              key: '3',
          },
      ])
      const tableData2 = ref([])
      const sumStats = ref([])
      const summary = ref<string>("")

      const clear = () => {
        maxSum.value = undefined
        minSum.value = undefined
        percent30.value = 1
        percent15.value = 1
        tableData1.value = [
            {
                stage: '设计',
                optimistic: '8',
                probably: '14',
                pessimistic: '20',
                average: '14',
                sd: '2',
                distribution: 'normal',
                key: '1',
            },
            {
                stage: '开发',
                optimistic: '14',
                probably: '23',
                pessimistic: '32',
                average: '23',
                sd: '3',
                distribution: 'normal',
                key: '2',
            },
            {
                stage: '测试',
                optimistic: '10',
                probably: '22',
                pessimistic: '34',
                average: '22',
                sd: '4',
                distribution: 'normal',
                key: '3',
            },
      ]
      tableData2.value = []
      sumStats.value = []
      summary.value = ""
      }
      
      return {
        maxSum, minSum, percent30, percent15, tableData1, tableData2, sumStats, summary, clear
      };
    },
    {
      persist: true, // 使用Vue的persisted插件来持久化数据
    }
  );