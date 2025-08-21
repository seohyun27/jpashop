package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.*;

import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 원래라면 테스트의 경우 Spring과 DB에 간섭받지 않는 단위 테트스가 훨씬 좋다
// 현재는 DB를 공부하기 위해 통합 테스트를 진행함

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("대구", "명곡로", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("JPA의 기초");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        // 비교 메소드 (기대하는 값, 실제 값)
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());      // 주문의 상태가 ORDER가 맞는지
        assertEquals(1, getOrder.getOrderItems().size());   // 주문 상품이 한 개가 맞는지
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()); // 주문 가격이 맞는지
        assertEquals(10 - orderCount, book.getStockQuantity());     // 상품 수량에서 주문한 개수만큼이 빠져나갔는지
    }

    @Test
    public void 상품주문_재고초과() throws Exception{
        // given
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("대구", "명곡로", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("JPA의 기초");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 11;

        //when
        try {
            Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        } catch (NotEnoughStockException e){
            return;
        }

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    public void 주문취소() throws Exception{
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("대구", "명곡로", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("JPA의 기초");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());  // 주문의 상태가 CANCEL가 맞는지
        assertEquals(10, book.getStockQuantity());       // 상품 수량에서 주문한 개수만큼이 복구되었는지
    }
}