package com.at.locks;

import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-10
 */
public class ReeLock {


    /*

        在一个Synchronized修饰的方法或代码块的内部调用本类的其他Synchronized修饰的方法或代码块时，是永远可以得到锁的

        每个锁对象拥有一个锁计数器和一个指向持有该锁的线程的指针。

        当执行monitorenter时，如果目标锁对象的计数器为零，那么说明它没有被其他线程所持有，Java虚拟机会将该锁对象的持有线程设置为当前线程，并且将其计数器加1。

        在目标锁对象的计数器不为零的情况下，如果锁对象的持有线程是当前线程，那么 Java 虚拟机可以将其计数器加1，否则需要等待，直至持有线程释放该锁。

        当执行monitorexit时，Java虚拟机则需将锁对象的计数器减1。计数器为零代表锁已被释放。

     */
    public synchronized void m1(){
        m1();
    }

    static Lock lock = new ReentrantLock();


    public static void main(String[] args) {

        // synchronized
        new Thread(()->{

            synchronized (ReeLock.class){

                System.out.println("外层锁...");

                synchronized (ReeLock.class){

                    System.out.println("内层锁...");

                }

            }

        }).start();

        // ReentrantLock
        new Thread(() -> {

            lock.lock();
            try {
                System.out.println("----外层调用 lock");

                lock.lock();
                try {
                    System.out.println("----内层调用 lock");
                }finally {

                    // 由于加锁次数和释放次数不一样，线程 B 始终无法获取到锁，导致一直在等待。


//                    lock.unlock(); // 正常情况，加锁几次就要解锁几次
                }

            }finally {
                lock.unlock();
            }

        }).start();

        new Thread(() -> {
            lock.lock();
            try
            {
                System.out.println("b thread----外层调用lock");
            }finally {
                lock.unlock();
            }
        },"B").start();





    }

}
