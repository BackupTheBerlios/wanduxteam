/*
 * Created on 5 nov. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jacob.com.Variant;

import pfe.migration.client.pre.service.WanduxWmiBridge;
import pfe.migration.server.ejb.bdd.NetworkConfig;

/**
 * @author joe star
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NetConfig
{
	// TODO si on doit continue dans cette voie la, il faut choper tte les interfaces, (possible avec une map, mais surtout savoir si quel interface est valide)
	
//	public static List outpout = new ArrayList();
	WanduxWmiBridge wwb = null; 
	String rootCIMV2 = "root\\CIMV2";
	String rootCIMV2ApplicationsMicrosoftIE = "root\\CIMV2\\Applications\\MicrosoftIE";
	
  public NetConfig(WanduxWmiBridge WWB)
  {
  	// todo : controler si WWB est null, si oui, le creer
  	wwb = WWB;
  }

   public String GetMac()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Adresse physique.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }

   public String GetIp()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Adresse IP.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }

   public java.lang.Byte GetDHCPEnable(String NetworkInterfaceIndex)
   {
	   String res = "";
	   Variant[] rqRSLT = null;
//		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Caption = "  + "\'" + NetworkInterfaceCaption + "\'";
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Index = "  + "\'" + NetworkInterfaceIndex + "\'";
	   //String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration";
	   System.out.println(rq);
		String wzName = "DHCPEnabled"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			if(rqRSLT[0].equals("1")) // erreur detected
			{
				System.err.println(rqRSLT[1]);
				return null;
			}
			System.out.println("valeur recue : " + rqRSLT[0]);
			Variant var = rqRSLT[0];
			return (new Byte(var.getString()));
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
		}
		return null;
   }

   public String GetNetmask()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Masque de sous-r�seau.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }
   
   public String GetGate()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Passerelle par d�faut.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }
   
   public String GetHostname()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Nom de l'h�te.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }
   
   public String GetDNSSuffixe()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Suffixe DNS principal.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }
	
   public String GetSeekingDNSSuffixe()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Liste de recherche du suffixe DNS.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }

   public String GetDescription()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Description.*: (.*)");
//		   Matcher m = p.matcher(((String)outpout.get(i)));
//		   if (m.matches())
//		   {
//			   res = m.group(1);
//			   break;
//		   }
//	   }
	   return res;
   }
   public Variant[] listNetworkInterfaces()
   {
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration";
		String wzName = "Index"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
//		Variant ob = obj[0];
//		System.out.println(ob.getClass());
//		System.out.println(ob);
		//Integer entier = new Integer(obj);
		//int [] valueOBJ = (int[])obj;
		//System.out.println(valueOBJ.toString());
		return obj;
   }

//   public static void main(String[] args) throws IOException
//   {
//	   IpConfig ipconf = new IpConfig();
//   		String address = ipconf.GetMac();
//   		System.out.println(address);
//   		System.out.println(ipconf.GetDHCPEnable());
//   		System.out.println(ipconf.GetGate());
//   		System.out.println(ipconf.GetIp());
//   		System.out.println(ipconf.GetNetmask());
//   }
}