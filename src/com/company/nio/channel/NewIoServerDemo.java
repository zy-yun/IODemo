package com.company.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO中的channel通道模型 及 ByteBuffer缓冲区模型 应用demo
 *
 * 经过验证：
 * 1.channel默认为阻塞的，需要手动设置为 false非阻塞
 * 2.实际channel必须要与selector结合使用才能是同步非阻塞 NIO模型
 * @Author zy
 * @Date 2020/06/13 19:24 PM
 */
public class NewIoServerDemo {


    public static void main(String[] args) {

        ServerSocketChannel serverSocketChannel = null;
        try {
            //打开一个一个channel
            serverSocketChannel = ServerSocketChannel.open();
            //设置为异步，默认为同步阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定一个端口
            serverSocketChannel.bind(new InetSocketAddress(8080));
            //监听
            while (true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                //因为是非阻塞，所以需要判断socketChannel不为null
                if (null!=socketChannel){
                    //初始化一个缓冲区大小
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(buffer);
                    if (read>0){
                        System.out.println("服务端收到的信息："+new String(buffer.array()));
                    }
                    //反转buffer
                    buffer.flip();
                    socketChannel.write(buffer);

                }else {
                    System.out.println("链接未就绪！");
                    Thread.sleep(1000);
                }
            }


        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
