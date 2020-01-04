package org.home.hone.jsr223;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class KotlinScriptRunner {

    public static void main(String[] args) {
        String script = "println 'hello'";
        ScriptEngineManager manager = new ScriptEngineManager();
        try {
            ScriptEngine engine = manager.getEngineByName("kotlin");
            if (engine instanceof Compilable) {
                CompiledScript compiledScript = ((Compilable) engine).compile(script);
                Bindings bindings = engine.createBindings();
                bindings.put("adj", "wonderful");
                Object result = compiledScript.eval(bindings);
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
