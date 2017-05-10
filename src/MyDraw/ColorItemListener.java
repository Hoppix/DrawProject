package MyDraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ColorItemListener implements ItemListener
{
    GUIHandler parentHandler = null;
    Color color = null;
    
    public ColorItemListener(GUIHandler handler)
    {
    	parentHandler = handler;
    }

    @SuppressWarnings("static-access")
    @Override
    public void itemStateChanged(ItemEvent e)
    {   	
        parentHandler.cmdQueue.add("changeColor" + (String)e.getItem());
    }

}
