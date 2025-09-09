package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

// 멤버 이름과 주문상태로 원하는 주문을 조회하는 기능 (검색 조건 클래스) -> 동적 쿼리를 사용해야 함
// JPA에서 동적 쿼리를 해결하는 방법

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
