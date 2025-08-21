package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

// @Embeddable : 해당 클래스를 값 타입으로 선언. 다른 테이블 내부에 내장될 수 있음
// 값 타입의 경우 한 번 설정된 값이 이후에 변경되지 않도록 해야 함 -> 생성 시에만 값을 설정할 수 있도록

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
    
    // JPA 스펙 상 엔티티, 임베디드 타입에는 기본 생성자가 필요하다
    // public 혹은 protected로 선언 가능 -> 외부에서 사용할 일 없음으로 protected 처리
    protected Address() { 
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}


// 해당 클래스에 @Embeddable 처리 or 사용하려는 곳에서 @Embedded 처리
// 둘 중 하나만 해도 괜찮음 -> 가독성을 위해 둘 다 작성하는 경우도 많다
