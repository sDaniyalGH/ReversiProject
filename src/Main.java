import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO: ۱۸/۰۶/۲۰۲۱ change it --> go to Menu.fxml
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/Game.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {



        });
        primaryStage.show();
    }
}
