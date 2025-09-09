package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }



    /// 원하는 멤버의 주문을 검색하는 기능 ///
    public List<Order> findAllByString(OrderSearch orderSearch){

        // JPA Criteria로 처리
        // 해당 방법 역시 너무 복잡해서 실제로 사용하기는 어려움
        // Querydsl 라이브러리가 이를 해결할 수 있는 좋은 방법 중 하나임!! -> 이 방법을 추후에

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();



        /**
        // 원하는 멤버의 이름 + 주문 상태를 검색 -> 해당하는 주문들을 리스트 형태로 가져오기
        return em.createQuery("select o from Order o join o.member m" + // Order를 조회한 뒤 Order와 Order와 연관된 Member를 join
                        " where o.stauts = : status " +
                        "and m.name like :name", Order.class)
                .setParameter("stauts", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)    // 최대 1000건
                .getResultList();
         **/
    }
}
