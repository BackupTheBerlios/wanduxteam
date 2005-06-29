/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author dup
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgressBarStep extends JPanel
{
	JProgressBar jpb = null;
	
	public ProgressBarStep()
	{
		jpb = new JProgressBar();
		jpb.setPreferredSize(new Dimension(400, 20));
		
		this.add(jpb);
	}
}
