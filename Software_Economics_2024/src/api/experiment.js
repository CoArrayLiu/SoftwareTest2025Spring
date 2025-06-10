import axios from 'axios';

const BASE_URL = 'http://localhost:8002/api';

// 获取未发布的实验列表
export async function getExperimentsNotInCourse(courseId) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    const response = await axios.get(`${BASE_URL}/courses/${courseId}/experiments/available`, {
      params: { courseId },
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return { success: true, experiments: data.data };
      } else {
        return { success: false, message: data.message };
      }
    }
    return { success: false, message: '请求失败' };
  } catch (error) {
    console.error('获取未发布实验列表错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '获取实验列表失败'
    };
  }
}

// 发布实验
export async function publishExperiment(courseId, experimentId) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 直接发送实验ID数组作为请求体
    const experimentIdList = Array.isArray(experimentId) ? experimentId : [experimentId];

    const response = await axios.post(`${BASE_URL}/courses/${courseId}/experiments`, experimentIdList, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return {
          success: true,
          message: data.message || '添加实验成功！'
        };
      } else {
        return {
          success: false,
          message: getErrorMessage(data.code, data.message)
        };
      }
    }
    return {
      success: false,
      message: `服务端返回非预期状态码：${response.status}`
    };
  } catch (error) {
    // 错误处理逻辑与原有实现保持一致
    let message;
    if (error.response) {
      message = `服务端错误（${error.response.status}）：${error.response.data?.message || "未知错误"}`;
    } else if (error.request) {
      message = "网络错误：无法连接到服务器";
    } else {
      message = "请求失败：" + error.message;
    }

    // 开发环境下打印详细错误信息
    console.error("添加实验时发生错误：", error);

    return {
      success: false,
      message: message
    };
  }
}

// 添加错误信息映射函数
function getErrorMessage(code, message) {
  const errorMessages = {
    400: "添加实验失败",
    // 可以添加更多错误码映射
  };
  return errorMessages[code] || message || "发生未知错误";
}

// 获取实验内容
export async function getExperimentContent(experimentId, courseId) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    const response = await axios.get(`${BASE_URL}/courses/${courseId}/experiments/${experimentId}/config`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        const experimentContent = data.data;
        // 如果 file 为空字符串，则设置为 null 或 undefined
        const bookUrl = experimentContent.experimentBook && experimentContent.experimentBook.trim() !== ''
          ? experimentContent.experimentBook
          : null;

        return {
          success: true,
          data: {
            experimentId: experimentContent.experimentId,
            courseId: experimentContent.courseId,
            content: experimentContent.experimentContent || '',
            bookUrl: bookUrl,  // 使用处理后的 bookUrl
            templateUrl: experimentContent.reportTemplate || '',
            startTime: experimentContent.startTime,
            endTime: experimentContent.endTime,
            score: experimentContent.experimentFullScore || 100
          }
        };
      } else {
        return { success: false, message: data.message || '查找实验信息失败' };
      }
    }
    return { success: false, message: '请求失败' };
  } catch (error) {
    console.error('获取实验内容错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '获取实验内容失败'
    };
  }
}

// 上传实验内容
export async function uploadExperimentContent(experimentId, courseId, content) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    const jsonData = {
      experimentContent: content,
      experimentId: experimentId,
      courseId: courseId
    };
    
    const response = await axios.put(`${BASE_URL}/courses/${courseId}/experiments/${experimentId}`, jsonData, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return {
          success: true,
          message: data.data || '上传实验内容成功'
        };
      } else {
        return {
          success: false,
          message: data.message || '上传失败'
        };
      }
    }
    return { success: false, message: '上传失败' };
  } catch (error) {
    console.error('上传实验内容错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '上传实验内容失败'
    };
  }
}

// 上传实验指导书
export async function uploadExperimentBook(formData) {
  try {
    const response = await axios.put(`${BASE_URL}/experiment/upload_experiment_book`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return {
          success: true,
          data: data.data,
          message: '指导书上传成功'
        };
      } else {
        return {
          success: false,
          message: data.message || '上传失败'
        };
      }
    }
    return { success: false, message: '上传失败' };
  } catch (error) {
    console.error('上传指导书错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '上传指导书失败'
    };
  }
}

