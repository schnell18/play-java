/*
  Demonstrates the basic properties of thread
 */
package org.home.hone.thread;

public class Runner {
    static class SubclassThread extends Thread {
        public void run() {
            System.out.println("In subclass thread...");
            dumpThreadProps(null);
        }
    }

    public static void main(String[] args) {

        dumpThreadProps(null);
        SubclassThread st = new SubclassThread();
        dumpThreadProps(st);
        st.start();
        dumpThreadProps(st);
        Thread runnable = new Thread(
            () -> {
                System.out.println("In runnable thread...");
                dumpThreadProps(null);

            },
            "runnable thread"
        );
        dumpThreadProps(runnable);
        runnable.start();
        //runnable.start(); //start it again should generates error

    }

    public static void dumpThreadProps(Thread subjectThread) {
        Thread currentThread = Thread.currentThread();
        if (subjectThread == null) subjectThread = currentThread;
        System.out.println(
            String.format(
                "Observe basic properties of subject thread from %s:\n" +
                    "  id           : %s\n" +
                    "  name         : %s\n" +
                    "  thread group : %s\n" +
                    "  priority     : %d\n" +
                    "  state        : %s\n",
                currentThread.getName(),
                subjectThread.getId(),
                subjectThread.getName(),
                subjectThread.getThreadGroup().getName(),
                subjectThread.getPriority(),
                subjectThread.getState().name()
            )
        );
    }
}
