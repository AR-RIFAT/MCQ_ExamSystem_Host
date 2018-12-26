package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Thread.currentThread().setName("Main");

     Runtime.getRuntime().exec("C:\\xampp\\xampp-control.exe");
     DatabaseHelper.CheckDatabase();
        mStage = primaryStage;
//        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        primaryStage.setTitle("Exam");
        primaryStage.setScene(new Scene(root, 962, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
