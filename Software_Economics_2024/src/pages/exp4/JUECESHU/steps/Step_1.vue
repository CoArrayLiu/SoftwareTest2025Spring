<template>
  <h2>二、 实验原理</h2>
  <a-collapse default-active-key = "0" :bordered="false">
    <a-collapse-panel header = "1. 决策树基本概念">
      <div class="content">
        <p>
          决策论中 （如风险管理），决策树（Decision tree）由一个决策图和可能的结果（包括资源成本和风险）组成，用来创建到达目标的规划。决策树建立并用来辅助决策，是一种特殊的树结构。决策树是一个利用像树一样的图形或决策模型的决策支持工具，包括随机事件结果，资源代价和实用性。它是一个算法显示的方法。决策树经常在运筹学中使用，特别是在决策分析中，它帮助确定一个能最可能达到目标的策略。
        </p>

        <p>
          它利用了概率论的原理，并且利用一种树形图作为分析工具。其基本原理是用决策点代表决策问题，用方案分枝代表可供选择的方案，用概率分枝代表方案可能出现的各种结果，经过对各种方案在各种结果条件下损益值的计算比较，为决策者提供决策依据。
        </p>
      </div>
    </a-collapse-panel>
    <a-collapse-panel header = "2. 决策树分析法基本概念">
      <div class="content">
        <p>
          决策树分析法是一种运用概率与图论中的树对决策中的不同方案进行比较，从而获得最优方案的风险型决策方法。
        </p>

        <p>
          该方法是一种用树形图来描述各方案在未来收益的计算比较以及选择的方法，其决策是以期望值为标准的。人们对未来可能会遇到好几种不同的情况。每种情况均有出现的可能，人们现无法确知，但是可以根据以前的资料来推断各种自然状态出现的概率。在这样的条件下，人们计算的各种方案在未来的经济效果只能是考虑到各种自然状态出现的概率的期望值，与未来的实际收益不会完全相等。
        </p>

        <p>
          如果一个决策树只在树的根部有一决策点，则称为
          <span class="blank">
            <input type="text" v-model="principleAnswer[0].val" />
          </span>
          ；若一个决策不仅在树的根部有决策点，而且在树的中间也有决策点，则称为
          <span class="blank">
            <input type="text" v-model="principleAnswer[1].val" />
          </span>
          。
        </p>

        <p>
          整个决策树由如下五个要素构成：
        </p>

        <p>
          <ul>
            <li>
              <span class="blank">
                <input type="text" v-model="principleAnswer[2].val" />
              </span>
              ：决策的出发点，如果决策属于多级决策，则决策树的中间可以有多个决策点，以
              <span class="blank">
                <input type="text" v-model="principleAnswer[3].val" />
              </span>
              为最终决策方案。
            </li>
            <br>
            <li>
              <span class="blank">
                <input type="text" v-model="principleAnswer[4].val" />
              </span>
              ：由决策点引出的分支，对应于决策的
              <span class="blank">
                <input type="text" v-model="principleAnswer[5].val" />
              </span>
              。
            </li>
            <br>
            <li>
              <span class="blank">
                <input type="text" v-model="principleAnswer[6].val" />
              </span>
              ：代表备选方案的经济效果（期望值），通过各状态节点的经济效果的对比，按照一定的决策标准就可以选出最佳方案。
            </li>
            <br>
            <li>
              <span class="blank">
                <input type="text" v-model="principleAnswer[7].val" />
              </span>
              ：由状态节点引出的分支称为
              <span class="blank">
                <input type="text" v-model="principleAnswer[8].val" />
              </span>
              ，概率枝的数目表示
              <span class="blank">
                <input type="text" v-model="principleAnswer[9].val" />
              </span>
              ，每个分支上要注明该结果出现的
              <span class="blank">
                <input type="text" v-model="principleAnswer[10].val" />
              </span>
              。
            </li>
            <br>
            <li>
              <span class="blank">
                <input type="text" v-model="principleAnswer[11].val" />
              </span>
              ：将每个方案在各种自然状态下取得的
              <span class="blank">
                <input type="text" v-model="principleAnswer[12].val" />
              </span>
              标注于结果节点的右端。
            </li>
            <br>
          </ul>
        </p>
      </div>
    </a-collapse-panel>
  </a-collapse>
</template>

<script setup lang="ts">
  import {ref, watch, onMounted} from 'vue'
  import { useDecisionTreeStore } from '../stores';
  let DecisionTreeStore = useDecisionTreeStore()
  let principleAnswer = ref([
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

  watch(principleAnswer, (newAnswer) => {
    DecisionTreeStore.principleBlankAnswer = newAnswer;
  }, { deep: true });
  //  store 中的 blankAnswer 变化时同步到 answer 数组
  watch(() => DecisionTreeStore.principleBlankAnswer, (newAnswer) => {
    principleAnswer.value = newAnswer;
  }, { deep: true });
  onMounted(() => {
    if (DecisionTreeStore.principleBlankAnswer.length > 0) {
      principleAnswer.value = [...DecisionTreeStore.principleBlankAnswer];
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
</style>