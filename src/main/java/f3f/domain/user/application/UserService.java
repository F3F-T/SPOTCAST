package f3f.domain.user.application;

import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO;
import f3f.domain.user.dto.UserDTO.SaveRequest;
import f3f.domain.user.exception.DuplicateEmailException;
import f3f.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /**
     * 로그인
     * @param request
     * @return
     */
    public UserDTO.LoginRequest login(UserDTO.LoginRequest request){
        return null;
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    public UserDTO.LoginRequest logout(UserDTO.LoginRequest request){
        return null;
    }
    /**
     * 회원가입
     * @param request
     */
    @Transactional
    public void saveUser(SaveRequest request){

        if (!userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException("이메일 중복");
        }
        userRepository.save(request.toEntity());
    }

    /**
     * 유저 INFO
     * @param userId
     * @return
     */
    //로그인 체크
    @Transactional(readOnly = true)
    public UserDTO.UserInfoDTO getUserInfoById(long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.toFindUserDto();
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param request
     */
    //로그인 체크
    @Transactional
    public void updateUser(long userId, SaveRequest request){

    }

    /**
     * 회원 탈퇴
     * @param userId
     */
    //로그인 체크
    @Transactional
    public void deleteUser(long userId){

    }


}
