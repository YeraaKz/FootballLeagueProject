package com.company.controllers;

import com.company.models.Match;
import com.company.models.Team;
import com.company.models.TeamPoints;
import com.company.services.MatchService;
import com.company.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/football")
public class MatchController {
    private final TeamService teamService;
    private final MatchService matchService;

    @Autowired
    public MatchController(TeamService teamService,
                           MatchService matchService){
        this.teamService = teamService;
        this.matchService = matchService;
    }

    @GetMapping()
    public String showTable(Model model){
        List<Team> teamList = teamService.findAll();
        List<TeamPoints> teamPointsList = new ArrayList<>();
        for (Team team : teamList) {
            int points = 0;
            int played = 0;
            int won = 0;
            int drawn = 0;
            int lost = 0;
            int scored = 0;
            int conceded = 0;
            List<Match> homeMatches = matchService.findByHomeTeam(team);
            List<Match> awayMatches = matchService.findByAwayTeam(team);
            for (Match match : homeMatches) {
                played++;
                scored += match.getHomeTeamGoals();
                conceded += match.getAwayTeamGoals();
                if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
                    points += 3;
                    won++;
                } else if (match.getHomeTeamGoals() == match.getAwayTeamGoals()) {
                    points++;
                    drawn++;
                } else {
                    lost++;
                }
            }
            for (Match match : awayMatches) {
                played++;
                scored += match.getAwayTeamGoals();
                conceded += match.getHomeTeamGoals();
                if (match.getAwayTeamGoals() > match.getHomeTeamGoals()) {
                    points += 3;
                    won++;
                } else if (match.getAwayTeamGoals() == match.getHomeTeamGoals()) {
                    points++;
                    drawn++;
                } else {
                    lost++;
                }
            }
            TeamPoints teamPoints = new TeamPoints(team, played, won, drawn, lost, scored, conceded, points);
            teamPointsList.add(teamPoints);
        }
        Collections.sort(teamPointsList);
        model.addAttribute("teamPointsList", teamPointsList);
        return "table";
    }

    @PostMapping("/teams")
    public String addTeam(@RequestParam("name") String name,
                          @RequestParam("country") String country,
                          @RequestParam("city") String city){
        Team team = new Team();
        team.setName(name);
        team.setCountry(country);
        team.setCity(city);
        teamService.save(team);

        return "redirect:/football";
    }
    @GetMapping("newMatch")
    public String newMatchForm(Model model, @ModelAttribute("match") Match match){
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        return "createMatch";
    }

    @PostMapping("/matches")
    public String addMatch(@ModelAttribute("match") Match match,
                           @RequestParam("awayTeamId") int awayTeamId,
                           @RequestParam("homeTeamId") int homeTeamId){
        match.setHomeTeam(teamService.findOne(homeTeamId).orElse(null));
        match.setAwayTeam(teamService.findOne(awayTeamId).orElse(null));
        matchService.save(match);
        return "redirect:/football";
    }
}
