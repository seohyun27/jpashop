package jpabook.jpashop.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 이름을 orders로 교체 -> DB에 존재하는 order 명령어와 구분하기 위함
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne  // order와 member는 다대일(N : 1) 관계. 양측에 모두 어노테이션 명시
    @JoinColumn(name = "member_id") // Order 테이블의 member_id 컬럼과 해당 변수를 매핑. 해당 필드가 Order → Member 방향의 연관관계 필드가 됨
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    private OrderStatus status;      // 주문 상태 [ORDER, CANCEL]
}
