package org.home.hone.algorithm.fib;

import java.util.stream.LongStream;

public class Fibonacci {

    public long fibRecursive(long n) {
        if (n < 0)
            throw new IllegalArgumentException("n can not be negative");
        if (n < 2)
            return 1;
        else return fibRecursive(n - 2) + fibRecursive(n - 1);
    }

    public long fibIterative(long n) {
        if (n < 0)
            throw new IllegalArgumentException("n can not be negative");
        if (n < 2)
            return 1;

        long a = 1, b = 1;

        for (int i = 2; i < n; i ++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        Fibonacci fib = new Fibonacci();
        LongStream.rangeClosed(0, 10).forEach(l -> {
            System.out.println(fib.fibIterative(l));
            System.out.println(fib.fibRecursive(l));
        });
    }
}
