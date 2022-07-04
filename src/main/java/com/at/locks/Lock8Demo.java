package com.at.locks;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-04
 * <p>
 * 线程   操作  资源类  8锁案例说明
 * 1    标准访问有ab两个线程，请问先打印邮件还是短信 e
 * 2    sendEmail方法暂停3秒钟，请问先打印邮件还是短信 e
 * 3    新增一个普通的hello方法，请问先打印邮件还是hello h
 * 4    有两部手机，请问先打印邮件还是短信 s
 * 5    两个静态同步方法，同1部手机，请问先打印邮件还是短信 e
 * 6    两个静态同步方法， 2部手机，请问先打印邮件还是短信 e
 * 7    1个静态同步方法，1个普通同步方法,同1部手机，请问先打印邮件还是短信 s
 * 8    1个静态同步方法，1个普通同步方法,2部手机，请问先打印邮件还是短信 s
 */
public class Lock8Demo {

    public static void main(String[] args) {

        Phone phone = new Phone();
        Phone phone1 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        },"A").start();

        // 确保 A 线程先 就绪
        try { TimeUnit.MILLISECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
//            phone.sendSMS();

//            phone.hello();

            phone1.sendSMS();

        },"B").start();


    }

}


class Phone {

    public static synchronized void sendEmail() {
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("sendEmail ...");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS ...");
    }

    public void hello()
    {
        System.out.println("-------hello");
    }



}