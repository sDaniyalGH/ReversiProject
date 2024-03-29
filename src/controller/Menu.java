package controller;

import Enum.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Users;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Menu implements Initializable {

    @FXML private Tab dualPerson;
    @FXML private TextField player1Username;
    @FXML private MenuButton player1Color;
    @FXML private MenuItem blackColor1;
    @FXML private MenuItem whiteColor1;
    @FXML private TextField player2Username;
    @FXML private MenuButton player2Color;
    @FXML private MenuItem blackColor2;
    @FXML private MenuItem whiteColor2;
    @FXML private Label dualPersonLabel;
    @FXML private Button startGameBTN1;
    @FXML private Tab withComputer;
    @FXML private TextField playerUsername;
    @FXML private Label computerLabel;
    @FXML private Button startGameBTN2;
    @FXML private AnchorPane anchorPane;
    @FXML private TableView<Users> tableView;
    @FXML private Tab leaderBoard;
    private ArrayList<Users> allUsers2v2;
    private ArrayList<Users> allUsersComputer ;
    public static ArrayList<Users> listOfSortedUsers ;


    // private ArrayList<Users> users;   // for 2v2 mode and computer mode

    public void getUsersList (ArrayList<Users> allUsers2v2){
        this.allUsers2v2 = allUsers2v2;

    }

    @FXML
    void loadBtnClicked(ActionEvent event) throws IOException {


        String[][] loadString = new String[8][8];

        // TODO: ۲۵/۰۶/۲۰۲۱ if file namarboot ---> error
        FileChooser fileChooser = new FileChooser();

        // must choose txt file
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt file","*.txt" ));

        File file = fileChooser.showOpenDialog(null);

        Scanner fileReader = null;
        try {
             fileReader = new Scanner(file);
        } catch (Exception e){
            System.out.println("File chooser opened but not choose file");
        }

        if (fileReader!= null) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Game.fxml"));
            loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.setTitle("Reversi");
            ((Stage) startGameBTN2.getScene().getWindow()).close();

            stage.show();
            Game controller = loader.getController();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    loadString[i][j] = fileReader.next();


                }
            }
            String usernameBlack = fileReader.next();
            String usernameWhite = fileReader.next();
            String turn = fileReader.next();

            fileReader.close();

            addUsersToList (controller , usernameBlack , usernameWhite );

            controller.loadInit(fileReader , loadString , usernameBlack , usernameWhite , turn);
            controller.getListOfUsers(allUsers2v2 );


        }
    }

    // leaderboard setting ***************************************************

    public void initializee(ArrayList<Users> listOfUsers) {

        if (tableView.getColumns().isEmpty()) {
            TableColumn<Users, String> username = new TableColumn<>("Username");
            TableColumn<Users, Integer> score = new TableColumn<>("High Score");

            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            score.setCellValueFactory(new PropertyValueFactory<>("highScore"));

            tableView.getColumns().add(username);
            tableView.getColumns().add(score);
        }
        // to prevent duplicate users
        tableView.getItems().clear();
        for (Users eachUser : listOfUsers) {
            tableView.getItems().add(eachUser);
        }

    }

    private void sortUsers2v2 (){
        listOfSortedUsers = new ArrayList<>();

        while (allUsers2v2.size() > 0) {
            Users maxUser = null;
            int maxScore = -1;

            for (Users eachUser : allUsers2v2) {

                if (eachUser.getHighScore() > maxScore) {
                    maxScore = eachUser.getHighScore();
                    maxUser = eachUser;
                }
            }

            listOfSortedUsers.add(maxUser);
            allUsers2v2.remove(maxUser);

        }

        allUsers2v2 = listOfSortedUsers;

    }

   // ************************************************************************
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       leaderBoard.setOnSelectionChanged(event -> {
           if (!(allUsers2v2.isEmpty())){
               if (!(allUsers2v2.get(0).getHighScore() == 0 && allUsers2v2.get(1).getHighScore() == 0)) {
                   sortUsers2v2();
                   initializee(listOfSortedUsers);
               }
           }
       });

        anchorPane.getStylesheets().add(String.valueOf(this.getClass().getResource("../Graphic/MenuGraphic.css")));

        startGameBTN1.setOnAction(event -> {
            try {
                selectDualPersonMode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        blackColor1.setOnAction(event -> player1Color.setText(blackColor1.getText()));
        blackColor2.setOnAction(event -> player2Color.setText(blackColor2.getText()));

        whiteColor1.setOnAction(event -> player1Color.setText(whiteColor1.getText()));
        whiteColor2.setOnAction(event -> player2Color.setText(whiteColor2.getText()));

        startGameBTN2.setOnAction(event -> selectComputerMode());

        //playerBlack.setOnAction(event -> playerColor.setText(playerBlack.getText()));
        //playerWhite.setOnAction(event -> playerColor.setText(playerWhite.getText()));

    }

    public void selectDualPersonMode() throws IOException {

        if (dualPerson.isSelected()){
            if (checkIsUserEmpty() && checkEqualsUsers() && checkIsSelectedColor() && checkUsersWithColor() ){

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Game.fxml"));
                loader.load();

                Game controller = loader.getController();

                //Users user1 = new Users(player1Username.getText(), player1Color.getText());
                //Users user2 = new Users(player2Username.getText(), player2Color.getText());

                addUsersToList (controller , player1Username.getText() , player2Username.getText());
                dualPersonLabel.setText("");

                ((Stage)startGameBTN2.getScene().getWindow()).close();

                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Reversi");

                stage.show();
                controller.newGameInit();
                controller.getListOfUsers(allUsers2v2 );


            }
        }

    }

    void addUsersToList (Game controller , String username1 , String username2){
        Users user1 = null;
        Users user2 = null;

        boolean status1 = false;
        for (Users each : allUsers2v2) {

            if (each.getUsername().equals(username1)){
                status1 = true;
                user1 = each;
                break;
            }
        }

        boolean status2 = false;
        for (Users each : allUsers2v2) {

            if (each.getUsername().equals(username2)){
                status2 = true;
                user2 = each;
                break;
            }
        }

        if (!status1) {
            user1 = new Users(username1, player1Color.getText());
            allUsers2v2.add(user1);
        }
        if (!status2) {
            user2 = new Users(username2, player2Color.getText());
            allUsers2v2.add(user2);
        }


        if (user1.getColor().equals(Color.black))
            controller.getUsers(user1 , user2);

        else
            controller.getUsers(user2 , user1);


    }
    public void selectComputerMode(){

        if (withComputer.isSelected()){
            if (checkIsUserEmptyComputerMode() ){
                //users = new ArrayList<>();
                Users user = new Users(playerUsername.getText(), "White");

                // TODO: ۲۱/۰۶/۲۰۲۱ continue

                computerLabel.setText("");

                ((Stage)startGameBTN2.getScene().getWindow()).close();

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AI.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AI controller = loader.getController();
                controller.getUsers(user);
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Reversi");
                stage.show();
                controller.getListOfUsers(allUsers2v2 );


            }
        }

    }

    public boolean checkIsUserEmpty(){

        if (!(player1Username.getText().isEmpty() || player2Username.getText().isEmpty())){
            return true;
        }
        else {
            dualPersonLabel.setText("Some fields are empty");
            return false;
        }
    }

    public boolean checkUsersWithColor(){

        if (!(player1Color.getText().equals(player2Color.getText()))){
            return true;
        }
        else {
            dualPersonLabel.setText("Colors are the same");
            return false;
        }

    }

    public boolean checkEqualsUsers(){

        if (!(player1Username.getText().equals(player2Username.getText()))){
            return true;
        }
        else {
            dualPersonLabel.setText("Usernames are the same");
            return false;
        }

    }

    public boolean checkIsSelectedColor(){

        if (!(player1Color.getText().equals("Select Color") || player2Color.getText().equals("Select Color"))){
            return true;
        }
        else {
            dualPersonLabel.setText("Pick an color!");
            return false;
        }

    }

    public boolean checkIsUserEmptyComputerMode(){

        if (!(playerUsername.getText().isEmpty())){
            return true;
        }
        else {
            computerLabel.setText("Username field is empty");
            return false;
        }
    }

//    public boolean checkIsSelectedColorComputerMode(){
//
//        if (!(playerColor.getText().equals("Select Color"))){
//            return true;
//        }
//        else {
//            computerLabel.setText("Pick an color!");
//            return false;
//        }
//
//    }

}
