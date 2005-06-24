/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pfe.migration.client.pre.app.Img;
import pfe.migration.client.pre.app.apparence.steps.FirstStep;
import pfe.migration.client.pre.app.apparence.steps.LastStep;
import pfe.migration.client.pre.app.apparence.steps.ProgressBarStep;
import pfe.migration.client.pre.app.apparence.steps.UserNetStep;
/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WanduxApp implements WanduxAppListener
{
	private JPanel jContentPane = null;
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="75,55"
	private JPanel middle = null;

	private String applicationServer = "";

	private int currentStep = 0;
	
	public WanduxApp()
	{		
		jFrame = getJFrame();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		jFrame.getContentPane().add(initPanel(), BorderLayout.NORTH);
		jFrame.getContentPane().add(initBoutons(), BorderLayout.SOUTH);
		middle = new FirstStep();
  		middle.setBackground(Color.white);
		jFrame.getContentPane().add(middle, BorderLayout.CENTER);
		
		jFrame.getContentPane().setBackground(Color.white);
		jFrame.show();
	}

	private JPanel initBoutons()
	{
		JPanel boutons;
		boutons = new JPanel();
		boutons.setBackground(Color.white);

		boutons.add(new ButtonImageCanvas(
				new ImageIcon("utils/pre/gauche_gris.PNG").getImage() ,
				new ImageIcon("utils/pre/gauche_vert.PNG").getImage(),
				this, false));
		

		boutons.add(new ButtonImageCanvas(
				new ImageIcon("utils/pre/droite_gris.PNG").getImage() ,
				new ImageIcon("utils/pre/droite_vert.PNG").getImage(),
				this, true));

		return boutons;
	}
	
	private JPanel initPanel()
	{
		JPanel banner = new JPanel();
		banner.setBackground(Color.white);
		banner.add(new Img(new ImageIcon("utils/logo.png").getImage()));
		return banner;
	}
	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJContentPane()
	{
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */    
	private JFrame getJFrame()
	{
		if (jFrame == null)
		{
			jFrame = new JFrame();
			jFrame.setContentPane(getJContentPane());
			jFrame.setSize(554, 302);
			jFrame.setTitle("Wandux");
		}
		return jFrame;
	}
	
  	public static void main(String[] args)
	{
		new WanduxApp();
	}
  	
  	// -- les differents cas -- //
  	public void moveFirstStep()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new FirstStep(this.applicationServer);
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}

  	public void moveStepUserAndNetwork()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new UserNetStep();
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}
  	
  	public void moveProgressBar()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new ProgressBarStep();
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}
  	
  	public void moveLastStep()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new LastStep();
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}
  	
  	// -- le listener -- //
  	public void incStep()
	{
		this.currentStep++;
  		System.out.println(this.currentStep);
  		switch (this.currentStep)
		{
  			case 1:
  				this.applicationServer = ((FirstStep)middle).getIp();
  				moveStepUserAndNetwork();
  				break ;
  			case 2:
  				moveProgressBar();
  				break ;
  			default:
  		  		this.currentStep = 3;
  				moveLastStep();
  				System.out.println("c est fini inc");
		}
	}

  	public void decStep()
	{
		this.currentStep--;
  		System.out.println(this.currentStep);
  		switch (this.currentStep)
		{
  			case 1:
  				moveStepUserAndNetwork();
  				break ;
  			case 2:
  				moveProgressBar();
  				break ;
  			default:
  		  		this.currentStep = 0;
  				moveFirstStep();
  				System.out.println("c est fini dec");
		}
	}

}
