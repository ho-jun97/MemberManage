package com.Study.MemberManage.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 회원 이름

    private String grade; // 회원 등급

    private String type; // 종목

    private String startDay; // 신청날짜

    private String endDay; // 종료날짜

    private int remainDay; // 잔여일수

    private int pay; // 지불금액

    private String phone;

    private String address;

}
