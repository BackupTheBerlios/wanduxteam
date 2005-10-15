package pfe.migration.client.pre.system;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author joe star
 *
 * Created on 26 févr. 2005
 */
public class FileSystemModel implements TreeModel {
    protected File root;
    private List listeners = new ArrayList();
    /** Creates a new instance of FileSystemModel */
    public FileSystemModel(File root) {
        this.root = root;
    }
    public Object getRoot() {
        return root;
    }
    public Object getChild(Object parent, int index) {
        File dir = (File) parent;
        String[] children = dir.list();
        return new TreeFile(dir, children[index]);
    }
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (fileList != null) {
                return file.list().length;
            }
        }
        return 0;
    }
    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }
    public int getIndexOfChild(Object parent, Object child) {
        File dir = (File) parent;
        File file = (File) child;
        String[] children = dir.list();
        for (int i=0; i<children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;
    }
    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {
            getIndexOfChild(parent, targetFile) };
        Object[] changedChildren =  { targetFile };
        fireTreeNodesChanged(path.getParentPath(), 
            changedChildrenIndices, changedChildren);
    }
    private void fireTreeNodesChanged(TreePath parentPath, 
        int indices[], Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, 
            parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }
    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }
    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }
    private class TreeFile extends File {
        /**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;
		public TreeFile(File parent, String child) {
            super(parent, child);
            
        }
        public String toString() {
            return getName();
        }
    }
}
