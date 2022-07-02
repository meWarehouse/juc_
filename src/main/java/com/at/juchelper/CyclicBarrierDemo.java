package com.at.juchelper;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @create 2022-07-02
 */
public class CyclicBarrierDemo {

    /*

        CyclicBarrier：循环栅栏。可以设置多个屏障点
			CyclicBarrier barrier = new CyclicBarrier(相互等待的线程数，通用的业务逻辑)
			barrier.await()方法可以设置屏障点

			和CountDownLatch区别？
				1.CountDownLatch是一次性的；CyclicBarrier可以设置多个屏障点
				2.CountDownLatch一个线程等待多个线程执行完成；CyclicBarrier多个线程之间在某个屏障点相互等待

     */

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("召唤神龙"));

        for (int i = 0; i < 7; i++) {

            int finalI = i;

            new Thread(() -> {

                System.out.println("收集到第：" + (finalI + 1) + " 颗龙珠");

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }


            }, String.valueOf(i)).start();

        }


    }

}
