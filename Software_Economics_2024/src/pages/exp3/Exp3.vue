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
import { onMounted, ref } from 'vue';
import { useExperimentStore } from '@/store/experiment';
const experimentDate = new Date()
const receivedData = ref(null);
const accountStore = useAccountStore()
const experimentStore = useExperimentStore()
const rt = useRoute()

// experimentId is dynamically determined
const experimentId = (rt.meta.id.toString()) === "31" ? "71" :
  (rt.meta.id.toString()) === "32" ? "72" :
    (rt.meta.id.toString()) === "33" ? "73" : "";

const isSubmissionPermanentlyBlocked = ref(false);
const permanentBlockMessage = ref('');

async function checkInitialReportStatus() {
  const token = localStorage.getItem('token');
  if (!token) {
    return; // Not logged in
  }
  if (!accountStore.course.courseId) {
    console.warn('Course ID not available for initial report status check.');
    return;
  }
  if (!experimentId) {
    console.warn('Experiment ID not determined for initial report status check.');
    return;
  }

  const savedDataForCheck = localStorage.getItem(`experimentData_exp${experimentId}`);
  let dataForCheck = {};
  if (savedDataForCheck) {
    try {
      dataForCheck = JSON.parse(savedDataForCheck);
    } catch (e) {
      console.error(`Failed to parse saved experiment data for exp ${experimentId} during check:`, e);
    }
  }

  const reportDataForCheck = {
    course_id: accountStore.course.courseId,
    experiment_id: experimentId, 
    ...dataForCheck,
  };

  const payload = {
    experimentId: experimentId, 
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
        console.log(`Initial check exp ${experimentId}: Report already scored. Submission blocked.`);
      }
    }
  } catch (error) {
    console.warn(`Error during initial report status check for exp ${experimentId}:`, error);
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
    course_id: accountStore.course.courseId,
    // experiment_id will be part of receivedData.value or fallback to component's experimentId
    ...receivedData.value,
  };
  const currentExperimentId = receivedData.value?.experiment_id || experimentId;

  if (!currentExperimentId) {
    message.error('无法确定实验ID，无法下载报告。');
    return;
  }
  reportData.experiment_id = currentExperimentId; // Ensure it's in reportData for the body

  if (reportData.submit_time && typeof reportData.submit_time === 'number') {
    reportData.submit_time = new Date(reportData.submit_time).toLocaleString();
  }

  try {
    const generateResponse = await fetch('http://see-toju.com:8002/api/reports/generate/pdf', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({
        experimentId: currentExperimentId, 
        courseId: accountStore.course.courseId,
        other: reportData
      })
    });

    if (!generateResponse.ok) {
      let errorData;
      try {
        errorData = await generateResponse.json();
      } catch (e) {
        throw new Error(`HTTP error! status: ${generateResponse.status}`);
      }

      if (generateResponse.status === 400 && errorData && errorData.msg === "生成报告失败：该学生曾提交的报告已评分，无法再提交新的报告") {
        message.warn(' 该学生曾提交的报告已评分，将尝试下载已存在的最新报告。');
        isSubmissionPermanentlyBlocked.value = true;
        permanentBlockMessage.value = errorData.msg;
        downloadReport();
        return;
      }
      throw new Error(`HTTP error! status: ${generateResponse.status}, message: ${errorData?.msg || '生成PDF失败'}`);
    }
    downloadReport();
  } catch (error: any) {
    console.error('There was a problem with the fetch operation for generating/downloading report:', error);
    message.error(`网络请求失败或处理错误: ${error.message || '下载报告时出错'}`);
  }
}

function downloadReport() {
  const courseId = accountStore.course.courseId;
  const token = localStorage.getItem('token');
  if (!token) {
    console.error('Token not found, please login first.');
    return;
  }
  const experimentIdForDownload = receivedData.value?.experiment_id || experimentId;
  if (!experimentIdForDownload) {
    message.error('无法确定实验ID，无法下载报告。');
    console.error('Experiment ID for download is missing.');
    return;
  }

  console.log(`Downloading latest report for expID: ${experimentIdForDownload}, courseID: ${courseId}`);

  fetch(`http://see-toju.com:8002/api/reports/students/latest?experimentId=${experimentIdForDownload}&courseId=${courseId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', // 设置请求体的类型为 JSON
      'Accept': 'application/json', // 设置期望的响应类型为 JSON
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
  //experimentId = 13;
  console.log("1111111",experimentId);
  courseId = accountStore.course.courseId;
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
  if (isSubmissionPermanentlyBlocked.value) {
    message.error(permanentBlockMessage.value || '报告已评分，无法再次提交。');
    return;
  }
  if (!experimentId) {
    message.error('无法确定实验ID，无法提交。');
    return;
  }

  const finalData = {
    experiment_id: experimentId, // Use the component's dynamic experimentId
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    submit_time: experimentDate.getTime(),
    course_name:accountStore.course.courseName ,
    course_id:accountStore.course.courseId ,
    ...data
  }
  
  console.log("提交数据", JSON.stringify(finalData));

  try {
    const res = await experimentStore.submitData(finalData);
    localStorage.setItem(`experimentData_exp${experimentId}`, JSON.stringify(finalData));
    receivedData.value = finalData; // Update receivedData after successful submission and storage
    message.success('提交成功');
    
  } catch (error: any) {
    const errorMsg = error?.response?.data?.msg || error?.message || '提交时发生未知错误';
    if (errorMsg === "生成报告失败：该学生曾提交的报告已评分，无法再提交新的报告") {
      isSubmissionPermanentlyBlocked.value = true;
      permanentBlockMessage.value = errorMsg;
      message.error(errorMsg);
    } else {
      message.error(`提交失败: ${errorMsg}`);
    }
  }
};

onMounted(async () => {
  if (!accountStore.course.courseName || !accountStore.course.courseId) {
    await accountStore.updateCourseId();
  }
  if (experimentId) {
    const savedData = localStorage.getItem(`experimentData_exp${experimentId}`);
    if (savedData) {
      try {
        receivedData.value = JSON.parse(savedData);
      } catch (e) {
        console.error(`Failed to parse saved experiment data for exp ${experimentId}:`, e);
        localStorage.removeItem(`experimentData_exp${experimentId}`);
      }
    }
    await checkInitialReportStatus();
  } else {
    console.error("Experiment ID not resolved on mount for Exp3, cannot load data or check status.");
    message.error("无法加载实验信息，请检查路由配置。");
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
