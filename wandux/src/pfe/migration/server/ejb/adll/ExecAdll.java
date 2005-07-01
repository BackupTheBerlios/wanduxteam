/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.server.ejb.adll;

import	java.io.*;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExecAdll {

	String cfgFileName = "/wandux/mandrake/unattended/";
	String xmlFileName = "/wandux/mandrake/unattended/";
	
  public ExecAdll (String cfgFileName)
  {
  	this.cfgFileName += cfgFileName + ".cfg";
  	xmlFileName += cfgFileName + ".xml";
  }
	
  public void doExec ()
  {
	String ls_str;
	
	try {
		Process ls_proc = Runtime.getRuntime().exec("/wandux/utils/adll -q -o " + cfgFileName + " " + xmlFileName);
	} catch (IOException e1) {
		System.err.println(e1);
		System.exit(1);
	}
  }
}
