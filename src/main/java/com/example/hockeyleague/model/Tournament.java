package com.example.hockeyleague.model;

import com.example.hockeyleague.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournament", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Tournament extends DateAudit {//TODO extends DateAudit...
    @Id
    @Column(name = "tournament_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    private String name; //TODO: хорошо ли это что name immutable, но по нему быстрый поиск

    @NotBlank
    @Column(name = "short_name")
    @Size(max = 10)
    private String shortName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tournament_teams",
            joinColumns = { @JoinColumn(name = "tournament_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") })
    private Set<Team> teams = new HashSet<>();

    @Column(nullable = false)
    private Timestamp start;

    @Column(nullable = false)
    private Timestamp end;

    @OneToMany(mappedBy = "tournament")
    private Set<Match> matches = new HashSet<>();

    public Tournament() {}

    public Tournament(String name, String shortName, Timestamp start, Timestamp end) {
        this.name = name;
        this.shortName = shortName;
        this.start = start;
        this.end = end;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
