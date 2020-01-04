package org.home.hone.benchmark;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import org.home.hone.groovy.GroovyRunner;
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

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

@State(Scope.Thread)
public class GroovyRunnerBenchmark {

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private CompiledScript compiledScript;
    private Script script;
    private Object scriptInstance;
    private Object scriptInstance2;


    @Setup
    public void init() {
        String script1 = "return \"hello $adj world\"";
        String script2 = "def hello_world() { return 'Hello, world!'}";
        try {
            manager = new ScriptEngineManager();
            engine = manager.getEngineByName("groovy");
            if (engine instanceof Compilable) {
                compiledScript = ((Compilable) engine).compile(script1);
            }
            else {
                throw new RuntimeException("Bad script engine");
            }
        }
        catch (ScriptException e) {
            e.printStackTrace();
            throw new RuntimeException("Bad script");
        }
        this.script = new GroovyShell().parse(script2);

        try {
            // Declaring a class to conform to a java interface class would get rid of
            // a lot of the reflection here
            Class scriptClass = new GroovyClassLoader().parseClass(script2);
            scriptInstance = scriptClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


        // Declaring a class to conform to a java interface class would get rid of
        // a lot of the reflection here
        try {
            Class scriptClass = new GroovyScriptEngine(".").loadScriptByName("test.groovy");
            scriptInstance2 = scriptClass.newInstance();
        }
        catch (ResourceException e) {
            e.printStackTrace();
        }
        catch (groovy.util.ScriptException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkGroovyShell() {
        return GroovyRunner.runWithGroovyShell(this.script);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkGroovyClassloader() {
        return GroovyRunner.runWithGroovyClassLoader(scriptInstance);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkGroovyScriptEngine() {
        return GroovyRunner.runWithGroovyScriptEngine(scriptInstance2);
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 50, time = 1)
    @BenchmarkMode(Mode.Throughput)
    public int benchmarkCompiledJsr223ScriptEngine() {
        return GroovyRunner.runWithCompiledJsr223ScriptEngine(engine, compiledScript);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(GroovyRunnerBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
