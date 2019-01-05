package com.practice.management.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RouteController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "Hi~欢迎");
        return "home";
    }

    @RequestMapping("/login")
    public  String login(){
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
}
