package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.springframework.ui.Model;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberCotoller {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")         // form 화면 열기
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); //html 내에서 쓸 MemberForm 객체를 "memberForm"에 담아서 넘겨줌
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")        // 실제 데이터 등록
    public String create(@Valid MemberForm form, BindingResult result){   // MemberForm이 파라미터로 넘어오게 됨

        if(result.hasErrors()){     // 오류가 발생했을 때(이름이 없을 때) 화이트 에러를 띄우는 대신 아래의 행위를 하게 됨
            return "members/createMemberForm";     // 회원가입 페이지로 되돌아가기. 이때 내가 설정한 @NotEmpty의 에러 메시지가 함께 출력됨
        }

        Address adress = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());     // 이름이 없는 경우에는 @Valid에 의해 에러 발생
        member.setAddress(adress);

        memberService.join(member);
        return "redirect:/";    // 첫번째 페이지로 되돌아가기
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> membes = memberService.findMembers();
        model.addAttribute("members", membes); 
        return "members/memberList";    // 가져온 멤버 정보들을 모델에 담아 화면에 넘겨줌

        // 상황이 조금만 더 복잡해져도 멤버 객체를 바로 쓰는 것보다 폼 객체로 반환해 필요한 정보만을 전달하는 방법이 권장됨
    }
}
