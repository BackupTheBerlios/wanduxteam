/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.pre.app;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;


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
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Img extends Canvas
{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	
  public Img(Image img)
  {
		MediaTracker medTreck = new MediaTracker(this);
		this.image = img;
    medTreck.addImage( img, 1 );
    try{
     medTreck.waitForID( 1 );
    } catch (InterruptedException e) { System.err.println (e); }
		this.setSize(img.getWidth(this), img.getHeight(this));
  }


  public void paint(Graphics g)
  {
		super.paint(g);
		g.drawImage(this.image, 0, 0, this);
	}

  public void update(Graphics g)
  {
  	super.update(g);
  }
  
private void initGUI() {
	try {
		{
			this.setPreferredSize(new java.awt.Dimension(296, 99));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}


/**
 * @param dimension
 */
private void setPreferredSize(Dimension dimension) {
	// TODO Auto-generated method stub
	
}
}
