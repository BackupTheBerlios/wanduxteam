package pfe.migration.client.pre.service;

import javax.swing.tree.DefaultMutableTreeNode;

public class SelectableMutableTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	private boolean chosen;

	public SelectableMutableTreeNode() {
		super();
	}

	public SelectableMutableTreeNode(Object arg0) {
		super(arg0);
	}

	public SelectableMutableTreeNode(Object arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	* Chosen is used since selected is already used by checkbox and tree node
	* @return boolean
	*/
	public boolean isChosen() {
		return chosen;
	}
	
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

}
