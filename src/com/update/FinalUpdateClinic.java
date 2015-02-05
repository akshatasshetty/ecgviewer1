package com.update;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.rjil.jio.nasan.wscall.HttpsWSCall;

/**
 * Servlet implementation class FinalUpdateClinic
 */
public class FinalUpdateClinic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FinalUpdateClinic() {
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

		System.out.println("final update clinic called");
		String csearch = request.getParameter("clinicid");
		String orgname = request.getParameter("oname");
		String adminname = request.getParameter("aname");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String mobile = request.getParameter("mobile");
		String anumber = request.getParameter("anumber");
		String emailid = request.getParameter("emailid");
		String aemail = request.getParameter("aemail");

		System.out.println("cid "+csearch);

		HttpsWSCall call = new HttpsWSCall();
		org.json.simple.JSONObject success = new org.json.simple.JSONObject();
		JSONObject js = new JSONObject();
		PrintWriter out = response.getWriter();

		try {
			success = call.updateClinicInfo(csearch, orgname, adminname, address, city, mobile, anumber, emailid, aemail);
			if(success.get("status").toString().equalsIgnoreCase("Clinic Information Updated Successfully"))
			{
				System.out.println("clinic updation");
			js.put("Result", "Clinic Information Updated Successfully");}
			else
				js.put("Result", "Failed To Update Clinic Information");
			
		} catch (JSONException e) {
			
			//e.printStackTrace();
		}

		out.print(js);
		System.out.println(js);
		out.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
