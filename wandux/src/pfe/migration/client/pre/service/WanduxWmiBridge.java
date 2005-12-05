/*
 * Created on 2 déc. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.service;

/**
 * @author lahous
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WanduxWmiBridge {
	
//	public native void toto();
	public native String[] exec_rq(String rq, String wszName);
	static {
  	try { 
      	System.loadLibrary("wwi");
      } catch (UnsatisfiedLinkError e) { 
          System.err.println("Library not found"); 
          System.exit(-1);
      } 
	 }
}
