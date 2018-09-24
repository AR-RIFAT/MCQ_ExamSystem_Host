package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ListUserThread extends Thread {


    public ListUserThread() {
    }

    @Override
    public void run() {
        System.out.println("Rifat");
        for(int i=0;i<20;i++){

            try {
                Thread.sleep(102);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
