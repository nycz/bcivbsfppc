package binaryclock.timer;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.sys.Vm;
import ewe.ui.SingleContainer;

import binaryclock.common.GenericApp;
import binaryclock.common.Paintable;
import binaryclock.common.PButton;
import binaryclock.common.PLabel;


public class TimerApp extends SingleContainer {
    private GenericApp setTimeTab, countdownTab;
    private Font large_font = new Font("Sans-serif", Font.BOLD, 50),
                 font = new Font("Sans-serif", Font.BOLD, 28);

    public TimerApp() {
        large_font = new Font("Sans-serif", Font.BOLD, 50);
        font = new Font("Sans-serif", Font.BOLD, 28);

        setTimeTab = new SetTimeTab(this, font, large_font);
        countdownTab = new CountdownTab(this, font, large_font, 0, 0);
        setControl(setTimeTab, true);
    }

    public void startTimer(int m, int s) {
        setControl(countdownTab);
        ((CountdownTab)countdownTab).activate(m,s);
    }

    public void stopTimer() {
        setControl(setTimeTab);
        ((SetTimeTab)setTimeTab).activate();
    }
}


class SetTimeTab extends GenericApp {
    private final TimerApp parent;

    public SetTimeTab(TimerApp parent, Font font, Font large_font) {
        this(parent, new int[]{0,0,0,0}, font, large_font);
    }

    public SetTimeTab(TimerApp parent, int[] time, Font font, Font large_font) {
        this.parent = parent;

        buttons = new PButton[15];
        paintables = new Paintable[16];

        // Top buttons: HH:MM
        int x=30, y=10, w=38, h=70;
        for (int i=0; i<4; i+=1) {
            buttons[i] = new PButton(x, y, w, h, ""+time[i], large_font, PButton.SELECTABLE);
            x += w;
            if (i == 1) {
                paintables[0] = new PLabel(x, y, 26, h, ":", large_font);
                x += 26;
            }
        }
        buttons[0].selected = true;
        selectedButton = buttons[0];

        // Bottom buttons: 0-9
        x=10; y=150; w=44; h=50;
        for (int i=0; i<10; i+=1) {
            buttons[i+4] = new PButton(x, y, w, h, ""+i, font, PButton.REGULAR);
            x += w;
            // Jump to next row
            if (i == 4) { x = 10; y += h; }
        }

        // Start button
        buttons[14] = new PButton(20, 80, 200, 40, "Start", font, PButton.SPECIAL);

        // Add all buttons to the paintables list
        for (int i=1; i<16; i+=1) {
            paintables[i] = buttons[i-1];
        }
    }

    public void activate() {
        repaintAll();
    }

    @Override
    protected void specialButtonEvent(PButton btn) {
        parent.startTimer(Integer.parseInt(buttons[0].text+buttons[1].text),
                          Integer.parseInt(buttons[2].text+buttons[3].text));
    }

    @Override
    protected void regularButtonEvent(PButton btn) {
        selectedButton.text = btn.text;
        repaintTarget(selectedButton);
    }
}


class CountdownTab extends GenericApp {
    private final TimerApp parent;
    private int minutes, seconds;
    private int timer;
    private PLabel[] timeLabels;

    public CountdownTab(TimerApp parent, Font font, Font large_font, int minutes, int mins) {
        this.parent = parent;
        this.minutes = minutes;
        this.seconds = mins;
        initialized = true;

        buttons = new PButton[2];
        timeLabels = new PLabel[4];
        paintables = new Paintable[7];

        // Top labels: HH:MM
        int x=30, y=10, w=38, h=70;
        for (int i=0; i<4; i+=1) {
            paintables[i] =
            timeLabels[i] = new PLabel(x, y, w, h, "0", large_font);
            x += w;
            if (i == 1) {
                paintables[4] = new PLabel(x, y, 26, h, ":", large_font);
                x += 26;
            }
        }

        // Pause and Stop buttons
        paintables[5] =
        buttons[0] = new PButton(20, 80, 200, 40, "Pause", font, PButton.SPECIAL);
        paintables[6] =
        buttons[1] = new PButton(20, 140, 200, 40, "Stop", font, PButton.SPECIAL);
    }

    @Override
    public void ticked(int timerId, int elapsed) {
        int oldminutes = minutes, oldseconds = seconds;
        seconds -= 1;
        if (minutes == 0) {
            if (seconds == 0) {
                Color red = new Color(255,0,0);
                for (int i=0; i<5; i+=1) {
                    paintables[i].setForeground(red);
                    repaintTarget(paintables[i]);
                }
                Vm.cancelTimer(timer);
            }
        } else if (seconds == -1) {
            minutes -= 1;
            seconds = 59;
        }
        if (minutes / 10 != oldminutes / 10) {
            timeLabels[0].text = "" + minutes/10;
            repaintTarget(timeLabels[0]);
        }
        if (minutes % 10 != oldminutes % 10) {
            timeLabels[1].text = "" + minutes%10;
            repaintTarget(timeLabels[1]);
        }
        if (seconds / 10 != oldseconds / 10) {
            timeLabels[2].text = "" + seconds/10;
            repaintTarget(timeLabels[2]);
        }
        if (seconds % 10 != oldseconds % 10) {
            timeLabels[3].text = "" + seconds%10;
            repaintTarget(timeLabels[3]);
        }
    }

    public void activate(int m, int s) {
        minutes = m; seconds = s;
        timeLabels[0].text = (m/10) + "";
        timeLabels[1].text = (m%10) + "";
        timeLabels[2].text = (s/10) + "";
        timeLabels[3].text = (s%10) + "";
        timer = Vm.requestTimer(this, 1000);
        buttons[0].text = "Pause";
        repaintAll();
    }

    @Override
    protected void specialButtonEvent(PButton btn) {
        if (btn.text.equals("Stop")) {
            Vm.cancelTimer(timer);
            parent.stopTimer();
        } else if (btn.text.equals("Back")) {
            Vm.cancelTimer(timer);
            parent.stopTimer();
        } else if (btn.text.equals("Pause")) {
            btn.text = "Resume";
            Vm.cancelTimer(timer);
        } else if (btn.text.equals("Resume")) {
            btn.text = "Pause";
            timer = Vm.requestTimer(this, 1000);
        }
    }

    @Override
    protected void regularButtonEvent(PButton btn) {}
}
