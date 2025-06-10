import { defineStore } from 'pinia';
import http from '../utils/http';
import { Response } from '@/types';
import axios from 'axios';
import Cookies from 'js-cookie';

export const useExperimentStore = defineStore('experiment', {
  actions: {
    async getExperiment(id: string | unknown) {
      return http.request<any, Response<any>>(`/experiment/${id}`, 'GET').then((res) => {
        // console.log(res)
        return res.data;
      });
    },
    async getExperimentList() {
      return http.request<any, Response<any>>(`/menu/student_experiment`, 'GET').then((res) => {
        console.log(res.data);
        return res.data;
      });
    },
    async uploadReport(formData) {
      return new Promise<{ success: boolean; msg: string }>((resolve, reject) =>
        axios({
          method: 'post',
          url: 'api/report/submit',
          headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: Cookies.get('Authorization'),
          },
          data: formData,
        })
          .then((res) => {
            resolve(res.data);
          })
          .catch((error) => {
            reject(error);
          })
      );
    },
    async submitData(exp_data: any) {
      return http.request<any, Response<any>>(`http://139.196.226.104:8001/api/report/submitData
`, 'post_json', exp_data).then((res) => {
        // console.log('后端返回:', res.data);
        return res.data;
      });
    },

    // async getReport(experiment_id: string, student_id: string, name: string) {
    //   return http.request<any, Response<any>>(`http://139.196.226.104:8001/api/report/getByInfo?experiment_id=${experiment_id}&student_id=${student_id}&name=${name}`,
    //     'GET',).then((res) => {
    //     console.log('后端返回:', res.data);
    //     return res.data;
    //   });
    // },

    async getReport(experimentId, courseId, reportData) {
      try {
        const response = await fetch('http://localhost:8002/api/reports/generate/word', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
          body: JSON.stringify({
            experimentId,
            courseId,
            other: reportData,
          }),
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const blob = await response.blob();
        const blobUrl = URL.createObjectURL(blob);

        // 尝试在新窗口直接打开 Word
        const newWindow = window.open(blobUrl, '_blank');
        if (!newWindow) {
          alert('弹窗被阻止，请允许该网站的弹窗。');
        }

        // 释放 Blob URL
        setTimeout(() => URL.revokeObjectURL(blobUrl), 1000);
        
        // 返回一个成功的标志，以便调用者可以检查
        return 'success';
      } catch (error) {
        console.error('Error:', error);
        alert('生成报告失败，请稍后重试。');
        // 返回错误信息，以便调用者可以检查
        return error.message || '生成报告失败';
      }
    },

    uploadImg(blob: Blob, experiment_id: string, student_id: string, filename: string) {
      let fm = new FormData();
      fm.append("file", blob);
      fm.append("experiment_id", experiment_id);
      fm.append("student_id", student_id)
      fm.append("filename", filename)
      var request = new XMLHttpRequest();
      let res_url = ""
      request.open("PUT", "http://localhost:8002/api/report/upload");
      request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
          const res = JSON.parse(request.response)
          res_url = res.data
        }
      }
      request.send(fm);
      return res_url
    }
  },
});
