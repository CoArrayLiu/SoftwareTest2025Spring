<template>
  <div style="padding:20px">
    <div style="text-align: center;">
      <h1 class="title">{{ $route.meta.title }} </h1>
    </div>
    <div class="btn-groups" style="height:50px">
      <a-button type="primary" class="btn" @click="downLoadFile">
        <DownloadOutlined />
        查看报告
      </a-button>
    </div>
    <div style="padding-top:30px;padding-bottom:20px" ref="content_1">
      <a-config-provider>
        <p style="line-height:200%;font-size: 16px;">
          <a-row justify="center">
            <a-col span="6">课程名称：{{ accountStore.course.courseName }}</a-col>
            <a-col span="6">课号：{{ accountStore.course.courseId }}</a-col>
            <a-col span="6">实验项目名称：{{ $route.meta.title }}</a-col>
          </a-row>
          <a-row justify="center">
            <a-col span="6">实验时间：<span style="border-bottom: 1px solid grey;border-radius: none;">
                {{ experimentDate.toISOString().slice(0, 10) }}</span></a-col>
            <a-col span="6">实验报告人： <span style="border-bottom: 1px solid grey;border-radius: none;">{{
              accountStore.account.name
            }} </span>
            </a-col>
            <a-col span="6">学号：
              {{accountStore.account.email.slice(0, accountStore.account.email.indexOf('@'))}}</a-col>
          </a-row>
        </p>
      </a-config-provider>
    </div>

    <RouterView @submit="(data) => submit(data)" />


  </div>
</template>

<script lang="ts" setup>
import { message } from 'ant-design-vue';
import { useRoute } from 'vue-router';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
const experimentDate = new Date()

const accountStore = useAccountStore()
const experimentStore = useExperimentStore()
const rt = useRoute()
console.log(rt.meta)
async function downLoadFile() {
  // const res = await experimentStore.getReport(rt.meta.id.toString(),'2052538','陈诺')
  const res = await experimentStore.getReport(rt.meta.id.toString(), accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')), accountStore.account.name)
  console.log(res)
  if (!res) {
    message.info('报告正在生成或未提交')
  }
  else if (res.startsWith('http')) {
    window.open(res, '_blank');
  }
  else {
    // 这一步可以获取后端的message
    message.info(res)
  }
}
const submit = async (data) => {
  const finalData = {

    experiment_id: rt.meta.id.toString(),
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    submit_time: experimentDate.getTime(),
    course_name:accountStore.course.courseName ,
    course_id:accountStore.course.courseId ,

    ...data
  }
  console.log("提交数据", JSON.stringify(finalData))
  // http请求
  const res = await experimentStore.submitData(finalData)
  if (res == '上传成功') {
    message.success('提交成功')
  } else {
    message.error('提交失败')
  }

}
</script>

<style scoped lang="less">
.title {
  font-family: sans-serif;
  font-size: 30px;
}

.btn-groups {
  display: flex;
  justify-content: flex-end;
}

.btn {
  margin-right: 10px;
}
</style>
