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
		l.add(new Cellule(ip));
	}
	
	public void modify(String ip, int percent, boolean setpup)
	{
		Iterator it = l.iterator();
		while (it.hasNext())
		{
			Cellule c = (Cellule) it.next();
			if (ip.equals(c.getIp()) == true)
			{
				c.setPercent(percent);
				if (setpup == true)
					c.incStep();
				return ;
			}
		}
		System.out.println("[PB] ip non reference");
	}
	
	class Cellule
	{
		private String ip = "";
		private int percent = 0;
		private  int step = 0; 
		
		public Cellule(String ip)
		{
			this.ip = ip;
		}
	
		public String getIp()
		{
			return this.ip;
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
