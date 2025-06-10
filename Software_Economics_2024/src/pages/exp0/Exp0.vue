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
                    <a-col span="6">课程名称：软件工程经济学</a-col>
                    <a-col span="6">课号：{{ accountStore.course.courseId }}</a-col>
                    <a-col span="6">实验项目名称：{{$route.meta.title}}</a-col>
                </a-row>
                <a-row justify="center">
                    <a-col span="6">实验时间：<span style="border-bottom: 1px solid grey;border-radius: none;">{{ experimentDate }}</span></a-col>
                    <a-col span="6">实验报告人： <span style="border-bottom: 1px solid grey;border-radius: none;">{{ accountStore.account.name }} </span></a-col>
                    <a-col span="6">学号：<span style="border-bottom: 1px solid grey;">{{ accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')) }}</span></a-col>
                </a-row>
            </p>
        </a-config-provider>
    </div>

    <RouterView  @submit="(data)=>submit(data)"  />


  </div>
</template>

<script lang="ts" setup>
import { message } from 'ant-design-vue';
import { useRoute } from 'vue-router';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import { onMounted, ref } from 'vue';

const experimentDate = new Date().toISOString().slice(0, 10)
const accountStore = useAccountStore()
const experimentStore = useExperimentStore()
const rt = useRoute()
const receivedData = ref(null);

console.log(rt.meta)

async function downLoadFile() {
  if (!receivedData.value) {
    message.info('报告数据未准备好，请先提交报告数据。');
    return;
  }
  
  // 使用从子组件接收的数据来填充 reportData
  const reportData = {
    course_id: accountStore.course.courseId,
    experiment_id: rt.meta.id.toString(),
    // 使用 receivedData.value 中的数据
    ...receivedData.value,
  };

  // 将时间戳转换为可读格式
  if (reportData.submit_time) {
    reportData.submit_time = new Date(reportData.submit_time).toLocaleString();
  }

  fetch('http://see-toju.com:8002/api/report_generate/pdf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
    },
    body: JSON.stringify({
      experimentId: rt.meta.id.toString(),
      courseId: accountStore.course.courseId,
      other: reportData
    })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok ' + response.statusText);
    }
    const contentDisposition = response.headers.get('content-disposition');
    let filename = 'Report';
    filename = `${filename}_${accountStore.account.email.slice(0, accountStore.account.email.indexOf('@'))}_${accountStore.account.name}_${rt.meta.title}`;
    if (contentDisposition) {
      const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
      if (filenameMatch && filenameMatch[1]) {
        filename = filenameMatch[1].replace(/['"]/g, '');
      }
    }
    return response.blob().then(blob => ({ blob, filename }));
  })
  .then(({ blob, filename }) => {
    const downloadUrl = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = downloadUrl;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(downloadUrl);
  })
  .catch(error => {
    console.error('There was a problem with the fetch operation:', error);
    message.info('报告正在生成或未提交');
  });
}

const submit = (data)=>{
  receivedData.value = data;
  const finalData = {
    exp_date: experimentDate,
    reporter_name: accountStore.account.name,
    ...data
  }
  console.log(finalData)
  // 保存到localStorage以备后用
  localStorage.setItem('experimentData', JSON.stringify(finalData));
}

onMounted(() => {
  // 从localStorage加载之前保存的数据(如果有)
  const savedData = localStorage.getItem('experimentData');
  if (savedData) {
    receivedData.value = JSON.parse(savedData);
  }
});
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
