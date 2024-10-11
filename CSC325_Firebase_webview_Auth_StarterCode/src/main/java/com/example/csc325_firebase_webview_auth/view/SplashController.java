package com.example.csc325_firebase_webview_auth.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.csc325_firebase_webview_auth.view.App.loadFXML;

public class SplashController {

    @FXML
    private Button register;

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
        Parent root = loadFXML("/files/Registration.fxml");
        Scene scene = new Scene(root);
        Stage stage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
