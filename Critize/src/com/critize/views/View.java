package com.critize.views;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class View {

	public abstract String getName();
	public abstract void view(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException;
	
}
