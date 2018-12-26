package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    private volatile Thread blinker;
    private String threadName;

    public void setBlinker() {
        this.blinker = null;
    }

    public ServerThread(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        Thread thisThread = Thread.currentThread();
        blinker = thisThread;

        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("Port : "+ serverSocket.getLocalPort());
            Helper.port = Integer.toString(serverSocket.getLocalPort());

            while (true){
                System.out.println("Waiting...");
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection : " + socket);

                if(blinker != thisThread){
                    break;
                }

                Scanner scanstd = new Scanner(socket.getInputStream());

                String regno = scanstd.nextLine();
                String user = scanstd.nextLine();
                Helper.students.add(new StdInstance(regno,user));
                StdInstance instance=new StdInstance(regno,user,"xxx",0,0,0,0);
               DatabaseHelper.InsertData(instance,Helper.courseCode,Helper.subjectName);

                System.out.println(regno);

                PrintStream ps = new PrintStream(socket.getOutputStream());

                ps.println(Helper.courseCode);
                ps.println(Helper.subjectName);
                ps.println(Helper.startTime.toString());
                String tt = Helper.startTime.toString()+Helper.endTime.toString();
                System.out.println(tt);
                ps.println(tt);
                ps.println(Helper.totalQues);

                SenderThread sender = new SenderThread("SenderThread",socket);
                sender.start();

                Helper.senderThreadList.add(sender);

            }

            while(true){
                System.out.println("Total Connection : "+Helper.students.size());
                System.out.println("Waiting For Ans...");
                Socket socket = serverSocket.accept();

                Scanner sc = new Scanner(socket.getInputStream());

                String str = sc.next();
                String arr[] = str.split("#");
                String reg = arr[0];
                String ans = arr[1];
                System.out.println("Received Ans..." + str);

                boolean flag = false;
                for (StdInstance st:Helper.students) {

                    String roll = st.getRegId();

                    if(roll.equals(reg)){
                        flag = true;
                    }
                }

                if(flag){
                    Helper.ansSheetCounter++;
                    DatabaseHelper.UpdateAns(Helper.courseCode,Helper.subjectName,reg,ans);

                    if(Helper.ansSheetCounter == Helper.students.size()){
                        break;
                    }
                }else {
                    socket.close();
                }
            }

/*            if(blinker != thisThread){
                System.out.println("Sender Thread Closed ");
                serverSocket.close();
                socket.close();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
