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

/**
 * @author joe star
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IpConfig
{
	// TODO si on doit continue dans cette voie la, il faut choper tte les interfaces, (possible avec une map, mais surtout savoir si quel interface est valide)
	
	public static List outpout = new ArrayList();
	
   public IpConfig() throws IOException
   {
      String command = "ipconfig /all";
      Process pid = Runtime.getRuntime().exec(command);
      BufferedReader in =
         new BufferedReader(
         new InputStreamReader(pid.getInputStream()));

      while (true) {
         String line = in.readLine();
         outpout.add(line);
         if (line == null)
            break;
      }
      in.close();
   }

   public String GetMac()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Adresse physique.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }

   public String GetIp()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Adresse IP.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }

   public boolean GetDHCPEnable()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*DHCP activé.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res=="oui"?true:false;
   }

   public String GetNetmask()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Masque de sous-réseau.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }
   
   public String GetGate()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Passerelle par défaut.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }
   
   public String GetHostname()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Nom de l'hôte.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }
   
   public String GetDNSSuffixe()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Suffixe DNS principal.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }
	
   public String GetSeekingDNSSuffixe()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Liste de recherche du suffixe DNS.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }

   public String GetDescription()
   {
	   String res = "";

	   for (int i = 0; i < outpout.size(); i++)
	   {
		   Pattern p = Pattern.compile(".*Description.*: (.*)");
		   Matcher m = p.matcher(((String)outpout.get(i)));
		   if (m.matches())
		   {
			   res = m.group(1);
			   break;
		   }
	   }
	   return res;
   }

   public static void main(String[] args) throws IOException
   {
	   IpConfig ipconf = new IpConfig();
   		String address = ipconf.GetMac();
   		System.out.println(address);
   		System.out.println(ipconf.GetDHCPEnable());
   		System.out.println(ipconf.GetGate());
   		System.out.println(ipconf.GetIp());
   		System.out.println(ipconf.GetNetmask());
   }
}