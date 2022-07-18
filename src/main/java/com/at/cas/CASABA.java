package com.at.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @create 2022-07-17
 */
public class CASABA {


    public static void main(String[] args) {

        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(0, 0);



        new Thread(() -> {

            int stamp = stampedReference.getStamp();
            int valueA = stampedReference.getReference().intValue();

            System.out.println(Thread.currentThread().getName() + " -> 线程操作前的 stamp = " + stamp + " ,value = " + valueA);

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            stampedReference.compareAndSet(valueA,100,stamp,stamp+1);

            System.out.println(Thread.currentThread().getName() + " -> 线程操作后的 stamp = " + stampedReference.getStamp() + " ,value = " + stampedReference.getReference().intValue());



        },"A").start();



        new Thread(() -> {

            int stamp = stampedReference.getStamp();
            int valueB = stampedReference.getReference().intValue();

            System.out.println(Thread.currentThread().getName() + " -> 线程操作前的 stamp = " + stamp + " ,value = " + valueB);

            stampedReference.compareAndSet(valueB,100,stamp,stamp+1);

            System.out.println(Thread.currentThread().getName() + " -> 线程 第一次 操作后的 stamp = " + stampedReference.getStamp() + " ,value = " + stampedReference.getReference().intValue());


            stampedReference.compareAndSet(stampedReference.getReference().intValue(),0,stampedReference.getStamp(),stampedReference.getStamp()+1);

            System.out.println(Thread.currentThread().getName() + " -> 线程 第二次 操作后的 stamp = " + stampedReference.getStamp() + " ,value = " + stampedReference.getReference().intValue());



        },"B").start();



    }

    public static void m1(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        new Thread(() -> {

            int atomicA = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " 线程操作前的 atomicInteger = " + atomicA);

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            atomicInteger.compareAndSet(atomicA,100);

            System.out.println(Thread.currentThread().getName() + " 线程操作后的 atomicInteger = " + atomicInteger.get());

        },"A").start();


        new Thread(() -> {

            int atomicB = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " 线程操作前的 atomicInteger = " + atomicB);

            atomicInteger.compareAndSet(atomicB,100);

            System.out.println(Thread.currentThread().getName() + " 线程操作 第一次 后的 atomicInteger = " + atomicInteger.get());

            atomicInteger.compareAndSet(100,0);

            System.out.println(Thread.currentThread().getName() + " 线程操作 第二次 后的 atomicInteger = " + atomicInteger.get());

        },"B").start();



    }

}
