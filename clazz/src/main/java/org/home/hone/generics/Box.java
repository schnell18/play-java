package org.home.hone.generics;

public class Box<T> {
    private T item;

    public T get() {
       return item;
    }

    public void set(T item) {
        this.item = item;
    }
}
