<template>
  <div>
    <div ref="wordContainer" class="word-preview-container"></div>
    <a-spin v-if="loading" class="loading-spinner" />
    <div v-if="error" class="error-message">
      <a-alert type="error" :message="errorMessage" />
      <div v-if="objectUrl" class="fallback-container">
        <iframe :src="objectUrl" class="word-iframe"></iframe>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineProps, watch, onUnmounted } from 'vue';
import { message } from 'ant-design-vue';

const props = defineProps({
  blob: {
    type: Object as () => Blob,
    required: true
  }
});

const wordContainer = ref(null);
const loading = ref(true);
const error = ref(false);
const errorMessage = ref('预览文档时出错，尝试使用备选方式');
const objectUrl = ref('');

// 当 blob 变化时重新渲染
watch(() => props.blob, (newBlob) => {
  if (newBlob) {
    renderDocument(newBlob);
  }
}, { immediate: false });

onMounted(async () => {
  console.log('WordPreview 组件已加载，blob 类型:', props.blob?.type);
  if (props.blob && wordContainer.value) {
    renderDocument(props.blob);
  }
});

const renderDocument = async (blob: Blob) => {
  loading.value = true;
  error.value = false;
  
  try {
    console.log('开始渲染文档，大小:', blob.size, '类型:', blob.type);
    
    // 创建 Blob URL 作为备选预览方式
    objectUrl.value = URL.createObjectURL(blob);
    
    // 尝试使用 docx-preview 渲染
    try {
      // 动态导入 docx-preview
      const docxPreview = await import('docx-preview');
      if (docxPreview && docxPreview.renderAsync) {
        console.log('使用 docx-preview 渲染文档');
        await docxPreview.renderAsync(blob, wordContainer.value, null, {
          className: 'docx-viewer',
          inWrapper: true,
          ignoreWidth: false,
          ignoreHeight: false,
        });
        console.log('docx-preview 渲染成功');
      } else {
        throw new Error('docx-preview 库不可用');
      }
    } catch (err) {
      // 如果 docx-preview 不可用或渲染失败，显示 iframe 备选方案
      console.error('docx-preview 渲染失败:', err);
      error.value = true;
      errorMessage.value = `docx-preview 渲染失败，使用备选方式预览 (${err.message})`;
    }
  } catch (error) {
    console.error('预览文档时出错:', error);
    errorMessage.value = `预览失败：${error.message}`;
    error.value = true;
  } finally {
    loading.value = false;
  }
};

// 组件卸载时清理资源
const cleanupResources = () => {
  if (objectUrl.value) {
    URL.revokeObjectURL(objectUrl.value);
    objectUrl.value = '';
  }
};

onUnmounted(() => {
  cleanupResources();
});
</script>

<style scoped>
.word-preview-container {
  width: 100%;
  min-height: 500px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  padding: 20px;
  background: white;
  overflow: auto;
}

.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.error-message {
  margin-top: 20px;
}

.fallback-container {
  margin-top: 20px;
  width: 100%;
}

.word-iframe {
  width: 100%;
  height: 700px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

:deep(.docx-viewer) {
  padding: 20px;
}
</style> 