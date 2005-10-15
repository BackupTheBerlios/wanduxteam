/*
 * Created on 28 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre.app.apparence;

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
	private static final long serialVersionUID = 1L;
	protected Image img;
	protected Image pushedImg;
	protected boolean state = true;
	
	boolean rightStep = true;
	WanduxAppListener wal = null;
	
	public ButtonImageCanvas(Image img, Image pushedImg, WanduxAppListener wal, boolean rightTrueLeftfalse)
	{
		super(img);
		this.img = img;
		this.pushedImg = pushedImg;
		this.wal = wal;
		this.addMouseListener(this);
		this.rightStep = rightTrueLeftfalse;
	}
	
	public ButtonImageCanvas(Image img, Image pushedImg)
	{
		super(img);
		this.img = img;
		this.pushedImg = pushedImg;
		this.addMouseListener(this);
	}


  public void paint(Graphics g)
  {
  	if(state == true)
  		g.drawImage(img, 0, 0, this);
  	else
  		g.drawImage(pushedImg, 0, 0, this);
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
		//System.out.println("bouton enfonce");
	    state = false;
	    repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
		if (wal != null)
		{
			if (this.rightStep == true)
				this.wal.incStep();
			else
				this.wal.decStep();
		}
		//System.out.println("bouton lache");
	    state = true;
	    repaint();
	}

}
