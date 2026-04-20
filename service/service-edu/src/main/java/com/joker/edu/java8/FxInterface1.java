package com.joker.edu.java8;

/**
 * lambda 延迟执行
 */
public class FxInterface1 {

    @FunctionalInterface
    public interface MessageBuilder{
        String buildMessage();
    }

    public static void log(int level,MessageBuilder messageBuilder){
        if(level==1){
            System.out.println(messageBuilder.buildMessage());
        }
    }

  public static void main(String[] args) {
    String msgA="hello";
    String msgB="world";
    String msgC="Pig";
    log(1,()->{
        System.out.println("lambda 是否执行了");
        return msgA+msgB+msgC;
    });
  }
}
