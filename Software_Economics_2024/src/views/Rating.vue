<template>
  <div>
    <h1>{{ accountStore.course.courseName || '软件工程管理与经济' }} - 评分模块</h1>

    <!-- 班级平均分显示 -->
    <div v-if="students.length > 0" style="margin-top: -40px; text-align: right; font-size: 20px; margin-bottom: 10px;">
      <el-button type="primary" @click="previewTranscript" style="margin-right: 20px;">预览成绩单</el-button>
      <el-button type="primary" @click="getTranscript" style="margin-right: 20px;" :disabled="!isTranscriptConfirmed">获取成绩单</el-button>
      班级平均分：<strong>{{ classAverageScore }}</strong>
    </div>

    <!-- 预览成绩单对话框 -->
    <el-dialog
      v-model="previewDialogVisible"
      title="成绩单预览"
      width="90%"
      :before-close="handleClosePreview"
    >
      <el-table :data="students" style="width: 100%;font-size: 18px;" max-height="70vh" stripe
                border :default-sort="{ prop: 'studentIndex', order: 'ascending' }">
        <el-table-column label="学生姓名" prop="studentName" fixed align="center" min-width="100"/>

        <!-- 动态生成实验列 -->
        <el-table-column v-for="experiment in experiments" :key="experiment.experimentId"
                         :label="experiment.experimentName" show-overflow-tooltip align="center" min-width="150">
          <template #default="{ row }">
            <div>
              <template v-if="!row.reports[experiment.experimentId] || row.reports[experiment.experimentId].status === 'none'">
                <span>-</span>
              </template>
              <template v-else-if="row.reports[experiment.experimentId].status === 'submitted'">
                <span>未评分</span>
              </template>
              <template v-else-if="row.reports[experiment.experimentId].status === 'graded'">
                <span>{{ row.reports[experiment.experimentId].score }}</span>
              </template>
              <template v-else>
                <span>{{ row.reports[experiment.experimentId].status || '-' }}</span>
              </template>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="平均分" prop="totalScore" sortable align="center" min-width="100"/>
      </el-table>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleClosePreview">取消</el-button>
          <el-button type="primary" @click="confirmTranscript">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- Loading 状态 -->
    <el-loading :loading="isLoading" text="数据加载中..." />

    <el-table v-if="students.length > 0" :data="students" style="width: 100%;font-size: 18px;" max-height="90vh" stripe
              border :default-sort="{ prop: 'studentIndex', order: 'ascending' }">
      <el-table-column label="学生姓名" prop="studentName" fixed align="center" min-width="100"/>

      <!-- 动态生成实验列 -->
      <el-table-column v-for="experiment in experiments" :key="experiment.experimentId"
                       :label="experiment.experimentName" show-overflow-tooltip align="center" min-width="125">
        <template #default="{ row }">
          <div>
            <template v-if="!row.reports[experiment.experimentId] || row.reports[experiment.experimentId].status === 'none'">
              <span>-</span>
            </template>
            <template v-else-if="row.reports[experiment.experimentId].status === 'submitted'">
              <el-row :gutter="8" style="flex-direction: column;">
                <el-col :span="24">
                  <el-input v-model="scores[`${row.studentIndex}-${experiment.experimentId}`]" type="number"
                            placeholder="打分" min="0" max="100" style="width: 80px; margin-right: 8px" />
                </el-col>
                <el-col :span="24">
                  <el-button @click="submitScore(row.studentIndex, experiment.experimentId)" size="mini">
                    确认
                  </el-button>
                </el-col>
                <el-col :span="24">
                  <el-button @click="viewReport(row.studentIndex, experiment.experimentId)" size="mini">
                    查看报告
                  </el-button>
                </el-col>
              </el-row>
            </template>
            <template v-else-if="row.reports[experiment.experimentId].status === 'graded'">
              <el-row :gutter="8" style="flex-direction: column;">
                <el-col :span="24">
                  <span v-if="!row.reports[experiment.experimentId].isEditing">{{ row.reports[experiment.experimentId].score }}</span>
                  <el-input v-else v-model="scores[`${row.studentIndex}-${experiment.experimentId}`]" type="number"
                            placeholder="打分" min="0" max="100" style="width: 80px; margin-right: 8px" />
                </el-col>
                <el-col :span="24">
                  <el-button v-if="!row.reports[experiment.experimentId].isEditing"
                             @click="startReScore(row.studentIndex, experiment.experimentId)"
                             size="mini" type="primary">
                    重新评分
                  </el-button>
                  <el-button v-else
                             @click="submitScore(row.studentIndex, experiment.experimentId)"
                             size="mini" type="success">
                    确认
                  </el-button>
                </el-col>
                <el-col :span="24">
                  <el-button @click="viewReport(row.studentIndex, experiment.experimentId)" size="mini">
                    查看报告
                  </el-button>
                </el-col>
              </el-row>
            </template>
            <template v-else>
              <span>{{ row.reports[experiment.experimentId].status || '-' }}</span>
            </template>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="平均分" prop="totalScore" sortable align="center" min-width="80"/>
    </el-table>
    
    <!-- 没有数据时提示 -->
    <el-alert v-else title="没有数据" type="info" description="暂无学生数据" />
  </div>
