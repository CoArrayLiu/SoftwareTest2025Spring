<template>
    <div>
        <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        
        <div class="steps-content">
            <div class="exp-step-card" v-if="current == 0" ref="content1">
                <h2 class="bold-black">
                    一、实验目的
                </h2>
                <p class="recontent">
                    学生在通过本次实验要达成以下目标：<br>
                    (1) 掌握软件项目财务评价的建模方法。<br>
                    (2) 理解软件产品/服务的营销与定价策略。<br>
                    (3) 掌握记录和整理实验数据，分析与理解财务评价数据映射关系的能力。<br>
                    (4) 掌握根据财务评价计算结果，判断软件项目盈利能力的能力。
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 1" ref="content2">
                <h2 class="bold-black">
                    二、实验原理
                </h2>
                <p class="recontent">
                    1、软件项目财务评价是在国家现行财税制度和市场价格体系下，分析与测算软件项目或服务的财务效益与投资费用，计算财务评价周期内财务分析指标，考察拟开发软件项目(产品或服务)财务盈利能力，为判断软件项目经济可行性提供依据。<br/>
                    <br/>
                    2、设计和构建软件项目经济分析模型是本实验的基础及关键工作。财务评价模型是由一组财务报表构成。<br/>
                    <br/>
                    3、这些报表可分为基本报表和辅助报表两大类，其中基本报表包括项目投资现金流量表和项目资本金现金流量表；辅助报表包含项目建设投资估算表、流动资金估算表、资金使用计划与资金筹措表、总成本费用估算表和营业收入、税金及附加估算表等。(如下图)<br/>
                    <br/>
                    <el-table :data="repoertFormCategoryTableData" border style="width: 100%" :span-method="repoertFormCategoryTableMergeCells">
                        <el-table-column label="盈利能力分析" prop="type" />
                        <el-table-column label="报表名称" prop="name" />
                        <el-table-column label="评价指标" prop="indicator" />
                    </el-table>
                    <br/>
                    4、在编制上述基本报表过程中，需要依据课程设计项目背景，调查收集本实验所需要的各类投入产出数据及社会经济基础数据，包括(但不限于)投资、建设期利息、流动资金、项目投资及使用计划、资金筹措计划、收入税金及附加、项目运营期总成本等数据。
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 2" ref="content3">
                <h2 class="bold-black">
                    三、实验内容
                </h2>
                <p style="padding-left: 20px;" class="recontent">
                  1、依托课程设计项目，查询、收集、整理及提取软件项目经济分析与评价所需要的各类基础数据。
                  <br/><br/>
                  2、编制实验用辅助报表。学生在本步骤内，需要编制项目建设投资估算表、流动资金估算表、资金使用计划与资金筹措表、总成本费用估算表和营业收入表、增值税金及附加估算表等内容
                  <br/><br/>
                  3、进行融资前分析。在不考虑项目债务融资的条件下，进行项目盈利能力分析。基本步骤如下：
                  <br/><br/>
                  （1）编制项目投资现金流量表，计算项目投资内部收益率、净现值和动态投回收期等指标。
                  <br/><br/>
                  （2）判断：若前述步骤的计算结果表明，本项目的盈利能力符合要求，则再设计融资方案，进行后续步骤的融资后分析。若前述的计算结果不能满足盈利能力要求，则可以通过修改设计进一步完善原有方案。若无必，可据此做出放弃项目的建议
                  <br/><br/>
                  4、根据上述步骤三的计算结果，判断软件项目的盈利能力，且参照软件行业的相应技术经济指标，做出本实验项目的财务评价。
                </p>
            </div>


            <div class="exp-step-card" v-if="current == 3" ref="content4">
                <h2 class="bold-black">
                    四、实验步骤
                </h2>
                <a-steps v-model:current="subCurrent" size="small">
                    <a-step v-for="item in substeps" :key="item.title" :title="item.title" />
                </a-steps>

                <div class="steps-action">

                    <div class="step-card" v-show="subCurrent == 0">
                      <a-button type="primary" @click="resetAllData() ">
                        清空本实验表格数据
                      </a-button>

                      <h2>项目基本信息</h2>
                      <div>
                          <label for="inputNumber">项目规划期（单位：年）：{{ projectPlanningPeriod }} </label>
                      </div>
                      <div>
                          <label for="inputNumber">折现率（单位：%）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="discountRate" 
                            :min="1" 
                            :max="100" 
                          />
                      </div>
                    </div>

                    <div class="step-card" v-show="subCurrent == 1">

                      <h2>附表一 软件开发与运维费</h2>
                      <p>提示：第零年不需要开发人员，开发期间开发人员多于后续运维期</p>
                      <a-table :columns="softwareDevelopPersonsColumns" :data-source="softwareDevelopPersonsTableData" :pagination="false" rowKey="year">
                        <template #bodyCell="{ column, record, index }">
                          <template v-if="column.dataIndex === 'personCount'">
                            <a-input-number
                              v-model:value="softwareDevelopPersons[index]"
                              :min="0"
                              :pagination="false" 
                              bordered
                            />
                          </template>
                          <template v-else>
                            {{ record[column.dataIndex] }}
                          </template>
                        </template>
                      </a-table>
                      <br/>
                      <div>
                          <label for="inputNumber">综合人工成本（单位：万元/人年）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="comprehensieLaborCost" 
                            :min="0" 
                          />
                      </div>
                      <br/>
                      <h2>附表二 设备及工器具购置费</h2>
                      <div>
                          <label for="inputNumber">办公场地装饰布线（单位：万元）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="workingVenueDecorateFee" 
                            :min="0" 
                          />
                          <br/>
                          <label for="inputNumber">计算机与网络设备（单位：万元）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="computerFee" 
                            :min="0" 
                          />
                          <br/>
                          <label for="inputNumber">办公家具（单位：万元）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="workingFurnitureFee" 
                            :min="0" 
                          />
                      </div>
                      <br/>
                      <h2>附表三 工程建设其他费用</h2>
                      <div>
                          <label for="inputNumber">项目开办费（单位：万元）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="proejctStatringFee" 
                            :min="0" 
                          />
                      </div>
                      <div>
                          <label for="inputNumber">建设单位管理费（单位：万元）： </label>
                          <a-input-number 
                            id="inputNumber" 
                            v-model:value="constructUnitManageFee" 
                            :min="0" 
                          />
                      </div>
                      <br/>
                      <h2>附表四 基本预备费</h2>
                      <div>
                          <label>基本预备费（以上三项建设投资合计值10%，取整数）（单位：万元）：</label>
                          <a-typography-text>
                            {{ basicReserveFund[0] }}
                          </a-typography-text>
                      </div>
                      <br/>
                      <a-button type="primary" @click="renewProjectInvestTablePre();">
                        更新项目建设投资表
                      </a-button>
                      <br/>
                      <h2>项目建设投资表</h2>
                      <a-table :dataSource="projectInvestTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" v-model:value="record[column.dataIndex]" style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex] }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                    </div>
                    

                    <div class="step-card" v-show="subCurrent == 2">
                      <div>
                          <label for="inputNumber">期初流动资金（单位：万元）： </label>
                              <a-input-number 
                                id="inputNumber" 
                                v-model:value="initialWorkingCapital" 
                                :min="0" 
                                @change="renewWorkingCapitalTablePre()"
                          />
                      </div>

                      <div>
                          <label for="inputNumber">流动资金年增加额（单位：万元）： </label>
                              <a-input-number 
                                id="inputNumber" 
                                v-model:value="initialWorkingCapitalAnnualIncrease" 
                                :min="0" 
                                @change="renewWorkingCapitalTablePre()"
                          />
                      </div>
                      <br/>

                      <a-button @click="renewWorkingCapitalTablePre()" type="primary">
                        更新流动资金估算表
                      </a-button>

                      <h2>流动资金估算表</h2>
                      <a-table :dataSource="workingCapitalTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" v-model:value="record[column.dataIndex]" style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex] }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                    
                    </div>

                    <div class="step-card" v-show="subCurrent == 3">
                      
                      <a-button type="primary" @click="renewInvestCollectTablePre();">
                        更新资金使用计划与资金筹措表
                      </a-button>

                      <h2>资金使用计划与资金筹措表</h2>
                      <p>为确保项目顺利推进，请确保资本金+贷款>=投资总额</p>
                      <a-table :dataSource="investCollectSourceTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              @change="(e)=>handleInvestSourceChange(column.dataIndex, investCollectSourceTableData.indexOf(record), Number(e.target.value))"
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex] }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>

                    </div>

                    <div class="step-card" v-show="subCurrent == 4">
                      <div>
                          <label for="inputNumber">每年员工底薪（单位：万元）： </label>
                              <a-input-number 
                                id="inputNumber" 
                                v-model:value="employeeBaeSalary" 
                                :min="0" 
                          />
                          <br/>
                          <label for="inputNumber">五险一金占底薪标准（单位：%）： </label>
                              <a-input-number 
                                id="inputNumber" 
                                v-model:value="employeeWelfareRate" 
                                :min="0" 
                          />
                      </div>
                      <br/>
                      <a-button type="primary" @click="renewTotalCostTablePre();">
                        生成总成本费用表
                      </a-button>
                      <h2>附表一 市场营销人员工资福利费用</h2>
                      <a-table :dataSource="employeesSalaryAndWelfareTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              @change="(e)=>handleEmployeesNumChange(column.dataIndex, employeesSalaryAndWelfareTableData.indexOf(record), Number(e.target.value))"
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                      <br/>
                      <h2>附表二 营销费用</h2>
                      <a-table :dataSource="marketingCostTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              @change="(e)=>handleMarketingIncomeChange(column.dataIndex, marketingCostTableData.indexOf(record), Number(e.target.value))"
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                      <br/>
                      <h2>附表三 管理费用</h2>
                      <a-table :dataSource="manageCostTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              @change="(e)=>handleMonthlyManageCostChange(column.dataIndex, manageCostTableData.indexOf(record), Number(e.target.value))"
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                      <br/>
                      <a-button type="primary" @click="renewTotalCostTablePre();">
                        更新总成本费用表
                      </a-button>
                      <h2>总成本费用表</h2>
                      <a-table :dataSource="totalCostTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                      <br/>
                    </div>

                    <div class="step-card" v-show="subCurrent == 5">
                      <p>税率及计算税金基数说明</p>
                      <p>增值税按照销售收入6%计算</p>
                      <p>城市建设维护税按照增值税7%计算</p>
                      <p>教育费附加按照增值税3%计算</p>
                      <a-button type="primary" @click="renewIncomeAndTaxTablePre();">
                          更新营业收入、税金及附加
                      </a-button>
                      <h2>营业收入、税金及附加估算表</h2>
                      <a-table :dataSource="incomeAndTaxTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>
                    </div>

                    <div class="step-card" v-show="subCurrent == 6">
                      <a-button type="primary" @click="renewProjectInvestCashFlowTablePre();">
                        更新项目投资现金流量表
                      </a-button>
                      <h2>项目投资现金流量表</h2>
                      <a-table :dataSource="projectInvestCashFlowTableData" :columns="tableColumns" :pagination="false" bordered  :scroll="{ x: true }">
                        <template #bodyCell="{ column, record }">
                          <template v-if="column.dataIndex !== 'item'">
                            <a-input v-if="record.editable" 
                              v-model:value="record[column.dataIndex]"  
                              style="width: 100%" />
                            <span v-else>{{ record[column.dataIndex].toFixed(precision) }}</span>
                          </template>
                          <template v-else>
                            <span :class="{ 'bold-text': record.isBold }">{{ record[column.dataIndex] }}</span>
                          </template>
                        </template>
                      </a-table>

                      <h2>分析指标</h2>
                      <p>
                        项目财务内部收益率IRR为：
                        {{ ((irrBeforeIncomeTax ?? 0) * 100).toFixed(precision ?? 2) }}%
                      </p>
                      <p>
                        项目财务净现值（i=8%）NPV为：
                        {{ (npvBeforeIncomeTax ?? 0).toFixed(precision ?? 2) }}万元
                      </p>
                      <p>
                        静态投资回收期（含建设期）DPP为：
                        {{ (dppBeforeIncomeTax ?? 0).toFixed(precision ?? 2) }}年
                      </p>

                    </div>


                    <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
                        @click="next"><step-forward-outlined />下一步</a-button>
                    <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>

                  </div>

            </div>


            <div class="exp-step-card" v-if="current == 4" ref="content5">
                <h2 class="bold-black">
                    五、实验心得
                </h2>
                <a-form :model="summary" v-bind="layout" name="nest-messages">
                    <a-form-item label="请输入实验总结" name="summary">
                        <a-textarea v-model:value="summary" />
                    </a-form-item>
                </a-form>
            </div>
        </div>

    </div>
    <div v-if="current == 4" style="margin-top: 20px; display: flex;justify-content: flex-end;">

        <a-spin :spinning="spinning">
            <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
              <EyeOutlined />
              预览报告
            </a-button>

            <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
                <a-button type="primary" style="margin-right: 10px;" @click="submit1()">提交</a-button>
            </div>
        </a-spin>

    </div>
