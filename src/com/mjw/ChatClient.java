package com.mjw;

public class ChatClient{

    public static void main(String[] args) {
        Chat c =new Chat();
        c.getSocket("localhost",6666);
    }
}
