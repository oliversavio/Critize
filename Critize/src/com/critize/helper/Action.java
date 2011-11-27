package com.critize.helper;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {

	public abstract void setResponseModule(HttpServletRequest request);

	public abstract void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException;

}