package com.critize.dax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

public class MySqlDAO implements Iconnect {

	private static String url = null;
	private static String dbName = null;
	private static String usrName = null;
	private static String password = null;
	private static String driver = null;
	Connection conn = null;
	
	private void readConfiguration(String propertyFile) {
		Properties readConfig = new Properties();
		
		
		
		try {
			FileInputStream fi = new FileInputStream(propertyFile);
			readConfig.load(fi);
			url = readConfig.getProperty("URL");
			dbName = readConfig.getProperty("DBNAME");
			usrName = readConfig.getProperty("USRNAME");
			password = readConfig.getProperty("PSWD");
			driver = readConfig.getProperty("DRIVER");
			
			fi.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private Connection getConnection() { Connection conn = null;
	 * 
	 * readConfiguration(
	 * "D:/xampplite/htdocs/Critize/WebContent/WEB-INF/dbConfig.properties");
	 * 
	 * System.out.println("Url: " + url); System.out.println("user" + usrName);
	 * System.out.println("user" + password); System.out.println("user" +
	 * driver); System.out.println("user" + dbName);
	 * 
	 * try { Class.forName(driver).newInstance(); conn =
	 * DriverManager.getConnection(url + dbName, usrName, password);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return conn; }
	 */
	
	
	@Override
	public Connection getConnection() {
		
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "proa";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "olly0808";
		String password = "olly0808";

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	

	
	@Override
	public HashMap<Integer, HashMap> executeQuery(Connection con, String query)
			throws DAXException {

		HashMap<Integer, HashMap> row = new HashMap<Integer, HashMap>();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {

			rs = getRS(con, query);
			rsmd = rs.getMetaData();
			int totalColumns = rsmd.getColumnCount();

			row = getHashMap(rs);

		} catch (SQLException e) {
			throw new DAXException(e);
		}finally{
			
			closeConnection();
		}
		
		
		return row;
	}
	
	private ResultSet getRS(Connection con, String query) throws DAXException {

		ResultSet myResult = null;
		Statement stat = null;
		try {
			stat = con.createStatement();
			myResult = stat.executeQuery(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAXException(e);
		}finally{
			//closeConnection();
		}

		return myResult;

	}
	
	public void updateQuery (Connection con, String query)throws DAXException{
		
		try {
			Statement stat = con.createStatement();
			stat.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		
	}

	private HashMap<Integer, HashMap> getHashMap(ResultSet rs)
			throws SQLException {

		HashMap<Integer, HashMap> row = new HashMap<Integer, HashMap>();

		ResultSetMetaData rsmd = null;
		rsmd = rs.getMetaData();
		int totalColumns = rsmd.getColumnCount();

		int r = 1;
		while (rs.next()) {
			HashMap<String, String> col = new HashMap<String, String>();
			for (int cols = 1; cols <= totalColumns; cols++) {
				col.put(rs.getMetaData().getColumnName(cols), rs.getString(cols));
			}
			row.put(r, col);
			r++;
		}
		return row;

	}

	@Override
	public void createProcedure(Connection con, String procName)
			throws DAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object executeProcedure(Connection con, String Schema,
			String procName, Object... aid) throws DAXException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeFunction(Connection con, String Schema,
			String funcName, Object... parameter) throws DAXException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, HashMap> executeProcedureRS(Connection con,
			String Schema, String procName, Object... parameter)
			throws DAXException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeConnection() throws DAXException {
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void closeConnection(Connection con){
		if(con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void closePreparedStmt(PreparedStatement ps){
		if(ps!=null)
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws DAXException {

		DaoFactory factory = DaoFactory.getInstance();
		Iconnect iCon = factory.getDao("MySql");
		Connection connect = (Connection)iCon.getConnection();		
		
		HashMap result = new HashMap<Integer, HashMap>();
		String query = "SELECT * FROM stock";
		HashMap hashMap = new HashMap();
		try {
			result = (HashMap<Integer, HashMap>) iCon.executeQuery(connect, query);
			
		for(int rows = 1; rows <= result.size() ; rows++){
			 hashMap = (HashMap) result.get(rows);
			
				 
				 System.out.print(hashMap.get("Company"));
				 System.out.print("\t");
				 System.out.print(hashMap.get("Products"));
				 System.out.print("\t");
				 System.out.print(hashMap.get("ModelNo"));
				 System.out.print("\t");
				 System.out.print(hashMap.get("Description"));
				 System.out.print("\t");
				 System.out.print(hashMap.get("QTY"));
				 System.out.println();
				 
				 
				 
		
					
			
		}
			
			
		} catch (DAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (connect != null) {
			System.out.println("Connected");
		} else {
			System.out.println("Could not connect");
		}

	}
	
	
	
	

}
