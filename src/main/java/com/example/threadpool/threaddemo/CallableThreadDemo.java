package com.example.threadpool.threaddemo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ProjectName threadpool
 * @Author: lancx
 * @Date: 2020/3/31 21:53
 * @Description:
 */
public class CallableThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Random random = new Random();
                int i = random.nextInt();
                return "返回值为：" + System.currentTimeMillis();
            }
        };

        for (int i = 0; i < 3; i++) {
            FutureTask<String> futureTask = new FutureTask<String>(callable);
            Thread thread = new Thread(futureTask);
            System.out.println("threadName: " + thread.getName());
            thread.start();
            String s = futureTask.get();
            System.out.println(s);
        }
    }

}