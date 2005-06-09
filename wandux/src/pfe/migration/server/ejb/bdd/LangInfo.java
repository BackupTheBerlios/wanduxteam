package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseLangInfo;

/**
 * This is the object class that relates to the LANG_INFO table.
 * Any customizations belong here.
 */
public class LangInfo extends BaseLangInfo {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public LangInfo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public LangInfo (java.lang.String _langLanguage) {
		super(_langLanguage);
	}

/*[CONSTRUCTOR MARKER END]*/
}