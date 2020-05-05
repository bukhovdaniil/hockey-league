package com.example.hockeyleague.model;

import com.example.hockeyleague.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "hockey_match")
public class Match extends DateAudit {//TODO extends DateAudit (with createdBy/updatedBy
    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    //TODO how to show that this id ref for Team(Entity)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_team_id", referencedColumnName = "team_id", nullable = false)
    private Team firstTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_team_id", referencedColumnName = "team_id", nullable = false)
    private Team secondTeam;

    @Column(name = "match_date", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp matchDate;

    @Column(name = "gf", nullable = false)//TODO: Делать эти поля обязательными наверное не очень верно
    private Integer goalsScoredFirstTeam;

    @Column(name = "gs", nullable = false)
    private Integer goalsScoredSecondTeam;

    @NotNull
    private Boolean overtime;

    @Column(name = "bf")
    private Integer bulletsScoredFirstTeam;

    @Column(name = "bs")
    private Integer bulletsScoredSecondTeam;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Timestamp getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Timestamp matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getGoalsScoredFirstTeam() {
        return goalsScoredFirstTeam;
    }

    public void setGoalsScoredFirstTeam(Integer goalsScoredFirstTeam) {
        this.goalsScoredFirstTeam = goalsScoredFirstTeam;
    }

    public Integer getGoalsScoredSecondTeam() {
        return goalsScoredSecondTeam;
    }

    public void setGoalsScoredSecondTeam(Integer goalsScoredSecondTeam) {
        this.goalsScoredSecondTeam = goalsScoredSecondTeam;
    }

    public Boolean getOvertime() {
        return overtime;
    }

    public void setOvertime(Boolean overtime) {
        this.overtime = overtime;
    }

    public Integer getBulletsScoredFirstTeam() {
        return bulletsScoredFirstTeam;
    }

    public void setBulletsScoredFirstTeam(Integer bulletsScoredFirstTeam) {
        this.bulletsScoredFirstTeam = bulletsScoredFirstTeam;
    }

    public Integer getBulletsScoredSecondTeam() {
        return bulletsScoredSecondTeam;
    }

    public void setBulletsScoredSecondTeam(Integer bulletsScoredSecondTeam) {
        this.bulletsScoredSecondTeam = bulletsScoredSecondTeam;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
