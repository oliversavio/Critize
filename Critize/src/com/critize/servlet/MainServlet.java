package com.critize.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.critize.model.Stock;
import com.critize.persist.PersistFactory;

public class MainServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			if(req.getParameter("Company")!= null && req.getParameter("Product")!= null ){
				Stock mystock = new Stock((String) req.getParameter("Company"),
										  (String) req.getParameter("Product"), 
						                  (String) req.getParameter("ModelNo"), 
						                  (String) req.getParameter("Description"), 
						                  (String) req.getParameter("QTY"), 
										   null);
				
				PersistFactory perFact = PersistFactory.getInstance();
				
				perFact.persist(mystock);
				
				RequestDispatcher dispatch = req.getRequestDispatcher("jsp/addNew.jsp");
				dispatch.forward(req, resp);
			}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

}
