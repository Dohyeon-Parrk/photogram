package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import javax.validation.constraints.NotBlank;

public class UserUpdateDto {
    @NotBlank
    private String name;    //필수데이터
    @NotBlank
    private String password;    //필수데이터
    private String website;     //쩌리
    private String bio;     //쩌리
    private String phone;       //쩌리
    private String gender;      //쩌리

    public User toEntity(){
        return User.builder()
                .name(name)     //이름을 안 적었으면 문제가 됨.
                .password(password)     //패스워드를 안적었으면 문제가 됨. -> Validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
