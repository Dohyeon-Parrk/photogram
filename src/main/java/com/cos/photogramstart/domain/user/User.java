package com.cos.photogramstart.domain.user;


import com.cos.photogramstart.domain.image.Image;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//JPA : Java Persistencs API(자바로 데이터를 영구적으로 저장할 수 있는 API
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity // -> DB에 데이터 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가가 데이터베이스를 따라감.
    private Long id;

    @Column(length = 20, unique = true)  //username 제약 조건 걸기
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; //사진
    private String role;    //권한

    //mappedBy : '연관 관계의 주인이 아니다' -> 테이블에 컬럼을 만들지마
    //User 를 select 할 때 해당 User id로 등록된 image 다 가져 옴.
    //Lazy = User 를 select 할 때 해당 User id로 등록된 image 들을 안가져 옴 - 대신 getImages() 함수의 image 들이 호출될 때 가져 옴
    //Eager = User 를 select 할 때 해당 User id로 등록된 image 들을 전부 join 해서 가져 옴.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Image> images;     //양방향 매핑

    private LocalDateTime createDate;

    @PrePersist     // -> DB에 insert 되기 직전에 실행됨
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}



