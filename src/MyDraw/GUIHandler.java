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
    private JFrame frame;
    public DrawGUIs panel;
    
    public GUIHandler()
    {
        frame = new JFrame();
        panel = new DrawGUIs(this);   
        frame.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().setLayout(null);
        frame.add(panel);        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 600));
        frame.pack();
        frame.setTitle("MyDraw - Gruppe 5");
        frame.setResizable(false);
        frame.setVisible(true);      
    }
    
    public Container getFrame()
    {
        return frame;
    }
    
    public JPanel getPanel()
    {
        return panel;
    }
    
    public void doCommand(String command)
    {
        DrawGUIs drawScreen = (DrawGUIs) panel;
        if (command.equals("clear"))
        { 
           drawScreen.clear();

        }
        else if (command.equals("quit"))
        { // quit the application
            frame.dispose(); // close the GUI
            System.exit(0); // and exit.
        }
        else if(command.equals("auto"))
        {           
            drawScreen.autoDraw();
        }
        else if(command.equals("save"))
        {
            try
            {
                //TODO Verzeichnis anpassen!
                drawScreen.writeImage(drawScreen.getDrawing(), "C:\\Users\\5hopfman\\Desktop\\test.bmp");                              
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
