package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // 이넘 타입의 사용을 위해서 어노테이션이 필요함
    // EnumType.ORDINAL : 각 항목이 모두 숫자로 처리됨. 중간에 다른 항목을 추가할 경우 순서가 밀려 DB의 정보가 다 꼬인다
    // EnumType.STRING : 각 항목이 String 타임으로 처리됨. 권장

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
