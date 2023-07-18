package hello.hellospring.domain;

import javax.persistence.*;

@Entity
/** 이걸 달면 jpa에서 관리하는 엔티티가 됨*/
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 아이덴티티 전략
     * 오라클DB의 시퀀스,
     * 내가 직접 넣어줄 수도 있고,
     * 지금처럼 db가 알아서 생성해주는 것을 아이덴티티라고 한다.*/
    private Long id;

    //@Column(name="username")
    /** 테이블의 컬럼명이 username이면 이렇게 매핑하면 된다.
     * 같으면 안해도 되고*/
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
