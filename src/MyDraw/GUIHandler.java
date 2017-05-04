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
    //TODO gui, logik trennen
    //TODO command queue
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
        {
            frame.dispose();
            System.exit(0);
        }
        else if(command.equals("auto"))
        {           
            drawScreen.autoDraw();
        }
        else if(command.equals("save"))
        {
            try
            {
                drawScreen.writeImage(drawScreen.getDrawing(), "imageTesting\\test.bmp");                              
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
