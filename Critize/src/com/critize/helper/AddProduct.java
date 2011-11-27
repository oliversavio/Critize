package com.critize.helper;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.critize.model.Stock;
import com.critize.persist.PersistFactory;

public class AddProduct extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setResponseModule(HttpServletRequest request) {
		
		if(request.getParameter("Company") == null || request.getParameter("Company") == ""){
		   request.setAttribute("module", "Dashboard");	
		}else{
		   request.setAttribute("module", "CurrentStock");
		}
		
	}

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String Id = UUID.randomUUID().toString();
		
		if (req.getParameter("Company") != null && req.getParameter("Product") != null) {
			Stock mystock = new Stock(Id,
					(String) req.getParameter("Company"),
					(String) req.getParameter("Product"),
					(String) req.getParameter("ModelNo"),
					(String) req.getParameter("Description"),
					(String) req.getParameter("QTY"), null);

			String fill = req.getParameter("fileUP");
			PersistFactory perFact = PersistFactory.getInstance();

			perFact.persist(mystock);
			req.setAttribute("reply", "SUCCESS");
			setResponseModule(req);

		}
	}

}
