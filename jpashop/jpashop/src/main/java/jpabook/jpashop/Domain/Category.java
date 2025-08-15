package jpabook.jpashop.Domain;

import jakarta.persistence.*;
import jpabook.jpashop.Domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany //실제 프로젝트에서는 다대다 연결은 일대다 + 다대일로 풀어서 작성해야 함
    @JoinTable(name = "category_item", //다대다 연결의 경우 중간 매핑 테이블의 필요함
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne // 본인이 본인에게 연결. 키를 가지는 쪽
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 본인이 본인에게 연결
    private List<Category> child = new ArrayList<>();
}
