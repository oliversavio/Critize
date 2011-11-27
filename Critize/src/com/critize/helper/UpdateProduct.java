package com.critize.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.critize.dax.DAXException;
import com.critize.dax.DaoFactory;
import com.critize.dax.Iconnect;
import com.critize.model.Stock;
import com.critize.persist.PersistFactory;

public class UpdateProduct extends Action {

	private DaoFactory factory = DaoFactory.getInstance();
	private Iconnect iCon = factory.getDao("MySql");
	private Connection conn = null;
	
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		
		
		HttpSession mySession = request.getSession();
		Stock myStockRec =  (Stock) mySession.getAttribute("myStockRec");
		
		myStockRec.setsCompany((String) request.getParameter("Company"));
		myStockRec.setsPoducts((String) request.getParameter("Product"));
		myStockRec.setsModelNo((String) request.getParameter("ModelNo"));
		myStockRec.setsDescription((String) request.getParameter("Description"));
		myStockRec.setsQTY((String) request.getParameter("QTY"));
		
		PersistFactory perFact = PersistFactory.getInstance();
		perFact.update(myStockRec);
		
		request.setAttribute("reply", "SUCCESS");
		setResponseModule(request);
	}
	
	@Override
	public void setResponseModule(HttpServletRequest request) {
		
		 request.setAttribute("module", "CurrentStock");	
		
	}
	
	
	
}
