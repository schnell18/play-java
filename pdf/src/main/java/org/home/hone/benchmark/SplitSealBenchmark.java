package org.home.hone.benchmark;

import org.home.hone.pdfcut.PdfCutter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Group)
public class SplitSealBenchmark {

    private static volatile int seq;

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Group("g1")
    @GroupThreads(10)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkSplitAndSeal() {
        String seal = "seal.png";
        String pdf = "sa_sheet.pdf";
        return PdfCutter.splitAndSeal(pdf, seal);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(SplitSealBenchmark.class.getSimpleName())
            .forks(1)
            .jvmArgsAppend("-Djava.awt.headless=true")
            .jvmArgsAppend("-Dsun.java2d.cmm=sun.java2d.cmm.kcms.KcmsServiceProvider")
            .build();

        new Runner(opt).run();
    }
}
