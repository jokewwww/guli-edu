package com.joker.edu.java8;

import java.util.Arrays;
import java.util.Comparator;

/** 如果一个方法的返回值类型是一个函数式接口，那么就可以直接返回一个Lambda表达式 (类始于new 内部类的实现方式 方法中可以实现构造内部类) */
public class FxInterface4 {

  private static Comparator<String> newComparator() {
    return (a, b) -> b.length() - a.length();
  }

  private static Comparator<String> oldComparator() {
    return new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o2.length() - o1.length();
      }
    };
  }

  public static void main(String[] args) {
    String[] array = {"abc", "ab", "abcd"};
    System.out.println(Arrays.toString(array));

    Arrays.sort(array, oldComparator());
    Arrays.sort(array, (a, b) -> b.length() - a.length());
    System.out.println(Arrays.toString(array));
  }
}
