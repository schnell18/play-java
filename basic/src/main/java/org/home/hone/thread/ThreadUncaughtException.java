/**
 * A daemon thread is a thread whose execution state is not evaluated
 * when the JVM decides if it should stop or not. The JVM stops when
 * all user threads (in contrast to the daemon threads) are terminated.
 */
package org.home.hone.thread;

public class ThreadUncaughtException {

    static class MyThread extends Thread {
        private int counter = 0;
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1000);
                    if (counter == 5) throw new RuntimeException("Opos");
                    counter ++;
                    System.out.println("Daemon is waking up...");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(
                        "Got exception: "
                        + e.getMessage()
                        + " from thread: "
                        + t.getName()
                    );
                    e.printStackTrace();
                }
            }
        );
        MyThread thread = new MyThread();
        thread.start();
        thread.join();
    }
}
