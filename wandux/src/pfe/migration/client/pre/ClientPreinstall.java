package pfe.migration.client.pre;

import java.awt.List;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import pfe.migration.client.network.ClientEjb;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class ClientPreinstall extends javax.swing.JPanel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private List list;

	private ClientEjb ce = null;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ClientPreinstall());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.show();
	}
	
	public ClientPreinstall() {
		super();
		ce = new ClientEjb("127.0.0.1");
		ce.EjbConnect();
		initGUI();
		fillJList(getUserList());
		ce.EjbClose();

	}
	
	private void fillJList(String[] userList)
	{
		for (int i = 0; i < userList.length ; i++)
		{
			list.add(userList[i]);
		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(298, 174));
			{
				ListModel jListModel = new DefaultComboBoxModel(new String[] {
						"voici les noms", "" });
				list = new List();
				this.add(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  String [] getUserList()
	{	
		
		String lst [] = null;
		File dir = new File("C:\\Documents and Settings");
	    
	    String[] children = dir.list();
	    if (children == null) {
	        // Either dir does not exist or is not a directory
	    } else {
	    	lst = new String[children.length];
	    	for (int i=0; i<children.length; i++)
	    	{
	            // Get filename of file or directory
	            lst[i] = children[i];
	    	}
    	}
	    return lst;
	}

}
