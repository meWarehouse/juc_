package com.at.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @create 2022-07-03
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("thread name："+Thread.currentThread().getName());
            try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            return 1;
        });

        new Thread(futureTask,"A").start();

//        System.out.println("获取子任务结果：" + futureTask.get());

        // 减缓阻塞 等待2s 过时不候
//        System.out.println("获取子任务结果：" + futureTask.get(2,TimeUnit.SECONDS));
//
//        System.out.println(" --- main 线程 running");


        // 克服阻塞 ---> 使用 轮询  --> 轮询浪费资源
        while (true){
            if(futureTask.isDone()){
                System.out.println("获取子任务结果：" + futureTask.get());
                break;
            }else {
                System.out.println(" --- main 线程 running");
            }
        }

    }

}
