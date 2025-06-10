<template>
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title" />
    </a-steps>
    <div class="steps-content">
      <div class="exp-step-card" v-if="current == 0" ref="content1">
        <h2>一、实验目的</h2>
        <p class="recontent">
          了解供给与需求的概念，并通过实验理解供给、需求与市场价格之间的关系，掌握计算供需平衡点的方法。在实验过程中，学生应被分成买家和卖家两组，各自给出报价，并观察报价变化带来的供需曲线变化。本实验学时1学时，完成实验报告1学时。        </p>
      </div>
      <div class="exp-step-card" v-if="current == 1" ref="content2">
        <h2>二、实验内容</h2>
        <p class="recontent">
          需求与供给实验的实验内容主要是通过模拟市场机制来研究商品的需求和供给情况，探究不同政策和经济环境下商品价格的变化情况。具体实验步骤如下：        </p>
        <a-collapse default-active-key="0" :bordered="false">
          <a-collapse-panel header="1. 设计实验方案：确定实验的目的、参与者、参数等。">
            <li class="recontent">
              目的：寻求商品市场交易的供给与需求平衡点，以达成均衡价格。
            </li>
            <li class="recontent">参与者：学生。</li>
            <li class="recontent">
              参数：买家的购买数量和购买价格；卖家的卖出数量和卖出价格。
            </li>
          </a-collapse-panel>
          <a-collapse-panel header="2. 2.	模拟商品市场：在实验中模拟一个交易市场，包括商品的供应方、需求方和中介。">
            <li class="recontent">
              供应方：项目发展方、金融机构、咨询顾问、技术转让方、政策制定者。
            </li>
            <li class="recontent">需求方：政府机构、EU ETS安装方、自愿买方。</li>
            <li class="recontent">中介：经纪商、交易者、交易所、大型买方联监。</li>
          </a-collapse-panel>
          <a-collapse-panel header="3. 设定初始条件：设置初始数量、价格等初始条件。">
            <li class="recontent">买家的购买数量：0。</li>
            <li class="recontent">买家的购买价格：0。</li>
            <li class="recontent">卖家的卖出数量：0。</li>
            <li class="recontent">卖家的卖出价格：0。</li>
          </a-collapse-panel>
          <a-collapse-panel
            header="4.收集数据并分析：收集实验中的数据，如商品价格，并对数据进行统计和分析，以得出有关商品需求和供给变化的结论。以下是我们需要收集的数据部分。通过收集和分析这些数据，可以更好地理解商品需求和供给的关系，进而制定有效的商品市场管理措施。我们会在设计实验的部分专门设置一个模块用来展示所收集的数据。">
            <li class="recontent">
              1.商品价格：需要记录市场中商品的价格，可以通过市场交易数据来获得。可以分析不同政策下商品价格的变化，以及市场上的商品价格分布情况等。
            </li>
            <li class="recontent">
              2.市场结构：需要记录市场中参与者的数量和市场结构。可以分析不同市场结构对商品需求和供给的影响，以及市场结构变化对商品价格的影响等。
            </li>
            <li class="recontent">
              3.经济指标：需要记录市场中的一些经济指标，如GDP、失业率等。可以分析不同经济环境下商品需求和供给的变化，以及商品对经济发展的影响等。
            </li>
            <li class="recontent">
              4.可以采用统计学方法对收集到的数据进行分析，如回归分析、方差分析等。可以通过数据可视化工具，如图表、统计图等来呈现数据的变化趋势和关联关系。
            </li>
          </a-collapse-panel>
          <a-collapse-panel
            header="5.总结和讨论：总结实验结果，探讨不同政策和经济环境下商品需求和供给的变化情况，提出改进政策的建议。总结和讨论的内容可以有所不同，取决于实验的具体目的和研究者的关注点。总之，总结和讨论可以帮助研究者更好地理解实验结果、得出结论并提出改进措施，从而推动商品市场管理措施的进一步优化。我们将在我们实验进行的最后，进行结果的讨论分析。">
            <li class="recontent">
              1.实验结果总结：总结实验中不同政策下商品价格和市场结构的变化情况。
            </li>
            <li class="recontent">
              2.结论讨论：探讨实验结果所反映的商品需求和供给的特点和关系，分析商品市场管理措施的有效性和局限性，进一步讨论如何制定更有效的市场政策等。
            </li>
            <li class="recontent">
              3.建议改进措施：根据实验结果和结论，提出改进市场管理措施的建议和方案。
            </li>
            <li class="recontent">
              4.实验的局限性：讨论实验的局限性，例如实验设计不足、实验样本不足等因素对实验结果的影响，以及未考虑到的其他可能影响商品的因素。
            </li>
            <li class="recontent">
              5.探讨实验的应用前景，例如实验结果对于商品市场政策的指导意义、对于商品市场的运作规则的启示等。
            </li>
          </a-collapse-panel>
        </a-collapse>
      </div> 

      <div class="exp-step-card" v-if="current == 2" ref="content3">
        <h2>三、实验原理</h2>
        <a-collapse default-active-key="0" :bordered="false">
          <a-collapse-panel header="1.需求规律">
            <h4 class="content">1.1 概念</h4>
            <p class="content">
              需求数量（需求量）：消费者在一定时期内愿意并能够购买的这种商品的数量
            </p>
            <p class="content">
              需求：一种商品的需求是指在其他因素不变的条件下，消费者在一定时期内各种可能的价格水平愿意并且能够购买的商品的数量。
            </p>
            <h4 class="content">1.2 需求曲线</h4>
            <p class="content">需求曲线：以几何图形来表示商品的价格和需求量的函数关系</p>
            <p class="content">横坐标：需求量 纵坐标：价格</p>
            <img class="content" style="margin-left: 100px" src="../assets/demand.png" alt="需求曲线" />
            <br />
            <br />
            <h4 class="content">1.3 需求定理</h4>
            <p class="content">
              需求定理：在其他因素不变的条件下，一种商品的价格上升，则对该商品的需求量减少
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="2.供给规律">
            <h4 class="content">2.1 概念</h4>
            <p class="content">
              供给数量：生产者在一定时期内愿意并且能够提供出售的这种商品的数量。
            </p>
            <p class="content">
              供给：一种商品的供给是指在其他因素不变的条件下，生产者在一定时期内在各种可能的价格水平愿意并且能够提供出售的该商品的数量。
            </p>
            <h4 class="content">2.2 供给曲线</h4>
            <p class="content">类似地，供给曲线表示了供给量与价格的函数关系</p>
            <p class="content">横坐标：需求量 纵坐标：价格</p>
            <img class="content" style="margin-left: 100px" src="../assets/supply.png" alt="供给曲线" />
            <br />
            <br />
            <h4 class="content">2.3 供给定理</h4>
            <p class="content">
              供给定理：在其他因素保持不变的条件下，一种商品的价格上升，该商品的供给量增加。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="3.均衡价格">
            <p class="content">
              需求曲线说明了消费者对某种商品在每一价格水平的需求量;供给曲线说明了生产者对某种商品在每一价格水平的供给量
            </p>
            <p class="content">
              微观经济学中，商品价格指的是商品的均衡价格。商品的均衡价格是在商品的市场需求和市场供给这两种相反力量的相互作用下形成的。
            </p>
            <p class="content">均衡价格：市场需求量与供给量相同时的价格</p>
            <img src="../assets/equal.png" style="margin-left: 100px" alt="均衡价格" />
            <br />
          </a-collapse-panel>
        </a-collapse>
      </div>
      <div class="exp-step-card" v-if="current == 3" ref="content4">
        <h2>四、实验步骤</h2>
        <p class="content buttons">
          1. 实验开始，请输入你对商品的报价:
        </p>
        <br />

        <!-- <a-button type="primary" ghost @click="changeQuotation" class="buttons">切换报价状态</a-button> -->

        <h3> </h3>
        

        <a-input 
          clearable
          v-model:value="userprice" 
          placeholder="请输入价格" 
          :disabled="isDisabled" 
          style="width: 180px; margin-right: 10px" 
        ></a-input>
        
        <!-- 买卖选择下拉菜单 -->
        <a-select v-model:value="tradeType" :disabled="isDisabled" style="width: 120px; margin-right: 10px">
          <a-select-option value="BUY">买</a-select-option>
          <a-select-option value="SELL">卖</a-select-option>
        </a-select>
        
        <!-- 按钮，点击时执行函数 -->
        <a-button type="primary" @click="addRecord" :disabled="isDisabled">提交</a-button>
        <!-- <a-button type="primary" @click="getChartImg">图片</a-button> -->

        
        <hr />



        <p class="content buttons">
          2.
          在实验的过程中，统计买家数量和卖家数量和总的参与人数，以及当前的实验已经进行的时间等:
        </p>
        <a-descriptions title="实验详情" class="content" :column="4" bordered>
          <a-descriptions-item label="实验进行情况">
            <a-tag color="blue">{{ myStore.nowsitua }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="总的参与人数">{{
            peoplecount
          }}</a-descriptions-item>
          <a-descriptions-item label="买家数量">{{
            buycount
          }}</a-descriptions-item>
          <a-descriptions-item label="卖家数量">{{
            sellcount
          }}</a-descriptions-item>
        </a-descriptions>
        <p class="table-title">供需曲线图</p>
        <a-button type="primary" ghost @click="updateData" class="buttons">更新数据</a-button>
        <a-button type="primary" ghost @click="downloadImg">下载图片</a-button>
        <div style="width: 800px; height: 600px">
          <DemandSupplyChart ref="demandSupplyChart":demandprice="demandData" :supplyprice="supplyData" @intersection-found="handleIntersectionFound"/>
        </div>
      </div>
      <div class="exp-step-card" v-if="current == 4" ref="content5">
        <h2>五、实验结果</h2>
        <a-collapse default-active-key="0" :bordered="false">
          <a-collapse-panel header="1.实验设计和方法：">
            <p class="content">
              本实验采用了市场实验设计，共有{{ peoplecount}}名参与者，其中{{ sellcount}}名为卖家，{{ buycount
              }}名为买家。买家和卖家在实验开始前接受了一次关于商品交易的信息介绍，了解了市场交易规则。实验的主要指标为商品的价格、交易量和总收益。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="2.分析结果：">
            <p class="content">
              实验结果显示，在本次实验中，碳排放权的均衡价格为{{ balancepoint}}元，买家和卖家的最优交易数量分别为{{buybest}}和{{ sellbest}}。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="3.讨论结果：">
            <p class="content">
              根据实验结果，我们可以发现，商品市场的供给和需求之间存在均衡价格，市场竞争的力量使得价格能够自我调整到一个相对稳定的水平。同时，交易手续费的支出也需要引起政策制定者和市场监管者的关注，以确保商品市场的公平和透明。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="4.结论：">
            <p class="content">
              本实验结果表明，商品市场的供给和需求之间存在均衡价格，市场竞争能够有效地调节市场价格。政策制定者和市场监管者应该加强商品市场的监管和规范，鼓励更多的参与者参与商品交易，以促进经济的发展。未来的研究可以进一步探讨商品市场的发展趋势和政策影响。
            </p>
          </a-collapse-panel>
        </a-collapse>
      </div>
      <div class="exp-step-card" v-if="current == 5" ref="content6">
        <h2>六、实验思考</h2>
        <a-collapse default-active-key="0" :bordered="false">
          <a-collapse-panel header="1.实验结果回顾：">
            <p class="content">
              本次实验旨在探究商品市场的供给与需求之间的关系，通过模拟商品交易，研究市场的价格和交易量等指标。实验结果显示，商品市场具有均衡价格，市场竞争能够有效地调节市场价格。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="2.实验思考和分析：">
            <p class="content">
              从市场机制的角度来看，商品市场的供给和需求之间存在均衡价格，这说明市场机制对于商品交易的平衡和稳定具有重要作用。从政策影响的角度来看，政策的制定和实施对于商品市场的发展和稳定也具有重要作用，政策的引导和监管能够促进市场的规范和透明。从参与者行为的角度来看，参与者的信息和操作对于市场价格和交易量也具有重要影响，这说明市场的参与者对于市场的运作和发展有着重要的影响力。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="3.局限性和未来研究：">
            <p class="content">
              本次实验的局限性主要在于样本数量和实验设置的简化程度，未来实验可以进一步扩大样本数量和增加实验设置的复杂程度，以提高实验的科学性和实用性。同时，未来的研究可以继续探讨商品市场的发展趋势和政策影响，以及市场参与者的行为和决策等方面。
            </p>
          </a-collapse-panel>
          <a-collapse-panel header="4.结论和建议：">
            <p class="content">
              本次实验结果表明，商品市场具有供给和需求之间的均衡价格，市场竞争能够有效地调节市场价格。政策制定者和市场监管者应该加强商品市场的监管和规范，鼓励更多的参与者参与商品交易，以促进经济的发展。未来的研究可以进一步探讨商品市场的发展趋势和政策影响，以及参与者行为和决策等方面。
            </p>
          </a-collapse-panel>
        </a-collapse>
      </div>
      <div class="exp-step-card" v-if="current == 6" ref="content7">
      <h2>七、实验心得</h2>
        <a-form :model="summary" name="nest-messages">
          <a-form-item label="请输入实验总结" name="summary">
          <a-textarea v-model:value="summary" />
          </a-form-item>
        </a-form>
    </div>
    </div>
    
  </div>
  <a-spin :spinning="spinning">
    <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
      <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
                @click="next"><step-forward-outlined />下一步</a-button>
      <a-button v-if="current > 0" style="margin-right: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
      
      <a-button v-if="current == steps.length - 1" style="margin-right: 8px" type="primary"  @click="submit">
        提交
      </a-button>
      <a-button v-if="current == steps.length - 1" type="primary" class="btn" @click="downLoadFile">
        <EyeOutlined />
        预览报告
      </a-button>
      
      
    </div>
  </a-spin>
</template>

<script lang="ts" setup>
import { message } from "ant-design-vue";
import { toRaw, ref, onMounted,reactive } from "vue";
import { useStore } from './stores';
import { useAccountStore } from "@/store/account";
import { storeToRefs } from 'pinia';
import axios from "axios";
import DemandSupplyChart from "./DemandSupplyChart.vue";
import { useRouter } from 'vue-router';
import { 
  StepForwardOutlined, 
  StepBackwardOutlined, 
  EyeOutlined
} from '@ant-design/icons-vue';

interface ReportData {
  experiment_id: string;
  reporter_id: string;
  reporter_name: string;
  course_name: string;
  course_id: string | number;
  submit_time: Date;
  summary: string;
  money: string | number;
  quote_time: string;
  quote_type: string;
  quotationstatus: string;
  peoplecount: number;
  buycount: number;
  sellcount: number;
  balancepoint: number;
  buybest: number;
  sellbest: number;
  pic?: string;
}

const myStore = useStore();
// @ts-ignore 忽略TypeScript类型错误
const { summary } = storeToRefs(myStore);
const accountStore = useAccountStore();
const student_id = accountStore.account.email.slice(0, accountStore.account.email.indexOf('@'))  // 虽然是很诡异的算法，但上级的Exp6.vue就是这么写的，小编也很惊讶
const router = useRouter();

const spinning = ref(false)
const current = ref(0)
const steps = ref([
  { title: '实验目的', content: '0-content' },
  { title: '实验内容', content: '1-content' },
  { title: '实验原理', content: '2-content' },
  { title: '实验步骤', content: '3-content' },
  { title: '实验结果', content: '4-content' },
  { title: '实验思考', content: '5-content' },
  { title: '实验总结', content: '6-content' },
])
const demandData = ref([[0, 0]]); 
const supplyData = ref<number[][]>([[0, 0]]); 
const quotationstatus=ref(true) //报价状态
const userindex=ref("0") //用户index
const courseId=ref("0") //课程index
const token = localStorage.getItem('token');
const peoplecount=ref(0);
const buycount=ref(0);
const sellcount=ref(0);
const chartImage=ref("0");
const balancepoint=ref(0);
const buybest=ref(0);
const sellbest=ref(0);

// check

const userprice = ref();
const tradeType = ref('BUY');
const quotetime =ref();
// 存储组件是否禁用的状态
const isDisabled = ref(false);
const demandSupplyChart = ref(null);

const receivedData = ref(null);


const date = reactive(new Date)
const experimentDate = ref(date);

onMounted(() => {
  initexperiment();

  const isReturning = sessionStorage.getItem('isReturningFromPreview');
  if (isReturning === 'true') {
    // 是从预览页面返回
    const savedStateString = sessionStorage.getItem('tanpaifang_page_state_for_preview');
    if (savedStateString) {
      try {
        const savedState = JSON.parse(savedStateString);
        current.value = savedState.current || 0;
        demandData.value = savedState.demandData || [[0, 0]];
        supplyData.value = savedState.supplyData || [[0, 0]];
        peoplecount.value = savedState.peoplecount || 0;
        buycount.value = savedState.buycount || 0;
        sellcount.value = savedState.sellcount || 0;
        userprice.value = savedState.userprice;
        tradeType.value = savedState.tradeType || 'BUY';
        balancepoint.value = savedState.balancepoint || 0;
        buybest.value = savedState.buybest || 0;
        sellbest.value = savedState.sellbest || 0;
        chartImage.value = savedState.chartImage || "0";
        isDisabled.value = savedState.isDisabled || false;
        if (myStore && savedState.storeState) {
          // 假设 store 状态可以直接赋值恢复，或者 store 有一个 hydrate 方法
          // 对于简单情况，可以逐个恢复
          myStore.summary = savedState.storeState.summary || '';
          myStore.nowsitua = savedState.storeState.nowsitua || '未开始';
          // ... 恢复 myStore 中的其他状态 ...
        }
        receivedData.value = savedState.receivedData || null;
      } catch (e) {
        console.error('从预览恢复状态失败:', e);
        // 如果恢复失败，执行标准的清空逻辑
        resetComponentState(); 
      }
    }
    // 清除标记和保存的状态，避免影响下次刷新
    sessionStorage.removeItem('isReturningFromPreview');
    sessionStorage.removeItem('tanpaifang_page_state_for_preview');
  } else {
    // 正常加载或刷新，清空所有状态
    resetComponentState();
  }
});

function resetComponentState() {
  current.value = 0;
  demandData.value = [[0, 0]];
  supplyData.value = [[0, 0]];
  peoplecount.value = 0;
  buycount.value = 0;
  sellcount.value = 0;
  userprice.value = undefined;
  tradeType.value = 'BUY';
  balancepoint.value = 0;
  buybest.value = 0;
  sellbest.value = 0;
  chartImage.value = "0";
  isDisabled.value = false;
  if (myStore) {
    myStore.$reset(); 
  }
  receivedData.value = null;
}

async function downLoadFile() {
  // 先准备基础数据
  const finalData: ReportData = {
    experiment_id: "1",
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.value,
    summary: myStore.summary,
    money: userprice.value,
    quote_time: experimentDate.value.toLocaleString(),
    quote_type: tradeType.value,
    quotationstatus: myStore.nowsitua,
    peoplecount: peoplecount.value,
    buycount: buycount.value,
    sellcount: sellcount.value,
    balancepoint: balancepoint.value,
    buybest: buybest.value,
    sellbest: sellbest.value,
    pic: ''
  };

  getChartImg();
  finalData.pic = chartImage.value;

  // 在跳转前保存当前页面所有需要的状态到 sessionStorage
  try {
    const stateToSave = {
      current: current.value,
      demandData: demandData.value,
      supplyData: supplyData.value,
      peoplecount: peoplecount.value,
      buycount: buycount.value,
      sellcount: sellcount.value,
      userprice: userprice.value,
      tradeType: tradeType.value,
      balancepoint: balancepoint.value,
      buybest: buybest.value,
      sellbest: sellbest.value,
      chartImage: chartImage.value,
      isDisabled: isDisabled.value,
      receivedData: receivedData.value, // 如果 receivedData 也需要保留
      storeState: { // 保存 Pinia store 的相关状态
        summary: myStore.summary,
        nowsitua: myStore.nowsitua,
        // ... 保存 myStore 中其他需要恢复的状态 ...
      }
    };
    sessionStorage.setItem('tanpaifang_page_state_for_preview', JSON.stringify(stateToSave));
    sessionStorage.setItem('isReturningFromPreview', 'true'); // 设置返回标记
  } catch (e) {
    console.error('保存页面状态到sessionStorage失败（预览前）:', e);
  }

  spinning.value = true;
  try {
    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(finalData))
      }
    });
  } catch (error) {
    console.error('跳转到报告预览页面失败:', error);
    message.error('预览报告失败，请稍后重试');
  } finally {
    spinning.value = false;
  }
}

