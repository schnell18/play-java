package org.home.hone.perf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bricks {
    static class Brick implements Serializable {
        private static final long serialVersionUID = -5639672589007302783L;

        private double width;
        private double length;
        private double height;
        private float weight;
        private String name;

        public Brick() {
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Brick> bricks = new ArrayList<>();
        while(true) {
            bricks.add(new Brick());
        }

//        Thread.sleep(100010001000L);
//        System.out.println("Done");
    }
}
