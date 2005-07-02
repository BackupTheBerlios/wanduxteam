
package pfe.migration.client.pre.app;

import java.awt.Label;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;


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
/**
 * @author dup
 *
 * Created on 14 mars 2005
 */
public class EnterIpView extends JPanel implements KeyListener
{
	private ChangeFirstView listener = null;
	private JTextField text = null;
	private JButton jButton_ok;
	private Img mc = null;
	
	public EnterIpView (ChangeFirstView listenerApp)
	{
		this.listener = listenerApp;

		mc = new Img(new ImageIcon("utils/logo.png").getImage());
		
		this.add(mc);
		this.add(new Label("enter application server ip : "));
		{
			text = new JTextField("10.247.0.248", 6); // TODO a changer
			this.add(text);
			text.addKeyListener(this);

		}
		{
			jButton_ok = new JButton();
			this.add(jButton_ok);
			jButton_ok.setText("OK");
			jButton_ok.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					jButton_okMouseReleased(evt);
				}
			});
		}
		this.text.requestFocus();

	}
	
	// -- KeyListener -- 
	public void keyPressed(KeyEvent arg0) { }

	public void keyReleased(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			String ret = text.getText();
			if (ret.length() != 0)
			{
				listener.doChange(ret);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) { }
	
	private void initGUI() {
		try {
			{
				this.setPreferredSize(new java.awt.Dimension(432, 120));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated method for setting the popup menu for a component
	*/
	private void setComponentPopupMenu(
		final java.awt.Component parent,
		final javax.swing.JPopupMenu menu) {
		parent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}
	
	private void jButton_okKeyPressed(KeyEvent evt) {
		System.out.println("jButton_ok.keyPressed, event=" + evt);
		//TODO add your code for jButton_ok.keyPressed
		String ret = text.getText();
		if (ret.length() != 0)
		{
			listener.doChange(ret);
		}
	}
	
	private void jButton_okMouseReleased(MouseEvent evt) {
		//System.out.println("jButton_ok.mouseReleased, event=" + evt);
		//TODO add your code for jButton_ok.mouseReleased
		String ret = text.getText();
		if (ret.length() != 0)
		{
			listener.doChange(ret);
		}
	}

}
