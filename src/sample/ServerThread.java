package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    public ServerThread() {

    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            ServerSocket serverSocket = new ServerSocket(226);

            while (Helper.acceptClient){
                System.out.println("Waiting...");
                socket = serverSocket.accept();
                System.out.println("Accepted connection : " + socket);

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
                ps.println(Helper.startTime);
                ps.println(Helper.endTime);
                ps.println(Helper.totalQues);

                SenderThread sender = new SenderThread(socket);
                sender.start();

            }

            if(!Helper.acceptClient){
                System.out.println("No more Clients ... ");
//                serverSocket.close();
//                socket.close();
                Helper.acceptClient = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
