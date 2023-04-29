package com.example.aisi;

import com.example.aisi.Repository.HttpRequests;
import com.example.aisi.model.Team;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamController implements Initializable {

    @FXML
    private Button btnAddTeam;

    public Button getBtnAddTeam() {
        return btnAddTeam;
    }

    @FXML
    private TextField txtTeamName;

    public TextField getTxtTeamName() {
        return txtTeamName;
    }

    private HttpRequests requests = new HttpRequests();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
