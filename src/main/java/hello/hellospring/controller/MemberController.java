package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
//    private final MemberService memberService = new MemberService();
    /** 이렇게 new를 해서 쓰면 MemberController로 들어오는 애들 말고
     * 다른 여러 애들이 memberService를 가져다 쓸 수 있게됨.
     * 회원은 여러 군데서 쓰니까.
     * 그런데 얘를 여러번 생성할 필요없어. 하나만 생성하고 다른데서 공유해서 쓰면 됨.
     * 그래서 스프링 컨테이너에 Bean을 등록해서 쓴다. */
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    /** 이렇게 @Autowired 해서 쓴다. 이 Autowired가 spring 컨테이너에 있는 MemberService 객체를 가져와서 연결시켜준다.
     * 실행해보면 bean 등록이 아직 안된 상태
     * @Controller, @Service, @Repository 이렇게 Bean 등록
     * 정형화된 패턴이다.
     * 이렇게 해서 Bean을 가져와서 연결시키는 작업이 DI
     *
     * 이렇게 하고 main 메서드를 실행하면 잘 실행됨.
     * 원래 @Component라고 등록해도 됨. 그런데 @Service라고 해도 되는 이유는?
     * @Service안에 들어가보면 @Component 어노테이션이 등록되어 있다.
     * spring을 실행할 때 컴포넌트 빈을 스캔해서 등록함.
     * @Autowired는 연관관계를 연관시킴. */

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }
}
