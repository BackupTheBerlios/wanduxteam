/*
 * Created on 22 déc. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import com.jacob.com.Variant;

import pfe.migration.client.pre.service.WanduxWmiBridge;

/**
 * @author lahous
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserConfig {
	WanduxWmiBridge wwb = null; 
	
  public UserConfig(WanduxWmiBridge WWB)
  {
  	wwb = WWB;
  }
  
  public Variant[] listNetworkInterfaces()
  {
  		String rq = "SELECT * FROM Win32_UserAccount";
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj;
  }
  
  /**
 * 
 */
public String getUserKbLayout(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = \'" + username + "\'";
  		String wzName = "Name"; // element a recuperer depuis la requette
		System.out.println(rq);
  		Variant obj[] =  wwb.exec_rq(rq, wzName);
  		System.out.println("UserName : " + obj[0].getString());
		return obj.toString();
}
  
/**
 * 
 */
public String getUserHome(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}

/**
 * 
 */
public Integer getUserKey(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return new Integer(0);
}


/**
 * 
 */
public String geUserLogin(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}



/**
 * 
 */
public String getUserPass(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}


/**
 * 
 */
public String getUserProxyOverride(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}


/**
 * 
 */
public String getUserProxyServ(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}


/**
 * 
 */
public String getUserTimezone(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}


/**
 * 
 */
public String getUserType(String username) {
		String rq = "SELECT * FROM Win32_UserAccount WHERE Name = " + username;
  		String wzName = "Name"; // element a recuperer depuis la requette
		Variant obj[] =  wwb.exec_rq(rq, wzName);
		return obj.toString();
}



}
