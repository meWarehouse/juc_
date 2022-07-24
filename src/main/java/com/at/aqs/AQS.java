package com.at.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-24
 */
public class AQS {

    public static void main(String[] args) {



        ReentrantLock reentrantLock = new ReentrantLock();

        // A B C三个顾客，去银行办理业务，A先到，此时窗口空无一人，他优先获得办理窗口的机会，办理业务。


        // A 耗时严重，估计长期占有窗口
        new Thread(() -> {
            // code
            reentrantLock.lock();
            try {
                //code

                System.out.println("...come in A");

                try { TimeUnit.MINUTES.sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }

            }finally {
                reentrantLock.unlock();
            }

        }, "a").start();


        new Thread(() -> {
            // code
            reentrantLock.lock();
            try {
                //code
                System.out.println("...come in B");
            }finally {
                reentrantLock.unlock();
            }

        },"b").start();

        new Thread(() -> {
            // code
            reentrantLock.lock();
            try {
                //code
                System.out.println("...come in C");
            }finally {
                reentrantLock.unlock();
            }

        },"c").start();






    }

}
