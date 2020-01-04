package org.home.hone.jsr223;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyScriptRunner {

    public static void main(String[] args) {
        String script = "return \"hello $adj world\"";
        ScriptEngineManager manager = new ScriptEngineManager();
        try {
            ScriptEngine engine = manager.getEngineByName("groovy");
            if (engine instanceof Compilable) {
                CompiledScript compiledScript = ((Compilable) engine).compile(script);
                engine.put("adj", "wonderful");
                Object result = compiledScript.eval();
                System.out.printf("result=%s", result);
            }
            else {
                System.out.printf("Opos");
            }
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
