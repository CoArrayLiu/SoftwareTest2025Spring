<template>
  <div style="padding:20px">
    <div style="text-align: center;">
      <h1 class="title">{{ $route.meta.title }} </h1>
    </div>
    <div class="btn-groups" style="height:50px">
      <a-tooltip v-if="experimentId === '14'" title="该实验暂不支持下载">
        <a-button type="primary" class="btn" @click="downloadExperimentBook" :disabled="experimentId === '14'">
          <DownloadOutlined />
          下载实验指导书
        </a-button>
      </a-tooltip>
      <a-button v-else type="primary" class="btn" @click="downloadExperimentBook">
        <DownloadOutlined />
        下载实验指导书
      </a-button>
      <a-tooltip v-if="experimentId === '14'" title="该实验暂不支持下载">
        <a-button
          type="primary"
          class="btn"
          @click="downLoadFile"
          :disabled="experimentId === '14'"
        >
          <DownloadOutlined />
          下载报告
        </a-button>
      </a-tooltip>
      <a-button
        v-else
        type="primary"
        class="btn"
        @click="downLoadFile"
      >
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
import { ref, onMounted } from 'vue';

const experimentDate = new Date()

const accountStore = useAccountStore()
const experimentStore = useExperimentStore()
const rt = useRoute()
const receivedData = ref(null);
const isSubmissionPermanentlyBlocked = ref(false);
const permanentBlockMessage = ref('');

const experimentId = (rt.meta.id.toString()) === "21" ? "12" :
  (rt.meta.id.toString()) === "42" ? "13" :
    (rt.meta.id.toString()) === "23" ? "14" :
      (rt.meta.id.toString()) === "24" ? "15" : 
        (rt.meta.id.toString()) === "25" ? "18" :
            (rt.meta.id.toString()) === "26" ? "19" :
              (rt.meta.id.toString()) === "27" ? "20" :
                (rt.meta.id.toString()) === "28" ? "31" :
                   (rt.meta.id.toString()) === "29" ? "32" :
                     (rt.meta.id.toString()) === "20" ? "33" : "";

async function checkInitialReportStatus() {
  const token = localStorage.getItem('token');
  if (!token) {
    return; // Not logged in
  }
  if (!accountStore.course.courseId) {
    console.warn('Course ID not available for initial report status check.');
    return;
  }
  // Ensure experimentId is valid
  if (!experimentId) {
    console.warn('Experiment ID not available for initial report status check.');
    return;
  }

  const reportDataForCheck = {
    course_id: accountStore.course.courseId,
    experiment_id: experimentId, // Use the dynamic experimentId
    ...(receivedData.value || {}),
  };

  const payload = {
    experimentId: experimentId, // Use the dynamic experimentId
    courseId: accountStore.course.courseId,
    other: reportDataForCheck
  };

  try {
    const response = await fetch('http://see-toju.com:8002/api/reports/generate/pdf', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(payload)
    });

    if (response.status === 400) {
      const errorData = await response.json();
      if (errorData && errorData.msg === "生成报告失败：该学生曾提交的报告已评分，无法再提交新的报告") {
        isSubmissionPermanentlyBlocked.value = true;
        permanentBlockMessage.value = errorData.msg;
        console.log('Initial check: Report already scored. Submission blocked.');
      }
    }
  } catch (error) {
    console.warn('Error during initial report status check:', error);
  }
}

async function downLoadFile() {
  if (isSubmissionPermanentlyBlocked.value) {
    message.info(('报告已评分。') + ' 尝试下载已存在的最新报告。');
    downloadReport();
    return;
  }

  if (!receivedData.value) {
    message.error('报告数据未准备好，请先提交报告数据。');
    return;
  }
  const reportData = {
    ...receivedData.value,
    course_id: accountStore.course.courseId,
  };
  const experimentIdForDownload = receivedData.value.experiment_id;

  // 将时间戳转换为可读格式
  if (reportData.submit_time) {
    reportData.submit_time = new Date(reportData.submit_time).toLocaleString();
  }

  fetch('http://see-toju.com:8002/api/reports/generate/pdf', {
  //fetch('http://localhost:8001/api/report_generate/pdf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
    },
    body: JSON.stringify({
      experimentId: experimentIdForDownload,
      courseId: accountStore.course.courseId,
      other: reportData
    })
  })
    .then(response => {
      if (!response.ok) {
        // Try to parse error response as JSON
        return response.json().then(errData => {
          if (response.status === 400 && errData && errData.msg === "生成报告失败：该学生曾提交的报告已评分，无法再提交新的报告") {
            message.warn('该学生曾提交的报告已评分，将尝试下载已存在的最新报告。');
            isSubmissionPermanentlyBlocked.value = true;
            permanentBlockMessage.value = errData.msg;
            downloadReport(); // Attempt to download existing report
            return Promise.reject(new Error(errData.msg)); // Stop further processing in this chain
          }
          throw new Error(`HTTP error! status: ${response.status}, message: ${errData.msg || response.statusText}`);
        }).catch(() => {
          // If parsing JSON fails, throw a generic error
          throw new Error(`HTTP error! status: ${response.status}`);
        });
      }
      console.log(reportData);
      return response.json();
    })
    .then(result => {
      // 如果成功，result.data 应该包含 PDF 文件的 URL
      downloadReport();
    })
    .catch(error => {
      // Avoid double messaging if already handled
      if (!isSubmissionPermanentlyBlocked.value || (error && error.message !== permanentBlockMessage.value)) {
        console.error('There was a problem with the fetch operation:', error);
        message.error('网络请求失败或处理错误，请稍后再试');
      }
    });
}

