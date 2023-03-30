package emu.edu.fiscal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterUserController {
    @FXML
    private Button btn_Back;

    private Parent root;
    private Stage stage;
    private Scene scene;


    private void btnLoginOnClick(ActionEvent event) throws IOException{
        if (event.getSource() == btn_Back) {
            goToLoginScene(event);
        }
    }

    private void goToLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
