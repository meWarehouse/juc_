package com.at.threadpool;

import java.util.concurrent.*;

/**
 * @create 2022-07-02
 */
public class ConsumerThreadPool {

    public static void main(String[] args) {


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2,
                16,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.CallerRunsPolicy()
//                new ThreadPoolExecutor.AbortPolicy()
//                new ThreadPoolExecutor.DiscardOldestPolicy()
//                new ThreadPoolExecutor.DiscardPolicy()
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("自定义拒绝者策略");
                    }
                }
        );


        try {
            for (int i = 0; i < 10; i++) {

                poolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 正在执行任务");
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            poolExecutor.shutdown();

        }


    }

}