// 上传实验报告模板
export async function uploadExperimentTemplate(formData) {
  try {
    const response = await axios.put(`${BASE_URL}/experiment/upload_experiment_report_template`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return {
          success: true,
          data: data.data,
          message: '报告模板上传成功'
        };
      } else {
        return {
          success: false,
          message: data.message || '上传失败'
        };
      }
    }
    return { success: false, message: '上传失败' };
  } catch (error) {
    console.error('上传报告模板错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '上传报告模板失败'
    };
  }
}

// 获取课程中的实验列表
export async function getExperimentsInCourse(courseId) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    const response = await axios.get(`${BASE_URL}/courses/${courseId}/experiments`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return { success: true, experiments: data.data };
      } else {
        return { success: false, message: data.message };
      }
    }
    return { success: false, message: '请求失败' };
  } catch (error) {
    console.error('获取已发布实验列表错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '获取实验列表失败'
    };
  }
}

// 删除实验
export async function deleteExperiment(courseId, experimentId) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 使用相对路径，利用Vite代理解决CORS问题
    const response = await axios.delete(`http://localhost:8002/api/courses/${courseId}/experiments/${experimentId}`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return { success: true };
      } else {
        return { success: false, message: data.message };
      }
    }
    return { success: false, message: '删除失败' };
  } catch (error) {
    console.error('删除实验错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '删除实验失败'
    };
  }
}

// 下载实验指导书
export async function downloadExperimentBook(experimentId, courseId) {
  try {
    const response = await axios.get(`${BASE_URL}/courses/${courseId}/experiments/${experimentId}/guidebook`, {
      responseType: 'arraybuffer',
      withCredentials: true
    });

    if (response.status === 200) {
      // 处理文件下载
      const contentDisposition = response.headers['content-disposition'];
      const filename = contentDisposition
        ? contentDisposition.split('filename=')[1].replace(/"/g, '')
        : `实验指导书_${experimentId}.pdf`;

      const blob = new Blob([response.data], { type: response.headers['content-type'] });
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      link.click();
      URL.revokeObjectURL(url);

      return { success: true, message: '下载成功' };
    }
    return { success: false, message: '下载失败，请重试' };
  } catch (error) {
    let message = '下载实验指导书失败';
    if (error.response) {
      message = `服务器错误(${error.response.status}): ${error.response.data?.message || '未知错误'}`;
    } else if (error.request) {
      message = '网络错误：无法连接到服务器';
    } else {
      message = `请求失败：${error.message}`;
    }
    return { success: false, message };
  }
}

// 下载实验报告模板
export async function downloadExperimentReportTemplate(experimentId, courseId) {
  try {
    const response = await axios.get(`${BASE_URL}/courses/${courseId}/experiments/${experimentId}/template`, {
      responseType: 'arraybuffer',
      withCredentials: true
    });

    if (response.status === 200) {
      // 处理文件下载
      const contentDisposition = response.headers['content-disposition'];
      const filename = contentDisposition
        ? contentDisposition.split('filename=')[1].replace(/"/g, '')
        : `实验报告模板_${experimentId}.docx`;

      const blob = new Blob([response.data], { type: response.headers['content-type'] });
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      link.click();
      URL.revokeObjectURL(url);

      return { success: true, message: '下载成功' };
    }
    return { success: false, message: '下载失败，请重试' };
  } catch (error) {
    let message = '下载实验报告模板失败';
    if (error.response) {
      message = `服务器错误(${error.response.status}): ${error.response.data?.message || '未知错误'}`;
    } else if (error.request) {
      message = '网络错误：无法连接到服务器';
    } else {
      message = `请求失败：${error.message}`;
    }
    return { success: false, message };
  }
}

// 修改课程中的实验
export async function modifyExperimentInCourse(modifyDto) {
  try {
    const response = await axios.put(`${BASE_URL}/experiment/modify`, modifyDto);

    if (response.status === 200) {
      const data = response.data;
      if (data.code === 200) {
        return {
          success: true,
          message: data.data || '修改成功'
        };
      } else {
        return {
          success: false,
          message: data.message || '修改失败'
        };
      }
    }
    return {
      success: false,
      message: '修改失败'
    };
  } catch (error) {
    console.error('修改实验错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '修改实验失败'
    };
  }
}