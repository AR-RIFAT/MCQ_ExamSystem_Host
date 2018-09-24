package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SenderThread extends Thread {

    public final static String FILE_TO_SEND = "C:\\Users\\Rifat\\Desktop\\pics 2 Tourist Guide\\ques.pdf";
    Socket socket;

    public SenderThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {

        while(true){


                try {
                    if(Helper.sendFile)
                    {
                        System.out.println("file sending...");
                        File file = new File(Helper.quesPath);
                        // Get the size of the file
                        long length = file.length();
                        byte[] bytes = new byte[16 * 1024];
                        InputStream in = new FileInputStream(file);
                        OutputStream out = socket.getOutputStream();

                        int count;
                        while ((count = in.read(bytes)) > 0) {
                            out.write(bytes, 0, count);
                            System.out.println("ec : "+count);
                        }

                       //out.close();
                       //out.flush();
                        System.out.println("Aha : "+count);

/*                        byte[] test = new byte[17*1024];
                        out.write(test,0,17408);
                        out.flush();*/

                       in.close();
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

        while(true){
            try {
                Scanner ansSheet = new Scanner(socket.getInputStream());
                System.out.println("Tumi amar Jibon");

                String ans=ansSheet.nextLine();
                System.out.println(ans);
                String reg=ans.substring(0,10);
                String StdAns=ans.substring(10);
                DatabaseHelper.UpdateAns(Helper.courseCode,Helper.subjectName,reg,StdAns);
                System.out.println("Answer Received");
                break;

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(true){
            try {
                sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

/*

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;

        while(true){
                // send file
                try{
                if(true){
                try{
                File myFile = new File (FILE_TO_SEND);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Done.");
                }catch (Exception e){
                System.out.println("Vul hoise... Mara Khaiso ...");
                }finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
                }
                }
                }catch(Exception e) {

                }
                }*/
