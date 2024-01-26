package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity  //해당 파일로 시큐리티를 활성화 시키는 어노테이션
@Configuration
public class SecurityConfig{

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 모델 : Image, User, Likes, Subscribe, Tag : 인증 필요
    // auth 주소 : 인증 필요없음
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**","/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")  //GET
                .loginProcessingUrl("/auth/signin") //POST -> 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()     // oauth2 로그인을 하면 최종응답을 회원정보 바로 받을 수 있음
                .userService(oAuth2DetailsService);
        return http.build();
    }
}
