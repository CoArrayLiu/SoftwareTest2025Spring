package com.exercise.triangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTestBoundary {

    /**
     * 判断三角形类型
     * @param a 边 a
     * @param b 边 b
     * @param c 边 c
     * @return 三角形类型：等边三角形、等腰三角形、不等边三角形、非法三角形
     */
    public static String classifyTriangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) {
            return "非法三角形";
        }

        if (a == b && b == c) {
            return "等边三角形";
        } else if (a == b || b == c || a == c) {
            return "等腰三角形";
        } else {
            return "不等边三角形";
        }
    }

    // ======================= JUnit 测试用例 =======================

    @Test
    void test_UT_TC_TRI_001() {
        assertEquals("等边三角形", classifyTriangle(1, 1, 1));
    }

    @Test
    void test_UT_TC_TRI_002() {
        assertEquals("非法三角形", classifyTriangle(1, 1, 199));
    }

    @Test
    void test_UT_TC_TRI_003() {
        assertEquals("非法三角形", classifyTriangle(1, 1, 200));
    }

    @Test
    void test_UT_TC_TRI_004() {
        assertEquals("等边三角形", classifyTriangle(200, 200, 200));
    }

    @Test
    void test_UT_TC_TRI_005() {
        assertEquals("非法三角形", classifyTriangle(100, 99, 1));
    }

    @Test
    void test_UT_TC_TRI_006() {
        assertEquals("等腰三角形", classifyTriangle(2, 2, 3));
    }
}
