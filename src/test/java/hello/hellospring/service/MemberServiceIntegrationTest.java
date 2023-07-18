package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    /** 테스트는 코드를 갖다 쓰는게 아니고 혼자 하는거니까 필드 인젝션으로 많이 한다. */

    /** @AfterEach 같은 애는 다음 테스트를 위해 db에서 지우는거니까 필요 X
     * @Transactional 때문에
     *
     * @Transactional 을 주석하고 실행하면?
     * db에 데이터 잘 들어감. 그리고 다시 테스트를 실행하면? 다시 중복검증에 걸려서 에러
     * db는 트랜잭션 개념이 있다. 기본적으로 commit하기 전까지 db에 반영이 안됨.
     * test 끝난다음에 rollback하면? db에서 데이터 다 반영이 안됨.
     * @@Transactional 을 테스트 클래스에 달면
     * 테스트 하기 전에 트랜잭션을 하고, 테스트 다 하고 db에 넣은다음에 rollback을 해준다.
     * 반복해서 테스트 can*/

    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }


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

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}