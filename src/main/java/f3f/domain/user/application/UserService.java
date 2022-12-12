package f3f.domain.user.application;

import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO.SaveRequest;
import f3f.domain.user.exception.DuplicateEmailException;
import f3f.domain.user.exception.DuplicateNicknameException;
import f3f.domain.user.exception.UnauthenticatedUserException;
import f3f.domain.user.exception.UserNotFoundException;
import f3f.global.encrypt.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.util.List;

import static f3f.domain.user.dto.UserDTO.*;
import static f3f.global.util.UserConstants.EMAIL;
import static f3f.global.util.UserConstants.LOGIN_STATUS;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {



    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final HttpSession session;


    /**
     * 회원가입
     * @param saveRequest
     * @return
     */
    public Long saveUser(SaveRequest saveRequest){
        if(emailDuplicateCheck(saveRequest.getEmail())){
            throw new DuplicateEmailException();
        }

        if(nicknameDuplicateCheck(saveRequest.getNickname())){
            throw new DuplicateNicknameException();
        }

        saveRequest.passwordEncryption(encryptionService);
        User user = saveRequest.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    /**
     * 로그인
     * @param loginRequest
     */
    @Transactional(readOnly = true)
    public void login(LoginRequest loginRequest){
        existsByEmailAndPassword(loginRequest);
        String email = loginRequest.getEmail();
        setLoginUserType(email);
        session.setAttribute(EMAIL,email);
    }

    /**
     * 로그아웃
     */
    @Transactional(readOnly = true)
    public void logout(){
        session.removeAttribute(EMAIL);
        session.removeAttribute(LOGIN_STATUS);
    }

    /**
     * 회원 정보 조회
     * @param email
     * @return
     */
    public UserInfoDTO findMyPageInfo(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다.")).toFindUserDto();
    }

    /**
     * 비밀번호 수정 - 로그인된 상태인 경우
     * @param request
     */
    public void updatePassword(UpdatePasswordRequest request){
        request.passwordEncryption(encryptionService);
        String beforePassword = request.getBeforePassword();
        String afterPassword = request.getAfterPassword();

        if(!userRepository.existsByEmailAndPassword(request.getEmail(),beforePassword)){
            throw new UnauthenticatedUserException("잘못된 정보입니다.");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        user.updatePassword(afterPassword);
    }

    /**
     * 비밀번호 수정 - 로그인되지 않은 경우(본인 인증을 이후)
     * @param request
     */
    public void updatePasswordByForgot(UpdatePasswordRequest request){
        request.passwordEncryption(encryptionService);
        String email = request.getEmail();
        String afterPassword = request.getAfterPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        user.updatePassword(afterPassword);

    }

    /**
     * 이메일 찾기
     * @param request
     * @return
     */
    public List<EmailListResponse> findEmailByForgot(FindEmailRequest request){
        String name = request.getName();
        String phone = request.getPhone();

        return userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }

    /**
     * 닉네임 수정
     * @param saveRequest
     */
    public void updateNickname(SaveRequest saveRequest){

        String email = saveRequest.getEmail();
        String changeNickname = saveRequest.getNickname();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        if(nicknameDuplicateCheck(saveRequest.getNickname())){
            throw new DuplicateNicknameException();
        }

        user.updateNickname(changeNickname);
    }


    @Transactional(readOnly = true)
    public void setLoginUserType(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        session.setAttribute(LOGIN_STATUS,user.getLoginUserType());
    }

    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(LoginRequest loginRequest) {
        loginRequest.passwordEncryption(encryptionService);
        if(!userRepository.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())){
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
