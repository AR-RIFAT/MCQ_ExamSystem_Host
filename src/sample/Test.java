package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.DatagramSocket;

public class Test implements Initializable {

    @FXML
    VBox bak;
    @FXML
    Button btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

/*
*//*        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
            if(Main.mStage.getWidth() > 1100){
                HBox.setMargin(bak,new Insets(0,226,0,0));
            }
            if(Main.mStage.getWidth() < 1100){
                HBox.setMargin(bak,new Insets(0,70,0,0));
            }
        };*//*

        Main.mStage.widthProperty().addListener(stageSizeListener);
        Main.mStage.heightProperty().addListener(stageSizeListener);*/

/*        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            System.out.println(ip);
        }catch (Exception e){
            System.out.println();
        }*/

        btn.setOnAction(e->{
            System.out.println("Tipo");
        });

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm");

        Thread ansRecThread = new Thread(){

            @Override
            public void run() {
                int c = 0;
                while(true){
                    String time = dateFormatGmt.format(Calendar.getInstance().getTime());

                    int min = Integer.parseInt(time.substring(3,5));

                    if(min==18){
                        System.out.println("asdasd");
                        break;
                    }
                }
            }
        };

        ansRecThread.start();

    }

}
