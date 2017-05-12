package MyDraw;

/**
 * Gruppe 5
 *
 * Jonas Sander
 * Kolja Hopfmann
 */


import java.awt.*;
import java.io.IOException;
import java.util.*;


public class Draw implements DrawAPI {

	protected GUIHandler app;
	protected DrawGUIs gui;

	/** main entry point.  Just create an instance of this application class */
	public static void main(String[] args)
	{
		new Draw();
	}

	public Draw()
	{
		gui = new DrawGUIs();
		app = new GUIHandler(gui);
	}


	@Override
	public void setHeight(int height)
	{
		gui.setPanelHeight(height);
	}

	@Override
	public void setWidth(int width)
	{
		gui.setPanelWidth(width);
	}

	@Override
	public void setFGColor(String fgColor) throws ColorException
	{
		String colorLowercase = fgColor.toLowerCase();
		if (colorLowercase.equals("black"))
		{
			gui.color = Color.black;
		}
		else if (colorLowercase.equals("blue"))
		{
			gui.color = Color.blue;
		}
		else if (colorLowercase.equals("red"))
		{
			gui.color = Color.red;
		}
		else if (colorLowercase.equals("green"))
		{
			gui.color = Color.green;
		}
		else if (colorLowercase.equals("white"))
		{
			gui.color = Color.white;
		}
		else
		{
			throw new ColorException();
		}
		gui.paintG.setColor(gui.color);
		gui.imageG.setColor(gui.color);
	}

	@Override
	public void setBGColor(String bgColor) throws ColorException
	{
		String colorLowercase = bgColor.toLowerCase();
		if (colorLowercase.equals("black"))
		{
			gui.colorBG = Color.black;
		}
		else if (colorLowercase.equals("blue"))
		{
			gui.colorBG = Color.blue;
		}
		else if (colorLowercase.equals("red"))
		{
			gui.colorBG = Color.red;
		}
		else if (colorLowercase.equals("green"))
		{
			gui.colorBG = Color.green;
		}
		else if (colorLowercase.equals("white"))
		{
			gui.colorBG = Color.white;
		}
		else
		{
			throw new ColorException();
		}
		gui.paintG.setColor(gui.colorBG);
		gui.imageG.setColor(gui.colorBG);
	}

	@Override
	public String getFGColor()
	{
		if (gui.color.equals(Color.black))
		{
			return "black";
		}
		else if (gui.color.equals(Color.blue))
		{
			return "blue";
		}
		else if (gui.color.equals(Color.red))
		{
			return "red";
		}
		else if (gui.color.equals(Color.green))
		{
			return "green";
		}
		else if (gui.color.equals(Color.white))
		{
			return "white";
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getBGColor()
	{
		if (gui.colorBG.equals(Color.black))
		{
			return "black";
		}
		else if (gui.colorBG.equals(Color.blue))
		{
			return "blue";
		}
		else if (gui.colorBG.equals(Color.red))
		{
			return "red";
		}
		else if (gui.colorBG.equals(Color.green))
		{
			return "green";
		}
		else if (gui.colorBG.equals(Color.white))
		{
			return "white";
		}
		else
		{
			return null;
		}
	}

	@Override
	public void writeImage(Image img, String file) throws IOException
	{
		app.writeImage(img, file);
	}

	@Override
	public void autoDraw()
	{
		app.autoDraw();
	}

	@Override
	public void clear()
	{
		app.clear();
	}

	@Override
	public Image getDrawing()
	{
		return app.getDrawing();
	}

	@Override
	public void redraw()
	{

		app.redraw();
	}

	@Override
	public void undo()
	{
		app.undo();
	}

	@Override
	public void redo()
	{
		app.redo();
	}

	@Override
	public void drawRectangle(Point upper_left, Point lower_right)
	{
		app.drawRectangle(upper_left, lower_right);
	}

	@Override
	public void drawOval(Point upper_left, Point lower_right)
	{
		app.drawOval(upper_left, lower_right);
	}

	@Override
	public void drawPolyLine(java.util.List<Point> points)
	{
		app.drawPolyLine(points);
	}
}


