import { defineStore } from 'pinia';
import { ref, reactive} from 'vue';
import * as echarts from 'echarts';

export const useStore = defineStore('exp7_DONGTAITOUZI', () => {
    const summary = ref<string>('');
    const nowsitua = ref("未开始") // 实验进行时长
  },
    {
      persist: true, // 使用Vue的persisted插件来持久化数据
    }
  );