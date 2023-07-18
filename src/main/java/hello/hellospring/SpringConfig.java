package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
        /** MemberService에도 DI
         * 이렇게 하면 끝*/
    }
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }


//    @Bean
//    public MemberRepository memberRepository(){
//        return new JpaMemberRepository(em);
//        /**다른 어떤 코드도 손대지 않고 SpringConfig 만 건드렸다.
//         * 실행해보면 jdbc로 db에 접근 성공*/
////        return new MemoryMemberRepository();
//    }
//    /** @Controller는 어쩔수 없어.
//     * 어차피 Controller는 스프링에서 관리함.
//     * 클래스에 @Controller 어노테이션 붙여서 쓰고 @Autowired로 등록해야 한다.
//     *
//     * 이렇게 하면 셀프 bean 등록
//     * 근데 당연히 오토스캔 방식이 더 편하다. 한줄에 되니까
//     * 장단점이 있다.
//     * */



}
