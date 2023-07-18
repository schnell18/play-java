package org.home;

public class Thread2 {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("Hello World")).start();
    }
}
