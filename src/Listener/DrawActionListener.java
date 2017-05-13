package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import MyDraw.GUIHandler;

//Here's a local class used for action listeners for the buttons
public class DrawActionListener implements ActionListener
{
    private String command;
    private GUIHandler app;

    public DrawActionListener(String cmd, GUIHandler handler)
    {
        command = cmd;
        app = handler;
    }

    public void actionPerformed(ActionEvent e)
    {
        app.doCommand(command);
    }
}
