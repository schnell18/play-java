package org.home.hone.thread;

import static java.lang.System.out;

public class Deadlock {
    static class Resource {
        private String name;
        private Resource buddy;

        public Resource(String name) {
            this.name = name;
        }

        public synchronized void acquire(long delay) {
            out.printf(
                "Thread %s acquired %s, trying to get %s ...\n",
                Thread.currentThread().getName(),
                this.name,
                this.buddy.getName()
            );
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.buddy.hold();
        }

        public synchronized void hold() {
            out.printf(
                "Thread %s hold %s\n",
                Thread.currentThread().getName(),
                this.name
            );
        }

        public void setBuddy(Resource buddy) {
            this.buddy = buddy;
        }

        public String getName() {
            return this.name;
        }
    }

    static class CompetitorThread extends Thread {
        private Resource resource;
        private long delay;

        public CompetitorThread(Resource resource, long delay) {
            this.resource = resource;
            this.delay = delay;
        }

        @Override
        public void run() {
            resource.acquire(delay);
            out.printf(
                "Thread %s completes execution!\n",
                Thread.currentThread().getName()
            );
        }
    }

    public static void main(String[] args) {

        Resource resourceA = new Resource("Resource A");
        Resource resourceB = new Resource("Resource B");
        resourceA.setBuddy(resourceB);
        resourceB.setBuddy(resourceA);

        Thread thread1 = new CompetitorThread(resourceA, 0);
        Thread thread2 = new CompetitorThread(resourceB, 1000);
        thread1.start();
        /* uncomment the following code to avoid deadlock */
//        try {
//            Thread.sleep(1000);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        thread2.start();
    }
}
