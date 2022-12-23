package f3f.domain.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmailCertificationNumberDao implements EmailCertificationDao{

    @Override
    public void createEmailCertification(String email, String certificationNumber) {

    }

    @Override
    public String getEmailCertification(String email) {
        return null;
    }

    @Override
    public void removeEmailCertification(String email) {

    }

    @Override
    public boolean hasKey(String email) {
        return false;
    }
}
