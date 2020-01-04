package org.home.hone.perf.benchmark.micro;

import java.util.Random;
public class BadFibonacciTest {
    private int nLoops;
    private int[] input;

    public static void main(String[] args) {
        int repeat;
        if (args.length == 0) {
            repeat = 10000;
        }
        else {
            repeat = Integer.parseInt(args[0]);
        }
        new BadFibonacciTest(repeat)
            .doTest(true)
            .doTest(false);
    }

    private BadFibonacciTest(int n) {
        nLoops = n;
        input = new int[nLoops];
        Random r = new Random();
        for (int i=0; i < nLoops; i++){
            input[i] = r.nextInt(1000);
        }
    }

    private BadFibonacciTest doTest(boolean isWarmup) {
        long then = System.currentTimeMillis();
        for (int i=0; i < nLoops; i++){
            fibImpl1(input[i]);
        }

        if (!isWarmup) {
            long now = System.currentTimeMillis();
            System.out.println("Elapsed time: " + (now - then));
        }
        return this;
    }

    private double fibImpl1(int n) {
        if (n < 0) throw new IllegalArgumentException("Must be > 0");
        if (n == 0) return 0d;
        if (n == 1) return 1d;
        double d = fibImpl1(n - 2) + fibImpl1(n - 1);
        if (Double.isInfinite(d)) throw new ArithmeticException("Overflow");
        return d;
    }
}
