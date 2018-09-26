package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MainController implements Initializable {

    ServerThread serverThread;

    public static int wait_s,run_s;

    String user;
    Calendar calendar;
    static Boolean Flag=true;

    @FXML
    private JFXButton printResult,changeTime;
    @FXML
    public VBox connectedSt,bak;
    @FXML
    private Label courseCode,courseName,totalQues,Date,startTime,endTime,clock,examStatus;

    static int H=0,M=0,S=0,Ms=0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        acceptClient();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
            if(Main.mStage.getWidth() > 1100){
                HBox.setMargin(bak,new Insets(0,226,0,0));
            }
            if(Main.mStage.getWidth() < 1100){
                HBox.setMargin(bak,new Insets(0,70,0,0));
            }
        };
        Main.mStage.widthProperty().addListener(stageSizeListener);
        Main.mStage.heightProperty().addListener(stageSizeListener);


        courseCode.setText(": "+Helper.courseCode);
        courseName.setText(": "+Helper.subjectName);
        totalQues.setText(": "+Integer.toString(Helper.totalQues));
        calendar=Calendar.getInstance();
        String ampm;
        if(calendar.get(Calendar.HOUR) >12){
            ampm = "pm";
        }else{
            ampm = "am";
        }
        Date.setText(": "+new Date().toString());

        startTime.setText(Helper.startTime+" "+ampm);
        endTime.setText(Helper.endTime+" "+ampm);

        Clock();


        Label l = new Label();
        Font f = new Font("Candara",15);
        l.setFont(f);
        l.setText("Connected Students : ");
        connectedSt.getChildren().add(l);

        connectedSt.getChildren().add(new Label(""));

        Thread connectedStudents = new Thread(){

            @Override
            public void run() {
                int c = 0;
                while(true){
                    try {
                        sleep(200);
                        if(Helper.students.size() > c){
                            StdInstance st= Helper.students.get(c);
                            c++;
                            user = c + ". " + st.getName() + " - " + st.getRegId();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    connectedSt.getChildren().add(new Label(user));
                                    connectedSt.getChildren().add(new Label(""));
                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        connectedStudents.start();

        printResult.setOnAction(e->{
            try {
                pdfgenerator.pdf(Helper.courseCode,Helper.subjectName);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        changeTime.setOnAction(e->{
            // Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Change Time");
            dialog.setHeaderText("Enter New Timeline");

// Set the icon (must be included in the project).
            dialog.setGraphic(new ImageView(this.getClass().getResource("clock.jpg").toString()));

// Set the button types.
            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField start = new TextField();
            start.setPromptText("HH : MM");
            TextField duration = new TextField();
            duration.setPromptText("Time");

            grid.add(new Label("Start Time : "), 0, 0);
            grid.add(start, 1, 0);
            grid.add(new Label("Exam Duration : "), 0, 1);
            grid.add(duration, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
            Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
            okButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
            start.textProperty().addListener((observable, oldValue, newValue) -> {
                okButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
            //Platform.runLater(() -> start.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    return new Pair<>(start.getText(), duration.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(timeline -> {
              //  Flag=false;
                System.out.println("Start Time : " + timeline.getKey() + ", Duration : " + timeline.getValue());
                Helper.startTime=new AtomicReference<>(timeline.getKey());
                Helper.endTime=new AtomicReference<>(timeline.getValue());
            //    Flag=true;
             //   Clock();

            });
        });

        Main.mStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stopClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Close Clicked");
                Platform.exit();
            }
        });

    }

    //// ------- Methods ------- /////

    private void acceptClient(){
        serverThread = new ServerThread();
        serverThread.start();
    }

    private void stopClient() throws IOException {
        System.out.println("bondho kori ");
        serverThread.setBlinker();
        Socket sock = new Socket("127.0.0.1",226);

        for(SenderThread st : Helper.senderThreadList){
            st.setBlinker();
        }
    }

    private void Clock(){



        Thread t=new Thread(){

            @Override
            public void run() {


                while (Helper.wait==true || Helper.run==true )
                {

                    int shour = Integer.parseInt(Helper.startTime.toString().substring(0,2));
                    int smin = Integer.parseInt(Helper.startTime.toString().substring(3,5));
                    int ehour = Integer.parseInt(Helper.endTime.toString().substring(0,2));
                    int emin = Integer.parseInt(Helper.endTime.toString().substring(3,5));

                    if(!Flag){
                        System.out.println(Flag);
                        break;
                    }


                    try {
                        // System.out.println(Ms);
                        Date d=new Date();
                        sleep(1);
                        Calendar cal=Calendar.getInstance();
                        H=cal.get(Calendar.HOUR);
                        M=cal.get(Calendar.MINUTE);
                        S=cal.get(Calendar.SECOND);

                        /*if(H>=12)
                            H=H-12;*/

                        if(Helper.wait &&(H==shour && smin==M)) {
                            Helper.wait=false;
                            Helper.run=true;
                            Helper.sendFile = true;
/*                            String style = "-fx-background-color: rgba(154, 76, 178, 0.4);";
                            waitPanetime.setStyle(style);
                            runPanetime.setStyle(style);*/
                            S=run_s;
                            Ms=0;

                        }
                        if(Helper.run &&(H==ehour && emin==M)) {
                            Helper.run=false;
                            //set ended exam;
                        }

                       /* if(Ms==660)
                        {
                            Ms=0;
                            S--;
                        }

                        Ms++;*/

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {


                                String time=Integer.toString(H)+" : "+Integer.toString(M)+" : "+Integer.toString(S);
                                if(Helper.run){
                                    //waitClock.setText(time);
                                    examStatus.setText("Running");
                                }
                                else if(!Helper.run &&!Helper.wait){
                                    //runClock.setText(time);
                                    examStatus.setText("Ended");
                                }
                                else{
                                    //endClock.setText(time);
                                    examStatus.setText("Waiting");
                                }
                                clock.setText(time);

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();


    }

}
