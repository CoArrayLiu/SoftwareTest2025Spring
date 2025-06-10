<template>
  <div class="start-experiment">
    <img src="/TJlogo.svg" alt="Logo" class="logo" />
    <h1 class="title">软件工程经济学在线实验系统</h1>
    <button class="start-button" @click="startExperiment">开始实验</button>
  </div>
</template>

<script lang="ts" setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();

  function startExperiment() {
    router.push('/exp1/tanpaifang61');
  }
</script>

<style scoped>
  .start-experiment {
    height: 100vh; /* 全屏高度 */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #6ab2c8; /* 设置背景色 */
  }

  .logo {
    width: 120px;
    margin-bottom: 20px;
  }

  .title {
    font-size: 2rem;
    color: white;
    margin-bottom: 30px;
  }

  .start-button {
    padding: 10px 20px;
    font-size: 1.2rem;
    color: white;
    background: #005a8c;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
</style>
