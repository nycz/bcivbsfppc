package binaryclock.common;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;

import binaryclock.common.Paintable;


public class PLabel extends Paintable {
    public PLabel(int x, int y, int w, int h, String text, Font font) {
        super(x,y,w,h,text,font);
    }

    @Override
    void paint(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(x, y, width, height);
        g.setColor(fgColor);
        drawText(g);
    }
}
