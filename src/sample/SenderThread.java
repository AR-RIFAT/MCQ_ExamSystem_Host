package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SenderThread extends Thread {

    public final static String FILE_TO_SEND = "C:\\Users\\Rifat\\Desktop\\pics 2 Tourist Guide\\ques.pdf";
    Socket socket;
    private String threadName;

    private volatile Thread blinker;

    public void setBlinker() {
        this.blinker = null;
    }

    public SenderThread(String name, Socket s) {
        this.threadName = name;
        this.socket = s;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);

/*        Thread thisThread = Thread.currentThread();
        blinker = thisThread;*/

        while(true){


                try {
                    if(Helper.sendFile)
                    {
                        System.out.println("file sending...");
                        File file = new File(Helper.quesPath);

                        byte[] bytes = new byte[1];
                        InputStream in = new BufferedInputStream(new FileInputStream(file));
                        OutputStream out = socket.getOutputStream();

                        int count;
                        while ((count = in.read(bytes)) > 0) {
                            System.out.println("ec : "+count);
                            out.write(bytes, 0, count);
                        }

                        out.flush();
                        out.close();

                        break;
                    }
                } catch (IOException e) {
                    System.out.println("aMI ekhane");
                    e.printStackTrace();
                }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}