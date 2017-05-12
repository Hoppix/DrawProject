package MyDraw;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by khopf on 12/05/2017.
 */
public class TextFileFilter extends FileFilter
{

	/**
	 * Whether the given file is accepted by this filter.
	 *
	 * @param f
	 */
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{
			return false;
		}

		String s = f.getName();

		return s.endsWith(".txt")||s.endsWith(".TXT");
	}

	/**
	 * The description of this filter. For example: "JPG and GIF Images"
	 *
	 *
	 */
	@Override
	public String getDescription()
	{
		return "*.txt";
	}
}
