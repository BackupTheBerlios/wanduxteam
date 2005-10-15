/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

//import java.io.IOException;
//import java.io.InputStream;

/**
 * @author joe star
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NetSettings {
	public static String FindMacAddr() {
		//String macaddr = "00-11-0A-3A-74-7E";
//		String macaddr = "00-11-0a-3a-bf-bf";
//		String macaddr = "00-11-0a-3a-dc-ba";
		String macaddr = "00-11-85-11-4d-6b";
		
		
//		try {
//			InputStream stdoutStream = Runtime.getRuntime().exec(
//					"ipconfig /all").getInputStream();
//			StringBuffer buffer = new StringBuffer();
//			StringBuffer buffer2 = new StringBuffer();
//			for (int c = 0, j = 0; c != -1;) {
//				if (j == 22) {
//					if (c != '\t')
//						if (c == ':')
//							buffer2.append((char) c);
//						else if (buffer2.length() != 0 && c != '\r')
//							buffer2.append((char) c);
//				}
//				c = stdoutStream.read();
//				if (c != '\r')
//					buffer.append((char) c);
//				else
//					j++;
//			}
//			System.out.println(buffer2.toString());
//			macaddr = buffer2.toString().substring(2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return (macaddr);
	}
}