</template>


<script setup lang="ts" >
import { ref ,computed, watch} from 'vue';
import { useStore } from './stores'
import html2canvas from 'html2canvas';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';
const router = useRouter();
const experimentStore = useExperimentStore();
const accountStore = useAccountStore();
import { storeToRefs } from 'pinia';
// 这里和持久化存储有关
const store = useStore()
// 加载动画
const spinning = ref<boolean>(false)
//精度
const precision = ref(2);
const computeIndicatorPrecision = ref(3);
const experimentDate = new Date();
const receivedData = ref(null);


//基本数据
const { 
  projectPlanningPeriod, projectRDPeriod, discountRate,
  //建设投资估算表
  softwareDevelopAndMaintenance,softwareDevelopPersons,comprehensieLaborCost,equipmentAndToolPurchase,engineeringOtherCost,
  basicReserveFund,projectConstructInvest,proejctStatringFee,constructUnitManageFee,workingFurnitureFee,workingVenueDecorateFee,computerFee,
  thatYearConstructInvest,accumulatedConstructInvest,
  //流动资金表
  initialWorkingCapitalAnnualIncrease,initialWorkingCapital,cash,accountsPayable,accountsReceivable,flowDebt,workingCapital,workingCapitalAnnualIncrease,
  //资金使用计划与资金筹措表
  investCollectSource,investTotal,debt,debtBalance,capital,capitalBalance,accumulatedInvestTotal,
  //总成本费用估算表
  annualEmployees,annualEmployeesBaseSalary,annualEmployeesWelfare,annualMarketingCost,annualMarketingIncome,
  operatingCost,totalCost,managementCost,monthlyManagementCost,debtInterestCost,
  salaryAndWelfare,depreciationCost,employeeBaeSalary,employeeWelfareRate,
  //营业收入、税金及附加估算表
  VAT,VATIncomeBulk,VATSalesBulk,eduTax,cityTax,taxAndAttachment,taxAndAttachmentBesidesVAT,
  //项目项目投资现金流量表
  cashFlowIn,grossAnnualMarketingIncome,recollectFixedAssetsResidual,recollectWorkingCapital,
  cashFlowOut,grossProjectConstructInvest,grossOperatingCost,grossWorkingCapital,grossTaxAndAttachment,
  netCashFlowBeforeIncomeTax,accumulatedNetCashFlowBeforeIncomeTax,
  irrBeforeIncomeTax,npvBeforeIncomeTax,dppBeforeIncomeTax,

  summary,
} =
  storeToRefs(useStore());


// 直接从 store 获取 action 方法
const {
  handleProjectPlaningPeriodChange,
  renewProjectInvestTable,
  renewWorkingCapitalTable,
  renewInvestCollectTable,resetInvestCollectTable,
  renewTotalCostTable,resetTotalCostTable,
  renewIncomeAndTaxTable,
  renewProjectInvestCashFlowTable,resetProjectInvestCashFlowTable,
  resetAllData,
} = store;

