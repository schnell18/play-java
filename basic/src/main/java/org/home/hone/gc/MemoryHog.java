package org.home.hone.gc;

public class MemoryHog {
    public static void main(String[] args) {
        Thread hog = new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println(new Integer(i ++));
            }
        });

        // make hog daemon so that it can hog as long as the JVM is running
        hog.setDaemon(true);
        hog.start();
        try {
            Thread.sleep(20 * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }
}
