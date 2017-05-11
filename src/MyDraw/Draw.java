package MyDraw;


// Gruppe 5


import java.awt.*;
import java.io.IOException;
import java.util.*;

/** The application class.  Processes high-level commands sent by GUI */
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
        Rectangle rekt = gui.getDrawPanel().getBounds();
        gui.getDrawPanel().setBounds(rekt.x, rekt.y, rekt.width, height);
    }

    @Override
    public void setWidth(int width)
    {
        Rectangle rekt = gui.getDrawPanel().getBounds(); //get rekt
        gui.getDrawPanel().setBounds(rekt.x, rekt.y, width, rekt.height);
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
    }

    @Override
    public void setBGColor(String bgColor) throws ColorException
    {

    }

    @Override
    public String getFGColor()
    {
        return null;
    }

    @Override
    public String getBGColor()
    {
        return null;
    }

    @Override
    public void writeImage(Image img, String file) throws IOException {

    }

    @Override
    public void autoDraw()
    {

    }

    @Override
    public void clear()
    {

    }

    @Override
    public Image getDrawing()
    {
        return null;
    }

    @Override
    public void redraw()
    {

    }

    @Override
    public void undo()
    {

    }

    @Override
    public void redo()
    {

    }

    @Override
    public void drawRectangle(Point upper_left, Point lower_right)
    {

    }

    @Override
    public void drawOval(Point upper_left, Point lower_right)
    {

    }

    @Override
    public void drawPolyLine(java.util.List<Point> points)
    {

    }
}


