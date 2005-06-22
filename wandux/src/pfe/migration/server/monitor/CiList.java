package pfe.migration.server.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dupadmin
 *
 * Created on 6 mai 2005
 */
public class CiList
{
	List l = null;
	
	public CiList ()
	{
		l = new ArrayList();
	}
	
	public void add(String ip)
	{
		System.out.println("cilist add : " + ip);
		l.add(new Cellule(ip));
	}
	
	public void modify(String ip, int percent, boolean setpup)
	{
		Iterator it = l.iterator();
		while (it.hasNext())
		{
			Cellule c = (Cellule) it.next();
			if (ip.equals(c.getMac()) == true)
			{
				c.setPercent(percent);
				if (setpup == true)
					c.incStep();
				return ;
			}
		}
		System.out.println("[PB!!] mac non reference");
	}
	
	public String getAllMac()
	{
		String allMac = " - ";
		Iterator it = l.iterator();
		while (it.hasNext())
		{
			Cellule c = (Cellule) it.next();
			allMac += c.getMac() + " - ";
		}
		return allMac;
	}
	
	class Cellule
	{
//		private String ip = "";
//		private String ipMac  = ""; // choisir qqchose		
		private String mac = "";
		private int percent = 0;
		private  int step = 0; 
		
		public Cellule(String mac)
		{
			this.mac = mac;
		}
	
		public String getMac()
		{
			return this.mac;
		}
		
		public void setPercent(int percent)
		{
			this.percent = percent;
		}
	
		public int getPercent()
		{
			return this.percent;
		}
		
		public void incStep()
		{
			this.step++;
		}
	
		public int getStep()
		{
			return this.step;
		}
	}
}
