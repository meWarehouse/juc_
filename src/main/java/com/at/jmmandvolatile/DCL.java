package com.at.jmmandvolatile;

import java.util.Dictionary;

/**
 * @create 2022-07-17
 */
public class DCL {

    // ====================  使用 volatile

    //通过volatile声明，实现线程安全的延迟初始化
    private static volatile DCL singleton;

    //私有化构造方法
    private DCL(){}

    //双重锁设计
    public static DCL getInstance(){

        if(singleton == null){
            //1.多线程并发创建对象时，会通过加锁保证只有一个线程能创建对象
            synchronized (DCL.class){
                if(singleton == null){
                    //隐患：多线程环境下，由于重排序，该对象可能还未完成初始化就被其他线程读取
                    //原理:利用volatile，禁止 "初始化对象"(2) 和 "设置singleton指向内存空间"(3) 的重排序

                    singleton = new DCL();

                }
            }
        }

        //2.对象创建完毕，执行getInstance()将不需要获取锁，直接返回创建对象
        return singleton;

    }

    // ====================  采用静态内部类

    public static class DCLSignalHandler{
        private static DCL instance = new DCL();

        public static DCL getInstance(){
            return DCLSignalHandler.instance;
        }

    }





}
