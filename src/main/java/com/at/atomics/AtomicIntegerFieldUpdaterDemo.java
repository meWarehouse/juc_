package com.at.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @create 2022-07-20
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {


        BankAccount bankAcc = new BankAccount();

        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {


            new Thread(() -> {

                try {
                    for (int j = 0; j < 1000; j++) {

                        bankAcc.transfer(bankAcc);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            },String.valueOf(i)).start();

        }


        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t => branckAcc：" + bankAcc.getMoney());


    }

}

class BankAccount{

   private String bankName = "abc";

    //以一种线程安全的方式操作非线程安全对象内的某些字段

    //1 更新的对象属性必须使用 public volatile 修饰符。
   private volatile int money = 0;


    //2 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性
   AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");

   public void transfer(BankAccount bankAccount){

       atomicIntegerFieldUpdater.incrementAndGet(bankAccount);

   }

    public int getMoney() {
        return money;
    }
}
