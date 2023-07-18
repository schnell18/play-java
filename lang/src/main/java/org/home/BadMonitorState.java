package org.home;

public class BadMonitorState {
    public void waiForSignal() throws InterruptedException {
        Object obj = new Object();
        synchronized (Thread.currentThread()) {
            obj.wait();
            obj.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new BadMonitorState().waiForSignal();
    }
}
