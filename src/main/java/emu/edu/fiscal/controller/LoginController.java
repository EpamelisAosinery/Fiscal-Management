package emu.edu.fiscal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import java.util.Objects;

public class LoginController {
    @FXML
    private TextField txtFld_UserName;
    @FXML
    private PasswordField pswFld_Password;
    @FXML
    private Button btn_Login;
    @FXML
    private Button btn_Register;
    @FXML
    private Label lbl_ErrorMessage;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public LoginController() { }


    public void btnLoginOnClick(ActionEvent event) {

        if (isEmpty(txtFld_UserName, pswFld_Password)) {
            //displayErrorMessage("Successfully login");

            if (event.getSource() == btn_Login) {
                String username = txtFld_UserName.getText();
                String password = pswFld_Password.getText();
                //goToDashBoard(event);


                UserInfo user = new UserInfo();
                try {
                    if (user.authentication(username, password))
                        goToDashBoard(event);

                    else
                        displayErrorMessage("Username or Password does not exist");
                } catch (Exception e) {
                }
            }
        }
    }


    public void btnRegisterOnClick(ActionEvent event) throws IOException{
        if (event.getSource() == btn_Register) {
            goToRegisterUser(event);
        }
    }

    private boolean isEmpty(TextField txtFld_UserName, PasswordField pswFld_Password) {
        if (txtFld_UserName.getText().isEmpty() && pswFld_Password.getText().isEmpty()) {
            displayErrorMessage("Username and Password is empty");
            txtFld_UserName.requestFocus();
            return false;
        }
        else if (txtFld_UserName.getText().isEmpty()) {
            displayErrorMessage("Username is required");
            txtFld_UserName.requestFocus();
            return false;
        }
        else if (pswFld_Password.getText().isEmpty()) {
            displayErrorMessage("Password is required");
            pswFld_Password.requestFocus();
            return false;
        }

        return true;
    }

    public void goToDashBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("DashBoard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToRegisterUser(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("RegisterUser.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void displayErrorMessage(String message) {
        lbl_ErrorMessage.setText("");
        lbl_ErrorMessage.setText(message);
    }
}
