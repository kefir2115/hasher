package me.kefir.hasher.window.component.impl;

import me.kefir.hasher.window.component.Component;

import java.awt.*;

public class TextView extends Component {

    private String str;
    private int x;
    private int y;
    private int height;
    public TextView(String str, int x, int y, int height) {
        this.str = str;
        this.x = x;
        this.y = y;
        this.height = height;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("monospace", Font.BOLD, height));
        g.drawString(str, x, y+height);
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public int getWidth() {
        return str.length();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
