package pfe.migration.client.pre.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.pre.system.FileSystemModel;
import pfe.migration.client.pre.system.KeyVal;
import pfe.migration.server.ejb.WanduxEjbBean;
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
public class SwingApp extends javax.swing.JFrame implements ActionListener, KeyListener, ChangeFirstView
{
	{
		//Set Look & Feel
		try {
		        javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
		        e.printStackTrace();
		}
	}
	
	// -- menu bar --
	private JMenuBar 	jMenuBar;

	private JMenu 		jMenuFile;
	private JMenuItem 	newFileMenuItem;
	private JMenuItem 	openFileMenuItem;
	private JMenuItem 	saveMenuItem;
	private JMenuItem 	saveAsMenuItem;
	private JMenuItem 	closeFileMenuItem;
	private JSeparator 	jSeparatorFile;
	private JMenuItem 	exitMenuItem;
	
	private JMenu 		jMenuEdit;
	private JMenuItem 	cutMenuItem;
	private JMenuItem 	copyMenuItem;
	private JMenuItem 	pasteMenuItem;
	private JSeparator 	jSeparatorEdit;
	private JMenuItem 	deleteMenuItem;

	private JMenu 		jMenuHelp;
	private JMenuItem 	helpMenuItem;
	// -- menu bar -- // fin

	// -- les differents tabs -- 
	private JTabbedPane tabPrincipale;
	
	// -- tab LocalFs --
	private JSplitPane 	jSplitPaneLocalFs;

	// -- tab RegistryTester --
	private JSplitPane 	jSplitPaneRegistryTester;
	private JPanel 		jPanel1;
	private JTextField 	Jtf = null;
	private List 		jsearchres = null;
	private JButton 	jbsearch = null;
	private KeyVal 		kv = new KeyVal();
	private List 		components;

	// -- tab FileIndex --
	private JPanel 		FileIndex;
	
	// -- tab userIndex --
	private JPanel 		userIndex;
	private String 		allNameList = "user list account:\r\n\r\n";
	private JTextArea 	nameList = null;

	// -- les differents tabs -- // fin

	// -- partie touchant le reseaux -- //
	private EnterIpView jPaneIp = null;
	private ClientEjb 	ce = null;
	
	public static void main(String[] args)
	{
		SwingApp inst = new SwingApp();
		inst.setVisible(true);
		inst.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		inst.show();
	}
	
	public SwingApp()
	{
		super();
		this.addWindowListener(new ExitListener());
		initIP();
//		initGUI();
		
		// bean.putComputerInformation();
	}
	
	private void initIP() // pas touche !! >> DUP
	{
		setSize(600, 400);
		jPaneIp = new EnterIpView(this);
		jPaneIp.setBackground(Color.WHITE);
		jPaneIp.addKeyListener(this);
		jPaneIp.setVisible(true);
		this.getContentPane().add(jPaneIp, BorderLayout.CENTER);
	}
	
