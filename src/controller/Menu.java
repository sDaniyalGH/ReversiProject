package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    @FXML private MenuButton playerColor;
    @FXML private MenuItem playerBlack;
    @FXML private MenuItem playerWhite;
    @FXML private Label computerLabel;
    @FXML private Button startGameBTN2;
    private ArrayList<Users> users;   // for 2v2 mode and computer mode

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startGameBTN1.setOnAction(event -> selectDualPersonMode());

        blackColor1.setOnAction(event -> player1Color.setText(blackColor1.getText()));
        blackColor2.setOnAction(event -> player2Color.setText(blackColor2.getText()));

        whiteColor1.setOnAction(event -> player1Color.setText(whiteColor1.getText()));
        whiteColor2.setOnAction(event -> player2Color.setText(whiteColor2.getText()));

        startGameBTN2.setOnAction(event -> selectComputerMode());

        playerBlack.setOnAction(event -> playerColor.setText(playerBlack.getText()));
        playerWhite.setOnAction(event -> playerColor.setText(playerWhite.getText()));

    }

    public void selectDualPersonMode(){

        if (dualPerson.isSelected()){
            if (checkIsUserEmpty() && checkEqualsUsers() && checkIsSelectedColor() && checkUsersWithColor() ){
                users = new ArrayList<>();

                Users user1 = new Users(player1Username.getText(), player1Color.getText(), 0);
                users.add(user1);
                Users user2 = new Users(player2Username.getText(), player2Color.getText(), 0);
                users.add(user2);

                dualPersonLabel.setText("");

                ((Stage)startGameBTN2.getScene().getWindow()).close();

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Game.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Game controller = loader.getController();
                controller.getUsers(users);
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Reversi");
                stage.show();

            }
        }

    }

    public void selectComputerMode(){

        if (withComputer.isSelected()){
            if (checkIsUserEmptyComputerMode() && checkIsSelectedColorComputerMode()){
                users = new ArrayList<>();
                Users user = new Users(playerUsername.getText(), playerColor.getText(), 0);
                users.add(user);
                Users computer;
                if (playerColor.getText().equals("Black")) {
                    computer = new Users("Computer", "Black", 0);
                }
                else computer = new Users("Computer", "White", 0);
                users.add(computer);

                computerLabel.setText("");

                ((Stage)startGameBTN2.getScene().getWindow()).close();

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Game.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Game controller = loader.getController();
                controller.getUsers(users);
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Reversi");
                stage.show();

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

    public boolean checkIsSelectedColorComputerMode(){

        if (!(playerColor.getText().equals("Select Color"))){
            return true;
        }
        else {
            computerLabel.setText("Pick an color!");
            return false;
        }

    }

}
