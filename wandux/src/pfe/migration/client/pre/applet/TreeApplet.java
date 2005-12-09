package pfe.migration.client.pre.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JTree;

import pfe.migration.client.pre.applet.system.FileSystemModel;
import pfe.migration.client.pre.applet.tree.CheckTreeManager;

public class TreeApplet extends Applet {

	public void init()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		FileSystemModel fileSystemModel = new FileSystemModel(new File("\\"));
//		final JTextArea fileDetails = new JTextArea("");

		final JTree fileTree = new JTree(fileSystemModel);
        new CheckTreeManager(fileTree);
		add(fileTree, BorderLayout.CENTER);

		// scrollbar
		// http://forum.java.sun.com/thread.jspa?threadID=422026&messageID=1874240
		//
		// http://tools.arlut.utexas.edu/gash2/doc/javadoc/arlut/csd/JTree/treeControl.html
		
//		Scrollbar redSlider=new Scrollbar();
//		redSlider.addAdjustmentListener(fileTree.get)
//		add(redSlider, BorderLayout.EAST);
		
		// add(new JCheckBox(), BorderLayout.CENTER);
		// add(new TristateCheckBox("euh", null), BorderLayout.CENTER);
	  	// add(new Label("Vous etes interdit de séjour sur les chats Interneto", Label.CENTER), BorderLayout.CENTER);

		invalidate();
		validate();
	}
	
}
