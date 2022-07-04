package com.at.basethread;

/**
 * @create 2022-07-04
 */
public class SaleTickets {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                for (int j = 0; j < 30; j++) {
                    ticket.sale();
                }
            },String.valueOf(i)).start();

        }


    }

}

class Ticket{

    private int tickets = 30;


    public void sale(){
        if(tickets > 0){
            System.out.println(Thread.currentThread().getName() + "\t 卖出第" + (tickets--) + "张票，还剩： " + tickets);
        }
    }


}
