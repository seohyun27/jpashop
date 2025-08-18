package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 모든 데이터 변경, 로직은 트랜잭션 안에서 실행되어야 한다
                                // readOnly = true 옵션 : 조회만을 실행하게 될 때 성능을 더 최적화해줌
                                // 현재 해당 클래스의 기본 상태는 읽기 전용
@RequiredArgsConstructor    // lombok의 어노테이션 : final 함수들만을 가지고 기본 생성자를 만들어줌
                            // 기본 생성자를 만들 필요 없음 + @Autowired 없어도 됨 = 해당 어노테이션 하나로 의존성 주입이 모두 끝
public class MemberService {

    // DB와의 상호작용을 위해 멤버 리파지토리를 소유하고 있어야 함
    // final : 한 번 주입받은 이후 더는 고쳐질 일 없음
    private final MemberRepository memberRepository;

    /**
    @Autowired
    // 생성자 인젝션 : 생성되는 순간에 스프링 부트에서 MemberRepository를 주입받음
    // 생성자가 하나 뿐인 경우 @Autowired을 적을 필요도 없음
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    **/

    /// 회원 가입 ///
    @Transactional  // 읽기 전용이 아닌 메소드에는 반드시 읽기 전용이 아니라는 사실을 어노테이션을 통해 명시해줄 것!
    public Long join(Member member){
        validateDuplidateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplidateMember(Member member){ // 중복 회원 검증 로직
        // 중복 회원의 경우 예외를 터트리자
        // 문제가 없으면 다음 로직으로 넘어가 join()이 결과를 리턴하게 됨
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){ // 멤버의 개수 > 0라면 예외를 터트리는 식으로 최적화 가능
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        // 해당 코드의 경우 아직 문제가 존재 -> 둘 이상의 같은 이름이 동시에 등록될 때 둘 다 해당 메소드를 통과하는 불상사가 발생
        // 멀티 쓰레드 등을 고려해 DB의 칼럼 자체를 유니크로 잡아둘 수 있음
    }


    /// 회원 전체 조회 ///
    @Transactional(readOnly = true) // 조회만을 실행하게 될 때 성능을 더 최적화해줌
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    /// 회원 1명 조희 ///
    @Transactional(readOnly = true)
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
