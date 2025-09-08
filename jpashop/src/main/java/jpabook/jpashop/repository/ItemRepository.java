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

    // merge
    // DB 내에서 같은 id를 가진 객체를 찾은 뒤 파라미터로 받은 item 내의 값으로 모두 바꿔치기 한다
    // 파라미터로 넘어온 준영속 객체의 내용을 DB 내 영속성 객체에 덮어쓰기 하는 방식
    // 이후 업데이트가 완료된 영속성 객체를 반환
    // 더티 채킹의 경우 변화가 있는 속성만 선택적으로 변경
    // 그러나 merge의 경우 준영속 객체의 내용을 모두 덮어쓰기 하는 방식으로 병합 시 값을 정해주지 않은 필드는 null로 업데이트 된다!
    // 해당 위험으로 인해 변경 감지보다는 더티 채킹 기능을 사용할 것
    // 컨트롤러에서 함부로 엔티티를 새로 만들지 말 것

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
