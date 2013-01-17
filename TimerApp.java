package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Graphics;
import ewe.fx.Point;
import ewe.fx.Rect;
import ewe.sys.Vm;
import ewe.ui.Control;
import ewe.ui.Window;

import binaryclock.GenericApp;
import binaryclock.Paintable;
import binaryclock.GButton;
import binaryclock.GLabel;


public class TimerApp extends GenericApp {

    public TimerApp(Color textColor, Color bgColor) {
        Font large_font = new Font("Sans-serif", Font.BOLD, 50),
             font = new Font("Sans-serif", Font.BOLD, 28);
        buttons = new GButton[15];
        paintables = new Paintable[16];

        // Top buttons: HH:MM
        int x=30, y=10, w=38, h=70;
        for (int i=0; i<4; i+=1) {
            buttons[i] = new GButton(x, y, w, h, "0", large_font, GButton.SELECTABLE);
            x += w;
            if (i == 1) {
                paintables[0] = new GLabel(x, y, 26, h, ":", large_font);
                x += 26;
            }
        }
        buttons[0].selected = true;
        selectedButton = buttons[0];

        // Bottom buttons: 0-9
        x=10; y=150; w=44; h=50;
        for (int i=0; i<10; i+=1) {
            buttons[i+4] = new GButton(x, y, w, h, ""+i, font, GButton.REGULAR);
            x += w;
            // Jump to next row
            if (i == 4) { x = 10; y += h; }
        }

        // Start button
        buttons[14] = new GButton(20, 80, 200, 40, "Start", font, GButton.SPECIAL);

        // Add all buttons to the paintables list
        for (int i=1; i<16; i+=1) {
            paintables[i] = buttons[i-1];
        }
    }

    @Override
    protected void specialButtonEvent(GButton btn) {
        // TODO: THIS
    }

    @Override
    protected void regularButtonEvent(GButton btn) {
        selectedButton.text = btn.text;
        refreshButton(selectedButton);
    }
}
