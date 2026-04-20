package com.joker.edu.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** 四大核心接口 */
public class FxInterface6 {

  public static void generate(String state, Consumer<String> consumer) {
    consumer.accept(state);
  }

  public static void test1() {
    generate("说", (x) -> System.out.println("你是猪"));
    generate("说", (x) -> System.out.println("你是骚猪"));
    generate("说", (x) -> System.out.println("你是老骚猪"));
  }

  public static List<String> supplier(int num, Supplier<String> stringSupplier) {
    List<String> strings = new ArrayList();
    for (int i = 0; i < num; i++) {
      strings.add(stringSupplier.get());
    }
    return strings;
  }

  public static void test2() {
    List<String> 你是猪 = supplier(3, () -> new String("你是猪"));
    你是猪.stream().forEach(System.out::println);
  }

  public static void main(String[] args) {
    test2();
  }
}
