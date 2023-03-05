package com.company.repositories;

import com.company.models.Match;
import com.company.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findByHomeTeam(Team homeTeam);
    List<Match> findByAwayTeam(Team awayTeam);
}