const tableColumns = computed(()=>{
  const years = projectPlanningPeriod.value;
  const columns = [
    {
      title: '项目/年份',
      dataIndex: 'item',
      width: '10%',
    },
  ];
  for (let i = 0; i <= years; i++) {
    columns.push({
      title: `第${i}年`,
      dataIndex: `year${i}`,  
      width: "10%",
    });
  }

  return columns;
})

//项目投资现金流量表
const projectInvestCashFlowTableData = ref([]);

function renewProjectInvestCashFlowTablePre(){
  renewProjectInvestCashFlowTable();
  updateProjectCashFlowTableData();
  console.log("现金流入"+cashFlowIn.value);
  console.log("营业收入"+grossAnnualMarketingIncome.value);
  console.log("所得税前净现金流"+netCashFlowBeforeIncomeTax.value);
  console.log("建设投资"+grossProjectConstructInvest.value);
  console.log("流动资金年增加额"+workingCapitalAnnualIncrease.value);
}

function updateProjectCashFlowTableData(){
  const table = [
    {
      key: '1',
      item: '现金流入'
    },
    {
      key: '1.1',
      item: '营业收入'
    },
    {
      key:'1.2',
      item: '回收固定资产余值',
    },
    {
      key:'1.3',
      item: '回收流动资金',
    },
    {
      key:'2',
      item: '现金流出',
    },
    {
      key:'2.1',
      item: '建设投资',
    },
    {
      key:'2.2',
      item: '流动资金',
    },
    {
      key:'2.3',
      item: '经营成本',
    },
    {
      key:'2.4',
      item: '营业税金及附加',
    },
    {
      key:'3',
      item: '所得税前净现金流量',
    },
    {
      key:'3.1',
      item: '所得税前净现金流量累计值',
    },
  ];
  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = cashFlowIn.value[i] || 0;
    table[1][`year${i}`] = grossAnnualMarketingIncome.value[i] || 0;
    table[2][`year${i}`] = recollectFixedAssetsResidual.value[i] || 0;
    table[3][`year${i}`] = recollectWorkingCapital.value[i] || 0;
    table[4][`year${i}`] = cashFlowOut.value[i] || 0;
    table[5][`year${i}`] = grossProjectConstructInvest.value[i] || 0;
    table[6][`year${i}`] = grossWorkingCapital.value[i] || 0;
    table[7][`year${i}`] = grossOperatingCost.value[i] || 0;
    table[8][`year${i}`] = grossTaxAndAttachment.value[i] || 0;
    table[9][`year${i}`] = netCashFlowBeforeIncomeTax.value[i] || 0;
    table[10][`year${i}`] = accumulatedNetCashFlowBeforeIncomeTax.value[i] || 0;
  }

  projectInvestCashFlowTableData.value = table;
}

//营业收入、税金及附加估算表
const incomeAndTaxTableData = ref([]);

function renewIncomeAndTaxTablePre(){
  renewIncomeAndTaxTable();
  updateIncomeAndTaxTableData();
}

function updateIncomeAndTaxTableData() {
  const table = [
    {
      key: '1',
      item: '营业收入'
    },
    {
      key: '2',
      item: '税金及附加'
    },
    {
      key:'2.1',
      item: '增值税',
    },
    {
      key:'2.1.1',
      item: '增值税销项税额',
    },
    {
      key:'2.1.2',
      item: '增值税进项税额',
    },
    {
      key:'2.2',
      item: '城市维护建设税',
    },
    {
      key:'2.3',
      item: '教育费附加',
    },
    {
      key:'3',
      item: '税金及附加累计值',
    },
  ];

  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = annualMarketingIncome.value[i] || 0;
    table[1][`year${i}`] = taxAndAttachment.value[i] || 0;
    table[2][`year${i}`] = VAT.value[i] || 0;
    table[3][`year${i}`] = VATSalesBulk.value[i] || 0;
    table[4][`year${i}`] = VATIncomeBulk.value[i] || 0;
    table[5][`year${i}`] = cityTax.value[i] || 0;
    table[6][`year${i}`] = eduTax.value[i] || 0;
    table[7][`year${i}`] = taxAndAttachmentBesidesVAT.value[i] || 0;
  }

  incomeAndTaxTableData.value = table;

}

//总成本估算表
function renewTotalCostTablePre(){
  renewTotalCostTable();
  updateEmployeesSalaryAndWelfareTableData();
  updateMarketingCostTableData();
  updateManageCostTableData();
  updatetotalCostTableData();
}
  //附表一 市场营销人员工资福利费用
  const employeesSalaryAndWelfareTableData = ref([]);
  function updateEmployeesSalaryAndWelfareTableData(){
    const table = [
      {
        key: '1',
        item: '每年员工人数',
        editable:true,
      },
      {
        key: '2',
        item: '每年员工底薪'
      },
      {
        key: '3',
        item: '每年员工五险一金'
      },
    ]

    for (let i = 0; i <= projectPlanningPeriod.value; i++) {
      table[0][`year${i}`] = annualEmployees.value[i] || 0;
      table[1][`year${i}`] = annualEmployeesBaseSalary.value[i] || 0;
      table[2][`year${i}`] = annualEmployeesWelfare.value[i] || 0;
    }

    employeesSalaryAndWelfareTableData.value = table;
  }

  function handleEmployeesNumChange(key:string,index:number,value:number){
    const match = key.match(/year(\d+)/);
    const xuhao = match ? match[1] : null;
    annualEmployees.value[Number(xuhao)] = value;
    renewTotalCostTablePre();
  }

  //附表三：管理费用
  const manageCostTableData = ref([]);
  function updateManageCostTableData(){
    const table = [
      {
        key: '1',
        item: '月管理费用标准（单位：万元/月）',
        editable:true,
      },
      {
        key: '2',
        item: '管理费用'
      },
    ]

    for (let i = 0; i <= projectPlanningPeriod.value; i++) {
      table[0][`year${i}`] = monthlyManagementCost.value[i] || 0;
      table[1][`year${i}`] = managementCost.value[i] || 0;
    }

    manageCostTableData.value = table;
  }

  function handleMonthlyManageCostChange(key:string,index:number,value:number){
    console.log(value)
    const match = key.match(/year(\d+)/);
    const xuhao = match ? match[1] : null;
    if(!isNaN(value))
      monthlyManagementCost.value[Number(xuhao)] = value;
    renewTotalCostTablePre();
  }

  //附表二：营销费用
  const marketingCostTableData = ref([]);
  function updateMarketingCostTableData(){
    const table = [
      {
        key: '1',
        item: '每年销售收入（单位：万元）',
        editable:true,
      },
      {
        key: '2',
        item: '每年营销费用（收入25%）'
      },
    ]

    for (let i = 0; i <= projectPlanningPeriod.value; i++) {
      table[0][`year${i}`] = annualMarketingIncome.value[i] || 0;
      table[1][`year${i}`] = annualMarketingCost.value[i] || 0;
    }

    marketingCostTableData.value = table;
  }

  function handleMarketingIncomeChange(key:string,index:number,value:number){
    const match = key.match(/year(\d+)/);
    const xuhao = match ? match[1] : null;
    annualMarketingIncome.value[Number(xuhao)] = value;
    renewTotalCostTablePre();
  }

//总成本费用总表
const totalCostTableData = ref([]);
function updatetotalCostTableData(){
  const table = [
    {
      key: '1',
      item: '工资及福利费',
    },
    {
      key: '2',
      item: '营销费用'
    },
    {
      key: '3',
      item: '管理费用'
    },
    {
      key: '4',
      item: '经营成本（1+2+3）'
    },
    {
      key: '5',
      item: '折旧费'
    },
    {
      key: '6',
      item: '利息支出'
    },
    {
      key: '7',
      item: '总成本费用'
    },
  ]

  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = salaryAndWelfare.value[i] || 0;
    table[1][`year${i}`] = annualMarketingCost.value[i] || 0;
    table[2][`year${i}`] = managementCost.value[i] || 0;
    table[3][`year${i}`] = operatingCost.value[i] || 0;
    table[4][`year${i}`] = depreciationCost.value[i] || 0;
    table[5][`year${i}`] = debtInterestCost.value[i] || 0;
    table[6][`year${i}`] = totalCost.value[i] || 0;
  }

  totalCostTableData.value = table;
}

