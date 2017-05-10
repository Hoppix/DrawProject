package MyDraw;

import java.awt.Point;

public class visualObject
{
	Point upperLeft;
	Point lowerRight;
	
	public visualObject()
	{
		upperLeft.x = 0;
		upperLeft.y = 0;
		lowerRight.x = 0;
		lowerRight.y = 0;
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
