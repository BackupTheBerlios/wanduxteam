/*
 * Created on 6 mai 2005
 * 
 * @author dupadmin
 */
package pfe.migration.server.monitor;


public class ClientMonitor implements ClientMonitorListener
{
	CiList cil = null;
	
	public ClientMonitor(CiList cil)
	{
		this.cil = cil;
	}

	public String affichage()
	{
		if (this.cil == null)
			return "null";
		return this.cil.getAllMac();
	}
	
	public String pouet()
	{
		return "pouet";
	}

	// -- listener client --
	public ClientMonitorListener getListener()
	{
		return this;
	}

	public void CIProgress(String ip, int step, int percent)
	{
	}

	public void CINewIp(String ip)
	{
//		cl.add(ip);
	}
	
}
