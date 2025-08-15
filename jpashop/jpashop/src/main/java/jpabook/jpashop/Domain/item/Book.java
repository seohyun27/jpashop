package jpabook.jpashop.Domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //싱글 테이블에서 각 항목의 구분을 위한 값
@Getter @Setter
public class Book extends Item{
    private String author;
    private String isbn;

}
