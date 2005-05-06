/*
 * Created on 6 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.monitor;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CiList
{
	private String ip = "";
	private int percent = 0;
	private  int step = 0; 
	
	public CiList(String ip)
	{
		this.ip = ip;
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
