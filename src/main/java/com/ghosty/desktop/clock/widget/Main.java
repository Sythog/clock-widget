package com.ghosty.desktop.clock.widget;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.Window;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        createWindowKeyHook(new Widget(), window -> window.setVisible(!window.isVisible()));
    }

    private static void createWindowKeyHook(Window window, Consumer<Window> action) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalWindowKeyHook(window, action));
    }
}
