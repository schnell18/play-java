package org.home;

public class Inherit {
    static class X {
        public X() { System.out.print(1); }
        public X(int x) { this(); System.out.print(2); }
    }
    static class Y extends X {
        public Y() { super(6); System.out.print(3); }
        public Y(int x) { this(); System.out.print(4); }
    }
    public static void main(String[] args) {
        new Y(5);
    }
}
