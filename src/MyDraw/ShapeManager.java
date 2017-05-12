package MyDraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class ShapeManager implements ItemListener
{
	//TODO drawable implementieren
	//TODO graphics g integrieren
	DrawGUIs gui;
	Graphics2D imageG;

	abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent e)
		{
			/* ignore */
		}
	}

	// if this class is active, the mouse is interpreted as a pen
	class ScribbleDrawer extends ShapeDrawer
	{
		int lastx, lasty;

		public void mousePressed(MouseEvent e)
		{
			lastx = e.getX();
			lasty = e.getY();
		}

		public void mouseDragged(MouseEvent e)
		{
            imageG = gui.imageG;

			Graphics g = gui.drawPanel.getGraphics();
			int x = e.getX(), y = e.getY();

			g.setColor(gui.color);
			g.setPaintMode();
			g.drawLine(lastx, lasty, x, y);

			imageG.setColor(gui.color);
			imageG.drawLine(lastx, lasty, x, y);

			lastx = x;
			lasty = y;

		}
	}

	// if this class is active, rectangles are drawn
	class RectangleDrawer extends ShapeDrawer
	{
		int pressx, pressy;
		int lastx = -1, lasty = -1;

		// mouse pressed => fix first corner of rectangle
		public void mousePressed(MouseEvent e)
		{
			pressx = e.getX();
			pressy = e.getY();
		}

		// mouse released => fix second corner of rectangle
		// and draw the resulting shape
		public void mouseReleased(MouseEvent e)
		{
			Graphics g = gui.drawPanel.getGraphics();
			if (lastx != -1)
			{
				// first undraw a rubber rect
				g.setXORMode(gui.color);
				g.setColor(gui.drawPanel.getBackground());
				imageG.setXORMode(gui.color);
				imageG.setColor(gui.drawPanel.getBackground());
				doDraw(pressx, pressy, lastx, lasty, g);
				lastx = -1;
				lasty = -1;
			}
			// these commands finish the rubberband mode
			g.setPaintMode();
			g.setColor(gui.color);
			imageG.setPaintMode();
			imageG.setColor(gui.color);
			// draw the finel rectangle
			doDraw(pressx, pressy, e.getX(), e.getY(), g);
		}

		// mouse released => temporarily set second corner of rectangle
		// draw the resulting shape in "rubber-band mode"
		public void mouseDragged(MouseEvent e)
		{
			Graphics g = gui.drawPanel.getGraphics();
			// these commands set the rubberband mode
			g.setXORMode(gui.color);
			g.setColor(gui.drawPanel.getBackground());

			imageG.setXORMode(gui.color);
			imageG.setColor(gui.drawPanel.getBackground());

			if (lastx != -1)
			{
				// first undraw previous rubber rect
				doDraw(pressx, pressy, lastx, lasty, g);

			}
			lastx = e.getX();
			lasty = e.getY();
			// draw new rubber rect
			doDraw(pressx, pressy, lastx, lasty, g);
		}

		public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
		{
			// calculate upperleft and width/height of rectangle
			int x = Math.min(x0, x1);
			int y = Math.min(y0, y1);
			int w = Math.abs(x1 - x0);
			int h = Math.abs(y1 - y0);
			// draw rectangle
			g.drawRect(x, y, w, h);
			imageG.setColor(gui.color);
			imageG.drawRect(x, y, w, h);

		}
	}

	// if this class is active, ovals are drawn
	class OvalDrawer extends RectangleDrawer
	{
		public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
		{
			int x = Math.min(x0, x1);
			int y = Math.min(y0, y1);
			int w = Math.abs(x1 - x0);
			int h = Math.abs(y1 - y0);
			// draw oval instead of rectangle
			g.drawOval(x, y, w, h);
			imageG.setColor(gui.color);
			imageG.drawOval(x, y, w, h);
		}
	}

	ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
	RectangleDrawer rectDrawer = new RectangleDrawer();
	OvalDrawer ovalDrawer = new OvalDrawer();
	ShapeDrawer currentDrawer;

	public ShapeManager(DrawGUIs itsGui)
	{
		gui = itsGui;
		imageG = gui.imageG;
		//TODO temp fix
		// default: scribble mode
		currentDrawer = scribbleDrawer;
		// activate scribble drawer
		gui.drawPanel.addMouseListener(currentDrawer);
		gui.drawPanel.addMouseMotionListener(currentDrawer);
	}

	// reset the shape drawer
	public void setCurrentDrawer(ShapeDrawer l)
	{
		if (currentDrawer == l)
			return;

		// deactivate previous drawer
		gui.drawPanel.removeMouseListener(currentDrawer);
		gui.drawPanel.removeMouseMotionListener(currentDrawer);
		// activate new drawer
		currentDrawer = l;
		gui.drawPanel.addMouseListener(currentDrawer);
		gui.drawPanel.addMouseMotionListener(currentDrawer);
	}

	// user selected new shape => reset the shape mode
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getItem().equals("Scribble"))
		{
			setCurrentDrawer(scribbleDrawer);
		}
		else
			if (e.getItem().equals("Rectangle"))
			{
				setCurrentDrawer(rectDrawer);
			}
			else
				if (e.getItem().equals("Oval"))
				{
					setCurrentDrawer(ovalDrawer);
				}
	}
}
