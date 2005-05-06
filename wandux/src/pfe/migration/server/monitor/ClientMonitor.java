/*
 * Created on 6 mai 2005
 * 
 * @author dupadmin
 */
package pfe.migration.server.monitor;

import java.util.Hashtable;

public class ClientMonitor implements ClientMonitorListener
{
	Hashtable list = null;
	
	public ClientMonitor()
	{
		list = new Hashtable();
	}
	
	public ClientMonitorListener getListener()
	{
		return this;
	}
	
	// -- listener client --
	public void CIProgress(String ip, int step, int percent)
	{
		CiList m = (CiList)list.get(ip);
		if  (step != m.getStep())
			m.incStep();
		m.setPercent(percent);
	}

	public void CINewIp(String ip)
	{
		list.put(ip, new CiList(ip));		
	}

	public CiList getCI(String ip)
	{
		return (CiList)list.get(ip);
	}
	
	public int getCiListSize()
	{
		return list.size();
	}
}
