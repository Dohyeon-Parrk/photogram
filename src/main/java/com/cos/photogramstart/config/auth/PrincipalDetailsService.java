package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //log.info(">>>>>>>>>>> 로그인 실행 :: " + username);
        User userEntity = userRepository.findByUsername(username);

        //리턴이 성공하면 자동으로 UserDetails 세션 생성
        //패스워드는 스프링 시큐리티에서 자동으로 체킹함 -> 신경 쓸 필요 없움
        if(userEntity == null){
            return null;
        }else {
            return new PrincipalDetails(userEntity);    // SecurityContextHolder  => Authentication 객체 내부에 담김
        }
    }
}







