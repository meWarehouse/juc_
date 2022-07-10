package com.at.locks;

/**
 * @create 2022-07-10
 */
public class SyncBytecode {

/*
    Object object = new Object();

    public void syncCodeBlock(){
        synchronized (object){
            System.out.println("... 同步代码块 ...");
            throw new RuntimeException("...exception");
        }
    }
*/

    /*
  public synchronized void syncMethod();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String ... 同步方法 ...
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 20: 0
        line 21: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  this   Lcom/at/locks/SyncBytecode;

     */
//    public synchronized void syncMethod(){
//        System.out.println("... 同步方法 ...");
//    }


    /*
  public static synchronized void syncMethod();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
    Code:
      stack=2, locals=0, args_size=0
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String ... 静态同步方法 ...
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 43: 0
        line 44: 8

     */
    public static synchronized void syncMethod(){
        System.out.println("... 静态同步方法 ...");
    }



}
