package com.at.interrupt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @create 2022-07-10
 */
public class ThreadInterrupt {

    private static volatile boolean isStop = false;

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);


    /**
     * AtomicBoolean 原子引用
     */
    public static void m2(String[] args) {

        new Thread(() -> {

            while (true){
                if(atomicBoolean.get()){
                    System.out.println(String.format("... %s 线程被中断",Thread.currentThread().getName()));
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 线程 running...");
            }

        },"A").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
            System.out.println("线程 " + Thread.currentThread().getName() + " 将 线程 A 中断");
        },"B").start();

        System.out.println("main thread running.....");

    }

    /**
     * volatile 可见性
     */
    public static void m1(String[] args) {

        new Thread(() -> {

            while (true){
                if(isStop){
                    System.out.println(String.format("... %s 线程被中断",Thread.currentThread().getName()));
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " running...");
            }

        },"A").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
            System.out.println(Thread.currentThread().getName() + " 将 A 线程中断");
        },"B").start();

        System.out.println("main thread running.....");



    }


}
