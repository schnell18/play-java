package org.home.hone;

public class misc {
    public static void main(String[] args) {
        String fmt1 = "{\"inc\":\"PAJK\", \"type\":\"JK_CARD\", \"action\":\"ACTIVE\",\"content\":\"%s\"}";
        String fmt2 = "{\"inc\":\"PAJK\", \"type\":\"JK_CARD\", \"action\":\"ACTIVE\",\"content\":\"\"}";
        System.out.println(String.format(fmt1, "XXXXXX"));
        System.out.println(String.format(fmt2, "XXXXXX"));
    }
}
