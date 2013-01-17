package binaryclock.common;

import ewe.fx.BufferedGraphics;
import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Point;
import ewe.fx.Rect;
import ewe.ui.mLabel;

public class Button extends mLabel {
    boolean clicked = false;
    Color textColor;
    Color bgColor;

    public Button(String text, Color fg, Color bg, Font font) {
        super(text);

        textColor = foreGround = fg;
        bgColor = backGround = bg;
        this.font = font;
        alignment = CENTER;
        // Vm.requestTick(this, 1000, false);
    }

    public void ticked(int timerId, int elapsed) {
        repaintNow();
    }

    public void penPressed(Point p) {
        clicked = true;
        backGround = textColor;
        foreGround = bgColor;
        repaintNow();
    }
    public void penClicked(Point p) {
        clicked = false;
        backGround = bgColor;
        foreGround = textColor;
        repaintNow();
    }

    public void doPaint(Graphics g, Rect r) {
        BufferedGraphics bg = new BufferedGraphics(g, r);
        Graphics draw = bg.getGraphics();

        draw.setColor(backGround);
        draw.fillRect(r.x, r.y, r.width, r.height);
        draw.setColor(foreGround);
        // draw.drawRect(r.x, r.y, r.width, r.height);
        draw.drawText(draw.getFontMetrics(font), new String[]{text}, r, CENTER, CENTER);

        draw.free();
        g.flush();
    }
}
