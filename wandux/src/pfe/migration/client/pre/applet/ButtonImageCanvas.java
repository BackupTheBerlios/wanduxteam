/*
 * Created on 28 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre.applet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Interneto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ButtonImageCanvas extends ImageCanvas implements MouseListener { 

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5684584100674354754L;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */

	protected boolean state = true;
	
	public ButtonImageCanvas(Image img)
	{
		super(img);
		this.addMouseListener(this);
	}

  public void paint(Graphics g)
  {
 		g.drawImage(img, 0, 0, this);
  }

	public void update(Graphics g)
	{	
		paint(g);
	}
	
// -- MouseListener -----------------------------------------------------------------
	public void mouseClicked(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e)
	{
    	repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
    	repaint();
	}

}