</template>

<script>
  import axios from 'axios';
  import {
    fetchExperiments,
    fetchStudents,
    fetchStudentScores,
    fetchReport,
    fetchReportId,
    fetchReportStatus
  } from '@/api/rating';
  import { useAccountStore } from '@/store/account';
  import { ElMessage } from 'element-plus';

  const API_BASE_URL = 'http://localhost:8002/api';

  export default {
    name: 'Rating',
    data() {
      return {
        experiments: [],
        students: [],
        scores: {},
        isLoading: true,
        previewDialogVisible: false,
        isTranscriptConfirmed: false,
      };
    },
    computed: {
      classAverageScore() {
        const valid = this.students.filter(s => typeof s.totalScore === 'number' && !isNaN(s.totalScore));
        if (valid.length === 0) return 0;
        const sum = valid.reduce((acc, s) => acc + s.totalScore, 0);
        return Number((sum / valid.length).toFixed(2));
      }
    },
    async created() {
      this.accountStore = useAccountStore();
      const courseId = this.accountStore.course.courseId;

      if (!courseId) {
        console.error('缺少课程 ID');
        return;
      }

      try {
        this.isLoading = true;
        // 并行获取所有初始数据
        const [experimentResponse, studentResponse] = await Promise.all([
          fetchExperiments(courseId),
          fetchStudents(courseId),
        ]);

        if (experimentResponse.success) {
          this.experiments = experimentResponse.experiments;
        } else {
          console.error('获取实验数据失败：', experimentResponse.message);
        }

        if (studentResponse.success) {
          this.students = studentResponse.students.map(student => ({
            ...student,
            reports: {},
            totalScore: 0
          })).sort((a, b) => {
            const idA = parseInt(a.studentIndex, 10) || 0;
            const idB = parseInt(b.studentIndex, 10) || 0;
            return idA - idB;
          });

          // 批量获取报告状态
          await this.fetchReportsStatus(courseId);
          
          // 获取总分数据
          await this.fetchTotalScores(courseId);
        } else {
          console.error('获取学生数据失败：', studentResponse.message);
        }
      } catch (error) {
        console.error('加载数据时发生错误：', error);
        ElMessage.error('加载数据失败，请刷新页面重试');
      } finally {
        this.isLoading = false;
      }
    },
    methods: {
      async fetchTotalScores(courseId) {
        try {
          const response = await axios.get(`${API_BASE_URL}/courses/${courseId}/scores/total`, {
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          });

          if (response.data.success) {
            const totalScores = response.data.data;
            this.students.forEach(student => {
              const scoreData = totalScores.find(score => score.studentIndex === student.studentIndex);
              if (scoreData) {
                this.updateTotalScore(student);
              }
            });
          }
        } catch (error) {
          console.error('获取总分数据失败：', error);
        }
      },
      async fetchReportsStatus(courseId) {
        const token = localStorage.getItem('token');
        if (!token) {
          console.error('未找到token，请先登录');
          return;
        }

        // 创建批处理请求组
        const batchSize = 5; // 每批处理的学生数量
        const studentBatches = [];
        for (let i = 0; i < this.students.length; i += batchSize) {
          studentBatches.push(this.students.slice(i, i + batchSize));
        }

        // 按批次处理学生数据
        for (const batch of studentBatches) {
          const batchPromises = batch.map(student => 
            this.fetchStudentReports(student, courseId, token)
          );
          await Promise.all(batchPromises);
        }
      },
      async fetchStudentReports(student, courseId, token) {
        const reportPromises = this.experiments.map(async experiment => {
          try {
            const reportIdResponse = await fetchReportId(student.studentIndex, courseId, experiment.experimentId);
            if (!reportIdResponse.success || !reportIdResponse.reportId) {
              student.reports[experiment.experimentId] = { status: 'none' };
              return;
            }

            const statusResponse = await fetchReportStatus(reportIdResponse.reportId);
            if (!statusResponse.success) {
              student.reports[experiment.experimentId] = { status: 'none' };
              return;
            }

            student.reports[experiment.experimentId] = { status: statusResponse.status };
            
            if (statusResponse.status === 'graded') {
              try {
                const scoreResponse = await axios.get(
                  `${API_BASE_URL}/reports/${reportIdResponse.reportId}/score`,
                  {
                    headers: { 'Authorization': `Bearer ${token}` }
                  }
                );
                if (scoreResponse.data.success) {
                  student.reports[experiment.experimentId].score = scoreResponse.data.data;
                }
              } catch (error) {
                console.error('获取分数失败：', error);
              }
            }
          } catch (error) {
            console.error('获取报告状态失败：', error);
            student.reports[experiment.experimentId] = { status: 'none' };
          }
        });

        await Promise.all(reportPromises);
        this.updateTotalScore(student);
      },
      async viewReport(studentIndex, experimentId) {
        const courseId = this.accountStore.course.courseId;
        if (!courseId) return;

        try {
          const reportIdResponse = await fetchReportId(studentIndex, courseId, experimentId);
          if (!reportIdResponse.success || !reportIdResponse.reportId) {
            ElMessage.error(`未找到报告ID: ${reportIdResponse.message}`);
            return;
          }

          const statusResponse = await fetchReportStatus(reportIdResponse.reportId);
          if (!statusResponse.success) {
            ElMessage.error(`无法获取报告状态: ${statusResponse.message}`);
            return;
          }

          if (statusResponse.status === 'submitted' || statusResponse.status === 'graded') {
            const reportResponse = await fetchReport(studentIndex, courseId, experimentId);
            if (reportResponse.success) {
              const link = document.createElement('a');
              link.href = reportResponse.reportUrl;
              const student = this.students.find(s => s.studentIndex === studentIndex);
              const experiment = this.experiments.find(e => e.experimentId === experimentId);
              const fileName = reportResponse.filename || `实验报告_${student?.studentName || studentIndex}_${experiment?.experimentName || experimentId}.pdf`;
              link.download = fileName;
              document.body.appendChild(link);
              link.click();
              document.body.removeChild(link);
              window.URL.revokeObjectURL(reportResponse.reportUrl);
            } else {
              ElMessage.error('获取报告失败：' + reportResponse.message);
            }
          } else {
            ElMessage.warning('报告尚未提交或不可用');
          }
        } catch (error) {
          console.error('查看报告时发生错误：', error);
          ElMessage.error('查看报告失败，请稍后重试');
        }
      },

      async startReScore(studentIndex, experimentId) {
        const student = this.students.find(s => s.studentIndex === studentIndex);
        if (student?.reports[experimentId]?.status === 'graded') {
          student.reports[experimentId].isEditing = true;
          this.scores[`${studentIndex}-${experimentId}`] = student.reports[experimentId].score;
          ElMessage.success('可以开始重新评分');
        } else {
          ElMessage.warning('该报告尚未评分，无法重新评分');
        }
      },

      async submitScore(studentIndex, experimentId) {
        const courseId = this.accountStore.course.courseId;
        const score = this.scores[`${studentIndex}-${experimentId}`];

        if (!courseId || score === undefined || score < 0 || score > 100) {
          ElMessage.error('请输入有效的分数（0-100）');
          return;
        }

        try {
          const token = localStorage.getItem('token');
          const reportIdResponse = await fetchReportId(studentIndex, courseId, experimentId);
          if (!reportIdResponse.success || !reportIdResponse.reportId) {
            ElMessage.error(`未找到报告ID: ${reportIdResponse.message}`);
            return;
          }

          const statusResponse = await fetchReportStatus(reportIdResponse.reportId);
          if (!statusResponse.success || !['submitted', 'graded'].includes(statusResponse.status)) {
            ElMessage.warning('报告状态不允许评分');
            return;
          }

          const scoreValue = parseInt(score, 10);
          const response = await axios.put(
            `${API_BASE_URL}/reports/${reportIdResponse.reportId}/score`,
            scoreValue,
            {
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
              }
            });

          if (response.data.success) {
            const student = this.students.find(s => s.studentIndex === studentIndex);
            if (student?.reports[experimentId]) {
              student.reports[experimentId].score = scoreValue;
              student.reports[experimentId].isEditing = false;
              student.reports[experimentId].status = 'graded';
              this.updateTotalScore(student);
            }
            this.scores[`${studentIndex}-${experimentId}`] = '';
            ElMessage.success('评分提交成功');
          } else {
            ElMessage.error(`分数提交失败: ${response.data.msg}`);
          }
        } catch (error) {
          console.error('提交评分失败：', error);
          ElMessage.error('提交评分失败，请稍后重试');
        }
      },

      updateTotalScore(student) {
        let total = 0;
        const totalExperiments = this.experiments.length;
        
        // 遍历所有实验
        this.experiments.forEach(experiment => {
          const report = student.reports[experiment.experimentId];
          if (report && report.status === 'graded' && report.score) {
            total += report.score;
          } else {
            // 如果没有报告或未评分，计为0分
            total += 0;
          }
        });
        
        // 计算平均分（包括所有实验，未提交的计为0分）
        student.totalScore = Number((total / totalExperiments).toFixed(2));
      },

      async getTranscript() {
        const courseId = this.accountStore.course.courseId;
        if (!courseId) {
          ElMessage.error('缺少课程ID');
          return;
        }

        try {
          const token = localStorage.getItem('token');
          const response = await fetch(
            `${API_BASE_URL}/courses/${courseId}/scores/transcript`,
            {
              headers: {
                'Authorization': `Bearer ${token}`,
                'accept': 'application/json'
              }
            }
          );

          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }

          // 从响应头中获取文件名
          const contentDisposition = response.headers.get('content-disposition');
          let filename = 'student_scores.xlsx';
          if (contentDisposition) {
            const filenameMatch = contentDisposition.match(/filename="(.+)"/);
            if (filenameMatch) {
              filename = filenameMatch[1];
            }
          }

          // 检查响应类型
          const contentType = response.headers.get('content-type');
          if (!contentType || !contentType.includes('application/octet-stream')) {
            const text = await response.text();
            try {
              const errorData = JSON.parse(text);
              ElMessage.error(`获取成绩单失败: ${errorData.message || '未知错误'}`);
            } catch (e) {
              ElMessage.error('获取成绩单失败: 响应格式错误');
            }
            return;
          }

          // 获取完整的响应数据
          const arrayBuffer = await response.arrayBuffer();

          // 检查文件大小是否合理（不为0且不超过10MB）
          if (arrayBuffer.byteLength === 0) {
            ElMessage.error('获取的成绩单文件为空');
            return;
          }

          if (arrayBuffer.byteLength > 10 * 1024 * 1024) { // 10MB
            ElMessage.error('文件大小超过限制');
            return;
          }

          // 创建下载链接
          const uint8Array = new Uint8Array(arrayBuffer);
          
          // 检查文件头
          const fileHeader = String.fromCharCode.apply(null, uint8Array.slice(0, 2));
          if (fileHeader !== 'PK') {
            ElMessage.error('文件格式不正确');
            return;
          }

          const blob = new Blob([uint8Array], {
            type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
          });
          
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', filename);
          
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          window.URL.revokeObjectURL(url);
          
          ElMessage.success('成绩单下载成功');
        } catch (error) {
          console.error('获取成绩单失败：', error);
          if (error.response) {
            if (error.response.status === 401) {
              ElMessage.error('未授权，请重新登录');
            } else if (error.response.status === 404) {
              ElMessage.error('未找到成绩单数据');
            } else {
              ElMessage.error(`获取成绩单失败: ${error.response.status} ${error.response.statusText}`);
            }
          } else if (error.request) {
            ElMessage.error('服务器无响应，请检查网络连接');
          } else {
            ElMessage.error('请求配置错误');
          }
        }
      },

      previewTranscript() {
        this.previewDialogVisible = true;
        this.isTranscriptConfirmed = false;
      },

      handleClosePreview() {
        this.previewDialogVisible = false;
        this.isTranscriptConfirmed = false;
      },

      confirmTranscript() {
        this.isTranscriptConfirmed = true;
        this.previewDialogVisible = false;
        ElMessage.success('已确认成绩单，可以下载了');
      },
    },
  };
</script>

<style scoped>
  .el-table {
    border-radius: 8px;
    border: 1px solid #ddd;
  }

  .el-table th {
    background-color: #f4f4f4;
    color: #555;
  }

  .el-table .el-table-column__header {
    font-weight: bold;
  }

  .el-table .el-table-column__cell {
    text-align: center;
  }

  /* 添加对话框样式 */
  :deep(.el-dialog) {
    border-radius: 8px;
  }

  :deep(.el-dialog__header) {
    margin-right: 0;
    padding: 20px;
    border-bottom: 1px solid #eee;
  }

  :deep(.el-dialog__body) {
    padding: 20px;
  }

  .dialog-footer {
    padding: 20px 0 0;
    text-align: right;
  }
</style>
