package org.home.hone.reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;

public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<>();

    public String getClassDefinition(String clazz) throws ClassNotFoundException {
        Class cl = Class.forName(clazz);
        StringBuilder builder = new StringBuilder();
        builder
            .append(Modifier.toString(cl.getModifiers()))
            .append(" class ")
            .append(cl.getName());
        Class superCl = cl.getSuperclass();
        if (superCl != Object.class) {
            builder
                .append(" extends ")
                .append(superCl.getName());
        }
        builder
            .append(" {")
            .append("\n");
        //print fields
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            builder
                .append("    ")
                .append(Modifier.toString(field.getModifiers()))
                .append(" ");
            Class type = field.getType();
            if (type.isArray()) {
                builder
                    .append(type.getComponentType().getName())
                    .append("[]");
            }
            else {
                builder.append(type.getName());
            }
            builder
                .append(" ")
                .append(field.getName())
                .append(";")
                .append("\n");
        }
        //print constructors
        builder.append("\n");
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor constr : constructors) {
            builder
                .append("    ")
                .append(Modifier.toString(constr.getModifiers()))
                .append(" ")
                .append(constr.getName())
                .append("(");
            Class[] paramTypes = constr.getParameterTypes();
            int i = 0;
            for (i = 0; i < paramTypes.length; i ++) {
                builder
                    .append(paramTypes[i].getName())
                    .append(" arg")
                    .append(i)
                    .append(",");
            }
            if (i > 0) builder.deleteCharAt(builder.length() - 1);
            builder
                .append(")")
                .append(" {}")
                .append("\n");
        }
        //print methods
        builder.append("\n");
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            builder
                .append("    ")
                .append(Modifier.toString(method.getModifiers()))
                .append(" ")
                .append(method.getName())
                .append("(");
            Class[] paramTypes = method.getParameterTypes();
            int i = 0;
            for (i = 0; i < paramTypes.length; i ++) {
                builder
                    .append(paramTypes[i].getName())
                    .append(" arg")
                    .append(i)
                    .append(",");
            }
            if (i > 0) builder.deleteCharAt(builder.length() - 1);
            builder
                .append(")")
                .append(" {}")
                .append("\n");
        }
        builder.append("}");
        return builder.toString();
    }

    public String toString(Object obj) {
        if (obj == null) return "null";
        if (visited.contains(obj)) return "...";

        visited.add(obj);
        Class cl = obj.getClass();
        if (cl == String.class) return (String) obj;

        StringBuilder builder = new StringBuilder();
        if (cl.isArray()) {
            builder
                .append(cl.getComponentType())
                .append("[]{");
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) builder.append(",");
                Object val = Array.get(obj, i);
                if (cl.getComponentType().isPrimitive()) builder.append(val);
                else builder.append(toString(val));
            }
            builder.append("}");
            return builder.toString();
        }

        builder.append(cl.getName());
        do {
            int i = 0;
            builder.append("[");
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (i = 0; i < fields.length; i ++) {
                Field f = fields[i];
                if (!Modifier.isStatic(f.getModifiers())) {
                    builder.append(fields[i].getName());
                    builder.append("=");
                    try {
                        Class t = f.getType();
                        Object val = f.get(obj);
                        if (t.isPrimitive()) builder.append(val);
                        else builder.append(toString(val));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    builder.append(",");
                }
            }
            if (i > 0) builder.deleteCharAt(builder.length() - 1);
            builder.append("]");
            cl = cl.getSuperclass();

        }
        while(cl != null);
        return builder.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer();
        Date date1 = new Date();
        System.out.printf("%s\n", objectAnalyzer.getClassDefinition("org.home.hone.inner.TalkingClock$TimerPrinter"));
        //Date date2 = new Date();
        //date2.setTime(date2.getTime() + 1000l);
        //Date[] dates = new Date[]{ date1, date2 };
        //System.out.printf("%s\n", objectAnalyzer.toString(dates));
    }
}
