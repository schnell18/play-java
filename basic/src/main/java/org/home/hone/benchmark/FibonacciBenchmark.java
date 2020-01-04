package org.home.hone.benchmark;

import org.home.hone.algorithm.fib.Fibonacci;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
public class FibonacciBenchmark {

    private Fibonacci fib;
    private long start;
    private long end;

    @Setup
    public void setUp() {
        fib = new Fibonacci();
        start = 0;
        end = 50;
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public List<Long> benchmarkRecursive() {

        return LongStream.rangeClosed(start, end).mapToObj(fib::fibRecursive).collect(toList());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(FibonacciBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
