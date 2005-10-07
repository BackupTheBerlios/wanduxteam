package pfe.migration.server.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pfe.migration.client.network.ComputerInformation;

/**
 * @author dupadmin
 *
 * Created on 6 mai 2005
 */
public class CiList
{
	Map m = null;
	
	public CiList ()
	{
		m = new HashMap();
	}
	
	public void add(String ip)
	{
		m.put(ip, null);
	}

	public void fill(ComputerInformation ci)
	{
		m.put(ci.getIp(), ci);
	}

	public ComputerInformation get(String ip)
	{
		return (ComputerInformation) m.get(ip);
	}

	public void remove(String ip)
	{
		m.remove(ip);
	}
	
	public List getListIp()
	{
		List l = new ArrayList();
		
		Set ensemble = m.entrySet();
		Iterator i = ensemble.iterator();
		while (i.hasNext())
		{
			Map.Entry e = (Map.Entry) i.next(); 
			l.add(e.getKey());
		}
		return l;
	}
	
	
}
