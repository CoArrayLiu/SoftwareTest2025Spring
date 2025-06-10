import { defineStore } from 'pinia';
import http from '../utils/http';
import { ref } from 'vue';
import { Response } from '@/types';
import { addRoutes, removeRoute } from '@/router/dynamicRoutes';

export interface MenuProps {
  id?: number;
  name: string;
  path: string;
  title?: string;
  icon?: string;
  badge?: number | string;
  target?: '_self' | '_blank';
  link?: string;
  component: string;
  renderMenu?: boolean;
  permission?: string;
  parent?: string;
  children?: MenuProps[];
  cacheable: boolean;
}

export interface Experiment {
  id: number; // id 建议用十进制表示法
  title: string; // 中文标题
  name: string; // 英文标题
  kind: string; // 大类名称
}

export const useMenuStore = defineStore('menu', () => {
  const menuList = ref<MenuProps[]>([]);
  const toRoutes = (list: MenuProps[]) => {
    const routes = list.map((item) => ({
      name: item.name,
      path: item.path,
      component: item.component,
      children: item.children && toRoutes(item.children),
      meta: {
        id: item.id,
        title: item.title,
        permission: item.permission,
        icon: item.icon,
        renderMenu: item.renderMenu,
        cacheable: item.cacheable,
        href: item.link,
        badge: /^(false|true)$/i.test(item.badge + '') ? JSON.parse(item.badge + '') : item.badge,
        target: item.target,
      },
    }));
    return routes
  };
  const toMenu = (list: Experiment[]): MenuProps[] => {
    let prevClass = 0;
    const tmp = [];
    list.map((item) => {
      if (Math.floor(item.id / 10) != prevClass) {
        prevClass = Math.floor(item.id / 10);
        tmp.push({
          id: prevClass,
          name: `exp${prevClass}`,
          title: `${prevClass}-${item.kind}`,
          path: `/exp${prevClass}`,
          component: `@/pages/exp${prevClass}`,
          target: '_blank',
          renderMenu: true,
          permission: null,
          children: [],
          cacheable: true,
        });
      }
      tmp[tmp.length - 1].children.push({
        id: item.id,
        name: item.name,
        title: item.title,
        path: `/exp${prevClass}/${item.name}`,
        component: `@/pages/exp${prevClass}/${item.name.toUpperCase()}/index.vue`,
        target: '_self',
        renderMenu: true,
        cacheable: true,
      });
    });
    return tmp;
  };
  async function getMenuList() {
    try {
      const response = await http.request<Experiment, Response<Experiment[]>>('/menu/student_experiment', 'GET');
      const { data } = response;
      menuList.value = toMenu(data);
      menuList.value.unshift(
        {
          id: 0,
          name: `exp0`,
          title: `0-模板`,
          path: `/exp0`,
          component: `@/pages/exp0`,
          target: '_blank',
          renderMenu: false,
          permission: null,
          children: [],
          cacheable: true,
        }
      )
      menuList.value[0].children.push({
        id: 0,
        name: 'template',
        title: '实验开发模板',
        path: `/exp0/template`,
        component: `@/pages/exp0/TEMPLATE/index.vue`,
        target: '_self',
        renderMenu: false,
        cacheable: true,
      })
      // console.log('menuList.value', menuList.value)

      const toRoutesList = toRoutes(menuList.value);
      // console.log('toRoutesList', toRoutesList)
      addRoutes(toRoutesList);
      return data;
    } catch (error) {
      console.error(error);
    }
  }

  async function addMenu(menu: MenuProps) {
    return http
      .request<any, Response<any>>('/menu', 'POST_JSON', menu)
      .then((res) => {
        return res.data;
      })
      .finally(getMenuList);
  }

  async function updateMenu(menu: MenuProps) {
    return http
      .request<any, Response<any>>('/menu', 'PUT_JSON', menu)
      .then((res) => {
        return res.data;
      })
      .finally(getMenuList);
  }

  async function removeMenu(id: number) {
    return http
      .request<any, Response<any>>('/menu', 'DELETE', { id })
      .then(async (res) => {
        if (res.code === 0) {
          removeRoute(res.data.name);
        }
      })
      .finally(getMenuList);
  }

  return {
    menuList,
    getMenuList,
    addMenu,
    updateMenu,
    removeMenu,
  };
});
