package org.home.hone.interfaces;

public class Implementor implements InterfaceA, InterfaceB {
    @Override
    public void method1(String param1) {

    }

    /* Although this method is defined in bother InterfaceA and B */
    /* It is implemented here just once */
    @Override
    public void method2(String param1) {

    }

    @Override
    public void method2(String param1, String param2) {

    }
}
