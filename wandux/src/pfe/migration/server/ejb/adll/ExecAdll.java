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

	String cfgFileName = "C:\\wandux\\output\\mandrake_10_1_FR.pl";
	String xmlFileName = "C:\\wandux\\output\\mandrake_10_1_FR.xml";
	
  public ExecAdll (String cfgFileName, String xmlFileName)
  {
  	this.cfgFileName = cfgFileName;
  	this.xmlFileName = xmlFileName;
  }
	
  public void doExec ()
  {
	String ls_str;
	
	try {
		Process ls_proc = Runtime.getRuntime().exec("./utils/adll/linux/adll -q -o " + cfgFileName + " " + xmlFileName);
	} catch (IOException e1) {
		System.err.println(e1);
		System.exit(1);
	}
  }
}
