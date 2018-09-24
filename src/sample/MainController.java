package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController implements Initializable {

    public static int wait_s,run_s;

    String user;

    @FXML
    private JFXButton printResult;
    @FXML
    public VBox connectedSt;
    @FXML
    private Label waitClock,runClock,endClock;
    @FXML
    Pane waitPanetime,waitPanetxt,runPanetime,runPanetxt,endPaneTime,endPanetxt;

    static int H=0,M=0,S=0,Ms=0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        acceptClient();

       // remainingTime();

        Thread t=new Thread(){

            @Override
            public void run() {


                int shour = Integer.parseInt(Helper.startTime.substring(0,2));
                int smin = Integer.parseInt(Helper.startTime.substring(3,5));
                int ehour = Integer.parseInt(Helper.endTime.substring(0,2));
                int emin = Integer.parseInt(Helper.endTime.substring(3,5));

                while (Helper.wait==true || Helper.run==true)
                {
                    try {
                       // System.out.println(Ms);
                        sleep(800);
                        Date date=new Date();
                        H=date.getHours();
                        M=date.getMinutes();
                        S=date.getSeconds();

                        if(H>=12)
                            H=H-12;

                        if(Helper.wait &&(H==shour && smin==M))
                        {
                            Helper.wait=false;
                            Helper.run=true;
                            Helper.sendFile = true;
                            String style = "-fx-background-color: rgba(154, 76, 178, 0.4);";
                            waitPanetime.setStyle(style);
                            runPanetime.setStyle(style);
                            S=run_s;
                            Ms=0;

                        }
                        if(Helper.run &&(H==ehour && emin==M))
                        {
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
                          if(Helper.wait && !Helper.run)
                              waitClock.setText(time);
                          else if(!Helper.wait && Helper.run)
                              runClock.setText(time);
                          else
                              endClock.setText(time);

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();

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
                pdfgenerator.pdf();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }

    //// ------- Methods ------- /////

    private void acceptClient(){
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    private void stopClient() throws IOException {
        System.out.println("bondho kori ");
        Helper.acceptClient = false;
        Socket sock = new Socket("127.0.0.1",226);
    }

    private void remainingTime(){

        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        int h = Integer.parseInt(timeStamp.substring(0,2));
        int m = Integer.parseInt(timeStamp.substring(2,4));
        int s = Integer.parseInt(timeStamp.substring(4,6));
        int shour = Integer.parseInt(Helper.startTime.substring(0,2));
         int smin = Integer.parseInt(Helper.startTime.substring(3,5));
        int ehour = Integer.parseInt(Helper.endTime.substring(0,2));
        int emin = Integer.parseInt(Helper.endTime.substring(3,5));


        if(h>=12)
            h=h-12;
        if(shour>=12)
            shour=shour-12;
        if(ehour>=12)
            ehour=ehour-12;
        System.out.println(h);

        wait_s=((shour*3600+smin*60)-(h*3600+m*60+s));
        if(wait_s<0) {
            wait_s = 0;
        }
        S=wait_s;

        run_s=((ehour*3600+emin*60)-(shour*3600+smin*60));

    }

}
