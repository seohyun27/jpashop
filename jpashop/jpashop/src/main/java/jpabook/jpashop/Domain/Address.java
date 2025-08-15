package jpabook.jpashop.Domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable                // 해당 클래스를 값 타입으로 선언. 다른 테이블 내부에 내장될 수 있음
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}


// 해당 클래스에 @Embeddable 처리 or 사용하려는 곳에서 @Embedded 처리
// 둘 중 하나만 해도 괜찮음 -> 가독성을 위해 둘 다 작성하는 경우도 많다
