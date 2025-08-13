package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext         // 스프링부트가 엔티티 매니저를 생성하게 하기 위한 어노테이션
    private EntityManager em;   // JPA를 사용하기 위해서는 엔티티매니저가 필요함

    public Long save(Member member){    // 저장
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){        // 식별 id로 찾기
        return em.find(Member.class, id);
    }
}
