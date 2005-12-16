/*
 * Created on 3 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;
import pfe.migration.client.pre.system.KeyVal;
import pfe.migration.client.pre.app.tools.HashOperation;

/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgramsLister {
	public Hashtable ParseExtensions()
	{
		KeyVal kvpl = new KeyVal();
		RegistryKey aKey = null;
		String currentkey = ".init";
		String program = null;
		Hashtable pghash = new Hashtable();	
		
		ArrayList[] extensionlist = new ArrayList[300];

		ArrayList getlist = new ArrayList();
		int nbprograms = 0;
		boolean pgexists = false;
		
		System.out.println("__programs configuration");
		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT.openSubKey("");
			for (int i = 1; i < aKey.getNumberSubkeys(); i++)
			{
				if (!currentkey.substring(0, 1).equals("."))
				{
					System.out.println("\nextension count: "+ i);
					break;
				}
					currentkey = kvpl.getNextKey(aKey, i);
					
					program = kvpl.getCommandFromExtension(currentkey);
					if (program != null)
					{
						Vector v = new Vector(pghash.keySet());
						Iterator it = v.iterator();

						while (it.hasNext())
						{
							String element = (String)it.next();
							if (element.equals(program))
							{
								getlist = (ArrayList)pghash.get(element);
								getlist.add(currentkey);
								pgexists = true;
								break;
							}
							else
								pgexists = false;
						}
						if (pgexists == false)
						{
							extensionlist[nbprograms] = new ArrayList();
							extensionlist[nbprograms].add(currentkey);
							pghash.put(program, extensionlist[nbprograms]);
							nbprograms++;
						}
						else
						{
							Vector v2 = new Vector(pghash.keySet());
							Iterator it2 = v2.iterator();
							int c2 = v.indexOf(program);
							extensionlist[c2].add(currentkey);
						}
					}
					// TODO CB6 store results under a hashtable without redundancy
					// propose to user to add a list of extension/program association into the database
					// if they are not already referenced > add this to log file and try to get it on
					// the net
					// Other example : ask user if he would like to
					// "save all files associated with program "RegSnap.exe""
			}
		} catch (NoSuchKeyException e) {
			e.printStackTrace();
		} catch (RegistryException e) {
			e.printStackTrace();
		}
		return pghash;
	}
	public void ShowProgramExtensions(Hashtable pghash)
	{
		Vector v = new Vector(pghash.keySet());
		Collections.sort(v);
		Iterator it = v.iterator();
		ArrayList getlist = new ArrayList();

		while (it.hasNext())
		{
			String element = (String)it.next();
			getlist = (ArrayList)pghash.get(element);
			System.out.println(">>> "+element+" <<<\n");
			for (int i = 0; i < getlist.size(); i++)
			{
				if (i == (getlist.size()-1))
					System.out.print(getlist.get(i));
				else
					System.out.print(getlist.get(i) + ", ");
			}
			System.out.println("");
			System.out.println("");
		}
	}
	public String[] Programexists(Hashtable pghash, String[] programs, int nb)
	{
		Vector v = new Vector(pghash.keySet());
		Collections.sort(v);
		Iterator it = v.iterator();
		String pathes[] = new String[programs.length];
		boolean b = false;
		int j = 0;

		while (it.hasNext())
		{
			String element = (String)it.next();
			for (int i = 0; i < nb; i++)
			{
				b = element.matches("(?i).*" + programs[i] + ".*");
				if (b == true)
				{
					pathes[i] = element;
					break;
				}
			}
		}
		return pathes;
	}
}