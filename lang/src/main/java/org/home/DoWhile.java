package org.home;

public class DoWhile {
    public static void main(String[] args) {
        int x = 0;
        int y = 10;
        do {
            y--;
            ++x;
        } while (x < 5);
        System.out.print(x + "," + y);
    }
}
