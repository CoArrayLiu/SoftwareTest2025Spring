package com.backend.vse.tools;

public class AccountUtil {
    /**
     * 判断输入的账号是邮箱还是学工号
     * @param account 输入的账号
     * @return AccountType 账号类型枚举
     */
    public static AccountType getAccountType(String account) {
        if (account == null || account.trim().isEmpty()) {
            throw new IllegalArgumentException("账号不能为空");
        }
        return account.contains("@") ? AccountType.EMAIL : AccountType.STUDENT_STAFF_ID;
    }

    /**
     * 验证输入的账号格式是否正确
     * @param account 输入的账号
     * @return boolean 是否有效
     */
    public static boolean isValidAccount(String account) {
        if (account == null || account.trim().isEmpty()) {
            return false;
        }

        AccountType type = getAccountType(account);

        if (type == AccountType.EMAIL) {
            // 简单的邮箱格式验证
            return account.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        } else {
            // 学工号为5位或7位数字
            return account.matches("^\\d{5}$|^\\d{7}$");
        }
    }

    /**
     * 账号类型枚举
     */
    public enum AccountType {
        EMAIL,           // 邮箱
        STUDENT_STAFF_ID // 学工号
    }
}
