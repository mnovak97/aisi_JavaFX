package com.example.aisi.Repository;

import com.example.aisi.model.Player;
import com.example.aisi.model.Team;


public interface HttpInterface {
    Team[] getTeams();
    Player[] getTeamPlayers(int teamId);
    void addPlayer(Player newPlayer);
    void addTeam(Team newTeam);
    void deletePlayer(int idPlayer);
    void updatePlayer(Player player);
    Team getTeamById(int id);

}
