package com.eungyu.nonejpa.model.member;

public class Member {
    private String memberId;
    private String name;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    static public class Builder {
        private String memberId;
        private String name;

        public Builder() {}

        public Builder(Member member) {
            this.memberId = member.memberId;
            this.name = member.name;
        }

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Member build() {
            return new Member(memberId, name);
        }
    }
}
