/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.awt.Dimension;

import javax.swing.JLabel;
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
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar jpb = null;
	private JLabel jl = null;
	private JLabel topText = null;
	
	public ProgressBarStep(int fileNb)
	{
//		this.setLayout(new BorderLayout());

		this.jpb = new JProgressBar();
		this.jpb.setMaximum(fileNb);
		this.jpb.setPreferredSize(new Dimension(400, 20));
		this.jpb.setStringPainted(true);

		this.jl = new JLabel("");
		
		this.topText = new JLabel("fichiers en cours de copie ...");
		
		this.add(this.topText);
		this.add(this.jpb);
		this.add(this.jl);
	}
	
	public void refreshBar(int currentCopied, String currentFile)
	{
		if (currentCopied == jpb.getMaximum())
			this.topText.setText("Tous les fichiers ont été transférés");
		this.jl.setText(currentFile);
		this.jpb.setValue(currentCopied);
	}
}
