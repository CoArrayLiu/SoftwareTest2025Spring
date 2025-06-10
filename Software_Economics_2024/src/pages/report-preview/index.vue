<template>
  <div class="report-preview-container">
    <div class="header">
      <h1>实验报告预览</h1>
      <div class="actions">
        <a-button type="primary" @click="downloadFile">
          <DownloadOutlined />
          下载文档
        </a-button>
        <a-button @click="goBack">
          <ArrowLeftOutlined />
          返回
        </a-button>
      </div>
    </div>
    
    <a-spin :spinning="loading">
      <WordPreview v-if="docBlob" :blob="docBlob" />
      <a-empty v-else description="暂无报告数据" />
    </a-spin>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { DownloadOutlined, ArrowLeftOutlined } from '@ant-design/icons-vue';
import WordPreview from '@/components/WordPreview.vue';
import { message } from 'ant-design-vue';

const router = useRouter();
const route = useRoute();
const docBlob = ref<Blob | null>(null);
const loading = ref(true);

onMounted(() => {
  console.log('路由参数:', route.query);
  
  // 从 URL 获取 blob URL (通过路由参数传递)
  const blobUrl = route.query.blobUrl as string;
  if (blobUrl) {
    console.log('使用blobUrl获取报告:', blobUrl);
    fetchBlobFromUrl(blobUrl);
  } else if (route.query.reportData) {
    // 如果直接传递了报告数据，从服务器获取报告
    try {
      const decodedData = decodeURIComponent(route.query.reportData as string);
      console.log('收到报告数据参数:', decodedData.substring(0, 200) + '...');
      const reportData = JSON.parse(decodedData);
      console.log('解析后的报告数据:', reportData);
      generateReport(reportData);
    } catch (error) {
      console.error('解析报告数据出错:', error);
      message.error('报告数据格式错误，请重试');
      loading.value = false;
    }
  } else {
    console.error('未找到报告数据参数');
    message.error('未找到报告数据');
    loading.value = false;
  }
});

// 从 URL 获取 Blob 数据
const fetchBlobFromUrl = async (url: string) => {
  try {
    console.log('开始从URL获取报告:', url);
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error('获取报告失败');
    }
    docBlob.value = await response.blob();
    console.log('成功获取Blob数据, 大小:', docBlob.value.size);
  } catch (error) {
    console.error('获取Blob失败:', error);
    message.error('获取报告失败，请重试');
  } finally {
    loading.value = false;
  }
};

// 生成报告
const generateReport = async (reportData: any) => {
  loading.value = true;
  try {
    console.log('开始生成报告, 数据:', reportData);
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('未找到登录令牌');
      message.error('未登录，请先登录');
      loading.value = false;
      return;
    }

    // 直接将时间戳转换为可读格式
    if (reportData.submit_time) {
      reportData.submit_time = new Date(reportData.submit_time).toLocaleString();
    }

    // 构建正确的请求数据格式
    const requestData = {
      experimentId: reportData.experiment_id,
      courseId: reportData.course_id,
      other: reportData
    };
    
    console.log('发送API请求数据:', requestData);
    const response = await fetch('http://see-toju.com:8002/api/reports/generate/word', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(requestData),
    });

    console.log('API响应状态:', response.status);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const blob = await response.blob();
    console.log('成功获取报告Blob, 大小:', blob.size);
    docBlob.value = blob;
  } catch (error) {
    console.error('生成报告失败:', error);
    message.error('生成报告失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

// 下载文件
const downloadFile = () => {
  if (!docBlob.value) {
    message.error('无可下载的文件');
    return;
  }

  console.log('下载文件, 大小:', docBlob.value.size);
  const url = URL.createObjectURL(docBlob.value);
  const a = document.createElement('a');
  a.href = url;
  a.download = '实验报告.docx';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
  message.success('下载成功');
};

// 返回上一页
const goBack = () => {
  window.history.back();
};
</script>

<style scoped>
.report-preview-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.actions {
  display: flex;
  gap: 10px;
}
</style> 