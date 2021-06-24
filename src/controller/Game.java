package controller;
import Enum.*;
import animatefx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CellNeighbor;
import model.Users;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game  {

    @FXML private BorderPane container;
    Color currentTurn = Color.black;
    final int size = 8;
    @FXML private GridPane gridPane;
    @FXML private Label blackScoreTV;
    @FXML private Label whiteScoreTV;
    @FXML private Label blackUserName;
    @FXML private Label whiteUserName;
    private String[][] stringScreen = new String[size][size];
    private Button[][] buttonArr = new Button[size][size];
    ArrayList<CellNeighbor> hamsayeHa = new ArrayList<>();
    ArrayList<CellNeighbor> canSelected = new ArrayList<>();
    int whiteScore = 0;
    int blackScore = 0;
    private ArrayList<String[][]> saveList = new ArrayList<>();
    @FXML private Button undoBtn;
    public Users userBlack = new Users("","",0);
    public Users userWhite = new Users("","",0);
    @FXML private ListView msgListView;
    @FXML private TextField msgTextField;
    @FXML private VBox vBoxRight;
    @FXML private Button backBtn;
    @FXML private Button saveBTN;
    @FXML private Button aboutBTN;
    @FXML private Button exitBTN;
    @FXML private Button sendBTN;
    @FXML private StackPane stackPane;
    @FXML private MediaView mediaView;

    void getUsers(Users userBlack , Users userWhite){

        this.userBlack = userBlack;
        this.userWhite = userWhite;

        this.blackUserName.setText(userBlack.getUsername());
        this.whiteUserName.setText(userWhite.getUsername());

        this.blackScoreTV.setText(String.valueOf(userBlack.getScore()));
        this.whiteScoreTV.setText(String.valueOf(userWhite.getScore()));


    }

    void condition (int I , int J){

        if (I >= 0 && I < size && J >= 0 && J < size && stringScreen[I][J].equals("e")) {

            CellNeighbor position1 = new CellNeighbor(I, J );
            hamsayeHa.add(position1);

        }

    }
    void toggleTurn(){

        if (currentTurn == Color.black)
            currentTurn = Color.white;
        else
            currentTurn = Color.black;
    }
    // return hamsayeHaye node
    ArrayList<CellNeighbor> findHamsayeHa (int i , int j) {

        //hamsayeHa = new ArrayList<>();

        int I;
        int J;

        if (i != 0 && j != 0){
        I = i - 1;
        J = j - 1;
            condition(I,J);
        }

        if (i != 0 ) {

            I = i - 1;
            J = j;
            condition(I,J);
        }

        if (i != 0 && j != size-1){

        I = i-1;
        J = j+1;
            condition(I,J);
        }

        if (j!= 0) {
            I = i;
            J = j - 1;
            condition(I, J);
        }

        if (j!= size-1) {
            I = i;
            J = j + 1;
            condition(I, J);
        }

        if (i!=size-1 && j!=0) {
            I = i + 1;
            J = j - 1;
            condition(I, J);
        }

        if (i!=size-1 ) {
            I = i + 1;
            J = j;
            condition(I, J);
        }

        if (i!= size-1 && j!= size-1) {
            I = i + 1;
            J = j + 1;
            condition(I, J);
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

                    if (stringScreen[i][j].equals(zed)) {


                        hamsayeHa = findHamsayeHa(i, j);

                    }
                }
            }

                        // find empty hamsayeHa
                        for (CellNeighbor posHamsaye : hamsayeHa) {

                            if (stringScreen[posHamsaye.getI()][posHamsaye.getJ()].equals("e")){

                                // check : can it be selected ?
                                boolean status = false;
                                int I = posHamsaye.getI();
                                int J = posHamsaye.getJ();


                                        int currentI = I;
                                        int currentJ = J;
                                        ArrayList<CellNeighbor> baze = new ArrayList<>();

                                            while (true) {

                                                currentI++;
                                                currentJ++;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);

                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {

                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;


                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }

                                        currentI = I;
                                        currentJ = J;
                                        baze.clear();

                                            while (true){

                                                currentI++;
                                               // int currentJ = J;


                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }

                                        currentI = I;
                                        currentJ = J;
                                        baze.clear();


                                while (true){

                                                //int currentI = I+1;
                                                //int currentJ = J-1;

                                                currentJ--;
                                                currentI++;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }
                                        currentI = I;
                                        currentJ = J;
                                baze.clear();



                                while (true){

                                               //int currentI = I;
                                               // int currentJ = J+1;
                                                currentJ++;


                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }
                                        currentI = I;
                                        currentJ = J;
                                baze.clear();



                                while (true){

                                                //int currentI = I;
                                                //int currentJ = J-1;

                                                currentJ--;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }
                                        currentI = I;
                                        currentJ = J;
                                baze.clear();



                                while (true){

                                                //int currentI = I-1;
                                               // int currentJ = J+1;

                                                currentI--;
                                                currentJ++;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;


                                            }
                                        currentI = I;
                                        currentJ = J;
                                baze.clear();



                                while (true){

                                                //int currentI = I-1;
                                               // int currentJ = J;

                                                currentI--;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                }  else break;


                                            }
                                        currentI = I;
                                        currentJ = J;
                                baze.clear();

                                while (true){

                                                //int currentI = I-1;
                                               // int currentJ = J-1;


                                                currentI--;
                                                currentJ--;

                                                if (currentI >= 0 && currentI < size && currentJ >= 0 && currentJ < size){
                                                    if (stringScreen[currentI][currentJ].equals(zed)) {
                                                        CellNeighbor reversedPos = new CellNeighbor(currentI , currentJ);
                                                        baze.add(reversedPos);
                                                    } else if (stringScreen[currentI][currentJ].equals(current)) {
                                                        status = true;

                                                        for (CellNeighbor cellNeighbor : baze) {
                                                            posHamsaye.getReverseBaze().add(cellNeighbor);
                                                        }
                                                        break;
                                                    } else if (stringScreen[currentI][currentJ].equals("e") || stringScreen[currentI][currentJ].equals("c")) {
                                                        break;
                                                    }
                                                } else break;

                                            }


                                if (status && posHamsaye.getReverseBaze().size() > 0) {
                                    canSelected.add(posHamsaye);
                                    stringScreen[posHamsaye.getI()][posHamsaye.getJ()] = "c";
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





    }


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


                            new Jello(button).play();




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
    public void newGameInit() {

        backBtn.setStyle("-fx-background-image: url('/Images/back.png')");
        saveBTN.setStyle("-fx-background-image: url('/Images/save.png')");
        aboutBTN.setStyle("-fx-background-image: url('/Images/about.png')");
        exitBTN.setStyle("-fx-background-image: url('/Images/exit.png')");
        undoBtn.setStyle("-fx-background-image: url('/Images/undo.png')");
        sendBTN.setStyle("-fx-background-image: url('/Images/send.png')");


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



            stringScreen[3][3] = "w";
            stringScreen[4][4] = "w";
            stringScreen[3][4] = "b";
            stringScreen[4][3] = "w";
            stringScreen[4][2] = "w";
            stringScreen[5][2] = "b";
            stringScreen[5][3] = "w";



            setCanSelectedBtn();
            countScore();




    }
    public void loadInit(Scanner fileReader) {

        // fill the arrs
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Button button = new Button("");
                button.setMaxWidth(Double.MAX_VALUE);
                button.setPrefHeight(Double.MAX_VALUE);
                buttonArr[i][j] = button;

                // if buuton clicked
                buttonClicked(button , i , j );

                String data = fileReader.next();
                stringScreen[i][j] = data;

            }
        }
        String usernameBlack = fileReader.next();
        String usernameWhite = fileReader.next();

        String turn = fileReader.next();
        if (turn.equals("black"))
            currentTurn = Color.black;
        else
            currentTurn = Color.white;

        fileReader.close();
        userBlack = new Users(usernameBlack , "Black" , 0);
        userWhite = new Users(usernameWhite , "White" , 0);


        getUsers(userBlack , userWhite);



        //   init screen and add buttons to gridPane
        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {


                gridPane.add( buttonArr[i][j], i , j , 1 , 1);
            }
        }






        CStoEmpty();
        setCanSelectedBtn();
            countScore();




    }

    @FXML void backClicked () throws IOException {

        ((Stage)backBtn.getScene().getWindow()).close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Menu.fxml"));
        loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("Reversi");

        stage.show();




    }
    @FXML void clickSaveBtn(ActionEvent event) throws IOException {

        // TODO: ۲۱/۰۶/۲۰۲۱ save names
        // TODO: ۲۱/۰۶/۲۰۲۱ count scores after load game


        // TODO: ۲۱/۰۶/۲۰۲۱ hash kardan file


        // show window
        JFrame parentFrame = new JFrame();

        // file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Saving Game");


        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            String path = fileToSave.getAbsolutePath() + ".txt";
            FileWriter fileWriter = new FileWriter(path);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    fileWriter.write(stringScreen[i][j] + " ");
                }
                //fileWriter.write("\n");
            }

            // black ... white
            fileWriter.write(userBlack.getUsername() + " " + userWhite.getUsername() + " ");

            String turn;

            if (currentTurn.equals(Color.black))
                turn = "black";
            else
                turn = "white";

            fileWriter.write(turn);
            fileWriter.close();

        }
    }
    @FXML void exitBtn(){

        Platform.exit();

    }
    @FXML void aboutBtnClicked(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About Us");
        alert.setHeaderText("About Us");
        alert.setContentText("AmirHossein Gharaati \n" +
                "Seyed DaniyalGhoreyshi \n\n" +
                "sponsored By SCU\n\n" +
                "v1.0.0");

        alert.show();



    }

    @FXML void clickSendMsgBtn(ActionEvent event) {

        Users currentUser;

        if (userBlack.getColor().equals(currentTurn))
            currentUser = userBlack;
        else
            currentUser = userWhite;

        String msg = currentUser.getUsername() + " : " + msgTextField.getText();

        msgListView.getItems().add(msg);

        msgTextField.setText("");

    }

}
