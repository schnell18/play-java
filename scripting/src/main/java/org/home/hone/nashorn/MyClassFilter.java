package org.home.hone.nashorn;

import javax.script.ScriptEngine;
//import jdk.nashorn.api.scripting.ClassFilter;
//import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public class MyClassFilter {

//    class MyCF implements ClassFilter {
//        @Override
//        public boolean exposeToScripts(String s) {
//            if (s.compareTo("java.io.File") == 0) return false;
//            return true;
//        }
//    }

    public void testClassFilter() {

        final String script =
                "print(java.lang.System.getProperty(\"java.home\"));" +
                        "print(\"Create file variable\");" +
                        "var File = Java.type(\"java.io.File\");";

//        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
//
//        ScriptEngine engine = factory.getScriptEngine(
//                new MyClassFilter.MyCF());
//        try {
//            engine.eval(script);
//        } catch (Exception e) {
//            System.out.println("Exception caught: " + e.toString());
//        }
    }

    public static void main(String[] args) {
        MyClassFilter myApp = new MyClassFilter();
        myApp.testClassFilter();
    }
}