//资金使用计划与资金筹措表
const investCollectSourceTableData = ref([]);

function renewInvestCollectTablePre(){
  renewInvestCollectTable();
  updateInvestCollectSourceTableData();
}

function updateInvestCollectSourceTableData() {
  const table = [
    {
      key: '1',
      item: '投资总额'
    },
    {
      key: '1.1',
      item: '建设投资'
    },
    {
      key:'1.2',
      item: '流动资金',
    },
    {
      key:'2',
      item: '筹措资金来源',
    },
    {
      key:'2.1',
      item: '资本金',
      editable: true,
    },
    {
      key:'2.1.1',
      item: '资本金余额',
    },
    {
      key:'2.2',
      item: '贷款（五年期）',
      editable: true,
    },
    {
      key:'2.2.1',
      item: '贷款余额',
    },
    {
      key:'3',
      item: '投资总额累计值',
    },
  ];

  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = investTotal.value[i] || 0;
    table[1][`year${i}`] = projectConstructInvest.value[i] || 0;
    table[2][`year${i}`] = workingCapitalAnnualIncrease.value[i] || 0;
    table[3][`year${i}`] = investCollectSource.value[i] || 0;
    table[4][`year${i}`] = capital.value[i] || 0;
    table[5][`year${i}`] = capitalBalance.value[i] || 0;
    table[6][`year${i}`] = debt.value[i] || 0;
    table[7][`year${i}`] = debtBalance.value[i] || 0;
    table[8][`year${i}`] = accumulatedInvestTotal.value[i] || 0;
  }

  investCollectSourceTableData.value = table;

}

function handleInvestSourceChange(key:string,index:number,value:number){
  const match = key.match(/year(\d+)/);
  const xuhao = match ? match[1] : null;
  if(index==4){
    capital.value[Number(xuhao)]=value ? value:0;
  }
  else if(index==6){
    debt.value[Number(xuhao)]=value ? value:0;
  }
  renewInvestCollectTablePre();
}

//流动资金表
const workingCapitalTableData = ref([]);

function updateWorkingCapitalTableData() {
  const table = [
    {
      key: '1',
      item: '流动资产'
    },
    {
      key: '1.1',
      item: '现金'
    },
    {
      key:'1.2',
      item: '应收账款',
    },
    {
      key:'2',
      item: '流动负债',
    },
    {
      key:'2.1',
      item: '应付账款',
    },
    {
      key:'3',
      item: '流动资金',
    },
    {
      key:'4',
      item: '流动资金年增加额',
    },
  ];

  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = workingCapital.value[i] || 0;
    table[1][`year${i}`] = cash.value[i] || 0;
    table[2][`year${i}`] = accountsReceivable.value[i] || 0;
    table[3][`year${i}`] = flowDebt.value[i] || 0;
    table[4][`year${i}`] = accountsPayable.value[i] || 0;
    table[5][`year${i}`] = workingCapital.value[i] || 0;
    table[6][`year${i}`] = workingCapitalAnnualIncrease.value[i] || 0;
  }

  workingCapitalTableData.value = table;
}

function renewWorkingCapitalTablePre(){
  renewWorkingCapitalTable();
  updateWorkingCapitalTableData();
}

//项目投资估算表
function renewProjectInvestTablePre(){
  renewProjectInvestTable();
  updateProjectInvestTableData();
  console.log("项目建设投资"+projectConstructInvest.value);
  console.log("软件开发人数"+softwareDevelopPersons.value);
  console.log("软件运维费用"+softwareDevelopAndMaintenance.value);
  console.log("设备及工器具购置费"+equipmentAndToolPurchase.value);
  console.log("工程建设其他花销"+engineeringOtherCost.value);
  console.log("基本预备费"+basicReserveFund.value);
}

// 表格列定义
const softwareDevelopPersonsColumns = [
  {
    title: '年份',
    dataIndex: 'year',
    key: 'year',
  },
  {
    title: '开发人员数',
    dataIndex: 'personCount',
    key: 'personCount',
  },
];

// 构造表格数据
const softwareDevelopPersonsTableData = computed(() =>
  softwareDevelopPersons.value.map((count, index) => ({
    year: `第 ${index} 年`,
    personCount: count,
  }))
);


const projectInvestTableData = ref([]);

function updateProjectInvestTableData() {
  const table = [
    {
      key: '1',
      item: '建设投资'
    },
    {
      key: '1.1',
      item: '软件开发与运维费'
    },
    {
      key:'1.2',
      item: '设备及工器具购置费',
    },
    {
      key:'1.3',
      item: '工程建设及其他费用',
    },
    {
      key:'1.4',
      item: '基本预备费',
    },
    {
      key:'2',
      item: '当年建设投资',
    },
    {
      key:'2.1',
      item: '建设投资累计值',
    },
  ];

  for (let i = 0; i <= projectPlanningPeriod.value; i++) {
    table[0][`year${i}`] = projectConstructInvest.value[i] || 0;
    table[1][`year${i}`] = softwareDevelopAndMaintenance.value[i] || 0;
    table[2][`year${i}`] = equipmentAndToolPurchase.value[i] || 0;
    table[3][`year${i}`] = engineeringOtherCost.value[i] || 0;
    table[4][`year${i}`] = basicReserveFund.value[i] || 0;
    table[5][`year${i}`] = thatYearConstructInvest.value[i] || 0;
    table[6][`year${i}`] = accumulatedConstructInvest.value[i] || 0;
  }

  projectInvestTableData.value = table;
}






// 当前实验步骤的游标
const current = ref(0)

const layout = {
    labelCol: {
        span: 4,
    },
    wrapperCol: {
        span: 16,
    },
}

const content = ref<any>([])
// 根据实际上的实验步数进行修改
const steps = [
{
    title: '实验目的',
    content: '0-content',
},
{
    title: '实验原理',
    content: '1-content',
},
{
    title: '实验内容',
    content: '2-content',
},
{
    title: '实验步骤',
    content: '3-content',
},
{
    title: '实验心得',
    content: '4-content',
},
]


const subCurrent = ref(0);
const substeps = ref([
  { title: '基本数据', disabled: false },
  { title: '项目投资估算表', disabled: false },
  { title: '流动资金估算表', disabled: false },
  { title: '资金使用计划与资金筹措表', disabled: false },
  { title: '总成本费用估算表', disabled: false },
  { title: '营业收入及税金表', disabled: false },
  { title: '项目投资现金流量表', disabled: false },
]);
const processes = computed(() => substeps.value.length);

const next =()=> {
  if (current.value == 3) {
    // 如果在步骤栏下
    if (subCurrent.value >= 0 && subCurrent.value <= processes.value-2) {
      if (substeps.value[subCurrent.value + 1].disabled == false) {
        subCurrent.value += 1;
      }
    } else {
      current.value = 4;
    }
  } else {
    // current.value = Math.min(steps.value.length - 1, current.value + 1);
    current.value += 1;
  }
}

const prev =()=> {
  if (current.value == 3) {
    // 如果在步骤栏下
    if (subCurrent.value == 0) {
      current.value = 2;
    } else {
      subCurrent.value -= 1;
    }
  } else {
    current.value = Math.max(0, current.value - 1);
  }
}


// 这里定义父级的提交函数
// 数据是：实验页面的数据收集到data，点击按钮的时候触发这里的submit函数，传参，再调用父级的submit
// 父级结合控制信息和数据信息，调用接口，提交数据
// **不需要修改**
const emit = defineEmits({
    submit: (data: any) => {
        return true
    }
})


