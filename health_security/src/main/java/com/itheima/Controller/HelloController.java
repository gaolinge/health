package com.itheima.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public void add(){
        System.out.println("add");
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('update')")
    public void update(){
        System.out.println("update");
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(){
        System.out.println("delete");
    }

    @RequestMapping("/select")
    @PreAuthorize("hasAuthority('select')")
    public void select(){
        System.out.println("select");
    }

}
