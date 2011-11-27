package com.critize.dax;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

public interface Iconnect {
	//connection should be removed
	public Object getConnection() throws DAXException;
	
	public HashMap<Integer,HashMap> executeQuery(Connection con, String query)
			throws DAXException;

	//procedure script
	public void createProcedure(Connection con, String procName)
			throws DAXException;

	//aid input for procedure could use object instead of string
	public Object executeProcedure(Connection con,String Schema , String procName, Object...aid)
			throws DAXException;

	public Object executeFunction(Connection con, String Schema,
			String funcName,Object... parameter)
			throws DAXException;
	public void updateQuery (Connection con, String query) throws DAXException ;
	
	public Map<Integer,HashMap> executeProcedureRS(Connection con, String Schema,
			String procName, Object... parameter)
			throws DAXException ;
	
	public void closeConnection() throws DAXException;

	public void closePreparedStmt(PreparedStatement ps);
	
	public void closeConnection(Connection con);
}
