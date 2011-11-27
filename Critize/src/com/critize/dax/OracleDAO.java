package com.critize.dax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date;
import java.util.Properties;

import oracle.jdbc.driver.OracleDatabaseMetaData;
import oracle.jdbc.driver.OracleTypes;

public class OracleDAO implements Iconnect {

	private Connection con = null;
	private PreparedStatement stat = null;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	private CallableStatement cs = null;
	private Statement stmt = null;

	private static String url;
	private static String usrName;
	private static String password;
	private static String driver;

	public HashMap<Integer, HashMap> executeQuery(Connection con, String query)
			throws DAXException {

		HashMap<Integer, HashMap> row = new HashMap<Integer, HashMap>();

		try {

			rs = getRS(con, query);
			rsmd = rs.getMetaData();
			int totalColumns = rsmd.getColumnCount();

			row = getHashMap(rs);

		} catch (SQLException e) {
			throw new DAXException(e);
		}
		return row;
	}

	private ResultSet getRS(Connection con, String query) throws DAXException {

		ResultSet myResult = null;

		try {
			stat = con.prepareStatement(query);
			myResult = stat.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAXException(e);
		}

		return myResult;

	}

	private HashMap<Integer, HashMap> getHashMap(ResultSet rs)
			throws SQLException {

		HashMap<Integer, HashMap> row = new HashMap<Integer, HashMap>();

		ResultSetMetaData rsmd = null;
		rsmd = rs.getMetaData();
		int totalColumns = rsmd.getColumnCount();

		int r = 1;
		while (rs.next()) {
			HashMap<Integer, Object> col = new HashMap<Integer, Object>();
			for (int cols = 1; cols <= totalColumns; cols++) {
				col.put(cols, rs.getString(cols));
			}
			row.put(r, col);
			r++;
		}
		return row;

	}

