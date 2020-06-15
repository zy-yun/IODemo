package com.company.bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class BlockIoClientDemo2 {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost",8080);
            OutputStream outputStream = socket.getOutputStream();
            Thread.sleep(20000);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write("这是来自客户端的一个信息2！\n");
            writer.flush();
        } catch (IOException | InterruptedException e) {
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
