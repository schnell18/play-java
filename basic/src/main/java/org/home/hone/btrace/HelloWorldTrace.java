package org.home.hone.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class HelloWorldTrace {
    @OnMethod(
        clazz="org.home.hone.btrace.HelloWorld",
        method="/call.*/",
        location=@Location(Kind.RETURN)
    )
    public static void onMethod(@ProbeMethodName(fqn = true) String pmn, @Duration long dur) {
        println("Hello from method " + pmn);
        println("It took " + str(dur) + "ns to execute this method");
    }
}
