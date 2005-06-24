/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintFromTreePath extends JPanel
{
	public PrintFromTreePath(TreePath[] tp)
	{
		String allPath[] = null;
		if (tp == null || tp.length == 0)
		{
			allPath = new String [1];
			allPath[0] = "Aucun élément n'a été sélectionné";
		}
		else
		{
			allPath = new String [tp.length+1];
			allPath[0] = "Liste des fichiers séléctionnés";
			for (int i = 0; i < tp.length ; i++)
			{
				allPath[i+1] = formatPath(tp[i]);
			}
		}
		JList jl = new JList(allPath);
		// jl.scrollRectToVisible(new Rectangle(400, 400)); // TODO trouver comment mettre les scrollbar
		this.add(jl);
	}

	public String formatPath(TreePath t)
	{
		String s = "";
		s = t.toString().replaceAll(", ", "/");
		s = s.replaceFirst("\\\\", "");
		return s;
	}
}
