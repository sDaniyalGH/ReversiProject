package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {


    @FXML private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gridPane.getChildren().clear();
        int size = 8;


//        Image emptyImage = null;
//        try {
//             emptyImage = new Image(new File("src/Images/empty.png").toURL().toString());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }



        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {


                //ImageView imageView = new ImageView(button);
                Button button = new Button("");


                button.setStyle("-fx-background-image: url('/Images/white.png')");


                button.setPrefWidth(500);
                button.setPrefHeight(500);


                gridPane.add( button, i , j , 1 , 1);
            }
        }
        gridPane.setGridLinesVisible(true);
    }
}
