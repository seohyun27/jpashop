package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 인자가 없는 기본 생성자를 protected로 생성
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "order_id")
    private Order order;

    //spring boot 기본 전략에 따라 해당 필드의 이름이 DB 칼럼에서는 order_price로 변경됨(대문자 없음)
    private int orderPrice;     // 주문 가격
    private int count;          //주문 수량


    /// 생성 메소드 ///
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 주문 물품이 생성된 순간에 물품의 재고가 count 만큼 줄어들어야 함
        item.removeStock(count);

        return orderItem;
    }


    /// 비즈니스 로직 ///
    public void cancel(){       // 주문 취소시 각 물품의 주문 수량을 복구하는 역할
        // 주문 수량만큼 주문 물품의 가격을 다시 올려줘야 함!!
        getItem().addStock(count);

        // getItem()과 item은 완전히 동일한 필드를 의미
        // 단, JPA 엔티티에서는 직접 필드 접근보다는 게터 세터를 통한 접근을 권장하는 경우가 많음
        // 이유는 일관성, 캡슐화, LAZY 로딩 처리, 유지보수성 때문
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
