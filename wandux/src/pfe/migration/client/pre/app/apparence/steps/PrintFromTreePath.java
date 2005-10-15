/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintFromTreePath extends JPanel
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public PrintFromTreePath(String[] tp)
	{
		JList jl = new JList(tp);
//		jl.setFocusable(false);

		JScrollPane scrollPane= new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,400));
		scrollPane.setViewportView(jl);
		this.add(scrollPane);
	}

}
