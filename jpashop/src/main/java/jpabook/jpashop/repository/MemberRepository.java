package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor    // lombok의 어노테이션 : final 함수들만을 가지고 기본 생성자를 만들어줌
                            // 기본 생성자를 만들 필요 없음 + @Autowired 없어도 됨 = 해당 어노테이션 하나로 의존성 주입이 모두 끝
public class MemberRepository {
    /// 주의! : @RequiredArgsConstructor 어노테이션이 의존성 주입으로 작동하기 위해서는 반드시 final 키워드가 함께 필요함!!!!
    ///        final 키워드가 붙은 어트리뷰트만을 넣어 생성자를 만들기 때문!!!

    // @PersistenceContext         // 스프링부트가 엔티티 매니저를 생성하게 하기 위한 어노테이션
    private final EntityManager em;	// JPA를 사용하기 위해서는 엔티티매니저가 필요함

    public void save(Member member){ // JPA가 DB 안에 Member 변수를 추가해줌
        em.persist(member);
    }

    public Member findOne(Long id){ // id를 통해 하나의 멤버 조회하기
        return em.find(Member.class, id);
        // 타입, 키
        // em.find가 해당 Member 변수를 찾아서 반환해줌
    }

    public List<Member> findAll(){  // 전체 멤버 리스트로 조회하기
        // 쿼리문을 직접 작성해야 함
        // JPQL 쿼리문, 반환 타입
        // JPQL : SQL과 거의 비슷. 엔티티 객체를 대상으로 쿼리를 날림 (SQL은 테이블을 상대로 쿼리를 날림)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){ // 이름으로 멤버 조회하기
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // 조회 시 where문을 붙여 추가 조건 달기
                .setParameter("name", name)   //파라미터 설정
                .getResultList();
    }
}
