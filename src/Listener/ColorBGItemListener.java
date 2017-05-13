package Listener;

import MyDraw.GUIHandler;

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
    	parentHandler.doCommand("changeBGColor" + (String)e.getItem());
    }
}
