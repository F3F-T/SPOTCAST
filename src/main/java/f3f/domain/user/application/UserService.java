package f3f.domain.user.application;

import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.dto.UserDTO;
import f3f.domain.user.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserDTO.SaveRequest request){

        if (!userRepository.existsByEmail(request.getLoginBase().getEmail())){
            throw new DuplicateEmailException("이메일 중복");
        }
        userRepository.save(request.toEntity());
    }
    //로그인 체크
    @Transactional
    public void updateUser(){

    }

    //로그인 체크
    @Transactional
    public void deleteUser(){

    }

    //로그인 체크
    @Transactional(readOnly = true)
    public UserDTO.UserInfoDTO getUserInfoById(long userId){
         return userRepository.findById(userId).get().toFindUserDto();
    }


}
