import { useDecisionTreeStore } from '../stores'
import axios from 'axios';
import { useAccountStore } from '@/store';
import { message } from 'ant-design-vue';

export default function(){

  const DecisionTreeStore = useDecisionTreeStore()
  const AccountStore = useAccountStore()
  function clear(){
    DecisionTreeStore.clear()
  }

  async function submit() {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found, please login first.');
      return;
    }
    //let response2 = await axios.post("http://localhost:8001/api/report_generate/pdf",
    let response2 = await axios.post("http://see-toju.com:8002/api/reports/generate/pdf",
      {
        experimentId: "84",
        courseId: useAccountStore().course.courseId,
        other:{
          reporterId: AccountStore.account.email.slice(0, AccountStore.account.email.indexOf('@')),
          reporterName: AccountStore.account.name,
          courseName: AccountStore.course.courseName,
          courseId: AccountStore.course.courseId,
          submitTime: new Date().toLocaleString(),

          answer_1: DecisionTreeStore.principleBlankAnswer,
          answer_2: DecisionTreeStore.baseQuestionBlankAnswer,
          answer_3: DecisionTreeStore.advancedQuestionBlankAnswer,

          table_1: DecisionTreeStore.Q2traditionTreeTableAnswer,
          table_2: DecisionTreeStore.Q3traditionTreeTableAnswer,
          table_3: DecisionTreeStore.compareTableAnswer,
          table_4: DecisionTreeStore.MLTreeTableAnswer,

          MLTreeSummary: DecisionTreeStore.MLTreeSummary,

          summary: DecisionTreeStore.summary,
        }
      },
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
    console.log(response2)
    if(response2.data.msg==="success"){
      message.success("提交成功")
    }
    else {
      message.error("提交失败")
    }
  }

  return {
    clear, submit
  }
}