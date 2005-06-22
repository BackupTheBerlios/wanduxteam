/*
 * Created on 16 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author lahous
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogDebug {

	public LogDebug(Exception e)
	{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("c:\\wandux\\errors.txt");
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		PrintStream ps = new PrintStream(fos);
		System.setErr(ps);
		System.out.println("foo");
		System.err.println(e.getMessage());
	}
}
