package jpabook.jpashop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;    // assertThat을 위한 라이브러리

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;   // 자동 생성

    @Test
    @Transactional      // 엔티티 매니저를 통한 테스트에는 반드시 트랜젝션이 필요함
    @Rollback(false)    // 만약 테스트의 결과를 DB에 남기고 싶다면 해당 어노테이션을 사용할 것
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");  // lombok을 통해 자동으로 만든 세터

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 멤버끼리의 직접 비교
        // 같은 테이블의 같은 id를 가진 객체는 같은 엔티티로 인식된다 -> 따라서 두 멤버는 같은 멤버이다
        assertThat(findMember).isEqualTo(member);
    }
}