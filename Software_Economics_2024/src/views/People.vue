<template>
  <div class="people-module">
    <!-- Course Header -->
    <div class="course-name">
      {{ accountStore.course.courseName || '软件工程管理与经济' }}
    </div>

    <!-- Top Navigation -->
    <el-row class="navigation">

    </el-row>

    <!-- Search Bar and Filters -->
    <el-row class="search-bar">
      <el-col :span="6" class="relative-input">
        <el-input v-model="searchQuery" placeholder="搜索人员" clearable @input="filterResults" />
      </el-col>
      <!-- <el-col :span="6" class="relative-select">
        <el-select v-model="selectedRole" placeholder="所有身份" @change="filterResults" clearable>
          <el-option label="所有身份" value=""></el-option>
          <el-option label="学生" value="student"></el-option>
          <el-option label="责任老师" value="responsible_teacher"></el-option>
        </el-select>
      </el-col> -->
      <el-col :span="18" class="button-group">
        <el-button type="primary" @click="showAddStudentDialog">添加学生</el-button>
        <el-button type="danger" @click="showDeleteStudentDialog">删除学生</el-button>
      </el-col>
    </el-row>

    <!-- Personnel Table -->
    <el-table :data="filteredResults" stripe style="width: 100%; margin: 20px auto; font-size: 18px;" max-height="75vh">
      <el-table-column prop="studentIndex" label="学生索引" width="150" align="center" />
      <el-table-column prop="studentName" label="学生姓名" align="center" />
      <el-table-column prop="studentId" label="学生学号" align="center" />
      <el-table-column prop="school" label="学校" align="center" />
      <el-table-column prop="email" label="学生邮箱" align="center" width="300" />

      <!-- 自定义 status 列的显示内容 -->
      <el-table-column label="学生状态" align="center">
        <template #default="{ row }">
          <span>{{ row.status === 0 ? '未激活' : '已激活' }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="role" label="角色" align="center" />
    </el-table>

    <!-- 添加学生对话框 -->
    <el-dialog
      title="添加学生"
      v-model="addStudentDialogVisible"
      width="50%">
      <el-form :model="addStudentForm" label-width="120px">
        <el-form-item label="选择学生">
          <el-select
            v-model="addStudentForm.studentIds"
            multiple
            placeholder="请选择学生"
            style="width: 100%;">
            <div class="select-search-box">
              <el-input
                v-model="addStudentSearchQuery"
                placeholder="搜索学生姓名或学号"
                clearable
                @input="filterAvailableStudents"
                size="small"
                style="width: 100%;">
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </div>
            <el-option
              v-for="student in filteredAvailableStudents"
              :key="student.studentId"
              :label="student.studentName + ' (' + student.studentId + ')'"
              :value="student.studentId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addStudentDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addStudents" :loading="loading">添加</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 删除学生对话框 -->
    <el-dialog
      title="删除学生"
      v-model="deleteStudentDialogVisible"
      width="50%">
      <el-form :model="deleteStudentForm" label-width="120px">
        <el-form-item label="选择学生">
          <el-select
            v-model="deleteStudentForm.studentIds"
            multiple
            placeholder="请选择要删除的学生"
            style="width: 100%;">
            <div class="select-search-box">
              <el-input
                v-model="deleteStudentSearchQuery"
                placeholder="搜索学生姓名或学号"
                clearable
                @input="filterCurrentStudents"
                size="small"
                style="width: 100%;">
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </div>
            <el-option
              v-for="student in filteredCurrentStudents"
              :key="student.studentId"
              :label="student.studentName + ' (' + student.studentId + ')'"
              :value="student.studentId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteStudentDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteStudents" :loading="loading">删除</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
  import axios from "axios";
  import { useAccountStore } from '@/store/account';
  import { ElMessage } from 'element-plus';
  import { Search } from '@element-plus/icons-vue';
  import http from '@/utils/http';

  export default {
    components: {
      Search
    },
    data() {
      return {
        peopleList: [],
        filteredResults: [],
        searchQuery: "",
        selectedRole: "", // 新增角色筛选条件
        addStudentDialogVisible: false,
        deleteStudentDialogVisible: false,
        loading: false,
        searchLoading: false, // 添加搜索加载状态
        availableStudents: [], // 可添加的学生列表
        allAvailableStudents: [], // 存储所有可添加的学生，用于搜索
        addStudentSearchQuery: "", // 添加学生搜索查询
        deleteStudentSearchQuery: "", // 删除学生搜索查询
        addStudentForm: {
          studentIds: [] // 修改为数组，支持多选
        },
        deleteStudentForm: {
          studentIds: [] // 修改为数组，支持多选
        }
      };
    },
    computed: {
      selectedCourseId() {
        return this.accountStore.course.courseId;
      },
      // 过滤后的可添加学生列表
      filteredAvailableStudents() {
        if (!this.addStudentSearchQuery) {
          return this.availableStudents;
        }
        const query = this.addStudentSearchQuery.toLowerCase();
        return this.availableStudents.filter(student =>
          student.studentName.toLowerCase().includes(query) ||
          student.studentId.toLowerCase().includes(query)
        );
      },
      // 过滤后的当前课程学生列表
      filteredCurrentStudents() {
        if (!this.deleteStudentSearchQuery) {
          return this.peopleList;
        }
        const query = this.deleteStudentSearchQuery.toLowerCase();
        return this.peopleList.filter(student =>
          student.studentName.toLowerCase().includes(query) ||
          student.studentId.toLowerCase().includes(query)
        );
      }
    },
    created() {
      this.accountStore = useAccountStore();
      if (this.selectedCourseId) {
        this.fetchPeople(this.selectedCourseId);
      } else {
        console.error("未选择课程");
      }
    },

    methods: {
      async fetchPeople(courseId) {
        if (!courseId) {
          console.error("courseId 未定义");
          return;
        }
        try {
          console.log("正在获取课程ID为", courseId, "的学生数据");

          // 从localStorage获取token
          const token = localStorage.getItem('token');

          const response = await axios.get(`http://localhost:8002/api/courses/${courseId}/students`, {
            headers: {
              Authorization: token ? `Bearer ${token}` : ''
            }
          });

          console.log("后端返回数据：", response.data);

          // 检查数据格式并提取 data 字段
          if (response.data) {
            if (response.data.code === 200) {
              this.peopleList = response.data.data || []; // 提取 data 字段中的数组
              this.filteredResults = [...this.peopleList];
              console.log("成功获取学生数据：", this.peopleList);
            } else {
              console.error("后端返回错误码：", response.data.code);
              console.error("后端返回错误信息：", response.data.message || "未知错误");
              this.peopleList = [];
              this.filteredResults = [];
            }
          } else {
            console.error("后端返回数据为空");
            this.peopleList = [];
            this.filteredResults = [];
          }
        } catch (error) {
          console.error("获取人员数据失败，详细错误：", error);
          if (error.response) {
            console.error("错误响应数据：", error.response.data);
            console.error("错误状态码：", error.response.status);
          }
          this.peopleList = [];
          this.filteredResults = [];
        }
      },
      switchToAll() {
        this.searchQuery = "";
        this.selectedRole = "";
        this.filteredResults = [...this.peopleList];
      },
      filterResults() {
        this.filteredResults = this.peopleList.filter((person) => {
          const matchesQuery =
            this.searchQuery === "" ||
            (person.studentName && person.studentName.includes(this.searchQuery)) ||
            (person.studentId && person.studentId.includes(this.searchQuery));
          const matchesRole =
            this.selectedRole === "" || person.role === this.selectedRole;
          return matchesQuery && matchesRole;
        });
      },

      // 显示添加学生对话框
      async showAddStudentDialog() {
        this.addStudentDialogVisible = true;
        await this.fetchAvailableStudents();
      },

      // 显示删除学生对话框
      showDeleteStudentDialog() {
        this.deleteStudentDialogVisible = true;
      },

      // 获取可添加的学生列表
      async fetchAvailableStudents() {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get('http://localhost:8002/api/users/students', {
            headers: {
              Authorization: token ? `Bearer ${token}` : ''
            }
          });

          if (response.data.success) {
            // 过滤掉已经在课程中的学生
            const currentStudentIds = this.peopleList.map(student => student.studentId);
            this.allAvailableStudents = response.data.data.filter(student =>
              !currentStudentIds.includes(student.studentId)
            );
            this.availableStudents = [...this.allAvailableStudents];
          } else {
            ElMessage.error('获取学生列表失败');
          }
        } catch (error) {
          console.error('获取可添加学生列表失败:', error);
          ElMessage.error('获取学生列表失败');
        }
      },

      // 过滤可添加的学生
      filterAvailableStudents() {
        // 通过计算属性自动处理
      },

      // 过滤当前课程中的学生
      filterCurrentStudents() {
        // 通过计算属性自动处理
      },

      // 添加学生
      async addStudents() {
        if (!this.addStudentForm.studentIds || this.addStudentForm.studentIds.length === 0) {
          ElMessage.warning('请选择要添加的学生');
          return;
        }

        this.loading = true;
        try {
          const token = localStorage.getItem('token');

          // 创建一个Promise数组，用于并行处理多个请求
          const promises = this.addStudentForm.studentIds.map(studentId => {
            // 打印请求信息以便调试
            console.log(`准备添加学生，ID: ${studentId}，课程ID: ${this.selectedCourseId}`);

            return axios({
              method: 'post',
              url: `http://localhost:8002/api/courses/${this.selectedCourseId}/students?studentId=${studentId}`,
              headers: {
                'Authorization': token ? `Bearer ${token}` : '',
                'Content-Type': 'application/json'
              },
              data: '' // 空请求体
            });
          });

          // 等待所有请求完成
          const results = await Promise.all(promises);

          // 打印响应信息以便调试
          console.log('添加学生响应:', results);

          // 检查是否所有请求都成功
          const allSuccess = results.every(response =>
            response.status === 200 && response.data && response.data.success === true
          );

          if (allSuccess) {
            ElMessage.success(`成功添加 ${this.addStudentForm.studentIds.length} 名学生`);
            this.addStudentDialogVisible = false;
            // 重新加载学生列表
            this.fetchPeople(this.selectedCourseId);
            // 重置表单
            this.addStudentForm.studentIds = [];
          } else {
            // 如果有失败的请求，显示错误信息
            const failedResponses = results.filter(response =>
              response.status !== 200 || !response.data || response.data.success !== true
            );

            const errorMessages = failedResponses.map(response => {
              if (response.data && response.data.msg) {
                return response.data.msg;
              } else if (response.status === 405) {
                return '方法不允许 (405)，可能缺少权限或API发生变更';
              } else if (response.status === 400) {
                return '请求格式错误 (400)，请检查参数';
              } else if (response.status === 500) {
                return '服务器内部错误 (500)，请稍后重试';
              } else {
                return `状态码: ${response.status || '未知'}`;
              }
            });

            ElMessage.error(`部分学生添加失败: ${errorMessages.join(', ')}`);
          }
        } catch (error) {
          console.error('添加学生失败:', error);

          // 提供更详细的错误信息
          let errorMsg = '未知错误';
          if (error.response) {
            errorMsg = `服务器返回错误 (${error.response.status}): ${error.response.data?.msg || '未知错误'}`;
            console.log('错误响应数据:', error.response.data);
          } else if (error.request) {
            errorMsg = '无法连接到服务器，请检查网络连接';
          } else {
            errorMsg = error.message;
          }

          ElMessage.error('添加学生失败: ' + errorMsg);
        } finally {
          this.loading = false;
        }
      },

      // 删除学生
      async deleteStudents() {
        if (!this.deleteStudentForm.studentIds || this.deleteStudentForm.studentIds.length === 0) {
          ElMessage.warning('请选择要删除的学生');
          return;
        }

        this.loading = true;
        try {
          const token = localStorage.getItem('token');

          // 创建一个Promise数组，用于并行处理多个请求
          const promises = this.deleteStudentForm.studentIds.map(studentId => {
            // 打印请求信息以便调试
            console.log(`准备删除学生，ID: ${studentId}，课程ID: ${this.selectedCourseId}`);

            return axios({
              method: 'delete',
              url: `http://localhost:8002/api/courses/${this.selectedCourseId}/students?studentId=${studentId}`,
              headers: {
                'Authorization': token ? `Bearer ${token}` : '',
                'Content-Type': 'application/json'
              }
            });
          });

          // 等待所有请求完成
          const results = await Promise.all(promises);

          // 打印响应信息以便调试
          console.log('删除学生响应:', results);

          // 检查是否所有请求都成功
          const allSuccess = results.every(response =>
            response.status === 200 && response.data && response.data.code === 200
          );

          if (allSuccess) {
            ElMessage.success(`成功删除 ${this.deleteStudentForm.studentIds.length} 名学生`);
            this.deleteStudentDialogVisible = false;
            // 重新加载学生列表
            this.fetchPeople(this.selectedCourseId);
            // 重置表单
            this.deleteStudentForm.studentIds = [];
          } else {
            // 如果有失败的请求，显示错误信息
            const failedResponses = results.filter(response =>
              response.status !== 200 || !response.data || response.data.code !== 200
            );

            const errorMessages = failedResponses.map(response => {
              if (response.data && response.data.msg) {
                return response.data.msg;
              } else if (response.status === 405) {
                return '方法不允许 (405)，可能缺少权限或API发生变更';
              } else if (response.status === 400) {
                return '请求格式错误 (400)，请检查参数';
              } else {
                return `状态码: ${response.status || '未知'}`;
              }
            });

            ElMessage.error(`部分学生删除失败: ${errorMessages.join(', ')}`);
          }
        } catch (error) {
          console.error('删除学生失败:', error);

          // 提供更详细的错误信息
          let errorMsg = '未知错误';
          if (error.response) {
            errorMsg = `服务器返回错误 (${error.response.status}): ${error.response.data?.msg || '未知错误'}`;
            console.log('错误响应数据:', error.response.data);
          } else if (error.request) {
            errorMsg = '无法连接到服务器，请检查网络连接';
          } else {
            errorMsg = error.message;
          }

          ElMessage.error('删除学生失败: ' + errorMsg);
        } finally {
          this.loading = false;
        }
      }
    },
  };
</script>

<style>
  .el-table .el-table__cell {
    padding: 12px 0;
    /* 设置行之间的间距 */
  }

  .people-module {
    padding: 20px;
  }

  .navigation {
    margin-bottom: 20px;
  }

  .search-bar {
    margin-bottom: 20px;
    display: flex;
    display: flex;
    align-items: center;
    gap: 20px;
  }

  .relative-input {
    flex: 2;
  }

  .relative-select {
    flex: 1;
  }

  .course-name {
    font-size: 24px;
    font-weight: bold;
    margin-left: 5px;
    margin-bottom: -10px;
  }

  .button-group {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
  }

  .select-search-box {
    padding: 8px;
    border-bottom: 1px solid #EBEEF5;
  }
</style>