	public Object getConnection(String propertyFile) throws DAXException {

		readConfiguration(propertyFile);

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAXException(e);
		}
		try {
			return DriverManager.getConnection(url, usrName, password);
		} catch (SQLException e) {
			throw new DAXException(e);
		}
	}

	// java.util
	private static void readConfiguration(String propertyFile) {
		Properties readConfig = new Properties();
		try {
			readConfig.load(new FileInputStream(propertyFile));
			url = readConfig.getProperty("URL");
			usrName = readConfig.getProperty("USR");
			password = readConfig.getProperty("PWD");
			driver = readConfig.getProperty("DRIVER");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() throws DAXException {
		try {
			if (con != null) {
				con.close();
			} else {
				con = null;
			}
			if (stat != null) {
				stat.close();
			} else {
				stat = null;
			}
			if (rs != null) {
				rs.close();
			} else {
				rs = null;
			}

		} catch (SQLException e) {
			throw new DAXException(e);
		}
	}

	private String callPreparedProc(int totalNoParameter, String procName) {

		String callPrepared;
		String start = "{call ";
		String end = "(";

		for (int i = 0; i < totalNoParameter; i++) {
			end = end.concat("?,");
		}
		end = end.substring(0, (end.length() - 1));
		end = end.concat(") }");
		callPrepared = start + procName + end;

		// System.out.println(callPrepared);
		return callPrepared;
	}

	/**
	 * @param con
	 * @param Schema
	 * @param procName
	 * @param parameter
	 * @return
	 * @throws DAXException
	 */
	public Map<Integer, HashMap> executeProcedureRS(Connection con,
			String Schema, String procName, Object... parameter)
			throws DAXException {

		ArrayList<MetaDataOfParameter> inputs = new ArrayList<MetaDataOfParameter>();
		ArrayList<Object> output = new ArrayList<Object>();
		inputs = getProcedureMetaData(con, Schema.toUpperCase(),
				procName.toUpperCase());

		int totalNoOfParameters = 0;
		int indexForRs = 0;
		int indexInput = 0;
		Date date1 = null;
		ResultSet rs = null;
		Map<Integer, HashMap> row = new HashMap<Integer, HashMap>();

		totalNoOfParameters = getTotalNoParameters(inputs);
		try {
			cs = con.prepareCall(callPreparedProc(totalNoOfParameters, procName));
			for (MetaDataOfParameter m : inputs) {

				switch (m.columnType) {
				/* DatabaseMetaData.procedureColumnIn */

				case 1:
					if (m.columnDatatype.equals("DATE")) {

						cs.setDate((indexForRs + 1),
								date1.valueOf((String) parameter[indexInput++]));
					} else {
						cs.setString((indexForRs + 1),
								parameter[indexInput++].toString());

					}
					break;

				/* DatabaseMetaData.procedureColumnOut */
				case 4:
					setOutputColumns(cs, m.columnDatatype, indexForRs);

					break;

				/* DatabaseMetaData.procedureColumnInOut */
				case 2:
					setInputOutputColumns(cs, m.columnDatatype, indexForRs);
					cs.setString((indexForRs + 1),
							parameter[indexInput++].toString());

					break;
				default:
					// procReturn = "Unknown";
				}
				indexForRs++;

			}
			// con = DriverManager.getConnection(url, Schema.toUpperCase(),
			// Schema.toUpperCase());
			cs.execute();

			for (MetaDataOfParameter m : inputs) {
				if (m.columnType == 2 || m.columnType == 4 || m.columnType == 5) {
					if (m.columnDatatype.equals("REF CURSOR")) {
						rs = (ResultSet) cs.getObject(m.positionOfParameter);
					}
				}
			}
			row = getHashMap(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return row;

	}

	@Override
	public Object executeProcedure(Connection con, String Schema,
			String procName, Object... parameter) throws DAXException {
		ArrayList<MetaDataOfParameter> inputs = new ArrayList<MetaDataOfParameter>();
		ArrayList<Object> output = new ArrayList<Object>();
		inputs = getProcedureMetaData(con, Schema.toUpperCase(),
				procName.toUpperCase());

		int totalNoOfParameters = 0;
		int indexForRs = 0;
		int indexInput = 0;
		Date date1 = null;

		totalNoOfParameters = getTotalNoParameters(inputs);
		try {
			cs = con.prepareCall(callPreparedProc(totalNoOfParameters, procName));
			for (MetaDataOfParameter m : inputs) {

				switch (m.columnType) {
				/* DatabaseMetaData.procedureColumnIn */

				case 1:
					if (m.columnDatatype.equals("DATE")) {

						cs.setDate((indexForRs + 1),
								date1.valueOf((String) parameter[indexInput++]));
					} else {
						cs.setString((indexForRs + 1),
								parameter[indexInput++].toString());

					}
					break;

				/* DatabaseMetaData.procedureColumnOut */
				case 4:
					setOutputColumns(cs, m.columnDatatype, indexForRs);

					break;

				/* DatabaseMetaData.procedureColumnInOut */
				case 2:
					setInputOutputColumns(cs, m.columnDatatype, indexForRs);
					cs.setString((indexForRs + 1),
							parameter[indexInput++].toString());

					break;
				default:
					// procReturn = "Unknown";
				}
				indexForRs++;
			}

			// con = DriverManager.getConnection(url, Schema.toUpperCase(),
			// Schema.toUpperCase());

			cs.execute();
			output = getReturnValues(cs, inputs, totalNoOfParameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;
	}

	private int getTotalNoParameters(ArrayList<MetaDataOfParameter> inputs) {
		int in = 0;
		int out = 0;
		int inout = 0;
		for (MetaDataOfParameter m : inputs) {
			switch (m.columnType) {
			// in
			case 1:
				++in;
				break;
			// out
			case 4:
				++out;
				break;
			// in out
			case 2:
				++inout;
				break;
			default:
				break;
			}
		}
		return (in + out + inout);
	}

	private ArrayList<Object> getReturnValues(CallableStatement cs2,
			ArrayList<MetaDataOfParameter> inputs, int totalNoOfParameters) {
		ArrayList<Object> output = new ArrayList<Object>();
		try {
			for (MetaDataOfParameter m : inputs) {
				if (m.columnType == 2 || m.columnType == 4 || m.columnType == 5) {
					if (m.columnDatatype.equals("VARCHAR2")) {
						output.add(cs.getString(m.positionOfParameter));
					} else if (m.columnDatatype.equals("NUMBER")) {
						output.add(cs.getInt(m.positionOfParameter));
					} else if (m.columnDatatype.equals("BOOLEAN")) {
						output.add(cs.getBoolean(m.positionOfParameter));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;

	}

	private ArrayList<MetaDataOfParameter> getProcedureMetaData(Connection con,
			String Schema, String procName) {
		OracleDatabaseMetaData metaData = null;
		ResultSet rs = null;
		MetaDataOfParameter paramMetaData = null;
		int position = 1;
		ArrayList<MetaDataOfParameter> inputs = new ArrayList<MetaDataOfParameter>();
		try {

			con = DriverManager.getConnection(url, Schema.toUpperCase(),
					Schema.toUpperCase());
			metaData = (OracleDatabaseMetaData) con.getMetaData();
			rs = metaData.getProcedureColumns(null, Schema, procName, "%");
			while (rs.next()) {
				paramMetaData = new MetaDataOfParameter();
				paramMetaData.columnName = rs.getString(4);
				paramMetaData.columnType = rs.getShort(5);
				paramMetaData.columnDatatype = rs.getString(7);
				paramMetaData.positionOfParameter = position++;
				inputs.add(paramMetaData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inputs;
	}

	private void setInputOutputColumns(CallableStatement cs2,
			String columnDatatype, int indexForRs) {
		try {
			if (columnDatatype.equals("VARCHAR2")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.VARCHAR);
			} else if (columnDatatype.equals("NUMBER")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.DOUBLE);
			} else if (columnDatatype.equals("BOOLEAN")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.BOOLEAN);
			} else if (columnDatatype.equals("REF CURSOR")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.CURSOR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setOutputColumns(CallableStatement cs2, String columnDatatype,
			int indexForRs) {
		try {
			if (columnDatatype.equals("VARCHAR2")) {

				cs.registerOutParameter((indexForRs + 1), OracleTypes.VARCHAR);

			} else if (columnDatatype.equals("NUMBER")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.DOUBLE);
			} else if (columnDatatype.equals("BOOLEAN")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.BOOLEAN);
			} else if (columnDatatype.equals("REF CURSOR")) {
				cs.registerOutParameter((indexForRs + 1), OracleTypes.CURSOR);// Chandeg
																				// from
																				// ref
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String prepareCallFunc(int totalNoParameter, String funcName) {
		String start = "{call ? := ";
		String end = "(";
		String callPrepared = null;
		for (int i = 0; i < totalNoParameter; i++) {
			end = end.concat("?,");
		}
		end = end.substring(0, (end.length() - 1));
		end = end.concat(") }");
		callPrepared = start + funcName + end;

		return callPrepared;
	}

	@Override
	public Object executeFunction(Connection con, String Schema,
			String funcName, Object... parameter) throws DAXException {
		int totalNoOfParameters = 0;
		int indexForRs = 0;
		int indexInput = 0;
		ArrayList<MetaDataOfParameter> inputs = new ArrayList<MetaDataOfParameter>();
		ArrayList<Object> output = new ArrayList<Object>();
		inputs = getProcedureMetaData(con, Schema.toUpperCase(),
				funcName.toUpperCase());
		totalNoOfParameters = getTotalNoParameters(inputs);
		try {
			cs = con.prepareCall(prepareCallFunc(totalNoOfParameters, funcName));
			for (MetaDataOfParameter m : inputs) {
				switch (m.columnType) {
				/* DatabaseMetaData.procedureColumnIn */
				case 1:
					cs.setString((indexForRs + 1),
							parameter[(indexInput++)].toString());
					break;

				/* DatabaseMetaData.procedureColumnOut */
				case 4:
				case 5:

					setOutputColumns(cs, m.columnDatatype, indexForRs);
					m.positionOfParameter = (indexForRs + 1);
					break;

				/* DatabaseMetaData.procedureColumnInOut */
				case 2:

					setInputOutputColumns(cs, m.columnDatatype, indexForRs);

					cs.setString((indexForRs + 1),
							parameter[indexInput++].toString());
					m.positionOfParameter = (indexForRs + 1);
					break;
				/*
				 * 
				 * case 5: if (m.columnDatatype.equals("VARCHAR2")) {
				 * cs.registerOutParameter((indexForRs + 1),
				 * OracleTypes.VARCHAR); } else if
				 * (m.columnDatatype.equals("NUMBER")) {
				 * cs.registerOutParameter((indexForRs + 1),
				 * OracleTypes.DOUBLE); } else if
				 * (m.columnDatatype.equals("BOOLEAN")) {
				 * cs.registerOutParameter((indexForRs + 1),
				 * OracleTypes.BOOLEAN); } else if
				 * (m.columnDatatype.equals("REF CURSOR")) {
				 * cs.registerOutParameter((indexForRs + 1), OracleTypes.REF); }
				 * m.positionOfParameter = (indexForRs + 1); break;
				 */
				default:
					// procReturn = "Unknown";
				}
				indexForRs++;
			}

			cs.execute();
			// total+1-->for return type
			output = getReturnValues(cs, inputs, (totalNoOfParameters + 1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void createProcedure(Connection con, String procName)
			throws DAXException {
		try {
			stmt = con.createStatement();
			String createProcedure = "create or replace procedure " + procName
					+ " as " + " Begin select EMPNO, ENAME, HIREDATE"
					+ " from emp " + "WHERE (HIREDATE > '2/2/1982') "
					+ " order by ENAME; End;";

			// Create stored procedure, this is a one time procedure
			stmt.executeUpdate(createProcedure);
			System.out.println("Procedure Created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
