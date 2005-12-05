package pfe.migration.client.pre.app.tools;

import java.util.*;

public class HashOperation {
	public void Sort(Hashtable ht)
	{
		Vector v = new Vector(ht.keySet());
		Collections.sort(v);
		Iterator it = v.iterator();
		while (it.hasNext())
		{
			String element = (String)it.next();
			System.out.println(element + " " + (String)ht.get(element));
		}
	}

	/**
	 * Testing section
	 * @param args
	 */
	public static void main(String args[])
	{
		String[] array1 = {"C","B","A"};
		String[] array2 = {"1","1","3"};
		Hashtable ht = new Hashtable();
		
		ht.put(array1[0], array2[0]);
		ht.put(array1[1], array2[1]);
		ht.put(array1[2], array2[2]);
		
		HashOperation ho = new HashOperation();
		ho.Sort(ht);
	}
}

