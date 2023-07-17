package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    /** @Controller는 어쩔수 없어.
     * 어차피 Controller는 스프링에서 관리함.
     * 클래스에 @Controller 어노테이션 붙여서 쓰고 @Autowired로 등록해야 한다.
     *
     * 이렇게 하면 셀프 bean 등록
     * 근데 당연히 오토스캔 방식이 더 편하다. 한줄에 되니까
     * 장단점이 있다.
     * */
}
