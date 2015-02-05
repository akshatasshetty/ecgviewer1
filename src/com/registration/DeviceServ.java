package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rjil.jio.nasan.wscall.HttpsWSCall;

/**
 * Servlet implementation class DeviceServ
 */
public class DeviceServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		System.out.println("this is servlet is called");
		
		System.out.println("receviced");
		
		String devicename=request.getParameter("dname");
		String imeinumber=request.getParameter("imei");
		String status=request.getParameter("state");
		String clinic_id=request.getParameter("clinicid");
		String organisation_name=request.getParameter("clinicname");
		System.out.println(devicename);
		System.out.println(clinic_id);
		System.out.println(organisation_name);
		

		//System.out.println(c_id);
		org.codehaus.jettison.json.JSONObject json=new org.codehaus.jettison.json.JSONObject();
		PrintWriter out = response.getWriter();
		String success="";
		org.json.simple.JSONObject data=new org.json.simple.JSONObject();
        
        
        HttpsWSCall call=new HttpsWSCall();
        try {
			data=call.devreg(imeinumber, devicename, status, clinic_id, organisation_name);
			System.out.println("data"+data);
			if(data.get("sucess").toString().equalsIgnoreCase("Registration Sucessful"))
			{
				System.out.println("hello");
				json.put("success", "1");
				success="1";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.print(json);
		out.flush();
		
        /*RequestDispatcher dispatch = request.getRequestDispatcher("/registeredclinics.jsp");
		dispatch.forward(request,response);*/
		/*PrintWriter out = response.getWriter();
		try {
			json.put("register", "failed");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/jioecg", "root", "root");
			Statement stmt;
			stmt = con.createStatement();
			String sql = "INSERT INTO device_registration(clinic_id,organisation_name,device_id,device_name,device_status) VALUES('"
					+ c_id
					+ "','"
					+ o_name
					+ "','"
					+ imeinumber
					+ "','"
					+ devicename
					+ "','"
					+ status
					+ "')";
			int flag = stmt.executeUpdate(sql);
			if (flag == 0) {
				json.put("register", "nomatch");
			} else if (flag == 1) {
				json.put("register", "success");
			}
			out.print(json);
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
		
		
	}

}
