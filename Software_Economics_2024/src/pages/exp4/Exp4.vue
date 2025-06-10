<template>
  <div style="padding:20px">
    <div style="text-align: center;">
      <h1 class="title">{{ $route.meta.title }} </h1>
    </div>
    <div class="btn-groups" style="height:50px">
      <a-button type="primary" class="btn" @click="downloadExperimentBook">
        <DownloadOutlined />
        下载实验指导书
      </a-button>
      <a-button type="primary" class="btn" @click="downLoadFile">
        <DownloadOutlined />
        下载报告
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
            <a-col span="6">实验时间：<span style="border-bottom: 1px solid grey;border-radius: none;">{{ experimentDate.toISOString().slice(0, 10) }}</span></a-col>
            <a-col span="6">实验报告人： <span style="border-bottom: 1px solid grey;border-radius: none;">{{accountStore.account.name }} </span></a-col>
            <a-col span="6">学号：<span style="border-bottom: 1px solid grey;">{{ accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')) }}</span></a-col>
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
import { onMounted } from 'vue';
const experimentDate = new Date()

const accountStore = useAccountStore()
const experimentStore = useExperimentStore()
const rt = useRoute()

const experimentId = (rt.meta.id.toString()) === "41" ? "84" : "";



console.log(rt.meta)






async function downLoadFile() {
  const courseId = accountStore.course.courseId;
  const token = localStorage.getItem('token');
  if (!token) {
    console.error('Token not found, please login first.');
    return;
  }
  console.log(experimentId,courseId,token);


  fetch(`http://see-toju.com:8002/api/reports/students/latest?experimentId=${experimentId}&courseId=${courseId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': `Bearer ${token}`,
    }
  })
    .then(response => {
      console.log(response);
      if (!response.ok) {
        message.error("网络有误或未查询到报告")
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
    });
}




async function downloadExperimentBook() {
  let courseId = accountStore.course.courseId;
  const token = localStorage.getItem('token');
  if (!token) {
    console.error('Token not found, please login first.');
    return;
  }

  console.log("aaaaa",experimentId);

  fetch(`http://see-toju.com:8002/api/courses/${courseId}/experiments/${experimentId}/guidebook`, {
    method: 'GET',
    headers: {
      'Accept': 'application/octet-stream',
      'Authorization': `Bearer ${token}`,
    },
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      const contentDisposition = response.headers.get('content-disposition');
      let filename = 'experiment_guide_';
      filename = `${filename}${rt.meta.title}`;
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
    });
}


const submit = async (data) => {
  const finalData = {

    experiment_id: experimentId,
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    submit_time: experimentDate.getTime(),
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    
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

onMounted(async () => {
  if (!accountStore.course.courseName || !accountStore.course.courseId) {
    await accountStore.updateCourseId(); // 假设有一个方法可以获取课程数据
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
