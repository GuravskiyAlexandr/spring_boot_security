package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "user";
    }
}
