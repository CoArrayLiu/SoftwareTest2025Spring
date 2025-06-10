<template>
     <div>
        <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        <div class="steps-content">
            <div class="exp-step-card" v-if="current == 0" ref="content1">
 <!-- 实验目的 -->
    <h2>一、实验目的 </h2>
    <p class="content">理解软件项目规模度量功能点法原理，通过实验操作掌握功能点法。 学生应以小组为单位，根据本小组"软件工程管理与经济"课程设计项目架构及组件等设计成果，以功能点方法测量该项目的规模(功能点数量)。
        建议选用某一种功能点方法度量课程设计项目的功能点，并采用另外一种功能点方法或其他的软件规模度量方法对前一种方法的度量结果进行验证。 本实验为课内设计性实验项目，实验学时 1 学时，完成实验报告 1 学时。
    </p>
</div>
<div class="exp-step-card" v-if="current == 1" ref="content2">

     <!-- 实验步骤 -->
    <h2>二、实验步骤 </h2>
    <!-- <p class="content" > -->
    <p class="secondtitle">第一步：识别数据功能点和事务处理功能点</p>
    <p class="content">数据功能是指更新、引用和检索而储存的可用的逻辑数据。数据块及控制信
        息是逻辑上的并且用户可确认的。数据功能分为内部逻辑文件(ILF)和外部接口
        文件(EIF)。事务处理是指外部输入、外部输出、外部查询、完成更新、检索和输
        出等操作，分为外部输入(EI)、外部输出(EO)和外部查询(EQ)。
        <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请详细阅读文档中提供的系统设计模型。
    </p>
</div>
            <div class="exp-step-card" v-if="current == 2" ref="content3">
    <p class="secondtitle">第二步：测量内部逻辑文件(ILF)</p>
    <p class="content">内部逻辑文件(ILF)是用户可确认的，在应用程序内部维护、逻辑上相关的数
        据块或控制信息。内部逻辑文件(ILF)用来保存经由应用程序的一个或多个处理
        后的数据。一旦应用程序内部的一个数据块被标识为 ILF，即使它被另一个事务
        处理所引用，它也不能再被同一个应用程序当作 EIF。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：清点实验案例中 ILF 数量。</p>

    <p class="secondtitle">第三步：测量外部接口文件(EIF) </p>
    <p class="content">外部接口文件(EIF)是用户可确认的、由被测应用程序引用，但在其他应用程
        序内部维护的、逻辑上相关的数据块或控制信息。外部接口文件(EIF)用来存放被
        测应用程序中的一个或多个基本处理所引用的数据。数据或控制数据通过诸如增
        加、变更、更新等事务来维护，一个 EIF 可以被多个应用程序引用和计算，但是
        对于一个应用程序来讲，一个 EIF 只应被计算一次。 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：清点实验案例中 EIF 数量。</p>

    <p class="secondtitle">第四步：计算 ILF 和 EIF 复杂度</p>
    <p class="content">根据 IFPUG 功能点计算实践手册(4.1 版)，识别 ILF 和 EIF 组件的复杂程度，
        并按照下表的参数并赋值(简单、平均或复杂)。 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：复杂度为简单的 ILF 数量和复杂的 ILF 数量各占 50%。EIF 的复
        杂度均为复杂。填写下表。</p>
    <h2 style="text-align: center">ILF 和 EIF 数据复杂度认定表</h2>
    <a-table :pagination="false" :columns="columns1" :data-source="tableData1" bordered size="middle"
        style="word-break: break-all;" />
    <br>
</div>
            <div class="exp-step-card" v-if="current == 3" ref="content4">
    <p class="secondtitle">第五步：测量外部输入(EI)</p>
    <p class="content">外部输入(EI)是应用程序处理来自系统边界以外的数据或控制信息的基本
        过程。EI 的作用是维护一个或多个 ILF 以及通过其处理逻辑来改变系统的行为。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：清点实验案例中 EI 数量。 </p>

    <p class="secondtitle">第六步：测量外部输出(EO) </p>
    <p class="content">外部输出(EO)是应用程序向其边界之外提供数据或控制信息的基本处理。
        EO 的作用是向用户提供经过处理逻辑加工的，除了检索信息或控制信息之外的
        信息或附加信息。处理逻辑中必须至少包含一个数学公式或者计算，创建导出数
        据或者维护一个或多个 ILF，并且改变系统的行为。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：清点实验案例中 EO 数量。 </p>
    <p class="secondtitle">第七步：测量外部查询(EQ) </p>
    <p class="content">外部查询(EQ)是应用程序向其边界之外提供数据或控制信息查询的基本处
        理。EQ 的作用是通过查询数据或控制信息来为用户提供信息，处理逻辑中既不
        包含数学公式或计算，也不产生导出数据。处理过程中不维护 ILF，系统行为不
        受影响。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：清点实验案例中 EQ 数量。 </p>
    

    <p class="secondtitle">第八步：计算 EI、EO 和 EQ 复杂度 </p>
    <p class="content">根据 IFPUG 功能点计算实践手册(4.1 版)，分别识别 EI 以及 EO 和 EQ 组件
        的复杂程度，并按照以下两个表格的参数并赋值(简单、平均或复杂)。<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：复杂度为简单的 EI 数量和复杂的 EI 数量各占 50%。复杂度为平
        均的 EO 数量占 2/3，复杂度为复杂的 EO 数量占 1/3。复杂度为简单、平均和复
        杂的 EQ 数量各占 1/3。</p>
    <h2 style="text-align: center">EI 复杂度认定表</h2>
    <a-table :pagination="false" :columns="columns2" :data-source="tableData2" bordered size="middle"
        style="word-break: break-all;" />
    <br>

    <h2 style="text-align: center">EO 和 EQ 复杂度认定表</h2>
    <a-table :pagination="false" :columns="columns3" :data-source="tableData3" bordered size="middle"
        style="word-break: break-all;" />
    <br>
