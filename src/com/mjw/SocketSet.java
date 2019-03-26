package com.mjw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSet {
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private String name;
    public SocketSet(String host, int port,String name){
        try {
            this.socket=new Socket(host,port);
            this.name = name;
            pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public SocketSet(ServerSocket serverSocket) {
        try {
            socket = serverSocket.accept();
            pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            name = br.readLine();
            System.out.println(name+"已经连接...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PrintWriter getWriter(){
        return pw;
    }
    public BufferedReader getReader(){
        return br;
    }
    public String getName(){
        return name;
    }
}
