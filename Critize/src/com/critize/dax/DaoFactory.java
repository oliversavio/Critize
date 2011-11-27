package com.critize.dax;

public class DaoFactory {

	private static DaoFactory me = null;
	static {
		me = new DaoFactory();
	}

	public static DaoFactory getInstance() {
		return me;
	}

	public Iconnect getDao(String type) {
		Iconnect iCon = null;
		if (type.equals("Oracle"))
			iCon = new OracleDAO();
		if(type.equals("MySql"))
			iCon = new MySqlDAO();
		if (iCon != null)
			return iCon;
		else
			return null;
	}

}
