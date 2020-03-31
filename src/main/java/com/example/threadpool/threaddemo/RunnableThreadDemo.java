package com.example.threadpool.threaddemo;

/**
 * @ProjectName threadpool
 * @Author: lancx
 * @Date: 2020/3/31 21:40
 * @Description: 直接创建线程
 */
public class RunnableThreadDemo {
    public static void main(String[] args) {
        long bf = System.currentTimeMillis();
        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行完毕");
                }
            }).start();
        }
        long ed = System.currentTimeMillis();
        System.out.println("执行时间:" + (ed - bf) + "s");
    }
}
