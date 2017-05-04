package MyDraw;

import javax.swing.JOptionPane;

public class ColorException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -8848154012034505650L;

    public ColorException()
    {
        //JOptionPane.showMessageDialog(null,  "Unbekannte Farbe!");
        System.out.println("Unbekannte Farbe!");
    }
}
