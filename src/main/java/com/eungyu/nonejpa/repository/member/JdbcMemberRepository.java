package com.eungyu.nonejpa.repository.member;

import com.eungyu.nonejpa.model.member.MemberNoneJPA;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<MemberNoneJPA> find(String memberId) {
        List<MemberNoneJPA> results = jdbcTemplate.query(
                "SELECT MEMBER_ID, NAME FROM MEMBER WHERE MEMBER_ID = ?",
                new Object[]{memberId},
                mapper
        );
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void save(MemberNoneJPA member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO member(member_id, name) VALUES (?,?)");
            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getName());
            return ps;
        }, keyHolder);
    }

    static RowMapper<MemberNoneJPA> mapper = (rs, rowNum) -> new MemberNoneJPA.Builder()
            .memberId(rs.getString("member_id"))
            .name(rs.getString("name"))
            .build();
}
