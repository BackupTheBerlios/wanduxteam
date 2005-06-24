/*
 * Created on 22 juil. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre.app.apparence;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

/**
 * @author Interneto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImageCanvas  extends Canvas {

	protected Image img = null;

	public ImageCanvas(Image img)
	{
		MediaTracker medTreck = new MediaTracker(this);
		this.img = img;
    medTreck.addImage( img, 1 );
    try{
     medTreck.waitForID( 1 );
    } catch (InterruptedException e) { System.err.println (e); }
		this.setSize(img.getWidth(this), img.getHeight(this));
	}

  public void paint(Graphics g)
  {
		super.paint(g);
		g.drawImage(this.img, 0, 0, this);
	}

  public void update(Graphics g)
  {
  	super.update(g);
  }

}
