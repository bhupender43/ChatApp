package sample;


import java.io.*;
import java.util.*;
import java.net.*;

public class Server extends Thread{
    ServerWin gui_obj;
    private HashMap<String, PrintWriter> connections = new HashMap<>();
    private  int port;
    private  ServerSocket server;

    @Override
    public void run(){
        startServer();
    }

    private class ClientManager implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientManager(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run(){
            Date currtime= Calendar.getInstance().getTime();
            String single_msg="Connection from Socket[addr=" + socket.getInetAddress()+",localport="+port+"] at "+currtime+"\n";
            gui_obj.chatwin.appendText(single_msg);
            System.out.println("Connection from Socket[addr=" + socket.getInetAddress()+",localport="+port+"] at "+currtime);

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while(true) {
                    out.println("Enter your Name:");
                    name = in.readLine();
                    if (name == null) {
                        continue;
                    }
                    if(connections.containsKey(name)){
                        out.println("Already Exists User ");
                        continue;
                    }
                    break;
                }
                connections.put(name,out);
                out.println("-101");
                String first="Enter your Name:"+name;
                out.println(first);
                String message;
                while ((message = in.readLine()) != null) {
                    if (!message.isEmpty()) {
                        broadcastMessage(name + ": " + message);
                    }
                }

            }
            catch (Exception e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    connections.remove(name);
                }
            }
        }

    }

    public Server(ServerWin x){
        gui_obj=x;
    }

    private  void broadcastMessage(String message) {
        System.out.println(message);
        String single_msg=message+"\n";
        gui_obj.chatwin.appendText(single_msg);
        for (PrintWriter wrt: connections.values()) {
            wrt.println(message);
        }
    }

    public void startServer() {
        try {
            getRandomPort();
            Date currtime=Calendar.getInstance().getTime();
            String single_msg="MultiThreaded Server  with Port["+port +"] started At: " + currtime+"\n";
            gui_obj.chatwin.appendText(single_msg);
            System.out.println("MultiThreaded Server  with Port["+port +"] started At: " + currtime);
            while(true) {
                Socket x=server.accept();
                ClientManager obj=new ClientManager(x);
                Thread th = new Thread(obj);
                th.start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getRandomPort() {
        for(int i=1050;i<50000;i++){
            try {
                server=new ServerSocket(i);
                port=i;
                break;
            } catch (IOException ex) {
                continue;
            }
        }
    }

}
