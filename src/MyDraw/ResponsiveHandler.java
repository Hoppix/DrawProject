package MyDraw;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

/**
 * Created by khopf on 08/05/2017.
 */
public class ResponsiveHandler implements WindowStateListener, ComponentListener
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
			updatePanel();
		}
		else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
		{
			updatePanel();
		}

	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		updatePanel();
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		/**
		 * ignore
		 */
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		/**
		 * ignore
		 */
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		/**
		 * ignore
		 */
	}

	private void updatePanel()
	{
		responsive.paintComponent(responsive.paintG);
	}
}
