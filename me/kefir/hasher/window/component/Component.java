package me.kefir.hasher.window.component;

import java.awt.*;

public abstract class Component {
    private boolean mouseOver = false, visible = true;
    public abstract void render(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getX();
    public abstract int getY();

    public Component setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
    public void setIsMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isMouseOver() {
        return mouseOver && isVisible();
    }
}
