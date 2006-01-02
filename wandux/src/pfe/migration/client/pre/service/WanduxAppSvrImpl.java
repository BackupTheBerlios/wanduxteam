package pfe.migration.client.pre.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

public class WanduxAppSvrImpl implements WanduxAppSvr {

	private static final long serialVersionUID = 1L;
	private DefaultTreeModel dtm = null; 
	
	public WanduxAppSvrImpl() {
		super();
	}
	
	public void setSelectedFileList(DefaultTreeModel tree) throws RemoteException
	{
		
		this.dtm = tree;
	}
	
	private void dataRecoder()
	{
		List list = new ArrayList();
		SelectableMutableTreeNode rootNode = (SelectableMutableTreeNode)this.dtm.getRoot();
		getSelectedFiles(rootNode,list); // TODO verifier le bon fonctionnement

//		for (int i = 1; i < list.size(); i++)
//		{
//			File f = new File(listFiles[i]);
//			
//			String disk = "disk" + listFiles[i].substring(0,1);
//			String path = listFiles[i].substring(3,listFiles[i].length());
//			
//			if (((File)list.get(i)).isDirectory() == true)
//			{
//				DirCopy.CopyRec(listFiles[i] , "\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.addrMac + "\\"+ disk + "\\" + path);
//			}
//			else
//			{
//				FileCopy.CopyRec(listFiles[i] , "\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.addrMac + "\\"+ disk);
//			}
//			this.pbs.refreshBar(i , listFiles[i]);
//		}
	}

	private void getSelectedFiles(SelectableMutableTreeNode rootNode,List toAddTo){
		if(rootNode.isLeaf())
			return;
		Enumeration enum = rootNode.children();
		while(enum.hasMoreElements())
		{
			SelectableMutableTreeNode nextNode = (SelectableMutableTreeNode)enum.nextElement();
			if(nextNode.isChosen())
			{
		      toAddTo.add(nextNode.getUserObject());
			}
			getSelectedFiles(nextNode,toAddTo);
		}
	}

	public DefaultTreeModel getSelectedFileList() throws RemoteException
	{
		return this.dtm;
	}

}
