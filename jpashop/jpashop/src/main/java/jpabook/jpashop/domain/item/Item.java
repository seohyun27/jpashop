package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();



    /// 비즈니스 로직 ///
    // 엔티티 내부에서 해결될 수 있는 문제는 엔티티 클래스 내부에 비즈니스 클래스로 설계하면 좋음
    // stockQuantity의 변경이 setter를 통해 이뤄지지 않고 해당 비즈니스 로직을 통해 이뤄짐

    // 재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    // 재고 수량 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
