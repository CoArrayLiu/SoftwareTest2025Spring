<template>
  <div class="experiment-container">
    <div class="course-header">
      <div class="course-name">{{ accountStore.course.courseName || '软件工程管理与经济' }}</div>
      <el-button type="primary" class="create-button" :icon="Plus" @click="dialogVisible = true">创建实验</el-button>
    </div>
    <el-row :gutter="32" class="full-height">
      <!-- 左侧未发布实验列表 -->
      <el-col :span="12" class="full-height">
        <div class="experiment-section left-section">
          <div class="section-header">
            <h3>未发布实验</h3>
            <el-input v-model="searchUnpublished" placeholder="输入实验名称或类型进行搜索..." :prefix-icon="Search" clearable
                      class="search-input" />
          </div>

          <el-card v-loading="loading" shadow="hover" class="list-card">
            <el-table :data="filteredUnpublishedExperiments" style="width: 100%" height="100%"
                      :stripe="true" :border="true" :header-cell-style="tableHeaderStyle" :row-class-name="tableRowClassName"
                      highlight-current-row @row-click="handleRowClick">
              <el-table-column prop="experimentName" label="实验名称" min-width="120" show-overflow-tooltip align="center">
                <template #default="scope">
                  <span class="experiment-name">{{ scope.row.experimentName }}</span>
                </template>
              </el-table-column>

              <el-table-column prop="kind" label="类型" width="250" align="center">
                <template #default="scope">
                  <el-tag size="small" :type="getTagType(scope.row.kind)">
                    {{ scope.row.kind }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column fixed="right" label="操作" width="100" align="center">
                <template #default="scope">
                  <el-tooltip content="发布实验" placement="top" :hide-after="100">
                    <el-button type="primary" size="small" :icon="Upload" circle @click.stop="handlePublish(scope.row)"
                               :loading="publishingId === scope.row.experimentId">
                    </el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </el-col>

      <!-- 右侧已发布实验列表 -->
      <el-col :span="12" class="full-height">
        <div class="experiment-section right-section">
          <div class="section-header">
            <h3>已发布实验</h3>
            <el-input v-model="searchPublished" placeholder="输入实验名称或类型进行搜索..." :prefix-icon="Search" clearable
                      class="search-input" />
          </div>

          <el-card v-loading="loading" shadow="hover" class="list-card">
            <el-table :data="filteredPublishedExperiments" style="width: 100%" height="100%"
                      :stripe="true" :border="true" :header-cell-style="tableHeaderStyle" :row-class-name="tableRowClassName"
                      highlight-current-row @row-click="handleRowClick">
              <el-table-column prop="experimentName" label="实验名称" min-width="120" show-overflow-tooltip align="center">
                <template #default="scope">
                  <span class="experiment-name">{{ scope.row.experimentName }}</span>
                </template>
              </el-table-column>

              <el-table-column prop="kind" label="类型" width="180" align="center">
                <template #default="scope">
                  <el-tag size="small" :type="getTagType(scope.row.kind)">
                    {{ scope.row.kind }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column label="文件" width="135" align="center">
                <template #default="scope">
                  <el-tooltip content="下载指导书" placement="top" :hide-after="200" v-if="scope.row.experimentBook">
                    <el-button type="primary" link :icon="Document" @click.stop="downloadBook(scope.row)" class="file-button">
                      指导书
                    </el-button>
                  </el-tooltip>
                  <el-tooltip content="下载报告模板" placement="top" :hide-after="200" v-if="scope.row.reportTemplate">
                    <el-button type="primary" link :icon="Document" @click.stop="downloadTemplate(scope.row)" class="file-button">
                      报告模板
                    </el-button>
                  </el-tooltip>
                </template>
              </el-table-column>

              <el-table-column fixed="right" label="操作" width="135" align="center">
                <template #default="scope">
                  <el-tooltip content="编辑实验" placement="top" :hide-after="400">
                    <el-button type="primary" size="small" :icon="Edit" circle @click.stop="handleEdit(scope.row)"
                               :loading="editingId === scope.row.experimentId">
                    </el-button>
                  </el-tooltip>
                  <el-tooltip content="删除实验" placement="top" :hide-after="400">
                    <el-button type="danger" size="small" :icon="Delete" circle @click.stop="handleDelete(scope.row)"
                               :loading="deletingId === scope.row.experimentId">
                    </el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!-- 删除确认对话框 -->
    <el-dialog v-model="deleteDialogVisible" title="确认删除" width="400px" class="custom-dialog" destroy-on-close center>
      <div class="dialog-content">
        <el-icon class="warning-icon">
          <Warning />
        </el-icon>
        <span>确定要删除这个实验吗？此操作不可恢复</span>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete" :loading="deleting">确定删除</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 创建实验的对话框 -->
    <el-dialog
      title="创建实验"
      v-model="dialogVisible"
      width="50%">
      <el-form :model="newExperiment" label-width="120px" ref="experimentForm">
        <el-form-item label="实验名称" prop="experimentName" :rules="[{ required: true, message: '请输入实验名称', trigger: 'blur' }]">
          <el-input v-model="newExperiment.experimentName" placeholder="请输入实验名称"></el-input>
        </el-form-item>
        <el-form-item label="实验编号" prop="experimentId" :rules="[{ required: true, message: '请输入实验编号', trigger: 'blur' }]">
          <el-input v-model="newExperiment.experimentId" placeholder="请输入实验编号"></el-input>
        </el-form-item>
        <el-form-item label="实验类型" prop="kind" :rules="[{ required: true, message: '请选择实验类型', trigger: 'change' }]">
          <el-select v-model="newExperiment.kind" placeholder="请选择实验类型" style="width: 100%;">
            <el-option label="基础实验" value="基础实验"></el-option>
            <el-option label="综合实验" value="综合实验"></el-option>
            <el-option label="设计实验" value="设计实验"></el-option>
            <el-option label="创新实验" value="创新实验"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitNewExperiment" :loading="loading">创建</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
  import { ref, computed, onMounted, watch } from 'vue'
  import { useAccountStore } from '@/store/account'
  import { ElMessage } from 'element-plus'
  import {
    getExperimentsNotInCourse,
    getExperimentsInCourse,
    publishExperiment,
    deleteExperiment,
    downloadExperimentBook,
    downloadExperimentReportTemplate
  } from '@/api/experiment'
  import { Search, Upload, Document, Edit, Delete, Plus } from '@element-plus/icons-vue'
  import { useRouter } from 'vue-router'
  import axios from 'axios'

  const accountStore = useAccountStore()
  const router = useRouter()
  const loading = ref(false)
  const publishingId = ref(null)
  const deletingId = ref(null)
  const deleteDialogVisible = ref(false)
  const experimentToDelete = ref(null)
  const unpublishedExperiments = ref([])
  const publishedExperiments = ref([])
  const searchUnpublished = ref('')
  const searchPublished = ref('')
  const editingId = ref(null)
  const dialogVisible = ref(false)
  const newExperiment = ref({
    experimentId: '',
    experimentName: '',
    kind: ''
  })

  // 过滤未发布实验
  const filteredUnpublishedExperiments = computed(() => {
    if (!searchUnpublished.value) return unpublishedExperiments.value
    const searchText = searchUnpublished.value.toLowerCase()
    return unpublishedExperiments.value.filter(exp =>
      exp.experimentName.toLowerCase().includes(searchText) ||
      exp.kind.toLowerCase().includes(searchText)
    )
  })

  // 过滤已发布实验
  const filteredPublishedExperiments = computed(() => {
    if (!searchPublished.value) return publishedExperiments.value
    const searchText = searchPublished.value.toLowerCase()
    return publishedExperiments.value.filter(exp =>
      exp.experimentName.toLowerCase().includes(searchText) ||
      exp.kind.toLowerCase().includes(searchText)
    )
  })

  // 获取实验列表
  const fetchExperiments = async () => {
    loading.value = true
    try {
      const courseId = accountStore.course.courseId
      if (!courseId) {
       // ElMessage.warning('请先选择课程')
        return
      }

      const [unpublishedResult, publishedResult] = await Promise.all([
        getExperimentsNotInCourse(courseId),
        getExperimentsInCourse(courseId)
      ])

      if (unpublishedResult.success) {
        unpublishedExperiments.value = unpublishedResult.experiments
      }
      if (publishedResult.success) {
        publishedExperiments.value = publishedResult.experiments
      }
    } catch (error) {
      console.error('获取实验列表错误:', error)
      ElMessage.error('获取实验列表失败')
    } finally {
      loading.value = false
    }
  }

  // 发布实验
  const handlePublish = async (experiment) => {
    if (publishingId.value) return

    publishingId.value = experiment.experimentId

    try {
      const courseId = accountStore.course.courseId
      const result = await publishExperiment(courseId, [experiment.experimentId])

      if (result.success) {
        ElMessage.success('发布成功')
        await fetchExperiments()
      } else {
        ElMessage.error(result.message || '发布失败')
        // 立即清除加载状态
        publishingId.value = null
      }
    } catch (error) {
      console.error('发布实验错误:', error)
      ElMessage.error('发布失败')
      // 立即清除加载状态
      publishingId.value = null
    }

    // 设置一个最大显示时间（例如 2 秒）
    setTimeout(() => {
      if (publishingId.value === experiment.experimentId) {
        publishingId.value = null
      }
    }, 2000)
  }

  // 删除实验
  const handleDelete = (experiment) => {
    experimentToDelete.value = {
      experimentId: experiment.experimentId,
      courseId: accountStore.course.courseId
    }
    deleteDialogVisible.value = true
  }

  // 删除状态
  const deleting = ref(false)

  // 确认删除
  const confirmDelete = async () => {
    if (deleting.value) return

    try {
      deleting.value = true
      const result = await deleteExperiment(
        experimentToDelete.value.courseId,
        experimentToDelete.value.experimentId
      )
      if (result.success) {
        ElMessage.success('删除成功')
        deleteDialogVisible.value = false
        await fetchExperiments()
      } else {
        ElMessage.error(result.message || '删除失败')
      }
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    } finally {
      deleting.value = false
      experimentToDelete.value = null
    }
  }

  // 下载实验指导书
  const downloadBook = async (experiment) => {
    try {
      const courseId = accountStore.course.courseId;
      const token = localStorage.getItem('token');
      if (!token) {
        console.error('Token not found, please login first.');
        return;
      }

      // 使用相对路径，利用Vite代理解决CORS问题
      const response = await fetch(`http://localhost:8002/api/courses/${courseId}/experiments/${experiment.experimentId}/guidebook`, {
        method: 'GET',
        headers: {
          'Accept': 'application/octet-stream',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }

      const contentDisposition = response.headers.get('content-disposition');
      let filename = 'experiment_guide_';
      filename = `${filename}${experiment.experimentName}`;
      if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
        if (filenameMatch && filenameMatch[1]) {
          filename = filenameMatch[1].replace(/['"]/g, '');
        }
      }

      const blob = await response.blob();
      const downloadUrl = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = downloadUrl;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(downloadUrl);
      
      ElMessage.success('下载成功');
    } catch (error) {
      console.error('下载指导书错误:', error);
      ElMessage.error('下载失败');
    }
  }

  // 下载实验报告模板
  const downloadTemplate = async (experiment) => {
    try {
      const courseId = accountStore.course.courseId;
      const token = localStorage.getItem('token');
      if (!token) {
        console.error('Token not found, please login first.');
        return;
      }

      // 使用相对路径，利用Vite代理解决CORS问题
      const response = await fetch(`http://localhost:8002/api/courses/${courseId}/experiments/${experiment.experimentId}/template`, {
        method: 'GET',
        headers: {
          'Accept': 'application/octet-stream',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }

      const contentDisposition = response.headers.get('content-disposition');
      let filename = 'report_template_';
      filename = `${filename}${experiment.experimentName}`;
      if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
        if (filenameMatch && filenameMatch[1]) {
          filename = filenameMatch[1].replace(/['"]/g, '');
        }
      }

      const blob = await response.blob();
      const downloadUrl = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = downloadUrl;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(downloadUrl);
      
      ElMessage.success('下载成功');
    } catch (error) {
      console.error('下载模板错误:', error);
      ElMessage.error('下载失败');
    }
  }

  // 修改编辑方法
  const handleEdit = (experiment) => {
    if (editingId.value) return

    try {
      editingId.value = experiment.experimentId
      const courseId = accountStore.course.courseId

      // 使用 params 和 query 参数进行路由跳转
      router.push({
        name: 'ExperimentEdit',
        params: {
          experimentId: experiment.experimentId.toString() // 确保转换为字符串
        },
        query: {
          courseId: courseId.toString() // 确保转换为字符串
        }
      })
    } catch (error) {
      console.error('跳转编辑页面错误:', error)
      ElMessage.error('操作失败')
    } finally {
      editingId.value = null
    }
  }

  onMounted(() => {
    fetchExperiments()
  })

  // 监听courseId变化，重新加载实验列表
  watch(() => accountStore.course, () => {
    fetchExperiments()
  }, { deep: true })

  // 表格样式
  const tableHeaderStyle = {
    backgroundColor: '#f8fafc',
    color: '#1f2937',
    fontWeight: '600'
  }

  // 获取标签类型
  const getTagType = (kind) => {
    const typeMap = {
      '基础实验': '',
      '综合实验': 'success',
      '设计实验': 'warning',
      '创新实验': 'danger'
    }
    return typeMap[kind] || 'info'
  }

  // 处理行点击
  const handleRowClick = (row) => {
    // 可以添加行点击的逻辑，比如显示详情等
    console.log('Row clicked:', row)
  }

  // 表格行样式
  const tableRowClassName = ({ rowIndex }) => {
    return rowIndex % 2 === 0 ? 'even-row' : 'odd-row'
  }

  const submitNewExperiment = async () => {
    try {
      const token = localStorage.getItem('token');
      // 确保experimentId是数字类型
      const experimentData = {
        ...newExperiment.value,
        experimentId: parseInt(newExperiment.value.experimentId)
      };
      
      const response = await axios.post('http://localhost:8002/api/experiments', experimentData, {
        headers: {
          'Authorization': token ? `Bearer ${token}` : '',
          'Content-Type': 'application/json'
        }
      });

      if (response.data.success) {
        ElMessage.success('创建实验成功');
        dialogVisible.value = false;
        await fetchExperiments();
      } else {
        ElMessage.error(response.data.msg || '创建实验失败');
      }
    } catch (error) {
      console.error('创建实验错误:', error);
      ElMessage.error(error.response?.data?.msg || '创建实验失败');
    }
  }
</script>

<style scoped>
  .experiment-container {
    height: 100vh;
    padding: 12px 24px;
    box-sizing: border-box;
    background: linear-gradient(to bottom, #f0f2f5, #ffffff);
    position: relative;
    overflow: hidden;
  }

  .full-height {
    height: 96%;
  }

  .experiment-section {
    height: 100%;
    display: flex;
    flex-direction: column;
    background: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(10px);
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    padding: 24px;
    position: relative;
    z-index: 1;
    animation: fadeIn 0.5s ease;
  }

  .left-section {
    margin-right: 6px;
  }

  .right-section {
    margin-left: -16px;
  }

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    padding: 16px;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }

  .section-header h3 {
    margin: 0;
    color: #1f2937;
    font-size: 20px;
    font-weight: 600;
    position: relative;
    padding-left: 12px;
  }

  .section-header h3::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 24px;
    background: linear-gradient(to bottom, #409eff, #66b1ff);
    border-radius: 2px;
  }

  .search-input {
    width: 320px;
    transition: all 0.3s ease;
  }

  :deep(.el-input__wrapper) {
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    padding: 0 16px;
  }

  :deep(.el-input__wrapper:hover) {
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.15);
    transform: translateY(-1px);
  }

  .list-card {
    flex: 1;
    border-radius: 16px;
    overflow: hidden;
    transition: all 0.3s ease;
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  }

  :deep(.el-card__body) {
    padding: 0;
    height: 100%;
  }

  :deep(.el-table) {
    height: 95% !important;
    font-size: 14px;
    background: transparent;
  }

  :deep(.el-table__header) {
    background-color: rgba(248, 250, 252, 0.8);
  }

  :deep(.el-table__header th) {
    background-color: rgba(248, 250, 252, 0.8);
    color: #1f2937;
    font-weight: 600;
    height: 56px;
    font-size: 14px;
  }

  :deep(.el-table__row) {
    height: 56px;
    transition: all 0.3s ease;
  }

  :deep(.el-table__row:hover) {
    background-color: rgba(64, 158, 255, 0.05) !important;
    transform: translateY(-1px);
  }

  :deep(.el-button--primary) {
    background: linear-gradient(45deg, #409eff, #66b1ff);
    border: none;
  }

  :deep(.el-button--primary:hover) {
    background: linear-gradient(45deg, #66b1ff, #409eff);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  }

  :deep(.el-button--danger) {
    background: linear-gradient(45deg, #f56c6c, #ff9d9d);
    border: none;
  }

  :deep(.el-button--danger:hover) {
    background: linear-gradient(45deg, #ff9d9d, #f56c6c);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(245, 108, 108, 0.3);
  }

  :deep(.el-button--link) {
    font-size: 14px;
    font-weight: 500;
    padding: 4px 8px;
  }

  :deep(.el-button--link:hover) {
    color: #66b1ff;
    transform: translateY(-1px);
  }

  :deep(.el-tag) {
    border-radius: 12px;
    padding: 0 12px;
    height: 24px;
    line-height: 24px;
    border: none;
  }

  .custom-dialog {
    border-radius: 16px;
    overflow: hidden;
  }

  .dialog-content {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 24px 0;
  }

  .warning-icon {
    font-size: 28px;
    color: #e6a23c;
    animation: pulse 2s infinite;
  }

  @keyframes pulse {
    0% {
      transform: scale(1);
    }

    50% {
      transform: scale(1.1);
    }

    100% {
      transform: scale(1);
    }
  }

  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    padding-top: 16px;
  }

  @media screen and (max-width: 1200px) {
    .search-input {
      width: 260px;
    }
  }

  @media screen and (max-width: 768px) {
    .experiment-container {
      padding: 16px;
    }

    .el-row {
      flex-direction: column;
    }

    .el-col {
      width: 100% !important;
      margin-bottom: 24px;
    }

    .experiment-section {
      margin: 0;
      padding: 16px;
    }

    .search-input {
      width: 100%;
    }

    .section-header {
      flex-direction: column;
      gap: 16px;
      align-items: flex-start;
    }
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(20px);
    }

    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  :deep(.file-button) {
    color: #1e40af;  /* 使用深蓝色 */
    font-weight: bold;
    background-color: #e0f2fe;  /* 非常淡的蓝色背景 */
    border-radius: 4px;
    padding: 4px 8px;
    margin: 2px 0;
    transition: all 0.3s;
  }

  :deep(.file-button:hover) {
    color: #1e3a8a;  /* hover时更深的蓝色 */
    background-color: #bfdbfe;  /* hover时稍深的背景 */
  }

  .course-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
    margin-top: -12px;
    padding: 10px 16px;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }

  .course-name {
    font-size: 18px;
    font-weight: 600;
    color: #1f2937;
    position: relative;
    padding-left: 12px;
  }

  .course-name::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 18px;
    background: linear-gradient(to bottom, #409eff, #66b1ff);
    border-radius: 2px;
  }

  .create-button {
    background: linear-gradient(45deg, #409eff, #66b1ff);
    border: none;
    border-radius: 4px;
    padding: 8px 16px;
    font-weight: 500;
    transition: all 0.3s ease;
  }

  .create-button:hover {
    background: linear-gradient(45deg, #66b1ff, #409eff);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  }
</style>