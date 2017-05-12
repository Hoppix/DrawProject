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

	public void setUpperLeft(Point p)
	{
		upperLeft = p;
	}

	public void setLowerRight(Point p)
	{
		lowerRight = p;
	}

	public String toSaveString()
	{
		String parseMe = this.toString();
		String[] splitMe = parseMe.split("@");

		parseMe = splitMe[0];
		parseMe = parseMe.replaceFirst("MyDraw.", "");

		parseMe = parseMe + ": " + "start(" + upperLeft.x + "," + upperLeft.y + ")" + " end(" + lowerRight.x + "," +
				lowerRight.y + ")";
		System.out.println(parseMe);


		return parseMe;
	}
}
