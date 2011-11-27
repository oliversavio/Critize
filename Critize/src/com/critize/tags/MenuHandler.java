package com.critize.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * TagHandler for rendering Menus
 * Reads menu options from a text file in WEB-INF folder
 * @author Oliver9471
 *
 */
public class MenuHandler extends TagSupport {

	private List<String> name = new ArrayList<String>();;

	private List<String> menuList = new ArrayList<String>();

	public void setName(List<String> name) {
		this.name = name;
	}

	@Override
	public int doStartTag() throws JspException {

		menuList = name;

		for (int index = 0; index < menuList.size(); index = index + 2) {

			try {
				pageContext.getOut().write("<li>");
				pageContext.getOut().write(
						"<a href='" + menuList.get(index + 1) + "'>");
				pageContext.getOut().write(menuList.get(index));
				pageContext.getOut().write("</a></li>");

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return EVAL_PAGE;
	}

}
