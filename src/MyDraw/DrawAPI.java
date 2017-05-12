package MyDraw;

import java.awt.*;
import java.io.IOException;

/**
 * Created by hoppix on 08.05.17.
 */
public interface DrawAPI
{
    public void setHeight(int height);

    public void setWidth(int width);

    public void setFGColor(String fgColor) throws ColorException;

    public void setBGColor(String bgColor) throws ColorException;

    public String getFGColor();

    public String getBGColor();

    public void writeImage(Image img, String file) throws IOException;

    public void autoDraw();

    public void clear();

    public Image getDrawing();

    public void redraw();

    public void undo();

    public void redo();

    public void drawRectangle(Point upper_left, Point lower_right);

    public void drawOval(Point upper_left, Point lower_right);

    public void drawPolyLine(java.util.List<Point> points);

    public void drawFillRectangle(Point upper_left, Point lower_right);

    public void drawFillOval(Point upper_left, Point lower_right);

    public void drawTriangle(Point upper_left, Point lower_right);

    public void drawLine(Point upper_left, Point lower_right);

}
