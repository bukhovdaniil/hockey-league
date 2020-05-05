package com.example.hockeyleague.controller;

import com.example.hockeyleague.exception.NotFoundException;
import com.example.hockeyleague.model.Team;
import com.example.hockeyleague.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/teams")
    public Page<Team> getTeams(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @GetMapping("/teams/{teamId}")
    public Team getTeamById(@PathVariable Long teamId) {
        return teamRepository.findByTeamId(teamId)
                .orElseThrow(() -> new NotFoundException("Team with id " + teamId + " doesn't exist."));
    }

    @PostMapping("/teams")
    public Team createTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @PutMapping("/teams/{teamId}")
    public Team updateTeam(@PathVariable Long teamId, @RequestBody Team teamRequest) {
        return teamRepository.findByTeamId(teamId)
                .map(team -> {
                    team.setName(teamRequest.getName());
                    team.setShortName(teamRequest.getShortName());
                    return teamRepository.save(team);
                }).orElseThrow(() -> new NotFoundException("Team with id " + teamId + " doesn't exist."));
    }

    @DeleteMapping("teams/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) {
        return teamRepository.findByTeamId(teamId)
                .map(team -> {
                    teamRepository.delete(team);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new NotFoundException("Team with id " + teamId + " doesn't exist."));
    }
}
