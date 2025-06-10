import { defineStore } from 'pinia';
import { ref, computed, reactive, } from 'vue';

export const useExp1Store = defineStore(
  'exp1_IFPUG',
  () => {
    // 这里定义需要持久化保存的数据
    const summary = ref<string>('');
    // 1-1
    const ILF = ref(0);
    const EIF = ref(0);
    const EI = ref(0);
    const EO = ref(0);
    const EQ = ref(0);
    const columns = ref([
      {
          title: '组件',
          dataIndex: 'component',
          key: 'component',
          align: 'center',
      },
      {
          title: '数量',
          dataIndex: 'number',
          key: 'number',
          align: 'center',
      },
      {
          title: '复杂度',
          children: [
              {
                  title: '简单',
                  align: 'center',
                  children: [
                      {
                          title: '计数',
                          align: 'center',
                          children: [
                              {
                                  title: 'A',
                                  dataIndex: 'A',
                                  align: 'center',
                              }
                          ],
                      },
                      {
                          title: '权重',
                          align: 'center',
                          children: [
                              {
                                  title: 'B',
                                  dataIndex: 'B',
                                  align: 'center',
                              }
                          ],
                      },
                      {
                          title: '功能点数',
                          align: 'center',
                          children: [
                              {
                                  title: 'C=A*B',
                                  dataIndex: 'C',
                                  align: 'center',
                              }
                          ],
                      }
                  ],
              },
              {
                  title: '平均',
                  align: 'center',
                  children: [
                      {
                          title: '计数',
                          children: [
                              {
                                  align: 'center',
                                  dataIndex: 'D',
                                  title: 'D',
                              }
                          ],
                      },
                      {
                          title: '权重',
                          align: 'center',
                          children: [
                              {
                                  dataIndex: 'E',
                                  title: 'E',
                                  align: 'center',
                              }
                          ],
                      },
                      {
                          title: '功能点数',
                          align: 'center',
                          children: [
                              {
                                  dataIndex: 'F',
                                  title: 'F=D*E',
                                  align: 'center',
                              }
                          ],
                      }
                  ],
              },
              {
                  title: '复杂',
                  align: 'center',
                  children: [
                      {
                          title: '计数',
                          align: 'center',
                          children: [
                              {
                                  dataIndex: 'G',
                                  title: 'G',
                                  align: 'center',
                              }
                          ],
                      },
                      {
                          title: '权重',
                          align: 'center',
                          children: [
                              {
                                  dataIndex: 'H',
                                  title: 'H',
                                  align: 'center',
                              }
                          ],
                      },
                      {
                          title: '功能点数',
                          align: 'center',
                          children: [
                              {
                                  dataIndex: 'I',
                                  title: 'I=G*H',
                                  align: 'center',
                              }
                          ],
                      }
                  ],
              }
          ]
      },
      {
          title: '未调整功能点数',
          dataIndex: 'unchanged',
          key: 'unchanged',
          align: 'center',
      }
  ]);
  

  
  
  const tableData5 = ref([
    {
      component: 'EI',
      A: '',
      B: '3',
      C: '',
      D: '',
      E: '4',
      F: '',
      G: '',
      H: '6',
      I: '',
      unchanged: '',
    },
    {
      component: 'EO',
      A: '',
      B: '4',
      C: '',
      D: '',
      E: '5',
      F: '',
      G: '',
      H: '7',
      I: '',
      unchanged: '',
    },
    {
      component: 'EQ',
      A: '',
      B: '3',
      C: '',
      D: '',
      E: '4',
      F: '',
      G: '',
      H: '6',
      I: '',
      unchanged: '',
    },
    {
      component: 'ILF',
      A: '',
      B: '7',
      C: '',
      D: '',
      E: '10',
      F: '',
      G: '',
      H: '15',
      I: '',
      unchanged: '',
    },
    {
      component: 'EIF',
      A: '',
      B: '5',
      C: '',
      D: '',
      E: '7',
      F: '',
      G: '',
      H: '10',
      I: '',
      unchanged: '',
    },
  ]);
  
  const dataadjust = ref([
      { index: '1', title: 'Requirement for reliable backup and recovery', grade: '0' },
      { index: '2', title: 'Requirement for data communication', grade: '0' },
      { index: '3', title: 'Extent of distributed processing', grade: '0' },
      { index: '4', title: 'Performance requirements', grade: '0' },
      { index: '5', title: 'Expected operational environment', grade: '0' },
      { index: '6', title: 'Extent of online data entries', grade: '0' },
      { index: '7', title: 'Extent of multi-screen or multi-operation online data input', grade: '0' },
      { index: '8', title: 'Extent of online updating of master files', grade: '0' },
      { index: '9', title: 'Extent of complex inputs, outputs, online queries and files', grade: '0' },
      { index: '10', title: 'Extent of complex data processing', grade: '0' },
      { index: '11', title: 'Extent that currently developed code can be designed for reuse', grade: '0' },
      { index: '12', title: 'Extent of conversion and installation included in the design', grade: '0' },
      { index: '13', title: 'Extent of multiple installations in an organization and variety of customer organizations', grade: '0' },
      { index: '14', title: 'Extent of change and focus on ease of use', grade: '0' }
  ]);
    // 第二个方法的表格的功能点数
    const c = computed(
      () => (index) =>
      (tableData5.value[index].C =
        (tableData5.value[index].A ? parseInt(tableData5.value[index].A) : 0) * parseInt(tableData5.value[index].B) +
        '')
    );
    // 平均功能点数
    const f = computed(
      () => (index) =>
      (tableData5.value[index].F =
        (tableData5.value[index].D ? parseInt(tableData5.value[index].D) : 0) * parseInt(tableData5.value[index].E) +
        '')
    );
    // 复杂功能点数
    const i = computed(
      () => (index) =>
      (tableData5.value[index].I =
        (tableData5.value[index].G ? parseInt(tableData5.value[index].G) : 0) * parseInt(tableData5.value[index].H) +
        '')
    );
    
    const number = (index) => {
      if (tableData5[index]) {
        const result = (parseInt(tableData5[index].A) || 0) + (parseInt(tableData5[index].D) || 0) + (parseInt(tableData5[index].G) || 0);
        tableData5[index].number = result.toString();  // 将结果转换为字符串
        return tableData5[index].number;
      }
    };
    // 未调整功能点数(是上面三个功能点数的加和)
    const unchanged = computed(
      () => (index) =>
      (tableData5.value[index].unchanged =
        (tableData5.value[index].C ? parseInt(tableData5.value[index].C) : 0) +
        (tableData5.value[index].F ? parseInt(tableData5.value[index].F) : 0) +
        (tableData5.value[index].I ? parseInt(tableData5.value[index].I) : 0) +
        '')
    );
    // const VAF = computed(() => {
    //     let vaf = 0;
    //     for (let i = 0; i < dataadjust.value.length; i++) {
    //       if (dataadjust[i] && dataadjust[i].grade) {
    //         vaf += (parseInt(dataadjust[i].grade) || 0);
    //     }
    //     }
    //     vaf = vaf * 0.01 + 0.65; // Applying the formula to calculate VAF
    //     VAF.value = parseFloat(vaf.toFixed(2)); // Convert the formatted string back to number
    //     return VAF.value;
    // });
    const SUM = computed(() => {
      return SUM.value =tableData5.value.reduce((amt, item) => amt + parseInt(item.unchanged), 0);
    });
    // 功能点调整因子
    const VAF = computed(() => {
      let vaf = dataadjust.value.reduce((amt, item) => amt + (item.grade ? parseInt(item.grade) : 0), 0);
      // VAF = 0.65 + 0.01 × 14个数据的加和
      vaf = vaf * 0.01 + 0.65;
      vaf = parseFloat(vaf.toFixed(2));
      return vaf;
    }); 
    // // 功能点数
    // const ALL = computed(() => (SUM.value * VAF.value).toFixed(2));
    
    
    // Computes the final value using SUM and VAF
    const ALL = computed(() => {
        return (SUM.value * VAF.value).toFixed(2); // 返回一个格式化到两位小数的字符串
    });

    // 清空数据
    const clear = () => {
      EIF.value = undefined;
      ILF.value = undefined;
      EI.value = undefined;
      EO.value = undefined;
      EQ.value = undefined;
      summary.value = '';
      tableData5.value = [
        {
          component: 'EI',
          A: '',
          B: '3',
          C: '',
          D: '',
          E: '4',
          F: '',
          G: '',
          H: '6',
          I: '',
          unchanged: '',
        },
        {
          component: 'EO',
          A: '',
          B: '4',
          C: '',
          D: '',
          E: '5',
          F: '',
          G: '',
          H: '7',
          I: '',
          unchanged: '',
        },
        {
          component: 'EQ',
          A: '',
          B: '3',
          C: '',
          D: '',
          E: '4',
          F: '',
          G: '',
          H: '6',
          I: '',
          unchanged: '',
        },
        {
          component: 'ILF',
          A: '',
          B: '7',
          C: '',
          D: '',
          E: '10',
          F: '',
          G: '',
          H: '15',
          I: '',
          unchanged: '',
        },
        {
          component: 'EIF',
          A: '',
          B: '5',
          C: '',
          D: '',
          E: '7',
          F: '',
          G: '',
          H: '10',
          I: '',
          unchanged: '',
        },
      ];
      dataadjust.value = [
        {
          index: '1',
          title: 'Requirement for reliable backup and recovery ',
          grade: '0',
        },
        {
          index: '2',
          title: 'Requirement for data communication',
          grade: '0',
        },
        {
          index: '3',
          title: 'Extent of distributed processing ',
          grade: '0',
        },
        {
          index: '4',
          title: 'Performance requirements ',
          grade: '0',
        },
        {
          index: '5',
          title: 'Expected operational environment ',
          grade: '0',
        },
        {
          index: '6',
          title: 'Extent of online data entries ',
          grade: '0',
        },
        {
          index: '7',
          title: 'Extent of multi-screen or multi-operation online data input ',
          grade: '0',
        },
        {
          index: '8',
          title: 'Extent of online updating of master files ',
          grade: '0',
        },
        {
          index: '9',
          title: 'Extent of complex inputs, outputs, online queries and files ',
          grade: '0',
        },
        {
          index: '10',
          title: 'Extent of complex data processing ',
          grade: '0',
        },
        {
          index: '11',
          title: 'Extent that currently developed code can be designed for reuse ',
          grade: '0',
        },
        {
          index: '12',
          title: 'Extent of conversion and installation included in the design ',
          grade: '0',
        },
        {
          index: '13',
          title: 'Extent of multiple installations in an organization and variety of customer organizations ',
          grade: '0',
        },
        {
          index: '14',
          title: 'Extent of change and focus on ease of use ',
          grade: '0',
        },
      ];
      
    };

    return {
      ILF, EIF, EI,EO,EQ,  dataadjust, summary,
      tableData5, c, f, i,number, unchanged, SUM, VAF, ALL, clear
    };
  },
  {
    persist: true,  
  }
);