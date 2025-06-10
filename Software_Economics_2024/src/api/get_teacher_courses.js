import axios from 'axios';

const API_URL = "http://localhost:8002/api/courses/teaching";
const ALL_COURSES_URL = "http://localhost:8002/api/courses"; // 新增获取所有课程的接口

export async function fetchCourses() {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 添加认证头
    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    };
    
    const response = await axios.get(API_URL, config);

    if (response.status === 200) {
      const data = response.data;

      if (data.code === 200) {
        return { success: true, courses: data.data };
      } else {
        console.log("API返回错误:", data); // 添加调试日志
        return { success: false, message: getErrorMessage(data.code, data.message) };
      }
    } else {
      return { success: false, message: `服务端返回非预期状态码：${response.status}` };
    }
  } catch (error) {
    console.error("API请求错误:", error); // 添加调试日志
    if (error.response) {
      return { success: false, message: `服务端错误（${error.response.status}）：${error.response.data.message || "未知错误"}` };
    } else if (error.request) {
      return { success: false, message: "网络错误：无法连接到服务器" };
    } else {
      return { success: false, message: "请求失败：" + error.message };
    }
  }
}

// 新增：获取所有课程的函数，给责任教师使用
export async function fetchAllCourses() {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 添加认证头
    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    };
    
    const response = await axios.get(ALL_COURSES_URL, config);

    if (response.status === 200) {
      const data = response.data;

      if (data.code === 200) {
        return { success: true, courses: data.data };
      } else {
        console.log("获取所有课程API返回错误:", data);
        return { success: false, message: getErrorMessage(data.code, data.message) };
      }
    } else {
      return { success: false, message: `服务端返回非预期状态码：${response.status}` };
    }
  } catch (error) {
    console.error("获取所有课程API请求错误:", error);
    if (error.response) {
      return { success: false, message: `服务端错误（${error.response.status}）：${error.response.data.message || "未知错误"}` };
    } else if (error.request) {
      return { success: false, message: "网络错误：无法连接到服务器" };
    } else {
      return { success: false, message: "请求失败：" + error.message };
    }
  }
}

export async function createCourse(courseData) {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 确保数据格式符合API要求
    const formattedData = {
      courseId: courseData.courseId,
      courseName: courseData.courseName,
      semester: courseData.semester,
      year: courseData.year,
      teacherList: courseData.teacherList || [],
      studentList: courseData.studentList || []
    };
    
    // 添加认证头
    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : '',
        'Content-Type': 'application/json'
      }
    };
    
    console.log('API提交数据:', JSON.stringify(formattedData));
    
    const response = await axios.post('http://localhost:8002/api/courses', formattedData, config);

    if (response.status === 200) {
      const data = response.data;

      if (data.success) {
        return { success: true, data: data.data, message: data.msg || '创建课程成功' };
      } else {
        console.log("API返回错误:", data);
        return { success: false, message: data.msg || getErrorMessage(data.code, '') };
      }
    } else {
      return { success: false, message: `服务端返回非预期状态码：${response.status}` };
    }
  } catch (error) {
    console.error("API请求错误:", error);
    if (error.response) {
      // 尝试获取详细的错误信息
      const errorMsg = error.response.data?.msg || error.response.data?.message || error.response.data?.error || "未知错误";
      return { success: false, message: `服务端错误（${error.response.status}）：${errorMsg}` };
    } else if (error.request) {
      return { success: false, message: "网络错误：无法连接到服务器" };
    } else {
      return { success: false, message: "请求失败：" + error.message };
    }
  }
}

export async function getTeachers() {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 添加认证头
    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    };
    
    const response = await axios.get('http://localhost:8002/api/users/teachers', config);

    if (response.status === 200) {
      const data = response.data;

      if (data.success) {
        return { success: true, teachers: data.data };
      } else {
        console.log("API返回错误:", data);
        return { success: false, message: data.msg || getErrorMessage(data.code, '') };
      }
    } else {
      return { success: false, message: `服务端返回非预期状态码：${response.status}` };
    }
  } catch (error) {
    console.error("API请求错误:", error);
    if (error.response) {
      return { success: false, message: `服务端错误（${error.response.status}）：${error.response.data.msg || "未知错误"}` };
    } else if (error.request) {
      return { success: false, message: "网络错误：无法连接到服务器" };
    } else {
      return { success: false, message: "请求失败：" + error.message };
    }
  }
}

export async function getStudents() {
  try {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    
    // 添加认证头
    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    };
    
    const response = await axios.get('http://localhost:8002/api/users/students', config);

    if (response.status === 200) {
      const data = response.data;

      if (data.success) {
        return { success: true, students: data.data };
      } else {
        console.log("API返回错误:", data);
        return { success: false, message: data.msg || getErrorMessage(data.code, '') };
      }
    } else {
      return { success: false, message: `服务端返回非预期状态码：${response.status}` };
    }
  } catch (error) {
    console.error("API请求错误:", error);
    if (error.response) {
      return { success: false, message: `服务端错误（${error.response.status}）：${error.response.data.msg || "未知错误"}` };
    } else if (error.request) {
      return { success: false, message: "网络错误：无法连接到服务器" };
    } else {
      return { success: false, message: "请求失败：" + error.message };
    }
  }
}

function getErrorMessage(code, message) {
  const errorMessages = {
    10006: "教师ID无效或不能为空",
    10008: "教师不存在",
    10009: "查询课程数据时发生错误",
    10010: "该教师暂无课程",
    10011: "课程列表排序时发生错误",
    401: "未授权，请重新登录",
    403: "没有访问权限",
    404: "请求的资源不存在",
    500: "服务器内部错误"
  };
  
  // 如果有消息则直接显示
  if (message) {
    return message;
  }
  
  return errorMessages[code] || `未知错误 (错误码: ${code})`;
}
