package MyDraw;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by khopf on 08/05/2017.
 */
public class ColorBGItemListener implements ItemListener
{
    DrawGUIs parentGUI;
    Color colorBG;
    public ColorBGItemListener(DrawGUIs gui)
    {
        parentGUI = gui;
    }
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        ColorHashMap colorMap = new ColorHashMap();

        colorBG = colorMap.StringToColor((String)e.getItem());

        parentGUI.colorBG = colorBG;

        parentGUI.clear();
    }
}
