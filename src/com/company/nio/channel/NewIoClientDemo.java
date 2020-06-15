package com.company.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class NewIoClientDemo {

    public static void main(String[] args) {
        try {
            //打开一个channel
            SocketChannel socketChannel = SocketChannel.open();
            //非阻塞，默认为阻塞
            socketChannel.configureBlocking(false);
            //指定一个连接地址
            socketChannel.connect(new InetSocketAddress("localhost",8080));
            //判断是否可以建立连接
            if (socketChannel.isConnectionPending()){
                //完成连接的建立
                socketChannel.finishConnect();
            }
            //创建一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //放置信息
            buffer.put("这是一个来自客户端的信息！".getBytes());
            //缓冲区反转
            buffer.flip();
            //发送到指定的端口
            socketChannel.write(buffer);

            //清空数据
            buffer.clear();
            int read = socketChannel.read(buffer);
            if (read>0){
                System.out.println("接受到服务端返回的信息："+new String(buffer.array()));
            }else {
                System.out.println("未接受到服务端的数据！");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
