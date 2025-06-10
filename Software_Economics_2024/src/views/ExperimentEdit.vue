<template>
  <div class="experiment-edit-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>实验编辑</h2>
          <div class="header-buttons">
            <el-button @click="handleCancel">取消</el-button>
            <el-button type="primary" @click="saveExperiment">保存</el-button>
          </div>
        </div>
      </el-header>

      <el-main>
        <el-form :model="experimentForm" label-width="120px">
          <!-- 实验时间设置 -->
          <el-form-item label="开始时间">
            <el-date-picker v-model="experimentForm.startTime" type="datetime" placeholder="选择开始时间"
                            format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
          </el-form-item>

          <el-form-item label="结束时间">
            <el-date-picker v-model="experimentForm.endTime" type="datetime" placeholder="选择结束时间"
                            format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
          </el-form-item>

          <el-form-item label="总分">
            <el-input-number v-model="experimentForm.score" :min="0" :max="100" :precision="1" :step="0.5" />
          </el-form-item>

          <!-- 实验内容展示区 -->
          <el-form-item label="实验内容">
            <el-input type="textarea" v-model="experimentForm.content" :rows="10" placeholder="请输入实验内容、步骤、目标等..." />
          </el-form-item>

          <!-- 实验指导书 -->
          <el-form-item label="实验指导书">
            <div class="file-section">
              <!-- 当前文件显示 -->
              <div v-if="experimentForm.bookUrl" class="current-file">
                <span>当前文件：</span>
                <el-link type="primary" @click="downloadFile('book')">
                  {{ getFileName(experimentForm.bookUrl) }}
                </el-link>
              </div>

              <!-- 上传区域 -->
              <el-upload class="upload-demo" action="#" :auto-upload="false" :on-change="handleBookChange"
                         :before-upload="beforeUpload" ref="bookUploadRef">
                <el-button type="primary">
                  {{ experimentForm.bookUrl ? '更新指导书' : '上传指导书' }}
                </el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    支持 PDF、Word 格式，文件大小不超过 10MB
                  </div>
                </template>
              </el-upload>
            </div>
          </el-form-item>

          <!-- 实验报告模板 -->
          <el-form-item label="实验报告模板">
            <div class="file-section">
              <!-- 当前文件显示 -->
              <div v-if="experimentForm.templateUrl" class="current-file">
                <span>当前文件：</span>
                <el-link type="primary" @click="downloadFile('template')">
                  {{ getFileName(experimentForm.templateUrl) }}
                </el-link>
              </div>

              <!-- 上传区域 -->
              <el-upload class="upload-demo" action="#" :auto-upload="false" :on-change="handleTemplateChange"
                         :before-upload="beforeUpload" ref="templateUploadRef">
                <el-button type="primary">
                  {{ experimentForm.templateUrl ? '更新报告模板' : '上传报告模板' }}
                </el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    支持 Word 格式，实验报告模板仅供后台生成实验报告使用
                  </div>
                </template>
              </el-upload>
            </div>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useStore } from 'vuex'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import {
    getExperimentContent,
    modifyExperimentInCourse,
    uploadExperimentContent,
    uploadExperimentBook,
    uploadExperimentTemplate,
    downloadExperimentBook,
    downloadExperimentReportTemplate
  } from '@/api/experiment'

  const route = useRoute()
  const router = useRouter()
  const store = useStore()

  const experimentForm = ref({
    startTime: '',
    endTime: '',
    score: 100,
    content: '',
    bookUrl: '',
    templateUrl: ''
  })

  // 基础 URL
  const baseUrl = 'http://localhost:8002/api/experiment'

  // 获取实验内容
  const fetchExperimentContent = async () => {
    try {
      const result = await getExperimentContent(route.params.experimentId, route.query.courseId)
      if (result.success) {
        experimentForm.value = {
          content: result.data.content || '',
          bookUrl: result.data.bookUrl || '',
          templateUrl: result.data.templateUrl || '',
          startTime: result.data.startTime ? formatTimestamp(result.data.startTime) : '',
          endTime: result.data.endTime ? formatTimestamp(result.data.endTime) : '',
          score: result.data.score || 100
        }
      } else {
        ElMessage.error(result.message || '获取实验内容失败')
      }
    } catch (error) {
      console.error('获取实验内容失败:', error)
      ElMessage.error('获取实验内容失败')
    }
  }

  // 修改时间戳格式化函数，处理可能的时间
  const formatTimestamp = (timestamp) => {
    if (!timestamp) return ''
    let date
    if (typeof timestamp === 'number') {
      date = new Date(timestamp)
    } else if (typeof timestamp === 'string') {
      // 如果是ISO格式的字符串，直接创建Date对象
      date = new Date(timestamp)
    } else {
      return ''
    }

    if (isNaN(date.getTime())) return '' // 检查日期是否有效

    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    const hours = String(date.getHours()).padStart(2, '0')
    const minutes = String(date.getMinutes()).padStart(2, '0')
    const seconds = String(date.getSeconds()).padStart(2, '0')
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
  }

  // 从URL中获取文件名
  const getFileName = (url) => {
    if (!url) return ''
    const parts = url.split('/')
    return parts[parts.length - 1]
  }

  // 文件上传相关配置
  const uploadHeaders = {
    // 如果需要认证token等
    // 'Authorization': `Bearer ${token}`
  }

  const getUploadData = (type) => ({
    experimentId: parseInt(route.params.experimentId),
    courseId: parseInt(route.query.courseId),
  })

  const beforeUpload = (file) => {
    const isValidType = /\.(pdf|doc|docx)$/i.test(file.name)
    const isLt10M = file.size / 1024 / 1024 < 10

    if (!isValidType) {
      ElMessage.error('只支持 PDF 和 Word 格式的文件!')
      return false
    }
    if (!isLt10M) {
      ElMessage.error('文件大小不能超过 10MB!')
      return false
    }
    return true
  }

  // 文件下载
  const downloadFile = async (type) => {
    try {
      const experimentId = route.params.experimentId;
      const courseId = route.query.courseId;
      const token = localStorage.getItem('token');
      
      // 使用RESTful风格的接口
      const endpoint = type === 'book' ? 'guidebook' : 'template';
      const url = `http://localhost:8002/api/courses/${courseId}/experiments/${experimentId}/${endpoint}`;
      
      // 使用fetch进行文件下载
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/octet-stream',
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });
      
      if (!response.ok) {
        throw new Error(`下载失败，状态码: ${response.status}`);
      }
      
      // 获取文件名
      const contentDisposition = response.headers.get('content-disposition');
      let filename = type === 'book' ? '实验指导书.pdf' : '实验报告模板.pdf';
      
      if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
        if (filenameMatch && filenameMatch[1]) {
          filename = filenameMatch[1].replace(/['"]/g, '');
        }
      }
      
      // 处理文件下载
      const blob = await response.blob();
      const downloadUrl = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = downloadUrl;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(downloadUrl);
      
      ElMessage.success('下载成功');
    } catch (error) {
      console.error('下载失败:', error);
      ElMessage.error('下载失败: ' + error.message);
    }
  }

  // 添加文件上传方法
  const uploadBook = async (file) => {
    try {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('experimentId', route.params.experimentId)
      formData.append('courseId', route.query.courseId)

      // 使用相对路径利用Vite代理
      const token = localStorage.getItem('token')
      const courseId = route.query.courseId
      const response = await fetch(`http://localhost:8002/api/courses/${courseId}/experiments/${route.params.experimentId}/guidebook`, {
        method: 'PUT',
        headers: {
          'Authorization': token ? `Bearer ${token}` : ''
        },
        body: formData
      })

      if (!response.ok) {
        // 尝试打印响应内容以获取更多错误信息
        const errorText = await response.text();
        console.error('服务器响应(上传指导书):', errorText);
        throw new Error(`上传失败，状态码: ${response.status}, 错误信息: ${errorText}`);
      }

      const result = await response.json()
      if (!result.success) {
        throw new Error(result.message || '实验指导书上传失败')
      }
      experimentForm.value.bookUrl = result.data
      return result
    } catch (error) {
      console.error('上传实验指导书失败:', error)
      throw new Error(error.message || '实验指导书上传失败')
    }
  }

  const uploadTemplate = async (file) => {
    try {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('experimentId', route.params.experimentId)
      formData.append('courseId', route.query.courseId)

      // 使用相对路径利用Vite代理
      const token = localStorage.getItem('token')
      const courseId = route.query.courseId
      const response = await fetch(`http://localhost:8002/api/courses/${courseId}/experiments/${route.params.experimentId}/template`, {
        method: 'PUT',
        headers: {
          'Authorization': token ? `Bearer ${token}` : ''
        },
        body: formData
      })

      if (!response.ok) {
        // 尝试打印响应内容以获取更多错误信息
        const errorText = await response.text();
        console.error('服务器响应(上传模板):', errorText);
        throw new Error(`上传失败，状态码: ${response.status}, 错误信息: ${errorText}`);
      }

      const result = await response.json()
      if (!result.success) {
        throw new Error(result.message || '实验报告模板上传失败')
      }
      experimentForm.value.templateUrl = result.data
      return result
    } catch (error) {
      console.error('上传实验报告模板失败:', error)
      throw new Error(error.message || '实验报告模板上传失败')
    }
  }

  // 上传实验内容
  const uploadContent = async () => {
    if (experimentForm.value.content) {
      try {
        const token = localStorage.getItem('token')
        const courseId = parseInt(route.query.courseId)
        const experimentId = parseInt(route.params.experimentId)
        
        // 构建更新请求数据，使用正确的字段名
        const jsonData = {
          experimentContent: experimentForm.value.content,
          experimentId: experimentId,
          courseId: courseId,
          // 保留实验的其他关键属性
          startTime: experimentForm.value.startTime ? new Date(experimentForm.value.startTime).getTime() : undefined,
          endTime: experimentForm.value.endTime ? new Date(experimentForm.value.endTime).getTime() : undefined,
          score: experimentForm.value.score
        }
        
        // 使用统一的实验更新接口
        const response = await fetch(`http://localhost:8002/api/courses/${courseId}/experiments/${experimentId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': token ? `Bearer ${token}` : ''
          },
          body: JSON.stringify(jsonData)
        })

        if (!response.ok) {
          // 尝试打印响应内容以获取更多错误信息
          const errorText = await response.text();
          console.error('服务器响应:', errorText);
          throw new Error(`上传失败，状态码: ${response.status}, 错误信息: ${errorText}`);
        }

        const result = await response.json()
        if (!result.success) {
          throw new Error(result.message || '实验内容上传失败')
        }

        return result
      } catch (error) {
        console.error('上传实验内容失败:', error)
        throw new Error(error.message || '实验内容上传失败')
      }
    }
  }

  // 添加文件选择处理函数
  const selectedBookFile = ref(null)
  const selectedTemplateFile = ref(null)

  const handleBookChange = (file) => {
    selectedBookFile.value = file.raw
  }

  const handleTemplateChange = (file) => {
    selectedTemplateFile.value = file.raw
  }

  // 修改保存方法
  const saveExperiment = async () => {
    try {
      // 验证时间
      if (new Date(experimentForm.value.endTime) <= new Date(experimentForm.value.startTime)) {
        ElMessage.error('结束时间必须晚于开始时间')
        return
      }

      // 处理文件上传 (注意：这些需要单独上传)
      if (selectedBookFile.value) {
        try {
          await uploadBook(selectedBookFile.value)
        } catch (error) {
          console.error("指导书上传失败:", error)
          ElMessage.error("指导书上传失败: " + error.message)
          return
        }
      }

      if (selectedTemplateFile.value) {
        try {
          await uploadTemplate(selectedTemplateFile.value)
        } catch (error) {
          console.error("模板上传失败:", error)
          ElMessage.error("模板上传失败: " + error.message)
          return
        }
      }

      // 统一更新实验的所有属性
      const courseId = parseInt(route.query.courseId)
      const experimentId = parseInt(route.params.experimentId)
      
      // 合并所有需要更新的字段到一个请求中
      const modifyDto = {
        courseId: courseId,
        experimentId: experimentId,
        startTime: new Date(experimentForm.value.startTime).getTime(),
        endTime: new Date(experimentForm.value.endTime).getTime(),
        score: experimentForm.value.score,
        experimentContent: experimentForm.value.content // 添加实验内容字段
      }

      // 使用相对路径
      const token = localStorage.getItem('token')
      
      const response = await fetch(`http://localhost:8002/api/courses/${modifyDto.courseId}/experiments/${modifyDto.experimentId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        },
        body: JSON.stringify(modifyDto)
      })

      if (!response.ok) {
        // 尝试打印响应内容以获取更多错误信息
        const errorText = await response.text();
        console.error('服务器响应(修改属性):', errorText);
        throw new Error(`修改失败，状态码: ${response.status}, 错误信息: ${errorText}`);
      }

      const result = await response.json()

      if (result.success) {
        ElMessage.success('保存成功')
        router.push('/teacher/experiment')
      } else {
        ElMessage.error(result.message || '保存失败')
      }
    } catch (error) {
      console.error('保存失败:', error)
      ElMessage.error(error.message || '保存失败')
    }
  }

  // 添加取消方法
  const handleCancel = () => {
    ElMessageBox.confirm(
      '确定要取消编辑吗？未保存的修改将会丢失',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
      .then(() => {
        router.push('/teacher/experiment')
      })
      .catch(() => {
        // 用点击取消，停留在当前页面
      })
  }

  onMounted(() => {
    fetchExperimentContent()
  })
</script>

<style scoped>
  .experiment-edit-container {
    padding: 20px;
  }

  .header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }

  .file-section {
    margin-bottom: 20px;
  }

  .current-file {
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .el-upload__tip {
    color: #909399;
    font-size: 12px;
    margin-top: 5px;
  }

  .el-form {
    max-width: 800px;
    margin: 0 auto;
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .upload-demo {
    margin-top: 10px;
  }

  .header-buttons {
    display: flex;
    gap: 12px;
  }
</style>