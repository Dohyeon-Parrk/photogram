package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User Profile(int userId){
        //select * from image where userId = :userId;
        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 존재 하지 않는 페이지 입니다");
        });
        return userEntity;
    }
    @Transactional
    public User Modify(int id, User user){
        // 1. 영속화 하기
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을 수 없는 ID 입니다.");
        });    // 1. 무조건 찾음 2. 못 찾을 경우 익셉션 작동

        // 2. 영속화 된 오브젝트 수정 - 더티체킹(업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }   //더티체킹이 일어나서 업데이트가 완료됨.
}
