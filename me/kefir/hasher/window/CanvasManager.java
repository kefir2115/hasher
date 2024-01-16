package me.kefir.hasher.window;

import me.kefir.hasher.Main;
import me.kefir.hasher.utils.HashInfo;
import me.kefir.hasher.window.component.Component;
import me.kefir.hasher.window.component.impl.Button;
import me.kefir.hasher.window.component.impl.TextField;
import me.kefir.hasher.window.component.impl.TextView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CanvasManager extends Canvas {

    public ArrayList<String> keysDown = new ArrayList<>();
    public ArrayList<Component> components = new ArrayList<>();

    public BufferedImage content;

    private int width, height;
    private boolean running = false;
    private Window w;
    public CanvasManager(Window w, int width, int height) {
        this.w = w;
        this.width = width;
        this.height = height;

        components.add(new Button("Dehash", width/2-200, 200, 370, 75));
        components.add(new TextField(width/2-200, 100, 370, 75, true));

        components.add(new Button("Hash", 20, 20, 470, 50).setVisible(false));
        components.add(new Button("Decrypt", 510, 20, 470, 50).setVisible(false));
        components.add(new Button("Choose file", 20, 90, 100, 30).setVisible(false));
        components.add(new TextView("Choose file", 140, 95, 15).setVisible(false));
    }

    private void loop() {
        new Thread(() -> {
            while(running) {
                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics g = img.getGraphics();
                paint(g);
                g.dispose();
                getGraphics().drawImage(img, 0, 0, null);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void beginRender() {
        running = true;
        loop();
    }
    public void stopRender() {
        running = false;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(70, 55, 178));
        g.fillRect(0,0,width,height);

        for(Component c : components) {
            if(c.isVisible()) c.render(g);
        }
        if(content!=null) {
            g.drawImage(content, 0, 150, null);
        }
    }

    @Override public int getWidth() {
        return width;
    }
    @Override public int getHeight() {
        return height;
    }

    @Override public void setSize(int width, int height) {
        super.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    public boolean isRunning() {
        return running;
    }

    public void typed(int code) {
        for(Component c : components) {
            if(c instanceof TextField) {
                TextField t = (TextField) c;
                if(t.isFocused()) {
                    if(code==8) { // backspace
                        if(t.text.length()>0) t.text = t.text.substring(0, t.text.length()-1);
                    } else {
                        if(code!=27) t.text+=(char)code;
                    }
                }
            }
        }
    }

    public void click() {
        Button login = (Button) getComponent(0);
        TextField loginField = (TextField) getComponent(1);

        Button hash = (Button) getComponent(2);
        Button decrypt = (Button) getComponent(3);
        Button chooseFile = (Button) getComponent(4);
        TextView strView = (TextView) getComponent(5);
        if(login.isMouseOver()) {
            if(loginField.text.equals("0crypto9")) {
                for(Component c : components) c.setVisible(true);

                login.setVisible(false);
                loginField.setVisible(false);
            } else {
                System.exit(0x0);
            }
        }
        if(hash.isMouseOver()) {

        }
        if(decrypt.isMouseOver()) {

        }
        if(chooseFile.isMouseOver()) {
            JFileChooser j = new JFileChooser();
            j.showSaveDialog(null);

            File f = j.getSelectedFile();
            strView.setStr(f.getAbsolutePath());
            String[] t = f.getName().split("\\.");
            String type = t[t.length-1].toLowerCase();
            boolean fileHashed = type.equals("hashfile");
            boolean image = type.equals("png") || type.equals("jpg") || type.equals("jpeg") || type.equals("webp");
            boolean video = type.equals("mp4");
            try {
                System.out.println(type);
                if(fileHashed) {
                    String content = "";
                    Scanner s = new Scanner(f);
                    while(s.hasNextLine()) content+=s.nextLine()+"\n";
                    s.close();

                    String realContent = Main.getHasher().dehash(content);
                    HashInfo info = new HashInfo(f, realContent);
                    FileOutputStream fo = new FileOutputStream(info.getNormalFileLocation());
                    fo.write(info.getContent().getBytes());
                    fo.close();
                    Desktop.getDesktop().open(info.getNormalFileLocation());

                    this.content = new BufferedImage(getWidth(), getHeight()-150, BufferedImage.TYPE_INT_ARGB);
                    Graphics g = this.content.getGraphics();

                    g.setColor(Color.black);
                    g.drawString("DeHashed path: "+info.getNormalFileLocation().getAbsolutePath(), 10, 40);
                } else {
                    File hashFile = new File(f.getAbsolutePath().replace(f.getName(), System.currentTimeMillis()+"-FILEHASHED.hashFile"));

                    String content = "";
                    FileInputStream fs = new FileInputStream(f);
                    content = new String(fs.readAllBytes(), StandardCharsets.UTF_8);
                    fs.close();

                    System.out.println(content + " " + f.getAbsolutePath());

                    String hashedContent = Main.getHasher().hash(f, content);
                    FileOutputStream fo = new FileOutputStream(hashFile);
                    fo.write(hashedContent.getBytes());
                    fo.close();
                    f.delete();

                    this.content = new BufferedImage(getWidth(), getHeight()-150, BufferedImage.TYPE_INT_ARGB);
                    Graphics g = this.content.getGraphics();

                    g.setColor(Color.black);
                    g.drawString("Hashed path: "+hashFile.getAbsolutePath(), 10, 40);
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
//            try {
//                BufferedImage img = ImageIO.read(f);
//                float ratio = (float) img.getWidth()/img.getHeight();
//                content = new BufferedImage(getWidth(), getHeight()-150, BufferedImage.TYPE_INT_ARGB);
//                Graphics g = content.getGraphics();
//
//                g.drawImage(img, 10, 10, 250, (int) (250.0f*ratio), null);
//                g.setColor(Color.black);
//                g.drawString("Hashed path: "+dest.getAbsolutePath(), 400, 40);
//            } catch(Exception e) {
//                throw new RuntimeException(e);
//            }
        }

        for(Component c : components) {
            if(c instanceof TextField) {
                TextField t = (TextField) c;
                if(t.isFocused() && !t.isMouseOver()) {
                    t.setFocus(false);
                } else {
                    t.setFocus(true);
                }
            }
        }
    }

    public Component getComponent(int id) {
        return components.get(id);
    }
}
