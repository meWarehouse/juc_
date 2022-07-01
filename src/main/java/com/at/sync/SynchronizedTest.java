package com.at.sync;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-01
 */
public class SynchronizedTest {


    private int tickets = 30;

    public void sale() {
        if (tickets > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 卖出第 " + (tickets--) + "张票，还剩： " + tickets);
        }
/*
    超卖

C	 卖出第 30张票，还剩： 27
A	 卖出第 28张票，还剩： 27
B	 卖出第 29张票，还剩： 27
C	 卖出第 26张票，还剩： 25
B	 卖出第 25张票，还剩： 24
A	 卖出第 27张票，还剩： 25
A	 卖出第 24张票，还剩： 22
C	 卖出第 23张票，还剩： 22
B	 卖出第 22张票，还剩： 21
B	 卖出第 21张票，还剩： 20
A	 卖出第 20张票，还剩： 19
C	 卖出第 21张票，还剩： 20
C	 卖出第 19张票，还剩： 18
A	 卖出第 19张票，还剩： 18
B	 卖出第 19张票，还剩： 18
C	 卖出第 18张票，还剩： 17
B	 卖出第 18张票，还剩： 17
A	 卖出第 18张票，还剩： 17
B	 卖出第 17张票，还剩： 16
A	 卖出第 17张票，还剩： 16
C	 卖出第 16张票，还剩： 15
C	 卖出第 15张票，还剩： 13
B	 卖出第 15张票，还剩： 13
A	 卖出第 14张票，还剩： 13
C	 卖出第 12张票，还剩： 11
A	 卖出第 13张票，还剩： 11
B	 卖出第 13张票，还剩： 11
A	 卖出第 11张票，还剩： 10
B	 卖出第 11张票，还剩： 10
C	 卖出第 11张票，还剩： 10
A	 卖出第 10张票，还剩： 8
C	 卖出第 9张票，还剩： 8
B	 卖出第 10张票，还剩： 8
C	 卖出第 8张票，还剩： 7
B	 卖出第 8张票，还剩： 7
A	 卖出第 8张票，还剩： 7
B	 卖出第 6张票，还剩： 5
C	 卖出第 7张票，还剩： 5
A	 卖出第 7张票，还剩： 5
C	 卖出第 5张票，还剩： 4
B	 卖出第 3张票，还剩： 2
A	 卖出第 4张票，还剩： 3
C	 卖出第 2张票，还剩： 1
B	 卖出第 2张票，还剩： 1
A	 卖出第 2张票，还剩： 1
A	 卖出第 1张票，还剩： 0
C	 卖出第 1张票，还剩： 0
B	 卖出第 1张票，还剩： 0

*/


    }


    public synchronized void lockSale() {
        if (tickets > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 卖出第 " + (tickets--) + "张票，还剩： " + tickets);
        }
/*
    正常
A	 卖出第 30张票，还剩： 29
A	 卖出第 29张票，还剩： 28
A	 卖出第 28张票，还剩： 27
A	 卖出第 27张票，还剩： 26
A	 卖出第 26张票，还剩： 25
A	 卖出第 25张票，还剩： 24
A	 卖出第 24张票，还剩： 23
A	 卖出第 23张票，还剩： 22
A	 卖出第 22张票，还剩： 21
A	 卖出第 21张票，还剩： 20
A	 卖出第 20张票，还剩： 19
A	 卖出第 19张票，还剩： 18
A	 卖出第 18张票，还剩： 17
A	 卖出第 17张票，还剩： 16
A	 卖出第 16张票，还剩： 15
A	 卖出第 15张票，还剩： 14
A	 卖出第 14张票，还剩： 13
A	 卖出第 13张票，还剩： 12
A	 卖出第 12张票，还剩： 11
A	 卖出第 11张票，还剩： 10
A	 卖出第 10张票，还剩： 9
A	 卖出第 9张票，还剩： 8
A	 卖出第 8张票，还剩： 7
A	 卖出第 7张票，还剩： 6
A	 卖出第 6张票，还剩： 5
A	 卖出第 5张票，还剩： 4
A	 卖出第 4张票，还剩： 3
A	 卖出第 3张票，还剩： 2
A	 卖出第 2张票，还剩： 1
A	 卖出第 1张票，还剩： 0
 */
    }



    public static void main(String[] args) {

        SynchronizedTest ticket = new SynchronizedTest();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
//                ticket.lockSale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
//                ticket.lockSale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
//                ticket.lockSale();
            }
        }, "C").start();


    }


}

