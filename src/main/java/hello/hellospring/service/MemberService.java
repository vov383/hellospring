package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
/** 스프링 컨테이너에 Bean으로 등록함
 * Service라는걸 표시 */
public class MemberService {
    /** 서비스는 비지니스에 의존적으로 설계, 리포지토리는 개발에 포커스해서 명칭 작성 */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /** repository를 new 해서 쓰면 테스트를 별도의 instance에서 하는 셈
     * repository를 쓸 때마다 instance를 새로 만드는게 아니라 기존의 db를 가져와서 작업하려면 어떻게? */

    /** repository를 외부에서 넣어주도록 바꿔준다.*/
    private final MemberRepository memberRepository;
//    @Autowired
    /** MemberRepository 를 주입, 얘의 구현체는 MemoryMembrerRepository니까 얘를 주입해준다. */

    /** 직접 spring bean을 등록하는 방법. 
     * 먼저 어노테이션들을 지우고
     * 실행해보면 오류 발생
     * 직접 컴포넌트 등록하기*/
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }



    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복회원 금지

//        Optional<Member> result = memberRepository.findByName(member.getName());
//        /* ctrl + alt + V 하면 단축키로 내가 작성한 걸 우변에 할당, 좌변을 자동완성 */
//
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        /** 과거에는 if(member == null){} 이런 식으로 코딩했음. 지금은 null일 까능성이 있으면 Optional<>로 감싸서 반환해주고 감싼 덕분에 ifPresent()같은 메서드도 사용할 수 있게 됐다.
        * 추가적으로 orElseGet() : 값이 있으면 꺼내고 값이 없으면 어떤 메서드를 실행해 */

//        memberRepository.findByName(member.getName())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
        /** Optional을 바로 반환하는걸 권장하지 않음. 그래서 이런 형식으로 쓴다.*/


        validateDuplicateMember(member); //중복 회원 검증
        /** 그리고 ctrl alt M 해서 메서드로 추출하는 리펙토링 해줌 */
        
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}