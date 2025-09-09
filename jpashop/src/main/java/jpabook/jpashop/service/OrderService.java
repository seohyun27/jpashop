package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /// 주문 ///
    @Transactional  // DB를 변경하게 됨
    public Long order(Long memberId, Long itemId, int count){
        // 멤버, 아이템 테이블의 내용을 id를 통해서 조회하게 됨 -> 둘의 DB를 조회해야 한다 -> 리파지토리 클래스 import

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());   // 해당 회원의 주소를 배달지로 설정

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();

        // 원래라면 배송과 주문 상품을 만든 이후 이들도 각각 리파지토리에 넣어줘야 함
        // 그러나 cascade = CascadeType.All 옵션으로 인해 delivery, orderItem를 Order를 DB에 저장할 때 함께 저장하게 해줌
        // 배송 정보와 주문 상품의 생성과 삭제는 주문을 만들 때 이외에 쓰일 일이 없다!
        // 주문의 라이프사이클과 주문 상품, 배송 정보의 라이프 사이클이 동일 + 다른 클래스가 참조하지 않는 private 참조일 때!
    }

    /// 취소 ///
    @Transactional
    public void cancelOrder(Long id){
        Order order = orderRepository.findOne(id);
        order.cencel();
        // 배송이 완료된 상품을 취소할 수 없는 것(예외), 주문을 취소할 때 주문 상태가 취소로 변하는 것
        // 상품의 수량이 다시 채워지는 것 모두 Order 클래스 내부에서 이미 정의되어 있다

        // 원래라면 이미 DB에 담긴 데이터가 변경된 뒤에는 DB에 해당 데이터의 변경을 반영하기 위해 업데이트 쿼리를 날려야 한다
        // 그러나 JPA의 경우 엔티티 내의 데이터가 바뀔 때 JPA가 알아서 변경 내역을 DB에 업데이트 해준다!!!
        // 작성자인 나는 DB 내부에 데이터를 넣고 삭제하는 것만 신경써주면 됨!
    }

    /// 검색 ///
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

}