</div>
            <div class="exp-step-card" v-if="current == 4" ref="content5">
    <p class="secondtitle">第九步：计算未调整功能点 </p>
    <p class="content"> 按照 IFPUG 功能点计算实践手册(4.1 版)组件复杂度等级与功能点数对应关
        系表 4，计算得到未调整功能点数(UFP)。 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：将上述各步得到的数据，填写在下面的"未调整功能点计算表"中的
        合适位置，计算本实验案例的未调整功能点。 </p>

        <br />
    <h2 style="text-align: center">每个组件复杂度等级与功能点数对应关系表 </h2>
    <a-table :pagination="false" :columns="columns4" :data-source="tableData4" bordered size="middle"
        style="word-break: break-all;" />
    <br>
        <h2 style="text-align: center;">表1：未调整功能点计算表 </h2>
    <a-table :pagination="false" :columns="columns" :data-source="tableData5" bordered size="middle"
        style="word-break: break-all;">
        <template #bodyCell="{ column, record, index }">
            <template v-if="column.dataIndex === 'A' && tableData5 !== undefined">
                <a-input v-model:value="record.A" style="width:100px; " />
            </template>
            <template v-if="column.dataIndex === 'D' && tableData5 !== undefined">
                <a-input v-model:value="record.D" style="width:100px;" />
            </template>
            <template v-if="column.dataIndex === 'G' && tableData5 !== undefined">
                <a-input v-model:value="record.G" style="width:100px;" />
            </template>
            <template v-if="column.dataIndex === 'C' && tableData5 !== undefined">
                {{ myStore.c(index) }}
            </template>
            <template v-if="column.dataIndex === 'F' && tableData5 !== undefined">
                {{ myStore.f(index) }}
            </template>
            <template v-if="column.dataIndex === 'I' && tableData5 !== undefined">
                {{ myStore.i(index) }}
            </template>
            <template v-if="column.dataIndex === 'number' && tableData5 !== undefined">
                {{  number(index) }}
            </template>
            <template v-if="column.dataIndex === 'unchanged' && tableData5 !== undefined">
                {{ myStore.unchanged(index) }}
            </template>
        </template>
    </a-table>
    <br>

    <div style="width:100%;text-align:right">
        <span style="width:30%;display:inline-block" class="secondtitle">本实验未调整功能点总计为 </span>
        <span style="display:inline-block;font-size:20px;">{{ myStore.SUM }}</span>
    </div>
    <a-spin :spinning="spinning">
    <div v-if="current > 0" style="display: flex; justify-content: flex-end;margin-top: 20px;">
      <!-- <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button> -->
      <a-button danger @click="clearData">清空数据</a-button>
    </div>
  </a-spin>
  <div>
    <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
    style="width:300px" okText="确认" cancelText="取消">
      <p>确认清空实验数据？</p>
    </a-modal>
  </div>
    <br>
    <br>

    </div>
            <div class="exp-step-card" v-if="current == 5" ref="content6">
5X
    <p class="secondtitle">第十步：计算调整后功能点 </p>
    <p class="content">考虑本实验案例的非功能性，从表 6 采集相对复杂度调整因子(标红数值)，
        得到本实验案例的功能点调整因子(VAF)为 41。将 VAF 数值代入(IFPUG 法)功能
        点计算公式，计算得到本实验案例的功能点为_____________。 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实验操作：运用 IFPUG 标准规则，计算实验案例的调整后功能点。 </p>
    
    <h2 style="text-align: center;">表2：系统特征因子表及计算表 </h2>
    <a-table :columns="columnsadjust" :pagination="false" :data-source="dataadjust" bordered size="middle"
        style="word-break: break-all;">
        <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'grade'">
                <a-input-group compact>
                    <a-select v-model:value="record.grade">
                        <a-select-option value="0">0</a-select-option>
                        <a-select-option value="1">1</a-select-option>
                        <a-select-option value="2">2</a-select-option>
                        <a-select-option value="3">3</a-select-option>
                        <a-select-option value="4">4</a-select-option>
                        <a-select-option value="5">5</a-select-option>
                    </a-select>

                </a-input-group>

            </template>
        </template>
    </a-table>
    <br />
    <div style="width:100%;text-align:right">
        <span style="width:30%;display:inline-block" class="secondtitle">合计数 </span>
        <span style="display:inline-block;font-size:20px;">{{ myStore.SUM }}</span>
    </div>
    <br />
    <br />
    <span class="secondtitle">功能点调整因子(VAF)为 </span>
    <span style="font-size:20px">{{ myStore.VAF }}</span>
    <br /><br />
    <span class="secondtitle">本实验案例的功能点为</span>
    <span style="font-size:20px">{{ myStore.ALL }}</span>
    <a-spin :spinning="spinning">
    <div v-if="current > 0" style="display: flex; justify-content: flex-end;margin-top: 20px;">
      <!-- <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button> -->
      <a-button danger @click="clearData">清空数据</a-button>
    </div>
  </a-spin>
  <div>
    <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
    style="width:300px" okText="确认" cancelText="取消">
      <p>确认清空实验数据？</p>
    </a-modal>
  </div>
