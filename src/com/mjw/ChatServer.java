package com.mjw;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {

    Map<String,SocketSet> sockets = new HashMap<>();
    ServerSocket serverSocket = null;

    public void start(){
        try {
            System.out.println("等待连接中...");
            serverSocket = new ServerSocket(6666);
            int n=0;
            while(true){
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        SocketSet socketSet= new SocketSet(serverSocket);               //阻塞，线程外
        sockets.put(socketSet.getName(),socketSet);
        new Thread(() -> {
            String msg ;
            BufferedReader br = socketSet.getReader();
            while (true) {
                try {
                    msg = br.readLine();
                    send(socketSet,msg);
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void send(SocketSet socketSet,String msg){            //从哪个SocketSet发来，内容是什么
        ArrayList<SocketSet> as = new ArrayList<>();
        for(String name : sockets.keySet()){
            Pattern p = Pattern.compile("@"+name);               //@某人的名字可以直接仅发送给他
            Matcher m = p.matcher(msg);
            if(m.find()){
                msg=msg.replace(m.group(), "");
                as.add(sockets.get(name));
            }
        }
        if(as.isEmpty()) {
            for(String name: sockets.keySet()){
                send(socketSet,msg,name);
            }
        }else{
            for (SocketSet s : as) {
                send(socketSet,msg,s.getName());
            }
        }
    }

    private void send(SocketSet socketSet, String msg, String name){  //从哪个SocketSet发来，内容是什么，发向谁
        if(socketSet.getName()!=name){
            SocketSet socketSet2 = sockets.get(name);
            PrintWriter pw2 = socketSet2.getWriter();
            pw2.write(socketSet.getName()+":"+msg + "\n");
            pw2.flush();
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}
