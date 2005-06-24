/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FirstStep extends JPanel
{
	private JTextField text = null;
	
	public FirstStep()
	{
		text = new JTextField("127.0.0.1", 6);
		this.add(new JLabel("Bienvenue"));
		this.add(new Label("enter l'addresse du serveur d'application : "));
		this.add(text);
		this.text.requestFocus();
	}
	
	public FirstStep(String ip)
	{
		if (ip == "")
			ip = "127.0.0.1";
		text = new JTextField(ip, 6);
		this.add(new JLabel("Bienvenue"));
		this.add(new Label("enter l'addresse du serveur d'application : "));
		this.add(text);
		this.text.requestFocus();
	}

	public String getIp()
	{
		return text.getText();
	}

}
