package f3f.domain.user.application;

import f3f.domain.user.dao.EmailCertificationDao;
import f3f.domain.user.dto.MemberDTO;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static f3f.global.constants.EmailConstants.TITLE;
import static javax.mail.Message.RecipientType.TO;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCertificationService {

    private final JavaMailSender mailSender;
    private final EmailCertificationDao emailCertificationDao;

    @Value("${spring.mail.from-mail}")
    private String from;

//    인증번호 전송
    public void sendEmailForCertification(String email) throws MessagingException, UnsupportedEncodingException {
        String randomNumber = UUID.randomUUID().toString().substring(0, 6);
        String content = makeEmailContent(randomNumber);

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(TO,email);
        message.setFrom(new InternetAddress(from, "SPOTCAST"));
        message.setSubject(TITLE);
        message.setText(content, "utf-8", "html");
        mailSender.send(message);

        emailCertificationDao.createEmailCertification(email,randomNumber);

    }

    public void verifyEmail(MemberDTO.EmailCertificationRequest request){
        if(!isVerify(request)){
            throw new GeneralException(ErrorCode.EMAIL_CERTIFICATION_MISMATCH, "인증번호가 일치하지 않습니다.");
        }
        emailCertificationDao.removeEmailCertification(request.getEmail());
    }

    private boolean isVerify(MemberDTO.EmailCertificationRequest request) {
        return emailCertificationDao.hasKey(request.getEmail())
                &&
                emailCertificationDao.getEmailCertification(request.getEmail())
                .equals(request.getCertificationNumber());
    }

    private String makeEmailContent(String certificationNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div style = 'margin:100px;'>");
        sb.append("<h1> 안녕하세요 </h1>");
        sb.append("<h1> SPOTCAST 입니다.</h1>");
        sb.append("<br>");
        sb.append("<div align='center' style = 'border:1px solid black; font-family:verdana';>");
        sb.append("<h3 style = 'color:#020088;'>회원가입 인증 코드입니다.</h3>");
        sb.append("<div style='font-style:130%'>");
        sb.append("CODE: <strong>");
        sb.append(certificationNumber + "</strong><div><br/>");
        sb.append("</div>");
        return sb.toString();
    }
}
