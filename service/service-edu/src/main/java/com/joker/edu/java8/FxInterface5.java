package com.joker.edu.java8;

import java.util.function.Supplier;

/**
 * 常用函数式接口之 Supplier（供应接口）
 */
public class FxInterface5 {

    private static String testSupplier(Supplier<String> suply){
        return suply.get();
    }

  public static void main(String[] args) {
        test2();
  }

  public static void test1(){
      System.out.println(testSupplier(()->"产生数据1"));
      System.out.println((new Supplier<String>() {
          @Override
          public String get() {
              return "产生数据2";
          }
      }));
  }

  private static int getMax(Supplier<Integer> supplier){
        return supplier.get();
  }

  public static void test2(){
        Integer[] data={1,3,5,7,9,2,4,6,8,0};

        System.out.println(getMax(()->{
            int max=0;
            for(int i = 0; i <data.length ; i++) {
                max=Math.max(max,data[i]);
            }
            return max;
        }));
  }
}
