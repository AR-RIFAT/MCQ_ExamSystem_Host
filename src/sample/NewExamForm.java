package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;


public class NewExamForm implements Initializable {

    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
    @FXML
    JFXButton examStart,chooseQues,uploadAns;
    @FXML
    JFXTextField startTime,endTime,courseCode,subjectName,session,totalQues;
    @FXML
    Label quesPath;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Thread.currentThread().setName("NewExamForm");

        examStart.setOnAction(e->{
            Helper.courseCode = courseCode.getText();
            Helper.subjectName = subjectName.getText();
            Helper.startTime = new AtomicReference<>(startTime.getText());
            Helper.endTime = new AtomicReference<>(endTime.getText());
            Helper.ExamType="TT1";//Exam Type
            Helper.subjectName =Helper.subjectName+"_"+Helper.ExamType;


            try {
                String table=Helper.courseCode+"_"+Helper.subjectName;
                if(!DatabaseHelper.CheckTableName(table)) {
                    DatabaseHelper.CreateTable(Helper.courseCode, Helper.subjectName);
                    try {

                        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/examDashboard.fxml"));
                        Main.mStage.setScene(new Scene(root,962,600));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else
                {

                    System.out.println("Already ache");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        chooseQues.setOnAction(e->{
            File file = fileChooser.showOpenDialog(Main.mStage);
            if (file != null) {
                Helper.quesPath = file.toString();
                quesPath.setText(Helper.quesPath);
            }
        });

        uploadAns.setOnAction(e->{

            try{
                Helper.totalQues = Integer.parseInt(totalQues.getText());
            }catch(Exception b){
                System.out.println();
            }

            ChoiceDialog<String> choiceDialog = new ChoiceDialog("Upload Ans Sheet","MCQ Form");

            choiceDialog.setTitle("Options");
            choiceDialog.setHeaderText("Select a Option:");
            choiceDialog.setContentText("Option :");

            Optional<String> ow = choiceDialog.showAndWait();

            if(ow.isPresent()){
                String st = ow.get();
                if(st.equals("Upload Ans Sheet")){
                    FileChooser fc=new FileChooser();

                    File file=fc.showOpenDialog(Main.mStage);
                    try {
                        Scanner scanner=new Scanner(file);
                        StringBuilder sb=new StringBuilder("");

                        while(scanner.hasNextLine())
                        {
                            String ans=scanner.nextLine();
                            System.out.println(ans.length());
                            if(ans.length()>0)
                                sb.append(ans.substring(2));
                        }
                        System.out.println(sb);
                        Helper.resultString=sb.toString();
                    } catch (java.io.IOException e1) {
                        e1.printStackTrace();
                    }

                }else if(st.equals("MCQ Form")){
                    Stage stg = new Stage();
                    AnchorPane root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("fxml/mcqAns.fxml"));
                        stg.setTitle("Give MCQ Answers");
                        stg.setScene(new Scene(root));
                        stg.show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    public void backtoHome() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        Main.mStage.setScene(new Scene(root,962,600));
    }
}
