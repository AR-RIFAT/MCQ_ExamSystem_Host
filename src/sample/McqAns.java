package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class McqAns implements Initializable {


    @FXML
    VBox ansMcq;

    @FXML
    private JFXButton checkMcq,submitAns;

    @FXML
    private AnchorPane mcqAnchor;

    @FXML
    private HBox hb;

    @FXML
    private ScrollPane sc;

    @FXML
    private Label checked,notChecked;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<ToggleGroup> tg = new ArrayList<>();

        for(int i=1;i<=Helper.totalQues;i++){
            RadioButton a = new RadioButton("A");
            RadioButton b = new RadioButton("B");
            RadioButton c = new RadioButton("C");
            RadioButton d = new RadioButton("D");

            ToggleGroup ansGroup = new ToggleGroup();

            a.setToggleGroup(ansGroup);
            b.setToggleGroup(ansGroup);
            c.setToggleGroup(ansGroup);
            d.setToggleGroup(ansGroup);

            Insets insets = new Insets(0,60,0,20);
            a.setPadding(insets);
            b.setPadding(insets);
            c.setPadding(insets);
            d.setPadding(insets);

            HBox hb = new HBox(new Label("Ques No. " + Integer.toString(i)+" :  "),a,b,c,d);

            Insets insp = new Insets(12,12,12,26);

            hb.setPadding(insp);

            String style1 = "-fx-background-color: rgba(174, 214, 241);";

            String style2 = "-fx-background-color: rgba(253, 254, 254);";

            if(i%2 == 0){
                hb.setStyle(style1);
            }else{
                hb.setStyle(style2);
            }

            ansMcq.getChildren().add(hb);

            tg.add(ansGroup);

        }

        checkMcq.setOnAction(e->{
            String ck="";
            int countck = 0;
            String notck="";
            int countNotck = 0;
            for(int i=1;i<=Helper.totalQues;i++){
                ToggleGroup tgp = tg.get(i-1);
                try{
                    RadioButton rb = (RadioButton) tgp.getSelectedToggle();
                    rb.getText();
                    ck+=Integer.toString(i)+", ";
                    countck++;
                }catch(NullPointerException en){
                    notck+=Integer.toString(i)+", ";
                    countNotck++;
                }
            }

            checked.setText("Checked ("+Integer.toString(countck)+") -> "+ck);
            notChecked.setText("Remaining ("+Integer.toString(countNotck)+") -> "+notck);

        });

        submitAns.setOnAction(e->{

            for(int i=0;i<Helper.totalQues;i++){
                ToggleGroup tgp = tg.get(i);
                try{
                    RadioButton rb = (RadioButton) tgp.getSelectedToggle();
                    Helper.resultString += rb.getText();
                    //System.out.println(i+1 + " :  "+ rb.getText());
                }catch(NullPointerException en){
                    Helper.resultString += "X";
                    //System.out.println(i+1 + " :  X" );
                }
            }
            System.out.println(Helper.resultString);

            mcqAnchor.getChildren().removeAll(hb,sc);
            Label label = new Label("Done");

            Insets insm = new Insets(26,0,0,262);
            label.setPadding(insm);

            Font font = new Font(26);
            label.setFont(font);

            mcqAnchor.getChildren().add(label);
        });

    }
}
