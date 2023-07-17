package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
//@Repository
/** Repository라고 스프링 컨테이너에 bean 등록*/
public class MemoryMemberRepository implements MemberRepository {
    /**
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap,
     * AtomicLong 사용 고려
     */
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        /* Optional.ofNullable()이렇게 하면 얘가 null이어도 사용 가능 */
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        /* store.values()가 Member들. 얘들이 쭉 반환됨. */
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        /*자바의 람다 사용. 반복문. 맵을 순회하면서 name이 찾아지면 걔를 반환 없으면? null을 Optional에 반환*/
    }

    public void clearStore() {
        store.clear();
    }
}
