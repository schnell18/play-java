package org.home;

public class Thread3 extends Thread {
    @Override
    public void run() {
        System.out.println("Hello World");
    }

    public static void main(String[] args) {
        new Thread3().start();
    }
}
