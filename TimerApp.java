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
    private BLHybrid[] buttons;
    private BLHybrid[] labels;
    private BLHybrid selectedButton;
    private BLHybrid currentButton = null;
    private boolean initiated = false;
    private final Color labelColor = new Color(40,40,60);
    private final Color fgColor = Color.White;
    private final Color bgColor = Color.Black;
    private final Color selectedColor = new Color(255,0,0);

    public TimerApp(Color textColor, Color bgColor) {
        Font large_font = new Font("Sans-serif", Font.BOLD, 50),
             font = new Font("Sans-serif", Font.BOLD, 28);
        buttons = new BLHybrid[15];
        labels = new BLHybrid[1];

        // Top buttons: HH:MM
        int x=30, y=10, w=38, h=70;
        for (int i=0; i<4; i+=1) {
            buttons[i] = new BLHybrid(x, y, w, h, "0", large_font, BLHybrid.SELECTABLE);
            x += w;
            if (i == 1) {
                labels[0] = new BLHybrid(x, y, 26, h, ":", large_font, BLHybrid.REGULAR);
                x += 26;
            }
        }
        buttons[0].selected = true;
        selectedButton = buttons[0];

        // Bottom buttons: 0-9
        x=10; y=150; w=44; h=50;
        for (int i=0; i<10; i+=1) {
            buttons[i+4] = new BLHybrid(x, y, w, h, ""+i, font, BLHybrid.REGULAR);
            x += w;
            // Jump to next row
            if (i == 4) { x = 10; y += h; }
        }

        // Start button
        buttons[14] = new BLHybrid(20, 80, 200, 40, "Start", font, BLHybrid.SPECIAL);
    }

    @Override
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
                if (btn.isSelectable() && !btn.selected) {
                    btn.selected = true;
                    selectedButton.selected = false;
                    refreshButton(selectedButton);
                    selectedButton = btn;
                } else if (btn.isSpecial()) {
                    // TODO: THIS
                } else if (btn.isRegular()) {
                    selectedButton.text = btn.text;
                    refreshButton(selectedButton);
                }
                refreshButton(btn);
                return;
            }
        }
    }

    @Override
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
                return;
            }
        }
    }

    private void refreshButton(BLHybrid btn) {
        currentButton = btn;
        repaintNow(null, btn);
    }

    private void drawButton(Graphics g, BLHybrid btn) {
        if (btn.pressed) {
            g.setColor(fgColor);
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
            g.setColor(bgColor);
        } else {
            g.setColor(bgColor);
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
            g.setColor(fgColor);
        }
        if (btn.selected) {
            g.setColor(selectedColor);
        }
        btn.drawText(g);
    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        // This gets called after the first pen tap so that the whole
        // screen gets refreshed first
        if (currentButton == null) {
            for (BLHybrid btn : buttons) {
                drawButton(g, btn);
            }
            for (BLHybrid lbl : labels) {
                g.setColor(labelColor);
                lbl.drawText(g);
            }
        } else {
            drawButton(g, currentButton);
        }
        g.flush();
        g.free();
    }
}

class BLHybrid extends Rect {
    static final int REGULAR=0, SELECTABLE=1, SPECIAL=2;
    String text;
    boolean pressed = false;
    boolean selected = false;

    private final int mode;
    private final Font font;

    public BLHybrid(int x, int y, int w, int h, String text, Font font, int mode) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
        this.mode = mode;
    }

    boolean isRegular() {
        return mode == REGULAR;
    }

    boolean isSelectable() {
        return mode == SELECTABLE;
    }

    boolean isSpecial() {
        return mode == SPECIAL;
    }

    void drawText(Graphics g) {
        g.drawText(g.getFontMetrics(font), new String[]{text},
                   this, Control.CENTER, Control.CENTER);
    }
}
