package com.at.threadpool;

import java.util.concurrent.*;

/**
 * @create 2022-07-02
 */
public class ExecutorsTest {

    public static void main(String[] args) {

        // 单一线程池连接
        // 一个任务一个线程
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        // 固定线程池连接
        // 执行长期任务性能较好
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        // 缓冲线程池连接
        // 执行大量短期异步任务
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


        for (int i = 0; i < 5; i++) {

            // execute submit 最终都是执行 java.util.concurrent.ThreadPoolExecutor.execute 方法

            singleThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });

            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });

            cachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行业务");
            });


        }

        singleThreadPool.shutdown();
        fixedThreadPool.shutdown();
        cachedThreadPool.shutdown();



/*


    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }

    newSingleThreadExecutor 和 newFixedThreadPool 无限堆积 阻塞队列最大容量为 Integer.MAX_VALUE  容易出现 OOM
    newCachedThreadPool 允许创建的线程数为 Integer.MAX_VALUE，创建大量的线程 容易耗尽资源 出现OOM



    创建线程池的本质 其实都是 通过 ThreadPoolExecutor 创建


Creates a new ThreadPoolExecutor with the given initial parameters.
Params:
corePoolSize – the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
maximumPoolSize – the maximum number of threads to allow in the pool
keepAliveTime – when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
unit – the time unit for the keepAliveTime argument
workQueue – the queue to use for holding tasks before they are executed. This queue will hold only the Runnable tasks submitted by the execute method.
threadFactory – the factory to use when the executor creates a new thread
handler – the handler to use when execution is blocked because the thread bounds and queue capacities are reached
Throws:
IllegalArgumentException – if one of the following holds: corePoolSize < 0 keepAliveTime < 0 maximumPoolSize <= 0 maximumPoolSize < corePoolSize
NullPointerException – if workQueue or threadFactory or handler is null


    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }


    ThreadPoolExecutor 有七个重要参数
        1. corePoolSize：线程池中的常驻核心线程数
        2. maximumPoolSize：线程池中能够容纳同时 执行的最大线程数，此值必须大于等于1
        3. keepAliveTime：多余的空闲线程的存活时间 当前池中线程数量超过corePoolSize时，当空闲时间达到keepAliveTime时，多余线程会被销毁直到 只剩下corePoolSize个线程为止
        4. unit：keepAliveTime的单位
        5. workQueue：任务队列，被提交但尚未被执行的任务
        6. threadFactory：表示生成线程池中工作线程的线程工厂， 用于创建线程，一般默认的即可
        7. handler：拒绝策略，表示当队列满了，并且工作线程大于 等于线程池的最大线程数（maximumPoolSize）时，如何来拒绝 请求执行的runnable的策略



    线程池底层工作原理
        1. 在创建了线程池后，线程池中的线程数为零。
        2. 当调用execute()方法添加一个请求任务时，线程池会做出如下判断：
           1. 如果正在运行的线程数量小于corePoolSize，那么马上创建线程运行这个任务；
           2. 如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列；
           3. 如果这个时候队列满了且正在运行的线程数量还小于maximumPoolSize，那么还是要创建非核心线程立刻运行这个任务；
           4. 如果队列满了且正在运行的线程数量大于或等于maximumPoolSize，那么线程池会启动饱和拒绝策略来执行。
        3. 当一个线程完成任务时，它会从队列中取下一个任务来执行。
        4. 当一个线程无事可做超过一定的时间（keepAliveTime）时，线程会判断：
           如果当前运行的线程数大于corePoolSize，那么这个线程就被停掉。
           所以线程池的所有任务完成后，它最终会收缩到corePoolSize的大小。

    拒绝策略
        一般我们创建线程池时，为防止资源被耗尽，任务队列都会选择创建有界任务队列，但种模式下如果出现任务队列已满且线程池创建的线程数达到你设置的最大线程数时，这时就需要你指定ThreadPoolExecutor的RejectedExecutionHandler参数即合理的拒绝策略，来处理线程池"超载"的情况。
        ThreadPoolExecutor自带的拒绝策略如下：

        1. AbortPolicy(默认)：直接抛出RejectedExecutionException异常阻止系统正常运行
        2. CallerRunsPolicy：“调用者运行”一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量。
        3. DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加人队列中 尝试再次提交当前任务。
        4. DiscardPolicy：该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。 如果允许任务丢失，这是最好的一种策略。

        以上内置的策略均实现了RejectedExecutionHandler接口，也可以自己扩展RejectedExecutionHandler接口，定义自己的拒绝策略




 */



    }



}
