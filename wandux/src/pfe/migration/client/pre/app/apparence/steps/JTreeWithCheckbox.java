/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JComboBox;
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
public class JTreeWithCheckbox extends JPanel implements ActionListener
{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private Map pathList = null;
	
	private JSplitPane 	jSplitPaneLocalFs = null;
	private CheckTreeManager checkTreeManager = null; 
	private JComboBox jcb = null;
	private String currentHD = "";
	
	public JTreeWithCheckbox()
	{
		this.pathList = new HashMap();
		
		JTreeWithCheckboxPanel("c:");
		
		jcb = new JComboBox(rootLister());
		jcb.setBackground(Color.WHITE);

		jcb.addActionListener(this);
		
		JPanel jp = new JPanel();
		jp.setBackground(Color.WHITE);
		jp.add(jcb);

        this.setLayout(new BorderLayout());
        this.add(jp, BorderLayout.NORTH);
		this.add(jSplitPaneLocalFs, BorderLayout.CENTER);
	}
	
	public void JTreeWithCheckboxPanel(String hd)
	{
		this.currentHD = hd;
		FileSystemModel fileSystemModel = new FileSystemModel(new File(hd + "\\"));
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
		this.jSplitPaneLocalFs = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true,
				new JScrollPane(fileTree), FileDetailsSP);
		jSplitPaneLocalFs.setDividerSize(1);
		jSplitPaneLocalFs.setPreferredSize(new java.awt.Dimension(400, 400));
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
	
	public String [] rootLister()
	{
		List l = new ArrayList();
		for (char c = 'C'; c < 'Z'; c++)
		{
			String s = new String ( c + ":");
			File drive = new File(s);
            if (drive.exists())
                l.add(s);
        }
		return (String[]) l.toArray(new String[l.size()]);
	}
	
	public String formatPath(TreePath t)
	{
		String s = "";
		s = t.toString().replaceAll(", ", "\\\\");
		s = s.replaceFirst("\\\\", "");
		s = s.substring(1, s.length() - 1);
		return s;
	}
	
	// -- used with JComboBox -- //
	public void putSelectionnedPaths()
	{
		List list = new ArrayList();
		
		TreePath [] tp = checkTreeManager.getSelectionModel().getSelectionPaths(); 
		if (tp == null || tp.length == 0)
			return ;
		for (int i = 0; i < tp.length ; i++)
		{
			list.add(formatPath(tp[i]));
		}
		this.pathList.put(this.currentHD, list);
	}
	
	// -- called by wandux app -- //
	public String [] getSelectionnedPaths()
	{
		List fullList = new ArrayList();
		
		TreePath [] tp = checkTreeManager.getSelectionModel().getSelectionPaths();
		
		if ((tp == null || tp.length == 0) && this.pathList.size() == 0)
			return new String [] {"Aucun élément n'a été sélectionné"};

		fullList.add("Liste des fichiers séléctionnés");
		if (tp != null || tp.length > 0)
			for (int i = 0; i < tp.length ; i++)
				fullList.add(formatPath(tp[i]));

		Set s = this.pathList.entrySet();
		Iterator it = s.iterator();
		while (it.hasNext())
		{
			Entry e = (Entry)it.next();
			if (e.getKey().equals(currentHD))
				continue ;
			fullList.addAll((Collection) e.getValue());
		}
		return (String[]) fullList.toArray(new String[fullList.size()]);
	}

	// -- action listener -- //
	public void actionPerformed(ActionEvent arg0)
	{
		this.remove(this.jSplitPaneLocalFs);
		putSelectionnedPaths();
		JTreeWithCheckboxPanel(this.jcb.getSelectedItem().toString());
		this.add(this.jSplitPaneLocalFs);
		this.invalidate();
		this.validate();
	}
	
}
