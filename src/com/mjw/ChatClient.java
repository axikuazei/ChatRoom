package com.mjw;

import java.io.IOException;
import java.util.Scanner;

public class ChatClient {
    SocketSet client ;
    private void send(){
        while(true){
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            client.getWriter().write(s+"\n");
            client.getWriter().flush();
        }
    }
    private void receive() {
        String msg;
        while(true){
            try {
                msg=client.getReader().readLine();
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void start(){
        client.getWriter().write(client.getName()+"\n");
        client.getWriter().flush();
        new Thread(()->send()).start();
        new Thread(()->receive()).start();
    }

    public static void main(String[] args) {
        System.out.println("请输入姓名");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        ChatClient c =new ChatClient();
        c.client=new SocketSet("localhost",6666, name);
        System.out.println("已经成功连接....");
        c.start();
    }
}
