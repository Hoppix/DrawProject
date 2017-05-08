package MyDraw;


import java.io.IOException;
import javax.swing.JFrame;



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

        gui.getDrawFrame().addWindowStateListener(new ResponsiveHandler(gui));
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