</div>
<div class="exp-step-card" v-if="current == 6" ref="content4">
                <h2 style="margin-top: 20px">实验心得</h2>
                <a-form :model="summary" name="nest-messages">
                    <a-form-item label="请输入实验总结" name="summary">
                    <a-textarea v-model:value="summary" />
                    </a-form-item>
                </a-form>
                <div style="display: flex; justify-content: flex-end;">
                    <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button>
                    <a-button type="primary" class="btn" @click="downLoadFile">
                    <EyeOutlined />
                    预览报告
                  </a-button>
        
                  
    </div>
    <a-spin :spinning="spinning">
    <div v-if="current > 0" style="display: flex; justify-content: flex-end;margin-top: 20px;">
      <!-- <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button> -->
      <a-button danger @click="clearData">清空数据</a-button>
    </div>
  </a-spin>
  <div>
    <a-modal title="提示" v-model:visible=clearMsg @Ok="clearConfirm" @Cancel="clearCancel" 
    style="width:300px" okText="确认" cancelText="取消">
      <p>确认清空实验数据？</p>
    </a-modal>
  </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import dayjs from 'dayjs';
import { ref, reactive, computed,  onMounted, nextTick, toRaw} from 'vue';
import { useExperimentStore } from '@/store/experiment';
import { useAccountStore } from '@/store/account';
import { useExp1Store } from './stores';
import { storeToRefs } from 'pinia';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';
import { EyeOutlined } from '@ant-design/icons-vue';

const { summary,tableData5,dataadjust, } = storeToRefs(useExp1Store());
const experimentStore = useExperimentStore();
const accountStore = useAccountStore();
const myStore = useExp1Store();
const spinning = ref<boolean>(false);
const router = useRouter();
dayjs.locale('zh-cn');

// Data
const current = ref(0);
const content = ref([]);

const receivedData = ref(null);
const experimentDate = new Date();

interface ReportData {
  experiment_id: number;
  reporter_id: string;
  reporter_name: string;
  course_name: string;
  course_id: string | number;
  submit_time: number;
  summary: string;
  UFP_table: any[];
  UFP: number;
  adjust_table: any[];
  total_adjust: number | string;
  VAF: number | string;
  ALL: number | string;
}

async function downLoadFile() {
  // 先准备基础数据
  const finalData: ReportData = {
    experiment_id: 12,
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
    summary: myStore.summary,
    UFP_table: toRaw(myStore.tableData5),
    UFP: myStore.SUM,
    adjust_table: toRaw(myStore.dataadjust),
    total_adjust: toRaw(myStore.SUM),
    VAF: myStore.VAF,
    ALL: myStore.ALL
  };

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('ifpug_page_state', JSON.stringify({
      current: current.value,
      hasSubmitted: true,
      tableData5: toRaw(myStore.tableData5),
      dataadjust: toRaw(myStore.dataadjust),
      summary: myStore.summary
    }));
  } catch (e) {
    console.error('保存页面状态失败', e);
  }

  spinning.value = true;
  try {
    // 路由跳转到报告预览页面，传递报告数据参数
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

const steps = [
    { title: '实验目的', content: '0-content' },
    { title: '识别数据功能点和事务处理功能点', content: 'First-content' },
    { title: '测量ILF与EIF', content: 'Second-content' },
    { title: '测量EI、EO 和 EQ', content: 'Third-content' },
    { title: '计算未调整功能点', content: 'Fourth-content' },
    { title: '计算调整后功能点', content: 'Fifth-content' },
    {title: '实验总结', content: 'Last-content'}
];

// Columns and table data would be defined similarly.
// For brevity, not all data is rewritten here. Follow the same pattern as below for each data object.
const columns1 = ref([
    {
        title: '记录元素类型(RET)',
        dataIndex: 'RET',
        key: 'type',
        align: 'center',
        width: 400
    },
    {
        title: '数据元素类型(DET)',
        children: [
            {
                title: '1-19',
                dataIndex: 'left',
                key: '1-19',
                align: 'center'
            },
            {
                title: '20-50',
                dataIndex: 'mide',
                key: '20-50',
                align: 'center'
            },
            {
                title: '>50',
                dataIndex: 'right',
                key: '50',
                align: 'center'
            },
        ]
    }
]);

const columns2 = ref([{
    title: '引用的文件类型个数(FTR)',
    dataIndex: 'FTR',
    key: 'type',
    align: 'center',
    width: 400
},
{
    title: '数据元素类型(DET)',
    children: [
        {
            title: '1-4',
            dataIndex: 'left',
            key: 'type',
            align: 'center'
        },
        {
            title: '5-15',
            dataIndex: 'mide',
            key: 'type',
            align: 'center'
        },
        {
            title: '>15',
            dataIndex: 'right',
            key: 'type',
            align: 'center'
        },
    ]
}
]);

const columns3 = ref([
    {
        title: '引用的文件类型个数(FTR)',
        dataIndex: 'FTR',
        key: 'type',
        align: 'center',
        width: 400
    },
    {
        title: '数据元素类型(DET)',
        children: [
            {
                title: '1-5',
                dataIndex: 'left',
                key: 'type',
                align: 'center'
            },
            {
                title: '6-19',
                dataIndex: 'mide',
                key: 'type',
                align: 'center'
            },
            {
                title: '>19',
                dataIndex: 'right',
                key: 'type',
                align: 'center'
            },
        ]
    }
]);

const columns4 = ref([
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        align: 'center',
        width: 400
    },
    {
        title: '复杂度级别',
        children: [
            {
                title: '简单',
                dataIndex: 'easy',
                key: 'type',
                align: 'center'
            },
            {
                title: '平均',
                dataIndex: 'mide',
                key: 'type',
                align: 'center'
            },
            {
                title: '复杂',
                dataIndex: 'complex',
                key: 'type',
                align: 'center'
            },
        ]
    }
]);

