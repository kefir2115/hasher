package me.kefir.hasher;

import me.kefir.hasher.utils.Hasher;
import me.kefir.hasher.window.Window;

public class Main {

    private static Window window;
    private static Hasher hasher;
    public static void main(String[] args) {
        hasher = new Hasher();
        window = new Window();
        window.setVisible(true);
    }

    public static Window getWindow() {
        return window;
    }

    public static Hasher getHasher() {
        return hasher;
    }
}
