package com.company.services;

import com.company.models.Match;
import com.company.models.Team;
import com.company.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository){
        this.matchRepository = matchRepository;
    }

    public List<Match> findByHomeTeam(Team homeTeam){
        return matchRepository.findByHomeTeam(homeTeam);
    }

    public List<Match> findByAwayTeam(Team awayTeam){
        return matchRepository.findByAwayTeam(awayTeam);
    }

    @Transactional
    public void save(Match match){
        matchRepository.save(match);
    }
}
