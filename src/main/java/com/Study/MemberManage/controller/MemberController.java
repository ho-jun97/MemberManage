package com.Study.MemberManage.controller;

import com.Study.MemberManage.entity.Member;
import com.Study.MemberManage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/member/list";
    }

    @GetMapping("/member/list")
    public String memberList(Model model)throws Exception{
        List<Member> member = memberService.memberList();

        model.addAttribute("list",member);
        return "memberlist";
    }

    @GetMapping("/member/view")
    public String memberView(Model model, Integer id){
        model.addAttribute("member",memberService.memberView(id));
        return "memberview";
    }

    @GetMapping("/member/delete")
    public String memberDelete(Integer id){
        memberService.memberDelete(id);

        return "redirect:/member/list";
    }

    @GetMapping("/member/modify/{id}")
    public String memberModify(@PathVariable("id")Integer id, Model model){
        model.addAttribute("member",memberService.memberView(id));

        return "membermodify";
    }

    @PostMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable("id")Integer id, Member member){
        return "";
    }

}
