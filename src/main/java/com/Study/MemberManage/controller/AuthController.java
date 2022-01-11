package com.Study.MemberManage.controller;

import com.Study.MemberManage.entity.User;
import com.Study.MemberManage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String signInForm(){
        return "signin";
    }

    @GetMapping("/signup")
    public String signUpForm(){
        return "signup";
    }

    @PostMapping("/signupdo")
    public String signUp(User user, String confirmpassword){

        if (user.getPassword().equals(confirmpassword)){
            authService.signUp(user);
        }else{
            return "signup";
        }

        return "signin";
    }
}
