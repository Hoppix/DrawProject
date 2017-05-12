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

	@Override
	public String toString()
	{
		String parseMe = String.valueOf(this.getClass());
		System.out.println(parseMe);
		String[] splitMe = parseMe.split("@");

		parseMe = splitMe[0];
		parseMe = parseMe.replaceFirst("MyDraw.", "");
		parseMe = parseMe.replaceFirst("class ", "");


		parseMe = parseMe + ": " + "start(" + upperLeft.x + "," + upperLeft.y + ")" + " end(" + lowerRight.x + "," +
				lowerRight.y + ")";

		return parseMe;
	}
}
