package me.kefir.hasher.window.component.impl;

import me.kefir.hasher.utils.Utils;
import me.kefir.hasher.window.component.Component;

import java.awt.*;

public class Button extends Component {

    private String str;
    private int x;
    private int y;
    private int w;
    private int h;

    public Button(String str, int x, int y, int w, int h) {
        this.str = str;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        if(isMouseOver()) {
            x-=2;
            y-=2;
            g.fillRect(x+4, y+4, w, h);
        }
        g.fillRect(x, y, w, h);
        g.setColor(Color.white);
        g.fillRect(x+2, y+2, w-4, h-4);

        g.setColor(Color.black);
        g.setFont(new Font("monospace", Font.BOLD, getHeight()/2));
        Utils.drawCentered(g, str, x+w/2, y+h/2);

        if(isMouseOver()) {
            x+=2;
            y+=2;
        }
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    @Override
    public int getWidth() {
        return w;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    @Override
    public int getHeight() {
        return h;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }
}
