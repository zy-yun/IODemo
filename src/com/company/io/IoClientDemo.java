package com.company.io;

import java.io.*;
import java.net.Socket;

public class IoClientDemo {

    public static void main(String[] args){
        Socket socket = null;
        try {
            socket = new Socket("localhost",8080);
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("这是一个来自客户端的消息！\n");
            Thread.sleep(20000);
            bufferedWriter.flush();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("接受到的服务端的消息为："+bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
