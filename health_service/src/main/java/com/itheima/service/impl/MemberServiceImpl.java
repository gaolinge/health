package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.getMember(telephone);
    }

    @Override
    public void addMember(Member member) {
        memberDao.addMember(member);
    }

    @Override
    public Map getMemberReport() {

        Map<String,Object> map = new HashMap<>();

        List<Map> genderList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            HashMap<String,Object> genderMap = new HashMap<>();

            int count= memberDao.getMemberGender(i);

            if (i == 0) {

                genderMap.put("name","不限");
            }
            if (i == 1) {

                genderMap.put("name","男");
            }
            if (i == 2) {

                genderMap.put("name","女");
            }

            genderMap.put("value",count);

            genderList.add(genderMap);

        }

        map.put("genderList",genderList);


        List<Integer> ages= memberDao.getMemberAge();

        int ages1=0;
        int ages2=0;
        int ages3=0;
        int ages4=0;

        for (Integer age : ages) {

            if (age >= 0 && age < 18) {

                ages1++;

                continue;
            }
            if (age >= 18 && age < 30) {

                ages2++;

                continue;
            }
            if (age >= 30 && age < 45) {

                ages3++;

                continue;
            }
            if (age >= 45) {

                ages4++;

                continue;
            }
        }

        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("name","0-18");
        map1.put("value",ages1);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("name","18-30");
        map2.put("value",ages2);
        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("name","30-45");
        map3.put("value",ages3);
        HashMap<String,Object> map4 = new HashMap<>();
        map4.put("name","45以上");
        map4.put("value",ages4);


        List<Map> ageList = new ArrayList<>();
        ageList.add(map1);
        ageList.add(map2);
        ageList.add(map3);
        ageList.add(map4);

        map.put("ageList",ageList);

        return map;
    }
}
