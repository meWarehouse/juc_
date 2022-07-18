package com.at.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @create 2022-07-18
 */
public class AtomicIntegerDemo {

    /*

        AtomicInteger
        AtomicBoolean
        AtomicLong

            public final int get() //获取当前的值
            public final int getAndSet(int newValue)//获取当前的值，并设置新的值
            public final int getAndIncrement()//获取当前的值，并自增
            public final int getAndDecrement() //获取当前的值，并自减
            public final int getAndAdd(int delta) //获取当前的值，并加上预期的值
            boolean compareAndSet(int expect, int update) //如果输入的数值等于预期值，则以原子方式将该值设置为输入值（update）

     */

    public static void main(String[] args) throws InterruptedException {


        Number number = new Number();

        CountDownLatch downLatch = new CountDownLatch(50);

        for (int i = 0; i < 50; i++) {

            new Thread(() -> {

                try {
                    for (int j = 0; j < 10000; j++) {
                        number.add0();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    downLatch.countDown();
                }

            },String.valueOf(i)).start();

        }

        downLatch.await();


        System.out.println(Thread.currentThread().getName() + "\t" + number.atomic);


    }

}


class Number{

    AtomicInteger atomic = new AtomicInteger(0);

    public void add0(){
        atomic.getAndIncrement();
    }


}
