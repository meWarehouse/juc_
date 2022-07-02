package com.at.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-02
 */
public class BlockingQueueDemo {

    /*
抛出异常	特殊值	阻塞	超时
插入	add(e)	offer(e)	put(e)	offer(e, time, unit)
移除	remove()	poll()	take()	poll(time, unit)
检查	element()	peek()	不可用	不可用


抛出异常
add正常执行返回true，element（不删除）和remove返回阻塞队列中的第一个元素
当阻塞队列满时，再往队列里add插入元素会抛IllegalStateException:Queue full
当阻塞队列空时，再往队列里remove移除元素会抛NoSuchElementException
当阻塞队列空时，再调用element检查元素会抛出NoSuchElementException
特定值
插入方法，成功ture失败false
移除方法，成功返回出队列的元素，队列里没有就返回null
检查方法，成功返回队列中的元素，没有返回null
一直阻塞
	如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行。
当阻塞队列满时，再往队列里put元素，队列会一直阻塞生产者线程直到put数据或响应中断退出
当阻塞队列空时，再从队列里take元素，队列会一直阻塞消费者线程直到队列可用
超时退出
	如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行，但等待时间不会超过给定值。		         返回一个特定值以告知该操作是否成功(典型的是 true / false)。


     */

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        // 第一组方法：add remove element
//        System.out.println(queue.add("a"));
//        System.out.println(queue.add("b"));
//        System.out.println(queue.add("c"));
//        // System.out.println(queue.add("d"));
//        // System.out.println(queue.element());
//        System.out.println(queue.remove());
//        System.out.println(queue.remove());
//        System.out.println(queue.remove());
//        //System.out.println(queue.remove());
//        //System.out.println(queue.element());
        // 第二组：offer poll peek
//        System.out.println(queue.offer("a"));
//        System.out.println(queue.offer("b"));
//        System.out.println(queue.offer("c"));
//        System.out.println(queue.offer("d"));
//        System.out.println(queue.peek());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.peek());
        // 第三组：put take
//        queue.put("a");
//        queue.put("b");
//        queue.put("c");
//        System.out.println(queue.take());
//        queue.put("d");
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
        // 第四组：offer poll
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("d", 5, TimeUnit.SECONDS));
    }

}
