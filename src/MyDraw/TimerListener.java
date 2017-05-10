package MyDraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
	
	GUIHandler parentHandler;
	
	public TimerListener(GUIHandler handler)
    {
    	parentHandler = handler;
    }
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		parentHandler.doCommand(parentHandler.cmdQueue.poll());
		
	}

}
