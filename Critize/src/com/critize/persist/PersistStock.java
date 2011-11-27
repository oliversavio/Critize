package com.critize.persist;

import java.sql.Connection;
import java.sql.SQLException;

import com.critize.dax.DAXException;
import com.critize.dax.Iconnect;
import com.critize.model.Stock;
import com.mysql.jdbc.PreparedStatement;

public class PersistStock {

	private static Connection conn = null;

	public static void insert(Object obj, Iconnect iCon){

		PreparedStatement insertStock = null;

		String insertStockQS = "INSERT INTO currentStock (`id`,`Company`, `Products`, `ModelNo`, `Descriptions`, `QTY`) VALUES (?,?,?,?,?,?);" ;

		try {
			conn = (Connection) iCon.getConnection();
			insertStock = (PreparedStatement) conn.prepareStatement(insertStockQS);
			insertStock.setString(1, ((Stock) obj).getId());
			insertStock.setString(2, ((Stock) obj).getsCompany());
			insertStock.setString(3, ((Stock) obj).getsPoducts());
			insertStock.setString(4, ((Stock) obj).getsModelNo());
			insertStock.setString(5, ((Stock) obj).getsDescription());
			insertStock.setString(6, ((Stock) obj).getsQTY());

			insertStock.executeUpdate();

		} catch (DAXException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			iCon.closeConnection(conn);
			iCon.closePreparedStmt(insertStock);
		}

	}

	public static void update(Object obj, Iconnect iCon){
		
		PreparedStatement updateStock = null;
		
		String updateStockQS = "UPDATE currentStock SET Company = ?,Products = ?,ModelNo = ?,Descriptions = ?,QTY = ? WHERE Id = ? " ;
		try {
		
			conn = (Connection) iCon.getConnection();
			updateStock = (PreparedStatement) conn.prepareStatement(updateStockQS);
			
			updateStock.setString(1, ((Stock) obj).getsCompany());
			updateStock.setString(2, ((Stock) obj).getsPoducts());
			updateStock.setString(3, ((Stock) obj).getsModelNo());
			updateStock.setString(4, ((Stock) obj).getsDescription());
			updateStock.setString(5, ((Stock) obj).getsQTY());
			updateStock.setString(6, ((Stock) obj).getId());
			
			updateStock.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
	} catch (DAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally{
		iCon.closeConnection(conn);
		iCon.closePreparedStmt(updateStock);
	}
		
		
		
		

	}
}
