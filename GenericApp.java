package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Point;
import ewe.fx.Rect;
import ewe.sys.Vm;
import ewe.ui.Control;
import ewe.ui.Window;

import binaryclock.Paintable;
import binaryclock.GButton;
import binaryclock.GLabel;

public abstract class GenericApp extends Control {
    protected Paintable[] paintables;
    protected GButton[] buttons;
    protected GButton selectedButton;
    private GButton paintTarget = null;
    private boolean initiated = false;

    public GenericApp() {}

    @Override
    public void penPressed(Point p) {
        // Make sure the whole screen is repainted the first time
        if (!initiated) {
            repaintNow();
            initiated = true;
            return;
        }

        for (GButton btn : buttons) {
            if (btn.isIn(p.x, p.y)) {
                btn.pressed = true;
                if (btn.isSelectable() && !btn.selected) {
                    btn.selected = true;
                    selectedButton.selected = false;
                    refreshButton(selectedButton);
                    selectedButton = btn;
                } else if (btn.isSpecial()) {
                    specialButtonEvent(btn);
                } else if (btn.isRegular()) {
                    regularButtonEvent(btn);
                }
                refreshButton(btn);
                return;
            }
        }
    }

    protected abstract void specialButtonEvent(GButton btn);

    protected abstract void regularButtonEvent(GButton btn);

    @Override
    public void penClicked(Point p) {
        /*
            Aka when the pen is released
            whackjobs
        */
        if (!initiated) {
            return;
        }

        for (GButton btn : buttons) {
            if (btn.pressed) {
                btn.pressed = false;
                refreshButton(btn);
                return;
            }
        }
    }

    protected void refreshButton(GButton btn) {
        paintTarget = btn;
        repaintNow(null, btn);
    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        // This gets called after the first pen tap so that the whole
        // screen gets refreshed first
        if (paintTarget == null) {
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
