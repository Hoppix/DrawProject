package mydraw;
import static org.junit.Assert.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DrawTest
{

    Draw testMe;

    public DrawTest()
    {

    	testMe = new Draw();
    }
    
    @Before
    public void resetTestMe()
    {
    	testMe = new Draw();
    }
    
    @Test
    public void testGetSetBG()
    {
        try
        {
            testMe.setBGColor("white");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertEquals(testMe.getBGColor() , "white");
        assertNotEquals("black", testMe.getBGColor());
    }
    
    @Test
    public void testeGetSetFG()
    {
        try
        {
            testMe.setFGColor("red");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertEquals(testMe.getFGColor(), "red");
        assertNotEquals("green", testMe.getFGColor());
    }
    
    @Test(expected = ColorException.class)
    public void testColorUndefined() throws ColorException
    {
        testMe.setFGColor("top kek");
    }
    
    @Test(expected = ColorException.class)
    public void testColorBGUndefined() throws ColorException
    {
        testMe.setBGColor("get rekt");
    }
    
    @Test
    public void testSetGetHeight()
    {
        testMe.setHeight(42);
        assertEquals(42, testMe.gui.drawPanel.getHeight());
        assertNotEquals(0, testMe.gui.drawPanel.getHeight());
        
        testMe.setHeight(1337);
        assertEquals(1337, testMe.gui.drawPanel.getHeight());
        assertNotEquals(0, testMe.gui.drawPanel.getHeight());
    }
    
    @Test
    public void testSetGetWidth()
    {
        testMe.setWidth(42);
        assertEquals(42, testMe.gui.drawPanel.getWidth());
        assertNotEquals(0, testMe.gui.drawPanel.getWidth());
        
        testMe.setWidth(1337);
        assertEquals(1337, testMe.gui.drawPanel.getWidth());
        assertNotEquals(0, testMe.gui.drawPanel.getWidth());
    }
    
  
    @Test
    public void testAutoDraw() throws IOException, InterruptedException
    {
    	Image testCorrect;
    	Image testWrongSize;
    	Image testWrongContent;
    	
        testCorrect =  MyBMPFile.read("imageTesting\\testCorrect.bmp");
        testWrongSize =  MyBMPFile.read("imageTesting\\testWrongSize.bmp");
        testWrongContent =  MyBMPFile.read("imageTesting\\testWrongContent.bmp");
        
        testMe.autoDraw();
        testMe.writeImage(testMe.getDrawing(), "imageTesting\\test.bmp");
        
        BufferedImage drawnImg = (BufferedImage) testMe.getDrawing();
        BufferedImage correctImg = convertToBufferedImage(testCorrect);
        BufferedImage wrongSize = convertToBufferedImage(testWrongSize);
        BufferedImage wrongContent = convertToBufferedImage(testWrongContent);
        

        assertTrue(compareImages(drawnImg, correctImg));
        assertFalse(compareImages(drawnImg, wrongSize));
        assertFalse(compareImages(drawnImg, wrongContent));       
    }
        
    @Test
    public void testClear() throws IOException
    {
    	Image testBlankImage;
    	
    	
    	testMe.autoDraw();    	
    	testMe.clear();
    	testMe.writeImage(testMe.getDrawing(), "imageTesting\\test.bmp");
    	
    	BufferedImage drawnImg = (BufferedImage) testMe.getDrawing();
    	testBlankImage = MyBMPFile.read("imageTesting\\testBlankImage.bmp");
    	BufferedImage testBlank = convertToBufferedImage(testBlankImage);
    	
    	assertTrue(compareImages(drawnImg, testBlank));
    }
    
    @Test
    public void testgetDrawing() throws IOException
    {
    	Image drawn;
    	Image testCorrect;
    	
    	testMe.autoDraw();
    	drawn = testMe.getDrawing();
    	BufferedImage drawnImg = convertToBufferedImage(drawn);
    	testCorrect =  MyBMPFile.read("imageTesting\\testCorrect.bmp");
    	BufferedImage correctImg = convertToBufferedImage(testCorrect);
    	
    	assertTrue(compareImages(drawnImg, correctImg));
    }
    
    @Test
    public void testUndoRedo() throws IOException
    {
    	Image testUndo1;
    	Image testUndo2;
    	Image testRedo1;
    	Image testRedo2;
    	
    	testUndo1 = MyBMPFile.read("imageTesting\\testUndo1.bmp");
    	testUndo2 = MyBMPFile.read("imageTesting\\testUndo2.bmp");
    	testRedo1 = MyBMPFile.read("imageTesting\\testRedo1.bmp");
    	testRedo2 = MyBMPFile.read("imageTesting\\testRedo2.bmp");    	
    	
    	testMe.autoDraw();
    	testMe.writeImage(testMe.getDrawing(), "imageTesting\\test.bmp");
    	BufferedImage drawnImg = (BufferedImage) testMe.getDrawing();
    	
    	BufferedImage undo1 = convertToBufferedImage(testUndo1);
    	BufferedImage undo2 = convertToBufferedImage(testUndo2);
    	BufferedImage redo1 = convertToBufferedImage(testRedo1);
    	BufferedImage redo2 = convertToBufferedImage(testRedo2);
    	
    	testMe.undo();
    	assertTrue(compareImages(drawnImg, undo1));
    	testMe.undo();
    	assertTrue(compareImages(drawnImg, undo2));
    	testMe.redo();
    	assertTrue(compareImages(drawnImg, redo1));
    	testMe.redo();
    	assertTrue(compareImages(drawnImg, redo2));
    }
    
    @Test
    public void testSaveLoadTxt() throws IOException, InterruptedException
    {
    	testMe.readText("txtTesting\\testCorrecttxt.txt");
    	testMe.writeImage(testMe.getDrawing(), "imageTesting\\test.bmp");
    	BufferedImage drawnImg = (BufferedImage) testMe.getDrawing();
    	
    	Image testCorrect;
    	testCorrect =  MyBMPFile.read("imageTesting\\testLoadTxt.bmp");
    	BufferedImage correctImg = convertToBufferedImage(testCorrect);

    	//assertTrue(compareImages(drawnImg, correctImg));
    	
    	testMe.writeText("txtTesting\\test.txt");
    	assertTrue(compareTxt("txtTesting\\test.txt", "txtTesting\\testCorrecttxt.txt"));
    }
    
    private BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    private boolean compareImages(BufferedImage drawn, BufferedImage compare)
    {
    	if(drawn.getHeight() == compare.getHeight() && drawn.getWidth() == compare.getWidth())
        {
            for(int x = 0; x < compare.getWidth(); x++)
            {
                for(int y = 0; y < compare.getHeight(); y++)
                {
                    if(drawn.getRGB(x, y) != compare.getRGB(x, y))
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            return false;
        }
    	
    	return true;
    }
    
	private boolean compareTxt(String fileA, String fileB)
    {
		String lineSaved = null;
		String lineOriginal = null;
		String savedAllCommands = "";
		String originalAllCommands = "";
		
		try {
		FileReader fileReaderSaved;		
		fileReaderSaved = new FileReader(fileA);
		BufferedReader bufferedReaderSaved = new BufferedReader(fileReaderSaved);
		
        FileReader fileReaderOriginal = new FileReader(fileB);        
        BufferedReader bufferedReaderOriginal = new BufferedReader(fileReaderOriginal);
        
        try 
        {
			while ((lineSaved = bufferedReaderSaved.readLine()) != null)
			{
				savedAllCommands = savedAllCommands + lineSaved;
			}
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
        try 
        {
			while ((lineOriginal = bufferedReaderOriginal.readLine()) != null)
			{
				originalAllCommands = originalAllCommands + lineOriginal;
			}
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return (originalAllCommands.equals(savedAllCommands));
    }
}