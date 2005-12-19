/*
 * Created on 2 déc. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.service;
import com.jacob.com.*;
/**
 * @author lahous
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WanduxWmiBridge {
	
//	public native void toto();
	

//	
//	   System.runFinalizersOnExit(true);
//	   Variant v = new Variant();
//	   v.putInt(10);
//	   System.out.println("got="+v.toInt());
//	   v.putInt(10);
//	   System.out.println("got="+v.toDouble());
//	   v.putString("1234.567");
//	   System.out.println("got="+v.toString());
//	   v.putBoolean(true);
//	   System.out.println("got="+v.toBoolean());
//	   v.putBoolean(false);
//	   System.out.println("got="+v.toBoolean());
//	   v.putCurrency(123456789123456789L);
//	   System.out.println("got="+v.toCurrency());

	public native boolean connexion(String rootPAth);
	public native boolean deconnexion();
	//public native String[] exec_rq(String rq, String wszName);
//	public native Object exec_rq(String rq, String wszName);
	public native Variant exec_rq(String rq, String wszName);
//	public native int exec_rq_int(String rq, String wszName);
//	public native String exec_rq_string(String rq, String wszName);
//	public native String[] exec_rq(String rootPAth, String rq, String wszName);
	
	static {
  	try { 
      	System.loadLibrary("wwi");
      } catch (UnsatisfiedLinkError e) { 
          System.err.println("Library not found"); 
          System.exit(-1);
      } 
	 }
	
	/*
	 * constructeur permet d'instancier une seule fois la librairie wwi.dll
	 * cela permet de ne pas faire de connexion wmi a chaque requette
	 * 
	 */
	public  WanduxWmiBridge(String rootPAth)
	{
		connexion(rootPAth);
	}
	
	/*
	 * destructeur: permet d'appeler la methode pour fermer la connexion wmi dans la librairie wwi.dll
	 * 
	 */
	protected void finalize() {
		 // TODO: penser a faire le close dans le destructeur de la classe
		deconnexion();
		}
}
