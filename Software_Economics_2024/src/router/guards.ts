import { NavigationGuard, NavigationHookAfter } from 'vue-router';
import http from '@/utils/http';
import { useAccountStore, useMenuStore } from '@/store';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
NProgress.configure({ showSpinner: false });

const loginGuard: NavigationGuard = async function (to, from) {
  // 根路径总是重定向到登录页面
  if (to.path === '/') {
    return '/login';
  }
  
  // await useAccountStore().profile();
  if (!http.checkAuthorization() && !/^\/login?$/.test(to.fullPath)) {
    return '/login';
  } else {
    if (useMenuStore().menuList.length == 0 && !to.path.includes('/login')) {
      console.log('useMenuStore')
      await useMenuStore().getMenuList();
      return to.path;
    }
    // 这个情况不能用于token过期
    if (!useAccountStore().account.name && !to.path.includes('/login')) {
      return '/login';
    }
  }
};

const progressStart: NavigationGuard = function (to, from) {
  NProgress.start();
};

const progressEnd: NavigationHookAfter = function (to, from) {
  NProgress.done();
};

export default {
  before: [progressStart, loginGuard],
  after: [progressEnd],
};
