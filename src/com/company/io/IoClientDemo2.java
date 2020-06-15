package com.company.io;

import java.io.*;
import java.net.Socket;

/**
 * IO客户端2代码
 * @Author zy
 * @Date 2020/06/15 20：00 PM
 */
public class IoClientDemo2 {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("这是一个来自客户端2的消息！\n");
            bufferedWriter.flush();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("2接受到的服务端的消息为：" + bufferedReader.readLine());


        } catch (IOException e) {
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
