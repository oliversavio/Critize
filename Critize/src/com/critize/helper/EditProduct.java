package com.critize.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.critize.dax.DAXException;
import com.critize.dax.DaoFactory;
import com.critize.dax.Iconnect;
import com.critize.model.Stock;
import com.mysql.jdbc.PreparedStatement;

public class EditProduct extends  Action{
	
	
	@Override
	public void setResponseModule(HttpServletRequest request) {
		
		 request.setAttribute("module", "EditProduct");	
		
	}
	
	
	
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		
		String Id = (String)request.getParameter("Id");
		
		DaoFactory df = DaoFactory.getInstance();
		Iconnect iCon = df.getDao("MySql");
		Connection conn = null;
		String query = "SELECT * FROM currentstock WHERE Id = ?" ;
		PreparedStatement updateStock = null;
		ResultSet rs = null;
		Stock currentStock = null;
		try {
			conn = (Connection) iCon.getConnection();
			updateStock = (PreparedStatement) conn.prepareStatement(query);
			updateStock.setString(1, Id);
			rs = updateStock.executeQuery();
			
			while(rs.next()){
				
				currentStock = new Stock(rs.getString("Id"),
										 rs.getString("Company"),
										 rs.getString("Products"), 
										 rs.getString("ModelNo"), 
										 rs.getString("Descriptions"), 
										 rs.getString("QTY"), 
										 rs.getString("Date")) ;
				
				HttpSession mySession = request.getSession();
				
				mySession.setAttribute("myStockRec", currentStock);
			}
			
			//conn.commit();
			
		} catch (DAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if(conn != null){ conn.close();}
			if(updateStock != null){ updateStock.close(); }
			if(rs != null){ rs.close(); }
		}
		
		
		
		setResponseModule(request);
	}

	
	
	
}
