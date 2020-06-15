package com.company.bio;

import java.io.*;
import java.net.Socket;

public class BlockIoClientDemo {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost",8080);
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write("这是来自客户端的一个信息！\n");
            writer.flush();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("来自服务端的响应："+reader.readLine());
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