async function downLoadFile() {
  // 先准备基础数据
  const finalData = {
    experiment_id: "73",
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
  };

  // 如果没有先提交数据，先执行提交操作

    // 如果提交后仍然没有数据，显示错误
    if (!receivedData.value) {
      message.error('报告数据未准备好，请先提交报告数据。');
      return;
    }
  
  // 使用从子组件接收的数据来填充 reportData
  const reportData = {
    experiment_id: 73,  // 确保ID一致，使用数字格式
    course_id: accountStore.course.courseId,
    ...finalData,
    ...receivedData.value,
  };

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('tanpaifang_page_state', JSON.stringify({
      current: current.value,
      hasSubmitted: true,
    }));
  } catch (e) {
    console.error('保存页面状态失败', e);
  }

  console.log('即将发送报告数据:', reportData);

  spinning.value = true;
  try {
    // 路由跳转到报告预览页面，传递报告数据参数
    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(reportData))
      }
    });
  } catch (error) {
    console.error('跳转到报告预览页面失败:', error);
    message.error('预览报告失败，请稍后重试');
  } finally {
    spinning.value = false;
  }
}
// 不需要上传图表的逻辑
const submit1 = () => {
    spinning.value = true;
    const data = {
        //项目规划期
        projectPlanningPeriod: projectPlanningPeriod.value,
        //项目投资估算表相关数据
          //开发人员数
          softwareDevelopPersons0: softwareDevelopPersons.value[0].toFixed(2),
          softwareDevelopPersons1: softwareDevelopPersons.value[1].toFixed(2),
          softwareDevelopPersons2: softwareDevelopPersons.value[2].toFixed(2),
          softwareDevelopPersons3: softwareDevelopPersons.value[3].toFixed(2),
          softwareDevelopPersons4: softwareDevelopPersons.value[4].toFixed(2),
          softwareDevelopPersons5: softwareDevelopPersons.value[5].toFixed(2),
          //综合人工成本
          comprehensieLaborCost: comprehensieLaborCost.value,
          //办公场地装饰布线
          workingVenueDecorateFee: workingVenueDecorateFee.value,
          //办公与网络设备
          computerFee: computerFee.value,
          //办公家具
          workingFurnitureFee: workingFurnitureFee.value,
          //项目开办费
          proejctStatringFee: proejctStatringFee.value,
          //建设单位管理费
          constructUnitManageFee: constructUnitManageFee.value,
          //基本预备费
          basicReserveFund: basicReserveFund.value[0],
          //项目建设投资
          projectConstructInvest0: projectConstructInvest.value[0].toFixed(2),
          projectConstructInvest1: projectConstructInvest.value[1].toFixed(2),
          projectConstructInvest2: projectConstructInvest.value[2].toFixed(2),
          projectConstructInvest3: projectConstructInvest.value[3].toFixed(2),
          projectConstructInvest4: projectConstructInvest.value[4].toFixed(2),
          projectConstructInvest5: projectConstructInvest.value[5].toFixed(2),
          //软件开发与运维费
          softwareDevelopAndMaintenance0: softwareDevelopAndMaintenance.value[0].toFixed(2),
          softwareDevelopAndMaintenance1: softwareDevelopAndMaintenance.value[1].toFixed(2),
          softwareDevelopAndMaintenance2: softwareDevelopAndMaintenance.value[2].toFixed(2),
          softwareDevelopAndMaintenance3: softwareDevelopAndMaintenance.value[3].toFixed(2),
          softwareDevelopAndMaintenance4: softwareDevelopAndMaintenance.value[4].toFixed(2),
          softwareDevelopAndMaintenance5: softwareDevelopAndMaintenance.value[5].toFixed(2),
          //设备及工器具购置费
          equipmentAndToolPurchase0: equipmentAndToolPurchase.value[0].toFixed(2),
          equipmentAndToolPurchase1: equipmentAndToolPurchase.value[1].toFixed(2),
          equipmentAndToolPurchase2: equipmentAndToolPurchase.value[2].toFixed(2),
          equipmentAndToolPurchase3: equipmentAndToolPurchase.value[3].toFixed(2),
          equipmentAndToolPurchase4: equipmentAndToolPurchase.value[4].toFixed(2),
          equipmentAndToolPurchase5: equipmentAndToolPurchase.value[5].toFixed(2),
          //工程建设其他费用
          engineeringOtherCost0: engineeringOtherCost.value[0].toFixed(2),
          engineeringOtherCost1: engineeringOtherCost.value[1].toFixed(2),
          engineeringOtherCost2: engineeringOtherCost.value[2].toFixed(2),
          engineeringOtherCost3: engineeringOtherCost.value[3].toFixed(2),
          engineeringOtherCost4: engineeringOtherCost.value[4].toFixed(2),
          engineeringOtherCost5: engineeringOtherCost.value[5].toFixed(2),
          //基本预备费
          basicReserveFund0: basicReserveFund.value[0],
          basicReserveFund1: basicReserveFund.value[1].toFixed(2),
          basicReserveFund2: basicReserveFund.value[2].toFixed(2),
          basicReserveFund3: basicReserveFund.value[3].toFixed(2),
          basicReserveFund4: basicReserveFund.value[4].toFixed(2),
          basicReserveFund5: basicReserveFund.value[5].toFixed(2),
          //当年建设投资
          thatYearConstructInvest0: thatYearConstructInvest.value[0].toFixed(2),
          thatYearConstructInvest1: thatYearConstructInvest.value[1].toFixed(2),
          thatYearConstructInvest2: thatYearConstructInvest.value[2].toFixed(2),
          thatYearConstructInvest3: thatYearConstructInvest.value[3].toFixed(2),
          thatYearConstructInvest4: thatYearConstructInvest.value[4].toFixed(2),
          thatYearConstructInvest5: thatYearConstructInvest.value[5].toFixed(2),
          //建设投资累计值
          accumulatedConstructInvest0: accumulatedConstructInvest.value[0].toFixed(2),
          accumulatedConstructInvest1: accumulatedConstructInvest.value[1].toFixed(2),
          accumulatedConstructInvest2: accumulatedConstructInvest.value[2].toFixed(2),
          accumulatedConstructInvest3: accumulatedConstructInvest.value[3].toFixed(2),
          accumulatedConstructInvest4: accumulatedConstructInvest.value[4].toFixed(2),
          accumulatedConstructInvest5: accumulatedConstructInvest.value[5].toFixed(2),
        
        //流动资金估算表
        initialWorkingCapital: initialWorkingCapital.value,
        initialWorkingCapitalAnnualIncrease: initialWorkingCapitalAnnualIncrease.value,
          //流动资产
          workingCapital0: workingCapital.value[0].toFixed(2),
          workingCapital1: workingCapital.value[1].toFixed(2),
          workingCapital2: workingCapital.value[2].toFixed(2),
          workingCapital3: workingCapital.value[3].toFixed(2),
          workingCapital4: workingCapital.value[4].toFixed(2),
          workingCapital5: workingCapital.value[5].toFixed(2),
          //现金
          cash0: cash.value[0].toFixed(2),
          cash1: cash.value[1].toFixed(2),
          cash2: cash.value[2].toFixed(2),
          cash3: cash.value[3].toFixed(2),
          cash4: cash.value[4].toFixed(2),
          cash5: cash.value[5].toFixed(2),
          //应收账款
          accountsReceivable0: accountsReceivable.value[0].toFixed(2),
          accountsReceivable1: accountsReceivable.value[1].toFixed(2),
          accountsReceivable2: accountsReceivable.value[2].toFixed(2),
          accountsReceivable3: accountsReceivable.value[3].toFixed(2),
          accountsReceivable4: accountsReceivable.value[4].toFixed(2),
          accountsReceivable5: accountsReceivable.value[5].toFixed(2),
          //流动负债
          flowDebt0: flowDebt.value[0].toFixed(2),
          flowDebt1: flowDebt.value[1].toFixed(2),
          flowDebt2: flowDebt.value[2].toFixed(2),
          flowDebt3: flowDebt.value[3].toFixed(2),
          flowDebt4: flowDebt.value[4].toFixed(2),
          flowDebt5: flowDebt.value[5].toFixed(2),
          //应付账款
          accountsPayable0: accountsPayable.value[0].toFixed(2),
          accountsPayable1: accountsPayable.value[1].toFixed(2),
          accountsPayable2: accountsPayable.value[2].toFixed(2),
          accountsPayable3: accountsPayable.value[3].toFixed(2),
          accountsPayable4: accountsPayable.value[4].toFixed(2),
          accountsPayable5: accountsPayable.value[5].toFixed(2),
          //流动资金同流动资产
          //流动资金年增加额
          workingCapitalAnnualIncrease0: workingCapitalAnnualIncrease.value[0].toFixed(2),
          workingCapitalAnnualIncrease1: workingCapitalAnnualIncrease.value[1].toFixed(2),
          workingCapitalAnnualIncrease2: workingCapitalAnnualIncrease.value[2].toFixed(2),
          workingCapitalAnnualIncrease3: workingCapitalAnnualIncrease.value[3].toFixed(2),
          workingCapitalAnnualIncrease4: workingCapitalAnnualIncrease.value[4].toFixed(2),
          workingCapitalAnnualIncrease5: workingCapitalAnnualIncrease.value[5].toFixed(2),
         
        //资金使用计划与资金筹措表
          //投资总额
          investTotal0: investTotal.value[0].toFixed(2),
          investTotal1: investTotal.value[1].toFixed(2),
          investTotal2: investTotal.value[2].toFixed(2),
          investTotal3: investTotal.value[3].toFixed(2),
          investTotal4: investTotal.value[4].toFixed(2),
          investTotal5: investTotal.value[5].toFixed(2),
          //建设投资
          //流动资金
          //筹措资金来源
          investCollectSource0: investCollectSource.value[0].toFixed(2),
          investCollectSource1: investCollectSource.value[1].toFixed(2),
          investCollectSource2: investCollectSource.value[2].toFixed(2),
          investCollectSource3: investCollectSource.value[3].toFixed(2),
          investCollectSource4: investCollectSource.value[4].toFixed(2),
          investCollectSource5: investCollectSource.value[5].toFixed(2),
          //资本金
          capital0: capital.value[0].toFixed(2),
          capital1: capital.value[1].toFixed(2),
          capital2: capital.value[2].toFixed(2),
          capital3: capital.value[3].toFixed(2),
          capital4: capital.value[4].toFixed(2),
          capital5: capital.value[5].toFixed(2),
          //资本金余额
          capitalBalance0: capitalBalance.value[0].toFixed(2),
          capitalBalance1: capitalBalance.value[1].toFixed(2),
          capitalBalance2: capitalBalance.value[2].toFixed(2),
          capitalBalance3: capitalBalance.value[3].toFixed(2),
          capitalBalance4: capitalBalance.value[4].toFixed(2),
          capitalBalance5: capitalBalance.value[5].toFixed(2),
          //贷款
          debt0: debt.value[0].toFixed(2),
          debt1: debt.value[1].toFixed(2),
          debt2: debt.value[2].toFixed(2),
          debt3: debt.value[3].toFixed(2),
          debt4: debt.value[4].toFixed(2),
          debt5: debt.value[5].toFixed(2),
          //贷款余额
          debtBalance0: debtBalance.value[0].toFixed(2),
          debtBalance1: debtBalance.value[1].toFixed(2),
          debtBalance2: debtBalance.value[2].toFixed(2),
          debtBalance3: debtBalance.value[3].toFixed(2),
          debtBalance4: debtBalance.value[4].toFixed(2),
          debtBalance5: debtBalance.value[5].toFixed(2),
          //投资总额累计值
          accumulatedInvestTotal0: accumulatedInvestTotal.value[0].toFixed(2),
          accumulatedInvestTotal1: accumulatedInvestTotal.value[1].toFixed(2),
          accumulatedInvestTotal2: accumulatedInvestTotal.value[2].toFixed(2),
          accumulatedInvestTotal3: accumulatedInvestTotal.value[3].toFixed(2),
          accumulatedInvestTotal4: accumulatedInvestTotal.value[4].toFixed(2),
          accumulatedInvestTotal5: accumulatedInvestTotal.value[5].toFixed(2),
        
        //总成本费用估算表
        //每年员工底薪
        employeeBaeSalary: employeeBaeSalary.value,
        //五险一金占底薪标准
        employeeWelfareRate: employeeWelfareRate.value,
          //附表一 市场营销人员工资福利费用
          //每年员工人数
          annualEmployees0: annualEmployees.value[0],
          annualEmployees1: annualEmployees.value[1],
          annualEmployees2: annualEmployees.value[2],
          annualEmployees3: annualEmployees.value[3],
          annualEmployees4: annualEmployees.value[4],
          annualEmployees5: annualEmployees.value[5],
          //每年员工底薪
          annualEmployeesBaseSalary0: annualEmployeesBaseSalary.value[0].toFixed(2),
          annualEmployeesBaseSalary1: annualEmployeesBaseSalary.value[1].toFixed(2),
          annualEmployeesBaseSalary2: annualEmployeesBaseSalary.value[2].toFixed(2),
          annualEmployeesBaseSalary3: annualEmployeesBaseSalary.value[3].toFixed(2),
          annualEmployeesBaseSalary4: annualEmployeesBaseSalary.value[4].toFixed(2),
          annualEmployeesBaseSalary5: annualEmployeesBaseSalary.value[5].toFixed(2),
          //每年员工五险一金
          annualEmployeesWelfare0: annualEmployeesWelfare.value[0].toFixed(2),
          annualEmployeesWelfare1: annualEmployeesWelfare.value[1].toFixed(2),
          annualEmployeesWelfare2: annualEmployeesWelfare.value[2].toFixed(2),
          annualEmployeesWelfare3: annualEmployeesWelfare.value[3].toFixed(2),
          annualEmployeesWelfare4: annualEmployeesWelfare.value[4].toFixed(2),
          annualEmployeesWelfare5: annualEmployeesWelfare.value[5].toFixed(2),
          //附表二 营销费用
          //每年销售收入
          annualMarketingIncome0: annualMarketingIncome.value[0].toFixed(2),
          annualMarketingIncome1: annualMarketingIncome.value[1].toFixed(2),
          annualMarketingIncome2: annualMarketingIncome.value[2].toFixed(2),
          annualMarketingIncome3: annualMarketingIncome.value[3].toFixed(2),
          annualMarketingIncome4: annualMarketingIncome.value[4].toFixed(2),
          annualMarketingIncome5: annualMarketingIncome.value[5].toFixed(2),
          //每年营销费用
          annualMarketingCost0:annualMarketingCost.value[0].toFixed(2),
          annualMarketingCost1:annualMarketingCost.value[1].toFixed(2),
          annualMarketingCost2:annualMarketingCost.value[2].toFixed(2),
          annualMarketingCost3:annualMarketingCost.value[3].toFixed(2),
          annualMarketingCost4:annualMarketingCost.value[4].toFixed(2),
          annualMarketingCost5:annualMarketingCost.value[5].toFixed(2),
          //附表三 管理费用
          //月管理费用标准
          monthlyManagementCost0: monthlyManagementCost.value[0].toFixed(2),
          monthlyManagementCost1: monthlyManagementCost.value[1].toFixed(2),
          monthlyManagementCost2: monthlyManagementCost.value[2].toFixed(2),
          monthlyManagementCost3: monthlyManagementCost.value[3].toFixed(2),
          monthlyManagementCost4: monthlyManagementCost.value[4].toFixed(2),
          monthlyManagementCost5: monthlyManagementCost.value[5].toFixed(2),
          //管理费用
          managementCost0: managementCost.value[0].toFixed(2),
          managementCost1: managementCost.value[1].toFixed(2),
          managementCost2: managementCost.value[2].toFixed(2),
          managementCost3: managementCost.value[3].toFixed(2),
          managementCost4: managementCost.value[4].toFixed(2),
          managementCost5: managementCost.value[5].toFixed(2),
          //总成本费用表
          //工资及福利费
          salaryAndWelfare0: salaryAndWelfare.value[0].toFixed(2),
          salaryAndWelfare1: salaryAndWelfare.value[1].toFixed(2),
          salaryAndWelfare2: salaryAndWelfare.value[2].toFixed(2),
          salaryAndWelfare3: salaryAndWelfare.value[3].toFixed(2),
          salaryAndWelfare4: salaryAndWelfare.value[4].toFixed(2),
          salaryAndWelfare5: salaryAndWelfare.value[5].toFixed(2),
          //营销费用
          //管理费用
          //经营成本
          operatingCost0: operatingCost.value[0].toFixed(2),
          operatingCost1: operatingCost.value[1].toFixed(2),
          operatingCost2: operatingCost.value[2].toFixed(2),
          operatingCost3: operatingCost.value[3].toFixed(2),
          operatingCost4: operatingCost.value[4].toFixed(2),
          operatingCost5: operatingCost.value[5].toFixed(2),
          //折旧费
          depreciationCost0: depreciationCost.value[0].toFixed(2),
          depreciationCost1: depreciationCost.value[1].toFixed(2),
          depreciationCost2: depreciationCost.value[2].toFixed(2),
          depreciationCost3: depreciationCost.value[3].toFixed(2),
          depreciationCost4: depreciationCost.value[4].toFixed(2),
          depreciationCost5: depreciationCost.value[5].toFixed(2),
          //利息支出
          debtInterestCost0: debtInterestCost.value[0].toFixed(2),
          debtInterestCost1: debtInterestCost.value[1].toFixed(2),
          debtInterestCost2: debtInterestCost.value[2].toFixed(2),
          debtInterestCost3: debtInterestCost.value[3].toFixed(2),
          debtInterestCost4: debtInterestCost.value[4].toFixed(2),
          debtInterestCost5: debtInterestCost.value[5].toFixed(2),
          //总成本费用
          totalCost0: totalCost.value[0].toFixed(2),
          totalCost1: totalCost.value[1].toFixed(2),
          totalCost2: totalCost.value[2].toFixed(2),
          totalCost3: totalCost.value[3].toFixed(2),
          totalCost4: totalCost.value[4].toFixed(2),
          totalCost5: totalCost.value[5].toFixed(2),

        //营业收入税金及附加表
        //营业收入
        //税金及附加
        taxAndAttachment0: taxAndAttachment.value[0].toFixed(2),
        taxAndAttachment1: taxAndAttachment.value[1].toFixed(2),
        taxAndAttachment2: taxAndAttachment.value[2].toFixed(2),
        taxAndAttachment3: taxAndAttachment.value[3].toFixed(2),
        taxAndAttachment4: taxAndAttachment.value[4].toFixed(2),
        taxAndAttachment5: taxAndAttachment.value[5].toFixed(2),
        //增值税
        VAT0: VAT.value[0].toFixed(2),
        VAT1: VAT.value[1].toFixed(2),
        VAT2: VAT.value[2].toFixed(2),
        VAT3: VAT.value[3].toFixed(2),
        VAT4: VAT.value[4].toFixed(2),
        VAT5: VAT.value[5].toFixed(2),
        //增值税销项税额
        VATSalesBulk0: VATSalesBulk.value[0].toFixed(2),
        VATSalesBulk1: VATSalesBulk.value[1].toFixed(2),
        VATSalesBulk2: VATSalesBulk.value[2].toFixed(2),
        VATSalesBulk3: VATSalesBulk.value[3].toFixed(2),
        VATSalesBulk4: VATSalesBulk.value[4].toFixed(2),
        VATSalesBulk5: VATSalesBulk.value[5].toFixed(2),
        //增值税进项税额
        VATIncomeBulk0: VATIncomeBulk.value[0].toFixed(2),
        VATIncomeBulk1: VATIncomeBulk.value[1].toFixed(2),
        VATIncomeBulk2: VATIncomeBulk.value[2].toFixed(2),
        VATIncomeBulk3: VATIncomeBulk.value[3].toFixed(2),
        VATIncomeBulk4: VATIncomeBulk.value[4].toFixed(2),
        VATIncomeBulk5: VATIncomeBulk.value[5].toFixed(2),
        //城市维护建设税
        cityTax0: cityTax.value[0].toFixed(2),
        cityTax1: cityTax.value[1].toFixed(2),
        cityTax2: cityTax.value[2].toFixed(2),
        cityTax3: cityTax.value[3].toFixed(2),
        cityTax4: cityTax.value[4].toFixed(2),
        cityTax5: cityTax.value[5].toFixed(2),
        //教育费附加
        eduTax0: eduTax.value[0].toFixed(2),
        eduTax1: eduTax.value[1].toFixed(2),
        eduTax2: eduTax.value[2].toFixed(2),
        eduTax3: eduTax.value[3].toFixed(2),
        eduTax4: eduTax.value[4].toFixed(2),
        eduTax5: eduTax.value[5].toFixed(2),
        //税金及附加累计值
        taxAndAttachmentBesidesVAT0: taxAndAttachmentBesidesVAT.value[0].toFixed(2),
        taxAndAttachmentBesidesVAT1: taxAndAttachmentBesidesVAT.value[1].toFixed(2),
        taxAndAttachmentBesidesVAT2: taxAndAttachmentBesidesVAT.value[2].toFixed(2),
        taxAndAttachmentBesidesVAT3: taxAndAttachmentBesidesVAT.value[3].toFixed(2),
        taxAndAttachmentBesidesVAT4: taxAndAttachmentBesidesVAT.value[4].toFixed(2),
        taxAndAttachmentBesidesVAT5: taxAndAttachmentBesidesVAT.value[5].toFixed(2),
            
      //项目投资现金流量表
        //现金流入
        cashFlowIn0: cashFlowIn.value[0].toFixed(2),
        cashFlowIn1: cashFlowIn.value[1].toFixed(2),
        cashFlowIn2: cashFlowIn.value[2].toFixed(2),
        cashFlowIn3: cashFlowIn.value[3].toFixed(2),
        cashFlowIn4: cashFlowIn.value[4].toFixed(2),
        cashFlowIn5: cashFlowIn.value[5].toFixed(2),
        //营业收入
        //回收固定资产余值
        recollectFixedAssetsResidual0: recollectFixedAssetsResidual.value[0].toFixed(2),
        recollectFixedAssetsResidual1: recollectFixedAssetsResidual.value[1].toFixed(2),
        recollectFixedAssetsResidual2: recollectFixedAssetsResidual.value[2].toFixed(2),
        recollectFixedAssetsResidual3: recollectFixedAssetsResidual.value[3].toFixed(2),
        recollectFixedAssetsResidual4: recollectFixedAssetsResidual.value[4].toFixed(2),
        recollectFixedAssetsResidual5: recollectFixedAssetsResidual.value[5].toFixed(2),
        //回收流动资金
        recollectWorkingCapital0: recollectWorkingCapital.value[0].toFixed(2),
        recollectWorkingCapital1: recollectWorkingCapital.value[1].toFixed(2),
        recollectWorkingCapital2: recollectWorkingCapital.value[2].toFixed(2),
        recollectWorkingCapital3: recollectWorkingCapital.value[3].toFixed(2),
        recollectWorkingCapital4: recollectWorkingCapital.value[4].toFixed(2),
        recollectWorkingCapital5: recollectWorkingCapital.value[5].toFixed(2),
        //现金流出
        cashFlowOut0: cashFlowOut.value[0].toFixed(2),
        cashFlowOut1: cashFlowOut.value[1].toFixed(2),
        cashFlowOut2: cashFlowOut.value[2].toFixed(2),
        cashFlowOut3: cashFlowOut.value[3].toFixed(2),
        cashFlowOut4: cashFlowOut.value[4].toFixed(2),
        cashFlowOut5: cashFlowOut.value[5].toFixed(2),
        //所得税前净现金流量
        netCashFlowBeforeIncomeTax0: netCashFlowBeforeIncomeTax.value[0].toFixed(2),
        netCashFlowBeforeIncomeTax1: netCashFlowBeforeIncomeTax.value[1].toFixed(2),
        netCashFlowBeforeIncomeTax2: netCashFlowBeforeIncomeTax.value[2].toFixed(2),
        netCashFlowBeforeIncomeTax3: netCashFlowBeforeIncomeTax.value[3].toFixed(2),
        netCashFlowBeforeIncomeTax4: netCashFlowBeforeIncomeTax.value[4].toFixed(2),
        netCashFlowBeforeIncomeTax5: netCashFlowBeforeIncomeTax.value[5].toFixed(2),
        //所得税前净现金流量累计值
        accumulatedNetCashFlowBeforeIncomeTax0: accumulatedNetCashFlowBeforeIncomeTax.value[0].toFixed(2),
        accumulatedNetCashFlowBeforeIncomeTax1: accumulatedNetCashFlowBeforeIncomeTax.value[1].toFixed(2),
        accumulatedNetCashFlowBeforeIncomeTax2: accumulatedNetCashFlowBeforeIncomeTax.value[2].toFixed(2),
        accumulatedNetCashFlowBeforeIncomeTax3: accumulatedNetCashFlowBeforeIncomeTax.value[3].toFixed(2),
        accumulatedNetCashFlowBeforeIncomeTax4: accumulatedNetCashFlowBeforeIncomeTax.value[4].toFixed(2),
        accumulatedNetCashFlowBeforeIncomeTax5: accumulatedNetCashFlowBeforeIncomeTax.value[5].toFixed(2),
      
      //npv
      npvBeforeIncomeTax: npvBeforeIncomeTax.value.toFixed(2),
      //irr
      irrBeforeIncomeTax: (irrBeforeIncomeTax.value*100).toFixed(2),
      //dpp
      dppBeforeIncomeTax: dppBeforeIncomeTax.value.toFixed(2),

        //总结
        summary: summary.value,

    }
    localStorage.setItem('experimentData', JSON.stringify(data));
    // 保存数据到receivedData
    receivedData.value = data;
    console.log(receivedData.value);
    
    // 数据提取完毕以后调用父组件的submit方法，并传递数据
    emit('submit', data)
    // 关闭加载动画
    spinning.value = false
}

