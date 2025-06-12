package com.exercise.triangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TriangleTypeBoundaryTest {

    @Test
    public void testInvalidSideZero() {
        assertEquals("Invalid", TriangleType.getTriangleType(0, 3, 4));
        assertEquals("Invalid", TriangleType.getTriangleType(3, 0, 4));
        assertEquals("Invalid", TriangleType.getTriangleType(3, 4, 0));
    }

    @Test
    public void testNegativeSide() {
        assertEquals("Invalid", TriangleType.getTriangleType(-1, 3, 4));
        assertEquals("Invalid", TriangleType.getTriangleType(3, -1, 4));
        assertEquals("Invalid", TriangleType.getTriangleType(3, 4, -1));
    }

    @Test
    public void testNotTriangleBoundary() {
        // 边长之和等于第三边，应该返回“Not a Triangle”
        assertEquals("Not a Triangle", TriangleType.getTriangleType(3, 4, 7));
        assertEquals("Not a Triangle", TriangleType.getTriangleType(1, 1, 2));
    }

    @Test
    public void testEquilateralTriangle() {
        // 等边三角形，所有边相等
        assertEquals("Equilateral", TriangleType.getTriangleType(5, 5, 5));
    }

    @Test
    public void testScaleneTriangle() {
        // 普通三角形，三边都不相等
        assertEquals("Scalene", TriangleType.getTriangleType(3, 4, 5));
    }

    @Test
    public void testIsoscelesTriangle() {
        // 等腰三角形，两个边相等
        assertEquals("Isosceles", TriangleType.getTriangleType(3, 3, 5));
    }
}

