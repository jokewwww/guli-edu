package com.joker.edu.java8;

/** 使用Lambda作为参数和返回值 */
public class FxInterface3 {

  private static void startThread(Runnable task) {
    new Thread(task).start();
  }

  public static void main(String[] args) {
    startThread(() -> System.out.println("线程任务执行"));
  }
}
