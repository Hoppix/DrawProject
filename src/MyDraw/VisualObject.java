package MyDraw;

import java.awt.*;

public abstract class VisualObject
{
	public Point upperLeft;
	public Point lowerRight;

	public VisualObject()
	{
		upperLeft = new Point();
		lowerRight = new Point();
	}

	public void setupperLeft(Point p)
	{
		upperLeft = p;
	}

	public void setlowerRight(Point p)
	{
		lowerRight = p;
	}
}
