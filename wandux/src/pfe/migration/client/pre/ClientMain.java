/*
 * Created on 15 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre;

import pfe.migration.client.network.ClientEjb;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClientMain {

	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: launchClient.bat ip_serveur_ejb");
			System.exit (0);
		}
		ClientEjb ce = new ClientEjb (args[0]);
		ce.EjbConnect();
		System.out.println("main : " + ce.giveMsg()[0]);
		System.out.println("main : " + ce.giveMsg()[1]);
		ce.EjbClose();
		while (true) ;
	}

}
