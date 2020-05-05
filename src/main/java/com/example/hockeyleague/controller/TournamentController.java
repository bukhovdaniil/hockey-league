package com.example.hockeyleague.controller;

import com.example.hockeyleague.exception.NotFoundException;
import com.example.hockeyleague.model.Tournament;
import com.example.hockeyleague.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TournamentController  {
    @Autowired
    private TournamentRepository tournamentRepository;

    @GetMapping("/tournaments")
    public Page<Tournament> getTournaments(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }

    @GetMapping("/tournaments/{tournamentId}")
    public Tournament getTournamentById(@PathVariable Long tournamentId) {
        return tournamentRepository.findByTournamentId(tournamentId)
                .orElseThrow(() -> new NotFoundException("Tournament with id " + tournamentId + " doesn't exist."));
    }

    @PutMapping("/tournaments/{tournamentId}")
    public Tournament updateTournament(@PathVariable Long tournamentId,@RequestBody Tournament tournamentRequest) {
        return tournamentRepository.findByTournamentId(tournamentId)
                .map(tournament -> {
                    tournament.setName(tournamentRequest.getName());
                    tournament.setShortName(tournamentRequest.getShortName());
                    tournament.setStart(tournamentRequest.getStart());
                    tournament.setEnd(tournamentRequest.getEnd());
                    return tournamentRepository.save(tournament);
                }).orElseThrow(() -> new NotFoundException("Tournament with id " + tournamentId + " doesn't exist."));
    }

    @PostMapping("/tournaments")
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @DeleteMapping("/tournaments/{tournamentId}")
    public ResponseEntity<?> deleteTournament(@PathVariable Long tournamentId) {
        return tournamentRepository.findByTournamentId(tournamentId)
                .map(tournament -> {
                    tournamentRepository.delete(tournament);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new NotFoundException("Tournament with id " + tournamentId + " doesn't exist."));
    }
}
