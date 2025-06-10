<template>
  <div class="login h-[800px] flex items-center justify-center">
    <login-box class="shadow-lg" @success="onLoginSuccess" />
    <!-- 隐藏的文件输入元素 -->
    <input type="file" ref="fileInput" style="display: none;" />
  </div>
</template>

<script lang="ts" setup>
import LoginBox from './LoginBox.vue';
import { useRouter } from 'vue-router';
import { reactive, ref } from 'vue';
import { useAccountStore  } from '@/store';
import { client } from '@/utils/alioss';

const accountStore = useAccountStore();
const { logout, account } = useAccountStore();

const router = useRouter();
const fileInput = ref(null);

function onLoginSuccess() {
  useAccountStore()
    .profile()
    .then((response) => {
      console.log('1111');
      const { name, avatar, role } = response;
      //const { account } = response;
      console.log('login', name);

      user.name = name;
      user.avatar = avatar;

      // 根据角色跳转到不同页面
      if (role && (role.toLowerCase() === 'teacher' || role.toLowerCase() === 'responsible_teacher')) {
        // 如果是教师或责任教师，跳转到教师管理平台
        router.push('/teacher');
      } else {
        // 如果是学生，跳转到"开始实验"界面
        router.push('/start-experiment');
      }
    });
}


const showSetting = ref(false);
const user = reactive({
  name: account.name,

  avatar: account.avatar,
  //courseID: course.courseID,
  //courseName: course.courseName,

});

</script>
<style scoped lang="less"></style>
