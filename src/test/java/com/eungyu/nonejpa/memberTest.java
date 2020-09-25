package com.eungyu.nonejpa;

import com.eungyu.nonejpa.model.member.Member;
import com.eungyu.nonejpa.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class memberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 멤버의_정보를_찾는다() {
        Optional<Member> test01 = memberRepository.find("test01");
        Member member = test01.get();
        assertEquals(member.getName(), "테스트계정");
    }

}
