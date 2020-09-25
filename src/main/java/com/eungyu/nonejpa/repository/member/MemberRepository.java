package com.eungyu.nonejpa.repository.member;

import com.eungyu.nonejpa.model.member.MemberNoneJPA;

import java.util.Optional;

public interface MemberRepository {
    public Optional<MemberNoneJPA> find(String memberId);
    public void save(MemberNoneJPA member);
}
