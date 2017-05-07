package MyDraw;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class GUIHandler
{
    //TODO command queue
    private JFrame frame;
    public DrawGUIs gui;
    
    public GUIHandler(DrawGUIs getgui)
    {
        gui = getgui;
        frame = gui.getDrawFrame();

        // Define action listener adapters that connect the  buttons to the app
        gui.clear.addActionListener(new DrawActionListener("clear", this));
        gui.quit.addActionListener(new DrawActionListener("quit", this));
        gui.auto.addActionListener(new DrawActionListener("auto", this));
        gui.save.addActionListener(new DrawActionListener("save", this));

        gui.shape_chooser.addItemListener(new ShapeManager(gui));
        gui.color_chooser.addItemListener(new ColorItemListener(gui));
    }
    

    
    public void doCommand(String command)
    {
        if (command.equals("clear"))
        { 
           gui.clear();

        }
        else if (command.equals("quit"))
        {
            frame.dispose();
            System.exit(0);
        }
        else if(command.equals("auto"))
        {           
            gui.autoDraw();
        }
        else if(command.equals("save"))
        {
            try
            {
                gui.writeImage(gui.getDrawing(), "imageTesting\\test.bmp");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
