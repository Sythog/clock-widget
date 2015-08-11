package com.ghosty.desktop.component;

import javax.swing.JLabel;
import java.util.function.Supplier;

public class JLabelTextRefresher extends Thread {

    private JLabel label;
    private long delay;
    private Supplier<String> textProvider;

    public JLabelTextRefresher(JLabel label, long delay, Supplier<String> textProvider) {
        this.label = label;
        this.delay = delay;
        this.textProvider = textProvider;
        setName(textProvider.get() + "-refresher");
    }

    @Override
    public void run() {
        while (true) {
            label.setText(textProvider.get());
            setName(textProvider.get() + "-refresher");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
