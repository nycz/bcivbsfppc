package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
import ewe.fx.Image;
import ewe.fx.Insets;
import ewe.sys.Time;
import ewe.sys.Vm;
import ewe.ui.CellPanel;
import ewe.ui.Control;
import ewe.ui.ImageControl;
import ewe.ui.mLabel;


public class BinaryClock extends CellPanel {
    private ImageControl[] iHour, iMinute, iSecond;
    private Image[] img;
    private mLabel[] nums;
    private Time lastTime = null;
    private Font font;
    private final Color textColor;
    private final Color bgColor;

    public BinaryClock(Color textColor, Color bgColor, Font font) {
        img = new Image[2];
        img[0] = new Image("binaryclock/off.png");
        img[1] = new Image("binaryclock/on.png");

        this.textColor = textColor;
        this.bgColor = bgColor;

        // Top margin
        addLast(new Control()).setTag(INSETS, new Insets(0,0,0,0));
        // Filler instead of the 32-hour light
        addNext(new Control());

        defaultTags.set(INSETS, new Insets(10,0,10,0));

        iHour = createRow(5, STRETCH, DONTFILL|VCENTER, "H");
        iMinute = createRow(6, STRETCH, DONTFILL|VCENTER, "M");
        iSecond = createRow(6, STRETCH, DONTFILL|VCENTER, "S");
        nums = new mLabel[6];
        for (int i=5; i>=0; i-=1){
            nums[i] = new mLabel("" + (int)Math.pow(2,i));
            nums[i].foreGround = textColor;
            nums[i].font = font;
            addNext(nums[i], STRETCH, DONTFILL|BOTTOM);
        }
        // Filler instead of H/M/S
        addLast(new Control());

        Vm.requestTimer(this, 1000);
        ticked(0,0);
    }

    private ImageControl[] createRow(int len, int arg1, int arg2, String lbl) {
        ImageControl[] imgList = new ImageControl[len];
        for (int i=len-1; i>=0; i-=1) {
            imgList[i] = new ImageControl(img[0]);
            addNext(imgList[i], arg1, arg2);
        }
        mLabel l = new mLabel(lbl);
        l.foreGround = textColor;
        l.font = font;
        addLast(l, arg1, arg2);
        return imgList;
    }

    @Override
    public void ticked(int timerId, int elapsed) {
        Time t = new Time();
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
        for (int i=0; i<imgList.length; i+=1)
            if ((changed >> i) % 2 == 1)
                imgList[i].setImage(img[(value >> i) % 2]);
    }
}
