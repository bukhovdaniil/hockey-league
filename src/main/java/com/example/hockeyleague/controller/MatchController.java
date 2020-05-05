package com.example.hockeyleague.controller;

import com.example.hockeyleague.exception.NotFoundException;
import com.example.hockeyleague.model.Match;
import com.example.hockeyleague.repository.MatchRepository;
import com.example.hockeyleague.repository.TeamRepository;
import com.example.hockeyleague.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @GetMapping("/matches")
    public Page<Match> getMatches(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    @GetMapping("/matches/{matchId}")
    public Match getMatchById(@PathVariable Long matchId) {
        return matchRepository.findByMatchId(matchId)
                .orElseThrow(() -> new NotFoundException("Match with id " + matchId + " doesn't exist."));
    }

    @PostMapping("/tournaments/{tournamentId}/matches")
    public Match createMatch(@PathVariable Long tournamentId, @RequestBody Match match) {
        return tournamentRepository.findByTournamentId(tournamentId)
                .map(tournament -> {
                    match.setTournament(tournament);
                    return matchRepository.save(match);
                }).orElseThrow(() -> new NotFoundException("Tournament with id " + tournamentId + " doesn't exist."));
    }

    @PutMapping("/matches/{matchId}")
    public Match updateMatch(@PathVariable Long matchId, @RequestBody Match matchRequest) {
        return matchRepository.findByMatchId(matchId)
                .map(match -> {
                    match.setMatchDate(matchRequest.getMatchDate());
                    match.setFirstTeam(matchRequest.getFirstTeam());//TODO будет ли это работать с оьбъектами или надо перебить на айдишники
                    match.setSecondTeam(matchRequest.getSecondTeam());
                    match.setGoalsScoredFirstTeam(matchRequest.getGoalsScoredFirstTeam());
                    match.setGoalsScoredSecondTeam(matchRequest.getGoalsScoredSecondTeam());
                    match.setBulletsScoredFirstTeam(matchRequest.getBulletsScoredFirstTeam());
                    match.setBulletsScoredSecondTeam(matchRequest.getBulletsScoredSecondTeam());
                    match.setOvertime(matchRequest.getOvertime());
                    match.setTournament(matchRequest.getTournament());
                    return matchRepository.save(match);
                }).orElseThrow(() -> new NotFoundException("Match with id " + matchId + " doesn't exist."));
    }

    @DeleteMapping("/matches/{matchId}")
    public ResponseEntity<?> deleteMatch(@PathVariable Long matchId) {
        return matchRepository.findByMatchId(matchId)
                .map(match -> {
                    matchRepository.delete(match);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new NotFoundException("Match with id " + matchId + " doesn't exist."));
    }
}
