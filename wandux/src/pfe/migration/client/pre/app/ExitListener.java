package pfe.migration.client.pre.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author joe star
 *
 * Created on 11 mars 2005
 */
public class ExitListener extends WindowAdapter
{
	public void windowClosing(WindowEvent event)
	{
		System.exit(0);
	}
}