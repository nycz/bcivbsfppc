package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Rect;
import ewe.ui.Control;


public abstract class Paintable extends Rect {
    String text;

    private final Font font;
    protected Color fgColor = Color.White;
    protected Color bgColor = Color.Black;

    public Paintable(int x, int y, int w, int h, String text, Font font) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
    }

    abstract void paint(Graphics g);

    protected void drawText(Graphics g) {
        g.drawText(g.getFontMetrics(font), new String[]{text},
                   this, Control.CENTER, Control.CENTER);
    }
}
