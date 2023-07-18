package org.home;

public class MethodOverloadBadExample {

    public void doit() {
    }

//    public String doit() {
//        return "a";
//    }

    public double doit(int x) {
        return 1.0;
    }
}
