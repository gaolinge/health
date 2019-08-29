package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberDao {

    @Select("select * from t_member where phoneNumber=#{telephone}")
    Member getMember(String telephone);

    void addMember(Member member);

    @Select("select count(*) from t_member where regTime=#{today}")
    Integer findTodayNewMemberCount(String today);

    @Select("select count(*) from t_member")
    Integer findTotalMember();

    @Select("select count(*) from t_member where regTime>=#{thisWeekFirstDay}")
    Integer findThisWeekNewMemberCount(String thisWeekFirstDay);

    @Select("select count(*) from t_member where regTime>=#{thisMondayFirstDay}")
    Integer findThisMonthNewMemberCount(String thisMondayFirstDay);

    @Select("select count(1) from t_member where sex =#{i}")
    int getMemberGender(int i);

    @Select("select (YEAR(CURDATE())-YEAR(birthday)) age from t_member")
    List<Integer> getMemberAge();
}
