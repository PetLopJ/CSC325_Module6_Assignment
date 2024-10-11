package com.example.csc325_firebase_webview_auth.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


import static com.example.csc325_firebase_webview_auth.view.App.loadFXML;

public class RegistrationController {

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField password;
    @FXML
    private Button enter;

    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        Parent root = loadFXML("/files/AccessFBView.fxml");
        Scene scene = new Scene(root);
        Stage stage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/files/GUI.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
