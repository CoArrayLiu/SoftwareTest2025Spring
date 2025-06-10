<template>
  <div>
    <el-container>
      <el-header style="font-size: 2em;font-weight: bold;margin-top: 10px;">
        主页
        <span v-if="selectedCourse">
          <span>/ {{ selectedCourse.courseName }} ({{ selectedCourse.courseId }})</span>
          <el-button @click="exitCourse"
                     style="float: right;font-size: 20px;margin-top: 10px;margin-right: 10px;">退出课程</el-button>
        </span>
      </el-header>
      <el-main>
        <!-- 显示课程列表 -->
        <div v-if="isSuccess" class="course-list">
          <div style="display: flex; align-items: center; margin-bottom: 30px;">
            <h2>{{ isResponsibleTeacher ? '所有课程' : '已发布的课程' }} ({{ courses.length }}) </h2>
            <!-- 仅责任教师显示创建课程按钮 -->
            <el-button v-if="isResponsibleTeacher" type="primary" @click="dialogVisible = true" style="margin-left: 30px; margin-bottom: 5px;">
              <el-icon><Plus /></el-icon>
              创建课程
            </el-button>
          </div>
          <div class="course-cards">
            <div v-for="course in courses" :key="course.courseId" class="course-card" @click="handleCardClick(course)">
              <div class="course-card-header" :style="{ backgroundColor: getCardColor(course.courseId) }">
                <!-- 上半部分的背景颜色 -->
              </div>
              <div class="course-card-body">
                <!-- 下半部分显示课程信息 -->
                <div class="course-info">
                  <span :style="{ color: getCardColor(course.courseId) }">
                    {{ course.courseName }}
                  </span>
                  <span :style="{ color: getCardColor(course.courseId) }" style="font-size: 14px;">
                    {{ course.courseId }}
                  </span>
                </div>
                <div class="course-meta">
                  <p :style="{ color: 'rgb(65, 119, 159)' }">
                    {{ course.semester }} {{ course.year }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 显示错误消息 -->
        <div v-else>
          <p>{{ responseMessage }}</p>
        </div>
        
        <!-- 创建课程的对话框 -->
        <el-dialog
          title="创建课程"
          v-model="dialogVisible"
          width="50%">
          <el-form :model="newCourse" :rules="formRules" label-width="120px" ref="courseForm">
            <el-form-item label="课程名称" prop="courseName">
              <el-input v-model="newCourse.courseName" placeholder="请输入课程名称"></el-input>
            </el-form-item>
            <el-form-item label="课程号" prop="courseId">
              <el-input v-model="newCourse.courseId" placeholder="请输入课程号"></el-input>
            </el-form-item>
            <el-form-item label="学期" prop="semester">
              <el-select v-model="newCourse.semester" placeholder="请选择学期">
                <el-option label="spring" value="spring"></el-option>
                <el-option label="fall" value="fall"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年份" prop="year">
              <el-input-number v-model="newCourse.year" :min="2000" :max="2099" placeholder="请输入年份"></el-input-number>
            </el-form-item>
            <el-form-item label="责任教师">
              <span>{{ currentUserName }}</span>
            </el-form-item>
            <el-form-item label="任课教师" prop="teacherList">
              <el-select v-model="newCourse.teacherList" multiple placeholder="请选择任课教师" style="width: 100%;">
                <el-option
                  v-for="teacher in teachers"
                  :key="teacher.teacherId"
                  :label="teacher.name + ' (' + teacher.teacherId + ')'"
                  :value="teacher.teacherId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="导入学生" prop="studentFile">
              <el-upload
                class="upload-demo"
                action="#"
                :auto-upload="false"
                :on-change="handleFileChange"
                :limit="1"
                accept=".xlsx,.xls">
                <template #trigger>
                  <el-button type="primary">选择Excel文件</el-button>
                </template>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传包含学生信息的Excel文件，文件需包含学号、姓名、邮箱和学校字段
                  </div>
                </template>
              </el-upload>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitNewCourse" :loading="loading">创建</el-button>
            </span>
          </template>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import { fetchCourses, fetchAllCourses, createCourse, getTeachers, getStudents } from '@/api/get_teacher_courses.js';  // 导入封装的API方法
  import { inject } from 'vue';
  import { useAccountStore } from '@/store/account';
  import { ElMessage } from 'element-plus';
  import { Plus } from '@element-plus/icons-vue';
  import axios from 'axios';

  export default {
    name: 'Home',
    components: {
      Plus
    },
    data() {
      return {
        courses: [],            // 存储课程数据
        responseMessage: '',    // 存储消息
        isSuccess: false,       // 请求是否成功
        selectedCourse: null,   // 当前选中的课程
        dialogVisible: false,   // 控制对话框显示
        loading: false,         // 提交表单时的加载状态
        teachers: [],           // 教师列表
        students: [],           // 学生列表
        currentUserName: '',    // 当前用户名
        studentFile: null,      // 学生Excel文件
        formRules: {            // 表单验证规则
          courseName: [
            { required: true, message: '请输入课程名称', trigger: 'blur' },
            { required: true, message: '请输入课程名称', trigger: 'change' }
          ],
          courseId: [
            { required: true, message: '请输入课程号', trigger: 'blur' },
            { required: true, message: '请输入课程号', trigger: 'change' }
          ],
          semester: [
            { required: true, message: '请选择学期', trigger: 'change' }
          ],
          year: [
            { required: true, message: '请输入年份', trigger: 'blur' },
            { required: true, message: '请输入年份', trigger: 'change' }
          ],
          teacherList: [
            { required: true, message: '请选择任课教师', trigger: 'change' },
            { type: 'array', min: 1, message: '请至少选择一名教师', trigger: 'change' }
          ]
        },
        newCourse: {            // 新课程数据
          courseName: '',
          courseId: '',
          semester: '',
          year: new Date().getFullYear(),
          teacherList: [],
          studentList: []
        }
      };
    },
    computed: {
      // 判断当前用户是否为责任教师
      isResponsibleTeacher() {
        return this.accountStore.role && this.accountStore.role.toLowerCase() === 'responsible_teacher';
      }
    },
    setup() {
      // 注入从父组件提供的 isCoursesLoaded 状态
      const isCoursesLoaded = inject('isCoursesLoaded');
      // 获取 Pinia store
      const accountStore = useAccountStore();
      return {
        isCoursesLoaded, accountStore
      };
    },
    mounted() {
      // 组件挂载后自动调用加载课程的方法，根据角色选择不同的API
      this.loadCoursesBasedOnRole();
      
      // 加载教师和学生列表
      this.loadTeachers();
      this.loadStudents();
      
      // 获取当前用户信息
      this.getCurrentUser();
    },
    methods: {
      // 测试表单字段
      testFormFields() {
        console.log('==== 测试表单字段 ====');
        console.log('课程名称:', this.newCourse.courseName, typeof this.newCourse.courseName);
        console.log('课程号:', this.newCourse.courseId, typeof this.newCourse.courseId);
        console.log('学期:', this.newCourse.semester, typeof this.newCourse.semester);
        console.log('年份:', this.newCourse.year, typeof this.newCourse.year);
        console.log('教师列表:', this.newCourse.teacherList);
        console.log('文件:', this.studentFile);
        
        // 测试表单验证
        this.$refs.courseForm.validate((valid, invalidFields) => {
          console.log('表单验证结果:', valid);
          if (!valid) {
            console.log('验证失败的字段:', invalidFields);
            for (let field in invalidFields) {
              console.log(`${field}验证失败:`, invalidFields[field]);
            }
          }
        });
      },
      
      // 获取当前用户信息
      async getCurrentUser() {
        try {
          const token = localStorage.getItem('token');
          const response = await axios.get('http://localhost:8002/api/users/me', {
            headers: {
              Authorization: token ? `Bearer ${token}` : ''
            }
          });
          
          if (response.data.success) {
            this.currentUserName = response.data.data.name;
          } else {
            console.error('获取用户信息失败：', response.data.message);
          }
        } catch (error) {
          console.error('获取用户信息时发生错误：', error);
        }
      },
      // 根据角色选择不同的课程加载方法
      async loadCoursesBasedOnRole() {
        if (this.isResponsibleTeacher) {
          // 责任教师加载所有课程
          await this.loadAllCourses();
        } else {
          // 普通教师只加载自己的课程
          await this.loadOwnCourses();
        }
      },
      
      // 加载所有课程（责任教师使用）
      async loadAllCourses() {
        const result = await fetchAllCourses();
        this.handleCourseLoadResult(result);
      },
      
      // 加载自己的课程（普通教师使用）
      async loadOwnCourses() {
        const result = await fetchCourses();
        this.handleCourseLoadResult(result);
      },
      
      // 处理课程加载结果
      handleCourseLoadResult(result) {
        if (result.success) {
          this.courses = result.courses;
          this.responseMessage = "获取课程列表成功！";
          this.isSuccess = true;
        } else {
          this.responseMessage = result.message;
          this.isSuccess = false;
          this.isCoursesLoaded = false;
          this.accountStore.clearCourseId(); // 请求失败时清除 courseId
        }
      },
      
      async loadTeachers() {
        const result = await getTeachers();
        if (result.success) {
          this.teachers = result.teachers;
        } else {
          ElMessage.error(result.message || '获取教师列表失败');
        }
      },
      
      async loadStudents() {
        const result = await getStudents();
        if (result.success) {
          this.students = result.students;
        } else {
          ElMessage.error(result.message || '获取学生列表失败');
        }
      },
      
      handleCardClick(course) {
        console.log('选择课程前状态:', {
          isCoursesLoaded: this.isCoursesLoaded,
          oldCourseId: this.accountStore.course.courseId
        });
        
        this.selectedCourse = course;  // 选择课程
        
        // 确保课程ID是数字类型
        const courseId = Number(course.courseId);
        
        // 存储选中的courseId和courseName
        this.accountStore.setCourseId(courseId, course.courseName); 
        
        // 设置课程已加载标志
        this.isCoursesLoaded = true;
        
        console.log('选择课程后状态:', {
          selectedCourse: this.selectedCourse,
          isCoursesLoaded: this.isCoursesLoaded,
          newCourseId: this.accountStore.course.courseId,
          courseName: this.accountStore.course.courseName
        });
        
        // 这里可以实现点击卡片后的操作，比如跳转到课程详情页等
        console.log('Course clicked:', course);
      },
      
      exitCourse() {
        this.selectedCourse = null;  // 清空选中的课程
        this.isCoursesLoaded = false;  // 设置课程未加载
        this.accountStore.clearCourseId(); // 清除 Pinia 中的 courseId
      },
      
      getCardColor(courseId) {
        // 根据课程ID动态选择卡片颜色，你可以根据需要做修改
        const colors = [
          '#FF6347', '#3CB371', '#1E90FF', '#FFD700', '#8A2BE2'
        ];
        return colors[courseId % colors.length] || '#D3D3D3';
      },
      
      // 处理文件上传
      handleFileChange(file) {
        this.studentFile = file.raw;
        console.log('选择的文件:', file.name);
      },
      
      async submitNewCourse() {
        console.log('提交表单 - 当前课程名称:', this.newCourse.courseName);
        console.log('提交表单 - 表单数据:', JSON.stringify(this.newCourse));
        
        this.$refs.courseForm.validate(async (valid, invalidFields) => {
          console.log('表单验证结果:', valid);
          if (invalidFields) {
            console.log('验证失败的字段:', JSON.stringify(invalidFields));
          }
          
          if (valid) {
            if (!this.studentFile) {
              ElMessage.warning('请上传学生Excel文件');
              return;
            }
            
            this.loading = true;
            try {
              // 创建FormData对象
              const formData = new FormData();
              
              // 添加Excel文件
              formData.append('file', this.studentFile);
              
              // 添加课程信息 - 注意这里不是JSON.stringify，而是直接添加ModelAttribute的字段
              formData.append('courseId', parseInt(this.newCourse.courseId));
              formData.append('courseName', this.newCourse.courseName);
              formData.append('semester', this.newCourse.semester);
              formData.append('year', parseInt(this.newCourse.year));
              
              // 添加教师列表
              this.newCourse.teacherList.forEach(teacherId => {
                formData.append('teacherList', parseInt(teacherId));
              });
              
              // 输出FormData的内容用于调试
              console.log('FormData内容:');
              for (let pair of formData.entries()) {
                console.log(pair[0] + ': ' + pair[1]);
              }
              
              // 获取token
              const token = localStorage.getItem('token');
              
              // 发送请求
              const response = await axios.post('http://localhost:8002/api/courses', formData, {
                headers: {
                  'Authorization': token ? `Bearer ${token}` : '',
                  'Content-Type': 'multipart/form-data'
                }
              });
              
              console.log('服务器响应:', response.data);
              
              if (response.data.success) {
                ElMessage.success('创建课程成功');
                this.dialogVisible = false;
                // 重置表单
                this.resetForm();
                // 重新加载课程列表
                this.loadCoursesBasedOnRole();
              } else {
                ElMessage.error(response.data.msg || '创建课程失败');
              }
            } catch (error) {
              console.error('创建课程错误:', error);
              ElMessage.error('创建课程时发生错误: ' + (error.response?.data?.msg || error.message));
            } finally {
              this.loading = false;
            }
          }
        });
      },
      
      resetForm() {
        this.newCourse = {
          courseName: '',
          courseId: '',
          semester: '',
          year: new Date().getFullYear(),
          teacherList: [],
          studentList: []
        };
        this.studentFile = null;
      }
    }
  };
</script>

<style scoped>
  .course-list {
    margin-top: 20px;
    padding-left: 50px;
  }

  .course-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }

  .course-card {
    width: 250px;
    height: 250px;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    cursor: pointer;
    overflow: hidden;
    transition: transform 0.3s;
  }

  .course-card:hover {
    transform: scale(1.05);
  }

  .course-card-header {
    height: 50%;
    background-color: inherit;
  }

  .course-card-body {
    height: 50%;
    background-color: white;
    padding: 10px;
    text-align: center;
  }

  .course-info {
    margin-top: 15px;
    margin-bottom: 15px;
  }

  .course-info span {
    margin: 0 2px;
    font-size: 16px;
    font-weight: bold;
  }

  .course-meta p {
    margin: 0 5px;
    font-size: 16px;
    font-weight: bold;
    text-align: left;
  }

  .course-card-body p {
    color: rgb(65, 119, 159);
  }

  .selected-course-info {
    font-size: 18px;
    font-weight: bold;
    color: #333;
  }
</style>
