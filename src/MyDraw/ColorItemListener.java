package MyDraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ColorItemListener implements ItemListener
{
    DrawGUIs parentGUI = null;
    
    public ColorItemListener(DrawGUIs gui)
    {
        parentGUI = gui;
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Color color = null;
        ColorHashMap colorMap = new ColorHashMap();
        
        color = colorMap.StringToColor((String)e.getItem());
        
        parentGUI.color = color;
    }

}
