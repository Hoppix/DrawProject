package Drawers;

import java.awt.*;

/**
 * Created by khopf on 11/05/2017.
 */
public class LineDrawer extends VisualObject implements Drawable
{

    public LineDrawer(Point start, Point end)
    {
        startPoint = start;
        endPoint = end;
    }

    @Override
    public void draw(Graphics g)
    {
        if(startPoint.x < 0 && startPoint.y < 0)
        {
            return;
        }

        int x1 = (int) startPoint.getX();
        int y1 = (int) startPoint.getY();
        int x2 = (int) endPoint.getX();
        int y2 = (int) endPoint.getY();

        g.drawLine(x1, y1, x2, y2);
    }
}
