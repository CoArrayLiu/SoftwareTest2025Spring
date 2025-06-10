<template>
  <h2>四 、场景分析（进阶）</h2>
  <a-collapse default-active-key = "0" :bordered="false">
    <a-collapse-panel header = "1. 进阶计算(1)">
      <div class="content">
        某厂区建设项目，共分道路（甲）、厂房（乙）、办公楼（丙）3个标段进行招标建设，投标人只能选择其中一个标段参与投标。预期利润及概率见下表。若未中标，购买招标文件、图纸及人工费、利息支出合计为5000元。
      </div>
      <div>
        <el-table
          :data="Q2_Table" 
          :span-method="arraySpanMethodQ2"
          border
          class="custom-table"
          style="width: 100%; margin-top: 20px"
        >
          <el-table-column
            v-for="column in columns_2"
            :key="column.dataIndex"
            :label="column.title"
            :prop="column.dataIndex"
            :align="column.align"
            :custom-header-cell="column.customHeaderCell"
          />
        </el-table>
      </div>
      <div class ="content">
        <h3>
          数据计算：
        </h3>
        <p>
          参与甲标段投标期望利润： 
          <span class="blank">
            <input v-model="advancedQuestionBlanks[0].val" />
          </span>
          （万元）
        </p>
        <p>
          参与乙标段投标期望利润： 
          <span class="blank">
            <input v-model="advancedQuestionBlanks[1].val" />
          </span>
          （万元）
        </p>
        <p>
          参与丙标段投标期望利润： 
          <span class="blank">
            <input v-model="advancedQuestionBlanks[2].val" />
          </span>
          （万元）
        </p>
        <p>
          根据计算结果可知，选择投资
          <span class="blank">
            <select v-model="advancedQuestionBlanks[3].val">
              <option value="甲">甲</option>
              <option value="乙">乙</option>
            </select>
          </span>
          标段更优。
        </p>
      </div>
      <div>
        请填写下表，得到决策树结果（请用英文填写，若某项为空则输入-1）
      </div>
      <div>
        <a-button @click="tableClear('Q2')" style="margin-right: 20px;float: right" danger>清空</a-button>
          <a-table :columns="columns_4" :data-source="DecisionTreeStore.Q2traditionTreeTableAnswer" rowKey="key" :pagination="false" bordered>
            <template #bodyCell="{ column, index }">
              <template v-if="column.dataIndex==='num'">
                {{index + 1}}
              </template>
              <template v-else>
                <a-input v-model:value="DecisionTreeStore.Q2traditionTreeTableAnswer[index][column.dataIndex]"/>
              </template>
            </template>
          </a-table>
        </div>
        <div>
          <button @click = "insertTraditionTreePictureQ2()">
            生成传统决策树图片
          </button>
          <div class="imageContainer">
            <a-spin size="large" :spinning="imageMsg2"/>
            <a-image :src="DecisionTreeStore.Q2Image"/>
            </div>
  </div>
  </a-collapse-panel>
  <a-collapse-panel header = "2. 进阶计算(2)">
    <div class="content">
      几年后，项目计划扩张。只有第一阶段中标的项目才能扩大投资，预期利润及概率见下表
    </div>
    <div>
      <el-table
        :data="Q3_Table" 
        :span-method="arraySpanMethodQ3"
        border
        class="custom-table"
        style="width: 100%; margin-top: 20px"
      >
        <el-table-column
          v-for="column in columns_3"
          :key="column.dataIndex"
          :label="column.title"
          :prop="column.dataIndex"
          :align="column.align"
          :custom-header-cell="column.customHeaderCell"
        />
      </el-table>
    </div>
    <div class ="content">
      <h3>
        数据计算：
      </h3>
      <p>
        甲标段大规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[4].val" />
        </span>
        （万元）
      </p>
      <p>
        甲标段中规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[5].val" />
        </span>
        （万元）
      </p>
      <p>
        甲标段小规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[6].val" />
        </span>
        （万元）
      </p>
      <p>
        乙标段大规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[7].val" />
        </span>
        （万元）
      </p>
      <p>
        乙标段小规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[8].val" />
        </span>
        （万元）
      </p>
      <p>
        丙标段大规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[9].val" />
        </span>
        （万元）
      </p>
      <p>
        丙标段中规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[10].val" />
        </span>
        （万元）
      </p>
      <p>
        丙标段小规模投资期望利润： 
        <span class="blank">
          <input v-model="advancedQuestionBlanks[11].val" />
        </span>
        （万元）
      </p>
      <p>
        根据计算结果可知，选择
        <span class="blank">
          <select v-model="advancedQuestionBlanks[12].val" >
            <option value="甲标段大规模">甲标段大规模</option>
            <option value="甲标段中规模">甲标段中规模</option>
            <option value="甲标段小规模">甲标段小规模</option>
            <option value="乙标段大规模">乙标段大规模</option>
            <option value="乙标段小规模">乙标段小规模</option>
            <option value="丙标段大规模">丙标段大规模</option>
            <option value="丙标段中规模">丙标段中规模</option>
            <option value="丙标段小规模">丙标段小规模</option>
        </select>
        </span>
        投资方案更优。
      </p>
    </div>
    <div>
      请填写下表，得到决策树结果（请用英文填写，若某项为空则输入-1）
    </div>
    <div>
      <a-button @click="tableClear('Q3')" style="margin-right: 20px;float: right" danger>清空</a-button>
        <a-table :columns="columns_4" :data-source="DecisionTreeStore.Q3traditionTreeTableAnswer" rowKey="key" :pagination="false" bordered>
          <template #bodyCell="{ column, index }">
            <template v-if="column.dataIndex==='num'">
              {{index + 1}}
            </template>
            <template v-else>
              <a-input v-model:value="DecisionTreeStore.Q3traditionTreeTableAnswer[index][column.dataIndex]"/>
            </template>
          </template>
        </a-table>
      </div>
      <div>
  <button @click = "insertTraditionTreePictureQ3()">
    生成传统决策树图片
  </button>
  <div class="imageContainer">
    <a-spin size="large" :spinning="imageMsg3"/>
    <a-image :src="DecisionTreeStore.Q3Image"/>
    </div>