	private void initGUI()
	{
		Jtf = new JTextField("", 6);
		Jtf.addKeyListener(this);
		jsearchres = new List();
		jsearchres.setFocusable(false);
		jbsearch = new JButton("Search");
		
		try {
			setSize(600, 400);
			{
				tabPrincipale = new JTabbedPane();
				{
					GuiComponents();
				}
				WanduxMenuBar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main graphical components
	 *
	 */
	public void GuiComponents() {
		this.getContentPane().add(tabPrincipale, BorderLayout.CENTER);
		{
			SystemInfos();
			RegistryTester();
			FileTree();
			
			// TODO Bug making independant method
			{
				FileIndex = new JPanel();
				JButton fileindexbutton = new JButton("Generate file index");
				tabPrincipale.addTab("File indexer", null, FileIndex, null);
			}

		}
	}

	/**
	 * Graphical component : find programs by extension
	 *
	 */
	public void RegistryTester()
	{
		jPanel1 = new JPanel();
		jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
		Jtf.setMaximumSize(new Dimension(400, 18));
		//Jtf.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		jPanel1.add(Jtf);
		jbsearch.addActionListener(this);
		jPanel1.add(jbsearch);
		jSplitPaneRegistryTester = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true, jPanel1, jsearchres);
		tabPrincipale.addTab("Registry tester", null,
				jSplitPaneRegistryTester, null);
		jSplitPaneRegistryTester.setDividerSize(1);
		jSplitPaneRegistryTester.setContinuousLayout(true);
		jSplitPaneRegistryTester.setPreferredSize(new java.awt.Dimension(
				487, 315));
	}

	/**
	 * Graphical component : System informations
	 *
	 */
	public void SystemInfos()
	{
		JPanel testpanel = new JPanel();
		KeyVal kvusers = new KeyVal();
		//			tabPrincipale.addTab("system configuration", null, testpanel,
		// null);
		components = new List();

		//			components.add("enabledhcp: "+ kvusers.getKeyValLocalMachine
		//					("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\{5D57A7E1-F706-438F-ADA2-3DB088E24103}",
		//					"enabledhcp"));
		//			components.add("DisableDynamicUpdate: "+
		// kvusers.getKeyValLocalMachine
		//					("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\{5D57A7E1-F706-438F-ADA2-3DB088E24103}",
		//					"DisableDynamicUpdate"));
		components.add("----------   Global configuration   -------------");
		components.add("");
		components
				.add("HostName: "
						+ kvusers
								.getKeyValLocalMachine(
										"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters",
										"HostName"));
		components.add("");
		components.add("----------   Network configuration   ------------");
		components.add("");

		String curinterface = kvusers
				.FindCurrentInterFace("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces");
		String enabledhcp = new String(kvusers.getKeyValLocalMachine(
				"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
						+ curinterface, "enabledhcp"));
		if (enabledhcp.length() == 1)
			components.add("Dhcpenabled: yes");
		components.add("");

		components.add("DhcpServer: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "DhcpServer"));
		components.add("DhcpDefaultGateway: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "dhcpDefaultGateway"));
		components.add("Dhcpdomain: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "dhcpdomain"));
		components.add("DhcpIpaddress: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "dhcpIpaddress"));
		components.add("DhcpNameServer: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "DhcpNameServer"));
		components.add("DhcpSubnetMask: "
				+ kvusers.getKeyValLocalMachine(
						"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "DhcpSubnetMask"));
		components.add("");
		components.add("-----------   Users configuration   -------------");
		components.add("");
		getUserList();

		JSplitPane jSplitPaneRegistryTester2 = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true, new JTextField(
						"system informations"), components);
		tabPrincipale.addTab("System informations", null,
				jSplitPaneRegistryTester2, null);
		jSplitPaneRegistryTester2.setDividerSize(1);
		jSplitPaneRegistryTester2.setContinuousLayout(true);
		jSplitPaneRegistryTester2.setPreferredSize(new java.awt.Dimension(
				487, 315));
	}

	/**
	 * Browse file (TODO: add extension filter)
W	 */
	public void FileTree()
	{
		final JTextArea fileDetails = new JTextArea("");
		FileSystemModel fileSystemModel = new FileSystemModel(new File("\\"));
		final JTree fileTree = new JTree(fileSystemModel);
		fileTree.setEditable(true);
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(
					TreeSelectionEvent event) {
				File file = (File) fileTree.getLastSelectedPathComponent();
				fileDetails.setText(getFileDetails(file));
				}
			});
		JScrollPane FileDetailsSP = new JScrollPane(fileDetails);
		
		jSplitPaneLocalFs = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true,
				new JScrollPane(fileTree), FileDetailsSP);

		jSplitPaneLocalFs.setDividerSize(1);
		tabPrincipale.addTab("Local FS", null, jSplitPaneLocalFs, null);
	}

	private void getUserList()
	{
		
		File dir = new File("C:\\Documents and Settings");
	    String[] children = dir.list();
	    if (children == null) {
	    } else {
	    	for (int i=0; i<children.length; i++)
	    	{
	    		components.add(children[i]);
	    	}
    	}
	}
    
	private String getFileDetails(File file)
	{
    	final String NL = System.getProperty("line.separator");
        if (file == null)
            return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name : "+file.getName()+NL);
        buffer.append("Path : "+file.getPath()+NL);
        buffer.append("Size : "+file.length()+NL);
        return buffer.toString();
    }

// -- actionListener --
	public void actionPerformed(ActionEvent arg0)
	{
		jsearchres.add(kv.getKeyVal(Jtf.getText()));
		Jtf.setText("");
	}

// -- actionListener --
	public void keyPressed(KeyEvent arg0) { }

	public void keyReleased(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			String s = kv.getKeyVal(Jtf.getText());
			if (s != null)
				jsearchres.add(s);
			Jtf.setText("");
		}
	}

	public void keyTyped(KeyEvent arg0) { }

// -- dup listener -- // pas touche !!!
	public void doChange(String ip)
	{
		ce = new ClientEjb(ip);
		ce.EjbConnect();
//		this.bean = (WanduxEjbBean) ce.getBean();
		this.getContentPane().remove(jPaneIp);
		this.initGUI();
		ce.EjbClose();
	}

	public void WanduxMenuBar()
	{
		jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
		{
			jMenuFile = new JMenu();
			jMenuBar.add(jMenuFile);
			jMenuFile.setText("File");
			{
				newFileMenuItem = new JMenuItem();
				jMenuFile.add(newFileMenuItem);
				newFileMenuItem.setText("New");
			}
			{
				openFileMenuItem = new JMenuItem();
				jMenuFile.add(openFileMenuItem);
				openFileMenuItem.setText("Open");
			}
			{
				saveMenuItem = new JMenuItem();
				jMenuFile.add(saveMenuItem);
				saveMenuItem.setText("Save");
			}
			{
				saveAsMenuItem = new JMenuItem();
				jMenuFile.add(saveAsMenuItem);
				saveAsMenuItem.setText("Save As ...");
			}
			{
				closeFileMenuItem = new JMenuItem();
				jMenuFile.add(closeFileMenuItem);
				closeFileMenuItem.setText("Close");
			}
			{
				jSeparatorFile = new JSeparator();
				jMenuFile.add(jSeparatorFile);
			}
			{
				exitMenuItem = new JMenuItem();
				jMenuFile.add(exitMenuItem);
				exitMenuItem.setText("Exit");
			}
		}
		{
			jMenuEdit = new JMenu();
			jMenuBar.add(jMenuEdit);
			jMenuEdit.setText("Edit");
			{
				cutMenuItem = new JMenuItem();
				jMenuEdit.add(cutMenuItem);
				cutMenuItem.setText("Cut");
			}
			{
				copyMenuItem = new JMenuItem();
				jMenuEdit.add(copyMenuItem);
				copyMenuItem.setText("Copy");
			}
			{
				pasteMenuItem = new JMenuItem();
				jMenuEdit.add(pasteMenuItem);
				pasteMenuItem.setText("Paste");
			}
			{
				jSeparatorEdit = new JSeparator();
				jMenuEdit.add(jSeparatorEdit);
			}
			{
				deleteMenuItem = new JMenuItem();
				jMenuEdit.add(deleteMenuItem);
				deleteMenuItem.setText("Delete");
			}
		}
		{
			jMenuHelp = new JMenu();
			jMenuBar.add(jMenuHelp);
			jMenuHelp.setText("Help");
			{
				helpMenuItem = new JMenuItem();
				jMenuHelp.add(helpMenuItem);
				helpMenuItem.setText("Help");
			}
		}
	}
}
