/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import pfe.migration.client.pre.app.CheckTreeManager;
import pfe.migration.client.pre.system.FileSystemModel;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JTreeWithCheckbox extends JPanel
{
	private JSplitPane 	jSplitPaneLocalFs = null;
	private CheckTreeManager checkTreeManager = null; 

	public JTreeWithCheckbox()
	{
		FileSystemModel fileSystemModel = new FileSystemModel(new File("\\"));
		final JTextArea fileDetails = new JTextArea("");
        final JTree fileTree = new JTree(fileSystemModel);
		
        this.checkTreeManager = new CheckTreeManager(fileTree);
        
        
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(
					TreeSelectionEvent event) {
				File file = (File) fileTree.getLastSelectedPathComponent();
				fileDetails.setText(getFileDetails(file));
				}
			});
	 	JScrollPane FileDetailsSP = new JScrollPane(fileDetails);

		jSplitPaneLocalFs = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true,
				new JScrollPane(fileTree), FileDetailsSP);

		jSplitPaneLocalFs.setDividerSize(1);
	
		jSplitPaneLocalFs.setPreferredSize(new java.awt.Dimension(400, 400));
		this.add(jSplitPaneLocalFs);
	}
	
	public String formatPath(TreePath t)
	{
		String s = "";
		s = t.toString().replaceAll(", ", "\\\\");
		s = s.replaceFirst("\\\\", "");
		s = s.substring(1, s.length() - 1);
		return s;
	}

	public String [] getSelectionnedPaths()
	{
		String [] list = null;
		TreePath [] tp = checkTreeManager.getSelectionModel().getSelectionPaths(); 
		if (tp == null || tp.length == 0)
		{
			list = new String [1];
			list[0] = "Aucun élément n'a été sélectionné";
		}
		else
		{
			list = new String [tp.length+1];
			list[0] = "Liste des fichiers séléctionnés";
			for (int i = 0; i < tp.length ; i++)
			{
				list[i+1] = formatPath(tp[i]);
			}
		}
		return list;
	}
	
	private String getFileDetails(File file)
	{
    	final String NL = System.getProperty("line.separator");
        if (file == null)
            return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name : "+file.getName()+NL);
        buffer.append("Path : "+file.getPath()+NL);
        buffer.append("Size : "+file.length()+NL);
        return buffer.toString();
    }

}
