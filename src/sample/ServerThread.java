package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    private volatile Thread blinker;

    public void setBlinker() {
        this.blinker = null;
    }

    public ServerThread() {

    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        blinker = thisThread;
        Socket socket = new Socket();
        try {
            ServerSocket serverSocket = new ServerSocket(226);

            while (blinker == thisThread){
                System.out.println("Waiting...");
                socket = serverSocket.accept();
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
                ps.println(Helper.endTime.toString());
                ps.println(Helper.totalQues);

                SenderThread sender = new SenderThread(socket);
                sender.start();

                Helper.senderThreadList.add(sender);

            }

            if(blinker != thisThread){
                System.out.println("Sender Thread Closed ");
                serverSocket.close();
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
