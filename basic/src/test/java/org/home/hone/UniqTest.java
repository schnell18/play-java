package org.home.hone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class UniqTest {
   private Uniq uniq;

   @Before
   public void setUp() {
       this.uniq = new Uniq();
   }

   @After
   public void tearDown() {
       this.uniq = null;
   }

   @Test
   public void uniqTest() {
       int[] list;
       int[] exp;
       int[] act;

       // less duplicates test
       list = new int[]{1, 4, 5, 5, 6, 8, 9, 9, 9};
       exp  = new int[]{1, 4, 5, 6, 8, 9};
       act  = this.uniq.uniq(list);
       assertArrayEquals("less duplicates test", exp, act);

       // more duplicates test
       list = new int[]{1, 1, 1, 1, 2, 10};
       exp  = new int[]{1, 2, 10};
       act  = this.uniq.uniq(list);
       assertArrayEquals("more duplicates test", exp, act);

       // all duplicate test
       list = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
       exp  = new int[]{1};
       act  = this.uniq.uniq(list);
       assertArrayEquals("all duplicates test", exp, act);

       // no duplicate test
       list = new int[]{1, 4, 5, 6, 8, 19, 29, 99};
       exp  = new int[]{1, 4, 5, 6, 8, 19, 29, 99};
       act  = this.uniq.uniq(list);
       assertArrayEquals("no duplicates test", exp, act);

   }
}