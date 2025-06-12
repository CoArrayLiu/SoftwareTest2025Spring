package com.exercise.calendar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeapYearBoundaryTest {

    @Test
    public void testNotLeapYear() {
        // 非闰年：不能被4整除
        assertFalse(LeapYear.isLeapYear(2017));  // 2017
        // 非闰年：是100的倍数但不能被400整除
        assertFalse(LeapYear.isLeapYear(1900));  // 1900
    }

    @Test
    public void testLeapYear() {
        // 闰年：能被4整除但不能被100整除
        assertTrue(LeapYear.isLeapYear(2016));  // 2016
        // 闰年：能被400整除
        assertTrue(LeapYear.isLeapYear(2000));  // 2000
    }

    @Test
    public void testBoundaryYear() {
        // 边界测试：被4整除但不能被100整除
        assertTrue(LeapYear.isLeapYear(4));  // 4
        // 边界测试：是100的倍数但不是400的倍数
        assertFalse(LeapYear.isLeapYear(100));  // 100
        // 边界测试：被400整除
        assertTrue(LeapYear.isLeapYear(400));  // 400
    }
}