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

	  public static void main(String Argv[])
	  { // ICI CHANGE CE CODE
	  	try {
		    String ls_str;

		    Process ls_proc = Runtime.getRuntime().exec("/bin/ls -aFl");

		    // get its output (your input) stream

		    DataInputStream ls_in = new DataInputStream(ls_proc.getInputStream());

		    try {
		    	while ((ls_str = ls_in.readLine()) != null)
		    	{
		    		System.out.println(ls_str);
		    	}
			  } catch (IOException e) {	System.exit(0); }
			} catch (IOException e1) {
		    System.err.println(e1);
		    System.exit(1);
			}
			System.exit(0);
	  }
}
