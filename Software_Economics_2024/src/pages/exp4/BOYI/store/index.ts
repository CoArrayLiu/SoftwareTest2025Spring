import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useStore = defineStore(
    'exp8_boyi',
    () => {
        // 这里定义需要持久化保存的数据
        const role = ref('')
        const matchUser = ref('')
        const matchInfo = ref()
        const Role=ref('')
        const matchUser1 = ref('')
        const matchInfo1 = ref()
        const acceptPrincipal = ref()

        const strive = ref()
        const bug = ref()
        const principalPrice = ref<number>(0)
        const agentPrice = ref<number>(0)
        const situation=ref();
        const BadSituationHighPrice=ref<number>();
        const BadSituationLowPrice=ref<number>();
        const GoodSituationValue= ref<number>()
        const GoodSituationHighPrice=ref<number>()
        const GoodSituationLowPrice=ref<number>()
        const BadSituationValue = ref<number>();
        const Lowerprice=ref<number>()
        const Higerprice=ref<number>()
        const sale=ref<number>()
        const price=ref<number>()
        const summary = ref('')
        const summary1 = ref('')
        const clear = ()=>{

        }
        // 这里的数据将会被 `pinia.ts` 中的 `createPinia()` 返回的 `pinia` 实例管理
        return { clear,role,matchInfo,matchUser,Role,matchUser1, matchInfo1,
            acceptPrincipal,strive,agentPrice,principalPrice,bug, situation,
            BadSituationHighPrice,BadSituationLowPrice,GoodSituationValue,
            GoodSituationHighPrice,GoodSituationLowPrice,BadSituationValue,
                Lowerprice,Higerprice,sale,price,summary,summary1
        };
    },
    {
        persist: true,
    }
);