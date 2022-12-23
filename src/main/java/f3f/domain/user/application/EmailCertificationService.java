package f3f.domain.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static f3f.global.constants.EmailConstants.TITLE;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCertificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from-mail}")
    private String from;

    //인증번호 전송
    public void sendEmailForCertification(String email){
        String  randomNumber = UUID.randomUUID().toString().substring(0, 6);
        String content = makeEmailContent(randomNumber);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(from);
        message.setSubject(TITLE);
        message.setText(content);
        mailSender.send(message);

//        emailCertificationNumberDao.createEmail(email, randomNumber);

    }

    private String makeEmailContent(String certificationNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("[SPOTCAST] 인증번호는 ");
        sb.append(certificationNumber);
        sb.append(" 입니다.");

        return sb.toString();
    }
}
