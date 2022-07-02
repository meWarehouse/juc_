package com.at.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @create 2022-07-02
 */
public class ForkJoinDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Task task = new Task(1, 100);

        //创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(task);

        //获取合并后的结果
        Long res = forkJoinTask.get();

        System.out.println(res);


    }

    /*
        计算 1+2+3+...+100

            递归累加
            起始值与结束值相差<=10


     */

    static class Task extends RecursiveTask<Long>{

        // 拆分开始值
        private int start;
        // 拆分结束值
        private int end;
        // 结果
        private long result;

        public Task(int s,int e){
            this.start = s;
            this.end = e;
        }

        // 拆分合并逻辑
        @Override
        protected Long compute() {
            // 判断相加的两个数值相差是否大于10
            if((end - start) <= 10){
                // 合
                for (int i = start; i <= end; i++) {
                    result+=i;
                }
            }else{
                // 分

                // 获取中间值
                int mid = (start + end) / 2;

                //拆分左边
                Task leftTask = new Task(start, mid);
                //拆分右边
                Task rightTask = new Task(mid + 1, end);

                // 调用拆分方法
                leftTask.fork();
                rightTask.fork();

                //合并结果
                result  = leftTask.join() + rightTask.join();

            }

            return result;
        }
    }


}
