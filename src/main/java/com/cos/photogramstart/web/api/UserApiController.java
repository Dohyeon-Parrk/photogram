package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //log.info(">>>>>>>>> userUpdateDto :: " + userUpdateDto);

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("errorMap Log ===============" + error.getDefaultMessage());
            }
            throw new CustomValidationApiException("Validation Fail !!!!!!", errorMap);
        }else {
            User userEntity = userService.Modify(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity);

            return new CMRespDto<>(1,"SuccessModify!", userEntity);
        }

    }
}
