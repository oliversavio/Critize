package com.critize.views;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProdView extends View {
	@Override
	public String getName() {
		
		return "AddNew";
	}
	

	@Override
	public void view(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		RequestDispatcher desppatch = req.getRequestDispatcher("jsp/addNew.jsp");
		desppatch.forward(req, resp);
		
	}
}
