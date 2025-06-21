package com.exercise.calendar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarTestBoundary {

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 获取某年某月的天数
     */
    public static int getDaysInMonth(int year, int month) {
        if (month < 1 || month > 12 || year < 1900 || year > 2100) {
            throw new IllegalArgumentException("年份或月份不合法");
        }

        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    // ======================= 边界值分析法测试用例 =======================

    @Test
    void test_UT_TC_CAL_001() {
        assertEquals(31, getDaysInMonth(1900, 1));
    }

    @Test
    void test_UT_TC_CAL_002() {
        assertEquals(28, getDaysInMonth(1900, 2)); // 1900 是平年
    }

    @Test
    void test_UT_TC_CAL_003() {
        assertEquals(29, getDaysInMonth(2000, 2)); // 2000 是闰年
    }

    @Test
    void test_UT_TC_CAL_004() {
        assertEquals(29, getDaysInMonth(2004, 2)); // 2004 是闰年
    }

    @Test
    void test_UT_TC_CAL_005() {
        assertEquals(28, getDaysInMonth(2100, 2)); // 2100 是平年
    }

    @Test
    void test_UT_TC_CAL_006() {
        assertEquals(30, getDaysInMonth(2020, 4)); // 4月30天
    }

    @Test
    void test_UT_TC_CAL_007() {
        assertEquals(31, getDaysInMonth(2020, 12)); // 12月31天
    }
}
