package com.at.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @create 2022-07-02
 */
public class ExecutorsTest {

    public static void main(String[] args) {

        // 单一线程池连接
        // 一个任务一个线程
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        // 固定线程池连接
        // 执行长期任务性能较好
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        // 缓冲线程池连接
        // 执行大量短期异步任务
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


        for (int i = 0; i < 5; i++) {

            // execute submit 最终都是执行 java.util.concurrent.ThreadPoolExecutor.execute 方法

            singleThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });

            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });

            cachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });


        }

        singleThreadPool.shutdown();
        fixedThreadPool.shutdown();
        cachedThreadPool.shutdown();




    }

}
