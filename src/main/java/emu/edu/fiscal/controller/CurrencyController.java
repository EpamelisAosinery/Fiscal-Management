package emu.edu.fiscal.controller;

import emu.edu.fiscal.model.CCurrency;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class CurrencyController implements Initializable {
    @FXML
    private ComboBox<String> cmb_CurrencyInput;
    @FXML
    private ComboBox<String> cmb_CurrencyOutput;
    @FXML
    private TextField txtFld_Input;
    @FXML
    private TextField txtFld_Output;
    @FXML
    private Button btn_Result;
    @FXML
    public Label lbl_CurrencyRate;

    private final CCurrency[] currencies = CCurrency.values();

    private Parent root;
    private Stage stage;
    private Scene scene;

    public CurrencyController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (CCurrency type : currencies) {
            cmb_CurrencyInput.getItems().add(String.valueOf(type));
            cmb_CurrencyOutput.getItems().add(String.valueOf(type));
        }
    }
}
