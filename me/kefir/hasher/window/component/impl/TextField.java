package me.kefir.hasher.window.component.impl;

import me.kefir.hasher.utils.Utils;
import me.kefir.hasher.window.component.Component;

import java.awt.*;

public class TextField extends Component {

    public String text = "";
    private boolean focus = false, crypted;

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    public TextField(int x, int y, int w, int h, boolean crypted) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.crypted = crypted;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
        g.setColor(Color.white);
        g.fillRect(x+2, y+2, width-4, height-4);

        g.setColor(Color.black);
        g.setFont(new Font("monospace", Font.BOLD, getHeight()/2));
        Utils.drawCentered(g, crypted?text.replaceAll(".", "*"):text, x+width/2, y+height/2);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
    public boolean isFocused() {
        return focus;
    }
}
