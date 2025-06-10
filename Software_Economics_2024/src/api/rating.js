import axios from "axios";

const API_BASE_URL = "http://localhost:8002/api";

// 获取课程中实验信息
export async function fetchExperiments(courseId) {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.get(
      `${API_BASE_URL}/courses/${courseId}/experiments`,
      {
        headers: {
          Authorization: token ? `Bearer ${token}` : ''
        }
      }
    );
    if (response.data.success) {
      return { success: true, experiments: response.data.data };
    } else {
      return { success: false, message: response.data.msg };
    }
  } catch (error) {
    return { success: false, message: error.message };
  }
}

// 获取课程中学生信息
export async function fetchStudents(courseId) {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.get(`${API_BASE_URL}/courses/${courseId}/students`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });
    if (response.data.success) {
      return { success: true, students: response.data.data };
    } else {
      return { success: false, message: response.data.msg };
    }
  } catch (error) {
    return { success: false, message: error.message };
  }
}

// 获取学生实验成绩
export async function fetchStudentScores(courseId) {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.get(
      `${API_BASE_URL}/courses/${courseId}/scores/experiments`,
      { 
        headers: {
          Authorization: token ? `Bearer ${token}` : ''
        }
      }
    );
    if (response.data.success) {
      return { success: true, scores: response.data.data };
    } else {
      return { success: false, message: response.data.msg };
    }
  } catch (error) {
    return { success: false, message: error.message };
  }
}

// 获取学生的报告记录
export async function fetchStudentReports(studentIndex) {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/report/${studentIndex}/records`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`, // 确保使用有效的 token
        },
      }
    );
    if (response.data.success) {
      return { success: true, reports: response.data.data };
    } else {
      return { success: false, message: response.data.msg };
    }
  } catch (error) {
    return { success: false, message: error.message };
  }
}

// 提交学生实验分数
export async function submitExperimentScore(reportId, score) {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.put(
      `${API_BASE_URL}/reports/${reportId}/score`,
      { score },
      {
        headers: {
          Authorization: token ? `Bearer ${token}` : ''
        }
      }
    );
    return response.data;
  } catch (error) {
    console.error("提交实验分数时发生错误:", error);
    return { success: false, message: error.message };
  }
}

// 获取学生最新实验报告接口
export async function fetchReport(studentIndex, courseId, experimentId) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      return { success: false, message: "未找到token，请先登录" };
    }

    console.log(`开始下载学生${studentIndex}课程${courseId}实验${experimentId}报告`);

    const response = await fetch(
      `${API_BASE_URL}/reports/students/${studentIndex}/latest?courseId=${courseId}&experimentId=${experimentId}`,
      {
        method: 'GET',
        headers: {
          'Accept': '*/*',
          'Authorization': `Bearer ${token}`,
        }
      }
    );

    if (!response.ok) {
      throw new Error('Network response was not ok ' + response.statusText);
    }

    console.log('报告下载响应:', response);
    console.log('响应头:', [...response.headers.entries()]);

    const contentDisposition = response.headers.get('content-disposition');
    let filename = 'Report.pdf';
    if (contentDisposition) {
      const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
      if (filenameMatch && filenameMatch[1]) {
        filename = filenameMatch[1].replace(/['"]/g, '');
      }
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    console.log(`报告下载成功，文件名: ${filename}, 大小: ${blob.size} bytes`);

    return { success: true, reportUrl: url, filename };
  } catch (error) {
    console.error("下载报告失败：", error);
    return { success: false, message: error.message };
  }
}

// 获取报告ID
export async function fetchReportId(studentIndex, courseId, experimentId) {
  try {
    const token = localStorage.getItem('token');
    // 查询该学生的所有报告记录
    const response = await axios.get(
      `${API_BASE_URL}/reports/students/${studentIndex}`,
      {
        headers: {
          Authorization: token ? `Bearer ${token}` : ''
        }
      }
    );
    
    if (response.data.success) {
      const reports = response.data.data;
      
      // 调试日志
      console.log(`学生${studentIndex}的报告记录:`, reports);
      
      if (Array.isArray(reports)) {
        // 先筛选出匹配实验ID的报告，然后按reportId降序排序
        const matchingReports = reports
          .filter(report => {
            if (report.reportContent) {
              const match = report.reportContent.match(/report_(\d+)_/);
              return match && String(match[1]) === String(experimentId);
            }
            return false;
          })
          .sort((a, b) => b.reportId - a.reportId); // 按reportId降序排序

        // 获取reportId最大的报告
        const latestReport = matchingReports[0];
        
        if (latestReport) {
          console.log(`找到最新的报告(reportId最大):`, latestReport);
          return { success: true, reportId: latestReport.reportId };
        }
      }
      
      console.log(`未找到学生${studentIndex}实验${experimentId}的报告`);
      return { success: false, message: "未找到匹配的报告记录" };
    } else {
      return { success: false, message: response.data.msg || "获取报告记录失败" };
    }
  } catch (error) {
    console.error("获取报告ID失败：", error);
    return { success: false, message: error.message };
  }
}

// 获取报告状态
export async function fetchReportStatus(reportId) {
  try {
    const token = localStorage.getItem('token');
    const response = await axios.get(
      `${API_BASE_URL}/reports/${reportId}/status`,
      {
        headers: {
          Authorization: token ? `Bearer ${token}` : ''
        }
      }
    );
    
    // 调试日志
    console.log(`报告${reportId}状态响应:`, response.data);
    
    if (response.data.success) {
      return { success: true, status: response.data.data };
    } else {
      return { success: false, message: response.data.msg || "获取报告状态失败" };
    }
  } catch (error) {
    console.error("获取报告状态失败：", error);
    return { success: false, message: error.message };
  }
}
