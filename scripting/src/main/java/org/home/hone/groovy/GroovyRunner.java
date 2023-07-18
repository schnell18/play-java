package org.home.hone.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.script.*;
import java.io.IOException;

public class GroovyRunner {
    public static int runWithGroovyShell(Script script) {
        try {
            script.invokeMethod("hello_world", null);
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int runWithGroovyClassLoader(Object scriptInstance) {
        try {
            scriptInstance.getClass().getDeclaredMethod("hello_world", new Class[]{}).invoke(scriptInstance, new Object[]{});
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int runWithGroovyScriptEngine(Object scriptInstance) {
        try {
            scriptInstance.getClass().getDeclaredMethod("hello_world", new Class[]{}).invoke(scriptInstance, new Object[]{});
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int runWithCompiledJsr223ScriptEngine(ScriptEngine engine, CompiledScript compiledScript) {
        try {
            Bindings bindings = engine.createBindings();
            bindings.put("adj", "wonderful");
            Object result = compiledScript.eval(bindings);
            //System.out.printf("result=%s\n", result);
            return 1;
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @State(Scope.Thread)
    public static class GroovyRunnerBenchmark {

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
            return runWithGroovyShell(this.script);
        }

        @Benchmark
        @Warmup(iterations = 5, time = 1)
        @Measurement(iterations = 50, time = 1)
        @BenchmarkMode(Mode.Throughput)
        public int benchmarkGroovyClassloader() {
            return runWithGroovyClassLoader(scriptInstance);
        }

        @Benchmark
        @Warmup(iterations = 5, time = 1)
        @Measurement(iterations = 50, time = 1)
        @BenchmarkMode(Mode.Throughput)
        public int benchmarkGroovyScriptEngine() {
            return runWithGroovyScriptEngine(scriptInstance2);
        }

        @Benchmark
        @Warmup(iterations = 5, time = 1)
        @Measurement(iterations = 50, time = 1)
        @BenchmarkMode(Mode.Throughput)
        public int benchmarkCompiledJsr223ScriptEngine() {
            return runWithCompiledJsr223ScriptEngine(engine, compiledScript);
        }

        public static void main(String[] args) throws RunnerException {
            Options opt = new OptionsBuilder()
                .include(GroovyRunnerBenchmark.class.getSimpleName())
                .forks(1)
                .build();

            new Runner(opt).run();
        }
    }
}
