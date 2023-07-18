/*
  A daemon thread is a thread whose execution state is not evaluated
  when the JVM decides if it should stop or not. The JVM stops when
  all user threads (in contrast to the daemon threads) are terminated.
 */
package org.home.hone.thread;

public class DaemonThread extends Thread {
    public DaemonThread() {
        // comment out the following line will make the
        // program emitting "Daemon is waking up..." forever
        // because w/o setting daemon to true, the thread is
        // a user thread and JVM will not terminates if any user
        // thread is still running
        this.setDaemon(true);
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                System.out.println("Daemon is waking up...");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DaemonThread thread = new DaemonThread();
        thread.start();
    }
}
