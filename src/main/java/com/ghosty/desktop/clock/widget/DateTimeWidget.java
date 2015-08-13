package com.ghosty.desktop.clock.widget;

import com.ghosty.desktop.component.GlobalWindowKeyHook;
import com.ghosty.desktop.component.RefreshableJLabel;
import com.ghosty.desktop.util.Observer;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.awt.Color.GRAY;

public class DateTimeWidget extends JDialog implements Observer {

    private final List<JLabel> labels = new ArrayList<>();

    private Point initialClick;

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
        labels.add(timeLbl);
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
        labels.add(dateLbl);
        getContentPane().setBackground(GRAY);
        setLocation(60, (int) (screenSize.getHeight() - getSize().getHeight() - 60));
        setAlwaysOnTop(true);
        setVisible(true);
        createWindowKeyHook();
        makeWindowDraggable();
    }

    @Override
    public void update() {
        int maxLabelWidth =
                labels.stream()
                        .map(JComponent::getWidth)
                        .mapToInt(Integer::intValue)
                        .max()
                        .orElse(400);
        if (maxLabelWidth != getWidth() - 40) {
            setSize(maxLabelWidth + 40, 200);
        }
    }

    private void createWindowKeyHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalWindowKeyHook(this, window -> window.setVisible(!window.isVisible())));
    }

    private void makeWindowDraggable() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                int newX = thisX + xMoved;
                int newY = thisY + yMoved;
                setLocation(newX, newY);
            }
        });
    }
}
