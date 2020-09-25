package com.eungyu.nonejpa.repository.member;

import com.eungyu.nonejpa.model.member.Member;

import java.util.Optional;

public interface MemberRepository {
    public Optional<Member> find(String memberId);
    public void save(Member member);
}
