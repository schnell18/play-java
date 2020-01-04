package org.home.hone.groovy;

import groovy.lang.Script;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
}
