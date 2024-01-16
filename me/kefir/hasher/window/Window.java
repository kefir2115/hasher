package me.kefir.hasher.window;

import me.kefir.hasher.app.ApplicationInfo;
import me.kefir.hasher.window.component.Component;
import me.kefir.hasher.window.component.impl.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {

    private CanvasManager manager;
    private Point cursor = new Point(-1, -1);

    public Window() {
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hasher v"+ApplicationInfo.VERSION);
        setResizable(false);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                manager.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
            }

            @Override public void componentMoved(ComponentEvent e) {}
            @Override public void componentShown(ComponentEvent e) {}
            @Override public void componentHidden(ComponentEvent e) {}
        });

        manager = new CanvasManager(this, getWidth(), getHeight());
        manager.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {
                move(e);
            }
            @Override public void mouseMoved(MouseEvent e) {
                move(e);
            }
        });
        manager.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                manager.typed(e.getKeyChar());
            }

            @Override public void keyPressed(KeyEvent e) {
                String s = Character.toLowerCase(e.getKeyChar())+"";
                if(!manager.keysDown.contains(s)) manager.keysDown.add(s);
            }

            @Override public void keyReleased(KeyEvent e) {
                String s = Character.toLowerCase(e.getKeyChar())+"";
                manager.keysDown.remove(s);
            }
        });
        manager.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {
                manager.click();
            }
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        getContentPane().add(manager);
    }

    public void move(MouseEvent e) {
        cursor.x = e.getX();
        cursor.y = e.getY();
        for(Component c : manager.components) {
            c.setIsMouseOver(mouseOver(c.getX(), c.getY(), c.getWidth(), c.getHeight()));
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        createBufferStrategy(2);
        manager.beginRender();
    }

    public boolean mouseOver(int x, int y, int w, int h) {
        int cx = (int) cursor.getX();
        int cy = (int) cursor.getY();
        return x<=cx&&x+w>=cx&&y<=cy&&y+h>=cy;
    }
}