const repoertFormCategoryTableData = ref([
  { type: '基本报表', name: '项目投资现金流量表', indicator: '财务净现值 NPV,\n财务内部收益率 IRR,\n项目投资回收期 DPP' },
  { type: '辅助报表', name: '项目建设投资估算表', indicator: '' },
  { type: '辅助报表', name: '流动资金估算表', indicator: '' },
  { type: '辅助报表', name: '资金使用计划与资金筹措表', indicator: '' },
  { type: '辅助报表', name: '总成本费用估算表', indicator: '' },
  { type: '辅助报表', name: '营业收入、税金及附加估算表', indicator: '' }
])

const repoertFormCategoryTableMergeCells = ({ rowIndex, columnIndex }) => {
  // 只对第一列（type）进行合并处理
  if (columnIndex === 0) {
    if (rowIndex === 0) {
      // '基本报表' 只有一项
      return { rowspan: 1, colspan: 1 }
    }
    if (rowIndex === 1) {
      // 从第 1 行开始，‘辅助报表’ 有 5 行
      return { rowspan: 5, colspan: 1 }
    }
    // 其他行为被合并行的子行，不显示内容
    return { rowspan: 0, colspan: 0 }
  }
}

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

.recontent {
  margin-left: 20px;
  margin-right: 20px;
  font-size: 18px;
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

.textarea-with-min-width {
  min-height: 300px;
}

.exp-step-card {
  padding-inline: 14px;
}

/* 添加表格相关样式 */
.step-card {
  padding: 24px;
}

:deep(.ant-table-cell) {
  padding: 8px !important;
}

:deep(.ant-input) {
  padding: 4px 8px;
}

/* 添加分数公式样式 */
.formula-container {
  display: flex;
  align-items: center;
  font-size: 16px;
  margin: 20px 0;
  gap: 8px;
}

.formula-container.indented {
  margin-left: 40px; /* 添加缩进 */
  margin-right: 40px; /* 右侧也添加一些间距 */
}

.fraction {
  display: inline-block;
  text-align: center;
  vertical-align: middle;
}

.numerator {
  border-bottom: 1px solid #000;
  padding: 0 4px;
}

.denominator {
  padding: 0 4px;
}

sup {
  font-size: 12px;
}

/* 添加表格样式 */
.bold-text {
  font-weight: bold;
}

.indent-text {
  padding-left: 24px;
}

:deep(.ant-table-cell) {
  padding: 8px !important;
}

:deep(.ant-table-row-level-0[data-row-key='divider1']) {
  background-color: #f5f5f5;
  height: 1px;
  padding: 0;
}

:deep(.ant-table-row-level-0[data-row-key='divider1'] td) {
  padding: 0 !important;
  border-top: 1px solid #e8e8e8;
  border-bottom: 1px solid #e8e8e8;
}

.step-card h3 {
  margin-bottom: 16px;
}

/* 表格样式优化 */
:deep(.ant-table-thead > tr > th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 600;
  text-align: center;
}

:deep(.ant-table-tbody > tr > td) {
  text-align: center;
}

/* 步骤卡片样式 */
.exp-step-card {
  background: #ffffff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.step-card {
  background: #ffffff;
  padding: 20px;
  border-radius: 6px;
  margin: 16px 0;
}

/* 标题样式优化 */
h2 {
  color: #303133;
  margin-bottom: 20px;
  font-weight: 600;
  position: relative;
  padding-left: 12px;
}

h2::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 18px;
  background-color: #409eff;
  border-radius: 2px;
}

