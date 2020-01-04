package org.home.hone.benchmark;

import org.home.hone.crypto.BlockEncryptUtil;
import org.home.hone.crypto.HybridEncryptUtil;
import org.home.hone.crypto.RSAEncryptUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
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

@State(Scope.Thread)
public class EncryptBenchmark {

    private BlockEncryptUtil aesEncryptUtil;

    @Setup(Level.Trial)
    public void setup() {
        aesEncryptUtil = new BlockEncryptUtil("45wsakabcdf23r23", "AES/ECB/PKCS5Padding");
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public String testHybridEncryption() {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        return HybridEncryptUtil.encrypt(content);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public String testHybridEncryptionDoubleLength() {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28, \"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        return HybridEncryptUtil.encrypt(content);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public String testRSAEncryption() {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        return RSAEncryptUtil.encrypt(content);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public String testRSAEncryptionDoubleLength() {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28, \"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        return RSAEncryptUtil.encrypt(content);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public String testAESEncryption() {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        return aesEncryptUtil.encrypt(content);
    }

   public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(EncryptBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
