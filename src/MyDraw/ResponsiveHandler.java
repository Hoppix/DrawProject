package MyDraw;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

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
                responsive.setHeight(responsive.getDrawFrame().getHeight() - 40);
                responsive.setWidth(responsive.getDrawFrame().getWidth());

                responsive.saveImage =  new BufferedImage(responsive.getDrawPanel().getWidth(), responsive.getDrawPanel().getHeight(), BufferedImage.TYPE_INT_RGB);

                responsive.imageG = responsive.saveImage.createGraphics();
                responsive.imageG.setColor(responsive.colorBG);
                responsive.imageG.fillRect(0, 40, responsive.getDrawPanel().getWidth(), responsive.getDrawPanel().getHeight());
            }
            else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
            {
                responsive.setHeight(responsive.getDrawFrame().getHeight() - 40);
                responsive.setWidth(responsive.getDrawFrame().getWidth());

                responsive.saveImage =  new BufferedImage(responsive.getDrawPanel().getWidth(), responsive.getDrawPanel().getHeight(), BufferedImage.TYPE_INT_RGB);

                responsive.imageG = responsive.saveImage.createGraphics();
                responsive.imageG.setColor(responsive.colorBG);
                responsive.imageG.fillRect(0, 40, responsive.getDrawPanel().getWidth(), responsive.getDrawPanel().getHeight());
            }

    }
}
