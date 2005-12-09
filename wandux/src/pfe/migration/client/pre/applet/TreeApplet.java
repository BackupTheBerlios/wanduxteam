package pfe.migration.client.pre.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import pfe.migration.client.pre.applet.system.FileSystemModel;
import pfe.migration.client.pre.applet.tree.CheckTreeManager;

public class TreeApplet extends Applet {

	private static final long serialVersionUID = 1L;

	public void init()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		FileSystemModel fileSystemModel = new FileSystemModel(new File("\\"));
		final JTree fileTree = new JTree(fileSystemModel);
        new CheckTreeManager(fileTree);

        JScrollPane jsp  = new JScrollPane(fileTree);
        addAdjustmentListener(jsp);
        
        add(jsp, BorderLayout.CENTER);

		invalidate();
		validate();
	}
	
	private void addAdjustmentListener(JScrollPane jsp)
	{
		jsp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{	public void adjustmentValueChanged(AdjustmentEvent a)
			{
				repaint();
			}
		});
		jsp.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{	public void adjustmentValueChanged(AdjustmentEvent a)
			{
				repaint();
			}
		});
	}
}
