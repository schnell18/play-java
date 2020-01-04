package org.home.hone.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyDemo {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(
                null,
                new Class[]{Comparable.class},
                handler
            );
            elements[i] = proxy;
        }

        Integer key = new Random().nextInt(elements.length) + 1;
        int result = Arrays.binarySearch(elements, key);
        if (result >= 0) System.out.println(elements[result]);
    }

}

class TraceHandler implements InvocationHandler {
    private Object target;
    public TraceHandler(Integer value) {
        this.target = value;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StringBuilder builder = new StringBuilder();
        builder
            .append(target)
            .append(".")
            .append(method.getName())
            .append("(");
        if (args != null) {
            for (int i = 0; i < args.length; i ++) {
                builder.append(args[i]);
                if (i > 0) builder.append(",");
            }
        }
        builder.append(")");
        System.out.println(builder.toString());
        return method.invoke(target, args);
    }
}
