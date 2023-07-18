package org.home.hone.inner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyTimer {
    private boolean beep;

    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(2000, true);
        clock.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        // ActionListener listener = new TimerPrinter();
        // as lambda expression
        ActionListener listener = (event) -> {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);
            Toolkit.getDefaultToolkit().beep();
        };

        Timer t = new Timer(this.interval, listener);
        t.start();
    }

    public class TimerPrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);
            if (beep && interval > 1000) Toolkit.getDefaultToolkit().beep();
        }
    }
}
