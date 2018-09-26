package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    JFXButton newExam,archive;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newExam.setOnAction(e->{
            try {
                AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/newExamForm.fxml"));
                Main.mStage.setScene(new Scene(root,962,600));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        archive.setOnAction(e->{
            try {
                AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/examArchive.fxml"));
                Main.mStage.setScene(new Scene(root,962,600));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}
