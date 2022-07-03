package com.at.completablefuture;

import java.util.concurrent.*;


/**
 * @create 2022-07-03
 */
public class CfStaticMethod {

    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 2L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        runAsync();


        System.out.println("main thread running...");


    }

    // runAsync 无 返回值
    // public static CompletableFuture<Void> runAsync(Runnable runnable)
    // public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
    public static void runAsync() throws ExecutionException, InterruptedException {

        System.out.println("... thread name：" + Thread.currentThread().getName());

        CompletableFuture<Void> future = CompletableFuture
                .runAsync(() -> {
                    try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "runAsync ... future");
                }, poolExecutor);

        System.out.println("future.get()：" + future.get());


    }


}
