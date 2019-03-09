package com.jjhome.play.collection;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void push() {
        Stack stack = new Stack();
        stack.push("abc");
        String top = (String) stack.pop();
        assertEquals("abc", top);
    }

    @Test
    public void pop() {
        Stack stack = new Stack();
        stack.push("abc");
        String top = (String) stack.pop();
        assertEquals("abc", top);
        try {
            stack.pop();
            fail("No exception thrown");
        }
        catch (IllegalStateException e) {
        }
    }

    @Test
    public void isEmpty() {
        Stack stack = new Stack();
        assertTrue(stack.isEmpty());
        stack.push("abc");
        assertFalse(stack.isEmpty());
    }
}