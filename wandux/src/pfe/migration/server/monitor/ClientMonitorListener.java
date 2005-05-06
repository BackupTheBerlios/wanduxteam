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
public interface ClientMonitorListener
{
	public void CINewIp(String ip);
	public void CIProgress(String ip, int step, int percent);
	public CiList getCI(String ip);
}
