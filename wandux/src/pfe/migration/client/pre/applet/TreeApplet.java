package pfe.migration.client.pre.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.applet.tree.CheckTreeManager;

public class TreeApplet extends Applet implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	private String applicationServerIp = "";
	private String currentHostname = "";
	private ClientEjb ce = null;
	private ComputerInformation currentCI = null;
	
	private int step = 0;
	
	public static List finalList = new ArrayList(); 

	public void init()
	{
		try {
		    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
		    e.printStackTrace();
		}

		this.currentHostname = getParameter("currentHostname");
		this.applicationServerIp = getParameter("applicationServer");
		
		setLayout(new BorderLayout());
		setBackground(getColor(getParameter("background-color")));

//		System.out.println("-"+getParameter("currentHostname"));
//		System.out.println("applicationServer-"+getParameter("applicationServer"));
//		System.out.println("-"+getParameter("background-color"));
		
		if (makeConnection() == true)
		{
			System.out.println("connection etablie ...");
			try {
//				System.out.println(currentHostname);
//				System.out.println(ce);
//				System.out.println("bean:"+ce.getBean());
				
				currentCI = ce.getBean().getCi(currentHostname);
			} catch (RemoteException e) { e.printStackTrace(); }
		}
		if (this.ce.IsConnected() == false)
			return ;
		
		etapeBienvenue();
        
		invalidate();
		validate();
	}
	
	public void fillFileListOfCIToServer()
	{
		try {
			this.ce.getBean().putCi(this.currentCI);
		} catch (RemoteException e) { e.printStackTrace(); }
	}
	
	public void etapeBienvenue()
	{ // TODO utilise les trheads ... ou sinon matter si on a cree la listOfFile avec l hostname
		ButtonImageCanvas bic = null;
		try {
			bic = new ButtonImageCanvas(getImage(new URL(getCodeBase()+"/img/fleche_suivant.gif")));
		} catch (MalformedURLException e) { e.printStackTrace(); }
		bic.addMouseListener(this);
		
		add(new Label("wait to get le file system of the machine", Label.CENTER), BorderLayout.CENTER);
		add(bic, BorderLayout.EAST);
		invalidate();
		validate();
	}

	public void etapeTreeBrowser()
	{
		ButtonImageCanvas bic = null;
		try {
			bic = new ButtonImageCanvas(getImage(new URL(getCodeBase()+"/img/fleche_suivant.gif")));
		} catch (MalformedURLException e) { e.printStackTrace(); }
		bic.addMouseListener(this);
		
		final JTree fileTree = new JTree(this.currentCI.getFileSystemModel());
		new CheckTreeManager(fileTree);
        JScrollPane jsp  = new JScrollPane(fileTree);
        addAdjustmentListener(jsp);
        
        add(jsp, BorderLayout.CENTER);
		add(bic, BorderLayout.EAST);
        
		invalidate();
		validate();
	}
	public void etapeFin()
	{
		ButtonImageCanvas bic = null;
		try {
			bic = new ButtonImageCanvas(getImage(new URL(getCodeBase()+"/img/fleche_precedent.gif")));
		} catch (MalformedURLException e) { e.printStackTrace(); }
		bic.addMouseListener(this);

        add(new Label("the selected files will be saved", Label.CENTER), BorderLayout.CENTER);
        add(bic, BorderLayout.EAST);
//        add(bgo, BorderLayout.EAST);

		invalidate();
		validate();
	}
	
	private void addAdjustmentListener(JScrollPane jsp)
	{
		jsp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{	public void adjustmentValueChanged(AdjustmentEvent a)
			{
				repaint();
			}
		});
		jsp.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{	public void adjustmentValueChanged(AdjustmentEvent a)
			{
				repaint();
			}
		});
	}
	
  	private boolean makeConnection()
	{
  		
  		if (this.ce == null)
  		{
  			System.out.println(applicationServerIp);
  			this.ce = new ClientEjb(applicationServerIp);
			this.ce.EjbConnect();
  		}
  		
//
//  	// gestion de la mauvaise url (ca marche)
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
        //return true;
	}

    public static Color getColor(String str) {
  	  Integer intval = Integer.decode(str);

  	  if (intval == null) 
  		  return Color.white;
  		
  	  int i = intval.intValue();
  	  return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

	public void actionPerformed(ActionEvent arg0) {

	}

	// -- listener mouse --
	public void mouseClicked(MouseEvent arg0) { }

	public void mouseEntered(MouseEvent arg0) { }

	public void mouseExited(MouseEvent arg0) { }

	public void mousePressed(MouseEvent arg0) { }

	public void mouseReleased(MouseEvent arg0)
	{
		switch (step)
		{
		case 0:
			removeAll();
			etapeTreeBrowser();
			step++;
			break ;
		case 1:
			removeAll();
			etapeFin();
			try {
				this.ce.getBean().putFileList(this.currentHostname, this.finalList);
			} catch (RemoteException e) { e.printStackTrace(); }
			step++;
			break ;
		default:
			removeAll();
			etapeBienvenue();
			step = 0;
			break;
		}
	}
}
