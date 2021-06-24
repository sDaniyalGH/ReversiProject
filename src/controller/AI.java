package controller;

import Enum.*;
import animatefx.animation.Jello;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CellNeighbor;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

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
    @FXML private Button  backBtnai;
    @FXML private Button  aboutBTN;
    @FXML private Button exitBTN;


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


            CStoEmpty();
            currentTurn = player.getColor();

            syncArrs();
            hamsayeHa.clear();
            canSelected.clear();
            countScore();
            setCanSelectedBtn();





        }

//        if (saveList.size() == 0) {
//            undoBtn.setDisable(true);
//        }

    }

    void turnComputer (int i , int j){



        deactiveUndo();

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

        activeUndo();

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
                           // undoBtn.setDisable(false);




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


                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {



                                @Override
                                public void handle(ActionEvent event) {

                                    turnComputer(canSelected.get(randomIndex).getI() , canSelected.get(randomIndex).getJ());

                                }


                            }));
                            timeline.play();
                            deactiveUndo();
                            deactiveButtons();
                            timeline.setOnFinished(event1 -> {

                                activeUndo();
                                activeButtons();
                            });



                            new Jello(button).play();


                        }
                    }
                });

    }

    void deactiveButtons () {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttonArr[i][j].setDisable(true);
            }
        }
    }
    void activeButtons () {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttonArr[i][j].setDisable(false);
            }
        }
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

    void deactiveUndo () {

        undoBtn.setDisable(true);
    }
    void activeUndo () {

        undoBtn.setDisable(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backBtnai.setStyle("-fx-background-image: url('/Images/back.png')");
       // saveBTN.setStyle("-fx-background-image: url('/Images/save.png')");
        aboutBTN.setStyle("-fx-background-image: url('/Images/about.png')");
        exitBTN.setStyle("-fx-background-image: url('/Images/exit.png')");
        undoBtn.setStyle("-fx-background-image: url('/Images/undo.png')");


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
        gridPane.setMaxWidth(560);
        gridPane.setMaxHeight(560);
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


    @FXML void backClickedai () throws IOException {

        ((Stage)backBtnai.getScene().getWindow()).close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Menu.fxml"));
        loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("Reversi");

        stage.show();




    }
    @FXML void exitBtnai(){

        Platform.exit();

    }
    @FXML void aboutBtnClickedai(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About Us");
        alert.setHeaderText("About Us");
        alert.setContentText("AmirHossein Gharaati \n" +
                "Seyed DaniyalGhoreyshi \n\n" +
                "sponsored By SCU\n\n" +
                "v1.0.0");

        alert.show();


    }



}
