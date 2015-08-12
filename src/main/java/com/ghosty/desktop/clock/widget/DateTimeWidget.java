package com.ghosty.desktop.clock.widget;

import com.ghosty.desktop.component.GlobalWindowKeyHook;
import com.ghosty.desktop.component.RefreshableJLabel;
import com.ghosty.desktop.util.Observable;
import com.ghosty.desktop.util.Observer;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.util.Date;

import static java.awt.Color.GRAY;

public class DateTimeWidget extends JDialog implements Observer {

    public DateTimeWidget() throws HeadlessException {
        setUndecorated(true);
        setOpacity(0.85F);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        RefreshableJLabel timeLbl =
                new RefreshableJLabel(
                        100,
                        () -> DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date())
                );
        timeLbl.addObserver(this);
        timeLbl.setHorizontalAlignment(JLabel.CENTER);
        timeLbl.setFont(new Font(timeLbl.getFont().getName(), Font.BOLD, 100));
        timeLbl.setLocation(20, 20);
        getContentPane().add(timeLbl);
        RefreshableJLabel dateLbl =
                new RefreshableJLabel(
                        100,
                        () -> DateFormat.getDateInstance(DateFormat.LONG).format(new Date())
                );
        dateLbl.addObserver(this);
        dateLbl.setHorizontalAlignment(JLabel.CENTER);
        dateLbl.setFont(new Font(dateLbl.getFont().getName(), Font.BOLD, 50));
        dateLbl.setLocation(20, 200 - 20 - dateLbl.getHeight());
        getContentPane().add(dateLbl);
        getContentPane().setBackground(GRAY);
        setLocation(60, (int) (screenSize.getHeight() - getSize().getHeight() - 60));
        setAlwaysOnTop(true);
        setVisible(true);
        createWindowKeyHook();
    }

    @Override
    public void notify(Observable o) {
        if (!(o instanceof RefreshableJLabel)) {
            throw new IllegalArgumentException("Unexpected notification received");
        }
        RefreshableJLabel label = (RefreshableJLabel) o;
        if (label.getWidth() > getWidth() - 40)
            setSize(label.getWidth() + 40, 200);
    }

    private void createWindowKeyHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalWindowKeyHook(this, window -> window.setVisible(!window.isVisible())));
    }
}
