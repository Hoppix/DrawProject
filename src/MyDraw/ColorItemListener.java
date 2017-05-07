package MyDraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ColorItemListener implements ItemListener
{
    DrawGUIs parentGUI = null;
    Color color = null;
    
    public ColorItemListener(DrawGUIs gui)
    {
        parentGUI = gui;
    }

    @SuppressWarnings("static-access")
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        ColorHashMap colorMap = new ColorHashMap();
        
        color = colorMap.StringToColor((String)e.getItem());
        
        parentGUI.color = color;
    }

}
