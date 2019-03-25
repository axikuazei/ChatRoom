package com.mjw;


public class ChatServer{
    static final Chat chat = new Chat();
    public static void main(String[] args) {
        new Thread(()-> chat.getSocket(6666)).start();
    }
}
