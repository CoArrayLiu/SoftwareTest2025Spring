<template>
  <component
    :is="isCustomLayout ? 'router-view' : 'stepin-view'"
    system-name="软件工程经济学"
    logo-src="/TJlogo.svg"
    :class="contentClass"
    v-if="!isCustomLayout"
    v-model:show-setting="showSetting"
    v-model:theme="theme"
    :user="user"
    :navMode="navigation"
    :useTabs="useTabs"
    :themeList="themeList"
    @themeSelect="configTheme"
  >
    <template #headerActions>
      <HeaderActions @showSetting="showSetting = true" />
    </template>
    <template #pageFooter>
      <PageFooter />
    </template>
    <template #themeEditorTab>
      <a-tab-pane tab="其它" key="other">
        <Setting />
      </a-tab-pane>
    </template>
  </component>
  <router-view v-if="isCustomLayout" />
  <login-modal :unless="['/login']" />
  <a-menu-item v-for="item in user.menuList" :key="item.key" @click="item.onClick">
    <component :is="item.icon" />
    <span>{{ item.title }}</span>
  </a-menu-item>
  <div>
    <a-modal v-model:visible="visible" title="重设密码" okText="确定" cancelText="取消" @ok="updatePassword">
      <a-form :model="updateForm" :wrapperCol="{ span: 24 }" @finish="updatePassword" class="activate-form">
        <a-form-item :required="true" name="email">
          <a-input v-model:value="updateForm.email" placeholder="请输入用户名或邮箱" class="login-input" />
        </a-form-item>
        <a-form-item :required="true" name="oldPassword">
          <a-input v-model:value="updateForm.oldPassword" placeholder="请输入旧密码" class="login-input" type="password" />
        </a-form-item>
        <a-form-item :required="true" name="newPassword">
          <a-input v-model:value="updateForm.newPassword" placeholder="请输入新密码" class="login-input" type="password" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
  import { reactive, ref, computed, watch } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import { useAccountStore, useMenuStore, useSettingStore, storeToRefs } from '@/store';
  import { useAuthStore } from '@/plugins/auth-plugin';
  import HeaderActions from './components/layout/HeaderActions.vue';
  import PageFooter from '@/components/layout/PageFooter.vue';
  import Setting from './components/setting/Setting.vue';

  import http from '@/utils/http';
  import avatar from '@/assets/avatar.png';
  import { configTheme, themeList } from '@/theme';
  import { Menu, MenuItem, message } from 'ant-design-vue';
  import { SettingOutlined, LogoutOutlined, UploadOutlined } from '@ant-design/icons-vue';
  const visible = ref(false);

  const showModal = () => {
    visible.value = true;
  };

  const accountStore = useAccountStore();
  const { logout, account, profile } = useAccountStore();
  const { setAuthorities } = useAuthStore();
  const route = useRoute();
  const router = useRouter();
  const fileInput = ref(null);
  const selectedKeys = ref([]);
  const showSetting = ref(false);
  const updateForm = ref({
    email: '',
    oldPassword: '',
    newPassword: '',
  });
  // 异步接口调用
  const update = async (oldPassword, newPassword) => {
    return http
      .request<any, { success: boolean; msg: string }>(
        'http://localhost:8002/api/users/me/password',
        'put_json',
        { oldPassword, newPassword }
      )
      .then((response) => {
        console.log(response);
        return response;
      });
  };

  // 更新密码逻辑
  const updatePassword = async () => {
    if (
      updateForm.value.email === '' ||
      updateForm.value.oldPassword === '' ||
      updateForm.value.newPassword === ''
    ) {
      message.error('请输入完整信息');
      return;
    }

    try {
      console.log(updateForm.value.oldPassword);
      console.log(updateForm.value.newPassword);
      const res = await update(updateForm.value.oldPassword, updateForm.value.newPassword);
      if (res.success) {
        message.success('修改成功');
        visible.value = false;

        // 重置表单数据
        updateForm.value.email = '';
        updateForm.value.oldPassword = '';
        updateForm.value.newPassword = '';
      } else {
        message.error(res.msg);
      }
    } catch (error) {
      console.error('更新密码失败:', error);
      message.error('网络请求失败，请稍后重试');
    }
  };

  const form = reactive({
    email: '',
    password: '',
  });
  // 用户对象
  const user = reactive({
    name: '',
    avatar: avatar,
    menuList: [
      { title: '设置', key: 'setting', icon: 'SettingOutlined', onClick: () => (showSetting.value = true) },
      // { type: 'divider' },
      // {
      //   title: '修改头像',
      //   key: 'changeAvatar',
      //   icon: 'UploadOutlined',
      //   onClick: () => fileInput.value?.click(),
      // },
      { type: 'divider' },
      {
        title: '修改密码',
        key: 'update',
        icon: 'KeyOutlined',
        onClick: () => showModal(),
      },
      { type: 'divider' },
      {
        title: '退出登录',
        key: 'logout',
        icon: 'LogoutOutlined',
        onClick: () => logout().then(() => router.push('/login')),
      },
    ],
  });

  // 根据路由 meta 信息判断是否使用自定义布局
  const isCustomLayout = computed(() => route.meta.layout === false);

  // 从 store 获取设置
  const { navigation, useTabs, theme, contentClass } = storeToRefs(useSettingStore());

  // 获取用户信息的方法
  const fetchUserProfile = async () => {
    try {
      // 如果当前是根路径，并且没有token，则直接跳转到登录页面
      if (route.path === '/' && !localStorage.getItem('token')) {
        router.push('/login');
        return;
      }
      
      const response = await profile();
      const { name, avatar, role } = response;
      user.name = name;
      user.avatar = avatar;
      
      // 记录用户角色，便于调试
      console.log("当前用户角色:", role);
      
      // 根据用户角色重定向
      const currentPath = route.path;
      
      // 如果是teacher角色
      if (role && role.toLowerCase() === 'teacher') {
        // 如果不是教师界面相关路径，且不是登录页，则跳转到教师主页
        const teacherPaths = ['/teacher'];
        const isTeacherPath = teacherPaths.some(path => currentPath.startsWith(path));
        
        if (!isTeacherPath && currentPath !== '/login' && currentPath !== '/') {
          router.push('/teacher');
        }
      } 
      // 如果是responsible_teacher角色
      else if (role && role.toLowerCase() === 'responsible_teacher') {
        // 如果不是教师主页但是教师相关路径，重定向到教师主页
        if (currentPath !== '/teacher' && currentPath.startsWith('/teacher/')) {
          router.push('/teacher');
        }
        // 如果不是教师界面相关路径，且不是登录页，则跳转到教师主页
        else if (!currentPath.startsWith('/teacher') && currentPath !== '/login' && currentPath !== '/') {
          router.push('/teacher');
        }
      }
      // 如果是学生角色但访问了教师界面，跳转到学生界面
      else if (role && role.toLowerCase() === 'student') {
        const teacherPaths = ['/teacher'];
        const isTeacherPath = teacherPaths.some(path => currentPath.startsWith(path));
        
        if (isTeacherPath) {
          router.push('/start-experiment');
        }
      }
    } catch (e) {
      console.log('获取个人信息失败:', e);
      // 如果获取信息失败且不在登录页，重定向到登录页
      if (route.path !== '/login') {
        router.push('/login');
      }
    }
  };

  // 监听路由变化，并在用户登录时重新加载用户信息
  watch(
    () => route.fullPath,
    async (newPath) => {
      if (newPath === '/login') return;
      await fetchUserProfile();
    },
    { immediate: true } // 确保初次加载时也会调用
  );

  // 初始获取用户信息
  fetchUserProfile();

  // 加载菜单列表
  useMenuStore().getMenuList();
</script>

<style lang="less">
  ::-webkit-scrollbar {
    width: 4px;
    border-radius: 4px;
    background-color: theme('colors.primary.500');
  }

  ::-webkit-scrollbar-thumb {
    border-radius: 4px;
    background-color: theme('colors.primary.400');

    &:hover {
      background-color: theme('colors.primary.500');
    }
  }

  ::-webkit-scrollbar-track {
    box-shadow: inset 0 0 1px rgba(0, 0, 0, 0);
    border-radius: 4px;
    background: theme('backgroundColor.layout');
  }

  html {
    height: 100vh;
    overflow-y: hidden;
  }

  body {
    margin: 0;
    height: 100vh;
    overflow-y: hidden;
  }

  .stepin-img-checkbox {
  @apply transition-transform;

    &:hover {
    @apply scale-105~"-translate-y-[2px]";
    }

    img {
    @apply shadow-low rounded-md transition-transform;
    }
  }
</style>
