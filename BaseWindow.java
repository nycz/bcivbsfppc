package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Image;
import ewe.sys.Time;
import ewe.sys.Vm;
import ewe.ui.Control;
import ewe.ui.Form;
import ewe.ui.FormFrame;
import ewe.ui.mButton;
import ewe.ui.Window;

import binaryclock.BinaryClock;
import binaryclock.Button;
import binaryclock.CountdownApp;


public class BaseWindow extends Form {
    Font font;
    Color textColor = Color.DarkGray;
    Color bgColor = Color.Black;

    public BaseWindow() {
        backGround = bgColor;
        resizable = false;
        titleCancel = new mButton(new Image("binaryclock/quit.png"));
        titleCancel.borderWidth = 0;
        windowFlagsToSet |= Window.FLAG_FULL_SCREEN;
        windowFlagsToClear |= Window.FLAG_HAS_TITLE;

        font = new Font("Sans-serif", Font.ITALIC, 14);

        Button clockBtn = new Button("Clock", textColor, bgColor, font);
        Button countdownBtn = new Button("Timer", textColor, bgColor, font);

        addNext(clockBtn);
        addLast(countdownBtn);

        CountdownApp bc = new CountdownApp(textColor, bgColor, font);
        addNext(bc);
        
        Vm.requestTimer(this, 60000);
        ticked(0,0);
    }

    public void ticked(int timerId, int elapsed) {
        Time t = new Time();

        // Set titlebar to today's date
        String date = t.format("  EEEEEEE dd MMMM yyyy");
        if (!title.equals(date))
            setTitle(date);
    }

    public FormFrame getFormFrame(int options)
    {
        FormFrame ff = super.getFormFrame(options);
        ff.borderWidth = 0;
        ff.title.alignment = VCENTER;
        ff.title.font = font;
        ff.title.setBorder(BDR_NOBORDER, 6);
        ff.title.foreGround = textColor;
        ff.title.backGround = bgColor;
        return ff;
    }

    public static void main(String args[]) {
        Vm.startEwe(args);
        Form f = new BaseWindow();
        f.execute();
        Vm.exit(0);
     }
}