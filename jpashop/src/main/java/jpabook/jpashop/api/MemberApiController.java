package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/// HTTP 정리 ///
/*
 HTTP : 웹에서 클라이언트와 서버가 서로 정보를 주고받을 때 사용하는 통신 규칙
 통신 규칙 안에는 여러 메서드가 존재한다. 그 중 대표적인 것이 GET, POST, PUT, DELETE
 GET : 데이터 가져오기. 서버에 있는 데이터를 요청해서 받아온다
 POST : 새 데이터 생성하기. 서버에 새로운 데이터를 만들어달라고 요청한다. 서버가 새로운 ID를 만들어내며 같은 요청을 여러 번 보내면 여러 개의 리소스가 생길 수 있다
 PUT : 서버의 데이터를 전체적으로 수정하거나 교체하기. 클라이언트가 리소스의 ID를 알고 있어야 한다. 같은 요청을 여러 번 보내도 결과가 같다
 DELETE : 서버의 데이터를 삭제하기
 HTTP 요청은 [요청 라인 + 헤더 + 바디]의 구조. (GET, POST, PUT, DELETE 등의) 메소드는 요청 라인의 가장 앞에 명시된다
 GET, DELETE의 경우 URL 경로 혹은 쿼리 파라미터로 리소스의 ID 값이 전달된다
 POST, PUT의 경우 바디에 JSON으로 리소스 정보를 전달한다
 */

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /// 프론트에서 보낸 값을 Member로 바로 받는 건 위험하다! 반드시 별개의 DTO 클래스를 만들 것 ///
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        // @RequestBody : JSON으로 온 Body를 Member에 매핑해서 넣어줌
        // 이 경우 프론트엔드 측에서 JSON 형식으로 Member를 형성하기 위한 데이터를 HTTP 요청의 Body에 담아서 보내줘야 함
        // @RequestBody 어노테이션이 프론트가 보낸 JSON 데이터를 자바 객체로 자동 변환해줌
        // 이 시점에서 백과 프론트의 변수명이 동일해야 함!

        // @Valid : 검증 어노테이션을 활성화 하는 일종의 트리거
        // @Valid가 붙은 객체의 경우 내부 필드에 선언된 검증 어노테이션들(@NotEmpty, @NotNull, @Size)이 동작된다
        // 만약 생성하려는 객체에 @Valid가 없다면 @NotEmpty 등은 무시된다

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {  // 프론트 측에서도 http body에 "name"이라는 변수가 있어야 함 -> 변수명 일치!

        // @NotEmpty : 이름은 필수값이므로 빼고 저장될 수 없다
        // 보통 @ControllerAdvice를 사용해 전역 예외 처리기를 만든 뒤 프론트 측에 예외를 전달한다
        @NotEmpty(message = "이름은 비워져 있을 수 없습니다.")
        private String name;
        
        // 이름 외 다른 어트리뷰트를 채우는 과정을 생략함
    }
}