/* 公式容器样式优化 */
.formula-container {
  background-color: #f8f9fa;
  padding: 16px;
  border-radius: 6px;
  margin: 16px 40px;
}

/* 输入框样式 */
:deep(.ant-input-number) {
  margin: 0 8px;
}

/* 表格间距 */
.ant-table {
  margin-bottom: 24px;
}

/* 文本内容样式 */
.recontent {
  color: #606266;
  line-height: 1.8;
  margin-bottom: 16px;
}

/* 步骤导航样式 */
:deep(.ant-steps) {
  margin-bottom: 24px;
}

/* 按钮样式优化 */
.steps-action {
  margin-top: 24px;
  padding: 16px 0;
  border-top: 1px solid #ebeef5;
}

/* 小结文本框样式 */
.textarea-with-min-width {
  min-height: 120px;
  margin-top: 16px;
}

/* 分隔线样式优化 */
:deep(.ant-table-row-level-0[data-row-key='divider1']) {
  background-color: #f5f7fa;
}

/* 响应式布局优化 */
@media screen and (max-width: 768px) {
  .formula-container.indented {
    margin-left: 20px;
    margin-right: 20px;
  }

  .exp-step-card {
    padding: 16px;
  }
}

.result-text {
  color: #409eff;
  font-weight: bold;
  font-size: 16px;
  margin: 0 8px;
}

