import { defineStore } from 'pinia';
import http from '../utils/http';
import { Response } from '@/types';
import { useMenuStore } from './menu';
import { client } from '@/utils/alioss';

export interface Course {
  courseId: number;
  courseName: string;
}

export interface Profile {
  account: Account;
  permissions: string[];
  role: string;
  userIndex: string;
  name: string;
  email: string;
  avatar: string;
  course: Course;
}

export interface Account {
  index:number;
  name: string;
  age: number;
  avatar: string;
  gender: string;
  email: string;
}

export type TokenResult = {
  token: string;
  expires: number;
};

export const useAccountStore = defineStore('account', {
  state() {
    return {
      account: {} as Account,
      permissions: [] as string[],
      role: '',
      logged: false,
      course: {} as Course,
    };
  },

  actions: {
    setCourseId(courseId: number, courseName?: string) {
      console.log("设置课程ID:", {
        courseId: courseId,
        类型: typeof courseId,
        courseName: courseName
      });
      
      // 确保courseId是数字类型
      this.course.courseId = Number(courseId);
      
      if (courseName) {
        this.course.courseName = courseName;
      }
      
      console.log("课程ID设置完成:", this.course);
    },
    clearCourseId() {
      this.course.courseId = 0;
    },
    async login(account: string, password: string) {
      return http
        .request<TokenResult, Response<TokenResult>>('http://localhost:8002/api/users/auth/login', 'post_json', {
          account,
          password,
          school: '同济大学',
          //courseId: 42041301
        })
        .then(async (response) => {
          // 检查 response 结构是否正确
          if (response && response.code === 200) {
            this.logged = true;
            http.setAuthorization(`Bearer ${response.data.token}`, 10);
            console.log(111,response);
            
            // 先获取用户信息
            try {
              const profileData = await this.profile();
              // 只有当角色不是教师时，才尝试获取课程ID
              if (profileData.role !== 'teacher') {
                await this.updateCourseId();
              } else {
                // 为教师设置默认课程信息
                this.course.courseId = 0; // 使用默认值
                this.course.courseName = "软件工程管理与经济";
                console.log('教师账号不需要获取学生课程ID');
              }
            } catch (error) {
              console.error('获取用户信息失败:', error);
              // 设置默认课程信息
              this.course.courseName="软件工程管理与经济";
            }

            await useMenuStore().getMenuList();
            console.log('登录成功，已设置授权');
            localStorage.setItem('token', response.data.token);
            return response;
          } else {
            // 如果 response 结构不正确或者登录失败，则输出错误信息并拒绝 Promise
            console.error('登录失败:', response ? response.msg : '未知错误');
            return Promise.reject(response ? response.msg : '未知错误');
          }
        })
        .catch((error) => {
          // 捕获并输出请求过程中发生的错误
          console.error('登录过程中发生错误:', error);
          return Promise.reject(error);
        });
    },

    async logout() {
      return new Promise<boolean>((resolve) => {
        http.removeAuthorization();
        this.logged = false;
        // 完全清除用户信息
        this.account = {} as Account;
        this.permissions = [];
        this.role = '';
        this.course = {} as Course;
        // 移除本地存储
        localStorage.removeItem('token');
        localStorage.removeItem('userInfo');
        resolve(true);
      });
    },

    async activate(username: string, password: string, code: number) {
      return http
        .request<any, Response<any>>('http://localhost:8002/api/users/auth/activate', 'post_json', { username, password, code })
        .then(async (response) => {
          console.log(response)
          return response;
        });
    },

    // 发送忘记密码验证码
    async sendForgotPasswordCode(email: string) {
      return http
        .request<any, Response<any>>('http://localhost:8002/api/users/auth/send-verification-code', 'post', { email })
        .then(async (response) => {
          if (response && response.code === 200) {
            console.log('验证码已发送至邮箱，请查收');
            return response;
          } else {
            console.error('发送验证码失败:', response ? response.msg : '未知错误');
            return Promise.reject(response ? response.msg : '未知错误');
          }
        })
        .catch((error) => {
          console.error('发送验证码过程中发生错误:', error);
          return Promise.reject(error);
        });
    },

    async activateAccountOrChangePassword(email: string, activationCode: string, newPassword: string) {
      return http
        .request<any, Response<any>>('http://localhost:8002/api/users/auth/activate', 'post_json', {
          email,
          code: activationCode,
          password: newPassword
        })
        .then(async (response) => {
          if (response && response.code === 200) {
            console.log('账户激活或密码修改成功');
            return response;
          } else {
            console.error('账户激活或密码修改失败:', response ? response.msg : '未知错误');
            return Promise.reject(response ? response.msg : '未知错误');
          }
        })
        .catch((error) => {
          console.error('账户激活或密码修改过程中发生错误:', error);
          return Promise.reject(error);
        });
    },

    async loadAvatar(avatarUrl: string) {
      if (!avatarUrl) {
        console.error('头像URL不能为空');
        return Promise.reject('头像URL不能为空');
      }

      return http.request<void, Response<ArrayBuffer>>(
        'http://localhost:8002/api/users/avatar', 
        'post',
        { avatarUrl: avatarUrl },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      ).then((response) => {
        if (response && response.code === 200) {
          // 假设返回的是图片的二进制数据
          const blob = new Blob([response.data], { type: 'image/jpeg' }); // 根据实际情况调整MIME类型
          const url = URL.createObjectURL(blob);
          console.log('头像加载成功:', url);
          return url; // 返回一个可以用于显示图片的URL
        } else {
          console.error('获取用户头像失败:', response.msg);
          return Promise.reject(response.msg || '获取用户头像失败');
        }
      }).catch((error) => {
        console.error('加载头像过程中发生错误:', error);
        return Promise.reject(error);
      });
    },

    async profile() {
      return http.request<Account, Response<Profile>>(
        'http://localhost:8002/api/users/me',
        'get'
      ).then((response) => {
        if (response.code === 200) {
          // 根据返回的数据结构，解构需要的属性
          const { userIndex, name, email, avatar, role } = response.data;
          // 更新 store 中的状态
          this.account = {
            index: userIndex,
            name: name,
            email: email,
            //avatar: this.loadAvatar(avatar),
            avatar: avatar,
            role: role
          };
          // 保存角色信息
          this.role = role;
          
          console.log(response.data);
          console.log('用户信息获取成功');
          // 返回解构后的数据
          return {
            userIndex: userIndex,
            name: name,
            email: email,
            avatar: avatar,
            role: role
          };
        } else {
          // 如果响应不成功或 code 不为 0，则拒绝 Promise
          return Promise.reject(response.msg || '获取用户资料失败');
        }
      });
    },

    async updateCourseId() {
      try {
        const token = localStorage.getItem('token');
        const response = await http.request<number, Response<number>>(
          'http://localhost:8002/api/courses/students/current',
          'get',
          {},
          {
            headers: {
              Authorization: token ? `Bearer ${token}` : ''
            }
          }
        );

        if (response.code === 200) {
          this.course.courseId = response.data;
          console.log('课程 ID 更新成功:', this.course.courseId);
          
          // 获取课程信息
          await this.fetchCourseInfo(this.course.courseId);
          
          return {
            courseId: this.course.courseId,
            courseName: this.course.courseName
          }
        } else {
          console.error('获取课程 ID 失败:', response.msg);
          return Promise.reject(response.msg || '获取课程 ID 失败');
        }
      } catch (error) {
        console.error('更新课程 ID 过程中发生错误:', error);
        return Promise.reject(error);
      }
    },

    async fetchCourseInfo(courseId: number) {
      try {
        const token = localStorage.getItem('token');
        const response = await http.request<Course, Response<Course>>(
          `http://localhost:8002/api/courses/${courseId}`,
          'get',
          {},
          {
            headers: {
              Authorization: token ? `Bearer ${token}` : ''
            }
          }
        );

        if (response && response.code === 200) {
          this.course.courseName = response.data.courseName;
          return response.data;
        } else {
          console.error('获取课程信息失败:', response ? response.msg : '未知错误');
          return Promise.reject(response ? response.msg : '未知错误');
        }
      } catch (error) {
        console.error('获取课程信息过程中发生错误:', error);
        return Promise.reject(error);
      }
    },

    setLogged(logged: boolean) {
      this.logged = logged;
    },
    clear() {
      this.account = null;
    }
  },
});