const columns = ref([
    {
        title: '组件',
        dataIndex: 'component',
        key: 'component',
        align: 'center',
    },
    {
        title: '数量',
        dataIndex: 'number',
        key: 'number',
        align: 'center',
    },
    {
        title: '复杂度',
        children: [
            {
                title: '简单',
                align: 'center',
                children: [
                    {
                        title: '计数',
                        align: 'center',
                        children: [
                            {
                                title: 'A',
                                dataIndex: 'A',
                                align: 'center',
                            }
                        ],
                    },
                    {
                        title: '权重',
                        align: 'center',
                        children: [
                            {
                                title: 'B',
                                dataIndex: 'B',
                                align: 'center',
                            }
                        ],
                    },
                    {
                        title: '功能点数',
                        align: 'center',
                        children: [
                            {
                                title: 'C=A*B',
                                dataIndex: 'C',
                                align: 'center',
                            }
                        ],
                    }
                ],
            },
            {
                title: '平均',
                align: 'center',
                children: [
                    {
                        title: '计数',
                        children: [
                            {
                                align: 'center',
                                dataIndex: 'D',
                                title: 'D',
                            }
                        ],
                    },
                    {
                        title: '权重',
                        align: 'center',
                        children: [
                            {
                                dataIndex: 'E',
                                title: 'E',
                                align: 'center',
                            }
                        ],
                    },
                    {
                        title: '功能点数',
                        align: 'center',
                        children: [
                            {
                                dataIndex: 'F',
                                title: 'F=D*E',
                                align: 'center',
                            }
                        ],
                    }
                ],
            },
            {
                title: '复杂',
                align: 'center',
                children: [
                    {
                        title: '计数',
                        align: 'center',
                        children: [
                            {
                                dataIndex: 'G',
                                title: 'G',
                                align: 'center',
                            }
                        ],
                    },
                    {
                        title: '权重',
                        align: 'center',
                        children: [
                            {
                                dataIndex: 'H',
                                title: 'H',
                                align: 'center',
                            }
                        ],
                    },
                    {
                        title: '功能点数',
                        align: 'center',
                        children: [
                            {
                                dataIndex: 'I',
                                title: 'I=G*H',
                                align: 'center',
                            }
                        ],
                    }
                ],
            }
        ]
    },
    {
        title: '未调整功能点数',
        dataIndex: 'unchanged',
        key: 'unchanged',
        align: 'center',
    }
]);

const columnsadjust = ref([
    {
        title: '序号',
        dataIndex: 'index',
        key: 'component',
        align: 'center',
        width: 100
    },
    {
        title: '因子',
        dataIndex: 'title',
        key: 'component',
        align: 'center',
        width: 900
    },
    {
        title: '等级',
        dataIndex: 'grade',
        key: 'component',
        align: 'center'
    }
]);

// Data for the tables
const tableData1 = ref([
    { RET: '1', left: '简单', mide: '简单', right: '平均' },
    { RET: '2~4', left: '简单', mide: '平均', right: '复杂' },
    { RET: '>5', left: '平均', mide: '复杂', right: '复杂' }
]);

const tableData2 = ref([
    { FTR: '0~1', left: '简单', mide: '简单', right: '平均' },
    { FTR: '2', left: '简单', mide: '平均', right: '复杂' },
    { FTR: '>2', left: '平均', mide: '复杂', right: '复杂' }
]);

const tableData3 = ref([
    { FTR: '0~1', left: '简单', mide: '简单', right: '平均' },
    { FTR: '2~3', left: '简单', mide: '平均', right: '复杂' },
    { FTR: '>3', left: '平均', mide: '复杂', right: '复杂' }
]);

const tableData4 = ref([
    { type: 'ILF', easy: 'X7', mide: 'X10', complex: 'X15' },
    { type: 'EIF', easy: 'X5', mide: 'X7', complex: 'X10' },
    { type: 'EI', easy: 'X3', mide: 'X4', complex: 'X6' },
    { type: 'EO', easy: 'X4', mide: 'X5', complex: 'X7' },
    { type: 'EQ', easy: 'X3', mide: 'X4', complex: 'X6' }
]);
const number = (index) => {
      if (tableData5[index]) {
        const result = (parseInt(tableData5[index].A) || 0) + (parseInt(tableData5[index].D) || 0) + (parseInt(tableData5[index].G) || 0);
        tableData5[index].number = result.toString();  // 将结果转换为字符串
        return tableData5[index].number;
      }
    };
    
