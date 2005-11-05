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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author joe star
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetMac
{
   public String getMacAddress() throws IOException
   {
      String macAddress = null;
      String command = "ipconfig /all";
      Process pid = Runtime.getRuntime().exec(command);
      BufferedReader in =
         new BufferedReader(
         new InputStreamReader(pid.getInputStream()));
      while (true) {
         String line = in.readLine();
         if (line == null)
            break;
         Pattern p = Pattern.compile(".*Adresse physique.*: (.*)");
         Matcher m = p.matcher(line);
         if (m.matches()) {
            macAddress = m.group(1);
            break;
         }
      }
      in.close();
      return macAddress;
   }

   public static void main(String[] args)
   throws IOException
   {
   		String address = new GetMac().getMacAddress();
   		System.out.println(address);
   }
}