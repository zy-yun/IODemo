package com.company.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServerDemo {

    public static void main(String[] args) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(8080);
            //一直监听端口
            while (true){
                Socket accept = socket.accept();
                if (null!=accept){
                    InputStream inputStream = accept.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    System.out.println("服务端收到的消息为："+bufferedReader.readLine());
                    OutputStream outputStream = accept.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.write("服务端接受到client的消息了！\n");
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
