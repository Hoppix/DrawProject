package MyDraw;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by khopf on 08/05/2017.
 */
public class ColorBGItemListener implements ItemListener
{
    GUIHandler parentHandler;
    Color colorBG;
    public ColorBGItemListener(GUIHandler handler)
    {
        parentHandler = handler;
    }
    @Override
    public void itemStateChanged(ItemEvent e)
    {   	
    	parentHandler.cmdQueue.add("changeBGColor" + (String)e.getItem());
    }
}
