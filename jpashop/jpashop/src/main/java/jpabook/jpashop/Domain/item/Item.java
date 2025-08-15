package jpabook.jpashop.Domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 추상 클래스
// 해당 클래스를 상속한 Album, Book, Movie 클래스가 만들어진다

// DB 운용 시 상속관계 전략을 부모 클래스에 잡아줘야 한다
// 현재 클래스에서는 싱글 테이블 전략을 사용함 (하나의 테이블에 모든 자식을 함께 저장)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
