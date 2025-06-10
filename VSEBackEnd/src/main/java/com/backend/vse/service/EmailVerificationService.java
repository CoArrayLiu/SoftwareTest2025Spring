package com.backend.vse.service;

public interface EmailVerificationService {
    /**
     * 发送验证代码到指定邮箱
     * 该方法生成一个验证码，并将其发送到指定的邮箱地址。通常用于用户注册、密码重置等操作中的邮箱验证。
     *
     * @param email 目标邮箱地址，接收验证码的邮箱（String 类型）
     * @return 如果验证码发送成功，返回 true；如果发送失败，返回 false（boolean 类型）
     */
    boolean sendVerificationCode(String email);

    /**
     * 验证输入的验证码是否正确
     * 该方法用于验证用户输入的验证码是否与发送到邮箱的验证码匹配。通常用于验证用户在操作时输入的验证码是否有效。
     *
     * @param email 目标邮箱地址，用于查找发送的验证码（String 类型）
     * @param code 用户输入的验证码（String 类型）
     * @return 如果验证码匹配，返回 true；否则返回 false（boolean 类型）
     */
    boolean verifyCode(String email, String code);
}