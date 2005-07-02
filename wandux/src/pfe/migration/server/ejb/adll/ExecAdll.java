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

	String cfgFileName = "/wandux/mandrake/unattended/";
	String xmlFileName = "/wandux/mandrake/unattended/";
	String macAdress = null;
	
  public ExecAdll (String cfgFileName)
  {
  	this.cfgFileName += cfgFileName + ".cfg";
  	xmlFileName += cfgFileName + ".xml";
  	
  	macAdress = cfgFileName;
  	createBootFile (this.cfgFileName);
  	System.out.println(this.xmlFileName);
  	System.out.println(this.cfgFileName);
  }
  
  public void createBootFile (String cfgFileName)
  {
	String ls_str;
	
	try {
		System.out.println("/wandux/utils/adll -q -o " + cfgFileName + " " + xmlFileName);
		Process ls_proc = Runtime.getRuntime().exec("/wandux/utils/adll -q -o " + cfgFileName + " " + xmlFileName);
		
	} catch (IOException e1) {
		System.err.println(e1);
		System.err.println("[pb] exec adll");
		System.exit(1);
	}
	try {
		//System.out.println("lahous >>>" +this.macAdress);
		Runtime.getRuntime().exec("/wandux/utils/createBootFile.pl " + this.macAdress);
	} catch (IOException e1) {
		System.err.println(e1);
		System.err.println("[pb] exec createBootFile.pl");
		System.exit(1);
	}
  }
}
