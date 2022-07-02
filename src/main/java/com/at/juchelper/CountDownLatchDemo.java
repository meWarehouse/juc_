package com.at.juchelper;

import java.util.concurrent.CountDownLatch;

/**
 * @create 2022-07-02
 */
public class CountDownLatchDemo {

        /*

        CountDownLatch：倒计数器，又称闭锁。一个业务的执行需要等待一组业务执行完成
			CountDownLatch cdl = new CountDownLatch(等待线程数)
			cdl.countdown() // 计数减1
			cdl.await() // 设置阻塞点

			和join区别？
				都可以阻塞线程。
				join使用不够灵活，必须等待多有线程执行完成，才能执行阻塞任务
				可以在任务的任何点执行countdown方法放行阻塞任务

     */

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 走人。。。。。。。。。");
                countDownLatch.countDown();
            },String.valueOf(i)).start();

        }

        countDownLatch.await();


        System.out.println("关门.................");


    }

}
