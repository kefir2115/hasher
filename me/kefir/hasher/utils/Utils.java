package me.kefir.hasher.utils;

import java.awt.*;

public class Utils {

    public static void drawCentered(Graphics g, String str, int x, int y) {
        int w = g.getFontMetrics().stringWidth(str);
        int h = g.getFontMetrics().getHeight();

        g.drawString(str, x-w/2, y+h/4);
    }
}
