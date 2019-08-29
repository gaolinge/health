package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void addMember(Member member);

    Map getMemberReport();

}
