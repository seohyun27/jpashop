package jpabook.jpashop.domain;

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

    @ManyToOne(fetch = FetchType.LAZY)  // order와 member는 다대일(N : 1) 관계. 양측에 모두 어노테이션 명시
    @JoinColumn(name = "member_id") // Order 테이블의 member_id 컬럼과 해당 변수를 매핑. 해당 필드가 Order → Member 방향의 연관관계 필드가 됨
    private Member member;

    //cascade = CascadeType.ALL : order를 저장할 때 orderItems도 함께 저장하게 됨. 부모-자식의 종속관계에서만 사용
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    private OrderStatus status;      // 주문 상태 [ORDER, CANCEL]


    /// 연관관계 메소드 ///
    public void setMember(Member member){
        this.member = member;          // Order 객체 안에서 member 참조 연결
        member.getOrders().add(this);  // Member 객체 안에서 order 리스트를 반환. 거기에 자기자신(this = 현재 내 클래스의 객체 = 새 order)를 추가
    }

    public void setOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
