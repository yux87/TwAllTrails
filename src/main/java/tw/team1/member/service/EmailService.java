package tw.team1.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${server.ip}")
    private String serverIp;
    @Value("${server.port}")
    private String serverPort;
    public void sendVerificationEmail(String token, String username) throws MessagingException {
        String verificationLink = "http://" + serverIp + ":" + serverPort + "/verify/" + token;
        String emailBody = "<html><head></head><body>"
                + "<h2 style=\"font-family: Arial, sans-serif; color: #333333;\">請點擊連結以確認電子郵件:</h2>"
                + "<h3 style=\"font-family: Arial, sans-serif; color: #333333;\">感謝您註冊我們的服務。請點擊下方按鈕完成驗證過程:</h3>"
                + "<a href=\"" + verificationLink + "\" style=\""
                + "display: inline-block;"
                + "background-color: #007bff;"
                + "color: #ffffff;"
                + "text-decoration: none;"
                + "padding: 12px 24px;"
                + "border-radius: 4px;"
                + "font-family: Arial, sans-serif;"
                + "font-size: 16px;"
                + "font-weight: bold;"
                + "transition: background-color 0.3s ease;\""
                + ">確認註冊</a>"
                + "<h3 style=\"font-family: Arial, sans-serif; color: #333333;\">如果您沒有註冊本服務,請忽略此電子郵件。</h3>"
                + "</body></html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(username); // 設置收件人
        helper.setSubject("Email Verification"); // 設置主題
        helper.setText(emailBody, true); // 設置內容, true表示支持HTML

        mailSender.send(message);
    }

    public void sendResetPasswordEmail(String token, String username) throws MessagingException {
        String verificationLink = "http://" + serverIp + ":" + serverPort + "/verifyResetPassword/" + token;
        String emailBody = "<html><head></head><body>"
                + "<h2 style=\"font-family: Arial, sans-serif; color: #333333;\">請點擊連結以重設密碼:</h2>"
                + "<h3 style=\"font-family: Arial, sans-serif; color: #333333;\">您好，您已要求重設密碼。請點擊下方按鈕完成重設密碼過程:</h3>"
                + "<a href=\"" + verificationLink + "\" style=\""
                + "display: inline-block;"
                + "background-color: #dc3545;"
                + "color: #ffffff;"
                + "text-decoration: none;"
                + "padding: 12px 24px;"
                + "border-radius: 4px;"
                + "font-family: Arial, sans-serif;"
                + "font-size: 16px;"
                + "font-weight: bold;"
                + "transition: background-color 0.3s ease;\""
                + ">重設密碼</a>"
                + "<h3 style=\"font-family: Arial, sans-serif; color: #333333;\">如果您沒有要求重設密碼,請忽略此電子郵件。</h3>"
                + "</body></html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(username); // 設置收件人
        helper.setSubject("重設密碼"); // 設置主題
        helper.setText(emailBody, true); // 設置內容, true表示支持HTML

        mailSender.send(message);

    }
}