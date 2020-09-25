package com.eungyu.nonejpa.model.member;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemberNoneJPA {
    private String memberId;
    private String name;

    public MemberNoneJPA(String memberId) {
        this(memberId, "undefined");
    }

    public MemberNoneJPA(String memberId, String name) {
        checkNotNull(memberId, "memberId must be provided");

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

        public Builder(MemberNoneJPA member) {
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

        public MemberNoneJPA build() {
            return new MemberNoneJPA(memberId, name);
        }
    }
}
