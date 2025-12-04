package com.example.golfclub.repository;

import com.example.golfclub.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByMembershipTypeIgnoreCase(String membershipType);

    List<Member> findByPhoneNumber(String phoneNumber);

    List<Member> findByMembershipStartDate(LocalDate date);
}
