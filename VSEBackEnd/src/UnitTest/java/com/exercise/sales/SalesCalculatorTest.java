package com.exercise.sales;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalesCalculatorTest {

    // ===== 基本边界值测试（13个） =====
    @Test void test_TC1() { assertEquals(650.0, SalesCalculator.calculateCommission(1, 40, 45), 0.01); }
    @Test void test_TC2() { assertEquals(655.0, SalesCalculator.calculateCommission(2, 40, 45), 0.01); }
    @Test void test_TC3() { assertEquals(820.0, SalesCalculator.calculateCommission(35, 40, 45), 0.01); }
    @Test void test_TC4() { assertEquals(990.0, SalesCalculator.calculateCommission(69, 40, 45), 0.01); }
    @Test void test_TC5() { assertEquals(995.0, SalesCalculator.calculateCommission(70, 40, 45), 0.01); }

    @Test void test_TC6() { assertEquals(586.0, SalesCalculator.calculateCommission(35, 1, 45), 0.01); }
    @Test void test_TC7() { assertEquals(592.0, SalesCalculator.calculateCommission(35, 2, 45), 0.01); }
    @Test void test_TC8() { assertEquals(1054.0, SalesCalculator.calculateCommission(35, 79, 45), 0.01); }
    @Test void test_TC9() { assertEquals(1060.0, SalesCalculator.calculateCommission(35, 80, 45), 0.01); }

    @Test void test_TC10() { assertEquals(424.0, SalesCalculator.calculateCommission(35, 40, 1), 0.01); }
    @Test void test_TC11() { assertEquals(433.0, SalesCalculator.calculateCommission(35, 40, 2), 0.01); }
    @Test void test_TC12() { assertEquals(1216.0, SalesCalculator.calculateCommission(35, 40, 89), 0.01); }
    @Test void test_TC13() { assertEquals(1225.0, SalesCalculator.calculateCommission(35, 40, 90), 0.01); }

    // ===== 提佣金边界专项测试（8个） =====
    @Test void test_TCB1() { assertEquals(172.5, SalesCalculator.calculateCommission(25, 10, 5), 0.01); }
    @Test void test_TCB2() { assertEquals(161.25, SalesCalculator.calculateCommission(40, 1, 1), 0.01); }
    @Test void test_TCB3() { assertEquals(161.25, SalesCalculator.calculateCommission(40, 1, 1), 0.01); } // same as TCB2, boundary
    @Test void test_TCB4() { assertEquals(157.5, SalesCalculator.calculateCommission(39, 1, 1), 0.01); }
    @Test void test_TCB5() { assertEquals(153.75, SalesCalculator.calculateCommission(38, 1, 1), 0.01); }
    @Test void test_TCB6() { assertEquals(500.00, SalesCalculator.calculateCommission(40, 20, 20), 0.01); }
    @Test void test_TCB7() { assertEquals(509.0, SalesCalculator.calculateCommission(40, 20, 21), 0.01); }
    @Test void test_TCB8() { assertEquals(494.0, SalesCalculator.calculateCommission(40, 19, 20), 0.01); }

    // ===== 健壮性测试（异常输入，6个） =====
    @Test void test_R1() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(0, 40, 45));
        assertTrue(e.getMessage().contains("主机"));
    }

    @Test void test_R2() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(71, 40, 45));
        assertTrue(e.getMessage().contains("主机"));
    }

    @Test void test_R3() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(35, 0, 45));
        assertTrue(e.getMessage().contains("显示器"));
    }

    @Test void test_R4() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(35, 81, 45));
        assertTrue(e.getMessage().contains("显示器"));
    }

    @Test void test_R5() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(35, 40, 0));
        assertTrue(e.getMessage().contains("外设"));
    }

    @Test void test_R6() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                SalesCalculator.calculateCommission(35, 40, 91));
        assertTrue(e.getMessage().contains("外设"));
    }
}
