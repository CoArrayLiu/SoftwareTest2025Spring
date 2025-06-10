import Mock from 'mockjs';



const quotesTable = [
    { studentId: 1, courseId: 1, quoteType: "BUY", price: 100 },
    { studentId: 2, courseId: 1, quoteType: "BUY", price: 70 },
    { studentId: 3, courseId: 1, quoteType: "BUY", price: 10 },
    { studentId: 4, courseId: 1, quoteType: "BUY", price: 50 },
    { studentId: 5, courseId: 1, quoteType: "BUY", price: 100 },
    { studentId: 6, courseId: 1, quoteType: "BUY", price: 40 },
    { studentId: 7, courseId: 1, quoteType: "BUY", price: 50 },
    { studentId: 8, courseId: 1, quoteType: "BUY", price: 30 },
    { studentId: 9, courseId: 1, quoteType: "BUY", price: 70 },
    { studentId: 10, courseId: 1, quoteType: "BUY", price: 60 },

    { studentId: 1, courseId: 1, quoteType: "SELL", price: 110 },
    { studentId: 2, courseId: 1, quoteType: "SELL", price: 20 },
    { studentId: 3, courseId: 1, quoteType: "SELL", price: 50 },
    { studentId: 4, courseId: 1, quoteType: "SELL", price: 70 },
    { studentId: 5, courseId: 1, quoteType: "SELL", price: 80 },
    { studentId: 6, courseId: 1, quoteType: "SELL", price: 80 },
    { studentId: 7, courseId: 1, quoteType: "SELL", price: 10 },
    { studentId: 8, courseId: 1, quoteType: "SELL", price: 50 },
    { studentId: 9, courseId: 1, quoteType: "SELL", price: 40 },
    { studentId: 10, courseId: 1, quoteType: "SELL", price: 60 },
  ];
  




Mock.mock("/api/exp6/addrecord", "post", (options) => {
    // 解析请求体数据
    const body = JSON.parse(options.body);
    const { studentId, courseId, quoteType, price } = body;
  
    // 检查主码是否已存在
    const exists = quotesTable.some(
      (record) => record.studentId == studentId && record.courseId == courseId
    );
  
    if (exists) {
      return {
        status: "error",
        message: "Record already exists.",
      };
    }
  
    // 添加记录
    quotesTable.push({ studentId, courseId, quoteType, price });
  
    return {
      status: "success",
      message: "Record added successfully.",
      data: quotesTable,
    };
  });


Mock.mock('/api/exp6/datatreat', 'get', () => {
    // 统计BUY类型的记录
    const buyRecords = quotesTable.filter((record) => record.quoteType == "BUY");
    const demandData = getPriceCount(buyRecords, "desc");

    // 统计SELL类型的记录
    const sellRecords = quotesTable.filter((record) => record.quoteType == "SELL");
    const supplyData = getPriceCount(sellRecords, "asc");
  
    return {
        demandData,
        supplyData
    };
});
  
  // 获取价格和人数的统计，并按价格排序
  function getPriceCount(records, order) {
    const priceCount = {};
  
    // 统计每个价格对应的人数
    records.forEach((record) => {
      if (!priceCount[record.price]) {
        priceCount[record.price] = 0;
      }
      priceCount[record.price]++;
    });
  
    // 将统计结果转换为 [[price, count], [price, count], ...] 形式
    let result = Object.entries(priceCount).map(([price, count]) => [
      parseInt(price), // 转为整数
      count
    ]);
  
    // 根据需求排序
    if (order === "desc") {
      result.sort((a, b) => b[0] - a[0]); // 按价格降序排列
    } else if (order === "asc") {
      result.sort((a, b) => a[0] - b[0]); // 按价格升序排列
    }
  
    return result;
  }