package pfe.migration.client.pre.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer{ 
    private CheckTreeSelectionModel selectionModel; 
    private TreeCellRenderer delegate; 
    private TristateCheckBox checkBox = new TristateCheckBox(); 
 
    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel){ 
        this.delegate = delegate; 
        this.selectionModel = selectionModel; 
        setLayout(new BorderLayout()); 
        setOpaque(false); 
        checkBox.setOpaque(false); 
    } 
 
 
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){ 
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 
 
        TreePath path = tree.getPathForRow(row); 
        if(path!=null){ 
            if(selectionModel.isPathSelected(path, true)) 
                checkBox.setState(Boolean.TRUE); 
            else 
                checkBox.setState(selectionModel.isPartiallySelected(path) ? null : Boolean.FALSE); 
        } 
        removeAll(); 
        add(checkBox, BorderLayout.WEST); 
        add(renderer, BorderLayout.CENTER); 
        return this; 
    } 
}  


class RenduComposant implements TreeCellRenderer{
    
        public Component getTreeCellRendererComponent(JTree tree, Object obj,
            boolean selected, boolean expanded, boolean leaf,
                int row, boolean hasFocus){
        
        DefaultMutableTreeNode dmtcr = (DefaultMutableTreeNode)obj;
        JCheckBox toto = (JCheckBox)dmtcr.getUserObject();

        return toto;
    }
}

class EditComposant implements TreeCellEditor{

    public void addCellEditorListener(CellEditorListener l){ }
    public void cancelCellEditing() { }
    public Object getCellEditorValue(){
        return this;
    }
    public boolean isCellEditable(EventObject evt){
        if(evt instanceof MouseEvent){
            MouseEvent mevt = (MouseEvent) evt;
            if (mevt.getClickCount() == 1){
                return true;
            }
        }
        return false;
    }

    public void removeCellEditorListener(CellEditorListener l){}
    public boolean shouldSelectCell(EventObject anEvent){
        return true;
    }
    public boolean stopCellEditing(){
            return false;
    }
    public Component getTreeCellEditorComponent(JTree tree, Object obj, boolean isSelected, boolean expanded, boolean leaf, int row){
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)obj;
        JCheckBox tata=(JCheckBox)dmtn.getUserObject();
        tata.setEnabled(true);
        return tata;
    }
}