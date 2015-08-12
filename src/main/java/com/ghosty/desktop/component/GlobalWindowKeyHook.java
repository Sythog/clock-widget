package com.ghosty.desktop.component;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.Window;
import java.util.function.Consumer;

public class GlobalWindowKeyHook implements NativeKeyListener {

    private Window window;
    private Consumer<Window> action;

    public GlobalWindowKeyHook(Window window, Consumer<Window> action) {
        this.window = window;
        this.action = action;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if ((e.getKeyCode() == NativeKeyEvent.VC_BACKSPACE) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0)) {
            action.accept(window);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
