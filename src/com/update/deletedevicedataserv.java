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
import com.rjil.jio.nasan.wscall.WSCall;

/**
 * Servlet implementation class deletedevicedataserv
 */
public class deletedevicedataserv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deletedevicedataserv() {
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

		System.out.println("Delete device serv callled");
		String clinic_id = request.getParameter("clinic_id_delete");
		String device_id = request.getParameter("device_id_edit");

		System.out.println(clinic_id);

		HttpsWSCall call = new HttpsWSCall();
		org.json.simple.JSONObject success = new org.json.simple.JSONObject();
		JSONObject js = new JSONObject();
		PrintWriter out = response.getWriter();

		try {
			success = call.deletedeviceclient(clinic_id,device_id);

			System.out.println(success);
			
			if (success.get("success").toString()
					.equalsIgnoreCase("Device deleted successfully"))

				js.put("Response", "Device Deleted Successfully");
			else
				js.put("Response", "Failed to delete device");

		

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
