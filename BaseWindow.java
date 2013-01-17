package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Image;
import ewe.sys.Time;
import ewe.sys.Vm;
import ewe.ui.Form;
import ewe.ui.FormFrame;
import ewe.ui.mButton;
import ewe.ui.Window;

import binaryclock.common.Button;
import binaryclock.timer.TimerApp;


public class BaseWindow extends Form {
    private final Font font;
    private final Color textColor = Color.DarkGray;
    private final Color bgColor = Color.Black;

    public BaseWindow() {
        backGround = bgColor;
        resizable = false;
        titleCancel = new mButton(new Image("binaryclock/quit.png"));
        titleCancel.borderWidth = 0;
        windowFlagsToSet |= Window.FLAG_FULL_SCREEN;
        windowFlagsToClear |= Window.FLAG_HAS_TITLE;

        font = new Font("Sans-serif", Font.PLAIN, 14);

        Button clockBtn = new Button("Clock", textColor, bgColor, font);
        Button countdownBtn = new Button("Timer", textColor, bgColor, font);

        addNext(clockBtn, HSTRETCH|VSHRINK, FILL);
        addLast(countdownBtn, HSTRETCH|VSHRINK, FILL);

        TimerApp bc = new TimerApp();
        addLast(bc, STRETCH, FILL);

        Vm.requestTimer(this, 60000);
        ticked(0,0);
    }

    @Override
    public void ticked(int timerId, int elapsed) {
        Time t = new Time();

        // Set titlebar to today's date
        String date = t.format("  EEEEEEE dd MMMM yyyy");
        if (!title.equals(date))
            setTitle(date);
    }

    @Override
    public FormFrame getFormFrame(int options) {
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
