package MyDraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
	
	CommandHandler parentHandler;
	
	public TimerListener(CommandHandler handler)
    {
    	parentHandler = handler;
    }
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		parentHandler.cmdQueue.poll().draw(parentHandler.imageG);
		parentHandler.cmdQueue.poll().draw(parentHandler.paintG);
	}

}
