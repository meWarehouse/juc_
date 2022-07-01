package com.at.unsafecollection;

import java.util.*;

/**
 * @create 2022-07-01
 */
public class UnsafeList {

    public static void main(String[] args) throws Exception {

//        list();

//        vector();

        syncList();


        /*
            Vector和Synchronized的缺点：
                vector：内存消耗比较大，适合一次增量比较大的情况
                SynchronizedList：迭代器涉及的代码没有加上线程同步代码

         */





    }

    public static void syncList(){

        List<String> list = Collections.synchronizedList(new ArrayList<String>());

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

/*

java.util.Collections.SynchronizedCollection  add 与 forEach 线程安全

java.util.Collections.SynchronizedCollection 部分方法线程安全

iterator 非线程安全


    public boolean add(E e) {
        synchronized (mutex) {return c.add(e);}
    }

    public void forEach(Consumer<? super E> consumer) {
        synchronized (mutex) {c.forEach(consumer);}
    }

    public Iterator<E> iterator() {
        return c.iterator(); // Must be manually synched by user!
    }

 */

    }

    public static void vector() {

        List<String> vector = new Vector<>();

        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                vector.add(UUID.randomUUID().toString());
                System.out.println(vector);
            }, String.valueOf(i)).start();
        }

/*

    Vector 的 add 和 forEach 方法 都是线程安全的

    Vector 中的所有方法都是线程安全的

    public synchronized boolean add(E e) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = e;
        return true;
    }

    public synchronized void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int elementCount = this.elementCount;
        for (int i=0; modCount == expectedModCount && i < elementCount; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    public synchronized Iterator<E> iterator() {
        return new Itr();
    }


 */


    }

    public static void list() {
        // 开启 20 个线程，不断往 List 中写数据 并打印出 List 中的元素

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
/*

    java.util.ConcurrentModificationException 并发修改异常

Exception in thread "5" Exception in thread "0" Exception in thread "3" Exception in thread "13" Exception in thread "10" Exception in thread "7" Exception in thread "22" Exception in thread "15" Exception in thread "24" Exception in thread "25" Exception in thread "39" Exception in thread "40" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
	at java.util.ArrayList$Itr.next(ArrayList.java:851)
	at java.util.AbstractCollection.toString(AbstractCollection.java:461)
	at java.lang.String.valueOf(String.java:2994)
	at java.io.PrintStream.println(PrintStream.java:821)
	at com.at.unsafecollection.UnsafeList.lambda$list$2(UnsafeList.java:54)
	at java.lang.Thread.run(Thread.java:748)



ArrayList 的 add 与 forEach 方法 都是非线程安全的

ArrayList 中的所有方法都是非线程安全的

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

 */


    }


}
