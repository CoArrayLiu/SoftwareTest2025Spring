import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import dayjs, { Dayjs } from 'dayjs';
import 'moment/locale/zh-cn';

export const useStore = defineStore('exp8_YINKUIPINGHENG', () => {
  const spinning = ref<boolean>(false);
  const phase = ref('');
  const month = ref<Dayjs>();
  const fixed_cost = ref(0);
  const single_price = ref(0);
  const sales = ref(0);
  const variable_cost = ref(0);
  const safety_rate = ref(0); // 安全边际率
  const summary = ref(''); // 实验心得
  const tableData = ref([]);

  const avg_safety_rate = computed(() => {
    let temp = 0;
    if (tableData.value.length >= 1) {
      tableData.value.forEach(item => {
        const percentage = parseFloat(item.safety_rate.replace("%", "")) / 100;
        temp += percentage;
      });
      const result = temp / tableData.value.length;
      return (result * 100).toFixed(2) + "%";
    } else {
      return "0";
    }
  }); // 平均安全边际率

  const clear = () => {
    month.value = null;
    phase.value = '';
    fixed_cost.value = 0;
    single_price.value = 0;
    sales.value = 0;
    variable_cost.value = 0;
    const tableData = ref([]);
  };

  // const onDelete = (key: string) => {
  //   tableData.value.splice(parseInt(key) - 1, 1);
  // };
  const onDelete = (key: string) => {
    const index = parseInt(key) - 1;
    tableData.value = tableData.value.filter((_, i) => i !== index);
    
    // 更新 key 值，使其从 1 开始连续递增
    tableData.value.forEach((item, index) => {
      item.key = index + 1;
    });
  };

  return {
    spinning,
    phase,
    month,
    fixed_cost,
    single_price,
    sales,
    variable_cost,
    safety_rate, // 安全边际率
    summary, // 实验心得
    avg_safety_rate, // 平均安全边际率
    tableData,
    clear,
    onDelete
  };
}, {
  persist: true, // 使用Vue的persisted插件来持久化数据
});