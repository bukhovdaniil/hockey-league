package com.example.hockeyleague.model;

import com.example.hockeyleague.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Team extends DateAudit {//TODO DateAudit
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    private String name;//TODO: хорошо ли это что name immutable, но по нему быстрый поиск

    @NotBlank
    @Column(name = "short_name")
    @Size(max = 3)
    private String shortName;//TODO: хорошо ли это что name immutable, но по нему быстрый поиск

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)//TODO: true or not
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;*/

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "teams")
    private Set<Tournament> tournaments = new HashSet<>();

    @OneToMany(mappedBy = "firstTeam", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Match> homeMatches = new HashSet<>();

    @OneToMany(mappedBy = "secondTeam", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Match> awayMatches = new HashSet<>();

    public Team() {};

    public Team(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
        //this.stadium = stadium;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

//    public Stadium getStadium() {
//        return stadium;
//    }
//
//    public void setStadium(Stadium stadium) {
//        this.stadium = stadium;
//    }
}
