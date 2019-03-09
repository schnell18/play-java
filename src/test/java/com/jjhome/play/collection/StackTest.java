package com.jjhome.play.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void push() {
        Stack<String> stack = new Stack<>();
        stack.push("abc");
        String top = stack.pop();
        assertEquals("abc", top);
    }

    @Test
    public void pushAll() {
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> integers = Arrays.asList(1, 2, 3);
        stack.pushAll(integers);
        Number top = stack.pop();
        assertEquals(3, top);
        top = stack.pop();
        assertEquals(2, top);
        top = stack.pop();
        assertEquals(1, top);
    }

    @Test
    public void popAll() {
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> integers = Arrays.asList(1, 2, 3);
        stack.pushAll(integers);
        ArrayList<Object> dst = new ArrayList<>();
        stack.popAll(dst);
        assertEquals(3, dst.get(0));
        assertEquals(2, dst.get(1));
        assertEquals(1, dst.get(2));
    }

    @Test
    public void pop() {
        Stack<String> stack = new Stack<>();
        stack.push("abc");
        String top = stack.pop();
        assertEquals("abc", top);
        try {
            stack.pop();
            fail("No exception thrown");
        }
        catch (IllegalStateException e) {
            assertEquals("Empty stack", e.getMessage());
        }
    }

    @Test
    public void isEmpty() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        stack.push("abc");
        assertFalse(stack.isEmpty());
    }
}