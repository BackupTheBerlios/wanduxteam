/*
 * Created on 6 mai 2005
 * 
 * @author dupadmin
 */
package pfe.migration.server.monitor;


public class ClientMonitor implements ClientMonitorListener
{
	CiList cl = null;
	
	public ClientMonitor()
	{
		cl = new CiList();
	}
	
	public ClientMonitorListener getListener()
	{
		return this;
	}
	
	// -- listener client --
	public void CIProgress(String ip, int step, int percent)
	{
	}

	public void CINewIp(String ip)
	{
		cl.add(ip);
	}
	
	public String pouet()
	{
		return "pouet";
	}

//	public CiList getCI(String ip)
//	{
//		return (CiList)list.get(ip);
//	}
//	
//	public int getCiListSize()
//	{
//		return list.size();
//	}
}
