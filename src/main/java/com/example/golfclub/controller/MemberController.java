package com.example.golfclub.controller;

import com.example.golfclub.entity.Member;
import com.example.golfclub.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Basic Member REST API.
 *
 * NOTE:
 *   Some endpoints are implemented for you as examples.
 *   You should add/adjust endpoints and behaviour to fully
 *   meet the assignment requirements and your own design.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Create a new member
    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    // Get all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Get member by id
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Example search endpoints ---

    // Search by name
    @GetMapping("/search/by-name")
    public List<Member> searchByName(@RequestParam String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    // TODO: you can add more endpoints if you want (by phone, start-date, etc.)
    @GetMapping("/search/by-type")
    public List<Member> searchByType(@RequestParam String membershipType) {
        return memberRepository.findByMembershipTypeIgnoreCase(membershipType);
    }

    @GetMapping("/search/by-phone")
    public List<Member> searchByPhone(@RequestParam String phone) {
        return memberRepository.findByPhoneNumber(phone);
    }

    @GetMapping("/search/by-start-date")
    public List<Member> searchByStartDate(@RequestParam String date) {
        LocalDate parsed = LocalDate.parse(date);
        return memberRepository.findByMembershipStartDate(parsed);
    }
}
