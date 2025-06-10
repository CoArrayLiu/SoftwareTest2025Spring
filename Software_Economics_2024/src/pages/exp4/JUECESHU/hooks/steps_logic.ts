import { ref } from 'vue'
// 实验逻辑处理
// 实验步骤
export default function(){
  const current = ref(0) // 当前步骤
  const steps = ref([
    {
      title: '实验目的',
      content: '0-content',
    },
    {
      title: '实验原理',
      content: 'First-content',
    },
    {
      title: '场景分析（基础）',
      content: 'Second-content',
    },
    {
      title: '场景分析（进阶）',
      content: 'Third-content'
    },
    {
      title: '机器学习决策树',
      content: 'Fourth-content',
    },
    {
      title: '实验心得',
      content: 'Fifth-content',
    }
  ]);

// 上一页和下一页操作
  function nextStep(){
    current.value++;
  }

  function prevStep(){
    current.value--;
  }

  return {
    current,steps,
    nextStep,prevStep
  }
}
