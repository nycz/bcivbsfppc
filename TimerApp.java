package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Point;
import ewe.fx.Rect;
import ewe.sys.Vm;
import ewe.ui.Control;
import ewe.ui.Window;

public class TimerApp extends Control {
    BLHybrid[] buttons;
    BLHybrid[] labels;
    BLHybrid selectedButton;
    BLHybrid curBtn = null;
    boolean initiated = false;
    Color dark_gray = new Color(40,40,60);
    Color selectedColor = new Color(255,0,0);

    public TimerApp(Color textColor, Color bgColor) {
        Font large_font = new Font("Sans-serif", Font.BOLD, 42);
        Font font = new Font("Sans-serif", Font.BOLD, 28);
        buttons = new BLHybrid[14];
        labels = new BLHybrid[1];
        
        // Top buttons: HH:MM
        int x=30, y=10, w=38, h=70;
        for (int i=0; i<4; i+=1) {
            buttons[i] = new BLHybrid(x, y, w, h, "0", large_font, true);
            x += w;
            if (i == 1) { 
                labels[0] = new BLHybrid(x, y, 26, h, ":", large_font, false);
                x += 26; 
            }
        }
        buttons[0].selected = true;
        selectedButton = buttons[0];

        // Bottom buttons: 0-9
        x=10; y=100; w=44; h=50;
        for (int i=0; i<10; i+=1) {
            buttons[i+4] = new BLHybrid(x, y, w, h, ""+i, font, false);
            x += w;
            // Jump to next row
            if (i == 4) { x = 10; y += h; }
        }
    }

    public void penPressed(Point p) {
        // Make sure the whole screen is repainted the first time
        if (!initiated) {
            repaintNow();
            initiated = true;
            return;
        }

        for (BLHybrid btn : buttons) {
            if (btn.isIn(p.x, p.y)) {
                btn.pressed = true;
                refreshButton(btn);
                if (btn.selectable && !btn.selected) { 
                    btn.selected = true;
                    selectedButton.selected = false;
                    refreshButton(selectedButton);
                    selectedButton = btn;
                } else if (!btn.selectable) {
                    selectedButton.text = btn.text;
                    refreshButton(selectedButton);
                }
            }
        }
    }

    public void penClicked(Point p) {
        /* 
            Aka when the pen is released 
            whackjobs  
        */
        if (!initiated) {
            return;
        }

        for (BLHybrid btn : buttons) {
            if (btn.pressed) {
                btn.pressed = false;
                refreshButton(btn);
            }
        }
    }

    public void refreshButton(BLHybrid btn) {
        curBtn = btn;
        repaintNow(null, btn);
    }

    private void drawButton(Graphics g, BLHybrid btn) {
        if (btn.pressed) {
            g.setColor(Color.White);
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
            g.setColor(Color.Black);
        } else {
            g.setColor(Color.Black);
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
            g.setColor(Color.White);
        }
        if (btn.selected) {
            g.setColor(selectedColor);
        }
        btn.drawText(g);
    }

    public void doPaint(Graphics g, Rect r) {
        if (curBtn == null) {
            for (BLHybrid btn : buttons) {
                drawButton(g, btn);
            }
            for (BLHybrid lbl : labels) {
                g.setColor(Color.DarkGray);
                lbl.drawText(g);
            }
        } else {
            drawButton(g, curBtn);
        }
        g.flush();
        g.free();
    }
}

class BLHybrid extends Rect {
    String text;
    private Font font;
    boolean pressed = false;
    boolean selectable = false;
    boolean selected = false;

    public BLHybrid(int x, int y, int w, int h, String text, Font font, boolean selectable) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
        this.selectable = selectable;
    }

    public void drawText(Graphics g) {
        g.drawText(g.getFontMetrics(font), new String[]{text}, 
                   this, Control.CENTER, Control.CENTER);
    }
}