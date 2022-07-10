package com.at.locks;

/**
 * @create 2022-07-10
 */
public class SyncBytecode {

    Object object = new Object();

    public void syncCodeBlock(){
        synchronized (object){
            System.out.println("... 同步代码块 ...");
        }
    }


}
