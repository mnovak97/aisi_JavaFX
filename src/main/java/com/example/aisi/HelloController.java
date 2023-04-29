package com.example.aisi;

import com.example.aisi.Repository.HttpRequests;
import com.example.aisi.Utils.Constants;
import com.example.aisi.model.Player;
import com.example.aisi.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button btnAddTeam;

    @FXML
    private Button btnAddPlayer;

    @FXML
    private ComboBox<Team> teamComboBox;
    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> lastNameColumn;


    HttpRequests requests = new HttpRequests();

    @FXML
    protected void onAddPlayerClicked() {
       startNewWindow("player-view.fxml","New player", Constants.PlayerAdd);
    }

    @FXML
    protected void onAddTeamClicked() {
        startNewWindow("team-view.fxml","New team",Constants.TeamAdd);
    }

    @FXML
    protected void onDeleteClicked() {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        requests.deletePlayer(player.getId());
        loadPlayersForTeam();
    }

    @FXML
    protected void onUpdateClicked() {
        startNewWindow("player-view.fxml","Update player",Constants.PlayerUpdate);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Player,String>("lastName"));
        loadComboBox();
    }

    public void startNewWindow(String windowName, String title, String type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            switch (type) {
                case Constants.TeamAdd -> {
                    TeamController teamController = fxmlLoader.getController();
                    Button childButton = teamController.getBtnAddTeam();
                    TextField txtName = teamController.getTxtTeamName();
                    childButton.setOnAction(event -> {
                        Team team = new Team(txtName.getText());
                        requests.addTeam(team);
                        stage.close();
                        loadComboBox();
                    });
                }
                case Constants.PlayerAdd -> {
                    PlayerController playerController = fxmlLoader.getController();
                    Button btnAddPlayer = playerController.getButton();
                    btnAddPlayer.setOnAction(event -> {
                        playerController.onAddPLayerClicked();
                        stage.close();
                        loadPlayersForTeam();
                    });
                }
                case Constants.PlayerUpdate -> {
                    PlayerController playerController = fxmlLoader.getController();
                    Button button = playerController.getButton();
                    button.setText("Update player");
                    Player player = playerTable.getSelectionModel().getSelectedItem();
                    Team team = requests.getTeamById(player.getTeamId());
                    playerController.setUpdateData(player,team);
                    button.setOnAction(event -> {
                        playerController.onUpdatePlayerClicked(player);
                        stage.close();
                        loadPlayersForTeam();
                    });
                }
            }
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBox() {
        Team[] teams = requests.getTeams();
        ObservableList<Team> teamList = FXCollections.observableArrayList(teams);
        teamComboBox.setItems(teamList);
        teamComboBox.setOnAction(event -> {
            loadPlayersForTeam();
        });
    }
    public void loadPlayersForTeam() {
        Team selectedTeam = teamComboBox.getSelectionModel().getSelectedItem();
        Player[] players = requests.getTeamPlayers(selectedTeam.getId());
        ObservableList<Player> playerObservableList = FXCollections.observableArrayList(players);
        playerTable.setItems(playerObservableList);
    }
}