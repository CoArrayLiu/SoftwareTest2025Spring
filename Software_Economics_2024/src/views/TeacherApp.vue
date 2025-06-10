<template>
  <div id="app">
    <el-container class="main-container">
      <!-- 左侧导航栏 -->
      <el-aside width="90px">
        <Sidebar />
      </el-aside>
      <!-- 主内容区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import Sidebar from '../components/Sidebar.vue'
  import { ref, provide, onMounted, watchEffect } from 'vue'
  import { useAccountStore } from '@/store/account'

  export default {
    name: 'TeacherApp',
    components: {
      Sidebar
    },
    setup() {
      const accountStore = useAccountStore()
      const isCoursesLoaded = ref(false)
      
      // 初始化时检查课程ID
      onMounted(() => {
        console.log("TeacherApp初始化检查:", {
          课程ID: accountStore.course?.courseId,
          课程名称: accountStore.course?.courseName,
          用户角色: accountStore.role
        });
        
        if (accountStore.course && accountStore.course.courseId && accountStore.course.courseId !== 0) {
          console.log("TeacherApp初始化设置isCoursesLoaded为true");
          isCoursesLoaded.value = true;
        } else {
          console.log("TeacherApp初始化时课程ID无效或为0");
          isCoursesLoaded.value = false;
        }
      })
      
      // 监听课程ID变化
      watchEffect(() => {
        console.log("TeacherApp监听课程ID变化:", accountStore.course?.courseId);
        
        if (accountStore.course && accountStore.course.courseId && accountStore.course.courseId !== 0) {
          console.log("TeacherApp设置isCoursesLoaded为true");
          isCoursesLoaded.value = true;
        } else {
          console.log("TeacherApp监听到课程ID无效或为0");
          isCoursesLoaded.value = false;
        }
      })
      
      // 监听用户角色变化
      watchEffect(() => {
        console.log("TeacherApp监听用户角色变化:", accountStore.role);
        
        // 如果角色发生变化，重置课程加载状态
        if (accountStore.role === 'teacher') {
          // 教师角色需要选择课程
          if (accountStore.course && accountStore.course.courseId && accountStore.course.courseId !== 0) {
            isCoursesLoaded.value = true;
          } else {
            isCoursesLoaded.value = false;
          }
        } else if (accountStore.role === 'responsible_teacher') {
          // 责任教师不需要选择课程
          isCoursesLoaded.value = true;
        }
      })
      
      provide('isCoursesLoaded', isCoursesLoaded)
      return {
        isCoursesLoaded
      }
    }
  }
</script>

<style>
  .main-container {
    min-height: 100vh;
  }

  .el-aside {
    background-color: #2c3e50;
  }
</style> 