import { defineStore } from 'pinia';
import { ref } from 'vue';


export const useStore = defineStore('exp3-3_JIXIAOPINGGU', () => {
  const summary = ref('');
  const spinning = ref(false);
  //项目规划期
  const projectPlanningPeriod = ref<number>(5);
  const handleProjectPlaningPeriodChange = (newPeriod : number) =>{
    softwareDevelopPersons.value = Array(newPeriod + 1).fill(0);
    softwareDevelopAndMaintenance.value = Array(newPeriod + 1).fill(0);
    equipmentAndToolPurchase.value = Array(newPeriod + 1).fill(0);
    engineeringOtherCost.value = Array(newPeriod + 1).fill(0);
    basicReserveFund.value = Array(newPeriod + 1).fill(0);
    projectConstructInvest.value = Array(newPeriod + 1).fill(0);
    thatYearConstructInvest.value = Array(newPeriod + 1).fill(0);
    accumulatedConstructInvest.value = Array(newPeriod + 1).fill(0);
    cash.value = Array(newPeriod + 1).fill(0);
    accountsPayable.value = Array(newPeriod + 1).fill(0);
    accountsReceivable.value = Array(newPeriod + 1).fill(0);
    flowDebt.value = Array(newPeriod + 1).fill(0);
    workingCapital.value = Array(newPeriod + 1).fill(0);
    workingCapitalAnnualIncrease.value = Array(newPeriod + 1).fill(0);
    investTotal.value = Array(newPeriod + 1).fill(0);
    investCollectSource.value = Array(newPeriod + 1).fill(0);
    capital.value = Array(newPeriod + 1).fill(0);
    capitalBalance.value = Array(newPeriod + 1).fill(0);
    debt.value = Array(newPeriod + 1).fill(0);
    debtBalance.value = Array(newPeriod + 1).fill(0);
    accumulatedInvestTotal.value = Array(newPeriod + 1).fill(0);
    annualEmployees.value = Array(newPeriod + 1).fill(0);
    annualEmployeesBaseSalary.value = Array(newPeriod + 1).fill(0);
    annualEmployeesWelfare.value = Array(newPeriod + 1).fill(0);
    managementCost.value = Array(newPeriod + 1).fill(0);
    monthlyManagementCost.value = Array(newPeriod + 1).fill(0);
    annualMarketingIncome.value = Array(newPeriod + 1).fill(0);
    annualMarketingCost.value = Array(newPeriod + 1).fill(0);
    debtInterestCost.value = Array(newPeriod + 1).fill(0);
    salaryAndWelfare.value = Array(newPeriod + 1).fill(0);
    operatingCost.value = Array(newPeriod + 1).fill(0);
    totalCost.value = Array(newPeriod + 1).fill(0);
    depreciationCost.value = Array(newPeriod + 1).fill(0);
    cashFlowIn.value = Array(newPeriod + 1).fill(0);
    grossAnnualMarketingIncome.value = Array(newPeriod + 1).fill(0);
    recollectFixedAssetsResidual.value = Array(newPeriod + 1).fill(0);
    recollectWorkingCapital.value = Array(newPeriod + 1).fill(0);
    cashFlowOut.value = Array(newPeriod + 1).fill(0);
    grossProjectConstructInvest.value = Array(newPeriod + 1).fill(0);
    grossWorkingCapital.value = Array(newPeriod + 1).fill(0);
    grossOperatingCost.value = Array(newPeriod + 1).fill(0);
    grossTaxAndAttachment.value = Array(newPeriod + 1).fill(0);
    netCashFlowBeforeIncomeTax.value = Array(newPeriod + 1).fill(0);
    accumulatedNetCashFlowBeforeIncomeTax.value = Array(newPeriod + 1).fill(0);
    irrBeforeIncomeTax.value = 0;
    npvBeforeIncomeTax.value = 0;
    dppBeforeIncomeTax.value = 0;
  }
  //平台研发与部署期
  const projectRDPeriod = ref<number>(0);



  //项目投资估算表相关数据
  const projectConstructInvest = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0));
  const softwareDevelopAndMaintenance = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0));  // = softwareDevelopPersons*comprehensieLaborCost;
  const softwareDevelopPersons = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0));
  const comprehensieLaborCost = ref<number>(0);
  const equipmentAndToolPurchase = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0));
  const engineeringOtherCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0));
  const proejctStatringFee = ref<number>(0);
  const constructUnitManageFee = ref<number>(0);
  const basicReserveFund = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const workingVenueDecorateFee =  ref<number>(0);
  const computerFee =  ref<number>(0);
  const workingFurnitureFee =  ref<number>(0);
  const thatYearConstructInvest = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const accumulatedConstructInvest = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  

  const renewProjectInvestTable = () =>{
    basicReserveFund.value[0] = Math.floor(((softwareDevelopAndMaintenance.value[0]+engineeringOtherCost.value[0]+equipmentAndToolPurchase.value[0])/10));
    engineeringOtherCost.value[0] = proejctStatringFee.value + constructUnitManageFee.value;
    equipmentAndToolPurchase.value[0] = workingVenueDecorateFee.value + workingFurnitureFee.value + computerFee.value;
    for(let i=1;i<=projectPlanningPeriod.value+1;i++){
      softwareDevelopAndMaintenance.value[i-1]=comprehensieLaborCost.value*softwareDevelopPersons.value[i-1];
      projectConstructInvest.value[i-1] = softwareDevelopAndMaintenance.value[i-1] + engineeringOtherCost.value[i-1] + equipmentAndToolPurchase.value[i-1] + basicReserveFund.value[i-1];
      thatYearConstructInvest.value[i-1] = projectConstructInvest.value[i-1];
      accumulatedConstructInvest.value[i-1] = (i-1==0) ? projectConstructInvest.value[0] : accumulatedConstructInvest.value[i-2] + projectConstructInvest.value[i-1];
    }
  }


  //流动资金估算表相关数据
  const initialWorkingCapital = ref<number>(0);
  const initialWorkingCapitalAnnualIncrease = ref<number>(0);


  const cash = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const accountsReceivable = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const flowDebt = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const accountsPayable = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  const workingCapital = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const workingCapitalAnnualIncrease = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  const renewWorkingCapitalTable = () =>{
    workingCapital.value[0] = initialWorkingCapital.value;
    workingCapitalAnnualIncrease.value[0] = initialWorkingCapitalAnnualIncrease.value;


    for(let i=1;i<=projectPlanningPeriod.value+1;i++){
      workingCapitalAnnualIncrease.value[i-1] = (i-1==0) ? initialWorkingCapital.value : (i-1==1) ? 0 : initialWorkingCapitalAnnualIncrease.value;
      workingCapital.value[i-1] = (i-1==0) ? initialWorkingCapital.value : workingCapital.value[i-2] + workingCapitalAnnualIncrease.value[i-1];
    }
  }


  //资金使用计划与资金筹措表
  const investTotal = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const investCollectSource = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const capital = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const capitalBalance = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const debt = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const debtBalance = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const accumulatedInvestTotal = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const debtPeriod = ref<number>(5);

  function renewInvestCollectTable(){
    debtBalance.value = Array(projectPlanningPeriod.value+1).fill(0);
    capitalBalance.value = Array(projectPlanningPeriod.value+1).fill(0);

    for(let i=1;i<=projectPlanningPeriod.value+1;i++){
      investTotal.value[i-1] = projectConstructInvest.value[i-1] + workingCapitalAnnualIncrease.value[i-1];
      investCollectSource.value[i-1] = capital.value[i-1] + debt.value[i-1];
      capitalBalance.value[i-1] = (i-1==0) ? capital.value[0] : capitalBalance.value[i-2] + capital.value[i-1];
      accumulatedInvestTotal.value[i-1] = (i-1==0) ? investTotal.value[0] : accumulatedInvestTotal.value[i-2] + investTotal.value[i-1];
    }

    debt.value.forEach((item,i) => {
      for(let j=i;j<i+debtPeriod.value&&j<projectPlanningPeriod.value;j++){
        debtBalance.value[j]+=item;
      }
    });
  }

  function resetInvestCollectTable(){
    investTotal.value = Array(projectPlanningPeriod.value+1).fill(0);
    investCollectSource.value = Array(projectPlanningPeriod.value+1).fill(0);
    capitalBalance.value = Array(projectPlanningPeriod.value+1).fill(0);
    accumulatedInvestTotal.value = Array(projectPlanningPeriod.value+1).fill(0);
    debt.value = Array(projectPlanningPeriod.value+1).fill(0);
    debtBalance.value = Array(projectPlanningPeriod.value+1).fill(0);
    capital.value = Array(projectPlanningPeriod.value+1).fill(0);
  }

  //总成本费用估算表相关数据

    //附表一 工资福利费
    const employeeBaeSalary = ref<number>(0);
    const employeeWelfareRate = ref<number>(0);
    const marketingCostRate = ref<number>(25);
    const annualEmployees = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    const annualEmployeesBaseSalary = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    const annualEmployeesWelfare = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

    const salaryAndWelfare = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    //附表二 营销费用
    const annualMarketingIncome = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    const annualMarketingCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    
    //附表三 管理费用
    const monthlyManagementCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
    const managementCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  //经营成本 = 营销+工资+管理 费用
  const operatingCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  //折旧费用
  const residualValueRate = ref<number>(50);
  const depreciationCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  //贷款利息
  const debtInterestRate = ref<number>(5.5);
  const debtInterestCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  const totalCost = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  function renewTotalCostTable(){
    for(let i=1;i<=projectPlanningPeriod.value+1;i++){
      annualEmployeesBaseSalary.value[i-1] = annualEmployees.value[i-1]*employeeBaeSalary.value;
      annualEmployeesWelfare.value[i-1] = annualEmployeesBaseSalary.value[i-1]*employeeWelfareRate.value/100;
      salaryAndWelfare.value[i-1] = annualEmployeesBaseSalary.value[i-1] + annualEmployeesWelfare.value[i-1];

      annualMarketingCost.value[i-1] = annualMarketingIncome.value[i-1]*marketingCostRate.value/100;

      managementCost.value[i-1] = monthlyManagementCost.value[i-1]*12;

      operatingCost.value[i-1] = salaryAndWelfare.value[i-1] + annualMarketingCost.value[i-1] + managementCost.value[i-1];

      depreciationCost.value[i-1] = (i-1==0) ? 0 : (equipmentAndToolPurchase.value[0]*(100-residualValueRate.value)/100)/projectPlanningPeriod.value;

      debtInterestCost.value[i-1] = (i-1==0) ? 0 : debtBalance.value[i-1]*debtInterestRate.value/100;

      totalCost.value[i-1] = operatingCost.value[i-1] + depreciationCost.value[i-1] + debtInterestCost.value[i-1];
    }
  }

  function resetTotalCostTable(){
    annualEmployees.value = Array(projectPlanningPeriod.value+1).fill(0);
    annualEmployeesBaseSalary.value = Array(projectPlanningPeriod.value+1).fill(0);
    annualEmployeesWelfare.value = Array(projectPlanningPeriod.value+1).fill(0);
    managementCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    monthlyManagementCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    annualMarketingIncome.value = Array(projectPlanningPeriod.value+1).fill(0);
    annualMarketingCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    debtInterestCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    salaryAndWelfare.value = Array(projectPlanningPeriod.value+1).fill(0);
    operatingCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    totalCost.value = Array(projectPlanningPeriod.value+1).fill(0);
    depreciationCost.value = Array(projectPlanningPeriod.value+1).fill(0);
  } 

  //营业收入、税金及附加估算表
  const VATRate = ref<number>(6);
  const cityTaxRate = ref<number>(7);
  const eduTaxRate = ref<number>(3);

  const taxAndAttachment = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const VAT = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const VATSalesBulk = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const VATIncomeBulk = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const cityTax = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const eduTax = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 
  const taxAndAttachmentBesidesVAT = ref<Array<number>>(Array(projectPlanningPeriod.value+1).fill(0)); 

  function renewIncomeAndTaxTable(){
    resetIncomeAndTaxTable();
    for(let i=1;i<=projectPlanningPeriod.value+1;i++){
      VATSalesBulk.value[i-1] = annualMarketingIncome.value[i-1]*VATRate.value/100;
      VAT.value[i-1] = VATSalesBulk.value[i-1] - VATIncomeBulk.value[i-1];
      cityTax.value[i-1] = VAT.value[i-1]*cityTaxRate.value/100;
      eduTax.value[i-1] = VAT.value[i-1]*eduTaxRate.value/100;
      taxAndAttachment.value[i-1] = VAT.value[i-1] + cityTax.value[i-1] + eduTax.value[i-1];
      taxAndAttachmentBesidesVAT.value[i-1] = taxAndAttachment.value[i-1] - VAT.value[i-1];
    }
  }

  function resetIncomeAndTaxTable(){
    VATSalesBulk.value = Array(projectPlanningPeriod.value+1).fill(0);
    VATIncomeBulk.value = Array(projectPlanningPeriod.value+1).fill(0);
    VAT.value = Array(projectPlanningPeriod.value+1).fill(0);
    cityTax.value = Array(projectPlanningPeriod.value+1).fill(0);
    eduTax.value = Array(projectPlanningPeriod.value+1).fill(0);
    taxAndAttachment.value = Array(projectPlanningPeriod.value+1).fill(0);
    taxAndAttachmentBesidesVAT.value = Array(projectPlanningPeriod.value+1).fill(0);
  }

  // 项目投资现金流量表
  const cashFlowIn = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const grossAnnualMarketingIncome = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const recollectFixedAssetsResidual = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const recollectWorkingCapital = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const cashFlowOut = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const grossProjectConstructInvest = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const grossWorkingCapital = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const grossOperatingCost = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const grossTaxAndAttachment = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  //netCashFlowBeforeIncomeTax = cashFlowIn - cashFlowOut
  const netCashFlowBeforeIncomeTax = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 
  const accumulatedNetCashFlowBeforeIncomeTax = ref<Array<number>>(Array(projectPlanningPeriod.value + 1).fill(0)); 

  const irrBeforeIncomeTax = ref<number>(0);
  const npvBeforeIncomeTax = ref<number>(0);
  const dppBeforeIncomeTax = ref<number>(0);
  //折现率%
  const discountRate = ref<number>(8);
  //计算DPP
  function DPP(discountRate: number, cashFlows: number[]): number {
    let cumulativePV = 0;
    let initialInvestment = Math.abs(cashFlows[0]); // 假设初始投资为第一个元素，且为负数
    for (let i = 1; i < cashFlows.length; i++) {
        // 使用公式 PV = CF / (1 + r)^t 来计算每年现金流的现值
        const pv = cashFlows[i] / Math.pow(1 + discountRate, i);
        cumulativePV += pv;

        if (cumulativePV >= initialInvestment) {
            // 如果累计现值达到了初始投资，则计算具体的时间点
            const previousCumulativePV = cumulativePV - pv;
            return i - 1 + (initialInvestment - previousCumulativePV) / pv;
        }
    }
    return NaN; // 如果无法在给定现金流内回收投资
  }
  //计算NPV
  function NPV(rate: number, cashFlows: number[]): number {
    return cashFlows.reduce((npv, cash, period) => {
      return npv + cash / Math.pow(1 + rate, period);
    }, 0);
  }
  //计算IRR
  function IRR(values) {
    let guess = 0.1;
  
    // Credits: algorithm inspired by Apache OpenOffice
    let epsMax = 1e-10;
    let iterMax = 50;
    let x0 = guess;
    let x1;
    let f0, f1;
    let i = 0;
  
    function irrResult(values, rate) {
      var r = rate + 1;
      return values.reduce(function (acc, val, i) {
        return acc + val / Math.pow(r, i);
      }, 0);
    }
  
    f0 = irrResult(values, x0);
    f1 = 0;
    while (Math.abs(f0) > epsMax && i < iterMax) {
      x1 = x0 - f0 / ((irrResult(values, x0 + epsMax) - f0) / epsMax);
      f0 = irrResult(values, x1);
      x0 = x1;
      ++i;
    }
  
    return (Math.abs(f0) > epsMax) ? NaN : x0;
  }

  function renewProjectInvestCashFlowTable(){
    resetProjectInvestCashFlowTable();
    recollectFixedAssetsResidual.value[projectPlanningPeriod.value] = equipmentAndToolPurchase.value[0]*residualValueRate.value/100;
    recollectWorkingCapital.value[projectPlanningPeriod.value] = workingCapital.value[projectPlanningPeriod.value];
    for(let i=0; i<=projectPlanningPeriod.value; i++){
      //现金流入
      grossAnnualMarketingIncome.value[i] = (i==0) ? 0 : annualMarketingIncome.value[i];
      cashFlowIn.value[i] = grossAnnualMarketingIncome.value[i] + recollectFixedAssetsResidual.value[i] + recollectWorkingCapital.value[i];
      //现金流出
      grossProjectConstructInvest.value[i] = projectConstructInvest.value[i];
      grossWorkingCapital.value[i] = workingCapitalAnnualIncrease.value[i];
      grossOperatingCost.value[i] = operatingCost.value[i];
      grossTaxAndAttachment.value[i] = taxAndAttachment.value[i];
      cashFlowOut.value[i] = grossProjectConstructInvest.value[i] + grossWorkingCapital.value[i] + grossOperatingCost.value[i] + grossTaxAndAttachment.value[i];
      //所得税前净现金流量及所得税前净现金流量累计值
      netCashFlowBeforeIncomeTax.value[i] = cashFlowIn.value[i] - cashFlowOut.value[i];
      accumulatedNetCashFlowBeforeIncomeTax.value[i] = (i==0) ? netCashFlowBeforeIncomeTax.value[0] : netCashFlowBeforeIncomeTax.value[i] + accumulatedNetCashFlowBeforeIncomeTax.value[i-1]; 
    }
    irrBeforeIncomeTax.value = IRR(netCashFlowBeforeIncomeTax.value);
    npvBeforeIncomeTax.value = NPV(discountRate.value/100,netCashFlowBeforeIncomeTax.value);
    dppBeforeIncomeTax.value = DPP(discountRate.value/100,netCashFlowBeforeIncomeTax.value);
  }

  function resetProjectInvestCashFlowTable(){
    cashFlowIn.value = Array(projectPlanningPeriod.value+1).fill(0),
    grossAnnualMarketingIncome.value = Array(projectPlanningPeriod.value+1).fill(0),
    recollectFixedAssetsResidual.value = Array(projectPlanningPeriod.value+1).fill(0),
    recollectWorkingCapital.value = Array(projectPlanningPeriod.value+1).fill(0),
    cashFlowOut.value = Array(projectPlanningPeriod.value+1).fill(0),
    grossProjectConstructInvest.value = Array(projectPlanningPeriod.value+1).fill(0),
    grossWorkingCapital.value = Array(projectPlanningPeriod.value+1).fill(0),
    grossOperatingCost.value = Array(projectPlanningPeriod.value+1).fill(0),
    grossTaxAndAttachment.value = Array(projectPlanningPeriod.value+1).fill(0),
    netCashFlowBeforeIncomeTax.value = Array(projectPlanningPeriod.value+1).fill(0),
    accumulatedNetCashFlowBeforeIncomeTax.value = Array(projectPlanningPeriod.value+1).fill(0),
    irrBeforeIncomeTax.value = 0,
    npvBeforeIncomeTax.value = 0,
    dppBeforeIncomeTax.value = 0
  }

  function resetAllData(){
    handleProjectPlaningPeriodChange(projectPlanningPeriod.value);
  }


  return { spinning, projectPlanningPeriod, projectRDPeriod, handleProjectPlaningPeriodChange, discountRate, resetAllData,
    //项目投资估算表
    softwareDevelopAndMaintenance,softwareDevelopPersons,comprehensieLaborCost,proejctStatringFee,constructUnitManageFee,
    equipmentAndToolPurchase,engineeringOtherCost,basicReserveFund,projectConstructInvest,workingFurnitureFee,workingVenueDecorateFee,computerFee,
    renewProjectInvestTable,thatYearConstructInvest,accumulatedConstructInvest,
    //流动资金估算表相关数据
    initialWorkingCapitalAnnualIncrease,initialWorkingCapital,cash,accountsPayable,accountsReceivable,flowDebt,workingCapital,workingCapitalAnnualIncrease,
    renewWorkingCapitalTable,
    //资金使用计划与资金筹措表
    investTotal,investCollectSource,capital,capitalBalance,debt,debtBalance,accumulatedInvestTotal,
    renewInvestCollectTable,resetInvestCollectTable,
    //总成本费用估算表
    annualEmployees,annualEmployeesBaseSalary,annualEmployeesWelfare,managementCost,monthlyManagementCost,
    annualMarketingIncome,annualMarketingCost,debtInterestCost,salaryAndWelfare,operatingCost,
    totalCost,depreciationCost,employeeBaeSalary,employeeWelfareRate,
    renewTotalCostTable,resetTotalCostTable,
    //营业收入、税金及附加估算表
    VAT,cityTax,eduTax,taxAndAttachment,taxAndAttachmentBesidesVAT,VATIncomeBulk,VATSalesBulk,
    renewIncomeAndTaxTable,
    //项目投资现金流量表
    cashFlowIn,grossAnnualMarketingIncome,recollectFixedAssetsResidual,recollectWorkingCapital,
    cashFlowOut,grossProjectConstructInvest,grossWorkingCapital,grossOperatingCost,grossTaxAndAttachment,
    netCashFlowBeforeIncomeTax,accumulatedNetCashFlowBeforeIncomeTax,
    irrBeforeIncomeTax,npvBeforeIncomeTax,dppBeforeIncomeTax,
    renewProjectInvestCashFlowTable,resetProjectInvestCashFlowTable,

    summary,
  };
}, {
  persist: true
});
