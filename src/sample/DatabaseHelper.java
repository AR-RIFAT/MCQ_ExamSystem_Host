package sample;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static void CreateTable(String CourseCode,String SubjectName) throws Exception {
        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement insert=con.prepareStatement("CREATE TABLE "+Table+"(RegNo varchar (500),StdName varchar " +
                "(250),AnsString varchar (1000),correct int,incorrect int,untouched int,totalscore int)");

        insert.executeUpdate();


    }
    public static void InsertData(StdInstance instance,String CourseCode,String SubjectName) throws Exception {
        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement insert=con.prepareStatement("INSERT INTO "+Table+"(RegNo,StdName" +
                ",AnsString,correct,incorrect,untouched,totalscore) VALUES ('"+instance.getRegId()+"','"+
                instance.getName()+"','"+instance.getAnsString()+"',"+instance.getCorrect()+","+instance.getIncorrect()
                +","+instance.getUntouched()+","+instance.getTotal()+")");

        insert.executeUpdate();


    }
    public static List<StdInstance> ReadtAllData(String CourseCode, String SubjectName) throws Exception {
        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement read=con.prepareStatement("SELECT * FROM "+Table);
        ResultSet resultSet=read.executeQuery();
        List<StdInstance> list=new  ArrayList<StdInstance>();

        while (resultSet.next())
        {
            String reg=resultSet.getString(1);
            int correct=resultSet.getInt(4);
            int incorrect=resultSet.getInt(5);
            int untouched=resultSet.getInt(6);
            int total=resultSet.getInt(7);
            StdInstance instance=new StdInstance(reg,correct,incorrect,untouched,total);
            list.add(instance);

        }


        return list;

    }
    public static void CheckUpdateResult(String CourseCode,String SubjectName) throws Exception {


        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement read=con.prepareStatement("SELECT * FROM "+Table);
        ResultSet resultSet=read.executeQuery();

        while(resultSet.next())
        {

            String ans=resultSet.getString(3);
            System.out.println(ans);
            int correct=0;
            int incorrect=0;
            int untouched=0;
            String reg=resultSet.getString(1);
            for(int i=0;i<ans.length();i++)
            {
                if(ans.charAt(i)=='X')
                    untouched++;
                else if(ans.charAt(i)==Helper.resultString.charAt(i))
                    correct++;
                else
                    incorrect++;
            }
            String add="'"+reg+"'";

            PreparedStatement insert=con.prepareStatement("UPDATE "+Table+" SET  correct = "+Integer.toString(correct)+", incorrect = "+Integer.toString(incorrect)+", untouched = "+Integer.toString(untouched)+", totalscore = "+Integer.toString(correct)+" WHERE RegNo = "+add);

            insert.executeUpdate();


        }

    }
    public static void UpdateAns(String CourseCode,String SubjectName,String reg,String Ans) throws Exception {


        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();


        String add="'"+reg+"'";
        Ans="'"+Ans+"'";

        System.out.println(add);

        PreparedStatement insert=con.prepareStatement("UPDATE "+Table+" SET  AnsString= "+Ans+" WHERE RegNo= "+add);

        insert.executeUpdate();

        }






    public static Connection getConnection() throws Exception{
        try {
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://localhost:3306/MCQ";
            String username="root";
            String password="";
            Class.forName(driver);
            Connection conn= DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
