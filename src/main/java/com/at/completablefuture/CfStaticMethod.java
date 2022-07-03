package com.at.completablefuture;

import java.sql.Connection;
import java.util.concurrent.*;


/**
 * @create 2022-07-03
 */
public class CfStaticMethod {

    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 2L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        runAsync();

        supplyAsync();

        System.out.println("main thread running...");


    }

    // runAsync 无 返回值
    // public static CompletableFuture<Void> runAsync(Runnable runnable)
    // public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
    public static void runAsync() throws ExecutionException, InterruptedException {

        System.out.println("... thread name：" + Thread.currentThread().getName());

        // 没有指定Executor的方法，直接使用默认的ForkJoinPool.commonPool() 作为它的线程池执行异步代码。
        CompletableFuture<Void> runAsync = CompletableFuture
                .runAsync(() -> {
                    try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "runAsync ... runAsync");
                });

        //如果指定线程池，则使用我们自定义的或者特别指定的线程池执行异步代码
//        CompletableFuture<Void> runAsync = CompletableFuture
//                .runAsync(() -> {
//                    try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
//                    System.out.println(Thread.currentThread().getName() + "runAsync ... runAsync");
//                },poolExecutor);

        System.out.println("runAsync.get()：" + runAsync.get());


    }

    // supplyAsync 有 返回值
    // public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
    // public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)
    public static void supplyAsync() throws ExecutionException, InterruptedException {

        System.out.println("... thread name：" + Thread.currentThread().getName());

        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "runAsync ... supplyAsync");
            return 1;
        }, poolExecutor);

        System.out.println("supplyAsync.get()："+ supplyAsync.get());


    }


}
