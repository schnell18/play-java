package org.home.hone.benchmark;

import org.home.hone.pdfcut.PdfFiller;
import org.home.hone.pdfcut.PdfFiller2;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@State(Scope.Group)
public class PdfFillerBenchmark {

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Group("g1")
    @GroupThreads(10)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkFillFormPdfbox() throws IOException {
        String pdf = "/Users/justin/Documents/360_voucher.pdf";
        Map<String, String> formData = new HashMap<>();
        formData.put("billNo", "13424334344");
        formData.put("insuranceBillNo", "13424334344");
        formData.put("applicant", "taoda");
        formData.put("recognizee", "taoda");
        formData.put("effectiveYear", "2017");
        formData.put("effectiveMonth", "6");
        formData.put("effectiveDay", "1");
        return PdfFiller.fillForm(pdf, formData);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Group("g1")
    @GroupThreads(10)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkFillFormItext() throws IOException {
        String pdf = "/Users/justin/Documents/360_voucher_songti.pdf";
        Map<String, String> formData = new HashMap<>();
        formData.put("billNo", "13424334344");
        formData.put("insuranceBillNo", "13424334344");
        formData.put("applicant", "淘大年");
        formData.put("recognizee", "淘大年");
        formData.put("effectiveYear", "2017");
        formData.put("effectiveMonth", "6");
        formData.put("effectiveDay", "1");
        return PdfFiller2.fillForm(pdf, formData);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(PdfFillerBenchmark.class.getSimpleName())
            .forks(1)
            .jvmArgsAppend("-Djava.awt.headless=true")
            .jvmArgsAppend("-Dsun.java2d.cmm=sun.java2d.cmm.kcms.KcmsServiceProvider")
            .build();

        new Runner(opt).run();
    }
}
