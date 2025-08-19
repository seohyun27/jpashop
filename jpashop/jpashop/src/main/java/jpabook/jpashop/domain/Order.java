package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.FileItemStream;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/// 많은 비즈니스 로직들이(ex. 재고 관리) 얽혀서 돌아가는 클래스
/// 해당 어플리케이션에서 가장 중요한 클래스이다

@Entity
@Table(name = "orders") // 테이블 이름을 orders로 교체 -> DB에 존재하는 order 명령어와 구분하기 위함
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 인자가 없는 기본 생성자를 protected로 생성 -> 외부 패키지에서 사용할 수 없도록
                                                    // 새 Order 객체를 생성하는 건 반드시 아래에 별도로 작성된 static 생성자 메소드를 이용해야 한다!!
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

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    /// 생성 메소드 ///
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        // 주문을 생성하기 위해서는 주문만 필요한 게 아님
        // 아이템, 주문 아이템 등 여러 엔티티를 함께 생성해야 함
        // 이를 위해 전역 생성 메소드를 하나 만들어두고 해당 건들을 한거번에 처리하는 편이 좋음
        // +) OrderItem... : 가변 인자. OrderItem 객체를 0개 이상 전달할 수 있다
        // 들어온 인자들은 orderItems에 배열의 형태로 저장됨

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;

        // 상태 정보가 모두 저장된 order가 리턴됨
    }


    /// 비즈니스 로직 ///
    // 주문 취소
    public void cencel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : this.orderItems){    // 주문 상품의 재고를 다시 올려주자
            orderItem.cancel();                         // 주문 상품마다 cancel() 메소드를 실행
        }
    }


    /// 조회 로직 ///
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice(); // 주문 가격과 수량을 곱해 orderItem의 총 가격을 계산
        }
        return totalPrice;
    }
}
