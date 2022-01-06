package com.Study.MemberManage.service;

import com.Study.MemberManage.entity.Member;
import com.Study.MemberManage.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    public void write(Member member){

        // 신청일자 셋팅
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(date);
        member.setStartDay(startDate);

        // 종료일자 셋팅
        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);
        cal.add(Calendar.DATE, member.getRemainDay());
        String endDate = dateFormat.format(cal.getTime());
        member.setEndDay(endDate);

        /**
         * 할인율 셋팅
         * 브론즈    0%
         * 실버     5%
         * 골드     10%
         * 플래티넘  15%
         * 다이아   20%
         */

        String memberGrade = member.getGrade();
        double discountPrice = 0;

        switch(memberGrade){
            case "브론즈" :
                discountPrice = member.getPay();
                break;
            case "실버" :
                discountPrice = member.getPay() * 0.95;
                break;
            case "골드" :
                discountPrice = member.getPay() * 0.90;
                break;
            case "플래티넘" :
                discountPrice = member.getPay() * 0.85;
                break;
            case "다이아" :
                discountPrice = member.getPay() * 0.80;
                break;
            default:
                break;
        }
        member.setPay((int)discountPrice);
        memberRepository.save(member);
    }

    public List<Member> memberList(){
        return memberRepository.findAll();
    }
}
