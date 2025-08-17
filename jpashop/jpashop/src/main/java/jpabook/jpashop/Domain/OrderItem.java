package jpabook.jpashop.Domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jpabook.jpashop.Domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
    private int orderPrice; // 주문 가격
    private int count;      //주문 수량

}
