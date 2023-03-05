package com.company.services;

import com.company.models.Team;
import com.company.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public Optional<Team> findOne(int id){
        return teamRepository.findById(id);
    }

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    @Transactional
    public void save(Team team){
        teamRepository.save(team);
    }


}
