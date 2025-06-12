package com.exercise.calendar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeapYearEquivalenceTest {

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
}
