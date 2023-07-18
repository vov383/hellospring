package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateMemberRepository implements MemberRepository{
/** 사용법은 jdbcTemplate 메뉴얼
 * 검색해서 찾아보면 된다.
 * db 접속기술 강의에서 자세히 설명 가능*/
    private final JdbcTemplate jdbcTemplate;
//    @Autowired
    /** 생성자가 딱 하나만 있으면 자동으로 Bean 등록, @Autowired 생략 can
     * 생성자 두개면 안되고 */
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        /** sql을 직접 작성할 필요없이 이렇게 데이터 넣을 수 있다.
         * jdbcTemplate 도 설명하려면 엄청 자세히 설명할 수 있다.*/
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id=?", memberRowMapper(), id);
        return result.stream().findAny();
        /** 이렇게 해서 Optional 로 반환 */
        /** jdbc와 비교해보면 그 긴게 2줄로 줄었다.
         * 긴 코드를 줄이고 줄이고 줄인 게 template library 다.
         *
         * 왜 템플릿이야? 디자인 패턴 중에 템플릿 메서드 패턴을 사용했기 때문에
         * 그래서 코드를 많이 줄였다.
         *
         * jdbcTemplate 에서 쿼리 날려서
         * 그 결과를 memberRowMapper()를 통해서 매핑
         * 결과를 리스트로 받아서 Optional로 받아서 반환*/
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name=?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
        /** 메서드를 다 작성하고 alt + enter 해서 replace with lambda 하면 람다로 바꿀 수 있음.*/
        return (rs, rowNum) -> {

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
