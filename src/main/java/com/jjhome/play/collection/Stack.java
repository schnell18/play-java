package com.jjhome.play.collection;

import java.util.Arrays;

public class Stack<E> {
    private Object[] elements;
    private int size = 0;
    private static final  int DEFAULT_INIT_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INIT_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new IllegalStateException("Empty stack");
        }
        @SuppressWarnings("unchecked") E result = (E) elements[--size];
        elements[size] = null;
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size +1);
        }
    }

}
