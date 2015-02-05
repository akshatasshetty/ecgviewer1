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
 * Servlet implementation class UpdateClinic
 */
public class UpdateClinic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateClinic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(" updateclinic servlet called successfully");
		String csearch=request.getParameter("id");
		System.out.println(csearch);
		
		HttpsWSCall call = new HttpsWSCall();
		org.json.simple.JSONObject success = new org.json.simple.JSONObject();
		JSONObject js= new JSONObject();
		PrintWriter out = response.getWriter();
		
		try {
			success = call.putclinicdata(csearch);
			
			js.put("Result", success);
			js.put("status", "successful");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.print(js);
		System.out.println(js);
		out.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
