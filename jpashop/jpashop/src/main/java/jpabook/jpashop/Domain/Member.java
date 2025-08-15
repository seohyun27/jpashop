package jpabook.jpashop.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter // lombok 라이브러리를 통한 게터, 세터의 자동 생성
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "member_id") // DB 테이블의 "member_id"와 연결. DB에서는 해당 이름으로 동작
    private Long id;            // 테이블 식별 id, 자동으로 생성됨
    
    private String name;

    @Embedded
    private Address address;    // 해당 타입은 내장 타입임을 명시

    // member와 order는 일대다(1 : N) 관계
    // order 테이블의 "member" 필드에 의해 매핑된 읽기 전용 클래스임을 명시
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