const emit = defineEmits({
  submit: (data) => {
    return true
  }
});
const submit = () => {
    const data = {
        summary: myStore.summary,
        UFP_table: toRaw(myStore.tableData5),
        UFP: myStore.SUM.value,  // 假设 SUM 是响应式引用
        adjust_table: toRaw(myStore.dataadjust),
        total_adjust:toRaw(myStore.SUM),  // 假设已通过 computed 定义
        VAF: myStore.VAF,  // 假设已通过 computed 定义
        ALL: myStore.ALL,  // 假设已通过 computed 定义
        
    };
    receivedData.value = data;
    localStorage.setItem('experimentData', JSON.stringify(data));
    // console.log(JSON.stringify(data));
    // 此处使用 emit，确保在 setup 中定义了 emit
    emit('submit', data);
};

const saveData = () => {
    console.log('saveData');
};

onMounted(() => {
    getTableData(); // 假设这是加载数据的函数
    
    // 从sessionStorage恢复页面状态
    try {
        const savedState = sessionStorage.getItem('ifpug_page_state');
        if (savedState) {
            const state = JSON.parse(savedState);
            // 恢复状态
            current.value = state.current;
            if (state.tableData5) myStore.tableData5 = state.tableData5;
            if (state.dataadjust) myStore.dataadjust = state.dataadjust;
            if (state.summary) myStore.summary = state.summary;
            // 不要清除保存的状态，这样返回时还能继续使用
            // sessionStorage.removeItem('ifpug_page_state');
        }
    } catch (e) {
        console.error('恢复页面状态失败', e);
    }
    const savedData1 = localStorage.getItem('experimentData');
    if (savedData1) {
        receivedData.value = JSON.parse(savedData1);
    }
});
const clearMsg = ref(false);
const clearData = () => {
  clearMsg.value = true;
}

const clearConfirm = () => {
  myStore.clear();
  message.warning('已清空数据');
  clearMsg.value = false;
  current.value = 0;
}

const clearCancel = () => {
  clearMsg.value = false;
}
// 加载数据的方法
const getTableData = () => {
    // 加载数据的逻辑
    console.log('Data loaded');
};

// 处理内容切换的逻辑
const navigateContent = (direction) => {
    let newIndex = current.value + (direction === 'next' ? 1 : -1);
    if (newIndex >= 0 && newIndex < content.value.length) {
        current.value = newIndex;
    }
    console.log(`Current index after ${direction}:`, current.value);
};

const updateLayout = () => {
    nextTick(() => {
        // 假设 detailTable 是表格组件的引用
        console.log('Layout updated');
    });
};

const getSummaries = (param) => {
    const { columns, data } = param;
    const sums = columns.map((column, index) => {
        if (index === 0) {
            return '未调整功能点';  // 举例
        } else if (index === 11) {
            return `￥${data[index].nonum.toFixed(2)}`;  // 举例
        }
        return '';
    });
    console.log('Summaries:', sums);
    return sums;
};


</script>



