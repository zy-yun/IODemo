package com.company.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NewIoClientSelectorDemo2 {
    static Selector selector;

    public static void main(String[] args) {
        try {
            //打开一个selector
            selector= Selector.open();
            //打开一个channel
            SocketChannel socketChannel = SocketChannel.open();
            //非阻塞，默认为阻塞
            socketChannel.configureBlocking(false);
            //指定一个连接地址
            socketChannel.connect(new InetSocketAddress("localhost",8080));

            //将socketChannel注册到多路复用器上 类型为connection
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                //多路复用器 单线程轮询
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    //todo 此处为事件驱动机制
                    if (next.isConnectable()){
                        //连接事件
                        handleConnection(next);
                    }else if (next.isReadable()){
                        //读事件
                        handleReade(next);
                    }
                }

            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接事件 方法
     * @param selectionKey
     */
    public static void handleConnection(SelectionKey selectionKey) throws IOException, InterruptedException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        //判断连接
        if (socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
        }
        //非阻塞
        socketChannel.configureBlocking(false);
        Thread.sleep(20000);
        //发送消息
        socketChannel.write(ByteBuffer.wrap("这是一个来自客户端的消息2！".getBytes()));
        //将此连接channel注册到selector上，类型为读
        socketChannel.register(selector,SelectionKey.OP_READ);

    }

    public static void handleReade(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        //定义一个byteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //此处肯定有值，因为现在使用selector后为事件驱动机制，
        socketChannel.read(buffer);
        System.out.println("接受到的服务端消息为2："+new String(buffer.array()));

    }
}
