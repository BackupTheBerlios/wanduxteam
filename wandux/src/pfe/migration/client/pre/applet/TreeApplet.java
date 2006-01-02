package pfe.migration.client.pre.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.rmi.RemoteException;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;

public class TreeApplet extends Applet implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String applicationServerIp = "";
	private String currentHostname = "";
	private ClientEjb ce = null;
	private ComputerInformation currentCI = null;
	
	private int step = 0;
	// private FileSystemModel fileSystemModel = null; //

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

		System.out.println("-"+getParameter("currentHostname"));
		System.out.println("applicationServer-"+getParameter("applicationServer"));
		System.out.println("-"+getParameter("background-color"));
		
		if (makeConnection() == true)
		{
			System.out.println("connection etablie ...");
			try {
				System.out.println(currentHostname);
				System.out.println(ce);
				System.out.println("bean:"+ce.getBean());
				
				currentCI = ce.getBean().getCi(currentHostname);
			} catch (RemoteException e) { e.printStackTrace(); }
		}
		if (this.ce.IsConnected() == false)
			return ;
		
		// TODO voir pour mettre une plusieurs etapes -> loading data , le tree , connection echoue , information envoye recommencer??
	    
//		FileSystemModel fileSystemModel = new FileSystemModel(new File("c:/"));

		etapeBienvenue();
//		etapeTreeBrowser();
        
//		add(new Label("bienvenue - telechargement de donnees"), BorderLayout.CENTER);

		invalidate();
		validate();
	}
	
//	public void etapeGetFileSystem()
//	{
//        add(new Label("bienvenue - telechargement de donnees"), BorderLayout.CENTER);
//		invalidate();
//		validate();
//		
//		FileSystemModel fsm = new FileSystemModel(new File("c:/")); // 
//		this.fileSystemModel = fsm;
//	}
	
	public void fillFileListOfCIToServer()
	{
		try {
			this.ce.getBean().putCi(this.currentCI);
		} catch (RemoteException e) { e.printStackTrace(); }
	}
	
	public void etapeBienvenue()
	{
		Button bgo = new Button("go");
		bgo.addActionListener(this);
		add(new Label("bienvenue"), BorderLayout.CENTER);
		add(bgo, BorderLayout.SOUTH);
		invalidate();
		validate();
	}

	public void etapeTreeBrowser()
	{
		
	    // removeAll();

		final JTree fileTree = new JTree(this.currentCI.getFileSystemModel());
        JScrollPane jsp  = new JScrollPane(fileTree);
        addAdjustmentListener(jsp);
        
        add(jsp, BorderLayout.CENTER);

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
//        try
//        {
//           Properties jndiProps = new Properties() ;
//           String myServer = this.getCodeBase().getHost ();
//           jndiProps.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory" ) ;
//           //jndiProps.setProperty("java.naming.provider.url", myServer + ":1099" ) ;
//           jndiProps.setProperty("java.naming.provider.url", "127.0.0.1" + ":1099" ) ;
//           jndiProps.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" ) ;
//           WanduxEjbHome home = (WanduxEjbHome)PortableRemoteObject.narrow( new InitialContext(jndiProps).lookup( "WanduxEjb" ),
//        		   WanduxEjbHome.class) ;
//           WanduxEjb remote = home.create() ;
//           add(new Label( remote.sayMe() ), BorderLayout.CENTER) ;
//        }
//        catch ( SecurityException se )
//        {
//           se.printStackTrace ();
//        }
//        catch( Exception ex )
//        {
//           System.err.println( "APPLET" );
//           ex.printStackTrace();
//        }
  		
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
  		
  		//return ce.IsConnected();
        return true;
	}

    public static Color getColor(String str) {
  	  Integer intval = Integer.decode(str);

  	  if (intval == null) 
  		  return Color.white;
  		
  	  int i = intval.intValue();
  	  return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

	public void actionPerformed(ActionEvent arg0) {
		switch (step)
		{
		case 0:
			removeAll();
			etapeTreeBrowser();
			step++;
			break ;
		default:
				
		}
	}
}
