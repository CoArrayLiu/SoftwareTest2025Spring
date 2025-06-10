import { defineStore } from 'pinia';
import { ref, reactive} from 'vue';
import { cloneDeep } from 'lodash-es';

interface DataItem {
  key: string;
  period: string;
  cashflow: number;
  cashflow_discounted: number;
  attentioninfo: string;
}

export const useStore = defineStore('exp7_JINXIANZHI', () => {
  const spinning = ref(false);
  const periodvalue = ref(5);
  const discount_rate1 = ref(10);
  const ifshow_npv = ref(false);
  const ifshow_irr = ref(false);
  const npv = ref(0);
  const irr = ref(0);
  const dataSource = ref<DataItem[]>([]);
  const editableData = reactive<Record<string, DataItem>>({});
  const summary = ref<string>('');

  const clear = () => {
    spinning.value = false;
    periodvalue.value = 5;
    discount_rate1.value = 10;
    npv.value = 0;
    irr.value = 0;
    refreshData();
    summary.value = '';
}

  const refreshData = () => {
    dataSource.value = Array.from({ length: periodvalue.value }, (_, i) => ({
      key: i.toString(),
      period: `${i+1}`,
      cashflow: 0,
      cashflow_discounted: 0,
      attentioninfo: '为符合标准现金流要求，请注意时间点0时的现金流输入需要为负，其余时间点现金流输入需要为非负数',
    }));
  };

  const edit = (key) => {
    const item = dataSource.value.find(it => it.key === key);
    if (item) {
      editableData[key] = cloneDeep(item);
    }
  };
  
  const save = (key) => {
    const item = dataSource.value.find(it => it.key === key);
    if (item && editableData[key]) {
      Object.assign(item, editableData[key]);
      delete editableData[key];
    }
  };
  
  const cancel = (key) => {
    const item = dataSource.value.find(it => it.key === key);
    if (item) {
      delete editableData[key];
    }
  };

  const calculateNPV = () => {
    console.log("Calculating NPV and IRR...");
    // 首先计算 NPV
    let npvSum = dataSource.value.reduce((sum, item, index) => {
        const discounted = item.cashflow / Math.pow(1 + discount_rate1.value / 100, index);
        item.cashflow_discounted = Math.round(discounted * 100) / 100;
        return sum + discounted;
    }, 0);
    npv.value = npvSum;

    // 接下来计算 IRR
    const cashflows = dataSource.value.map(item => item.cashflow);
    const maxIter = 1000;  // 最大迭代次数
    const tol = 0.0001;    // 容忍误差
    let lower = -0.9999;   // 下限，不能是 -1 因为会导致除以零的错误
    let upper = 5;         // 上限
    let mid = 0;
    let npvMid = 0;
    let iter = 0;

    while (iter < maxIter) {
        mid = (lower + upper) / 2;
        npvMid = cashflows.reduce((acc, cf, i) => acc + cf / Math.pow(1 + mid, i), 0);

        if (Math.abs(npvMid) < tol) {
            break; // 如果达到容忍误差则结束循环
        } else if (npvMid > 0) {
            lower = mid; // 如果计算出的 NPV 大于 0，调整下限
        } else {
            upper = mid; // 如果计算出的 NPV 小于 0，调整上限
        }
        iter++;
    }

    if (isFinite(mid)) {
        irr.value = mid * 100; // 如果找到了合适的 IRR，转换为百分比形式
    } else {
        irr.value = NaN; // 如果没有合适的解，设为 NaN
        alert('IRR 计算出现问题，请尝试使用其他投资评估指标，如 MIRR 或 NPV。');
    }
};

  return { spinning, ifshow_irr, ifshow_npv, periodvalue, discount_rate1, npv, irr, dataSource, editableData, summary, edit, save, cancel, calculateNPV, refreshData,clear };
}, {
  persist: true
});
