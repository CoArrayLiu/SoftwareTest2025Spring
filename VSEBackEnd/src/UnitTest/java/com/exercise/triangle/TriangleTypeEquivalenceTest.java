package com.exercise.triangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TriangleTypeEquivalenceTest {

    @Test
    public void testInvalidTriangle() {
        // 无效三角形
        assertEquals("Invalid", TriangleType.getTriangleType(0, 0, 0));
        assertEquals("Invalid", TriangleType.getTriangleType(-1, 2, 3));
        assertEquals("Invalid", TriangleType.getTriangleType(1, 2, -1));
    }

    @Test
    public void testNotATriangle() {
        // 不构成三角形
        assertEquals("Not a Triangle", TriangleType.getTriangleType(1, 2, 3));
        assertEquals("Not a Triangle", TriangleType.getTriangleType(1, 1, 2));
    }

    @Test
    public void testEquilateralTriangle() {
        // 等边三角形
        assertEquals("Equilateral", TriangleType.getTriangleType(2, 2, 2));
    }

    @Test
    public void testIsoscelesTriangle() {
        // 等腰三角形
        assertEquals("Isosceles", TriangleType.getTriangleType(3, 3, 5));
    }

    @Test
    public void testScaleneTriangle() {
        // 普通三角形
        assertEquals("Scalene", TriangleType.getTriangleType(3, 4, 5));
    }
}
