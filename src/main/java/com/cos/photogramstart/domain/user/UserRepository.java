package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션 없어도 JpaRepository를 상속하면 자동 IoC 등록 됨.
public interface UserRepository extends JpaRepository<User, Integer> {
    //JPA query method
    User findByUsername(String username);
}
