package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.net.DatagramSocket;

public class Test implements Initializable {

    @FXML
    Button btn;
    @FXML
    private VBox box;

    int tmp ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*
        box.getChildren().add(new Label("Connected Persons :"));

        Thread t = new Thread(){
            @Override
            public void run() {

                while(true){
                    try {
                        sleep(200);

                        tmp = 1;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(tmp == 1){
                                    box.getChildren().add(new Label("Add ..."));
                                }
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(Helper.sendFile) break;
                }
            }
        };

        t.start();*/
/*
    btn.setOnAction(e->{

        ChoiceDialog<String> choiceDialog = new ChoiceDialog("Upload Ans Sheet","MCQ Form");

        choiceDialog.setTitle("Options");
        choiceDialog.setHeaderText("Select a Option:");
        choiceDialog.setContentText("Option :");

        Optional<String> ow = choiceDialog.showAndWait();

        if(ow.isPresent()){
            String st = ow.get();
            if(st.equals("Upload Ans Sheet")){

            }else if(st.equals("MCQ Form")){
                Stage stg = new Stage();
                AnchorPane root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("fxml/mcqAns.fxml"));
                    stg.setTitle("Give MCQ Answers");
                    stg.setScene(new Scene(root, 600, 320));
                    stg.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    });*/

/*        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) continue;

                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);

                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }*/

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            System.out.println(ip);
        }catch (Exception e){
            System.out.println();
        }

    }

}
