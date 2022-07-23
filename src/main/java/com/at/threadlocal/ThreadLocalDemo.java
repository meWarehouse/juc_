package com.at.threadlocal;

import java.util.function.Supplier;

/**
 * @create 2022-07-23
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

//        Ticket ticket = new Ticket();
//
//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                for (int j = 0; j < 50; j++) {
//                    ticket.sale();
//                }
//            },String.valueOf(i)).start();
//        }


        House house = new House();


        new Thread(() -> {
            try {

                for (int i = 0; i < 10; i++) {
                    house.saleHouse();
                    house.sale();
                }

                System.out.println(Thread.currentThread().getName() + " 卖出：" + house.threadLocal.get());
                System.out.println(Thread.currentThread().getName() + " 卖出：" + house.tl.get());

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                house.threadLocal.remove();
                house.tl.remove();
            }
        },"A").start();


        new Thread(() -> {
            try {

                for (int i = 0; i < 10; i++) {
                    house.saleHouse();
                }

                System.out.println(Thread.currentThread().getName() + " 卖出：" + house.threadLocal.get());

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                house.threadLocal.remove();

            }
        },"B").start();



    }

}

class House{

    String house;

    // 人手一份，大家各自安好，没必要抢夺
//    ThreadLocal threadLocal = ThreadLocal.withInitial(
//            new Supplier<Integer>() {
//                @Override
//                public Integer get() {
//                    return 0;
//                }
//            }
//    )
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void saleHouse(){
        Integer value = threadLocal.get();
        threadLocal.set(++value);
    }

    ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 100);
    public void sale(){
        Integer value = tl.get();
        tl.set(++value);
    }


}



class Ticket{

    int number = 50;

    public synchronized void sale(){
        if(number > 0){
            System.out.println(Thread.currentThread().getName() + " 卖出第：" + (number--) + " 张票");
        }else {
            System.out.println("。。。。票买完了");
        }
    }

}
