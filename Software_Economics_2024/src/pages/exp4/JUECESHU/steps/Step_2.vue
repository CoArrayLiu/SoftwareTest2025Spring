<template>
  <h2>三、 场景分析（基础）</h2>
      <div class="content">
        <h3>
          问题描述
        </h3>
        <p>
          某投资者预投资兴建一工厂，建设方案有两种：
        </p>
        <ul>
          <li>
            大规模投资300万元;
          </li>
          <li>
            小规模投资160万元。
          </li>
        </ul>
        <p>
          两个方案的生产期均为10年,其每年的损益值及销售状态的规律见下表。
        </p>
        <p>
          试用决策树法选择最优方案。
        </p>
        <div>
          <a-table
            :columns="columns_1"
            :dataSource="Q1_Table"
            rowKey="key"
            :pagination="false"
            bordered
          />
        </div>
      </div>
      <br>
      <div class ="content">
        <h3>
          数据计算：
        </h3>
        <p>
          进行大规模投资每年收入期望（不包含成本）： 
          <span class="blank">
            <input v-model="baseQuestionBlanks[0].val" />
          </span>
          （万元）
        </p>
        <p>
          进行小规模投资每年收入期望（不包含成本）： 
          <span class="blank">
            <input v-model="baseQuestionBlanks[1].val" />
          </span>
          （万元）
        </p>
        <p>
          进行大规模投资10年总体收入期望 ：
          <span class="blank">
            <input v-model="baseQuestionBlanks[2].val" />
          </span>
          （万元）
        </p>
        <p>
          进行小规模投资10年总体收入期望 ：
          <span class="blank">
            <input v-model="baseQuestionBlanks[3].val" />
          </span>
          （万元）
        </p>
        <p>
          根据计算结果可知，选择
          <span class="blank">
            <select v-model="baseQuestionBlanks[4].val">
              <option value="大规模投资方案">大规模投资方案</option>
              <option value="小规模投资方案">小规模投资方案</option>
            </select>
          </span>
          更优。
        </p>
      </div>
      <div>
        下图为决策表以及生成对应的决策树图片（请用英文填写，若某项为空则输入-1）
      </div>
      <a-table :columns="columns_5" :data-source="Q1traditionTreeTable" rowKey="key" :pagination="false" bordered>
            <template #bodyCell="{ column, index }">
              <template v-if="column.dataIndex==='num'">
                {{index + 1}}
              </template>
              <template v-else>
                {{Q1traditionTreeTable[index][column.dataIndex]}}
              </template>
            </template>
          </a-table>
          <div>
            生成的决策树图片
          </div>
          <div class="imageContainer">
              <img src="..\assets\tradition_tree_1.png" alt="">
          </div>
</template>

<script setup lang="ts">
  import {ref, watch, onMounted} from 'vue'
  import traditionTree from '../hooks/tradition_tree'

  import { useDecisionTreeStore} from '../stores';

  const tradition_tree = traditionTree();
  let DecisionTreeStore = useDecisionTreeStore()
  let baseQuestionBlanks = ref([
    {
      val:"",
    },
    {
      val:"",
    },
    {
      val:"",
    },
    {
      val:"",
    },
    {
      val:"",
    },
    ]);

  const columns_1 = tradition_tree.columns_1
  const Q1_Table = tradition_tree.Q1_Table
  const columns_5 = [
    {
      title: "序号",
      dataIndex: "num",
      width: '5%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "决策选项",
      dataIndex: "decisionOption",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "结果",
      dataIndex: "outcome",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "概率",
      dataIndex: "probability",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "收益值",
      dataIndex: "reward",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "子节点",
      dataIndex: "childNode",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
  ]
  const Q1traditionTreeTable = ref(
      [
        //一级
        {
          decisionOption:"invest",
          outcome:"-1",
          probability:-1,
          reward:-1,
          childNode:"large_scale",
        },
        {
          decisionOption:"invest",
          outcome:"-1",
          probability:-1,
          reward:-1,
          childNode:"small_scale",
        },
        //二级
        {
          decisionOption:"large_scale",
          outcome:"good_sale",
          probability:0.7,
          reward:100,
          childNode:"-1",
        },
        {
          decisionOption:"large_scale",
          outcome:"bad_sale",
          probability:0.3,
          reward:-20,
          childNode:"-1",
        },
        {
          decisionOption:"small_scale",
          outcome:"good_sale",
          probability:0.7,
          reward:60,
          childNode:"-1",
        },
        {
          decisionOption:"small_scale",
          outcome:"bad_sale",
          probability:0.3,
          reward:20,
          childNode:"-1",
        }
    ]
  )//答案2决策树表
  watch(baseQuestionBlanks, (newAnswer) => {//2的填空答案
    DecisionTreeStore.baseQuestionBlankAnswer = newAnswer;
  },
    {
      deep: true
    });
  watch(() => DecisionTreeStore.baseQuestionBlankAnswer, (newAnswer) => {//2的填空答案反监视
    baseQuestionBlanks.value = newAnswer;
  },
    {
      deep: true
    });
  onMounted(() => {//初始化2的填空
    if (DecisionTreeStore.baseQuestionBlankAnswer.length > 0) {
      baseQuestionBlanks.value = [...DecisionTreeStore.baseQuestionBlankAnswer];
    }
  });

</script>

<style scoped>
  .content{
    text-indent: 2em;
    margin-left: 40px;
    margin-right: 20px;
  }
  
  .imageContainer{
    text-align: center;
  }

  h3,h4{
    font-weight: bold;
  }

  a{
    margin-right: 20px;
  }

  .blank {
  text-decoration: underline;
  padding: 0 4px;
  }

  .imageContainer{
    text-align: center;
  }
  
  input {
  border: none;
  border-bottom: 2px solid black; /* 只显示下划线 */
  outline: none;
  background-color: #dddddd00;
  font-size: 14px;
  padding: 2px 0;
  text-align: center;
  font-weight: bold; /* 设置字体加粗 */
  }
  
  input[type="number"]::-webkit-outer-spin-button,
  input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  select{
  border: none;
  border-bottom: 2px solid black; /* 只显示下划线 */
  outline: none;
  background-color: #dddddd00;
  font-size: 14px;
  padding: 2px 0;
  text-align: center;
  }

.blank select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 120px; /* 设置宽度 */
  padding-right: 0;
}

.blank select::-ms-expand {
  display: none;
}

/* 自定义表格样式 */
.custom-table {
  width: 100%;
  margin: 0 auto;
  background-color: #ffffff; /* 设置背景为白色 */
  border: 1px solid #000000; /* 设置表格边框 */
}

:deep(.el-table th){
    background-color: rgba(62, 62, 62, 0.219);
    font-size: 15px;
    padding: 10px;
    text-align: center; /* 设置字体居中 */
    border: 1px solid #000000; /* 设置左右边框 */
}
:deep(.el-table td){
    background-color: rgba(255, 255, 255, 0.219);
    font-size: 15px;
    padding: 10px;
    text-align: center; /* 设置字体居中 */
    border: 1px solid #000000; /* 设置左右边框 */
}
</style>