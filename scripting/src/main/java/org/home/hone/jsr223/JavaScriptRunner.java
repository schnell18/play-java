package org.home.hone.jsr223;

import javax.script.*;

public class JavaScriptRunner {

    public static void main(String[] args) {
        String script = "\"hello ${adj} world\"";
        ScriptEngineManager manager = new ScriptEngineManager();
        try {
            ScriptEngine engine = manager.getEngineByName("nashorn");
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
