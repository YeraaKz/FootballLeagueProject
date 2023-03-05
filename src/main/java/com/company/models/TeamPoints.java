package com.company.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamPoints implements Comparable<TeamPoints>{
    private Team team;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int scored;
    private int conceded;
    private int points;

    @Override
    public int compareTo(TeamPoints o) {
        if (this.getPoints() == o.getPoints()) {
            return o.getScored() - this.getScored();
        }
        return o.getPoints() - this.getPoints();
    }
}
