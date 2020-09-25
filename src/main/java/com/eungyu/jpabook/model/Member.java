package com.eungyu.jpabook.model;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "ID")
    private final String id;    //아이디

    @Column(name = "NAME")
    private String username;        //이름
    private Integer age;        //나이

    public Member(String id) {
        this(id, "undefined", 0);
    }

    public Member(String id, String username, Integer age) {
        checkNotNull(id, "memberId must be provided");

        this.id = id;
        this.username = username;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    static public class Builder {
        private String id;
        private String username;
        private Integer age;

        public Builder() {}

        public Builder(Member member) {
            this.id = member.id;
            this.username = member.username;
            this.age = member.age;
        }

        public Builder memberId(String memberId) {
            this.id = memberId;
            return this;
        }

        public Builder name(String username) {
            this.username = username;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Member build() {
            return new Member(id, username, age);
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
