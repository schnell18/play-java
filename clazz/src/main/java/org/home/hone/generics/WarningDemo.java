package org.home.hone.generics;

public class WarningDemo {

    public void main(String[] args) {
        Box<Integer> box = createBox();
    }

    private static Box<Integer> createBox() {
        return new Box<>();
    }
}
