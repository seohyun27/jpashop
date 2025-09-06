package jpabook.jpashop.controller;

import io.micrometer.observation.GlobalObservationConvention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j  // lombok을 통한 로거 생성
public class HomeController {

    @RequestMapping("/")
    public String home(){
        log.info("HomeController");
        return "home";
    }
}
