import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface Column {
  title: string;
  dataIndex: string;
  align: string;
  customHeaderCell: () => { style: string };
}

export interface traditionTableData {
  key: string;
  state: string;
  probability: string;
  result: string;
  predictProfit: number;
  predictProfitProbability: number;
}

export interface Q2traditionTableData{
  decisionOption:string;
  outcome:string;
  probability:string;
  reward:string;
  childNode:string;
}

export interface compareTableData{
  type:string,
  method:string,
  continuous:string,
  missing:string,
}

export interface MLTreeTableData{
  type: string,
  splitter: string,
  max_depth: string,
  proportion: string,
  depth: string,
  accuracy: string,
  time: string,
  image: string,
}


export const useDecisionTreeStore = defineStore(
  'exp4_DecisionTree',
  () => {
    //填空
    const principleBlankAnswer =ref([
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
    ])

    const baseQuestionBlankAnswer =ref([
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
    ])

    const advancedQuestionBlankAnswer =ref([
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
    ])

    const Q2traditionTreeTableAnswer = ref<Array<Q2traditionTableData>>([
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"A",
      },
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"B",
      },
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"C",
      },
      {
        decisionOption: "A",
        outcome:"success",
        probability:"0.8",
        reward:"100",
        childNode:"-1",
      },
      {
        decisionOption: "A",
        outcome:"fail",
        probability:"0.2",
        reward:"-5",
        childNode:"-1",
      },
      {
        decisionOption: "B",
        outcome:"success",
        probability:"0.6",
        reward:"120",
        childNode:"-1",
      },
      {
        decisionOption: "B",
        outcome:"fail",
        probability:"0.4",
        reward:"-5",
        childNode:"-1",
      },
      {
        decisionOption: "C",
        outcome:"success",
        probability:"0.5",
        reward:"180",
        childNode:"-1",
      },
      {
        decisionOption: "C",
        outcome:"fail",
        probability:"0.5",
        reward:"-5",
        childNode:"-1",
      },
    ])//答案2决策树表

    const Q2Image = ref("")

    const Q3traditionTreeTableAnswer = ref<Array<Q2traditionTableData>>([
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"A",
      },
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"B",
      },
      {
        decisionOption: "tender",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"C",
      },
      {
        decisionOption: "A",
        outcome:"success",
        probability:"0.8",
        reward:"100",
        childNode:"enlarge_invest_A",
      },
      {
        decisionOption: "A",
        outcome:"fail",
        probability:"0.2",
        reward:"-5",
        childNode:"-1",
      },
      {
        decisionOption: "B",
        outcome:"success",
        probability:"0.6",
        reward:"120",
        childNode:"enlarge_invest_B",
      },
      {
        decisionOption: "B",
        outcome:"fail",
        probability:"0.4",
        reward:"-5",
        childNode:"-1",
      },
      {
        decisionOption: "C",
        outcome:"success",
        probability:"0.5",
        reward:"180",
        childNode:"enlarge_invest_C",
      },
      {
        decisionOption: "C",
        outcome:"fail",
        probability:"0.5",
        reward:"-5",
        childNode:"-1",
      },
      {
        decisionOption: "enlarge_invest_A",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"large_scale_A",
      },
      {
        decisionOption: "enlarge_invest_A",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"middle_scale_A",
      },
      {
        decisionOption: "enlarge_invest_A",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"small_scale_A",
      },
      {
        decisionOption: "enlarge_invest_B",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"large_scale_B",
      },
      {
        decisionOption: "enlarge_invest_B",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"middle_scale_B",
      },
      {
        decisionOption: "enlarge_invest_B",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"small_scale_B",
      },
      {
        decisionOption: "enlarge_invest_C",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"large_scale_C",
      },
      {
        decisionOption: "enlarge_invest_C",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"middle_scale_C",
      },
      {
        decisionOption: "enlarge_invest_C",
        outcome:"-1",
        probability:"-1",
        reward:"-1",
        childNode:"small_scale_C",
      },
      {
        decisionOption: "large_scale_A",
        outcome:"good",
        probability:"0.25",
        reward:"120",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_A",
        outcome:"common",
        probability:"0.35",
        reward:"-10",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_A",
        outcome:"bad",
        probability:"0.4",
        reward:"-50",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_A",
        outcome:"good",
        probability:"0.4",
        reward:"60",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_A",
        outcome:"common",
        probability:"0.2",
        reward:"10",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_A",
        outcome:"bad",
        probability:"0.4",
        reward:"-30",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_A",
        outcome:"good",
        probability:"0.7",
        reward:"30",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_A",
        outcome:"common",
        probability:"0.2",
        reward:"10",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_A",
        outcome:"bad",
        probability:"0.1",
        reward:"-10",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_B",
        outcome:"good",
        probability:"0.4",
        reward:"150",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_B",
        outcome:"common",
        probability:"0.2",
        reward:"-50",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_B",
        outcome:"bad",
        probability:"0.4",
        reward:"-80",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_B",
        outcome:"good",
        probability:"0.6",
        reward:"100",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_B",
        outcome:"common",
        probability:"0.2",
        reward:"-20",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_B",
        outcome:"bad",
        probability:"0.2",
        reward:"-40",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_B",
        outcome:"good",
        probability:"0.8",
        reward:"50",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_B",
        outcome:"common",
        probability:"0.1",
        reward:"10",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_B",
        outcome:"bad",
        probability:"0.1",
        reward:"-10",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_C",
        outcome:"good",
        probability:"0.3",
        reward:"250",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_C",
        outcome:"common",
        probability:"0.2",
        reward:"80",
        childNode:"-1",
      },
      {
        decisionOption: "large_scale_C",
        outcome:"bad",
        probability:"0.5",
        reward:"-100",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_C",
        outcome:"good",
        probability:"0.5",
        reward:"120",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_C",
        outcome:"common",
        probability:"0.2",
        reward:"30",
        childNode:"-1",
      },
      {
        decisionOption: "middle_scale_C",
        outcome:"bad",
        probability:"0.3",
        reward:"-60",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_C",
        outcome:"good",
        probability:"0.6",
        reward:"80",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_C",
        outcome:"common",
        probability:"0.1",
        reward:"-10",
        childNode:"-1",
      },
      {
        decisionOption: "small_scale_C",
        outcome:"bad",
        probability:"0.3",
        reward:"-30",
        childNode:"-1",
      },
    ])//答案3决策树表

    const Q3Image = ref("")

    const compareTableAnswer = ref<Array<compareTableData>>([
      {
        type: "ID3决策树",
        method: "",
        continuous: "",
        missing: "",
      },
      {
        type: "C4.5决策树",
        method: "",
        continuous: "",
        missing: "",
      },
      {
        type: "CART决策树",
        method: "",
        continuous: "",
        missing: "",
      },
    ])

    const row_num = ref(0)
    const MLTreeTableAnswer = ref<Array<MLTreeTableData>>([
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
    ])

    const MLTreeSummary = ref("")

    // 其它数据
    const summary = ref("");

    // 数据清空逻辑
    function clear() {
      principleBlankAnswer.value=[
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
      ]
      baseQuestionBlankAnswer.value=[
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
      ]
      advancedQuestionBlankAnswer.value=[
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
      ]
      compareTableAnswer.value=[
        {
          type: "ID3决策树",
          method: "",
          continuous: "",
          missing: "",
        },
        {
          type: "C4.5决策树",
          method: "",
          continuous: "",
          missing: "",
        },
        {
          type: "CART决策树",
          method: "",
          continuous: "",
          missing: "",
        },
      ]
      Q2traditionTreeTableAnswer.value=[
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
      Q2Image.value=""
      Q3Image.value=""
      Q3traditionTreeTableAnswer.value=[
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
      row_num.value=0
      MLTreeTableAnswer.value=[
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
        {
          type:"",
          splitter:"",
          max_depth:"",
          proportion:"",
          depth:"",
          accuracy:"",
          time:"",
          image:"",
        },
      ]
      MLTreeSummary.value = ""
      summary.value = ""
    }

    return {
      principleBlankAnswer,baseQuestionBlankAnswer,advancedQuestionBlankAnswer,Q2traditionTreeTableAnswer,
      Q3traditionTreeTableAnswer,compareTableAnswer,MLTreeTableAnswer,MLTreeSummary,summary,
      Q2Image,Q3Image,row_num,
      clear
    }
  }
)