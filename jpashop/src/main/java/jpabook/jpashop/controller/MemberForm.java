package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

// 폼 객체
// 엔티티와 별개로 화면을 위해 사용됨
// 웹 폼을 위한 DTO
// 여러 필드를 하나의 객체로 묶어서 처리
// 비즈니스 로직과 분리된 '순수 데이터' 구조임

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}



