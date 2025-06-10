import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useStore = defineStore(
  'exp0_template',
  () => {
    // 这里定义需要持久化保存的数据

    const clear = ()=>{
        
    } 
    // 这里的数据将会被 `pinia.ts` 中的 `createPinia()` 返回的 `pinia` 实例管理
    return { clear };
  },
  {
    persist: true,  
  }
);
