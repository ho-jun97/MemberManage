package com.Study.MemberManage.repository;

import com.Study.MemberManage.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Page<Member> findByNameContaining(String searchKeyword, Pageable pageable);
}
