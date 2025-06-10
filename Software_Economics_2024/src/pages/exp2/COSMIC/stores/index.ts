import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useCosmicStore = defineStore(
  'exp2_COSMIC',
  () => {
    // 功能用户
    const functionalUsers = ref<{ key: string; name: string }[]>([]);
    // 功能处理过程
    const functionalProcesses = ref<{ key: string; name: string; description: string }[]>([]);
    // 数据组
    const dataGroups = ref<{ key: string; name: string; description: string }[]>([]);
    // 数据移动
    const dataMovements = ref<{
      key: string;
      process: string;
      dataGroup: string;
      movementType: string;
      parentKey?: string;
    }[]>([]);
    // 统计数据
    const movementStatistics = ref<any[]>([]);
    // 实验总结
    const experimentSummary = ref('');

    // 计算总功能点CFP
    const totalCFP = computed(() => {
      // 统计所有数据移动的数量
      return dataMovements.value.length;
    });

    // 清空所有数据
    const clear = () => {
      functionalUsers.value = [];
      functionalProcesses.value = [];
      dataGroups.value = [];
      dataMovements.value = [];
      movementStatistics.value = [];
      experimentSummary.value = '';
    };

    // 添加功能用户
    const addFunctionalUser = (user: { key: string; name: string }) => {
      functionalUsers.value.push(user);
    };
    // 添加功能处理过程
    const addFunctionalProcess = (process: { key: string; name: string; description: string }) => {
      functionalProcesses.value.push(process);
    };
    // 添加数据组
    const addDataGroup = (group: { key: string; name: string; description: string }) => {
      dataGroups.value.push(group);
    };
    // 添加数据移动
    const addDataMovement = (movement: { key: string; process: string; dataGroup: string; movementType: string; parentKey?: string }) => {
      dataMovements.value.push(movement);
    };

    return {
      functionalUsers,
      functionalProcesses,
      dataGroups,
      dataMovements,
      movementStatistics,
      experimentSummary,
      totalCFP,
      clear,
      addFunctionalUser,
      addFunctionalProcess,
      addDataGroup,
      addDataMovement,
    };
  },
  {
    persist: true, // 可选：如需持久化
  }
);
