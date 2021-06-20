package controller;

import Enum.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Cell;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {

    final int size = 8;
    @FXML private GridPane gridPane;
    private Cell cellScreen[][] = new Cell[size][size];
    private String stringScreen[][] = new String[size][size];
    private Button[][] buttonArr = new Button[size][size];

    // apply stringScreen Changes to cellScreen
    void syncArrs (){

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (stringScreen[i][j].equals("e")) {
                    cellScreen[i][j].setType(Type.empty);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/empty.png')");
                }
                else if (stringScreen[i][j].equals("b")) {
                    cellScreen[i][j].setType(Type.black);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/black.png')");

                }
                else if (stringScreen[i][j].equals("w")) {
                    cellScreen[i][j].setType(Type.white);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/white.png')");

                }
                else if (stringScreen[i][j].equals("c")) {
                    cellScreen[i][j].setType(Type.canSelected);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/canSelected.png')");

                }
            }
        }


    }

    // apply arr Changes to Graphic button

//    void syncGraphic (){
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//
//                if (stringScreen[i][j].equals("e"))
//                    cellScreen[i][j].setType(Type.empty);
//                else if (stringScreen[i][j].equals("b"))
//                    cellScreen[i][j].setType(Type.black);
//                else if (stringScreen[i][j].equals("w"))
//                    cellScreen[i][j].setType(Type.white);
//                else if (stringScreen[i][j].equals("c"))
//                    cellScreen[i][j].setType(Type.canSelected);
//            }
//        }
//
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // fill the arrs
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Button button = new Button("");
                button.setMaxWidth(Double.MAX_VALUE);
                button.setPrefHeight(Double.MAX_VALUE);

                button.setStyle("-fx-background-image: url('/Images/empty.png')");

                buttonArr[i][j] = button;

                stringScreen[i][j] = "";

                Cell cell = new Cell(i , j , Type.empty);
                cellScreen[i][j] = cell;

            }
        }


        //   init screen and add buttons to gridPane
        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {


                gridPane.add( buttonArr[i][j], i , j , 1 , 1);
            }
        }
        gridPane.setGridLinesVisible(true);


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i == 3 && j == 3 || i == 4 && j == 4)
                    stringScreen[i][j] = "b";
                else if (i == 3 && j == 4 || i == 4 && j == 3)
                    stringScreen[i][j] = "w";
                else
                    stringScreen[i][j] = "e";
            }

            syncArrs();

        }


    }
}
