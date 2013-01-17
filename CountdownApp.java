package binaryclock;

import ewe.fx.Color;
import ewe.fx.Font;
// import ewe.fx.Image;
import ewe.fx.Insets;
import ewe.sys.Vm;
import ewe.ui.CellPanel;
import ewe.ui.Control;
import ewe.ui.mLabel;


public class CountdownApp extends CellPanel {
    mLabel[] numButtons;
    Color textColor;
    Color bgColor;
    Button[] hourButtons, minButtons;


    public CountdownApp(Color textColor, Color bgColor, Font font) {
        setBorder(BDR_NOBORDER, 10);
        font = new Font("Sans-serif", Font.BOLD, 28);
        Font largerFont = new Font("Sans-serif", Font.BOLD, 42);

        this.textColor = textColor;
        this.bgColor = bgColor;
        backGround = Color.Pink;

        numButtons = new Button[10];

        // Top clock
        CellPanel clockLayout = new CellPanel();
        CellPanel hourLayout = new CellPanel();
        CellPanel minLayout = new CellPanel();
        hourButtons = new Button[2];
        minButtons = new Button[2];
        Color timeBtnBgColor = new Color(20,20,20);
        Color timeBtnFgColor = Color.LightGray;
        foreGround = Color.White;

        for (int i=0; i<2; i+=1) {
            hourButtons[i] = new Button("0", Color.White, Color.Black, largerFont);
            hourLayout.addNext(hourButtons[i]);
        }

        for (int i=0; i<2; i+=1) {
            minButtons[i] = new Button("0", Color.White, Color.Black, largerFont);
            minLayout.addNext(minButtons[i]);
        }

        clockLayout.addNext(hourLayout).setTag(INSETS, new Insets(0,20,0,10));

        mLabel lbl = new mLabel(":");
        lbl.setFont(font);
        lbl.foreGround = new Color(0,155,0);
        clockLayout.addNext(lbl, 0, VSTRETCH|CENTER);

        clockLayout.addNext(minLayout).setTag(INSETS, new Insets(0,10,0,20));

        clockLayout.endRow();

        addLast(clockLayout);

        addLast(new Control()).setTag(INSETS, new Insets(0,0,0,0));


        Button lol = new Button("", textColor, bgColor, font);
        // addLast(lol);

        for (int i=0; i<10; i+=1) {
            numButtons[i] = new Button("" + i, Color.White, new Color(40,0,0), font);
            addNext(numButtons[i]);
            if (i == 4 || i == 9)
                endRow();
        }

        Button startButton = new Button("S t a r t", Color.White,
                                        new Color(40,0,0), font);
        addLast(startButton).setTag(INSETS, new Insets(0,0,0,0));
    }
}
