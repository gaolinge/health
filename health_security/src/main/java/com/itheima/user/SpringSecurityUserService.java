package com.itheima.user;

import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户
        User user = findUserByUsername(username);

        if (user == null) {

            return null;
        }

        //把用户名,数据库的密码,以及查询出来的权限访问,创建UserDetails对象返回
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);


    }

    //假设从数据库中获取user
    private User findUserByUsername(String username) {

        if ("admin".equals(username)) {

            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode("123456"));

            return user;

        }

        return null;
    }
}
