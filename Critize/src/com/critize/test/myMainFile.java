package com.critize.test;

import java.sql.Connection;
import java.util.HashMap;

import com.critize.dax.DAXException;
import com.critize.dax.MySqlDAO;
import com.critize.model.Stock;
import com.critize.persist.PersistFactory;

public class myMainFile {

	public static void main(String[] args) {

		Stock stock = new Stock("StudioMaster", "Speaker", "16Inch", "SubWoofer", "10",null);
		
		PersistFactory perFact = PersistFactory.getInstance();
		perFact.persist(stock);
		
	}

	/*private static void persist(Stock stock) {

		MySqlDAO obj = new MySqlDAO();
		Connection connect = obj.getConnection();
		

		String queryString = "INSERT INTO `proa`.`stock` " +
				"(`Company`, `Products`, `ModelNo`, `Descriptions`, `QTY`, `Date`) VALUES" +
				"('"+ stock.getsCompany() + "'," +
				"'"+ stock.getsPoducts() + "'," +
				"'"+ stock.getsModelNo() + "'," +
				"'"+ stock.getsDescription() + "'," +
				"'"+ stock.getsQTY() + "',null);";
		System.out.println(queryString);
		obj.updateQuery(connect, queryString);

		if (connect != null) {
			System.out.println("Connected");
		} else {
			System.out.println("Could not connect");
		}
	}
*/
}