.report-section {
  margin-bottom: 40px;
}

.report-item {
  text-align: left;
  text-indent: 2em;
}

.report-item h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.report-item p {
  font-size: 18px;
  margin-bottom: 20px;
}

.report-item span {
  font-size: 20px;
}

.report-item label {
  font-size: 20px;
}

.input-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 30px;
}

.input-group {

  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.input-group label {
  font-size: 16px;
  font-weight: bold;
  margin-right: 10px;
}

.confirm-button {
  margin-left: 20px;
}

.calculate-result {
  text-align: center;
  margin: 20px 25px;
}

.table-container {
  display: flex;
  justify-content: center;
  margin-bottom: 40px;
}

.chart1Ref-container {
  display: flex;
  justify-content: center;
}

.chart2Ref-container {
  display: flex;
  justify-content: center;
}

.result-section {
  display: flex;
  justify-content: center;
}

.result-card {
  background-color: #f0f2f5;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  width: 50%;
  text-align: center;
}

.result-card p {
  font-size: 18px;
  font-weight: bold;
}

.editable-row-operations {
  display: flex;
  justify-content: space-between;
  width: 150px;
}

.operation-btn {
  margin-right: 10px;
}

.a-table {
  width: 100%;
  background-color: #fff;
  border-collapse: collapse;
}

.a-table thead th {
  background-color: #f0f2f5;
  padding: 10px;
  text-align: left;
  font-weight: bold;
  font-size: 40px;
}

.a-table tbody tr:nth-child(even) {
  background-color: #f0f2f5;
}

.a-table tbody tr:hover {
  background-color: #e6f7ff;
}

.a-table tbody td {
  padding: 10px;
}

.result-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 18px;
  margin-top: 30px;
}

.result-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.result-label {
  font-weight: bold;
  margin-right: 5px;
}

.result-value {
  color: #1890ff;
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

.textarea-with-min-height {
  min-height: 300px;
}

</style>
