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

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.app.Img;
import pfe.migration.client.pre.app.apparence.steps.FirstStep;
import pfe.migration.client.pre.app.apparence.steps.JTreeWithCheckbox;
import pfe.migration.client.pre.app.apparence.steps.LastStep;
import pfe.migration.client.pre.app.apparence.steps.PrintFromTreePath;
import pfe.migration.client.pre.app.apparence.steps.ProgressBarStep;
import pfe.migration.client.pre.app.apparence.steps.UserNetStep;
import pfe.migration.client.pre.app.tools.CopyDataOnFileServer;
import pfe.migration.client.pre.app.tools.WorkQueue;
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
	private String[] tp = null;
	
	private int currentStep = 0;

	// -- les variables necessaires -- //
	private ComputerInformation ci = null;
	private ClientEjb ce = null;
	private WorkQueue wq = null;
	
	// -- initialisation -- //
	public WanduxApp()
	{
		wq = new WorkQueue(10); 
		
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
	
	// -- visual editor -- //
	private JPanel getJContentPane()
	{
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}
	
	private JFrame getJFrame()
	{
		if (jFrame == null)
		{
			jFrame = new JFrame();
			jFrame.setContentPane(getJContentPane());
			jFrame.setSize(450, 580);
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

  	private boolean makeConnection()
	{
  		if (this.ce == null)
  		{
  			this.ce = new ClientEjb(applicationServer);
			this.ce.EjbConnect();
  		}
//  		// gestion de la mauvaise url (ca marche)

//		else if (ce.IsConnected())
//		{
//			this.ce.EjbClose();
//			this.ce = new ClientEjb(applicationServer);
//			this.ce.EjbConnect();
//		}
//		else
//		{
//			this.ce = new ClientEjb(applicationServer);
//			this.ce.EjbConnect();
//		}
  		return ce.IsConnected();
	}
  	
  	public void moveStepUserAndNetwork()
  	{
  		if (makeConnection() == false)
  		{
  			this.currentStep--;
  			return ;
  		}
  		jFrame.getContentPane().remove(middle);
 		middle = new UserNetStep(this.ce);
 		if (this.ci == null)
 			this.ci = ((UserNetStep)middle).getComputerInformation();
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}
  	
  	public void moveProgressBar()
  	{
		wq.execute(new CopyDataOnFileServer(this.tp, ci.getMac()));
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

  	public void moveJtree()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new JTreeWithCheckbox();
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}

  	public void movePrintJtree()
  	{
  		jFrame.getContentPane().remove(middle);
  		middle = new PrintFromTreePath(this.tp);
  		middle.setBackground(Color.white);
  		jFrame.getContentPane().add(middle);
  		jFrame.getContentPane().invalidate();
  		jFrame.getContentPane().validate();
  	}
  	
  	// -- le listener -- //
  	public void incStep()
	{
		this.currentStep++;
  		switch (this.currentStep)
		{
  			case 1:
  				this.applicationServer = ((FirstStep)middle).getIp();
  				moveStepUserAndNetwork();
  				break ;
  			case 2:
  				moveJtree();
  				break ;
  			case 3:
  				this.tp = ((JTreeWithCheckbox)middle).getSelectionnedPaths();
  				movePrintJtree();
  				break ;
  			case 4:
  				moveProgressBar();
  				break ;
  			default:
  		  		this.currentStep = 5;
  				moveLastStep();
		}
	}

  	public void decStep()
	{
		this.currentStep--;
  		switch (this.currentStep)
		{
  			case 1:
  				moveStepUserAndNetwork();
  				break ;
  			case 2:
  				moveJtree();
  				break ;
  			case 3:
  				movePrintJtree();
  				break ;
			case 4:
				moveProgressBar();
				break ;
  			default:
  		  		this.currentStep = 0;
  				moveFirstStep();
		}
	}

}
