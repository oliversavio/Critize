package com.critize.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.critize.dax.DAXException;
import com.critize.dax.DaoFactory;
import com.critize.dax.Iconnect;

public class ReadFile extends Action {

	public String getName() {
		// TODO Auto-generated method stub
		return "ReadFile";
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String FilePath = "somepath";
		readFile(FilePath);

	}

	public void readFile(String FilePath) {

		DaoFactory factory = DaoFactory.getInstance();
		Iconnect iCon = factory.getDao("MySql");

		try {
			FileInputStream fi = new FileInputStream("C:/Users/Oliver/Desktop/TESTDAT.dat");
			InputStreamReader in = new InputStreamReader(fi);

			BufferedReader buff = new BufferedReader(in);

			if (buff != null) {
				String[] headers = buff.readLine().split("\\s+");
				String C;
				Connection conn = (Connection) iCon.getConnection();
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("INSERT INTO `proa`.`stock` (`");

				for (int i = 0; i < headers.length; i++) {
					sBuilder.append(headers[i]);
					if (i != headers.length - 1) {
						sBuilder.append("`,`");
					} else {
						sBuilder.append("`,`Date`) ");
					}
				}

				while ((C = buff.readLine()) != null) {
					StringBuilder sValues = new StringBuilder();
					sValues.append(sBuilder);
					sValues.append("VALUES ('");
					String[] values = C.split("\\s+");

					for (int i = 0; i < headers.length; i++) {
						sValues.append(values[i]);
						if (i != headers.length - 1) {
							sValues.append("','");
						} else {
							sValues.append("',CURRENT_TIMESTAMP);");
						}
					}

					iCon.updateQuery(conn, sValues.toString());
					// System.out.println(sValues.toString());
				}

			}
			fi.close();
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		ReadFile rf = new ReadFile();
		rf.readFile("asd");
	}

	@Override
	public void setResponseModule(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
