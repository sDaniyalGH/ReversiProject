import controller.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Users;

import java.util.ArrayList;

public class Main extends Application {

    // todo

    public static ArrayList<Users> allUsers2v2;
    public static ArrayList<Users> allUsersComputer ;


    // TODO: ۲۱/۰۶/۲۰۲۱ winnig bashokooh
    // TODO: ۲۱/۰۶/۲۰۲۱ animation baraye chrkhidan mohre az siah b sefid
    // TODO: ۲۱/۰۶/۲۰۲۱ if cant move --> turn passed
    // TODO: ۲۱/۰۶/۲۰۲۱ if all white --> win white
    // TODO: ۲۱/۰۶/۲۰۲۱ png : set size to btn.size
    // TODO: ۲۰/۰۶/۲۰۲۱  testing with the real game . the same moves !!!
    // TODO: ۲۱/۰۶/۲۰۲۱ play gif for choose random between black and white

    // TODO: ۲۱/۰۶/۲۰۲۱ big text that show turn is black or white

    public static void main(String[] args) {
        allUsers2v2 = new ArrayList<>();
        allUsersComputer = new ArrayList<>();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Menu.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
//        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
//
//
//
//        });
        primaryStage.setTitle("Login menu");
        primaryStage.show();
        Menu controller = loader.getController();
        controller.getUsersList(allUsers2v2 );
    }
}
