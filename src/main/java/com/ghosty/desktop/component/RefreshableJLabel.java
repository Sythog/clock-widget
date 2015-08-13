package com.ghosty.desktop.component;

import com.ghosty.desktop.util.Observable;
import com.ghosty.desktop.util.Observer;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RefreshableJLabel extends JLabel implements Observable {

    private final Supplier<String> textProvider;
    private final List<Observer> observers = new ArrayList<>();

    public RefreshableJLabel(long delay, Supplier<String> textProvider) {
        this.textProvider = textProvider;
        new JLabelTextRefresher(this, delay, textProvider).start();
    }

    @Override
    public void setFont(Font font) {
        int oldWidth = getWidth();
        super.setFont(font);
        refreshSize(oldWidth);
    }

    @Override
    public void setText(String text) {
        int oldWidth = getWidth();
        super.setText(text);
        refreshSize(oldWidth);
    }

    private void refreshSize(int oldWidth) {
        if (getFont() != null) {
            int newWidth = getFontMetrics(getFont()).stringWidth(textProvider == null ? "" : textProvider.get());
            if (newWidth != oldWidth) {
                setSize(newWidth, getFont().getSize() + 10);
                if (observers != null) {
                    notifyObservers();
                }
            }
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
