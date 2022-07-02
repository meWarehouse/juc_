package com.at.juchelper;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-02
 */
public class SemaphoreDemo {

    /*
        Semaphore：信号量
                应用于并发量大，而资源紧张的情况。
                Semaphore semaphore = new Semaphore(资源量);
                semaphore.acquire(); //获取一个资源
                semaphore.release(); // 释放一个资源

     */
    public static void main(String[] args) {

        // 十辆车  用三个车位

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {

                try {

                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " \t抢占到车位");

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " \t 离开了车位");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }, String.valueOf(i)).start();

        }


    }


}
