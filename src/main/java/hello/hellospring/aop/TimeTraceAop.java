package hello.hellospring.aop;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
/**
 * 스프링 빈으로 등록할 때
 * @Composnent 해서 등록해도 되는데
 * 그보다는 SpringConfig에 @Bean으로 등록해서 쓰는 방식을 선호한다.
 * AOP 같은 경우 정형화된 패턴이 아니기 때문에
 * 아 이런 기능에 AOP 걸어서 쓰는구나 하고 보는사람이 인지할 수 있도록 하기 위함.
 * */
public class TimeTraceAop {

    /**
     * AOP Bean 등록에서 순환참조 문제를 해결하기 위해 조치
     * SpringConfig에 @Bean으로 등록할 때 발생한 순환참조
     * 원인
     * SpringConfig의 timeTraceAop() 메서드도 AOP로 처리하게 됩니다.
     * 그런데 이게 바로 자기 자신인 TimeTraceAop를 생성하는 코드인 것이지요. 그래서 순환참조 문제가 발생합니다.
     * 반면에 컴포넌트 스캔을 사용할 때는 AOP의 대상이 되는 이런 코드 자체가 없기 때문에 문제가 발생하지 않았습니다.
     * 해결방법
     * 다음과 같이 AOP 대상에서 SpringConfig를 빼주면 됩니다.
     * */
//    @Around("execution(* hello.hellospring..*(..))")
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    /** AOP 공통 관심사항을 어디에다 적용할지 선택 can
     * 타겟팅
     * 문법이 있다.
     * 공식문서, 메뉴얼 보고 하면 어렵지 않다.
     * 디테일하게 제공하지만 실무에서 쓰는건 5프로정도?
     * 패키지명, 클래스명, 파라메터명 등등 원하는 걸 다 적을 수 있다.
     * 지금 위에 코드는 패키지 하위에 모두 적용하라는 뜻*/
    private Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed();

        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
