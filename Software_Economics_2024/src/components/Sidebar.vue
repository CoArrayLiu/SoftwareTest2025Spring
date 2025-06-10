<template>
  <el-menu class="sidebar" mode="vertical" background-color="#2c3e50" text-color="#fff"
    active-text-color="rgb(3, 116, 181)" v-model="activeIndex">
    <!-- 顶部图片和用户名 -->
    <div class="sidebar-logo">
      <div class="avatar-container">
        <el-avatar :size="50" :src="accountStore.account.avatar || defaultAvatar">
          {{ !accountStore.account.avatar ? accountStore.account.name?.substring(0, 1) || 'T' : '' }}
        </el-avatar>
        <!-- 用户名称 -->
        <div class="user-name">
          {{ accountStore.account.name || '用户' }}
        </div>
      </div>
    </div>

    <!-- 导航项 -->
    <el-menu-item index="1" :class="{ 'active-menu-item': activeIndex === '1' }">
      <router-link to="/teacher" class="router-link">主页</router-link>
    </el-menu-item>
    <!-- 仅当用户角色为teacher时显示其他菜单项 -->
    <template v-if="accountStore.role && accountStore.role.toLowerCase() === 'teacher'">
      <el-menu-item index="2" :class="{ 'active-menu-item': activeIndex === '2' }">
        <router-link to="/teacher/experiment" class="router-link" :class="{ 'disabled-link': !isCoursesLoaded }"
          :style="{ pointerEvents: isCoursesLoaded ? 'auto' : 'none' }">实验</router-link>
      </el-menu-item>
      <el-menu-item index="3" :class="{ 'active-menu-item': activeIndex === '3' }">
        <router-link to="/teacher/people" class="router-link" :class="{ 'disabled-link': !isCoursesLoaded }"
          :style="{ pointerEvents: isCoursesLoaded ? 'auto' : 'none' }">人员</router-link>
      </el-menu-item>
      <el-menu-item index="4" :class="{ 'active-menu-item': activeIndex === '4' }">
        <router-link to="/teacher/rating" class="router-link" :class="{ 'disabled-link': !isCoursesLoaded }"
          :style="{ pointerEvents: isCoursesLoaded ? 'auto' : 'none' }">评分</router-link>
      </el-menu-item>
    </template>
    
    <!-- 退出登录按钮，放在底部 -->
    <div class="logout-container">
      <el-button type="primary" size="small" @click="handleLogout" class="logout-button">退出登录</el-button>
    </div>
  </el-menu>
</template>

<script>
import { ElMessage, ElAvatar, ElButton } from 'element-plus';
import { ref, inject, watchEffect, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/store';
import defaultAvatar from '@/assets/avatar.png'; // 导入默认头像

export default {
  name: 'Sidebar',
  components: {
    ElAvatar,
    ElButton
  },
  setup() {
    const isCoursesLoaded = inject('isCoursesLoaded');
    const router = useRouter();
    const activeIndex = ref('1');  // 默认选中第一个菜单项，注意 index 是字符串
    const accountStore = useAccountStore(); // 使用 account store 获取用户信息
    
    // 获取用户角色
    const userRole = computed(() => {
      return accountStore.role ? accountStore.role.toLowerCase() : '';
    });

    // 监听路由变化，更新 activeIndex
    watchEffect(() => {
      const path = router.currentRoute.value.path;
      if (path === '/teacher' || path === '/teacher/') {
        activeIndex.value = '1';
      } else if (path.includes('/teacher/experiment')) {
        activeIndex.value = '2';
      } else if (path.includes('/teacher/people')) {
        activeIndex.value = '3';
      } else if (path.includes('/teacher/rating')) {
        activeIndex.value = '4';
      }
    });

    // 监听accountStore中的course变化
    watchEffect(() => {
      // 检查课程ID
      console.log("课程ID状态变化检测:", accountStore.course?.courseId);
      
      // 如果课程ID存在且不为0，则更新课程加载状态
      if (accountStore.course && accountStore.course.courseId && accountStore.course.courseId !== 0) {
        console.log("更新isCoursesLoaded为true，当前课程ID:", accountStore.course.courseId);
        isCoursesLoaded.value = true;
      } else {
        console.log("课程ID无效或为0，isCoursesLoaded保持为:", isCoursesLoaded.value);
      }
    });

    // 路由导航拦截
    router.beforeEach((to, from, next) => {
      // 确保每次获取最新的用户角色
      const currentRole = accountStore.role ? accountStore.role.toLowerCase() : '';
      
      console.log("路由导航判断:", {
        角色: currentRole,
        课程已加载: isCoursesLoaded.value,
        目标路径: to.path,
        课程ID: accountStore.course?.courseId
      });
      
      // 对于responsible_teacher角色，如果尝试访问除主页外的教师界面，则重定向到主页
      if (currentRole === 'responsible_teacher' && to.path !== '/teacher' && to.path.startsWith('/teacher/')) {
        ElMessage.warning('您无权访问该页面');
        next('/teacher');
        return;
      }
      
      // 简化逻辑，不再检查课程加载状态，交由UI样式控制
      next();
    });
    
    // 退出登录处理函数
    const handleLogout = () => {
      // 使用store的退出登录方法
      accountStore.logout().then(() => {
        // 跳转到登录页
        router.push('/login');
        ElMessage.success('已退出登录');
      });
    };

    return {
      isCoursesLoaded,
      activeIndex,
      handleLogout,
      accountStore,
      defaultAvatar,
      userRole,
    };
  }
};
</script>

<style scoped>
/* 侧边栏容器 */
.sidebar {
  width: 90px;
  height: 100vh;
  position: fixed;
  top: 0;
  display: flex;
  flex-direction: column;
}

/* 顶部图标区域 */
.sidebar-logo {
  min-height: 120px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  margin-bottom: 10px;
}

.avatar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-name {
  margin-top: 8px;
  color: white;
  font-size: 14px;
  text-align: center;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 80px;
}

/* 菜单项样式 */
.el-menu-item {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 22px;
  font-weight: bold;
  height: 70px;
  margin: 10px 0;
}

.router-link {
  color: inherit;
  text-decoration: none;
}

.disabled-link {
  color: grey;
  cursor: not-allowed;
}

/* 选中菜单项时的样式 */
.active-menu-item {
  background-color: white !important;
  color: rgb(3, 116, 181) !important;
}

/* 退出登录按钮容器 */
.logout-container {
  margin-top: auto;
  padding: 20px 0;
  display: flex;
  justify-content: center;
}

.logout-button {
  width: 80%;
}
</style>
