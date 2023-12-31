package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor        //final 필드를 DI(의존성 주입) 할 떄 사용
@Controller     //1.loC  2.파일을 리턴하는 컨트롤러
public class AuthController {

    private final AuthService authService;
/*
    public AuthController(AuthService authService){
        this.authService = authService;
    }*/

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    //회원가입버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("errorMap Log ===============" + error.getDefaultMessage());
            }
            throw new CustomValidationException("Validation Fail !!!!!!", errorMap);
        } else {
            log.info(signupDto.toString());
            User user = signupDto.toEntity();
            log.info(user.toString());
            User userEntity = authService.resist(user);

            return "auth/signin";
        }
    }
}
