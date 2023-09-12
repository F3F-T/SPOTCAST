package f3f.domain.user.application;

import f3f.domain.user.dao.EmailCertificationDao;
import f3f.domain.user.dto.MemberDTO;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static f3f.global.constants.EmailConstants.TITLE;
import static f3f.global.response.ErrorCode.EMAIL_SEND_EXCEPTION;
import static javax.mail.Message.RecipientType.TO;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCertificationService {

    private final JavaMailSender mailSender;
    private final EmailCertificationDao emailCertificationDao;
    @Value("${spring.mail.from-mail}")
    private String from;

    @Value("${spring.mail.from-mail-personal}")
    private String personal;

    //    인증번호 전송
    @Async

    public void sendEmailForCertification(String email) {

        String randomNumber = UUID.randomUUID().toString().substring(0, 6);
        String content = makeEmailContent(randomNumber);

        try {

            MimeMessage message = mailSender.createMimeMessage();
            message.addRecipients(TO, email);
            message.setFrom(new InternetAddress(from, personal));
            message.setSubject(TITLE);
            message.setText(content, "utf-8", "html");
            mailSender.send(message);
        }catch (MailException | MessagingException | UnsupportedEncodingException e){
            throw new GeneralException(EMAIL_SEND_EXCEPTION,"이메일 전송에 실패했습니다.");
        }

        emailCertificationDao.createEmailCertification(email, randomNumber);

    }

    public void verifyEmail(MemberDTO.EmailCertificationRequest request) {
        if (!isVerify(request)) {
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

        sb.append("<div style='background: #F7F8F9; margin: 0; padding: 15px; cursor: default; letter-spacing: -.03em; font-family: 'Apple SD Gothic Neo', Helvetica, arial, '나눔고딕', 'Nanum Gothic', '돋움', Dotum, Tahoma, Geneva, sans-serif;'>");
        sb.append("<table cellspacing='0' cellpadding='0' style='max-width: 680px; width: 100%; margin: 0 auto; border-collapse: collapse;'>");
        sb.append("<tbody>");
        sb.append("<tr><td colspan='2' style='padding: 40px 10px; background: white; border: 1px solid #E5E8EB;'>");
        sb.append("<div style='max-width: 490px; width: 100%; margin: 0 auto; font-size: 13px; color: #373a3c; line-height: 1.8em;''>");
        sb.append("<h1 style='margin: 20px 0 0 0; padding: 0; text-align: center; font-size: 25px; color: #081928; font-weight: bold;'>이메일 주소 인증</h1>");
        sb.append("<p style='margin: 25px 0 0 0; padding: 0; font-size: 14px; color: #626d75; text-align: center; line-height: 21px;'>안녕하세요. SPOTCAST입니다.<br>이메일 인증을 위해 아래의 인증번호를 입력해주세요.</p>");
        sb.append("<p style='margin: 7px 0 0 0; padding: 0; font-size: 14px; color: #626d75; font-weight: bold; text-align: center; line-height: 21px;'>인증 번호 : <strong style='color: #0282da;'>"+certificationNumber+"</strong></p>");

        sb.append("</div>");
        sb.append("</td></tr><tr><td colspan='2' style='text-align: left; padding: 19px 0 15px 0;'>");
        sb.append("<p style='padding: 4px 0 0; margin: 0; line-height: 1.8em; color: #808991; font-size: 11px;'>");
        sb.append("본 메일은 발신전용 메일로, 회신되지 않습니다.<br>");
        sb.append("<br></p>");

        sb.append("<p style='padding: 8px 0 0; margin: 0; color: #808991; font-size: 14px; font-family: Tahoma, helvetica; '>© SPOTCAST Corp.</p>");
        sb.append("</td></tr></tbody>");
        sb.append("</table>");
        sb.append("</div>");

        return sb.toString();
    }
}
