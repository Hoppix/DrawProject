package MyDraw;

/**
 * Gruppe 5
 *
 * Jonas Sander
 * Kolja Hopfmann
 *
 */


import java.awt.*;
import java.io.IOException;
import java.util.*;


public class Draw implements DrawAPI
{

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
        else if(colorLowercase.equals("white"))
        {
            gui.color = Color.white;
        }
        else
        {
            throw new ColorException();
        }
        gui.paintG.setBackground(gui.color);
        gui.imageG.setBackground(gui.color);
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
        else if(colorLowercase.equals("white"))
        {
            gui.colorBG = Color.white;
        }
        else
        {
            throw new ColorException();
        }
        gui.paintG.setBackground(gui.colorBG);
        gui.imageG.setBackground(gui.colorBG);
    }

    @Override
    public String getFGColor()
    {
        if(gui.color.equals(Color.black))
        {
            return "black";
        }
        else if(gui.color.equals(Color.blue))
        {
            return "blue";
        }
        else if(gui.color.equals(Color.red))
        {
            return "red";
        }
        else if(gui.color.equals(Color.green))
        {
            return "green";
        }
        else if(gui.color.equals(Color.white))
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
        if(gui.colorBG.equals(Color.black))
        {
            return "black";
        }
        else if(gui.colorBG.equals(Color.blue))
        {
            return "blue";
        }
        else if(gui.colorBG.equals(Color.red))
        {
            return "red";
        }
        else if(gui.colorBG.equals(Color.green))
        {
            return "green";
        }
        else if(gui.colorBG.equals(Color.white))
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
        MyBMPFile.write(file, img);
    }

    @Override
    public void autoDraw()
    {
        Point pA = new Point(5, 5);
        Point pB = new Point (150, 150);
        Point pC = new Point(300,300);
        ArrayList<Point> points = new ArrayList<>();
        points.add(pA);
        points.add(pB);
        points.add(pC);

        try
        {
            this.setBGColor("white");
            this.clear();

            this.setFGColor("black");
            this.drawRectangle(points.get(0), points.get(1));
            this.setFGColor("GrEEn");
            this.drawOval(points.get(1), points.get(2));
            this.setFGColor("RED");
            this.drawPolyLine(points);
            this.setFGColor("blue");
            this.drawOval(points.get(0), points.get(2));
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void clear()
    {
        gui.imageG.setColor(gui.colorBG);
        gui.imageG.fillRect(0, 0, gui.drawPanel.getSize().width, gui.drawPanel.getSize().height);
    }

    @Override
    public Image getDrawing()
    {
        return gui.saveImage;
    }

    @Override
    public void redraw()
    {

        for(int i = 0; i < app.executioner.cmdQueue.size(); i++ )
        {
            app.executioner.cmdQueue.get(i).draw(gui.paintG);
            app.executioner.cmdQueue.get(i).draw(gui.imageG);
        }
    }

    @Override
    public void undo()
    {
        app.executioner.cmdQueue.pollLast();
        app.executioner.cmdQueue.getLast().draw(gui.paintG);
        app.executioner.cmdQueue.getLast().draw(gui.imageG);
    }

    @Override
    public void redo()
    {
        app.executioner.cmdQueue.getLast().draw(gui.paintG);
        app.executioner.cmdQueue.getLast().draw(gui.imageG);
    }

    @Override
    public void drawRectangle(Point upper_left, Point lower_right)
    {
        Graphics g = gui.drawPanel.getGraphics();
        int x = upper_left.x;
        int y = upper_left.y;
        int w = Math.abs(lower_right.x - upper_left.x);
        int h = Math.abs(lower_right.y - upper_left.y);


        g.setColor(gui.color);
        g.drawRect(x, y, w, h);
        gui.imageG.setColor(gui.color);
        gui.imageG.drawRect(x, y, w, h);
    }

    @Override
    public void drawOval(Point upper_left, Point lower_right)
    {
        Graphics g = gui.drawPanel.getGraphics();
        g.setColor(gui.color);

        int x = upper_left.x;
        int y = upper_left.y;
        int w = Math.abs(lower_right.x - upper_left.x);
        int h = Math.abs(lower_right.y - upper_left.y);


        g.drawOval(x, y, w, h);
        gui.imageG.setColor(gui.color);
        gui.imageG.drawOval(x, y, w, h);
    }

    @Override
    public void drawPolyLine(java.util.List<Point> points)
    {
        Graphics g = gui.drawPanel.getGraphics();
        g.setColor(gui.color);
        gui.imageG.setColor(gui.color);
        for(int i = 0; i < points.size() -1; i++)
        {
            if(points.get(i+1).equals(null))
            {
                break;
            }
            Point pointA = points.get(i);
            Point pointB = points.get(i+1);

            int xA = pointA.x;
            int yA = pointA.y;
            int xB = pointB.x;
            int yB = pointB.y;

            g.drawLine(xA, yA, xB, yB);

        }

        for(int i = 0; i < points.size() -1; i++)
        {
            if(points.get(i+1).equals(null))
            {
                break;
            }
            Point pointA = points.get(i);
            Point pointB = points.get(i+1);

            int xA = pointA.x;
            int yA = pointA.y;
            int xB = pointB.x;
            int yB = pointB.y;

            gui.imageG.drawLine(xA, yA, xB, yB);

        }
    }
}


