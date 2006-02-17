/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.server.ejb.adll;

import java.io.IOException;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExecAdll {

	String cfgFileName = "";
	String xmlFileName = "";
	String macAdress = null;
	
  public ExecAdll (String path, String hostname, String MacAddr)
  {
	cfgFileName = path;
	xmlFileName = path;
  	this.cfgFileName += MacAddr + ".cfg";
  	xmlFileName += hostname + ".xml";
  	
  	macAdress = cfgFileName;
  	createBootFile (this.cfgFileName, MacAddr);
  	System.out.println(this.xmlFileName);
  	System.out.println(this.cfgFileName);
  }
  
  public void createBootFile (String path, String MacAddr)
  {
	String ls_str;
	
	try {
		System.out.println(path + "/adll -q -o " + cfgFileName + " " + xmlFileName);
		Process ls_proc = Runtime.getRuntime().exec("/wandux/utils/adll -q -o " + cfgFileName + " " + xmlFileName);
		
	} catch (IOException e1) {
		System.err.println(e1);
		System.err.println("[pb] exec adll");
		System.exit(1);
	}
	try {
		Runtime.getRuntime().exec("/wandux/utils/createBootFile.pl " + MacAddr);
	} catch (IOException e1) {
		System.err.println(e1);
		System.err.println("[pb] exec createBootFile.pl");
		System.exit(1);
	}
  }
}
