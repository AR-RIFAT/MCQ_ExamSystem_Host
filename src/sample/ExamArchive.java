package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExamArchive implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backtoHome() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        Main.mStage.setScene(new Scene(root,962,600));
    }

}
