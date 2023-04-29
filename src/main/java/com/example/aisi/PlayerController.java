package com.example.aisi;

import com.example.aisi.Repository.HttpRequests;
import com.example.aisi.model.Player;
import com.example.aisi.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private Button button;
    public Button getButton() {
        return button;
    }
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private ComboBox<Team> teamComboBox;
    private HttpRequests requests = new HttpRequests();

    public void onAddPLayerClicked() {
        Team selectedTeam = teamComboBox.getSelectionModel().getSelectedItem();
        Player player = new Player(txtFirstName.getText(),txtLastName.getText(),selectedTeam.getId());
        requests.addPlayer(player);
    }

    public void onUpdatePlayerClicked(Player player) {
        Team selectedTeam = teamComboBox.getSelectionModel().getSelectedItem();
        player.setFirstName(txtFirstName.getText());
        player.setLastName(txtLastName.getText());
        player.setTeamId(selectedTeam.getId());
        requests.updatePlayer(player);
    }
    public void setUpdateData(Player player,Team team) {
        txtFirstName.setText(player.getFirstName());
        txtLastName.setText(player.getLastName());
        teamComboBox.setValue(team);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Team[] teams = requests.getTeams();
        ObservableList<Team> teamList = FXCollections.observableArrayList(teams);
        teamComboBox.setItems(teamList);
    }
}
