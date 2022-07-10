package com.at.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-04
 */
public class SaleTickets {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                for (int j = 0; j < 30; j++) {
//                    ticket.sale();
//                    ticket.saleSync();
//                    ticket.saleLock();
                    ticket.saleLockFair();
                }
            }, String.valueOf(i)).start();

        }


    }

}

class Ticket {

    private int tickets = 30;

    private Object object = new Object();

    private Lock lock = new ReentrantLock();

    private Lock fairLock = new ReentrantLock(true);


    public void sale() {
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "\t 卖出第" + (tickets--) + "张票，还剩： " + tickets);
        }
    }

    public void saleSync() {
        synchronized (object) {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第" + (tickets--) + "张票，还剩： " + tickets);
            }
        }
    }

    public void saleLock() {
        lock.lock();
        try {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第" + (tickets--) + "张票，还剩： " + tickets);
            }
        } finally {
            lock.unlock();
        }
    }

    public void saleLockFair() {
        fairLock.lock();
        try {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第" + (tickets--) + "张票，还剩： " + tickets);
            }
        } finally {
            fairLock.unlock();
        }
    }

}
