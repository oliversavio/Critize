package com.critize.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagHandler to render data into a table
 * 
 * @author Oliver9471
 * 
 */
public class DataGrid extends TagSupport {

	private HashMap<Integer, HashMap> data;

	public void setData(HashMap<Integer, HashMap> data) {
		this.data = data;
	}

	@Override
	public int doStartTag() throws JspException {

		try {

			for (int index = 1; index <= data.size(); index++) {
				pageContext.getOut().write("<tr>");
				for (int index2 = 1; index2 <= data.get(index).size(); index2++) {
					if (data.get(index).get(index2) != null) {
						pageContext.getOut().write(
								"<td>" + data.get(index).get(index2).toString()
										+ "</td>");
					} else {
						pageContext.getOut().write("<td>NA</td>");
					}
				}
				pageContext.getOut().write("</tr>");

			}

			pageContext.getOut().write("</table>");

		} catch (IOException e) {

			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

}
