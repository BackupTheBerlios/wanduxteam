/*
 * Created on 19 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InformationUser {

	private List l = null;
	
	public InformationUser()
	{
		super();
		l = new ArrayList();
	}

	public void add(Object o)
	{
		this.l.add(o);
	}
	
	public List get()
	{
		return this.l;
	}
	
}
