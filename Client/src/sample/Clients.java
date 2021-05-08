package sample;
import java.io.*;
import java.util.*;
import java.net.*;
public class Clients extends Thread{
    private  Socket clientSocket;
    public ClientWin gui;
    @Override
    public  void run(){
        start_chat();
    }

    public Clients(ClientWin x){
        gui=x;
    }
     class Listener implements Runnable {
        private BufferedReader in;
        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String read=null;
                while(true) {
                    read = in.readLine();
                    if(read.equals("-101")){
                        gui.chatwin.clear();
                        read=null;
                    }
                    if (read != null && !(read.isEmpty())) {
                        String msg=read+"\n";
                        gui.chatwin.appendText(msg);
                        System.out.println(read);

                    }
                }
            } catch (IOException e) {
                return;
            }
        }

    }


    class Writer implements Runnable {
        private PrintWriter out;
        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                while(true) {
                    sleep(500);
                    if(gui.sing_msg.equals("-1")){
                        continue;
                    }
                    else{
                        out.println(gui.sing_msg);
                        gui.sing_msg="-1";
                    }
                }
            } catch (IOException | InterruptedException e) {
                return;
            }
        }
    }

    public void start_chat(){
        String ipAddress = "localhost";
        try {
            clientSocket = new Socket(ipAddress, 1050);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new Thread(new Writer()).start();
        new Thread(new Listener()).start();
    }
}
