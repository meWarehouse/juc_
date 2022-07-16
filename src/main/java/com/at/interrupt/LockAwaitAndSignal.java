package com.at.interrupt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-16
 */
public class LockAwaitAndSignal {

    public static void main(String[] args) {

        LockResource resource = new LockResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.desc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.desc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();


    }

}

class LockResource{

    private int shareResource = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();


    public void inc() throws InterruptedException {

        lock.lock();

        try {

            // 判断 虚假唤醒 用 while
            while (shareResource != 0) condition.await();

            // 干活
            shareResource++;

            System.out.println(Thread.currentThread().getName() + " -> " + shareResource);

            //通知
            condition.signalAll();

        }finally {
            lock.unlock();
        }

    }

    public void desc() throws InterruptedException {

        lock.lock();

        try {

            // 判断 虚假唤醒 用 while
            while (shareResource != 1) condition.await();

            // 干活
            shareResource--;

            System.out.println(Thread.currentThread().getName() + " -> " + shareResource);

            //通知
            condition.signalAll();

        }finally {
            lock.unlock();
        }

    }


}