</div>
  </a-collapse-panel>
  </a-collapse>
 
</template>

<script setup lang="ts">
import { ElTable, ElTableColumn } from 'element-plus'; // 按需引入
import { generateTraditionaltree } from '../api/generateTree';
import traditionTree from '../hooks/tradition_tree'
import {ref, watch, onMounted} from 'vue'
import {useDecisionTreeStore} from '../stores';

let DecisionTreeStore = useDecisionTreeStore()
const tradition_tree = traditionTree();
let imageMsg2 = ref(false)
let imageMsg3 = ref(false)
const columns_2 = tradition_tree.columns_2
const columns_3 = tradition_tree.columns_3
let advancedQuestionBlanks = ref([
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

let spanArrQ2: number[] = []; // 每行合并数
let spanArrQ3: number[] = []; // 每行合并数
let pos = 0; // 角标索引
const Q2_Table = tradition_tree.Q2_Table
const Q3_Table = tradition_tree.Q3_Table
const columns_4 = [
  {
    title: "序号",
    dataIndex: "num",
    width: '5%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  },
  {
    title: "决策选项",
    dataIndex: "decisionOption",
    width: '16%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  },
  {
    title: "结果",
    dataIndex: "outcome",
    width: '16%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  },
  {
    title: "概率",
    dataIndex: "probability",
    width: '16%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  },
  {
    title: "收益值",
    dataIndex: "reward",
    width: '16%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  },
  {
    title: "子节点",
    dataIndex: "childNode",
    width: '16%',
    customHeaderCell: () => ({ style: "font-size: 15px;"}),
  }
]
function getSpanArrToQ3(data:any) {
  for (let i = 0; i < data.length; i++) {
    if (i === 0) {
      spanArrQ3.push(1);
      pos = 0;
    } else {
      // 判断当前行的某一个字段和上一行的某一个字段是否相同
      if (data[i].state === data[i - 1].state && data[i].scale === data[i - 1].scale) {
        spanArrQ3[pos] += 1;
        spanArrQ3.push(0); // 如果相同，隐藏该单元格
      } else {
        spanArrQ3.push(1); // 如果不同，正常显示
        pos = i; // 更新 pos 索引
      }
    }
  }
}
function getSpanArrToQ2(data:any) {
  for (let i = 0; i < data.length; i++) {
    if (i === 0) {
      spanArrQ2.push(1);
      pos = 0;
    } else {
      // 判断当前行的某一个字段和上一行的某一个字段是否相同
      if (data[i].position === data[i - 1].position) {
        spanArrQ2[pos] += 1;
        spanArrQ2.push(0); // 如果相同，隐藏该单元格
      } else {
        spanArrQ2.push(1); // 如果不同，正常显示
        pos = i; // 更新 pos 索引
      }
    }
  }
}
  // 合并单元格方法
const arraySpanMethodQ3 = ({ row, column, rowIndex, columnIndex }: any) => {
    // 竖向合并
  if (columnIndex === 0 || columnIndex === 1) {
      const _row = spanArrQ3[rowIndex]; // 获取当前行的合并数
      const _col = _row > 0 ? 1 : 0; // 如果大于0则显示，否则隐藏
    return {
      rowspan: _row, // 设置竖向合并行数
      colspan: _col, // 设置横向合并列数
    };
  }
  // 如果是其他列，正常显示
  return {
    rowspan: 1,
    colspan: 1,
  };
};
const arraySpanMethodQ2 = ({ row, column, rowIndex, columnIndex }: any) => {
    // 竖向合并
  if (columnIndex === 0) {
      const _row = spanArrQ2[rowIndex]; // 获取当前行的合并数
      const _col = _row > 0 ? 1 : 0; // 如果大于0则显示，否则隐藏
    return {
      rowspan: _row, // 设置竖向合并行数
      colspan: _col, // 设置横向合并列数
    };
  }
  // 如果是其他列，正常显示
  return {
    rowspan: 1,
    colspan: 1,
  };
};

function tableClear(question:string){
  if(question==="Q2"){
    DecisionTreeStore.Q2traditionTreeTableAnswer=[
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
    ]
    DecisionTreeStore.Q2Image = ""
  }
  else{
    DecisionTreeStore.Q3traditionTreeTableAnswer=[
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
      {
        decisionOption: null,
        outcome:null,
        probability:null,
        reward:null,
        childNode:null,
      },
    ]
    DecisionTreeStore.Q3Image = ""
  }
}

onMounted(() => {//计算合并
  getSpanArrToQ3(Q3_Table); // 计算合并数
  getSpanArrToQ2(Q2_Table)
});
watch(advancedQuestionBlanks, (newAnswer) => {//3的填空答案
  DecisionTreeStore.advancedQuestionBlankAnswer = newAnswer;
}, { deep: true });
watch(() => DecisionTreeStore.advancedQuestionBlankAnswer, (newAnswer) => {//3的填空答案反监视
  advancedQuestionBlanks.value = newAnswer;
}, { deep: true });
onMounted(() => {//初始化3的填空
  if (DecisionTreeStore.advancedQuestionBlankAnswer.length > 0) {
    advancedQuestionBlanks.value = [...DecisionTreeStore.advancedQuestionBlankAnswer];
  }
});

function insertTraditionTreePictureQ2(){
  updatedDataQ2.value=updateDataTypes(DecisionTreeStore.Q2traditionTreeTableAnswer)
  console.log(DecisionTreeStore.Q2traditionTreeTableAnswer)
  console.log(DecisionTreeStore.Q3traditionTreeTableAnswer)
  console.log(updatedDataQ2.value)
  console.log(updatedDataQ3.value)
  generatePicture2()
}
function insertTraditionTreePictureQ3(){
  updatedDataQ3.value=updateDataTypes(DecisionTreeStore.Q3traditionTreeTableAnswer)
  console.log(DecisionTreeStore.Q2traditionTreeTableAnswer)
  console.log(DecisionTreeStore.Q3traditionTreeTableAnswer)
  console.log(updatedDataQ2.value)
  console.log(updatedDataQ3.value)
  generatePicture3()
}
async function generatePicture2(){
  imageMsg2.value=true
  DecisionTreeStore.Q2Image=null
  generateTraditionaltree(updatedDataQ2).then((result)=> {
    imageMsg2.value = false
    DecisionTreeStore.Q2Image = result.imageUrl
  })
    .catch(()=>{
      DecisionTreeStore.Q2Image=""
    })
}
async function generatePicture3(){
  imageMsg3.value=true
  DecisionTreeStore.Q3Image=null
  generateTraditionaltree(updatedDataQ3).then((result)=> {
    imageMsg3.value = false
    DecisionTreeStore.Q3Image = result.imageUrl
  })
    .catch(()=>{
      DecisionTreeStore.Q3Image=""
    })
}
const updatedDataQ2 = ref(updateDataTypes(DecisionTreeStore.Q2traditionTreeTableAnswer));
const updatedDataQ3 = ref(updateDataTypes(DecisionTreeStore.Q3traditionTreeTableAnswer));
function updateDataTypes(data:any) {
  return data.map((item,index) => ({
    ...item,
    No: String(index + 1), // 顺序从1开始，转为字符串
    probability: convertToNumber(item.probability),
    reward: convertToNumber(item.reward)
  }));
}
function convertToNumber(value) {
  return Number(value);
}
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