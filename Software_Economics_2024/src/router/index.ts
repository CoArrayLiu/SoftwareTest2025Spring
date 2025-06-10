import { createRouter, createWebHashHistory } from 'vue-router';
import { reactive } from 'vue';
import routes from './routes';
import guards from './guards';

const router = createRouter({
  history: createWebHashHistory(),  // 创建路由历史对象
  routes,  // 传递 routes 配置
});

// 注册导航守卫
guards.before.forEach(router.beforeEach);
guards.after.forEach(router.afterEach);

export default router;
