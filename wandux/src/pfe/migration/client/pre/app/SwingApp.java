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
		        javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch(Exception e) {
		        e.printStackTrace();
		}
	}
	
	// -- menu bar --
	private JMenuBar jMenuBar;

	private JMenu jMenuFile;
	private JMenuItem newFileMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem closeFileMenuItem;
	private JSeparator jSeparatorFile;
	private JMenuItem exitMenuItem;
	
	private JMenu jMenuEdit;
	private JMenuItem cutMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;
	private JSeparator jSeparatorEdit;
	private JMenuItem deleteMenuItem;

	private JMenu jMenuHelp;
	private JMenuItem helpMenuItem;
	// -- menu bar -- // fin

	// -- les differents tabs -- 
	private JTabbedPane tabPrincipale;
	
	// -- tab LocalFs --
	private JSplitPane jSplitPaneLocalFs;

	// -- tab RegistryTester --
	private JSplitPane jSplitPaneRegistryTester;
	private JPanel jPanel1;
	private JTextField Jtf = null;
	private List jsearchres = null;
	private JButton jbsearch = null;
	private KeyVal kv = new KeyVal();

	// -- tab FileIndex --
	private JPanel FileIndex;
	
	// -- les differents tabs -- // fin

	// -- partie touchant le reseaux -- // en travaux
	private EnterIpView jPaneIp = null;
	private ClientEjb ce = null;

	private final static int FIRSTSTEP = 0;
	private final static int SECONDSTEP = 1;
	private int step = FIRSTSTEP;
	
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
		jsearchres = new List();
		jsearchres.setFocusable(false);
		jbsearch = new JButton("Search");
		Jtf.addKeyListener(this);
		final JTextArea fileDetails = new JTextArea("");
		
		try {
			setSize(600, 400);
			{
				tabPrincipale = new JTabbedPane();
				{
					this.getContentPane().add(tabPrincipale, BorderLayout.CENTER);
					{
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
					{
						FileIndex = new JPanel();
						tabPrincipale.addTab("File indexer", null, FileIndex, null);
						//FileIndex.add(new Label("FileIndex"));
					}
					{
						{
							jPanel1 = new JPanel();
							jPanel1.setLayout(new BoxLayout(
								jPanel1,
								BoxLayout.Y_AXIS));
							Jtf.setMaximumSize(new Dimension(400, 18));
							//Jtf.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
							jPanel1.add(Jtf);
							jbsearch.addActionListener(this);
							jPanel1.add(jbsearch);
						}
						jSplitPaneRegistryTester = new JSplitPane(
							JSplitPane.HORIZONTAL_SPLIT,
							true,
							jPanel1,
							jsearchres);
						tabPrincipale.addTab("Registry tester", null, jSplitPaneRegistryTester, null);
						jSplitPaneRegistryTester.setDividerSize(1);
						jSplitPaneRegistryTester.setContinuousLayout(true);
						jSplitPaneRegistryTester
							.setPreferredSize(new java.awt.Dimension(487, 315));
						//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
						JPanel testpanel = new JPanel();
						KeyVal kvusers = new KeyVal();
						tabPrincipale.addTab("panel", null, testpanel, null);
						JTextField text = new JTextField(kvusers.getKeyValLocalMachine("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\{5D57A7E1-F706-438F-ADA2-3DB088E24103}"));
						//JTextField text = new JTextField("SYSTEM\\CurrentControlSet\\Service...");
						testpanel.add(text);
				
						//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
					}
				}
			}
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
		} catch (Exception e) {
			e.printStackTrace();
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
		System.out.println("actionPerformed");
		jsearchres.add(kv.getKeyVal(Jtf.getText()));
		Jtf.setText("");
	}

// -- actionListener --
	public void keyPressed(KeyEvent arg0) { }

	public void keyReleased(KeyEvent arg0)
	{
		System.out.println("keyReleased");
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			jsearchres.add(kv.getKeyVal(Jtf.getText()));
			Jtf.setText("");
		}
	}

	public void keyTyped(KeyEvent arg0) { }

// -- dup listener --
	public void doChange(String ip)
	{
//		ce = new ClientEjb(ip);
//		ce.EjbConnect();
		this.getContentPane().remove(jPaneIp);
		this.initGUI();
//		ce.EjbClose();
	}

}
