package Drawers;

import java.awt.*;

public abstract class VisualObject
{
	public Point startPoint;
	public Point endPoint;

	public VisualObject()
	{
		startPoint = new Point();
		endPoint = new Point();
	}

	public void setStartPoint(Point p)
	{
		startPoint = p;
	}

	public void setEndPoint(Point p)
	{
		endPoint = p;
	}

	@Override
	public String toString()
	{
		String parseMe = String.valueOf(this.getClass());
		String[] splitMe = parseMe.split("@");

		parseMe = splitMe[0];
		parseMe = parseMe.replaceFirst("Drawers.", "");
		parseMe = parseMe.replaceFirst("class ", "");


		parseMe = parseMe + ": " + "start(" + startPoint.x + "," + startPoint.y + ")" + " end(" + endPoint.x + "," +
				endPoint.y + ")";

		return parseMe;
	}
}
