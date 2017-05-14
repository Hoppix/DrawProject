package mydraw;
import static org.junit.Assert.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DrawTest
{
    //GUIHandler testApp;
    //DrawGUIs testMe;
    Image testCorrect;
    Draw testMe;


    public DrawTest()
    {
        //testMe = new DrawGUIs();
        //testApp = new GUIHandler(testMe);
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
        testCorrect =  MyBMPFile.read("imageTesting\\testCorrect.bmp");
        testMe.autoDraw();
        testMe.writeImage(testMe.getDrawing(), "imageTesting\\test.bmp");
        BufferedImage testImg = (BufferedImage) testMe.getDrawing();
        BufferedImage buffImg = convertToBufferedImage(testCorrect);
        boolean boolshit = true;
        
        if(testImg.getHeight() == buffImg.getHeight() && testImg.getWidth() == buffImg.getWidth())
        {
            for(int x = 0; x < buffImg.getWidth(); x++)
            {
                for(int y = 0; y < buffImg.getHeight(); y++)
                {
                    if(testImg.getRGB(x, y) != buffImg.getRGB(x, y))
                    {
                        boolshit =  false;
                        break;
                    }
                }
            }
        }
        else
        {
            boolshit = false;
        }

        assertTrue(boolshit);
        
    }
    
    private BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    @Before
    public void resetTestMe()
    {
        //testMe = new DrawGUIs();
        //testApp = new GUIHandler(testMe);
    	testMe = new Draw();
    }

}
