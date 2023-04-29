package com.example.aisi.Repository;

import com.example.aisi.Utils.HttpMethod;
import com.example.aisi.model.Player;
import com.example.aisi.model.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequests implements HttpInterface {
    @Override
    public Team[] getTeams() {
        Gson gson = new Gson();
        Type teamArrayType = new TypeToken<Team[]>() {}.getType();
        Team[] teams = gson.fromJson(String.valueOf(Get("http://localhost:5152/api/Teams")), teamArrayType);

        return  teams;
    }

    @Override
    public Player[] getTeamPlayers(int teamId) {
        Gson gson = new Gson();
        Type playerArrayType = new TypeToken<Player[]>(){}.getType();
        Player[] players = gson.fromJson(String.valueOf(Get("http://localhost:5152/api/Teams/" + teamId + "/Players")), playerArrayType);

        return players;
    }

    @Override
    public void addPlayer(Player newPlayer) {
        Gson gson = new Gson();
        String json = gson.toJson(newPlayer);
        Post("http://localhost:5152/api/Players",json);
    }

    @Override
    public void addTeam(Team newTeam) {
        Gson gson = new Gson();
        String json = gson.toJson(newTeam);
        Post("http://localhost:5152/api/Teams",json);
    }

    @Override
    public void deletePlayer(int idPlayer) {
        Delete("http://localhost:5152/api/Players/" + idPlayer);
    }

    @Override
    public void updatePlayer(Player player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        Put("http://localhost:5152/api/Players/"+ player.getId(),json);
    }

    @Override
    public Team getTeamById(int id) {
        Gson gson = new Gson();
        Type teamArrayType = new TypeToken<Team>() {}.getType();
        Team team = gson.fromJson(String.valueOf(Get("http://localhost:5152/api/Teams/" + id)), teamArrayType);

        return team;
    }

    private void Put(String url,String jsonObject) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(HttpMethod.PUT);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            byte[] requestBodyBytes = jsonObject.getBytes(StandardCharsets.UTF_8);
            con.setDoOutput(true);


            OutputStream outputStream = con.getOutputStream();
            outputStream.write(requestBodyBytes);
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            String response = responseBuilder.toString();
            System.out.println(response);

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Delete(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(HttpMethod.DELETE);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response;
            StringBuffer responseBuffer = new StringBuffer();
            while ((response = in.readLine()) != null) {
                responseBuffer.append(response);
            }
            in.close();

            System.out.println(responseBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuffer Get(String url) {
        StringBuffer response = new StringBuffer();
        try {
            URL requestUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestMethod(HttpMethod.GET);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return response;
    }

    private void Post(String requestUrl,String jsonObject) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            byte[] requestBodyBytes = jsonObject.getBytes(StandardCharsets.UTF_8);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(requestBodyBytes);
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            String response = responseBuilder.toString();
            System.out.println(response);

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
