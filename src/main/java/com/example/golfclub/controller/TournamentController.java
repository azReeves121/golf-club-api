package com.example.golfclub.controller;

import com.example.golfclub.entity.Member;
import com.example.golfclub.entity.Tournament;
import com.example.golfclub.repository.MemberRepository;
import com.example.golfclub.repository.TournamentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Basic Tournament REST API.
 *
 * NOTE:
 *   As with MemberController, some endpoints are implemented
 *   and others you can tweak/extend as needed.
 */
@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentRepository tournamentRepository;
    private final MemberRepository memberRepository;

    public TournamentController(TournamentRepository tournamentRepository,
                                MemberRepository memberRepository) {
        this.tournamentRepository = tournamentRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/by-start-date")
    public List<Tournament> searchByStartDate(@RequestParam String date) {
        LocalDate parsed = LocalDate.parse(date);
        return tournamentRepository.findByStartDate(parsed);
    }

    @GetMapping("/search/by-location")
    public List<Tournament> searchByLocation(@RequestParam String location) {
        return tournamentRepository.findByLocationContainingIgnoreCase(location);
    }

    // Add a member to a tournament
    @PostMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<Tournament> addMemberToTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId) {

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tournament not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        tournament.getMembers().add(member);
        Tournament updated = tournamentRepository.save(tournament);
        return ResponseEntity.ok(updated);
    }

    // Get all members in a tournament
    @GetMapping("/{tournamentId}/members")
    public ResponseEntity<Set<Member>> getMembersInTournament(@PathVariable Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .map(tournament -> ResponseEntity.ok(tournament.getMembers()))
                .orElse(ResponseEntity.notFound().build());
    }
}
