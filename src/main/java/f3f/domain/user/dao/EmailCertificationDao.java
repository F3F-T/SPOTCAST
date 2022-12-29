package f3f.domain.user.dao;

public interface EmailCertificationDao {

    void createEmailCertification(String email,String certificationNumber);
    String getEmailCertification(String email);
    void removeEmailCertification(String email);
    boolean hasKey(String email);
}
