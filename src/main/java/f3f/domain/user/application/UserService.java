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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;


    private void saveUser(UserDTO.SaveRequest userDto){
        if(emailDuplicateCheck(userDto.getEmail())){
            throw new DuplicateEmailException();
        }

        userDto.passwordEncryption(encryptionService);
        userRepository.save(userDto.toEntity());

    }



    @Transactional(readOnly = true)
    private boolean emailDuplicateCheck(String email) {
        return userRepository.existsByEmail(email);
    }


}
