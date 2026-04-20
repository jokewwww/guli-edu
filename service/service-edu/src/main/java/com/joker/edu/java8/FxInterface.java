package com.joker.edu.java8;


public class FxInterface {



  public static void main(String[] args) {
    test1();
  }

  public static void test1() {
    int num = 3;
    Converter<Integer, String> s = param -> System.out.println(param + num);
    s.convet(11);
  }

  @FunctionalInterface
  public interface Converter<T1, T2> {
    void convet(int i);
  }


}
