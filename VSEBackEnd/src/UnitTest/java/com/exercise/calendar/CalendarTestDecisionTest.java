package com.exercise.calendar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalendarTestDecisionTest {


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


    @Test
    void test_UT_TC_CAL_DT_001() {
        // 非闰年（不能被4整除）：2021年2月 → 28天
        assertEquals(28, getDaysInMonth(2021, 2));
    }

    @Test
    void test_UT_TC_CAL_DT_002() {
        // 闰年（被4整除但不被100整除）：2004年2月 → 29天
        assertEquals(29, getDaysInMonth(2004, 2));
    }

    @Test
    void test_UT_TC_CAL_DT_003() {
        // 特殊闰年（被400整除）：2000年2月 → 29天
        assertEquals(29, getDaysInMonth(2000, 2));
    }

    @Test
    void test_UT_TC_CAL_DT_004() {
        // 世纪非闰年（被100整除但不被400整除）：1900年2月 → 28天
        assertEquals(28, getDaysInMonth(1900, 2));
    }
}
