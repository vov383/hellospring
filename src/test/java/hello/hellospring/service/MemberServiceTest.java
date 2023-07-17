package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    /** 동작하기 전에 Repository를 직접 넣어준다.*/
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        /**이렇게 memberRepository를 넣어주면 MemberService와 Test가 같은 memoryRepository를 사용할 수 있음.
         * 내가 직접 new하지 않고 외부에서 repository를 넣어준다.
         * 이런 것을 DI 라고 함. */
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    /** 얘를 가져와서 돌때마다 clear 로직 추가함
     * 돌때마다 메모리 클리어 하니까 잘 동작*/

    @Test
    void 회원가입() {
        /** 테스트는 과감하게 한글로 코드 적어도 됨. 테스트코드는 빌드될 때 실제 코드에 포함 X
         *
         * 추천하는 문법, 패턴
         * given
         * 이 데이터를 기반으로 해
         *
         * when
         * 이걸 검증해
         *
         * then
         * 여기가 검증부
         * */
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        /** 스태틱 임폴트*/
    }
    /**이거는 너무 단순
     * 테스트는 정상플로우도 중요하지만 예외 플로우가 훨씬 더 중요.
     * 이 테스트는 반쪽짜리 테스트
     * join은 저장 되는 것도 중요한데
     * 중복회원 검증을 통해 예외가 터뜨리는 로직도 점검 need*/


    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        /** 내가 join을 두번 했을 때 member1은 들어가고 member2가 들어갈 때 validate 해서 예외가 터져야함.*/
        memberService.join(member1);

        /** try catch 쓰기 애매할 때 쓰기 좋은 문법
         * IllegalStateException으로 member2를 join에 넣으면 예외가 터지도록
         * 만약 여기를 NPE로 바꾸면 테스트 실패*/
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//        assertThrows(NullPointerException.class, () -> memberService.join(member2));

        /** ctrl + alt + V로 변수에 할당하고 이렇게 하면 message 검증 can */
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /** 만약 위에서 spring을 Name에 집어넣으면 member1 넣을 때 익셉션 발생.
         * 그래서 clear() 가 필요하다. */


        /** 이렇게 try catch로 예외 처리 하는 방법도 있다. */
/*
        try {
            memberService.join(member2);
            */
/** 중복인데 익셉션이 발생 안하고 다음 줄로 넘어가면 fail()*//*

            fail();
        }catch (IllegalStateException e){
            */
/** 이렇게 하면 성공이고*//*

//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
            */
/** 이렇게 뒤에 다른 메시지를 넣으면 실패*//*

            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }
*/
        /** 이렇게 try catch를 얘때문에 쓰는 것은 애매하다. */

        //then

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}