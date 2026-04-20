package com.joker.edu.java8;

public class FxInterface2 {

  @FunctionalInterface
  public interface MyFunctionInterface {
    void method();
  }

  public static void doSomething(MyFunctionInterface functionInterface) {
    functionInterface.method();
  }

  public static void main(String[] args) {
      doSomething(()->System.out.println("excuter lambda!"));
  }
}
