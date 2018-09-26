package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Helper {

    public static boolean acceptClient = true ;

    public static List<SenderThread> senderThreadList = new ArrayList<>();

    public static boolean sendFile = false;

    public static String courseCode,subjectName,quesPath,ExamType;


    public static AtomicReference<String> startTime=new AtomicReference<String>();
    public static AtomicReference<String> endTime=new AtomicReference<String>();

    public static int totalQues;

    public static ArrayList<StdInstance> students=new ArrayList<>();
    public static boolean wait=true,run=false;
    public static String resultString="";


}
