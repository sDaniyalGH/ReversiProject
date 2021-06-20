import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // todo


    // TODO: ۲۱/۰۶/۲۰۲۱ png : set size to btn.size
    // TODO: ۲۰/۰۶/۲۰۲۱  testing with the real game . the same moves !!!

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
