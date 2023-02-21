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

    public void btnLoginOnClick(ActionEvent event) throws IOException, SQLException {
        if (isEmpty(txtFld_UserName, pswFld_Password)) {
            displayErrorMessage("Successfully login");
            if (event.getSource() == btn_Login) {
                String username = txtFld_UserName.getText();
                String password = pswFld_Password.getText();
                //goToDashBoard(event);

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fiscaldb", "root", "password");
                    //Statement statement = connection.createStatement();
                    PreparedStatement psInsert = null;
                    PreparedStatement psCheckUserExists = null;
                    PreparedStatement psCheckPassExists = null;
                    ResultSet resultSet = null;

                    psCheckUserExists = connection.prepareStatement("SELECT * FROM userAuth WHERE username = ?");
                    psCheckUserExists.setString(1, username);

                    resultSet = psCheckUserExists.executeQuery();

                    if(resultSet.isBeforeFirst()) {
                        while(resultSet.next()) {
                            String retrievePassword = resultSet.getString("password");
                            if (retrievePassword.equals(password)) {
                                System.out.println("Exists");
                                goToDashBoard(event);
                            }
                            else
                                displayErrorMessage("Password not exist");
                        }
                    }

                    else {
                        displayErrorMessage("User not exists");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
