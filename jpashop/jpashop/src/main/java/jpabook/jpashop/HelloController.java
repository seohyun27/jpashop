package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //Model에 데이터를 담아 컨트롤러에서 뷰로 정보를 옮길 수 있음

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
        //return 뒤의 문자열은 .html이 자동으로 붙어 뷰의 이름이 됨
    }
}
