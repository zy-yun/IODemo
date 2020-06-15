package com.company.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO 异步非阻塞demo
 * 小结：
 * 1.BIO解决了请求同步的问题，使用了线程池来解决的
 * 2.BIO存在一个瓶颈问题，线程池的大小决定了并发请求的大小
 * 3.应用场景，例如：Tomcat 7 之前，使用的就是BIO模型
 * @Author zy
 * @Date 2020/06/13 21:03 PM
 */
public class BlockIoServerDemo {


    public static void main(String[] args) {
        //使用线程池。创建一个固定大小的线程池数量为10，最大支持并发数为10的服务端
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket accept = serverSocket.accept();
                if (null != accept) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            try {
                                //服务端获取到客户端的信息
                                inputStream = accept.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                System.out.println("服务端接收到的消息为：" + reader.readLine());
                                //服务端对客户端发送一个响应
                                outputStream = accept.getOutputStream();
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                                writer.write("服务端收到你的请求了！哈哈\n");
                                writer.flush();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    System.out.println("服务端已就绪！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
