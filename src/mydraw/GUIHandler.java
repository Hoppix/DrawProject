package mydraw;


import Drawers.*;
import FileFilters.BMPFileFilter;
import FileFilters.TextFileFilter;
import Listener.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class GUIHandler
{
	private JFileChooser chooser;
	private JFrame frame;

	public DrawGUIs gui;
	public Graphics imageG;
	public Graphics paintG;
	public Queue<String> cmdQueue;
	public Stack<String> doneActions;
	public CommandHandler executioner;


	public GUIHandler(DrawGUIs getgui)
	{
		gui = getgui;

		chooser = new JFileChooser();
		cmdQueue = new LinkedList<String>();
		doneActions = new Stack<String>();

		imageG = gui.imageG;
		paintG = gui.paintG;
		frame = gui.drawFrame;

		executioner = new CommandHandler(this);

		chooser.addChoosableFileFilter(new BMPFileFilter());
		chooser.addChoosableFileFilter(new TextFileFilter());

		// Define action listener adapters that connect the  buttons to the app
		gui.clear.addActionListener(new DrawActionListener("clear", this));
		gui.quit.addActionListener(new DrawActionListener("quit", this));
		gui.auto.addActionListener(new DrawActionListener("auto", this));
		gui.save.addActionListener(new DrawActionListener("save", this));
		gui.saveText.addActionListener(new DrawActionListener("saveText", this));
		gui.loadText.addActionListener(new DrawActionListener("loadText", this));
		gui.redo.addActionListener(new DrawActionListener("undo", this));
		gui.undo.addActionListener(new DrawActionListener("redo", this));
		gui.redraw.addActionListener(new DrawActionListener("redraw", this));


		gui.shape_chooser.addItemListener(new ShapeItemListener(this));
		gui.color_chooser.addItemListener(new ColorItemListener(this));
		gui.colorBG_chooser.addItemListener(new ColorBGItemListener(this));

		gui.drawFrame.addWindowStateListener(new ResponsiveHandler(gui));
		gui.drawFrame.addComponentListener(new ResponsiveHandler(gui));

	}


	public void doCommand(String command)
	{
		if (command == null)
		{
			return;
		}
		if (command.equals("clear"))
		{
			clear();
		}
		else if (command.equals("quit"))
		{
			frame.dispose();
			System.exit(0);
		}
		else if(command.equals("undo"))
		{
			undo();
		}
		else if(command.equals("redo"))
		{
			redo();
		}
		else if(command.equals("redraw"))
		{
			redraw();
		}
		else if (command.equals("auto"))
		{
			doneActions.add(command);
			autoDraw();
		}
		else if (command.equals("saveText"))
		{
			chooserSaveText();
		}
		else if (command.equals("loadText"))
		{
			chooserLoadText();
		}
		else if (command.equals("save"))
		{
			chooserSaveImage();
		}
		else if (command.contains("changeShape"))
		{
			command = command.replace("changeShape", "");
			executioner.shape = command;

		}
		else if (command.contains("changeColor"))
		{
			System.out.println("changingColor: " + command);
			command = command.replace("changeColor", "");
			ColorHashMap map = new ColorHashMap();
			gui.color = ColorHashMap.StringToColor(command);
			gui.imageG.setColor(gui.color);
			gui.imageG.setColor(gui.color);
		}
		else if (command.contains("changeBGColor"))
		{
			command = command.replace("changeBGColor", "");
			ColorHashMap map = new ColorHashMap();
			gui.colorBG = ColorHashMap.StringToColor(command);
			clear();
		}
	}

	public void clear()
	{
		executioner.cmdQueue.clear();

		paintG.setColor(gui.colorBG);
		paintG.fillRect(0, 0, gui.drawPanel.getSize().width, gui.drawPanel.getSize().height);
		imageG.setColor(gui.colorBG);
		imageG.fillRect(0, 0, gui.drawPanel.getSize().width, gui.drawPanel.getSize().height);

	}

	public Image getDrawing()
	{
		return gui.saveImage;
	}

	public void writeImage(Image img, String filename) throws IOException
	{
		MyBMPFile.write(filename, img);
	}

	public void autoDraw()
	{
		Point pA = new Point(5, 5);
		Point pB = new Point(150, 150);
		Point pC = new Point(300, 300);

		this.clear();
		imageG.setColor(Color.black);
		paintG.setColor(Color.black);
		drawRectangle(pA,pB);
		drawOval(pA, pB);

		imageG.setColor(Color.green);
		paintG.setColor(Color.green);
		drawFillRectangle(pB, pC);

		imageG.setColor(Color.red);
		paintG.setColor(Color.red);
		drawFillOval(pB, pC);
		drawTriangle(pA, pC);

		imageG.setColor(Color.blue);
		paintG.setColor(Color.blue);
		drawLine(pA, pB);

		imageG.setColor(Color.white);
		paintG.setColor(Color.white);
		//drawPolyLine();
		
		imageG.setColor(Color.black);
		paintG.setColor(Color.black);
	}

	public void redraw()
	{
		//TODO FIX
		for (int i = 0; i < executioner.cmdQueue.size(); i++)
		{
			executioner.cmdQueue.get(i).draw(gui.paintG);
			executioner.cmdQueue.get(i).draw(gui.imageG);
		}
	}

	public void undo()
	{
		//TODO FIX
		executioner.cmdQueue.pollLast();
		executioner.cmdQueue.getLast().draw(gui.paintG);
		executioner.cmdQueue.getLast().draw(gui.imageG);
	}

	public void redo()
	{
		//TODO FIX
		executioner.cmdQueue.getLast().draw(gui.paintG);
		executioner.cmdQueue.getLast().draw(gui.imageG);
	}

	public void drawRectangle(Point upper_left, Point lower_right)
	{
		RectangleDrawer rect = new RectangleDrawer(upper_left, lower_right);
		executioner.execute(rect);
	}

	public void drawOval(Point upper_left, Point lower_right)
	{
		OvalDrawer oval = new OvalDrawer(upper_left, lower_right);
		executioner.execute(oval);
	}

	public void drawFillRectangle(Point upper_left,Point lower_right)
	{
		FillRectangleDrawer fillrect = new FillRectangleDrawer(upper_left, lower_right);
		executioner.execute(fillrect);
	}

	public void drawFillOval(Point upper_left, Point lower_right)
	{
		FillOvalDrawer filloval = new FillOvalDrawer(upper_left, lower_right);
		executioner.execute(filloval);
	}

	public void drawLine(Point upper_left,Point lower_right)
	{
		LineDrawer line = new LineDrawer(upper_left, lower_right);
		executioner.execute(line);
	}

	public void drawTriangle(Point upper_left, Point lower_right)
	{
		TriangleDrawer triangle = new TriangleDrawer(upper_left, lower_right);
		executioner.execute(triangle);
	}

	public void drawPolyLine(java.util.List<Point> points)
	{

		for (int i = 0; i < points.size() - 1; i++)
		{
			if (points.get(i + 1).equals(null))
			{
				break;
			}
			Point pointA = points.get(i);
			Point pointB = points.get(i + 1);

			LineDrawer polyline = new LineDrawer(pointA, pointB);
			executioner.execute(polyline);
		}

	}

	public void saveToText(String fileName) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		for (int i = 0; i < executioner.cmdQueue.size(); i++)
		{
			if (bufferedReader.readLine() == null)
			{
				bufferedWriter.write(executioner.cmdQueue.get(i).toString());
				bufferedWriter.newLine();
			}

		}
		bufferedWriter.close();
		bufferedReader.close();
	}

	public void loadFromText(String fileName) throws IOException
	{
		String line = null;
		String parse = null;
		String[] parseSplit = null;

		String start;
		String end;

		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
			{
				if(line.contains("ScribbleDrawer"))
				{
					parse = line.replaceFirst("ScribbleDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					ScribbleDrawer scribble = new ScribbleDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(scribble);
					executioner.execute(scribble);

				}
				else if(line.contains("FillOvalDrawer"))
				{
					parse = line.replaceFirst("FillOvalDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					FillOvalDrawer filloval =  new FillOvalDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(filloval);
					executioner.execute(filloval);

				}
				else if(line.contains("FillRectangleDrawer"))
				{
					parse = line.replaceFirst("FillRectangleDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					FillRectangleDrawer fillrect = new FillRectangleDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(fillrect);
					executioner.execute(fillrect);

				}
				else if(line.contains("LineDrawer"))
				{
					parse = line.replaceFirst("LineDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					LineDrawer lineDraw = new LineDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(lineDraw);
					executioner.execute(lineDraw);
				}
				else if(line.contains("RectangleDrawer"))
				{
					parse = line.replaceFirst("RectangleDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					RectangleDrawer rekt = new RectangleDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(rekt);
					executioner.execute(rekt);
				}
				else if(line.contains("OvalDrawer"))
				{
					parse = line.replaceFirst("OvalDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					OvalDrawer oval = new OvalDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(oval);
					executioner.execute(oval);
				}
				else if(line.contains("TriangleDrawer"))
				{
					parse = line.replaceFirst("TriangleDrawer: ", "");
					parseSplit = parse.split(" ");
					start = parseSplit[0];
					end = parseSplit[1];
					start = start.replaceFirst("start", "");
					end = end.replaceFirst("end", "");

					Point startPoint = parsePoint(start);
					Point endPoint = parsePoint(end);

					TriangleDrawer triangle = new TriangleDrawer(startPoint, endPoint);
					executioner.cmdQueue.add(triangle);
					executioner.execute(triangle);
				}
			}
			bufferedReader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("datei nicht gefunden");
		}
	}

	private void chooserSaveImage()
	{
		chooser.setVisible(true);
		chooser.setDialogTitle("Save Image");

		int retrieve = chooser.showSaveDialog(gui.drawFrame);
		String extension2 = chooser.getFileFilter().getDescription();
		String savePath = chooser.getSelectedFile().getAbsolutePath();

		if (retrieve == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				if (extension2.equals("*.bmp,*.BMP"))
				{
					try
					{
						this.writeImage(this.getDrawing(), savePath + ".bmp");

					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
				e.printStackTrace();
			}
		}
	}

	private void chooserSaveText()
	{
		chooser.setVisible(true);
		chooser.setDialogTitle("Save Text");

		int retrieve = chooser.showSaveDialog(gui.drawFrame);
		String extension2 = chooser.getFileFilter().getDescription();
		String savePath = chooser.getSelectedFile().getAbsolutePath();

		if (retrieve == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				if (extension2.equals("*.txt"))
				{
					try
					{
						this.saveToText(savePath + ".txt");
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
				e.printStackTrace();
			}
		}
	}

	private void chooserLoadText()
	{
		chooser.setVisible(true);
		chooser.setDialogTitle("Load Text");

		int retrieve = chooser.showOpenDialog(gui.drawFrame);

		if (retrieve == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = chooser.getSelectedFile();
			try
			{
				this.loadFromText(selectedFile.getAbsolutePath());
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(frame, "Laden abgebrochen");
				e.printStackTrace();
			}
		}
	}

	private Point parsePoint(String s)
	{
		String[] parseSplit;

		s = s.substring(1, s.length()-1);
		parseSplit = s.split(",");
		int startX = Integer.parseInt(parseSplit[0]);
		int startY = Integer.parseInt(parseSplit[1]);
		Point startPoint = new Point(startX, startY);

		return startPoint;
	}
}