function downloadReport() {
  const courseId = accountStore.course.courseId;
  const token = localStorage.getItem('token');
  if (!token) {
    console.error('Token not found, please login first.');
    return;
  }
  const experimentIdForDownload = receivedData.value?.experiment_id || experimentId; // Fallback to current experimentId if not in receivedData
  if(!experimentIdForDownload){
    message.error('无法确定实验ID，下载失败。');
    return;
  }

  fetch(`http://see-toju.com:8002/api/reports/students/latest?experimentId=${experimentIdForDownload}&courseId=${courseId}`, {
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


async function downloadExperimentBook(url, courseId) {
  // experimentId = 13;
  console.log(experimentId);
  courseId = accountStore.course.courseId;
  const token = localStorage.getItem('token');
  if (!token) {
    console.error('Token not found, please login first.');
    return;
  }

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
  if (isSubmissionPermanentlyBlocked.value) {
    message.error(permanentBlockMessage.value || '报告已评分，无法再次提交。');
    return '提交失败：报告已评分';
  }

  const finalData = {
    experiment_id: experimentId,
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
    ...data
  }
  console.log("提交数据", JSON.stringify(finalData));

  // 存储到 localStorage，使用基于 experimentId 的唯一键
  const localStorageKey = `exp2_experimentData_${experimentId}`; // experimentId is a constant string here
  localStorage.setItem(localStorageKey, JSON.stringify(finalData));
  receivedData.value = finalData;

  try {
    //http请求
    const res = await experimentStore.submitData(finalData)
    message.success('提交成功')
    return '提交成功'
  } catch (error) {
    console.error('提交出错:', error)
    // Check for specific error message indicating report already scored
    const errorMsg = error?.response?.data?.msg || error?.message || '提交时发生未知错误';
    if (errorMsg === "生成报告失败：该学生曾提交的报告已评分，无法再提交新的报告") {
      isSubmissionPermanentlyBlocked.value = true;
      permanentBlockMessage.value = errorMsg;
      message.error(errorMsg);
      return '提交失败：报告已评分';
    }

    // 检查localStorage中是否已存储数据，如果已存储数据，我们认为提交可能已成功
    // This localStorage check might lead to incorrect success messages if API call truly failed for other reasons
    // However, keeping it as per original logic for now.
    if (localStorage.getItem('experimentData')) {
      message.success('提交成功')
      return '提交成功'
    } else {
      message.error('提交失败')
      return '提交失败'
    }
  }
}

onMounted(async () => {
  if (!accountStore.course.courseName || !accountStore.course.courseId) {
    await accountStore.updateCourseId(); // 假设有一个方法可以获取课程数据
  }
  // Similar to Exp1, reset relevant data if needed, or ensure checkInitialReportStatus runs correctly
  // Exp2 loads from 'experimentData', Exp1 from `experimentData_exp${experimentId}`
  // For now, we'll keep Exp2's localStorage key as is, but ensure checkInitialReportStatus runs.
  
  // 加载 localStorage 数据时使用基于 experimentId 的唯一键
  const localStorageKey = `exp2_experimentData_${experimentId}`;
  const savedData = localStorage.getItem(localStorageKey);

  if (savedData) {
    // check if the saved data is for the current experiment
    const parsedData = JSON.parse(savedData);
    // 在Exp2.vue中，experimentId本身就是动态计算的，或者从rt.meta.id转换而来
    // 此处的 experimentId.value 是父组件的，而 parsedData.experiment_id 是子组件 (NESMA等) 存入的
    // 因此，我们直接信任localStorageKey的特异性，如果能取到，就认为是对应这个父实验实例的数据
    // 如果子组件也往这个key存，那么需要确保子组件的 experiment_id 和父组件的 experimentId (转换后的) 含义一致
    // 目前的逻辑是，Exp2.vue存取它自己的整体数据，NESMA存取它自己的nesma_experimentData
    // 因此，这里的逻辑应该是安全的，我们加载的是Exp2自己的数据
    receivedData.value = parsedData; // 直接加载，因为键名已经是特定的
    console.log(`Exp2.vue: Loaded data from ${localStorageKey}`);
    
  } else {
    console.log(`Exp2.vue: No data found in localStorage for key ${localStorageKey}`);
    receivedData.value = null;
  }
  await checkInitialReportStatus();
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