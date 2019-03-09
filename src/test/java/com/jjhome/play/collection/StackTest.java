package com.jjhome.play.collection;

import org.junit.Test;

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