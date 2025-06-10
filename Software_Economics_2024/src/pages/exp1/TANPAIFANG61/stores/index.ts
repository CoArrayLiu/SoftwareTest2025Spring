import { defineStore } from 'pinia';
import { ref } from 'vue';
import * as echarts from 'echarts';

export const useStore = defineStore('TANPAIFANG61', () => {
  const summary = ref<string>('');
  const nowsitua = ref("未开始") // 实验进行时长
  const mainumberarray = ref([]) // 买家数量信息
  const sellnumberarray = ref([]) // 卖家数量信息
  const finalprice = ref(0) // 最终价格
  const finalmainum = ref(0) // 最终买家交易量
  const finalsellnum = ref(0) // 最终卖家交易量
  const marketMaiData = ref([])
  const marketSellData = ref([])
  const result = ref([])
  const imgUrl1 = 'https://vse2024.oss-cn-shanghai.aliyuncs.com/61/486bc649fc904b6ba9776654eba2579d-1950389-gongxu.png' // 使用 ref 来存储图片 URL
      return {
          imgUrl1,summary,nowsitua,mainumberarray,sellnumberarray,finalprice,finalmainum,finalsellnum,marketMaiData,marketSellData,result
      };
    }
  );