/*
 * Created on 2 déc. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.service;

import com.jacob.com.Variant;

/**
 * @author lahous
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WanduxWmiBridge {
	

	
//	public native boolean connexion(String rootPAth);
//	public native boolean deconnexion();
//	public native Variant[] exec_rq(String rq, String wszName);
	
	public  Variant[] exec_rq(String rq, String wszName)
	{
		Win32Cim w32c = new Win32Cim(rq);
		
		
		return w32c.getResult();
	}
	//
//	static {
//  	try { 
//      	System.loadLibrary("wwi");
//      } catch (UnsatisfiedLinkError e) { 
//          System.err.println("Library wwi.dll not found"); 
//          e.printStackTrace();
//          System.exit(-1);
//      } 
//	 }
//	
//	/*
//	 * constructeur permet d'instancier une seule fois la librairie wwi.dll
//	 * cela permet de ne pas faire de connexion wmi a chaque requette
//	 * 
//	 */
	public  WanduxWmiBridge(String rootPAth)
	{

//		connexion(rootPAth);
	}

//	
//	/*
//	 * destructeur: permet d'appeler la methode pour fermer la connexion wmi dans la librairie wwi.dll
//	 * 
//	 */
//	protected void finalize() {
//		 // TODO: penser a faire le close dans le destructeur de la classe
//		deconnexion();
//		}
}
/*
	public WanduxWmiBridge(String root)
	{
	
	}
	public  Variant[] exec_rq(String rq, String wszName)
	{
		
//		ActiveXComponent objWMIService = new ActiveXComponent("winmgmts:{impersonationLevel=impersonate}");
//
//				Variant arg[] = new Variant[] {new Variant("Win32_Process")};
//				Dispatch objProcs = objWMIService.invoke("InstancesOf",arg).toDispatch();
//				EnumVariant enumProcs = Dispatch.get(objProcs, "_newenum" ).toEnumVariant();
//				while ( enumProcs.hasMoreElements() )
//				{
//					Dispatch objProcess = enumProcs.Next().toDispatch();
//					String strCaption = Dispatch.get(objProcess, "Caption").toString();
//	
//					System.out.println("Process Name: " + strCaption);
//				}
	ActiveXComponent com = new ActiveXComponent("WbemScripting.SWbemLocator");
	Object objLocator = com.getObject();
	Object objService = Dispatch.call((Dispatch) objLocator, "ConnectServer", ".", "root\\cimv2").toDispatch();
///	Dispatch.put(objService, "Security_.ImpersonationLevel", new Integer(3));
	Object instances = Dispatch.call((Dispatch) objService, "ExecQuery", rq).toDispatch();
//	variant = Dispatch.get((Dispatch) instances,wszName);	
	
	EnumVariant rsltVariants = Dispatch.get((Dispatch) instances, wszName ).toEnumVariant();
	while ( rsltVariants.hasMoreElements() )
	{
		Dispatch objProcess = rsltVariants.Next().toDispatch();
		String strCaption = Dispatch.get(objProcess, wszName).toString();

		System.out.println("Process Name: " + strCaption);
	}
	
//	Variant variant[] = new Variant[10];
//	
//	while ( instances.hasMoreElements() )
//	{
//	Dispatch objProcess = enumProcs.Next().toDispatch();
//	String strCaption = Dispatch.get(objProcess, "Caption").toString();
//
//	System.out.println("Process Name: " + strCaption);
//	}
//
//	
	
//	return variant;
				return null;
	}
*/

