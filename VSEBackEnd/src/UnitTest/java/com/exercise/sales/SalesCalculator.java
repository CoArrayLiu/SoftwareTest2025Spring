package com.exercise.sales;

public class SalesCalculator {

    public static double UNIT_HOST = 25.0;
    public static double UNIT_MONITOR = 30.0;
    public static double UNIT_PERIPHERAL = 45.0;

    public static int MAX_HOST = 70;
    public static int MAX_MONITOR = 80;
    public static int MAX_PERIPHERAL = 90;

    public static double calculateCommission(int hosts, int monitors, int peripherals) {
        // 校验输入合法性
        if (hosts < 1 || hosts > MAX_HOST)
            throw new IllegalArgumentException("主机数量不合法（1-70）");
        if (monitors < 1 || monitors > MAX_MONITOR)
            throw new IllegalArgumentException("显示器数量不合法（1-80）");
        if (peripherals < 1 || peripherals > MAX_PERIPHERAL)
            throw new IllegalArgumentException("外设数量不合法（1-90）");

        // 计算销售额
        double total = hosts * UNIT_HOST + monitors * UNIT_MONITOR + peripherals * UNIT_PERIPHERAL;

        // 计算佣金
        double rate;
        if (total <= 1000) {
            rate = 0.10;
        } else if (total <= 1800) {
            rate = 0.15;
        } else {
            rate = 0.20;
        }

        return total * rate;
    }
}
