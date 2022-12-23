package f3f.domain.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

import static f3f.global.constants.JwtConstants.PREFIX_REFRESHTOKEN;
import static f3f.global.constants.JwtConstants.REFRESH_TOKEN_EXPIRE_TIME_SECOND;


@RequiredArgsConstructor
@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {

    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public void createRefreshToken(Long memberId, String refreshToken) {
        stringRedisTemplate.opsForValue().set(PREFIX_REFRESHTOKEN + memberId
                , refreshToken
                , Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME_SECOND));
    }

    @Override
    public String getRefreshToken(Long memberId) {
        return stringRedisTemplate.opsForValue().get(PREFIX_REFRESHTOKEN + memberId);
    }

    @Override
    public void removeRefreshToken(Long memberId) {
        stringRedisTemplate.delete(PREFIX_REFRESHTOKEN + memberId);
    }

    @Override
    public boolean hasKey(Long memberId) {
        return stringRedisTemplate.hasKey(PREFIX_REFRESHTOKEN + memberId);
    }
}
