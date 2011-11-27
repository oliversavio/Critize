package com.critize.servlet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.critize.helper.Action;
import com.critize.parser.DomParseTest;
import com.critize.views.View;

public class MainController extends HttpServlet {

	String dbPath;
	String dbPathB;
	String dbPathS;
	String form = null;
	String linkName = null;
	String empId = null;
	String sessionId = null;
	String managerProjectId = null;
	String userType = null;
	String requestType = null;
	private HashMap actions;
	private HashMap views;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		dbPath = context.getInitParameter("dbFile");
		actions = new HashMap();
		views = new HashMap();
		
		DomParseTest.readConfig(config, actions, views);
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String requestType,module = null;
		RequestDispatcher myDispatcher = null;
		
		
		module = req.getParameter("module");
		requestType = req.getParameter("requestType");

		if ("VIEW".equalsIgnoreCase(requestType)) {

			myDispatcher = req.getRequestDispatcher((String)(views.get(module)));
			myDispatcher.forward(req, resp);
		
		} else if ("ACTION".equalsIgnoreCase(requestType)) {
			Action ac = (Action) actions.get(module);
			try {
				ac.process(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String responseModule = (String) req.getAttribute("module");
			myDispatcher = req.getRequestDispatcher((String)(views.get(responseModule)));
			myDispatcher.forward(req, resp);

		} else {
			resp.getOutputStream().print("Sorry your request could no be accepted!!");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

}
