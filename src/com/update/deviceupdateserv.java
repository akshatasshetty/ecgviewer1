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
 * Servlet implementation class deviceupdateserv
 */
public class deviceupdateserv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deviceupdateserv() {
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

		System.out.println("device update servlet called");
		String device_name = request.getParameter("device_name_edit");
		String device_id = request.getParameter("device_id_edit");
		String device_status = request.getParameter("0");
		String clinic_id = request.getParameter("clinic_id_edit");

		System.out.println(clinic_id);

		HttpsWSCall call = new HttpsWSCall();
		org.json.simple.JSONObject success = new org.json.simple.JSONObject();
		JSONObject js = new JSONObject();
		PrintWriter out = response.getWriter();

		try {
			success = call.updatedeviceInfo(device_name, device_id,
					device_status, clinic_id);

			if (success.get("success").equals("Updated Device Details Successfully"))
				
				js.put("Result", "Updated Device Details Successfully");
			else
				js.put("Result", "Failed To Update Device Details");

		} catch (JSONException e) {

			// e.printStackTrace();
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
