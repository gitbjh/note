package com.example.spring.basicboardv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 화면 이동 = router
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String join() {
        return "sign-up";
    }

    @GetMapping("login")
    public String login() {
        return "sign-in";
    }
}
