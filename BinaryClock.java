package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Image;
import ewe.fx.Insets;
import ewe.sys.Time;
import ewe.sys.Vm;
import ewe.ui.CellPanel;
import ewe.ui.Control;
import ewe.ui.ControlEvent;
import ewe.ui.Event;
import ewe.ui.Form;
import ewe.ui.FormFrame;
import ewe.ui.Gui;
import ewe.ui.ImageControl;
import ewe.ui.mButton;
import ewe.ui.mLabel;
import ewe.ui.Window;


public class BinaryClock extends Form {
    ImageControl[] iHour, iMinute, iSecond;
    Image imgOn, imgOff;
    Image[] img;
    mLabel[] nums;
    Time lastTime = null;
    Font font;
    Color textColor = Color.DarkGray; // new Color(32,32,32);

    public BinaryClock() {
        backGround = Color.Black;

        // borderWidth = 10;

        // moveable = false;
        resizable = false;
        windowFlagsToSet |= Window.FLAG_FULL_SCREEN;//MAXIMIZE_ON_PDA;
        windowFlagsToClear |= Window.FLAG_HAS_TITLE;

        titleCancel = new mButton(new Image("binaryclock/quit.png"));
        titleCancel.modify(DrawFlat,0);
        titleCancel.borderWidth = 0;

        imgOn = new Image("binaryclock/on.png");
        imgOff = new Image("binaryclock/off.png");

        img = new Image[2];
        img[0] = imgOff;
        img[1] = imgOn;

        font = new Font("Sans-serif", Font.ITALIC, 14);

        iHour = new ImageControl[5];
        iMinute = new ImageControl[6];
        iSecond = new ImageControl[6];
        nums = new mLabel[6];
        addLast(new Control()).setTag(INSETS, new Insets(0,0,0,0));
        addNext(new Control());

        defaultTags.set(INSETS, new Insets(10,0,10,0));

        createRow(iHour, STRETCH, DONTFILL|VCENTER, "H");
        createRow(iMinute, HSTRETCH, DONTFILL|VCENTER, "M");
        createRow(iSecond, STRETCH, DONTFILL|VCENTER, "S");

        for (int i=5; i>=0; i-=1){
            nums[i] = new mLabel("" + (int)Math.pow(2,i));
            nums[i].foreGround = textColor;
            nums[i].font = font;
            addNext(nums[i], STRETCH, DONTFILL|BOTTOM);
        }
        addLast(new mLabel(""));
        // endRow();

        Vm.requestTimer(this, 1000);
        ticked(0,0);
    }

    public void createRow(ImageControl[] imgList, int arg1, int arg2, String lbl) {
        for (int i=0; i<imgList.length; i+=1) {
            imgList[imgList.length-1-i] = new ImageControl(imgOff);
            addNext(imgList[imgList.length-1-i], arg1, arg2);
        }
        mLabel l = new mLabel(lbl);
        l.foreGround = textColor;
        l.font = font;
        addLast(l, arg1, arg2);
    }

    public void ticked(int timerId, int elapsed) {
        Time t = new Time();
        String date = t.format("  EEEEEEE dd MMMM yyyy");
        if (!title.equals(date)) {
            setTitle(date);
        }


        int hourChange, minuteChange, secondChange;

        if (lastTime == null) {
            hourChange = 31;
            minuteChange = 63;
            secondChange = 63;
        } else {
            hourChange = lastTime.hour ^ t.hour;
            minuteChange = lastTime.minute ^ t.minute;
            secondChange = lastTime.second ^ t.second;
        }
        updateRow(iHour, t.hour, hourChange);
        updateRow(iMinute, t.minute, minuteChange);
        updateRow(iSecond, t.second, secondChange);

        lastTime = t;
    }

    private void updateRow(ImageControl[] imgList, int value, int changed) {
        for (int i=0; i<imgList.length; i+=1) {
            if ((changed >> i) % 2 == 1) {
                imgList[i].setImage(img[(value >> i) % 2]);
            }
        }
    }

    public FormFrame getFormFrame(int options)
    {
        FormFrame ff = super.getFormFrame(options);
        ff.borderWidth = 0;
        // ff.title.setText("");
        ff.title.alignment = VCENTER;
        ff.title.font = font;
        // ff.title.borderWidth = 5;
        ff.title.setBorder(BDR_NOBORDER, 6);
        ff.title.foreGround = textColor;
        ff.title.backGround = Color.Black;
        return ff;
    }

    public static void main(String args[]) {
        Vm.startEwe(args);
        Form f = new BinaryClock();
        f.execute();
        Vm.exit(0);
     }
}