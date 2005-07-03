/*
 * Created on 3 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

import java.io.IOException;

import pfe.migration.client.network.ComputerInformation;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyBookmark {

	/**
	 * 
	 */
	public CopyBookmark(ComputerInformation ci) {
		try {
			Process ls_proc = Runtime.getRuntime().exec("/wandux/utils/iefav2xml \\\\10.247.0.248\\wanduxStorage\\" + ci.getMac() + "\\diskc\\" + ci.udata.getUserLogin() + "\\Favoris");
		} catch (IOException e1) {
			System.err.println("Copy du bookmark");
		}
	}

}
