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
        Member setMember = memberService.beforeWrite(member);
        memberService.write(setMember);
        return "redirect:/member/list";
    }

    @GetMapping("/member/list")
    public String memberList(Model model)throws Exception{
        List<Member> member = memberService.memberList();

        List<Member> setMember = memberService.getDay(member);
        model.addAttribute("list",setMember);
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

    // member 업데이트하기위한 페이지 이동
    @GetMapping("/member/modify/{id}")
    public String memberModify(@PathVariable("id")Integer id, Model model){
        model.addAttribute("member",memberService.memberView(id));

        return "membermodify";
    }

    // member 업데이트
    @PostMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable("id")Integer id, Member member){
        Member beforeMember = memberService.memberView(id);
        Member after = memberService.memberUpdate(member,beforeMember);

        memberService.write(after);
        return "redirect:/member/list";
    }

    // 전체연장 폼
    @GetMapping("/member/plus")
    public String memberPlusForm(){
        return "memberplus";
    }

    // 전체연장 실행
    @PostMapping("/member/plusdo")
    public String memberPlusDo(int plusDate) throws Exception{
        List<Member> members = memberService.memberList();
        for(Member member : members) {
            Member after = memberService.memberPlusDay(member, plusDate);
            memberService.write(after);
        }

        return "redirect:/member/list";
    }

    // 개인 연장 폼
    @GetMapping("/member/personalplus/{id}")
    public String personalPlusForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("member", memberService.memberView(id));
        System.out.println("check");
        return "personalplus";
    }

    // 개인 연장 실행
    @PostMapping("/member/personalplusdo/{id}")
    public String personalPlusDo(@PathVariable("id")Integer id, int plusDate) throws Exception{
        Member member = memberService.memberView(id);
        Member after = memberService.memberPlusDay(member, plusDate);
        System.out.println("check");
        memberService.write(after);
        return "redirect:/member/list";
    }

}
