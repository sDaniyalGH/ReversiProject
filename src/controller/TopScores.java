package controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Users;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TopScores {

    @FXML
    private TableView<Users> tableView;




    public void initializee(ArrayList<Users> listOfUsers) {



        TableColumn<Users, String> username = new TableColumn<>("Username");
        TableColumn<Users, Integer> score = new TableColumn<>("High Score");

        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        score.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        username.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        score.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));


        // TODO: ۲۵/۰۶/۲۰۲۱ warning ????
            tableView.prefWidthProperty().bind(Bindings.selectDouble(tableView.sceneProperty(), "width"));
            tableView.prefHeightProperty().bind(Bindings.selectDouble(tableView.sceneProperty(), "height"));

        tableView.getColumns().add(username);
        tableView.getColumns().add(score);

        for (Users eachUser : listOfUsers) {
            tableView.getItems().add(eachUser);
        }

    }
}
