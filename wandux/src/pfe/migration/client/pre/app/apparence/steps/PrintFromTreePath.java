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
import javax.swing.tree.TreePath;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintFromTreePath extends JPanel
{
//	public PrintFromTreePath(TreePath[] tp)
//	{
//		String allPath[] = null;
//		if (tp == null || tp.length == 0)
//		{
//			allPath = new String [1];
//			allPath[0] = "Aucun élément n'a été sélectionné";
//		}
//		else
//		{
//			allPath = new String [tp.length+1];
//			allPath[0] = "Liste des fichiers séléctionnés";
//			for (int i = 0; i < tp.length ; i++)
//			{
//				allPath[i+1] = formatPath(tp[i]);
//			}
//		}
//		JList jl = new JList(allPath);
////		jl.setFocusable(false);
//
//		JScrollPane scrollPane= new JScrollPane();
//		scrollPane.setPreferredSize(new Dimension(400,400));
//		scrollPane.setViewportView(jl);
//		this.add(scrollPane);
//	}
//
//	public String formatPath(TreePath t)
//	{
//		String s = "";
//		s = t.toString().replaceAll(", ", "/");
//		s = s.replaceFirst("\\\\", "");
//		return s;
//	}

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
