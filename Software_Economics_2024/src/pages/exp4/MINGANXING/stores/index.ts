import { defineStore } from 'pinia';
import { ref } from 'vue'
export const useDataStore = defineStore('storeData',() => {
    const review =ref('')
    const spinning = ref<boolean>(false);
    const current = ref(0);
    const analysisData = ref(null);
    const series = ref(null);
    const names = ref(null);
    //选择分析变量
    const dataSource1 = ref([
    {
        key: '0',
        uncertainty: '敏感性因素1',
        value: 4000,
        change_minus15: 73,
        change_minus10: 82,
        change_minus5: 95,
        change_minus2: 98,
        changeBasicPlan: 100,
        change_2: 102,
        change_5: 105,
        change_10: 113,
        change_15: 122,
    },
    {
        key: '1',
        uncertainty: '敏感性因素2',
        value: 1200,
        change_minus15: 151,
        change_minus10: 138,
        change_minus5: 125,
        change_minus2: 113,
        changeBasicPlan: 100,
        change_2: 89,
        change_5: 55,
        change_10: 33,
        change_15: 16,
    },
    {
        key: '2',
        uncertainty: '敏感性因素3',
        value: 3500,
        change_minus15: 198,
        change_minus10: 172,
        change_minus5: 150,
        change_minus2: 123,
        changeBasicPlan: 100,
        change_2: 80,
        change_5: 55,
        change_10: 30,
        change_15: 8,
    },
    ]);
    
    const columns = ref([
            {
            title: '分析对象名',
            dataIndex: 'name',
            key: 'name',
            },
            {
            title: '分析对象值',
            dataIndex: 'value',
            key: 'value',
            },
        ]);
    const columns1 = ref([
            {
            title: '不确定因素',
            dataIndex: 'uncertainty',
            slots: {
                customRender: 'uncertainty',
            },
            },
            {
            title: '取值（万元）',
            dataIndex: 'value',
            slots: {
                customRender: 'value',
            },
            },
            {
            title: '操作',
            dataIndex: 'operation',
            slots: {
                customRender: 'operation',
            },
            },
        ]);
    const  columns2= ref([
            {
            title: '不确定因素',
            dataIndex: 'uncertainty',
            slots: {
                customRender: 'uncertainty',
            },
            },
            {
            title: '-15%',
            dataIndex: 'change_minus15',
            slots: {
                customRender: 'change_minus15',
            },
            },
            {
            title: '-10%',
            dataIndex: 'change_minus10',
            slots: {
                customRender: 'change_minus10',
            },
            },
            {
            title: '-5%',
            dataIndex: 'change_minus5',
            slots: {
                customRender: 'change_minus5',
            },
            },
            {
            title: '-2%',
            dataIndex: 'change_minus2',
            slots: {
                customRender: 'change_minus2',
            },
            },
            {
            title: '基本方案',
            dataIndex: 'changeBasicPlan',
            slots: {
                customRender: 'changeBasicPlan',
            },
            },
            {
            title: '2%',
            dataIndex: 'change_2',
            slots: {
                customRender: 'change_2',
            },
            },
            {
            title: '5%',
            dataIndex: 'change_5',
            slots: {
                customRender: 'change_5',
            },
            },
            {
            title: '10%',
            dataIndex: 'change_10',
            slots: {
                customRender: 'change_10',
            },
            },
            {
            title: '15%',
            dataIndex: 'change_15',
            slots: {
                customRender: 'change_15',
            },
            },
            {
            title: '操作',
            dataIndex: 'operation',
            slots: {
                customRender: 'operation',
            },
            },
        ]);
    const dataSource = ref([
            {
            key: '0',
            name: '收入',
            value: 100,
            },
        ]);
    return{review,spinning,current,analysisData,series,names,dataSource1,columns,columns1,columns2,dataSource}
},
{
    persist: true, 
}
);