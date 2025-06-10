package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.config.EmailSendConfig;

import com.backend.vse.exception.SystemException;
import com.backend.vse.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class EmailVerificationServiceImpl implements EmailVerificationService {
   
    private final EmailSendConfig emailProperties;
    private final StringRedisTemplate redisTemplate;

    // 验证码过期时间（分钟）
    private static final long CODE_EXPIRE_MINUTES = 10;
    // Redis key 前缀
    private static final String VERIFICATION_CODE_KEY_PREFIX = "email:verify:";
    /**
     * 六位数验证码生成
     * */
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    @Override
    public boolean sendVerificationCode(String email) {

        // 检查Redis中是否已存在未过期的验证码
        String checkRedisKey = VERIFICATION_CODE_KEY_PREFIX + email;
        String existingCode = redisTemplate.opsForValue().get(checkRedisKey);

        if (existingCode != null) {
            // 如果验证码存在，说明距离上次发送间隔不足，返回false
            log.warn("验证码存在，说明距离上次发送间隔不足");
            return false;
        }

        // 生成6位随机验证码
        String verificationCode = generateVerificationCode();

        try {
            // 发送邮件
            boolean res = sendEmail(email, verificationCode);

            // 将验证码保存到Redis，设置过期时间
            String redisKey = VERIFICATION_CODE_KEY_PREFIX + email;
            redisTemplate.opsForValue().set(
                    redisKey,
                    verificationCode,
                    CODE_EXPIRE_MINUTES,
                    TimeUnit.MINUTES
            );
            log.info("already send verification code to {}", email);
            return res;
        } catch (Exception e) {
            log.error("Failed to send verification code to {}", email, e);
            throw new SystemException(ResponseCode.SYSTEM_SEND_VERIFY_CODE_ERROR,"发送验证码失败", e);
        }
    }

    @Override
    public boolean verifyCode(String email, String code) {
        // Retrieve the stored verification code from Redis
        String redisKey = VERIFICATION_CODE_KEY_PREFIX + email;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        // Check if the codes match
        if (storedCode != null && storedCode.equals(code)) {
            // Optional: Delete the code after successful verification
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }


    private boolean sendEmail(String toEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProperties.getHost());
        props.put("mail.smtp.port", emailProperties.getPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailProperties.getUsername(),
                        emailProperties.getPassword()
                );
            }
        });

        try {
            // 准备模板上下文
            Context context = new Context();
            context.setVariable("verification_code", verificationCode);

            // 处理HTML模板
            //String htmlContent = templateEngine.process("email_template", context);

            String htmlContent = "验证码为："+verificationCode;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailProperties.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[虚拟实验平台-同济大学软件工程管理与经济学]验证码");
            // 设置HTML内容
            message.setContent(htmlContent, "text/html;charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new SystemException(ResponseCode.SYSTEM_MESSAGE_ERROR,"发送邮件失败", e);
        }
    }
}
