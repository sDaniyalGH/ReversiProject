package controller;

import Enum.*;
import animatefx.animation.Jello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.CellNeighbor;
import model.Users;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AI implements Initializable {

    Color currentTurn = Color.black;
    final int size = 8;
    @FXML private GridPane gridPane;
    @FXML private Label blackScoreTV;
    @FXML private Label whiteScoreTV;
    @FXML private Label blackUserName;
    @FXML private Label whiteUserName;
    private String[][] stringScreen = new String[size][size];
    private Button[][] buttonArr = new Button[size][size];
    ArrayList<CellNeighbor> hamsayeHa;
    ArrayList<CellNeighbor> canSelected = new ArrayList<>();
    int whiteScore = 0;
    int blackScore = 0;
    private ArrayList<String[][]> saveList = new ArrayList<>();
    @FXML private Button undoBtn;
    public Users player = new Users("","",0);
    public Users computer = new Users("Computer","",0);

    void getUsers(Users player){

            this.player = player;

            if (player.getColor().equals(Color.black)) {
                computer.setColor(Color.white);
                blackUserName.setText(player.getUsername());
                whiteUserName.setText(computer.getUsername());
            }
            else {
                computer.setColor(Color.black);
                whiteUserName.setText(player.getUsername());
                blackUserName.setText(computer.getUsername());

            }
            currentTurn = player.getColor();
    }

    void toggleTurn(){

        if (currentTurn == Color.black)
            currentTurn = Color.white;
        else
            currentTurn = Color.black;
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
        if (currentTurn == Color.black) {

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

                if (currentTurn == Color.black)
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
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/white.png')");

                }
                else if (stringScreen[i][j].equals("c")) {
                    buttonArr[i][j].setStyle("-fx-background-image: url('/Images/canSelected.png')");

                }
            }
        }


//                    Image img = new Image("/Images/black.png");
//                    ImageView view = new ImageView(img);
//                    buttonArr[i][j].setGraphic(view);



    }


    // TODO: ۲۲/۰۶/۲۰۲۱ 2 ta bere aghab
    @FXML void undoClick(MouseEvent event) {

        Color thisTurn = currentTurn;
        if (saveList.size() > 0) {


            // copy previous screen to current screen
            for (int k = 0; k < size; k++) {
                for (int l = 0; l < size; l++) {

                    stringScreen[k][l] = saveList.get(saveList.size()-1)[k][l];
                }
            }


            saveList.remove(saveList.size() - 1);



            // canSelected -->empty
            for (int k = 0; k < size; k++) {
                for (int l = 0; l < size; l++) {

                    if (stringScreen[k][l].equals("c")){
                        stringScreen[k][l] = "e";
                    }

                }
            }

            toggleTurn();
            syncArrs();
            hamsayeHa.clear();
            canSelected.clear();
            countScore();
            setCanSelectedBtn();


            if (thisTurn == Color.black)
                currentTurn = Color.white;
            else
                currentTurn = Color.black;

        }

        if (saveList.size() == 0) {
            undoBtn.setDisable(true);
        }

    }

    void turnComputer (int i , int j){



        String current;
        if (currentTurn == Color.black) {

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
                    new Jello(buttonArr[reversedCells.getI()][reversedCells.getJ()]).play();

                }
                break;
            }
        }
        toggleTurn();


        // canSelected -->empty

        CStoEmpty();
        syncArrs();
        hamsayeHa.clear();
        canSelected.clear();
        countScore();

        setCanSelectedBtn();
        new Jello(buttonArr[i][j]).play();


    }

    void buttonClicked(Button button , int i , int j){


        button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (stringScreen[i][j].equals("c")){

                            // save screen
                            String[][] newStringScreen = new String[size][size];
                            for (int k = 0; k < size; k++) {
                                for (int l = 0; l < size; l++) {

                                    newStringScreen[k][l] = stringScreen[k][l];
                                }
                            }
                            saveList.add(newStringScreen);
                            undoBtn.setDisable(false);




                            String current;
                            if (currentTurn == Color.black) {

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
                                            new Jello(buttonArr[reversedCells.getI()][reversedCells.getJ()]).play();

                                        }
                                        break;
                                    }
                                }
                                toggleTurn();


                             // canSelected -->empty
                            CStoEmpty();
                            syncArrs();
                            hamsayeHa.clear();
                            canSelected.clear();
                            countScore();
                            setCanSelectedBtn();

                            Random random = new Random();

                            int randomIndex = random.nextInt(canSelected.size());

                            turnComputer(canSelected.get(randomIndex).getI() , canSelected.get(randomIndex).getJ());

                            new Jello(button).play();


//                            for (int k = 0; k < 1000000; k++) {
//
//                                System.out.println(k);
//                            }
//                            try {
//                                Thread.sleep(1500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }


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

    // can Selected to Empty
    void CStoEmpty (){

        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {

                if (stringScreen[k][l].equals("c")){
                    stringScreen[k][l] = "e";
                }

            }
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



            setCanSelectedBtn();
            countScore();




    }


//    public void loadInit(Scanner fileReader) {
//
//        // fill the arrs
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//
//                Button button = new Button("");
//                button.setMaxWidth(Double.MAX_VALUE);
//                button.setPrefHeight(Double.MAX_VALUE);
//                buttonArr[i][j] = button;
//
//                // if buuton clicked
//                buttonClicked(button , i , j );
//
//                String data = fileReader.next();
//                stringScreen[i][j] = data;
//
//            }
//        }
//        String usernameBlack = fileReader.next();
//        String usernameWhite = fileReader.next();
//
//        String turn = fileReader.next();
//        if (turn.equals("black"))
//            currentTurn = Color.black;
//        else
//            currentTurn = Color.white;
//
//        fileReader.close();
//        userBlack = new Users(usernameBlack , "Black" , 0);
//        userWhite = new Users(usernameWhite , "White" , 0);
//
//
//        getUsers(userBlack , userWhite);
//
//
//
//        //   init screen and add buttons to gridPane
//        gridPane.getChildren().clear();
//
//        gridPane.setAlignment(Pos.CENTER);
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//
//
//                gridPane.add( buttonArr[i][j], i , j , 1 , 1);
//            }
//        }
//
//
//
//
//
//
//        CStoEmpty();
//        setCanSelectedBtn();
//            countScore();
//
//
//
//
//    }

//    @FXML void clickSaveBtn(ActionEvent event) throws IOException {
//
//        // TODO: ۲۱/۰۶/۲۰۲۱ save names
//        // TODO: ۲۱/۰۶/۲۰۲۱ count scores after load game
//
//
//        // TODO: ۲۱/۰۶/۲۰۲۱ hash kardan file
//
//
//        // show window
//        JFrame parentFrame = new JFrame();
//
//        // file chooser
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Saving Game");
//
//
//        int userSelection = fileChooser.showSaveDialog(parentFrame);
//
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
//            File fileToSave = fileChooser.getSelectedFile();
//
//            String path = fileToSave.getAbsolutePath() + ".txt";
//            FileWriter fileWriter = new FileWriter(path);
//
//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    fileWriter.write(stringScreen[i][j] + " ");
//                }
//                //fileWriter.write("\n");
//            }
//
//            // black ... white
//            fileWriter.write(userBlack.getUsername() + " " + userWhite.getUsername() + " ");
//
//            String turn;
//
//            if (currentTurn.equals(Color.black))
//                turn = "black";
//            else
//                turn = "white";
//
//            fileWriter.write(turn);
//            fileWriter.close();
//
//        }
//    }
}