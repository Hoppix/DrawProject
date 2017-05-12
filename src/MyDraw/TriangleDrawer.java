package MyDraw;

import java.awt.*;

/**
 * Created by khopf on 11/05/2017.
 */
public class TriangleDrawer implements Drawable
{
    public Point startPoint;
    public Point endPoint;

    public TriangleDrawer(Point start, Point end)
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


        //fix new triangle
        int x1 = (int) startPoint.getX();
        int y1 = (int) startPoint.getY();
        int x2 = (int) endPoint.getX();
        int y2 = (int) endPoint.getY();

        int ax = x1;
        int ay = y1;

        int bx = x2;
        int by = y2;

        int cx = (x2 / 2) + x2;
        int cy = (y1 / 2) + y1;

        //untested
        g.drawLine(ax ,ay , bx, by);
        g.drawLine(bx, by, cx, cy);
        g.drawLine(ax, ay, cx, cy);


    }
}