<!-- <script lang="ts">
import dayjs from 'dayjs';
import { ref,toRaw } from 'vue';
dayjs.locale('zh-cn');
import { message } from 'ant-design-vue';
export default {
    name: 'Exp1_IFPUG',
    emits: ['submit'],
    
    data() {
        return {
            current: 0,
            test: '21111',
            SUM: 0,
            VAF: 0,
            SUM_A: 0,
            content: [],
            steps: [{
                title: '实验目的',
                content: '0-content',
            }, {
                title: '识别数据功能点和事务处理功能点',
                content: 'First-content',
            }, {
                title: '测量内部逻辑文件(ILF)',
                content: 'Second-content',
            }, {
                title: '测量外部接口文件(EIF)',
                content: 'Third-content',
            },
            {
                title: '计算 ILF 和 EIF 复杂度',
                content: 'Fourth-content',
            },
            {
                title: '测量外部输入(EI)',
                content: 'Fifth-content',
            },
            {
                title: '测量外部输出(EO)',
                content: 'Sixth-content',
            },
            {
                title: '测量外部查询(EQ)',
                content: 'Seventh-content',
            },
            {
                title: '计算 EI、EO 和 EQ 复杂度',
                content: 'Enghth-content',
            },{
                title: '计算未调整功能点',
                content: 'Ninth-content',
            },{
                title: '计算调整后功能点',
                content: 'Last-content',
            }],
            
            columns1: [
                {
                    title: '记录元素类型(RET) ',
                    dataIndex: 'RET',
                    key: 'type',
                    align: 'center',
                    width: 400,
                    // fixed: 'left',
                },
                {
                    title: '数据元素类型(DET)',
                    children: [
                        {
                            title: '1-19',
                            dataIndex: 'left',
                            key: '1-19',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '20-50',
                            dataIndex: 'mide',
                            key: '20-50',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '>50',
                            key: '50',
                            dataIndex: 'right',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                    ]
                }

            ],
            columns2: [{
                title: '引用的文件类型个数(FTR) ',
                dataIndex: 'FTR',
                key: 'type',
                align: 'center',
                width: 400,
                // fixed: 'left',
            },
            {
                title: '数据元素类型(DET)',
                children: [
                    {
                        title: '1-4',
                        dataIndex: 'left',
                        key: 'type',
                        align: 'center'
                        // width: 30,
                        // fixed: 'left',
                    },
                    {
                        title: '5-15',
                        dataIndex: 'mide',
                        key: 'type',
                        align: 'center'
                        // width: 30,
                        // fixed: 'left',
                    },
                    {
                        title: '>15',
                        dataIndex: 'right',
                        key: 'type',
                        align: 'center'
                        // width: 30,
                        // fixed: 'left',
                    },
                ]
            }
            ],
            columns3: [
                {
                    title: '引用的文件类型个数(FTR) ',
                    dataIndex: 'FTR',
                    key: 'type',
                    align: 'center',
                    width: 400,
                    // fixed: 'left',
                },
                {
                    title: '数据元素类型(DET)',
                    children: [
                        {
                            title: '1-5',
                            dataIndex: 'left',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '6-19',
                            dataIndex: 'mide',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '>19',
                            dataIndex: 'right',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                    ]
                }
            ],
            columns4: [
                {
                    title: '类型 ',
                    dataIndex: 'type',
                    key: 'type',
                    align: 'center',
                    width: 400,
                    // fixed: 'left',
                },
                {
                    title: '复杂度级别',
                    children: [
                        {
                            title: '简单',
                            dataIndex: 'easy',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '平均',
                            dataIndex: 'mide',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                        {
                            title: '复杂',
                            dataIndex: 'complex',
                            key: 'type',
                            align: 'center'
                            // width: 30,
                            // fixed: 'left',
                        },
                    ]
                }
            ],
            columns: [
                {
                    title: '组件',
                    dataIndex: 'component',
                    key: 'component',
                    align: 'center'
                    // width: 30,
                    // fixed: 'left',
                },
                {
                    title: '数量',
                    dataIndex: 'number',
                    key: 'number',
                    align: 'center'
                    // width: 30,
                    // fixed: 'left',
                },
                {
                    title: '复杂度',
                    children: [
                        {
                            title: '简单',
                            align: 'center',
                            children: [{
                                title: '计数',
                                align: 'center',
                                children: [{
                                    title: 'A',
                                    // width: 30,
                                    dataIndex: 'A',
                                    align: 'center'
                                }],
                            },
                            {
                                title: '权重',
                                align: 'center',
                                children: [{
                                    title: 'B',
                                    dataIndex: 'B',
                                    align: 'center',
                                    // width: 30,
                                }],
                            },
                            {
                                title: '功能点数',
                                align: 'center',
                                children: [{
                                    title: 'C=A*B',
                                    align: 'center',
                                    dataIndex: 'C',
                                    // width: 30,
                                }],
                            }
                            ],
                        },
                        {
                            title: '平均',
                            align: 'center',
                            children: [{
                                title: '计数', children: [{
                                    align: 'center',
                                    dataIndex: 'D',
                                    title: 'D',
                                    // width: 30,
                                }],
                            },
                            {
                                title: '权重',
                                align: 'center',
                                children: [{
                                    dataIndex: 'E',
                                    align: 'center',
                                    title: 'E',
                                    // width: 30,
                                }],
                            },
                            {
                                title: '功能点数',
                                align: 'center',
                                children: [{
                                    dataIndex: 'F',
                                    title: 'F=D*E',
                                    align: 'center',
                                    // width: 30,
                                }],
                            }],
                        },
                        {
                            title: '复杂',
                            align: 'center',
                            children: [{
                                title: '计数',
                                align: 'center',
                                children: [{
                                    dataIndex: 'G',
                                    align: 'center',
                                    title: 'G',
                                    // width: 30,
                                }],
                            },
                            {
                                title: '权重',
                                align: 'center',
                                children: [{
                                    dataIndex: 'H',
                                    align: 'center',
                                    title: 'H',
                                    // width: 30,
                                }],
                            },
                            {
                                title: '功能点数',
                                align: 'center',
                                children: [{
                                    dataIndex: 'I',
                                    title: 'I=G*H',
                                    align: 'center',
                                    // width: 30,
                                }],
                            }],
                        },

                    ]
                },
                {
                    title: '未调整功能点数',
                    dataIndex: 'unchanged',
                    key: 'unchanged',
                    align: 'center',
                    // width: 800,
                    // fixed: 'right',
                },
            ],
            columnsadjust: [
                {
                    title: '序号',
                    dataIndex: 'index',
                    key: 'component',
                    align: 'center',
                    width: 100
                    // fixed: 'left',
                },
                {
                    title: '因子',
                    dataIndex: 'title',
                    key: 'component',
                    align: 'center',
                    width: 900,
                    // fixed: 'left',
                },
                {
                    title: '等级',
                    dataIndex: 'grade',
                    key: 'component',
                    align: 'center'
                    // width: 30,
                    // fixed: 'left',
                },
            ],
            tableData1: [
                {
                    RET: '1',
                    left: '简单',
                    mide: '简单',
                    right: '平均'

                },
                {
                    RET: '2~4',
                    left: '简单',
                    mide: '平均',
                    right: '复杂'

                },
                {
                    RET: '>5',
                    left: '平均',
                    mide: '复杂',
                    right: '复杂'

                }
            ],
            tableData2: [
                {
                    FTR: '0~1',
                    left: '简单',
                    mide: '简单',
                    right: '平均'
                },
                {
                    FTR: '2',
                    left: '简单',
                    mide: '平均',
                    right: '复杂'
                },
                {
                    FTR: '>2',
                    left: '平均',
                    mide: '复杂',
                    right: '复杂'
                }

            ],
            tableData3: [
                {
                    FTR: '0~1',
                    left: '简单',
                    mide: '简单',
                    right: '平均'
                },
                {
                    FTR: '2~3',
                    left: '简单',
                    mide: '平均',
                    right: '复杂'
                },
                {
                    FTR: '>3',
                    left: '平均',
                    mide: '复杂',
                    right: '复杂'
                }
            ],
            tableData4: [
                {
                    type: 'ILF',
                    easy: 'X7',
                    mide: 'X10',
                    complex: 'X15'
                },
                {
                    type: 'EIF',
                    easy: 'X5',
                    mide: 'X7',
                    complex: 'X10'
                },
                {
                    type: 'EI',
                    easy: 'X3',
                    mide: 'X4',
                    complex: 'X6'
                },
                {
                    type: 'EO',
                    easy: 'X4',
                    mide: 'X5',
                    complex: 'X7'
                },
                {
                    type: 'EQ',
                    easy: 'X3',
                    mide: 'X4',
                    complex: 'X6'
                }
            ],
            tableData: [
                {
                    component: 'EI',
                    number: '',
                    A: '',
                    B: '3',
                    C: '',
                    D: '',
                    E: '4',
                    F: '',
                    G: '',
                    H: '6',
                    I: '',
                    unchanged: '',
                },
                {
                    component: 'EO',
                    number: '',
                    A: '',
                    B: '4',
                    C: '',
                    D: '',
                    E: '5',
                    F: '',
                    G: '',
                    H: '7',
                    I: '',
                    unchanged: '',
                },
                {
                    component: 'EQ',
                    number: '',
                    A: '',
                    B: '3',
                    C: '',
                    D: '',
                    E: '4',
                    F: '',
                    G: '',
                    H: '6',
                    I: '',
                    unchanged: '',
                },
                {
                    component: 'ILF',
                    number: '',
                    A: '',
                    B: '7',
                    C: '',
                    D: '',
                    E: '10',
                    F: '',
                    G: '',
                    H: '15',
                    I: '',
                    unchanged: '',
                },
                {
                    component: 'EIF',
                    number: '',
                    A: '',
                    B: '5',
                    C: '',
                    D: '',
                    E: '7',
                    F: '',
                    G: '',
                    H: '10',
                    I: '',
                    unchanged: '',
                },
            ],
            dataadjust: [
                {
                    index: '1',
                    title: 'Requirement for reliable backup and recovery ',
                    grade: '0'

                },
                {
                    index: '2',
                    title: 'Requirement for data communication',
                    grade: '0'

                },
                {
                    index: '3',
                    title: 'Extent of distributed processing ',
                    grade: '0'

                },
                {
                    index: '4',
                    title: 'Performance requirements ',
                    grade: '0'

                },
                {
                    index: '5',
                    title: 'Expected operational environment ',
                    grade: '0'

                },
                {
                    index: '6',
                    title: 'Extent of online data entries ',
                    grade: '0'

                },
                {
                    index: '7',
                    title: 'Extent of multi-screen or multi-operation online data input ',
                    grade: '0'

                },
                {
                    index: '8',
                    title: 'Extent of online updating of master files ',
                    grade: '0'

                },
                {
                    index: '9',
                    title: 'Extent of complex inputs, outputs, online queries and files ',
                    grade: '0'

                },
                {
                    index: '10',
                    title: 'Extent of complex data processing ',
                    grade: '0'

                },
                {
                    index: '11',
                    title: 'Extent that currently developed code can be designed for reuse ',
                    grade: '0'

                },
                {
                    index: '12',
                    title: 'Extent of conversion and installation included in the design ',
                    grade: '0'

                },
                {
                    index: '13',
                    title: 'Extent of multiple installations in an organization and variety of customer organizations ',
                    grade: '0'

                },
                {
                    index: '14',
                    title: 'Extent of change and focus on ease of use ',
                    grade: '0'

                },
            ]
        }
    },
    computed: {
        c: function () {
            return (index) => {
                if (this.tableData.length > 0) {
                    this.tableData[index].C = (parseInt(this.tableData[index].A) ? parseInt(this.tableData[index].A) : 0) * parseInt(this.tableData[index].B)
                    return this.tableData[index].C
                }
            }
        },
        f: function () {
            return (index) => {
                if (this.tableData.length > 0) {
                    this.tableData[index].F = (parseInt(this.tableData[index].D) ? parseInt(this.tableData[index].D) : 0) * parseInt(this.tableData[index].E)
                    return this.tableData[index].F
                }
            }
        },
        i: function () {
            return (index) => {
                if (this.tableData.length > 0) {
                    this.tableData[index].I = (parseInt(this.tableData[index].G) ? parseInt(this.tableData[index].G) : 0) * parseInt(this.tableData[index].H)
                    return this.tableData[index].I
                }
            }
        },
        number: function () {
            return (index) => {
                if (this.tableData.length > 0) {
                    this.tableData[index].number = (parseInt(this.tableData[index].A) ? parseInt(this.tableData[index].A) : 0) + (parseInt(this.tableData[index].D) ? parseInt(this.tableData[index].D) : 0) + (parseInt(this.tableData[index].G) ? parseInt(this.tableData[index].G) : 0)
                    return this.tableData[index].number
                }
            }
        },
        unchanged: function () {
            return (index) => {
                if (this.tableData.length > 0) {
                    this.tableData[index].unchanged = (parseInt(this.tableData[index].C) ? parseInt(this.tableData[index].C) : 0) + (parseInt(this.tableData[index].F) ? parseInt(this.tableData[index].F) : 0) + (parseInt(this.tableData[index].I) ? parseInt(this.tableData[index].I) : 0)

                    var sum = 0
                    for (var i = 0; i < 5; i++)
                        sum += (parseInt(this.tableData[i].unchanged) ? parseInt(this.tableData[i].unchanged) : 0)
                    this.$data.SUM = sum

                    return this.tableData[index].unchanged
                }
            }
        },
        VAF() {
            var vaf = 0
            // console.log('111',this.$data.tableData)
            for (var i = 0; i < 14; i++)
                vaf += (parseInt(this.dataadjust[i].grade) ? parseInt(this.dataadjust[i].grade) : 0)

            vaf = vaf * 0.01 + 0.65
            this.$data.VAF = vaf.toFixed(2)
            return vaf
        },
        SUM_A() {
            var sum = 0
            for (var i = 0; i < 14; i++)
                sum += (parseInt(this.dataadjust[i].grade) ? parseInt(this.dataadjust[i].grade) : 0)
            return sum
        },
        ALL() {
            return (this.$data.SUM * this.$data.VAF).toFixed(2)
        }
    },
    methods: {
        submit() {
            // console.log(this.$data)
            const data = {
                UFP_table: toRaw(this.$data.tableData),
                UFP: this.$data.SUM,
                adjust_table: toRaw(this.$data.dataadjust),
                total_adjust: this.$data.SUM_A,
                VAF: this.$data.VAF,
                FP: (this.$data.SUM * this.$data.VAF).toFixed(2),
            }
            console.log(JSON.stringify(data))
            this.$emit('submit', data)

        },
        saveData() {
            console.log('savaData')
        },
        created() {
            this.gettableData()
        },
        next() {
            if (this.current == 0) {
 
                const content1 = this.$refs.content1;
                this.content.push(content1); 
            }
            if (this.current == 1) {
                const content2 = this.$refs.content2;
                this.content.push(content2);
            }
            if (this.current == 2) {
                const content3 = this.$refs.content3;
                this.content.push(content3);
                this.created();
            }
            if (this.current == 3) {
                const content4 = this.$refs.content4;
                this.content.push(content4);
            }
            if (this.current == 4) {
                const content5 = this.$refs.content5;
                this.content.push(content5);
            }
            if (this.current == 5) {
                const content6 = this.$refs.content6;
                this.content.push(content6);
            }
            if (this.current == 6) {
                const content7 = this.$refs.content7;
                this.content.push(content7);
            }
            if (this.current == 7) {
                const content8 = this.$refs.content8;
                this.content.push(content8);
            }
            if (this.current == 8) {
                const content9 = this.$refs.content9;
                this.content.push(content9);
            }
            if (this.current == 9) {
                const content10 = this.$refs.content10;
                this.content.push(content10);
            }
            this.current++;
        },
        prev() {
            if (this.current == 0) {
                const content1 = this.$refs.content1;
                this.content.push(content1);
            }
            if (this.current == 1) {
                const content2 = this.$refs.content2;
                this.content.push(content2);
            }
            if (this.current == 2) {
                const content3 = this.$refs.content3;
                this.content.push(content3);
            }
            if (this.current == 3) {
                const content4 = this.$refs.content4;
                this.content.push(content4);
            }
            if (this.current == 4) {
                const content5 = this.$refs.content5;
                this.content.push(content5);
            }
            if (this.current == 5) {
                const content6 = this.$refs.content6;
                this.content.push(content6);
            }
            if (this.current == 6) {
                const content7 = this.$refs.content7;
                this.content.push(content7);
            }
            if (this.current == 7) {
                const content8 = this.$refs.content8;
                this.content.push(content8);
            }
            if (this.current == 8) {
                const content9 = this.$refs.content9;
                this.content.push(content9);
            }
            if (this.current == 9) {
                const content10 = this.$refs.content10;
                this.content.push(content10);
            }
            this.current--;
        },
        updated() {
            // 用于防止表格合计行不显示
            this.$nextTick(() => {
                this.$refs['detailTable'].doLayout();
            })
        },

        getSummaries(param, val) {
            const { columns, data } = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = (() => {
                        // let el=<p>未调整功能点</p>
                    })();
                    return;
                }
                if (index === 11) {
                    sums[index] = (() => {
                        // let num=<p >￥{this.tableData[val].nonum.toFixed(2)}</p>
                        // return num;
                    })();
                    return;
                }
            });
            return sums;
        },
        count() {
        },
    }
}
</script> -->

<style scoped>
.title {
    text-align: center;
    font-family: sans-serif;
    font-size: 30px;
}

.secondtitle {
    text-indent: 2em;
    font-weight: bold;
    margin-left: 30px;
    margin-right: 30px;
}

.content {
    text-indent: 2em;
    margin-left: 20px;
    margin-right: 20px;
}

.guidance {
    position: absolute;
    right: 50px;
    font-weight: bold;
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