package com.exercise.calendar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalendarTestEqualClass {

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
        if (year < 1582) {
            throw new IllegalArgumentException("年份不合法，应大于等于1582");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("月份不合法，应为1~12之间的整数");
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
        assertEquals(28, getDaysInMonth(1900, 2)); // 平年
    }

    @Test
    void test_UT_TC_CAL_003() {
        assertEquals(29, getDaysInMonth(2000, 2)); // 闰年
    }

    @Test
    void test_UT_TC_CAL_004() {
        assertEquals(29, getDaysInMonth(2004, 2)); // 闰年
    }

    @Test
    void test_UT_TC_CAL_005() {
        assertEquals(28, getDaysInMonth(2100, 2)); // 平年
    }

    @Test
    void test_UT_TC_CAL_006() {
        assertEquals(30, getDaysInMonth(2020, 4)); // 小月
    }

    @Test
    void test_UT_TC_CAL_007() {
        assertEquals(31, getDaysInMonth(2020, 12)); // 大月
    }

    // ======================= 等价类分析法测试用例 =======================

    @Test
    void test_UT_TC_CAL_EQC_001() {
        assertEquals(29, getDaysInMonth(2020, 2)); // 闰年2月
    }

    @Test
    void test_UT_TC_CAL_EQC_002() {
        assertEquals(28, getDaysInMonth(2021, 2)); // 平年2月
    }

    @Test
    void test_UT_TC_CAL_EQC_003() {
        assertEquals(30, getDaysInMonth(2020, 4)); // 小月
    }

    @Test
    void test_UT_TC_CAL_EQC_004() {
        assertEquals(31, getDaysInMonth(2020, 12)); // 大月
    }

    @Test
    void test_UT_TC_CAL_EQC_005() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> getDaysInMonth(2020, 0));
        assertEquals("月份不合法，应为1~12之间的整数", e.getMessage());
    }

    @Test
    void test_UT_TC_CAL_EQC_006() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> getDaysInMonth(-1, 1));
        assertEquals("年份不合法，应大于等于1582", e.getMessage());
    }
}
