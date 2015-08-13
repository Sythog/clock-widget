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
        super.setFont(font);
        refreshSize();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        refreshSize();
    }

    private void refreshSize() {
        if (getFont() != null) {
            setSize(
                    getFontMetrics(getFont()).stringWidth(textProvider == null ? "" : textProvider.get()),
                    getFont().getSize() + 10
            );
            if (observers != null) {
                notifyObservers();
            }
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
