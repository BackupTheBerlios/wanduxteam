package pfe.migration.client.pre.applet.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import pfe.migration.client.pre.applet.TreeApplet;



public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener{ 
    private CheckTreeSelectionModel selectionModel; 
    private JTree tree = new JTree(); 
    int hotspot = new JCheckBox().getPreferredSize().width; 
 
    public CheckTreeManager(JTree tree){ 
        this.tree = tree; 
        
        selectionModel = new CheckTreeSelectionModel(tree.getModel()); 
        tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel)); 
        tree.addMouseListener(this); 
        selectionModel.addTreeSelectionListener(this); 
    } 
 
    public void mouseClicked(MouseEvent me){
        TreePath path = tree.getPathForLocation(me.getX(), me.getY()); 
        if(path==null) 
            return; 
        if(me.getX()>tree.getPathBounds(path).x+hotspot) 
            return; 
 
//    	System.out.println("---------------");
//    	System.out.println(path.getLastPathComponent());

    	boolean selected = selectionModel.isPathSelected(path); //, true); 
        selectionModel.removeTreeSelectionListener(this); 

        try{ 
            if(selected) 
            {
            	TreeApplet.finalList.remove(path.getLastPathComponent().toString());
            	selectionModel.removeSelectionPath(path); 
            }
            else 
            {
            	TreeApplet.finalList.add(path.getLastPathComponent().toString());
                selectionModel.addSelectionPath(path);
            }
        } finally{ 
            selectionModel.addTreeSelectionListener(this); 
            tree.treeDidChange(); 
        } 
    } 
 
    public CheckTreeSelectionModel getSelectionModel(){ 
        return selectionModel; 
    } 
 
    public void valueChanged(TreeSelectionEvent e){ 
        tree.treeDidChange(); 
    } 
}