package controller;

import Enum.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.CellNeighbor;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Game implements Initializable {

    Turn currentTurn = Turn.black;
    final int size = 8;
    @FXML private GridPane gridPane;
    @FXML private Label blackScoreTV;
    @FXML private Label whiteScoreTV;    //private Cell cellScreen[][] = new Cell[size][size];
    private String stringScreen[][] = new String[size][size];
    private Button[][] buttonArr = new Button[size][size];
    ArrayList<CellNeighbor> hamsayeHa;
    ArrayList<CellNeighbor> canSelected = new ArrayList<>();
    int whiteScore = 0;
    int blackScore = 0;



    void toggleTurn(){

        if (currentTurn == Turn.black)
            currentTurn = Turn.white;
        else
            currentTurn = Turn.black;
    }
    // return hamsayeHaye node
    ArrayList<CellNeighbor> findHamsayeHa (int i , int j) {

        ArrayList<CellNeighbor> hamsayeHa = new ArrayList<>();

        int I;
        int J;

        if (i != 0 && j != 0){
        I = i - 1;
        J = j - 1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J, Direction.downRight);
            hamsayeHa.add(position1);
        }
    }

        if (i != 0 ) {

            I = i - 1;
            J = j;
            if (I >= 0 && I < size && J >= 0 && J < size) {
                CellNeighbor position1 = new CellNeighbor(I, J, Direction.down);
                hamsayeHa.add(position1);
            }
        }

        if (i != 0 && j != size-1){

        I = i-1;
        J = j+1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.downLeft);
            hamsayeHa.add(position1);
        }
        }

        I = i;
        J = j-1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.right);
            hamsayeHa.add(position1);
        }

        I = i;
        J = j+1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.left);
            hamsayeHa.add(position1);
        }

        I = i+1;
        J = j-1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.topRight);
            hamsayeHa.add(position1);
        }

        I = i+1;
        J = j;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.top);
            hamsayeHa.add(position1);
        }

        I = i+1;
        J = j+1;
        if (I >= 0 && I < size && J >= 0 && J < size) {
            CellNeighbor position1 = new CellNeighbor(I, J , Direction.topLeft);
            hamsayeHa.add(position1);
        }

        return hamsayeHa;

    }

    void setCanSelectedBtn(){

        String current;
        String zed;
        if (currentTurn == Turn.black) {

             current = "b";
             zed = "w";

        } else {
             current = "w";
             zed = "b";

        }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    if (stringScreen[i][j].equals(zed)){


                        hamsayeHa = findHamsayeHa(i,j);

                        // find empty hamsayeHa
                        for (CellNeighbor posHamsaye : hamsayeHa) {

                            if (stringScreen[posHamsaye.getI()][posHamsaye.getJ()].equals("e")){

                                // check : can it be selected ?
                                boolean status = false;
                                int currentI = posHamsaye.getI();
                                int currentJ = posHamsaye.getJ();

                                switch (posHamsaye.getMove()){

                                    case downRight: {

                                        while (true) {

                                            currentI++;
                                            currentJ++;


                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {

                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                        } else break;

                                        }
                                        break;

                                    }

                                    case down: {

                                        while (true){

                                            currentI++;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;

                                        }
                                        break;

                                    }

                                    case downLeft: {

                                        while (true){

                                            currentI++;
                                            currentJ--;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;

                                        }
                                        break;

                                    }

                                    case right: {

                                        while (true){

                                            currentJ++;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;

                                        }
                                        break;

                                    }
                                    case left: {

                                        while (true){

                                            currentJ--;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;

                                        }
                                        break;

                                    }
                                    case topRight: {

                                        while (true){

                                            currentJ++;
                                            currentI--;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;


                                    }
                                        break;

                                    }
                                    case top: {

                                        while (true){

                                            currentI--;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            }  else break;


                                    }
                                        break;

                                    }
                                    case topLeft: {

                                        while (true){

                                            currentI--;
                                            currentJ--;

                                            if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                if (stringScreen[currentI][currentJ].equals(zed)) {
                                                    CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                    posHamsaye.getReverseBaze().add(reversedPos);
                                                } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                    status = true;
                                                    break;
                                                } else if (stringScreen[currentI][currentJ].equals("e")) {
                                                    break;
                                                }
                                            } else break;

                                        }
                                        break;

                                    }




                                }

                                if (status) {
                                    canSelected.add(posHamsaye);
                                    stringScreen[posHamsaye.getI()][posHamsaye.getJ()] = "c";
                                }
                            }
                        }
                    }
                }
            }
        syncArrs();

            if (canSelected.size() == 0 && whiteScore + blackScore != size*size){

                toggleTurn();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                String msg = "Turn Passed !! turn : ";

                if (currentTurn == Turn.black)
                    msg += "black";
                else
                    msg += "white";
                alert.setContentText(msg);
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.OK)){
                    setCanSelectedBtn();
                    countScore();

                }

            }

    }


    // apply stringScreen Changes to cellScreen
    void syncArrs (){

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (stringScreen[i][j].equals("e")) {
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/empty.png')");
                }
                else if (stringScreen[i][j].equals("b")) {
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/black.png')");

                }
                else if (stringScreen[i][j].equals("w")) {
                    //cellScreen[i][j].setType(Type.white);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/white.png')");

                }
                else if (stringScreen[i][j].equals("c")) {
                    //cellScreen[i][j].setType(Type.canSelected);
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/canSelected.png')");

                }
            }
        }


    }


    void buttonClicked(Button button , int i , int j){


        button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (stringScreen[i][j].equals("c")){


                            String current;
                            if (currentTurn == Turn.black) {

                                current = "b";

                            } else {
                                current = "w";
                            }
                                stringScreen[i][j] = current;


                                // reversed
                                for (CellNeighbor eachNeighbor  : canSelected) {
                                    if (eachNeighbor.getI() == i && eachNeighbor.getJ() == j){


                                        for (CellNeighbor reversedCells : eachNeighbor.getReverseBaze()) {
                                            stringScreen[reversedCells.getI()][reversedCells.getJ()] = current;

                                        }
                                        break;
                                    }
                                }
                                toggleTurn();


                             // canSelected -->empty
                            for (int k = 0; k < size; k++) {
                                for (int l = 0; l < size; l++) {

                                    if (stringScreen[k][l].equals("c")){
                                        stringScreen[k][l] = "e";
                                    }

                                }
                            }
                            syncArrs();
                            hamsayeHa.clear();
                            canSelected.clear();
                            countScore();
                            setCanSelectedBtn();


                        }
                    }
                });

    }

    void countScore (){

        blackScore = 0;
        whiteScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (stringScreen[i][j].equals("b"))
                    blackScore++;
                else if (stringScreen[i][j].equals("w"))
                    whiteScore++;
            }
        }

        whiteScoreTV.setText(String.valueOf(whiteScore));
        blackScoreTV.setText(String.valueOf(blackScore));

        // check winner
        if (whiteScore + blackScore == size*size){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (whiteScore > blackScore){
                // white wins
                alert.setContentText("White wins");

            } else if (blackScore > whiteScore){
                // black wins
                alert.setContentText("Black wins");

            } else {
                // draw match
                alert.setContentText("Draw Match");

            }

            alert.show();
        }

        if (whiteScore == 0){

            //black wins

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Black wins");
            alert.show();

        }
        else if (blackScore == 0){

            //white wins

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("white wins");
            alert.show();

        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        // fill the arrs
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Button button = new Button("");
                button.setMaxWidth(Double.MAX_VALUE);
                button.setPrefHeight(Double.MAX_VALUE);

                buttonArr[i][j] = button;

                stringScreen[i][j] = "e";

                // if buuton clicked
                buttonClicked(button , i , j );



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



            stringScreen[3][3] = "b";
            stringScreen[4][4] = "b";
            stringScreen[3][4] = "w";
            stringScreen[4][3] = "w";



            syncArrs();
            setCanSelectedBtn();
            countScore();



    }
}
