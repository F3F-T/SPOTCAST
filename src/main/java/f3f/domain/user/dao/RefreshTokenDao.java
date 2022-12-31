package f3f.domain.user.dao;

public interface RefreshTokenDao {
    void createRefreshToken(Long memberId,String refreshToken);
    String getRefreshToken(Long memberId);
    void removeRefreshToken(Long memberId);
    boolean hasKey(Long memberId);
}
