package com.backend.vse.tools;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Random;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/4
 * @JDKVersion 17.0.4

 * 实现一个用于发送电子邮件的工具类 MailSender，其中核心功能是向指定邮箱发送一封包含六位验证码的邮件
 */
public class MailSender {

    public static StringBuilder sendEmail(String email) throws Exception {
        // 创建Properties对象
        Properties prop = getProperties();

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect("smtp.qq.com", "3155002905", "blmkcdggktphdgdb");
        // 创建邮件
        Message message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("3155002905@qq.com"));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        // 邮件的标题
        message.setSubject("虚拟实验仿真系统VSE:请验证您的邮箱");
        // 邮件的文本内容
        StringBuilder code = createCode();
        message.setContent("您的验证码为"+ code, "text/html;charset=UTF-8");
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();

        return code;
    }

    private static Properties getProperties() throws GeneralSecurityException {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名为QQ邮箱的服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 设置发送邮件协议名称为SMTP(Simple Mail Transfer Protocol)
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        return prop;
    }
    // 生成随机的六位验证码
    public static StringBuilder createCode() {
        String dates = "0123456789";
        StringBuilder code = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            int index = r.nextInt(dates.length());
            char c = dates.charAt(index);
            code.append(c);
        }
        return code;
    }

}