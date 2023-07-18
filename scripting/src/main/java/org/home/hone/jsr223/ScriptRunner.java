package org.home.hone.jsr223;

import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class ScriptRunner {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        for (ScriptEngineFactory sef : manager.getEngineFactories()) {
            System.out.printf(
                "name=%s, lang=%s, threading=%s, ext=%s, mine=%s\n",
                sef.getEngineName(),
                sef.getLanguageName(),
                sef.getParameter("THREADING"),
                StringUtils.join(sef.getExtensions(), "|"),
                StringUtils.join(sef.getMimeTypes(), ";")
            );
        }
    }

}
