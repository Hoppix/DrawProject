package MyDraw;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveListener implements MouseMotionListener //extends MouseAdapter
{
    GUIHandler parentHandler;
    public MouseMoveListener(GUIHandler handler)
    {
        parentHandler = handler;
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        parentHandler.cmdQueue.add("mouseDrag;" + e.getX() + ";" + e.getY());
    }
}
