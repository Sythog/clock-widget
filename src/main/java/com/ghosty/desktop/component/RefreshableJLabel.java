package com.ghosty.desktop.component;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.function.Supplier;

public class RefreshableJLabel extends JLabel {

    private Supplier<String> textProvider;

    public RefreshableJLabel(long delay, Supplier<String> textProvider) {
        this.textProvider = textProvider;
        new JLabelTextRefresher(this, delay, textProvider).start();
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        setSize(
                getFontMetrics(getFont()).stringWidth(textProvider == null ? "" : textProvider.get()),
                getFont().getSize() + 10
        );
    }
}
