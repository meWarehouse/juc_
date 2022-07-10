package com.at.locks;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-10
 */
public class DeadLock {


    final static Object objectA = new Object();
    final static Object objectB = new Object();

    public static void main(String[] args) {


        new Thread(() -> {

            synchronized (objectA){

                System.out.println("--- 线程 " + Thread.currentThread().getName() + " 持有 objectA 锁 ---");

                try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (objectB){

                    System.out.println("--- 线程 " + Thread.currentThread().getName() + " 持有 objectB 锁 ---");

                }

            }

        },"A").start();


        try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }


        new Thread(() -> {

            synchronized (objectB){

                System.out.println("--- 线程 " + Thread.currentThread().getName() + " 持有 objectB 锁 ---");

                synchronized (objectA){

                    System.out.println("--- 线程 " + Thread.currentThread().getName() + " 持有 objectA 锁 ---");

                }

            }

        },"B").start();



    }

}
