
package pfe.migration.client.pre.app;

import java.awt.Label;
import java.awt.event.KeyEvent;
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
	private Img mc = null;
	
	public EnterIpView (ChangeFirstView listenerApp)
	{
		this.listener = listenerApp;
		
		text = new JTextField("", 6);
		text.addKeyListener(this);
		
		mc = new Img(new ImageIcon("utils/logo.png").getImage());
		
		this.add(mc);
		this.add(new Label("enter application server ip : "));
		this.add(text);

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
	
}
