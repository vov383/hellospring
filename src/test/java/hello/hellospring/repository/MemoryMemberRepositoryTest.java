package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
/* static import 돼서 밑에서 Assertion 안치고 바로 assertThat() 할 수 있음 */

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }
    /* 테스트 케이스는 실행 순서 보장X. 모든 테스트는 메서드별로 따로 동작하기 때문
     * 따라서 테스트는 순서에 상관없이, 서로 의존관계가 없도록 설계되어야 한다.
     * 그러기 위해서 하나의 테스트가 끝나면 데이터를 클리어 해주어야 함.
     * @AfterEach 는 메서드가 끝날 때마다 이 메서드를 동작시킨다.
     * 콜백 메서드.
     * 테스트할 메서드가 끝날 때마다 리파지토리에서 데이터를 클리어 */
    @Test
    /* org.junit.jupiter.api.Test 얘를 붙이면 이제 이 메서드를 그냥 실행할 수 있다. */
    public void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        /* Optional에서 값을 꺼낼 때 get(), 물론 이게 바람직한 방법은 아님. Test니까 이렇게 해도 되는 것 */
        assertThat(result).isEqualTo(member);
        /* assert,
         * Assertions.assertEquals(member, result); 얘로 실행해보면 출력되는건 없어도 녹색불 들어옴. ==> 같다는 의미.
         * assertEquals(member, null); 얘는 빨간불 뜬다. 기대했던 값이 안 들어오고 null이 들어와서 빨간불.
         * jupitor assertThat(member).isEqualTo(result); 얘는 member 가 result랑 똑같다.
         * assertThat(member).isEqualTo(null); 하면 빨간색 뜬다.
         * 실무에서는 툴에서 테스트 케이스 통과하지 않으면 다음단계로 못넘어가도록 막아버린다. */
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();
        /* repository.findByName("spring2").get()하고 실행하면 빨간불 */
        /* 테스트 케이스의 장점. 개별적으로 메서드를 실행하면 개별테스트
        * 전체 클래스를 실행하면 전체 테스트 가능 */

        //then
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo( 2 );

    }
}