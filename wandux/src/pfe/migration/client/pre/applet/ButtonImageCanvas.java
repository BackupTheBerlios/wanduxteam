/*
 * Created on 28 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre.applet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.accessibility.Accessible;
import javax.management.j2ee.statistics.JavaMailStats;

import pfe.migration.client.pre.app.apparence.ImageCanvas;

/**
 * @author Interneto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ButtonImageCanvas extends ImageCanvas implements MouseListener { 

	protected Image img;
	protected boolean state = true;
	
	public ButtonImageCanvas(Image img)
	{
		super(img);
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
