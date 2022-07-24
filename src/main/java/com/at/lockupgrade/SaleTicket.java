package com.at.lockupgrade;

/**
 * @create 2022-07-24
 */
public class SaleTicket {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 50; j++) {
                    ticket.sale();
                }
            },String.valueOf(i)).start();
        }

        /*
0 卖出第：50
0 卖出第：49
0 卖出第：48
0 卖出第：47
0 卖出第：46
0 卖出第：45
0 卖出第：44
0 卖出第：43
0 卖出第：42
0 卖出第：41
0 卖出第：40
0 卖出第：39
0 卖出第：38
0 卖出第：37
0 卖出第：36
0 卖出第：35
0 卖出第：34
0 卖出第：33
0 卖出第：32
0 卖出第：31
0 卖出第：30
0 卖出第：29
0 卖出第：28
0 卖出第：27
0 卖出第：26
0 卖出第：25
0 卖出第：24
0 卖出第：23
0 卖出第：22
0 卖出第：21
0 卖出第：20
0 卖出第：19
0 卖出第：18
0 卖出第：17
0 卖出第：16
0 卖出第：15
0 卖出第：14
0 卖出第：13
0 卖出第：12
0 卖出第：11
0 卖出第：10
0 卖出第：9
0 卖出第：8
0 卖出第：7
0 卖出第：6
0 卖出第：5
0 卖出第：4
0 卖出第：3
0 卖出第：2
0 卖出第：1
         */

    }

}


class Ticket{

    int num = 50;

    public synchronized void sale(){
        if(num > 0){
            System.out.println(Thread.currentThread().getName() + " 卖出第：" + (num--));
        }
    }

}