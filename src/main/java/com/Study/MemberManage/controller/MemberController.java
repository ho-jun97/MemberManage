package com.Study.MemberManage.controller;

import com.Study.MemberManage.entity.Member;
import com.Study.MemberManage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/member/write")
    public String memberWriteForm(){
        return "memberwrite";
    }

    @PostMapping("/member/writedo")
    public String memberWriteDo(Member member){
        memberService.write(member);
        return "redirect:/";
    }

    @GetMapping("/member/list")
    public String memberList(Model model){
        List<Member> member = memberService.memberList();
        model.addAttribute("list",member);
        return "memberlist";
    }
}
