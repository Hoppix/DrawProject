package MyDraw;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Created by khopf on 08/05/2017.
 */
public class ResponsiveHandler implements WindowStateListener
{
    DrawGUIs responsive;

    public ResponsiveHandler(DrawGUIs gui)
    {
        responsive = gui;
    }

    @Override
    public void windowStateChanged(WindowEvent e)
    {
            int oldState = e.getOldState();
            int newState = e.getNewState();

            if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0)
            {
                responsive.setHeight(responsive.getDrawFrame().getHeight() - 20);
                responsive.setWidth(responsive.getDrawFrame().getWidth());
            }
            else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
            {
                responsive.setHeight(responsive.getDrawFrame().getHeight() - 20);
                responsive.setWidth(responsive.getDrawFrame().getWidth());
            }

    }
}
