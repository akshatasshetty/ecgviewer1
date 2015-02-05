package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rjil.jio.nasan.wscall.HttpsWSCall;

/**
 * Servlet implementation class RegServ
 */
public class RegServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpsWSCall call = new HttpsWSCall();
		org.codehaus.jettison.json.JSONObject json = new org.codehaus.jettison.json.JSONObject();
		PrintWriter out = response.getWriter();
		org.json.simple.JSONObject data=new org.json.simple.JSONObject();
		String success = "";

		String orgname = request.getParameter("oname");
		String adminname = request.getParameter("aname");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String mobile = request.getParameter("mobile");
		String anumber = request.getParameter("anumber");
		String emailid = request.getParameter("emailid");
		String aemail = request.getParameter("aemail");
		String clinicid = request.getParameter("clinicid");
		/* int pass = Integer.parseInt(request.getParameter("pass")); */
		String pass = request.getParameter("pass");
		/*
		 * System.out.println(orgname); System.out.println(adminname);
		 * System.out.println(address); System.out.println(city);
		 */
		try {
			data = call.clinicreg(clinicid, pass, orgname, adminname, address,
					city, mobile, anumber, emailid, aemail, "0");
			if (data.get("sucess").toString().equalsIgnoreCase("Registation Sucessful")) {
				System.out.println("hello");
				json.put("success", "1");

			}

			else {
				json.put("success", "0");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		out.print(json);
		//System.out.println(json);
		out.flush();

	
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {}
}
