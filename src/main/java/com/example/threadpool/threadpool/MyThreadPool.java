package com.example.threadpool.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ProjectName threadpool
 * @Author: lancx
 * @Date: 2020/3/31 22:21
 * @Description:
 */
public class MyThreadPool {
    // runable 可当作是任务， BlockingQueue<Runnable> 即为多个任务的阻塞集合
    private BlockingQueue<Runnable> blockingQueue;
    // 管理线程的集合
    private List<Thread> workers;

    // 具体工作的每个线程
    public static class Worker extends Thread {
        private MyThreadPool pool;

        public Worker(MyThreadPool pool) {
            this.pool = pool;
        }

        @Override
        // 在线程池内的工作
        public void run() {
            // 重复执行
            while (true) {
                Runnable task = null;

                try {
                    // take 方法又阻塞特性 即没有任务是会阻塞
                    task = this.pool.blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    task.run();
                    System.out.println("线程：" + Thread.currentThread().getName() + "执行完成");
                }
            }

        }
    }

    // 线程池构造方法
    public MyThreadPool(int poolSize, int taskSize) {

        if (poolSize <= 0 || taskSize <= 0) {
            throw new IllegalArgumentException("非法入参");
        }
        this.blockingQueue = new LinkedBlockingQueue<>(taskSize);
        this.workers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(this);
            workers.add(worker);
            worker.start();
        }
    }

    public boolean submit(Runnable task) {
        return this.blockingQueue.offer(task);
    }

    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(3, 6);
        for (int i = 0; i < 6; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    // todo 逻辑
                    System.out.println("放入任务到线程");
                }
            });
        }
    }
}