async function updateData(){
  peoplecount.value=0;
  buycount.value=0;
  sellcount.value=0;
  // await axios.get('http://139.196.226.104:8002/api/supply_and_demand/get_quote_all', {//---------------------------------------------
  await axios.get(`http://see-toju.com:8002/api/courses/${courseId.value}/supply-demand/quotes`, {
    headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
  })
  .then(response =>{
    console.log(response.data);
    const allquotes=response.data.data;
    // console.log(allquotes);

    const buyRecords = allquotes.filter((record) => record.quote_type == "BUY");
    demandData.value = getPriceCount(buyRecords,"BUY");

    // 统计记录
    const sellRecords = allquotes.filter((record) => record.quote_type == "SELL");
    supplyData.value = getPriceCount(sellRecords,"SELL");

    // console.log(demandData.value);
    // console.log(supplyData.value);
    
    // 确保图表渲染完成后再获取图片
    setTimeout(() => {
      getChartImg();
      // 更新提交数据中的图片
      data.pic = chartImage.value;
    }, 500);

    message.success("获取数据成功！");
  } )
  .catch(error => {
    console.error(error);
    message.error("在获取数据时发生错误！");
  });
}

async function downloadImg() {
  // 确保获取最新的图表图片
  getChartImg();
  
  // 如果图片数据为空，提示错误并返回
  if (!chartImage.value) {
    message.error('无法获取图表图片，请先更新数据');
    return;
  }
  
  // 更新数据中的图片属性
  data.pic = chartImage.value;
  
  const link = document.createElement('a');
  link.href = chartImage.value;
  link.download = 'chart.png';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

function getPriceCount(records: { money: number }[],type:string): number[][] {
  const priceCount: Record<number, number> = {};

  // 统计每个价格对应的人数
  records.forEach((record) => {
    if (!priceCount[record.money]) {
      priceCount[record.money] = 0;
    }
    priceCount[record.money]++;
    peoplecount.value++;
    if(type=="BUY"){
      buycount.value++;
    }
    else if(type=="SELL"){
      sellcount.value++;
    }
  });

  // 将统计结果转换为 [[price, count], [price, count], ...] 形式
  const result: number[][] = Object.entries(priceCount).map(([money, count]) => [
    Number(money), // 将键转换为 number
    count,
  ]);

  return result;
}

const emit = defineEmits({
  submit: (data) => {
    return true
  }
});

const data = {
  summary: '',
  money: "0",
  quote_time: ' ',
  quote_type: "BUY",
  quotationstatus: '',
  peoplecount: 0,
  buycount: 0,
  sellcount: 0,
  imageurl: '',
  pic: '',
  balancepoint: 0,
  buybest: 0,
  sellbest: 0
};

const submit =  () => {
  spinning.value = true;
  data.summary = myStore.summary;
  data.money = userprice.value;
  data.quote_time = experimentDate.value.toLocaleString();
  data.quote_type = tradeType.value;
  data.quotationstatus = myStore.nowsitua;
  data.peoplecount = peoplecount.value;
  data.buycount = buycount.value;
  data.sellcount = sellcount.value;
  // 获取图表图片并设置到pic字段中
  getChartImg();
  data.pic = chartImage.value;
  data.balancepoint = balancepoint.value;
  data.buybest = buybest.value;
  data.sellbest = sellbest.value;
  
  // 保存数据到receivedData
  localStorage.setItem('experimentData', JSON.stringify(data));
  receivedData.value = data;
  
  // 数据提取完毕以后调用父组件的submit方法，并传递数据
  emit('submit', data)
  // 关闭加载动画
  spinning.value = false
};

async function addRecord() {
  console.log(quotationstatus.value)
  if(!quotationstatus.value){
    message.error("投票未开启！")
    return;
  }

  console.log(userprice.value)
  experimentDate.value=new Date

  // await axios.post('http://139.196.226.104:8002/api/supply_and_demand/quote', null, {//---------------------------------------------
  await axios.post(`http://see-toju.com:8002/api/courses/${courseId.value}/supply-demand/quotes`, null, {
    params: {
      money: userprice.value,
      quote_type: tradeType.value,
    },
    headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
  })
  .then(response =>{
    console.log(response.data);
  } )
  .catch(error => console.error(error));

  // 禁用所有组件
  isDisabled.value = true;
  message.success("提交成功！")
}

function next() {
  current.value = Math.min(steps.value.length - 1, current.value + 1);
}

function prev() {
   current.value = Math.max(0, current.value - 1);
}

async function initexperiment(){
  // await axios.get('http://139.196.226.104:8002/api/users/me', { //---------------------------------------------
  await axios.get('http://see-toju.com:8002/api/users/me', { //---------------------------------------------
    headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
  })
  .then(response =>{
    // console.log(response)
    userindex.value=response.data.data.userIndex
    // console.log(userindex.value);
  } )
  .catch(error => console.error(error));


  await accountStore.updateCourseId()
    .then(response => {
      courseId.value = response.courseId;
      checknowsitua();
    })
    .catch(error => {
      console.error('获取课程ID失败:', error);
    });

  checknowsitua();
}

async function checknowsitua(){
  // await axios.get('http://139.196.226.104:8002/api/course/get_quote', {
  await axios.get(`http://see-toju.com:8002/api/courses/${courseId.value}/supply-demand/vote-status`, {
    // params: {
    //   courseId: courseId.value
    // },
    headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
  })
  .then(response =>{
    console.log(response.data.data);
    quotationstatus.value=response.data.data;
    
  } )
  .catch(error => console.error(error));

  if(quotationstatus.value==true){
      myStore.nowsitua="已开始";
  }
  else{
      myStore.nowsitua="未开始";
  }
}

async function changeQuotation(){

  // await axios.get('http://139.196.226.104:8002/api/course/change_quote', {
  await axios.put(`/api/courses/${courseId.value}/supply-demand/vote-status`, {
    // params: {
    //   courseId: courseId.value
    // },
    headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
  })
  .then(response => {
    console.log(response.data);
    checknowsitua(); // 请求成功后调用其他函数
  })
  .catch(error => console.error('Error:', error));

}

function getChartImg(){
  if (demandSupplyChart.value) {
    chartImage.value = demandSupplyChart.value.getChartImage();

    if (!chartImage.value) {
      console.error("无法获取图表图片数据");
      return null;
    }
    return chartImage.value;
  }
  return null;
}

async function update (){

  quotationstatus.value=false
  myStore.nowsitua="未开始"
}

const handleIntersectionFound = ({ resultX, resultY, buybestvalue, sellbestvalue }) => {
      // intersection.value = { resultX, resultY, buybest, sellbest };

      balancepoint.value=resultY;
      buybest.value=buybestvalue;
      sellbest.value=sellbestvalue;

      // console.log(balancepoint.value);
      // console.log(buybest.value);
      // console.log(sellbest.value);
    };
</script>

<style scoped>
.title {
  text-align: center;
  font-family: sans-serif;
  font-size: 30px;
}

.content {
  text-indent: 2em;
  margin-left: 20px;
  margin-right: 20px;
}

.subtitle-content {
  text-indent: 2em;
  margin-left: 15px;
  margin-right: 20px;
  font-size: large;
  font-weight: bold;
}

.table-title {
  text-indent: 2em;
  margin-left: 10px;
  margin-right: 20px;
  font-size: 16px;
  font-weight: bold;
  margin: 20px;
}

.guidance {
  position: absolute;
  right: 50px;
  font-weight: bold;
}

.buttons {
  text-indent: 0em;
  margin-right: 20px;
  display: inline-block;
}

.recontent {
  margin-left: 60px;
  margin-right: 20px;
}

.steps-content {
    margin-top: 16px;
    border: 1px dashed #e9e9e9;
    border-radius: 6px;
    background-color: #fafafa;
    min-height: 200px;
    text-align: left;
    padding-top: 10px;
}
.image-center {
    margin-top: 16px;
    border: 1px dashed #e9e9e9;
    border-radius: 16px;
    background-color: #fafafa;
    min-height: 200px;
    text-align: center;
    padding-top: 10px;
}

.steps-action {
    margin-top: 24px;
}

[data-theme='dark'] .steps-content {
    background-color: #2f2f2f;
    border: 1px dashed #404040;
}

.exp-step-card {
    padding-inline: 14px;
}
</style>

