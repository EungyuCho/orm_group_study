package com.eungyu.jpabook.model;

import javax.persistence.*;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "BOARD_SEQ_GENERATOR")
    private final Long id;    //아이디

    @Column(name = "NAME")
    private String username;        //이름

    private Integer age;        //나이

    //== 추가==
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    //Getter, Setter

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
