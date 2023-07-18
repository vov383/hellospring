package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
    /** 이것만 넣으면 끝. 구현할 것이 없다.
     * SpringDataJpa 는 JpaRepository 를 상속하면 spring bean을 자동으로 등록해
     * SpringDataJpa가 등록을 해주니까 내가 등록할 필요 없다.
     * 우리는 SpringConfig에 가져다 쓰기만 하면 됨.
     * */
}
