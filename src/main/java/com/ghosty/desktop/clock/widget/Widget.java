package com.ghosty.desktop.clock.widget;

import com.ghosty.desktop.component.RefreshableJLabel;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.util.Date;

import static java.awt.Color.GRAY;

public class Widget extends JDialog {

    public Widget() throws HeadlessException {
        setUndecorated(true);
        setOpacity(0.85F);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JLabel timeLbl =
                new RefreshableJLabel(
                        200,
                        () -> DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date())
                );
        timeLbl.setHorizontalAlignment(JLabel.CENTER);
        timeLbl.setFont(new Font(timeLbl.getFont().getName(), Font.BOLD, 100));
        timeLbl.setLocation(20, 20);
        getContentPane().add(timeLbl);
        JLabel dateLbl =
                new RefreshableJLabel(
                        500,
                        () -> DateFormat.getDateInstance(DateFormat.LONG).format(new Date())
                );
        dateLbl.setHorizontalAlignment(JLabel.CENTER);
        dateLbl.setFont(new Font(dateLbl.getFont().getName(), Font.BOLD, 50));
        dateLbl.setLocation(20, 200 - 20 - dateLbl.getHeight());
        getContentPane().add(dateLbl);
        getContentPane().setBackground(GRAY);
        setSize(Math.max(timeLbl.getWidth(), dateLbl.getWidth()) + 40, 200);
        setLocation(60, (int) (screenSize.getHeight() - getSize().getHeight() - 60));
        setAlwaysOnTop(true);
    }
}
