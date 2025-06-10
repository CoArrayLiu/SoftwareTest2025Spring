<template>
  <h2>六、实验心得</h2>
  <a-textarea v-model:value=DecisionTreeStore.summary style="margin-top: 5px;" placeholder="请写下你的实验心得"  showCount :maxlength="1000" :rows="4"/>
</template>

<script setup lang="ts">
  import { useDecisionTreeStore } from '../stores'
  let DecisionTreeStore = useDecisionTreeStore()
</script>

<style scoped>

</style>