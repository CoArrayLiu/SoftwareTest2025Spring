package com.exercise.triangle;

public class TriangleType {

    // 判断三角形的类型
    public static String getTriangleType(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            return "Invalid";  // 无效的三角形
        }

        if (a + b <= c || a + c <= b || b + c <= a) {
            return "Not a Triangle";  // 不构成三角形
        }

        if (a == b && b == c) {
            return "Equilateral";  // 等边三角形
        } else if (a == b || b == c || a == c) {
            return "Isosceles";  // 等腰三角形
        } else {
            return "Scalene";  // 普通三角形
        }
    }

    public static void main(String[] args) {
        System.out.println(getTriangleType(3, 3, 3)); // Equilateral
        System.out.println(getTriangleType(3, 4, 5)); // Scalene
        System.out.println(getTriangleType(2, 2, 5)); // Not a Triangle
        System.out.println(getTriangleType(0, 3, 3)); // Invalid
    }
}
