package FileFilters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by khopf on 08/05/2017.
 */
public class BMPFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return false;
        }

        String s = f.getName();

        return s.endsWith(".bmp")||s.endsWith(".BMP");
    }

    @Override
    public String getDescription()
    {
        return "*.bmp,*.BMP";
    }
}
