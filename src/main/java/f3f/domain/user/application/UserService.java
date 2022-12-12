package f3f.domain.user.application;

import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO;
import f3f.domain.user.dto.UserDTO.SaveRequest;
import f3f.domain.user.exception.DuplicateEmailException;
import f3f.domain.user.exception.UserNotFoundException;
import f3f.global.encrypt.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

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


    public Long saveUser(SaveRequest userDto){
        if(emailDuplicateCheck(userDto.getEmail())){
            throw new DuplicateEmailException();
        }

        userDto.passwordEncryption(encryptionService);
        User user = userDto.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    
    @Transactional(readOnly = true)
    public void login(LoginRequest userDto){
        existsByEmailAndPassword(userDto);
        String email = userDto.getEmail();
        setLoginUserType(email);
        session.setAttribute(EMAIL,email);
    }
    @Transactional(readOnly = true)
    public void setLoginUserType(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        session.setAttribute(LOGIN_STATUS,user.getLoginUserType());
    }

    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(LoginRequest userDto) {
        userDto.passwordEncryption(encryptionService);
        if(!userRepository.existsByEmailAndPassword(userDto.getEmail(), userDto.getPassword())){
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        return userRepository.existsByEmail(email);
    }


}
