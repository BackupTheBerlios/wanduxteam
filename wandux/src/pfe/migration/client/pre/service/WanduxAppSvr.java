package pfe.migration.client.pre.service;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.tree.DefaultTreeModel;

public interface WanduxAppSvr  extends Serializable, Remote {
	public void setSelectedFileList(DefaultTreeModel tree) throws RemoteException;
}
