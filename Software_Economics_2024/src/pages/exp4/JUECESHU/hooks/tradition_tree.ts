import axios from 'axios';
import { ref } from 'vue';
import { message } from 'ant-design-vue';

export default function () {
    const columns_1 = [
    {
        title: "销售状态",
        dataIndex: "state",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "概率",
        dataIndex: "probability",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "大规模投资方案损益值（万元/年）",
        dataIndex: "largeInvestment",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "小规模投资方案损益值（万元/年）",
        dataIndex:"smallInvestment",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        }
    ]
    const Q1_Table = [
        { key: 1,
          state: '销售好',
          probability: 0.7,
          largeInvestment: 100,
          smallInvestment: 60
        },
        { key: 2,
          state: '销售差',
          probability: 0.3,
          largeInvestment: -20,
          smallInvestment: 20
        }
    ];
    const columns_3 =[
        {
            title: "标段",
            dataIndex: "state",
            align: "center",  // 使文字居中
            customHeaderCell: () => ({ style: "font-size: 15px;"}),
            },
            {
              title: "投标规模",
              dataIndex: "scale",
              align: "center",  // 使文字居中
              customHeaderCell: () => ({ style: "font-size: 15px;"}),
            },
            {
            title: "效果",
            dataIndex: "result",
            align: "center",  // 使文字居中
            customHeaderCell: () => ({ style: "font-size: 15px;"}),
            },
            {
            title: "预期利润（万元）",
            dataIndex:"predictProfit",
            align: "center",  // 使文字居中
            customHeaderCell: () => ({ style: "font-size: 15px;"}),
            },
            {
              title: "概率",
              dataIndex: "probability",
              align: "center",  // 使文字居中
              customHeaderCell: () => ({ style: "font-size: 15px;"}),
            }
    ]
    const Q3_Table = [
        {
          key: "1",
          state: "甲标段",
          scale:"大",
          result: "赚",
          predictProfit: 120,
          probability: "0.25",
        },
        {
          key: "2",
          state: "甲标段",
          scale:"大",
          result: "一般",
          predictProfit:-10,
          probability: "0.35",
        },
        {
          key: "3",
          state: "甲标段",
          scale:"大",
          result: "赔",
          predictProfit: -50,
          probability: "0.4",
        },
        {
          key: "4",
          state: "甲标段",
          scale:"中",
          result: "赚",
          predictProfit: 60,
          probability: "0.4",
        },
        {
          key: "5",
          state: "甲标段",
          scale:"中",
          result: "一般",
          predictProfit: 10,
          probability: "0.2",
        },
        {
          key: "6",
          state: "甲标段",
          scale:"中",
          result: "赔",
          predictProfit: -30,
          probability: "0.4",
        },
        {
          key: "7",
          state: "甲标段",
          scale:"小",
          result: "赚",
          predictProfit: 30,
          probability: "0.7",
        },
        {
          key: "8",
          state: "甲标段",
          scale:"小",
          result: "一般",
          predictProfit: 10,
          probability: "0.2",
        },
        {
          key: "9",
          state: "甲标段",
          scale:"小",
          result: "赔",
          predictProfit: -10,
          probability: "0.1",
        },
        {
          key: "10",
          state: "乙标段",
          scale:"大",
          result: "赚",
          predictProfit: 150,
          probability: "0.5",
        },
        {
          key: "11",
          state: "乙标段",
          scale:"大",
          result: "赔",
          predictProfit: -80,
          probability: "0.5",
        },
        {
          key: "12",
          state: "乙标段",
          scale:"小",
          result: "赚",
          predictProfit: 50,
          probability: "0.9",
        },
        {
          key: "13",
          state: "乙标段",
          scale:"小",
          result: "赔",
          predictProfit: -10,
          probability: "0.1",
        },
        {
          key: "14",
          state: "丙标段",
          scale:"大",
          result: "赚",
          predictProfit: 250,
          probability: "0.3",
        },
        {
          key: "15",
          state: "丙标段",
          scale:"大",
          result: "赔",
          predictProfit: -100,
          probability: "0.7",
        },
        {
          key: "16",
          state: "丙标段",
          scale:"中",
          result: "赚",
          predictProfit: 120,
          probability: "0.5",
        },
        {
          key: "17",
          state: "丙标段",
          scale:"中",
          result: "赔",
          predictProfit: -50,
          probability: "0.5",
        },
        {
          key: "18",
          state: "丙标段",
          scale:"小",
          result: "赚",
          predictProfit: 80,
          probability: "0.6",
        },
        {
          key: "19",
          state: "丙标段",
          scale:"小",
          result: "赔",
          predictProfit: -30,
          probability: "0.4",
        }
    ]
    const columns_2 =[
      {
        title: "标段",
        dataIndex: "position",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "结果",
        dataIndex: "result",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "利润",
        dataIndex: "predictProfit",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        },
        {
        title: "概率",
        dataIndex:"probability",
        align: "center",  // 使文字居中
        customHeaderCell: () => ({ style: "font-size: 15px;"}),
        }
    ]
    const Q2_Table = [
      {
        key: "1",
        position: "道路（甲）",
        result: "中标",
        predictProfit: 100,
        probability:0.8,
      },
      {
        key: "2",
        position: "道路（甲）",
        result: "落标",
        predictProfit: -5,
        probability:0.2,
      },
      {
        key: "3",
        position: "厂房（乙）",
        result: "中标",
        predictProfit: 120,
        probability:0.6,
      },
      {
        key: "4",
        position: "厂房（乙）",
        result: "落标",
        predictProfit: -5,
        probability:0.4,
      },
      {
        key: "5",
        position: "办公楼（丙）",
        result: "中标",
        predictProfit: 180,
        probability:0.5,
      },
      {
        key: "6",
        position: "办公楼（丙）",
        result: "落标",
        predictProfit: -5,
        probability:0.5,
      },
    ]
  return {
    columns_1,
    Q1_Table,
    columns_2,
    Q2_Table,
    columns_3,
    Q3_Table,
  };
}
