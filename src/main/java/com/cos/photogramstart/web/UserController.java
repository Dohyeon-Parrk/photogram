package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id, Model model){
        User userEntity = userService.Profile(id);
        model.addAttribute("user", userEntity);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info(">>>>>>>>>> SessionInfo :: " + principalDetails.getUser());

        //@AuthenticationPrincipal 사용으로 밑에 방법은 사용하지 않음
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//        log.info(">>>>>>>>>> SessionInfo2 :: " + mPrincipalDetails.getUser());

        return "user/update";
    }
}
