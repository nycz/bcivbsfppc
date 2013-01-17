package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;

import binaryclock.Paintable;


class GLabel extends Paintable {
    public GLabel(int x, int y, int w, int h, String text, Font font) {
        super(x,y,w,h,text,font);
        fgColor = new Color(40,40,60);
    }

    @Override
    void paint(Graphics g) {
        g.setColor(fgColor);
        drawText(g);
    }
}
