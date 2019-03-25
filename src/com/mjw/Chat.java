package com.mjw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private ServerSocket serverSocket=null;

    public void getSocket(String host, int port){
        try {
            socket = new Socket(host,port);
            System.out.println("已经成功连接");
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getSocket(int port){
        try {
            serverSocket = new ServerSocket(port);
            long n=0;
            while (true){
                socket=serverSocket.accept();
                System.out.println(++n+"个连接已经建立");
                start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            new Thread(()->send()).start();
            new Thread(()->receive()).start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void send(){
        while(true){
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            pw.write(s+"\n");
            pw.flush();
        }
    }

    private void receive(){
        while(true){
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
        }
    }
}
