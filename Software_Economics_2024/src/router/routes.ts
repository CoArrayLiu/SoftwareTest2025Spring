import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/pages/login/Login.vue'),
    meta: {
      title: '首页',
      renderMenu: false,
      icon: 'CreditCardOutlined',
    },
  },
  {
    path: '/start-experiment',
    name: 'start-experiment',
    meta: {
      title: '开始实验',
      layout: false, // 不使用 stepin-view 布局
      renderMenu: false, // 不显示在菜单栏中
    },
    component: () => import('@/pages/StartExperiment.vue'),
  },
  {
    path: '/teacher',
    component: () => import('@/views/TeacherApp.vue'),
    meta: {
      layout: false, // 不使用 stepin-view 布局
      renderMenu: false, 
    },
    children: [
      {
        path: '',
        name: 'teacher',
        component: () => import('@/views/Home.vue'),
      },
      {
        path: 'experiment',
        name: 'Experiment',
        component: () => import('@/views/Experiment.vue')
      },
      {
        path: 'experiment/edit/:experimentId',
        name: 'ExperimentEdit',
        component: () => import('@/views/ExperimentEdit.vue'),
        props: route => ({
          experimentId: route.params.experimentId,
          courseId: route.query.courseId
        })
      },
      {
        path: 'people',
        name: 'People',
        component: () => import('@/views/People.vue')
      },
      {
        path: 'rating',
        name: 'Rating',
        component: () => import('@/views/Rating.vue')
      }
    ]
  },
  {
    path: '/report-preview',
    name: 'report-preview',
    meta: {
      title: '实验报告预览',
      layout: false, // 不使用 stepin-view 布局
      renderMenu: false, // 不显示在菜单栏中
    },
    component: () => import('@/pages/report-preview/index.vue'),
  },
  {
    path: '/front',
    name: '前端',
    meta: {
      renderMenu: false,
    },
    component: () => import('@/components/layout/FrontView.vue'),
    children: [
      {
        path: '/login',
        name: '登录',
        meta: {
          icon: 'LoginOutlined',
          view: 'blank',
          target: '_blank',
          cacheable: false,
        },
        component: () => import('@/pages/login/Login.vue'),
      },
    ],
  },
 
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    meta: {
      icon: 'CreditCardOutlined',
      renderMenu: false,
      cacheable: false,
    },
    component: () => import('@/pages/exp0/Exp0.vue'),
  },
];

export default routes;
