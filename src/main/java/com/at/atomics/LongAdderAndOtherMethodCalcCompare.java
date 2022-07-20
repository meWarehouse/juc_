package com.at.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @create 2022-07-20
 */
public class LongAdderAndOtherMethodCalcCompare {


    public static final int SIZE_THREAD = 500;
    public static final int _1W = 10000;

    public static void main(String[] args) throws Exception {


        ClickNumber clickNumber = new ClickNumber();

        long sT, eT;

        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch5 = new CountDownLatch(SIZE_THREAD);

        //====================================================  1

        sT = System.currentTimeMillis();

        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.addSync();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch1.countDown();
                }

            }, String.valueOf(i)).start();
        }

        countDownLatch1.await();

        eT = System.currentTimeMillis();

        System.out.println("----costTime: " + (eT - sT) + " 毫秒" + "\t add synchronized" + "\t" + clickNumber.number);



        //==================================================== 2

        sT = System.currentTimeMillis();

        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.addAtomicInt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch2.countDown();
                }

            }, String.valueOf(i)).start();
        }

        countDownLatch2.await();

        eT = System.currentTimeMillis();

        System.out.println("----costTime: " + (eT - sT) + " 毫秒" + "\t add AtomicInteger" + "\t" + clickNumber.atomicInteger.get());



        //==================================================== 3

        sT = System.currentTimeMillis();

        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.addAtomicLong();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch3.countDown();
                }

            }, String.valueOf(i)).start();
        }

        countDownLatch3.await();

        eT = System.currentTimeMillis();

        System.out.println("----costTime: " + (eT - sT) + " 毫秒" + "\t add AtomicLong" + "\t" + clickNumber.atomicLong.get());



        //==================================================== 4

        sT = System.currentTimeMillis();

        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.addLongAdder();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch4.countDown();
                }

            }, String.valueOf(i)).start();
        }

        countDownLatch4.await();

        eT = System.currentTimeMillis();

        System.out.println("----costTime: " + (eT - sT) + " 毫秒" + "\t add LongAdder" + "\t" + clickNumber.longAdder.sum());


        //==================================================== 5

        sT = System.currentTimeMillis();

        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.addLongAccumulator();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch5.countDown();
                }

            }, String.valueOf(i)).start();
        }

        countDownLatch5.await();

        eT = System.currentTimeMillis();

        System.out.println("----costTime: " + (eT - sT) + " 毫秒" + "\t add LongAccumulator" + "\t" + clickNumber.longAccumulator.get());




/*
----costTime: 14100 毫秒	 add synchronized	500000000
----costTime: 8435 毫秒	 add AtomicInteger	500000000
----costTime: 8499 毫秒	 add AtomicLong	500000000
----costTime: 456 毫秒	 add LongAdder	500000000
----costTime: 442 毫秒	 add LongAccumulator	500000000
 */











    }


}


class ClickNumber {

    // ====================================== synchronized

    int number = 0;

    public synchronized void addSync() {
        number++;
    }

    // ====================================== AtomicInteger

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addAtomicInt() {
        atomicInteger.incrementAndGet();
    }

    // ====================================== AtomicLong

    AtomicLong atomicLong = new AtomicLong(0);

    public void addAtomicLong() {
        atomicLong.incrementAndGet();
    }


    // ====================================== LongAdder

    LongAdder longAdder = new LongAdder();

    public void addLongAdder() {
        longAdder.increment();
    }


    // ====================================== LongAccumulator
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void addLongAccumulator() {
        longAccumulator.accumulate(1);
    }


}