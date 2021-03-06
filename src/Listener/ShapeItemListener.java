package Listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mydraw.GUIHandler;

public class ShapeItemListener implements ItemListener {

	GUIHandler parentHandler = null;
    String shape = null;
    
    public ShapeItemListener(GUIHandler handler)
    {
        parentHandler = handler;
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {              
        parentHandler.doCommand("changeShape" + (String)e.getItem());       
    }

}
