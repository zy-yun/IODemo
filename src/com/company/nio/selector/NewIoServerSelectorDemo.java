package com.company.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 完整的NIO模型Demo 编写
 * 具体体现为：可以有多个client客户端同时请求server，server端不阻塞，异步执行不同的已就绪的socketChannel，单线程轮询
 * @Author zy
 * @Date 2020/06/13 19:20 PM
 */
public class NewIoServerSelectorDemo {

    static Selector selector;

    public static void main(String[] args) {

        ServerSocketChannel serverSocketChannel = null;
        try {
            //打开一个selector
            selector = Selector.open();
            //打开一个一个channel
            serverSocketChannel = ServerSocketChannel.open();
            //设置为异步，默认为同步阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定一个端口
            serverSocketChannel.bind(new InetSocketAddress(8080));
            //将serverSocketChannel注册到selector上，类型为连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //监听
            while (true) {

                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isAcceptable()) {
                        //连接事件
                        handelAccept(next);

                    } else if (next.isReadable()) {
                        //读事件
                        handleReade(next);
                    }
                }


            }


        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接处理事件方法
     *
     * @param selectionKey
     */
    public static void handelAccept(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("服务端接收到你的连接了".getBytes()));
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读事件处理方法
     *
     * @param selectionKey
     */
    public static void handleReade(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        //定一个缓冲区buffer大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取值
        try {
            socketChannel.read(buffer);
            System.out.println("接收到客户端的信息为："+new String(buffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
