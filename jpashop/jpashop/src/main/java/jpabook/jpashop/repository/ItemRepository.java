package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // final이 붙은 변수들을 포함해 기본 생성자를 만들어줌
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null)    // JPA에 저장하기 이전에는 id값이 존재하지 않는다 -> 완전히 새로 만들어진 item
            em.persist(item);
        else em.merge(item);        // 이미 DB에 등록된 item을 가져옴 -> 일종의 업데이트 개념
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
