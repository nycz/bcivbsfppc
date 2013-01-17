package binaryclock.common;

import ewe.fx.Color;
import ewe.fx.Graphics;
import ewe.fx.Point;
import ewe.fx.Rect;
import ewe.ui.Control;

import binaryclock.common.Paintable;
import binaryclock.common.PButton;

public abstract class GenericApp extends Control {
    protected Paintable[] paintables;
    protected PButton[] buttons;
    protected PButton selectedButton;
    private Paintable paintTarget = null;
    protected boolean initialized = false;

    public GenericApp() {
        super();
    }

    protected abstract void specialButtonEvent(PButton btn);

    protected abstract void regularButtonEvent(PButton btn);

    protected void repaintTarget(Paintable p) {
        paintTarget = p;
        repaintNow(null, p);
    }

    protected void repaintAll() {
        paintTarget = null;
        repaintNow();
    }

    @Override
    public void penPressed(Point p) {
        // Make sure the whole screen is repainted the first time
        if (!initialized) {
            repaintAll();
            initialized = true;
            return;
        }

        for (PButton btn : buttons) {
            // Ignore buttons without text
            if (btn.text.equals("")) {
                continue;
            }
            if (btn.isIn(p.x, p.y)) {
                btn.pressed = true;
                if (btn.isSelectable() && !btn.selected) {
                    btn.selected = true;
                    selectedButton.selected = false;
                    repaintTarget(selectedButton);
                    selectedButton = btn;
                } else if (btn.isSpecial()) {
                    specialButtonEvent(btn);
                } else if (btn.isRegular()) {
                    regularButtonEvent(btn);
                }
                repaintTarget(btn);
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
        if (!initialized) {
            return;
        }

        for (PButton btn : buttons) {
            if (btn.pressed) {
                btn.pressed = false;
                repaintTarget(btn);
                return;
            }
        }
    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        // This gets called after the first pen tap so that the whole
        // screen gets refreshed first
        if (paintTarget == null) {
            g.setColor(Color.Black);
            g.fillRect(r.x, r.y, r.width, r.height);
            for (Paintable d : paintables) {
                d.paint(g);
            }
        } else {
            paintTarget.paint(g);
        }
        g.flush();
        g.free();
    }
}
