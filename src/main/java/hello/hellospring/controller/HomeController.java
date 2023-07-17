package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    /** / 는 localhost:8081 딱 들어왔을 때 경로. */
    public String home(){
        return "home";
    }
}
