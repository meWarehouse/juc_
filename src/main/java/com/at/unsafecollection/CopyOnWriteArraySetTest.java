package com.at.unsafecollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @create 2022-07-01
 */
public class CopyOnWriteArraySetTest {


    public static void main(String[] args) {

        /*
            java.util.ConcurrentModificationException
                at java.util.HashMap$HashIterator.nextNode(HashMap.java:1437)
                at java.util.HashMap$KeyIterator.next(HashMap.java:1461)
                at java.util.AbstractCollection.toString(AbstractCollection.java:461)
                at java.lang.String.valueOf(String.java:2994)
                at java.io.PrintStream.println(PrintStream.java:821)
                at com.at.unsafecollection.CopyOnWriteArraySetTest.lambda$main$0(CopyOnWriteArraySetTest.java:29)
                at java.lang.Thread.run(Thread.java:748)
         */
        HashSet<String> hashSet = new HashSet<>();

        // ok
        Set<String> syncSet = Collections.synchronizedSet(new HashSet<String>());

        // ok
        CopyOnWriteArraySet<String> cowSet = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 1000; i++) {

            new Thread(() -> {
//                hashSet.add(UUID.randomUUID().toString());
//                syncSet.add(UUID.randomUUID().toString());
                cowSet.add(UUID.randomUUID().toString());
                System.out.println(cowSet);
            },String.valueOf(i)).start();

        }

/*

    java.util.concurrent.CopyOnWriteArraySet.add
    public boolean add(E e) {
        return al.addIfAbsent(e);
    }

    java.util.concurrent.CopyOnWriteArrayList.addIfAbsent(E)
    public boolean addIfAbsent(E e) {
        Object[] snapshot = getArray();
        return indexOf(e, snapshot, 0, snapshot.length) >= 0 ? false :
            addIfAbsent(e, snapshot);
    }

    indexOf 方法查找数组中是否已经存在当前需要添加的元素
    private static int indexOf(Object o, Object[] elements,
                               int index, int fence) {
        if (o == null) {
            for (int i = index; i < fence; i++)
                if (elements[i] == null)
                    return i;
        } else {
            for (int i = index; i < fence; i++)
                if (o.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    private boolean addIfAbsent(E e, Object[] snapshot) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] current = getArray();
            int len = current.length;
            // 快照和当期获取的数组不相等说明加锁之前数组被覆盖了，需要再次判断新的数组中是否有相同的对象
            if (snapshot != current) { // 并发操作
                // Optimize for lost race to another addXXX operation
                // 取两个数组长度最小的一个
                int common = Math.min(snapshot.length, len);
                // 0到commo比较是否有相同的对象
                for (int i = 0; i < common; i++)
                    if (current[i] != snapshot[i] && eq(e, current[i]))
                        return false;
                 //// common到len比较是否有相同的对象
                if (indexOf(e, current, common, len) >= 0)
                        return false;
            }
             //对数组进行动态扩容  copy 当前数组到 新数组中
            Object[] newElements = Arrays.copyOf(current, len + 1);
            //将新元素赋值到新的数组中
            newElements[len] = e;
            // 将索引指向新的元素
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

     并发环境多读少写，要求对象唯一



 */

    }

}
