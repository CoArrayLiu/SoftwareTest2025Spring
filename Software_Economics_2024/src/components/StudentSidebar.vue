<template>
  <el-menu class="sidebar" mode="vertical" background-color="#2c3e50" text-color="#fff"
    active-text-color="rgb(3, 116, 181)" v-model="activeIndex">
    <!-- 顶部图片 -->
    <div class="sidebar-logo">
      <el-avatar :size="50" :src="accountStore.account.avatar || defaultAvatar">
        {{ !accountStore.account.avatar ? accountStore.account.name?.substring(0, 1) || 'S' : '' }}
      </el-avatar>
    </div>

    <!-- 学生端导航项，不包含教师专用链接 -->
    <el-menu-item index="1" :class="{ 'active-menu-item': activeIndex === '1' }">
      <router-link to="/start-experiment" class="router-link">主页</router-link>
    </el-menu-item>
    
    <!-- 可以在这里添加学生专用的其他菜单项 -->
  </el-menu>
</template>

<script>
import { ElMessage, ElAvatar } from 'element-plus';
import { ref, inject, watchEffect, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/store';
import defaultAvatar from '@/assets/avatar.png'; // 导入默认头像

export default {
  name: 'StudentSidebar',
  components: {
    ElAvatar
  },
  setup() {
    const router = useRouter();
    const activeIndex = ref('1');  // 默认选中第一个菜单项
    const accountStore = useAccountStore();
    
    // 检查当前用户是否为学生
    const isStudent = computed(() => {
      return accountStore.role && accountStore.role.toLowerCase() !== 'teacher';
    });

    // 监听路由变化，更新 activeIndex
    watchEffect(() => {
      const path = router.currentRoute.value.path;
      if (path === '/start-experiment' || path.includes('/exp')) {
        activeIndex.value = '1';
      }
      // 如果添加更多菜单项，可以在这里添加更多条件
    });

    return {
      activeIndex,
      isStudent,
      accountStore,
      defaultAvatar,
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
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  margin-bottom: 20px;
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

/* 选中菜单项时的样式 */
.active-menu-item {
  background-color: white !important;
  color: rgb(3, 116, 181) !important;
}
</style> 