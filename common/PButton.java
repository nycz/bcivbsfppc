package binaryclock.common;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;

import binaryclock.common.Paintable;


public class PButton extends Paintable {
    public static final int REGULAR=0, SELECTABLE=1, SPECIAL=2;
    public boolean pressed = false;
    public boolean selected = false;

    private final int mode;
    private Color selectedColor = new Color(255,0,0);

    public PButton(int x, int y, int w, int h, String text, Font font, int mode) {
        super(x,y,w,h,text,font);
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

    @Override
    void paint(Graphics g) {
        if (pressed) {
            g.setColor(fgColor);
            g.fillRect(x, y, width, height);
            g.setColor(bgColor);
        } else {
            g.setColor(bgColor);
            g.fillRect(x, y, width, height);
            g.setColor(fgColor);
        }
        if (selected) {
            g.setColor(selectedColor);
        }
        drawText(g);
    }
}
