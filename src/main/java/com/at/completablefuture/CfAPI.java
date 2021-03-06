package com.at.completablefuture;


import java.util.concurrent.*;

/**
 * @create 2022-07-03
 */
public class CfAPI {

    public static void main(String[] args) throws Exception {


//        m1();

//        m2();

//        m3();

//        m4();

        m5();

    }

    /**
     * 对计算结果进行合并
     * thenCombine
     */
    public static void m5(){

        System.out.println(CompletableFuture
                .supplyAsync(() -> {
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                    return 10;
                })
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> "20"),
                        (r1, r2) -> {
                            return r1 + r2;
                        }
                ).join());

    }


    /**
     * 对计算速度进行选用
     *  applyToEither 谁算的快选谁
     */
    public static void m4(){

        System.out.println(
                CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return 1;
                    })
                    .applyToEither(
                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return 2;
                            }),
                            r -> {
                                return r;
                            }
                    )
                    .join()
        );

        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }


    }



    /**
     *  对计算结果进行消费
     *  thenRun       无需上一步结果，无返回值
     *  thenAccept    需要上一步结果，无返回值
     *  thenApply     需要上一步结果，有返回值
     *
     */
    public static void m3(){

        CompletableFuture
                .supplyAsync(() -> {
                    return 1;
                })
                .thenApply(f -> {
                    return f + 2;
                })
                .thenAccept(r -> System.out.println(r));


        //任务 A 执行完执行 B，并且 B 不需要 A 的结果
        System.out.println(CompletableFuture.supplyAsync(() -> 1).thenRun(() -> System.out.println("thenRun 不需要前面执行的结果")).join());

        //任务 A 执行完执行 B，B 需要 A 的结果，但是任务 B 无返回值
        System.out.println(CompletableFuture.supplyAsync(() -> 1).thenAccept(r -> System.out.println("thenAccept 需要上一步的结果 ： " + (r + 1))).join());

        //任务 A 执行完执行 B，B 需要 A 的结果，同时任务 B 有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resA").thenApply(r -> r + "resB").join());



    }



    /**
     * 对计算结果进行处理
     *
     *  supplyAsync 出现异常后面全中断
     *  handle 有异常也可以往下一步走，根据带的异常参数可以进一步处理
     *
     */
    public static void m2() {

        System.out.println(Thread.currentThread().getName() + " ...............");

        // 计算结果存在依赖关系，这两个线程串行化
        // 由于存在依赖关系(当前步错，不走下一步)，当前步骤有异常的话就叫停。
/*
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("step 1");
                    return 1;
                })
                .thenApply(f -> {

                    // 出现异常后面全中断
                    int i = 10 / 0;

                    System.out.println("step 2");
                    return f + 2;
                })
                .thenApply(f -> {
                    System.out.println("step 3");
                    return f + 3;
                })
                .whenCompleteAsync((r, e) -> {
                    if (e == null) System.out.println("result：" + r);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return -1;
                });

*/


        //当一个线程依赖另一个线程时用 handle 方法来把这两个线程串行化,
        // 异常情况：有异常也可以往下一步走，根据带的异常参数可以进一步处理
        CompletableFuture
                .supplyAsync(() -> {
                    try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println("step 1");
                    return 1;
                })
                .handle((f,e) -> {

                    // 有异常也可以往下一步走，根据带的异常参数可以进一步处理
                    int i = 10 / 0;

                    System.out.println("step 2");
                    return f + 2;
                })
                .handle((f,e) -> {

                    System.out.println("step 3");

                    if(e != null)  return 3;

                    return f + 3;
                })
                .handle((f,e) -> {
                    System.out.println("step 4");
                    return f + 4;
                })
                .whenCompleteAsync((r, e) -> {
                    if (e == null) System.out.println("result：" + r);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return -1;
                });




        // 阻塞获取结果
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + " running ...............");


    }


    /**
     * 获取结果
     * get
     * getNow
     * join
     * complete
     */
    public static void m1() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 1;
        });

        // 不见不散 一直阻塞等待结果
//        System.out.println(future.get());

        // 过时不候 在规定时间内还没有返回结果，就抛出异常，结束任务
//        System.out.println(future.get(2, TimeUnit.SECONDS));

        //立即获取结果不阻塞 没有计算完就用默认的
//        System.out.println(future.getNow(-1));


        // join 与 get 功能一样
        // join 没有 ExecutionException, InterruptedException 异常抛出，而 get 有
        System.out.println(future.join());


        //主动触发计算

        // 不暂停  --> true	-44
        // 暂停5秒线程，异步线程能够计算完成返回get   --> false	1
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //调用CompletableFuture.get()被阻塞的时候,complete方法就是结束阻塞并get()获取设置的complete里面的值.
        // complete(T value) 返回值 是否打断get方法立即返回括号值
        System.out.println(future.complete(-44) + "\t" + future.get());


    }


}
