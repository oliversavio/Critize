package com.critize.persist;

import com.critize.dax.DaoFactory;
import com.critize.dax.Iconnect;
import com.critize.model.Stock;

public class PersistFactory {
	private DaoFactory factory = DaoFactory.getInstance();
	private Iconnect iCon = factory.getDao("MySql");
	private static PersistFactory me = new PersistFactory();

	public static PersistFactory getInstance() {
		return me;
	}

	public void persist(Object obj) {

		if (obj instanceof Stock) {
			  PersistStock.insert(obj, iCon);
		}
	}
	
	
	public void update(Object obj) {

		if (obj instanceof Stock) {
			PersistStock.update(obj, iCon);
		}

	}

}
