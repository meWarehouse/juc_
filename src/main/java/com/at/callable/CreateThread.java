package com.at.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @create 2022-07-02
 */
public class CreateThread {


    /*
        1.Thread
		2.Runnable
		上面两种方式的缺陷：
			1.无法获取子任务的返回结果集
			2.无法捕获子任务的异常信息
		3.Callable接口 + FutureTask（本质就是Runnable，也是一个Future对象）
			1.编写一个实现类，实现Callable接口
			2.实例化一个Callable对象
			3.实例化一个FutureTask对象，以上一步Callable对象作为参数
			4.以FutureTask对象作为参数，初始化一个Thread对象。并启动
			5.可以通过FutureTask对象的get方法，阻塞获取子任务的返回结果集或者捕获子任务的异常信息
			6.也可以通过轮询方式（isDone方法）获取子任务的执行状态

			Future接口可以定义一个未来的任务，以便在将来的某个时刻获取子任务的返回结果集
				get方法，建议该方法使用时尽量靠后。如果同一个任务执行多次的情况下，会复用之前的执行结果
				isDone方法

			Callable和Runnable的区别？
				都是可以创建多线程程序的函数式接口。
				1.接口名称不同，其中的方法不同：call方法 run方法
				2.call方法有返回值，所以可以获取子任务的返回结果集
				3.call方法有异常抛出，所以可以捕获子任务异常信息
				4.需要结合FutureTask才可以初始化子线程程序

			创建多线程有几种方式？
				传统方式有两种：继承Thread类；实现Runnable接口
				jdk1.5开始juc提供了两种方式：Callable接口 + FutureTask；线程池

     */


    public static void main(String[] args) {

        // 继承 Thread
        new ThreadDemo().start();

        // 实现 Runnable 接口
        new Thread(new RunnableDemo()).start();

        // 实现 Callable 接口
        CallableDemo callable = new CallableDemo();
        FutureTask<Long> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        // 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.submit(() -> System.out.println(Thread.currentThread().getName()));


    }


    static class ThreadDemo extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class RunnableDemo implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class CallableDemo implements Callable<Long>{
        @Override
        public Long call() throws Exception {
            return Thread.currentThread().getId();
        }
    }


}
