/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pfe.migration.client.network.ClientEjb;

/**
 * @author dup
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LastStep extends JPanel
{
	public LastStep(final ClientEjb ce)
	{
		JButton fin = new JButton("Fin");
		
		this.add(new JLabel("Merci d avoir choisi Wandux"));
		this.add(fin);
		fin.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				ce.EjbClose();
				System.exit(0);
			}
		});
	}

}
