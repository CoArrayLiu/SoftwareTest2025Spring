import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useStore = defineStore(
  'exp1_GB11',
  () => {
    const selectFormula = ref("No1");
    const experimentSummary = ref<string>('');
    // 1
    const DHC = ref(0);
    const DNC = ref(0);
    const IHC = ref(0);
    const INC = ref(0);
    // 2
    const IF = ref(0);
    const DP = ref(0);
    const n = ref(1);
    const E = ref(0);
    let SumofProduct = 0;
    // 3
    const P = ref(0);
    const S = ref(0);

    const categories = ref<{ E: number, IF: number, DP: number, F: number }[]>([]);

    const SDC_1 = computed(
      () => (DHC.value ? Number(DHC.value) : 0) + (DNC.value ? Number(DNC.value) : 0) + (IHC.value ? Number(IHC.value) : 0) + (INC.value ? Number(INC.value) : 0)
    );

    const F = computed(
      () => Number(IF.value) * (1 + Number(DP.value))
    );

    const SDC_2 = computed(() => {
      const sum = categories.value.reduce((acc, item) => acc + item.E * item.F, 0);
      return sum + (DNC.value ? Number(DNC.value) : 0);
    });

    const SDC_3 = computed(
      () => (P.value ? Number(P.value) : 0) * (S.value ? Number(S.value) : 0) + (DNC.value ? Number(DNC.value) : 0)
    );

    const clear = () => {
      DHC.value = 0;
      DNC.value = 0;
      IHC.value = 0;
      INC.value = 0;
      IF.value = 0;
      DP.value = 0;
      n.value = 1;
      E.value = 0;
      P.value = 0;
      S.value = 0;
      experimentSummary.value = '';
      categories.value = [];
    }

    const addnum = () => {
      categories.value.push({
        E: Number(E.value) || 0,
        IF: Number(IF.value) || 0,
        DP: Number(DP.value) || 0,
        F: Number(IF.value) * (1 + Number(DP.value)) || 0
      });
      n.value++;
      E.value = 0;
      IF.value = 0;
      DP.value = 0;
    }

    return {
      DHC, DNC, IHC, INC, IF, DP, n, E, P, S, F, selectFormula, experimentSummary, SDC_1, SDC_2, SDC_3, clear, addnum
    };
  },
  {
    persist: true, // 使用Vue的persisted插件来持久化数据
  }
);