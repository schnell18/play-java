package org.home.hone.os;

public class AvailProcessors {
    public static void main(String[] args) {
        int procs = Runtime.getRuntime().availableProcessors();
        System.out.printf("You have %d CPUs", procs);
    }
}
