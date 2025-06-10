<template>
  <div ref="chartContainer" style="width: 100%; height: 500px;"></div>
</template>

<script>
import { defineComponent, ref, onMounted, onBeforeUnmount, watch } from "vue";
import * as echarts from "echarts";

export default defineComponent({
  name: "DemandSupplyChart",
  props: {
    demandprice: {
      type: Array,
      required: true, // 需求曲线的数据
    },
    supplyprice: {
      type: Array,
      required: true, // 供应曲线的数据
    },
  },
  emits: ["intersection-found"], // 声明自定义事件
  setup(props,{ expose,emit }) {
    const chartContainer = ref(null);
    let chartInstance = null;

    // 计算需求曲线（价格从高到低）
    const calculateDemandCurve = () => {
      const sortedData = [...props.demandprice].sort((a, b) => b[0] - a[0]); // 按价格从高到低排序
      let cumulativeDemand = 0;
      const demandPoints = sortedData.map(([price, count]) => {
        cumulativeDemand += count;
        return [cumulativeDemand, price]; // x 为需求，y 为价格
      });
      return demandPoints;
    };

    // 计算供应曲线（价格从低到高）
    const calculateSupplyCurve = () => {
      const sortedData = [...props.supplyprice].sort((a, b) => a[0] - b[0]); // 按价格从低到高排序
      let cumulativeSupply = 0;
      const supplyPoints = sortedData.map(([price, count]) => {
        cumulativeSupply += count;
        return [cumulativeSupply, price]; // x 为供应，y 为价格
      });
      return supplyPoints;
    };

    // 通过线性插值计算给定整数 x 处的 y 值
    const interpolate = (x, points) => {
      for (let i = 0; i < points.length - 1; i++) {
        const [x1, y1] = points[i];
        const [x2, y2] = points[i + 1];
        if (x >= x1 && x <= x2) {
          const t = (x - x1) / (x2 - x1);
          return y1 + t * (y2 - y1); // 线性插值
        }
      }
      return null;
    };

    // 查找需求曲线和供应曲线的交点（x 值为整数）
    const findIntersection = (demandPoints, supplyPoints) => {
      const minX = Math.max(demandPoints[0][0], supplyPoints[0][0]);
      const maxX = Math.min(demandPoints[demandPoints.length - 1][0], supplyPoints[supplyPoints.length - 1][0]);

      // console.log(minX)
      // console.log(maxX)

      // console.log(demandPoints)
      // console.log(supplyPoints)


    
      for (let x = Math.ceil(minX); x <= Math.floor(maxX); x+=0.001) {
        const yDemand = interpolate(x, demandPoints);
        const ySupply = interpolate(x, supplyPoints);

        if (yDemand !== null && ySupply !== null && Math.abs(yDemand - ySupply) < 0.1) {
          
          const resultX=Math.floor(x);
          const resultY=(interpolate(resultX, demandPoints)+interpolate(resultX, supplyPoints))/2;
          console.log([resultX, resultY])
          
          // 计算 buybest 和 sellbest
          const buybestvalue = Math.max(...demandPoints.filter(([x]) => x <= resultX).map(([x]) => x));
          const sellbestvalue = Math.max(...supplyPoints.filter(([x]) => x <= resultX).map(([x]) => x));
          
          // 通过事件传递交点、买入和卖出最佳数量
          emit("intersection-found", { resultX, resultY, buybestvalue, sellbestvalue });
          
          return [resultX, resultY]; // 找到交点
        }
      }
      return null;
    };

    // 初始化图表
    const initChart = () => {
      if (chartContainer.value) {
        chartInstance = echarts.init(chartContainer.value);
        setChartOptions();
      }
    };

    // 设置图表选项
    const setChartOptions = () => {
      const demandPoints = calculateDemandCurve();
      const supplyPoints = calculateSupplyCurve();
      const intersection = findIntersection(demandPoints, supplyPoints);

      // 找到需求曲线和供应曲线在交点左边的点并标注乘积
      const annotatePoints = (points, intersectionX) => {
        return points
          .filter(([x]) => x <= intersectionX)
          .map(([x, y]) => ({
            coord: [x, y],
            value: `乘积: ${(x * y).toFixed(2)}`,
            label: {
              show: true,
              formatter: `乘积: ${(x * y).toFixed(2)}`,
              position: "top",
              color: "#000",
              fontSize: 10,
              
            },
            symbolSize: 0
          }));
      };

      const demandAnnotations = intersection
        ? annotatePoints(demandPoints, intersection[0])
        : [];
      const supplyAnnotations = intersection
        ? annotatePoints(supplyPoints, intersection[0])
        : [];

      const options = {
        title: {
          text: "需求-供应曲线",
        },
        xAxis: {
          type: "value",
          name: "需求",
          min: 0,
        },
        yAxis: {
          type: "value",
          name: "价格",
          min: 0,
        },
        series: [
          {
            name: "需求曲线",
            data: demandPoints,
            type: "line",
            smooth: false,
            lineStyle: {
              color: "#2196F3",
            },
            markPoint: {
              data: demandAnnotations,
            },
          },
          {
            name: "供应曲线",
            data: supplyPoints,
            type: "line",
            smooth: false,
            lineStyle: {
              color: "#FF5722",
            },
            markPoint: {
              data: supplyAnnotations,
            },
          },
          intersection
            ? {
                name: "平衡点",
                data: [intersection],
                type: "scatter",
                symbolSize: 10,
                symbol: 'triangle',
                itemStyle: {
                  color: "#000F50",
                },
                label: {
                  show: true,
                  formatter: `平衡点: (${intersection[0]}, ${intersection[1].toFixed(2)})`,
                  position: "top",
                },
              }
            : null,
        ].filter(Boolean),
      };

      if (chartInstance) {
        chartInstance.setOption(options);
      }
    };

    const getChartImage = () => {
      if (!chartInstance) return null;
      return chartInstance.getDataURL({
        type: "png",
        pixelRatio: 2,
        backgroundColor: "#fff",
      });
    };

    // 监听数据的变化
    watch(() => [props.demandprice, props.supplyprice], setChartOptions);

    // 组件挂载时初始化图表
    onMounted(initChart);

    // 组件卸载时销毁图表
    onBeforeUnmount(() => {
      if (chartInstance) {
        chartInstance.dispose();
      }
    });

    // 暴露方法供父组件调用
    expose({
      getChartImage,
    });

    return { chartContainer };
  },
});
</script>

<style scoped>
/* 样式 */
</style>
