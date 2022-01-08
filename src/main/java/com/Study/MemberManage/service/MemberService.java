package com.Study.MemberManage.service;

import com.Study.MemberManage.entity.Member;
import com.Study.MemberManage.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    public void write(Member member){
        memberRepository.save(member);
    }

    public Member beforeWrite(Member member){
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

        return member;
    }
    public List<Member> memberList(){
        return memberRepository.findAll();
    }
    public List<Member> getDay(List<Member> members)throws Exception{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String toDayDate = now.format(formatter); // 오늘 날짜 String 반환

        // 남은 날짜
        for(Member member : members) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String endDay = member.getEndDay();
            String today = toDayDate;

            Date endDayDate = dateFormat.parse(endDay);
            Date todayDate = dateFormat.parse(today);

            int remainDay = (int) ((endDayDate.getTime() - todayDate.getTime()) / (24 * 60 * 60 * 1000));

            if (remainDay < 0) {
                remainDay = 0;
            }
            member.setRemainDay(remainDay);
        }

        return members;
    }

    public Member memberView(Integer id){
        return memberRepository.findById(id).get();
    }

    public void memberDelete(Integer id){
        memberRepository.deleteById(id);
    }

    public Member memberUpdate(Member member, Member update){

        /**
         * 할인율 셋팅
         * 브론즈    0%
         * 실버     5%
         * 골드     10%
         * 플래티넘  15%
         * 다이아   20%
         */

        String memberGrade = member.getGrade();  // 멤버의 등급을 받아옴
        double discountPriceDouble = 0; // 멤버로 할인된 가격
        switch (memberGrade) {
            case "브론즈" :
                discountPriceDouble = member.getPay();
                break;
            case "실버" :
                discountPriceDouble = member.getPay() * 0.95;
                break;
            case "골드" :
                discountPriceDouble = member.getPay() * 0.9;
                break;
            case "플래티넘" :
                discountPriceDouble = member.getPay() * 0.85;
                break;
            case "다이아" :
                discountPriceDouble = member.getPay() * 0.8;
                break;
        }
        int discountPrice = (int)discountPriceDouble;

        update.setGrade(member.getGrade());
        update.setName(member.getName());
        update.setType(member.getType());
        update.setPay(discountPrice);
        update.setAddress(member.getAddress());
        update.setPhone(member.getPhone());


//        // 신청일자 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = format.format(date);
//        update.setDay(dateString);
//        // 신청일자 셋팅 완료

        // 신청일자는 수정하면 안됨 (수정하면 수정한 날짜로 변경되기 때문)


        // 종료일자 설정
        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);
        cal.add(Calendar.DATE, member.getRemainDay());
        String dateString2 = format.format(cal.getTime());
        update.setEndDay(dateString2);
        // 종료일자 셋팅 완료

        return update;
    }

    public Member memberPlusDay(Member member, int plusDate)throws Exception{
            String endDay = member.getEndDay();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            Date regDate = null;
            regDate = format.parse(endDay);
            cal.setTime(regDate);

            cal.add(Calendar.DATE, plusDate);
            String dateValue = format.format(cal.getTime());


            member.setEndDay(dateValue);

            return member;
    }
}
