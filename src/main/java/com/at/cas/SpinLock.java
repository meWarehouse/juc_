package com.at.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @create 2022-07-17
 *
 * 实现一个自旋锁
 *      自旋锁好处：循环比较获取没有类似wait的阻塞。
 *
 *      通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 *      当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 *
 */
public class SpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){

        System.out.println(Thread.currentThread().getName() + " 尝试加锁...");

        while (!atomicReference.compareAndSet(null,Thread.currentThread())){

        }

        System.out.println(Thread.currentThread() + " 加锁成功");


    }


    public void unLock(){

        System.out.println(Thread.currentThread() + " 尝试解锁...");

        while (!atomicReference.compareAndSet(Thread.currentThread(),null)){

        }

        System.out.println(Thread.currentThread() + " 解锁成功");

    }

    public static void main(String[] args) {


        SpinLock spinLock = new SpinLock();

        new Thread(() -> {

            spinLock.lock();

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            spinLock.unLock();

        },"A").start();


        new Thread(() -> {

            spinLock.lock();

            spinLock.unLock();


        },"B").start();


    }



}
