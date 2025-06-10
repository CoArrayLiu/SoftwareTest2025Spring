import { defineStore } from 'pinia'
import { computed, defineComponent, reactive, ref, toRefs, watch } from 'vue';
import type { Ref, UnwrapRef } from 'vue';

type Key = string | number;
interface DataItem {
    key: string;
    state: string;
    cost1: number;
    cost2: number;
    cost3: number;
    cost4: number;
}

export const HurwiczData = defineStore('storageHurwicz', () => {
    const bestplan = ref<string>('');
    const factor = ref<number>(0.3);
    const reflection = ref<string>('');
    const plantext = ref<string>('因为加权后结果的最大值是____，所以最佳方案是_____。');

    const table1: Ref<DataItem[]> = ref([
        {
            key: '1',
            state: '01',
            cost1: -900,
            cost2: -550,
            cost3: -200,
            cost4: -100,

        },
        {
            key: '2',
            state: '02',
            cost1: 300,
            cost2: 400,
            cost3: 250,
            cost4: 200,

        },
        {
            key: '3',
            state: '03',
            cost1: -200,
            cost2: 50,
            cost3: 300,
            cost4: 150,

        },
        {
            key: '4',
            state: '04',
            cost1: 1800,
            cost2: 1500,
            cost3: 1200,
            cost4: 900,

        },
        {
            key: '5',
            state: '05',
            cost1: 700,
            cost2: 500,
            cost3: 1000,
            cost4: 800,

        },

    ]);

    const count = computed(() => table1.value.length + 1);
    return {
        reflection,
        bestplan,
        plantext,
        table1,
        count,
        factor,
    };
},
{
    persist: true,
}
);

export const LaplaceData = defineStore('storageLaplace', () => {
    const bestplan = ref<string>('');
    const reflection = ref<string>('');
    const plantext = ref<string>('因为期望值的最大值是____，所以最佳方案是_____。');
    const table2: Ref<DataItem[]> = ref([
        {
            key: '1',
            state: '01',
            cost1: -900,
            cost2: -550,
            cost3: -200,
            cost4: -100,

        },
        {
            key: '2',
            state: '02',
            cost1: 300,
            cost2: 400,
            cost3: 250,
            cost4: 200,

        },
        {
            key: '3',
            state: '03',
            cost1: -200,
            cost2: 50,
            cost3: 300,
            cost4: 150,

        },
        {
            key: '4',
            state: '04',
            cost1: 1800,
            cost2: 1500,
            cost3: 1200,
            cost4: 900,

        },
        {
            key: '5',
            state: '05',
            cost1: 700,
            cost2: 500,
            cost3: 1000,
            cost4: 800,

        },
    ]);
    
    const count = computed(() => table2.value.length + 1);

    return {
        reflection,
        bestplan,
        plantext,
        table2,
        count,
    };
},
{
    persist: true,
}
);

export const MaxmaxData = defineStore('storageMaxmax', () => {
    const bestplan = ref<string>('');
    const reflection = ref<string>('');
    const plantext =ref<string>('因为所有方案最大值中的最大值是____，所以最佳方案是_____。');
    
    const table3: Ref<DataItem[]> = ref([
        {
            key: '1',
            state: '01',
            cost1: -900,
            cost2: -550,
            cost3: -200,
            cost4: -100,
        },
        {
            key: '2',
            state: '02',
            cost1: 300,
            cost2: 400,
            cost3: 250,
            cost4: 200,

        },
        {
            key: '3',
            state: '03',
            cost1: -200,
            cost2: 50,
            cost3: 300,
            cost4: 150,

        },
        {
            key: '4',
            state: '04',
            cost1: 1800,
            cost2: 1500,
            cost3: 1200,
            cost4: 900,

        },
        {
            key: '5',
            state: '05',
            cost1: 700,
            cost2: 500,
            cost3: 1000,
            cost4: 800,

        },
        // {
        //     key: '6',
        //     state: '加权后预期',
        //     cost1: 0,
        //     cost2: 0,
        //     cost3: 0,
        //     cost4: 0,
        // },
    ]);
    const count = computed(() => table3.value.length + 1);

    return{
        bestplan,
        reflection,
        plantext,
        table3,
        count,
    }
},
{
    persist: true,
}
)

export const MaxminData = defineStore('storageMaxmin', () => {
    const bestplan = ref<string>('');
    const reflection = ref<string>('');
    const plantext = ref<string>('因为所有方案的最小值的最大值是____，所以最佳方案是_____。');
    
    const table4: Ref<DataItem[]> = ref([
        {
          key: '1',
          state: '01',
          cost1: -900,
          cost2: -550,
          cost3: -200,
          cost4: -100,
  
        },
        {
          key: '2',
          state: '02',
          cost1: 300,
          cost2: 400,
          cost3: 250,
          cost4: 200,
  
        },
        {
          key: '3',
          state: '03',
          cost1: -200,
          cost2: 50,
          cost3: 300,
          cost4: 150,
  
        },
        {
          key: '4',
          state: '04',
          cost1: 1800,
          cost2: 1500,
          cost3: 1200,
          cost4: 900,
  
        },
        {
          key: '5',
          state: '05',
          cost1: 700,
          cost2: 500,
          cost3: 1000,
          cost4: 800,
  
        },
        // {
        //     key: '6',
        //     state: '加权后预期',
        //     cost1: 0,
        //     cost2: 0,
        //     cost3: 0,
        //     cost4: 0,
  
        // },
  
    ]);
    const count = computed(() => table4.value.length + 1);

    return{
        bestplan,
        reflection,
        plantext,
        table4,
        count,
    };
},
{
    persist: true,
}
)

export const RegretData = defineStore('storageRegret', () => {
    const bestplan = ref<string>('');
    const reflection = ref<string>('');
    const plantext =ref<string>('因为所有方案的最大后悔值中的最小值是____，所以最佳方案是_____。');

    const table5: Ref<DataItem[]> = ref([
        {
            key: '1',
            state: '01',
            cost1: -900,
            cost2: -550,
            cost3: -200,
            cost4: -100,

        },
        {
            key: '2',
            state: '02',
            cost1: 300,
            cost2: 400,
            cost3: 250,
            cost4: 200,

        },
        {
            key: '3',
            state: '03',
            cost1: -200,
            cost2: 50,
            cost3: 300,
            cost4: 150,

        },
        {
            key: '4',
            state: '04',
            cost1: 1800,
            cost2: 1500,
            cost3: 1200,
            cost4: 900,

        },
        {
            key: '5',
            state: '05',
            cost1: 700,
            cost2: 500,
            cost3: 1000,
            cost4: 800,

        },
    ]);
    const count = computed(() => table5.value.length + 1);

    return{
        bestplan,
        reflection,
        plantext,
        table5,
        count,
    }
},
{
    persist: true,
}
)