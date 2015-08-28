package com.bern.ajax.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.bern.ajax.demo.bean.Address;
import com.bern.ajax.demo.service.AddressService;

@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		AddressService adsv = new AddressService(); 
		String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "utf-8");
//		String name = request.getParameter("name");
		System.out.println("name:"+name);
		List<Address> ads = adsv.init();
		Set<String> set = new HashSet<String>();
		for(Address a : ads) {
			if(a.getName().equals(name)) {
				for(Address ai : a.getAddresses()) {
					set.add(ai.getName());
				}
				String json = JSON.toJSONString(set); 
				System.out.println("return json:"+json);
				out.println(json);
				return;
			}
		}
	}

}
