package binaryclock.common;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Rect;
import ewe.ui.Control;


public abstract class Paintable extends Rect {
    public String text;

    protected Color fgColor = Color.White;
    protected Color bgColor = Color.Black;

    private final Font font;

    public Paintable(int x, int y, int w, int h, String text, Font font) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
    }

    public void setForeground(Color c) {
        fgColor = c;
    }

    abstract void paint(Graphics g);

    protected void drawText(Graphics g) {
        g.drawText(g.getFontMetrics(font), new String[]{text},
                   this, Control.CENTER, Control.CENTER);
    }
}
