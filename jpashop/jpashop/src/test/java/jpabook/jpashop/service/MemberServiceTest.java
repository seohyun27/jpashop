package jpabook.jpashop.service;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest     // 스프링 컨테이너의 안에서 테스트를 실행
@Transactional      // 테스트 후 롤백
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    // @Rollback(value = false) // 롤백 없이 커밋
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long id = memberService.join(member);

        //then
        em.flush(); // 기존 : 트랜잭션으로 인해 쿼리문을 볼 수 없음 -> em.flush : 트랜잭션으로 롤백하면서 쿼리문을 볼 수 있도록 함(쿼리를 날린 후 롤백)
        assertEquals(member.getId(), memberRepository.findOne(id).getId());
    }

    @Test
    public void 중복회원검증() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); // 이 타이밍에서 예외가 발생해야 함
        } catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외가 발생해야 합니다.");  // 예외가 터지지 않고 이 코드까지 도착하면 테스트에 실패함
    }
}
