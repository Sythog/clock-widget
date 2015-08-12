package com.ghosty.desktop.clock.widget;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Main::cleanUp));
        new DateTimeWidget();
    }

    private static void cleanUp() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            System.exit(1);
        }
        System.runFinalization();
        System.exit(0);
    }
}
