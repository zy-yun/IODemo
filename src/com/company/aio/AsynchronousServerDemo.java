package com.company.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousServerDemo {


    static Selector selector;

    public static void main(String[] args) {

        AsynchronousServerSocketChannel asynchronousServerSocketChannel;
        try {
            selector = Selector.open();
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(8080));

            Future<AsynchronousSocketChannel> accept = asynchronousServerSocketChannel.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
