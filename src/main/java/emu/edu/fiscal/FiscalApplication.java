package emu.edu.fiscal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class FiscalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //String sql = "INSERT INTO";


        FXMLLoader fxmlLoader = new FXMLLoader(FiscalApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("LoginHH.css");

        stage.setTitle("Fiscal Management System");
        stage.setResizable(false);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}