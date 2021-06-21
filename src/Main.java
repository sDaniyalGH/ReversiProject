import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // todo


    // TODO: ۲۱/۰۶/۲۰۲۱ winnig bashokooh
    // TODO: ۲۱/۰۶/۲۰۲۱ animation baraye chrkhidan mohre az siah b sefid
    // TODO: ۲۱/۰۶/۲۰۲۱ if cant move --> turn passed
    // TODO: ۲۱/۰۶/۲۰۲۱ if all white --> win white
    // TODO: ۲۱/۰۶/۲۰۲۱ png : set size to btn.size
    // TODO: ۲۰/۰۶/۲۰۲۱  testing with the real game . the same moves !!!
    // TODO: ۲۱/۰۶/۲۰۲۱ play gif for choose random between black and white

    // TODO: ۲۱/۰۶/۲۰۲۱ big text that show turn is black or white

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/Menu.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {



        });
        primaryStage.setTitle("Login menu");
        primaryStage.show();
    }
}
