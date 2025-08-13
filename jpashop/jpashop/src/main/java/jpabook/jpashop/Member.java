package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter     // lombok 라이브러리를 통한 게터, 세터의 자동 생성
public class Member {

    @Id @GeneratedValue
    private Long id; //테이블 식별 id, 자동으로 생성됨
    private String username;

}
