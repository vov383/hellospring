package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{
    /** jpa는 엔티티 매니저로 모든게 동작함
     * 스프링부트가 gradle과 properties 정보를 바탕으로 EntityManager를 생성해줌.
     * 내부적으로 dataSource를 다 들고 있음.
     * jpa를 쓰려면 엔티티 매니저를 주입 need*/

    private EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    /** 이렇게하면 insert 끝
     * jpa가 insert 쿼리 다 만들어서 db에 집어넣고 id도 setId() 해준다. */

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
        /** Optional은 리턴값이 null일까봐. */
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
        /** 이렇게 짜면 끝
         * 저장, 조회하고, 수정, 삭제는 sql not need.
         * pk 기반이 아니라면 나머지들은 jpql 작성 neel
         *
         * jpa를 spring으로 한번 감싸서 제공하는게 스프링 jpa
         * 그걸 사용하면 이 두개도 jpql 필요없음*/

    }

    @Override
    public List<Member> findAll() {
        /** 쿼리문이 특이함
         * select m ?
         * Member 엔티티 전체를 select 하는 것*/
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        /** 인라인이면 ctrl + alt + N */
        /** 리펙토링 단축키 ctrl + alt + shift + T*/
    }
}
