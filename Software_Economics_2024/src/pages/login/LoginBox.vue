<template>
  <div class="login-box">
    <a-form :model="form" :wrapperCol="{ span: 24 }" @finish="login" class="login-form">
      <h1>软件工程管理与经济</h1>
      <h2>虚拟仿真实验平台</h2>
      <a-form-item :required="true" name="email">
        <a-input v-model:value="form.email" placeholder="请输入邮箱" class="login-input" />
      </a-form-item>
      <a-form-item :required="true" name="password">
        <a-input v-model:value="form.password" placeholder="请输入密码" class="login-input" type="password" />
      </a-form-item>
      <div class="flex">
        <a-button htmlType="submit" class="login-button bg-primary-600 m-1" type="primary" :loading="loading"
          @click="$event => { login }">
          登录
        </a-button>
<!--        <a-button class="login-button bg-primary-600" type="primary" @click="$event => showModal()">-->
<!--          修改密码-->
<!--        </a-button>-->
      </div>

      <a-divider></a-divider>
      <div class="terms">
        登录即代表您同意我们的
        <span class="font-bold">用户条款 </span>、<span class="font-bold"> 数据使用协议 </span>、以及
        <span class="font-bold">Cookie使用协议</span>。
      </div>
    </a-form>
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
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue';
import { useAccountStore } from '@/store';
import useThemeStore from 'stepin/es/theme-editor/store';
import { message } from 'ant-design-vue';

interface LoginFormProps {
  email: string;
  password: string;
}
const visible = ref<boolean>(false);

const showModal = () => {
  visible.value = true;
};

const updatePassword = (e) => {
  if (updateForm.email === '' || updateForm.oldPassword === '' || updateForm.newPassword === '') {
    message.error('请输入完整信息')
    return
  }
  // accountStore.update(updateForm.email, updateForm.oldPassword, updateForm.newPassword)
  //   .then((res) => {
  //     if (res.success == true) {
  //       message.success("修改成功")
  //       visible.value = false;
  //       updateForm.email = ''
  //       updateForm.oldPassword = ''
  //       updateForm.newPassword = ''
  //     } else {
  //       message.error(res.msg)
  //     }
  //
  //   })
};

const { setBgSeriesColors } = useThemeStore();

onMounted(() => {
  setBgSeriesColors({ 'bg-base': '#fff' }, true, '.login-box');
});

const loading = ref(false);

const form = reactive({
  email: '',
  password: '',
});
const updateForm = reactive({
  email: form.email,
  oldPassword: '',
  newPassword: ''
});

const emit = defineEmits<{
  (e: 'success', fields: LoginFormProps): void;
  (e: 'failure', reason: string, fields: LoginFormProps): void;
}>();

const accountStore = useAccountStore();

function login(params: LoginFormProps) {
  loading.value = true;
  accountStore
    .login(params.email, params.password)
    .then((res) => {

      if (res.code === 200) {
        // console.log(res);

        emit('success', params);
        message.success("登录成功");
      } else if (res.code === 1) {
        alert(res.msg)
        showModal()
      }
    })
    .catch((e) => {
      message.error(e);
    })
    .finally(() => (loading.value = false));
  //accountStore.updateCourseId();
  //accountStore.course.courseName="软件工程管理与经济";
}


</script>
<style lang="less" scoped>
.login-box {
  .login-form {
    width: 440px;
    @apply py-12 h-fit border-border px-10 rounded-sm text-text bg-container;

    .third-platform {
      .third-title {
        @apply text-left mb-md;
        font-size: 16px;
      }

      .third-list {
        @apply flex;

        .icon {
          @apply text-gray-400 flex-1 hover:text-primary-500 cursor-pointer;
          font-size: 28px;

          &.wechat {
            @apply hover:text-green-600;
          }

          &.twitter {
            @apply hover:text-blue-400;
          }

          &.qq {
            @apply hover:text-red-600;
          }
        }
      }
    }

    .login-input,
    .login-button {
      @apply rounded;
      margin-top: 16px;
      height: 40px;
      width: 100%;
    }

    .terms {}
  }
}
</style>
