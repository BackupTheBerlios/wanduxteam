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

import com.jacob.com.SafeArray;
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

   public String GetMac(String NetworkInterfaceIndex)
   {
    String res = "";
	   Variant[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Index = "  + "\'" + NetworkInterfaceIndex + "\'";
		//System.out.println(rq);
		String wzName = "MACAddress"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			System.out.println(rqRSLT[0].getString());
			return  rqRSLT[0].getString();
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
		}
		return null;
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
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Index = "  + "\'" + NetworkInterfaceIndex + "\'";
		//System.out.println(rq);
		String wzName = "DHCPEnabled"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			//System.out.println(rqRSLT[0].getBoolean());
			return  new Byte(rqRSLT[0].getBoolean() == true ? "1" : "0");
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
		}
		return null;
   }

   public String GetNetmask(String NetworkInterfaceIndex)
   {
    String res = "";
	   Variant[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Index = "  + "\'" + NetworkInterfaceIndex + "\'";
		//System.out.println(rq);
		String wzName = "IPSubnet"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			Variant var =  rqRSLT[0];
			SafeArray str = var.toSafeArray();
			System.out.println(str.getString(0));
			return  str.getString(0);

		}
		catch(Exception e)
		{
			System.err.println("err in : String GetGate  " + e.getStackTrace());
		}
		return null;
   }
   
   public String GetGate(String NetworkInterfaceIndex)
   {
    String res = "";
	   Variant[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_NetworkAdapterConfiguration WHERE Index = "  + "\'" + NetworkInterfaceIndex + "\'";
		//System.out.println(rq);
		String wzName = "DefaultIPGateway"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			Variant var =  rqRSLT[0];
			SafeArray str = var.toSafeArray();
			System.out.println(str.getString(0));
			return  str.getString(0);

		}
		catch(Exception e)
		{
			System.err.println("err in : String GetGate  " + e.getStackTrace());
		}
		return null;
   }
   
   public String GetHostname()
   {
	   String res = "";

//	   for (int i = 0; i < outpout.size(); i++)
//	   {
//		   Pattern p = Pattern.compile(".*Nom de l'hôte.*: (.*)");
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