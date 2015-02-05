package com.rjil.jio.nasan.wscall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class WSCall {
	public static void main(String[] args) throws JSONException {
		//WSCall l = new WSCall();
		//l.verify("ril1000", "111111");
		// l.getClinicData("rf1025", "0");
		// l.getRegClinic();
		 //l.getPassword("ril1000");
		// l.getDeviceData("12");
		// l.devreg("880070023435164", "nokia", "1", "888", "kkkkk");
		// l.clinicreg("RF1232", "111111", "RF", "Raja", "Mumbai", "Mumbai",
		// "9664286130", "222545789", "sdfs@fortis.com", "jjfjjf@fortis.com",
		// "0");
		// l.changeDeviceStatus("57", "223122", "Disabled");
		// l.putclinicdata("rf1025");
		//l.updateClinicInfo("rf1025", "Reliance foundation", "Ganesh", "sanpada",
		//"navi mumbai","988428613", "", "sdfs@fortis.com", "");
		// l.updatedeviceInfo("lenovo","126787","1","57");
		// l.deletedeviceclient("56");
		
	/*	try(FileReader reader=new FileReader("url.properties")){
			
			Properties properties=new Properties();
			properties.load(reader);
			String url=properties.getProperty("localhostURL");
			System.out.println(url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}

	public JSONObject verify(String clinic_id, String passwoord)
			throws JSONException {
		JSONObject data = new JSONObject();
		


		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/weblogin?clinic_id="
					+ clinic_id + "&password=" + passwoord;
			
			
			HttpPost post = new HttpPost(path);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();
			System.out.println(line);
			HttpEntity entity = httpresponse.getEntity();

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {

				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";

			}

			System.out.println("json");
			System.out.println(data.toString());
			System.out.println("status value" + data.get("organisation_name"));
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}

	public JSONObject devreg(String device_id, String device_name,
			String device_status, String clinic_id, String organisation_name)
			throws JSONException {

		JSONObject data = new JSONObject();
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/deviceregistration?device_id="
					+ device_id
					+ "&device_name="
					+ device_name
					+ "&device_status="
					+ device_status
					+ "&clinic_id="
					+ clinic_id
					+ "&organisation_name="
					+ organisation_name
					+ "";
			HttpPost post = new HttpPost(path);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));
			String line = br.readLine();

			HttpEntity entity = httpresponse.getEntity();

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {
				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";
			}

			System.out.println("json");
			System.out.println(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public JSONArray getClinicData(String clinic_id, String user_type)
			throws JSONException {

		//List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();
		if (user_type == null) {
			user_type = "0";
		}

		List<JSONObject> datalist1 = new ArrayList<JSONObject>();

		JSONArray array = new JSONArray();
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/clinicdata?clinic_id="
					+ clinic_id + "&user_type=" + user_type + "";
			HttpPost post = new HttpPost(path);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();
			System.out.println(line);
			HttpEntity entity = httpresponse.getEntity();

			Map<String, String> datamap = new HashMap<String, String>();

			line = line.replace("{", "");

			line = line.replaceAll("\"", "");
			line = line.replaceAll(" ", "");

			line = line.replace("[", "").replace("]", "");
			JSONObject data = new JSONObject();
			String date = "", time = "", datetime = "";

			String a[] = new String[2];
			

			int k = 0;
			for (String retval1 : line.split("}", 1)) {
				String line1 = retval1;

				int j = 0;

				line1 = line1.replace("}", "");
				for (String retval : line1.split(",", 0)) {

					String a1 = retval;
					int i = 0;

					for (String a2 : a1.split("=", 0)) {
						a[i] = a2;
						i++;

					}
					j++;

					data.put(a[0], a[1]);

					datamap.put(a[0], a[1]);
					a[0] = "";
					a[1] = "";

					if (j == 14) {

						array.put(k, datamap);

						datalist1.add(k, data);

						j = 0;
						k++;

					}
				}

				for (int t = 0; t < array.length(); t++) {

					JSONObject dd = (JSONObject) array.get(t);

				}

			}

			datetime = (String) data.get("ecgtest_date");
			date = datetime.substring(0, 10);
			time = datetime.substring(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public JSONObject clinicreg(String clinic_id, String password,
			String organisation_name, String organisation_head,
			String organisation_address, String city, String contact_no,
			String alternate_contact_no, String email_id,
			String alternate_email_id, String user_type) throws JSONException {
		JSONObject data = new JSONObject();
		try {
			System.out.println(user_type);

			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/clinicregistration?clinic_id="
					+ URLEncoder.encode(clinic_id)
					+ "&password="
					+ URLEncoder.encode(password)
					+ "&organisation_name="
					+ URLEncoder.encode(organisation_name)
					+ "&organisation_head="
					+ URLEncoder.encode(organisation_head)
					+ "&organisation_address="
					+ URLEncoder.encode(organisation_address)
					+ "&city="
					+ URLEncoder.encode(city)
					+ "&contact_no="
					+ URLEncoder.encode(contact_no)
					+ "&alternate_contact_no="
					+ URLEncoder.encode(alternate_contact_no)
					+ "&email_id="
					+ URLEncoder.encode(email_id)
					+ "&alternate_email_id="
					+ URLEncoder.encode(alternate_email_id)
					+ "&user_type="
					+ URLEncoder.encode(user_type) + "";
			System.out.println(path);
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));
			String line = br.readLine();

			HttpEntity entity = httpresponse.getEntity();
			System.out.println(line);

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {
				
				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";
			}

			System.out.println("json");
			System.out.println(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public JSONArray getDeviceData(String clinic_id) throws JSONException {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/devicedata?clinic_id="
					+ clinic_id + "";
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();

			HttpEntity entity = httpresponse.getEntity();

			Map<String, String> datamap = new TreeMap<String, String>();

			line = line.replace("{", "");

			line = line.replaceAll("\"", "");
			line = line.replaceAll(" ", "");
			line = line.replace("[", "").replace("]", "");

			String a[] = new String[2];
			
			int k = 0;
			for (String retval1 : line.split("}", 1)) {
				String line1 = retval1;

				line1 = line1.replace("}", "");
				int j = 0;
				for (String retval : line1.split(",", 0)) {

					String a1 = retval;

					int i = 0;

					for (String a2 : a1.split("=", 0)) {
						a[i] = a2;
						i++;
					}
					j++;

					data.put(a[0], a[1]);

					datamap.put(a[0], a[1]);
					a[0] = "";
					a[1] = "";

					if (j == 5) {
						array.put(k, datamap);
						j = 0;
						k++;
					}
				}

				for (int t = 0; t < array.length(); t++) {

					JSONObject dd = (JSONObject) array.get(t);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public org.json.JSONObject getPassword(String clinic_id)
			throws JSONException {
		org.json.JSONObject data = new org.json.JSONObject();
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/forgotpassword?clinic_id="
					+ clinic_id + "";
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();
			System.out.println("line" + line);
			HttpEntity entity = httpresponse.getEntity();

			line = line.replace("{", "");
			line = line.replace("}", "");
			if (line.equalsIgnoreCase("1")) {
				data.put("status", "1");
			} else {
				data.put("status", "failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("data" + data);
		return data;
	}

	public JSONArray getRegClinic() throws JSONException {
		List<String> dl = new ArrayList<String>();
		List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();

		JSONArray array = new JSONArray();
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/regclinic";
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();

			HttpEntity entity = httpresponse.getEntity();

			Map<String, String> datamap = new TreeMap<String, String>();

			line = line.replace("{", "");

			line = line.replaceAll("\"", "");
			line = line.replaceAll(" ", "");

			line = line.replace("[", "").replace("]", "");
			JSONObject data = new JSONObject();

			String a[] = new String[2];
			
			int k = 0;
			for (String retval1 : line.split("}", 1)) {
				String line1 = retval1;
				int j = 0;

				line1 = line1.replace("}", "");
				for (String retval : line1.split(",", 0)) {

					String a1 = retval;
					int i = 0;

					for (String a2 : a1.split("=", 0)) {
						a[i] = a2;
						i++;

					}
					j++;
					data.put(a[0], a[1]);

					datamap.put(a[0], a[1]);
					a[0] = "";
					a[1] = "";

					if (j == 11) {

						array.put(k, datamap);

						datalist.add(k, datamap);

						j = 0;
						k++;
					}
				}

				for (int t = 0; t < array.length(); t++) {

					JSONObject dd = (JSONObject) array.get(t);

					
				}
				
				dl.addAll(dl);

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public JSONObject changeDeviceStatus(String clinic_id, String device_id,
			String device_status) throws JSONException {
		JSONObject data = new JSONObject();
		String line = "";
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/activatedeactivate?clinic_id="
					+ clinic_id
					+ "&device_id="
					+ device_id
					+ "&device_status="
					+ device_status;
			HttpPost post = new HttpPost(path);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			line = br.readLine();
			System.out.println(line);
			HttpEntity entity = httpresponse.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.put("status", line);
		System.out.println("data" + data);
		return data;
	}

	public JSONObject putclinicdata(String clinic_id) throws JSONException {

		JSONObject json = new JSONObject();
		String line = "";
		try {
			String query = "http://localhost:8080/jioecg_1401/rest/jioecg/editclinic?clinic_id="
					+ clinic_id;
			HttpPost post = new HttpPost(query);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			line = br.readLine();
			System.out.println(line);
			HttpEntity entity = httpresponse.getEntity();

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {
				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				json.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		json.put("", line);
		
		return json;

	}

	public JSONObject updateClinicInfo(String clinic_id,
			String organisation_name, String organisation_head,
			String organisation_address, String city, String contact_no,
			String alternate_contact_no, String email_id,
			String alternate_email_id) {

		JSONObject data = new JSONObject();
		try {

			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/updateclinicinformation?clinic_id="
					+ URLEncoder.encode(clinic_id)
					+ "&organisation_name="
					+ URLEncoder.encode(organisation_name)
					+ "&organisation_head="
					+ URLEncoder.encode(organisation_head)
					+ "&organisation_address="
					+ URLEncoder.encode(organisation_address)
					+ "&city="
					+ URLEncoder.encode(city)
					+ "&contact_no="
					+ URLEncoder.encode(contact_no)
					+ "&alternate_contact_no="
					+ URLEncoder.encode(alternate_contact_no)
					+ "&email_id="
					+ URLEncoder.encode(email_id)
					+ "&alternate_email_id="
					+ URLEncoder.encode(alternate_email_id) + "";

			System.out.println(path);
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();
			System.out.println("line" + line);

			HttpEntity entity = httpresponse.getEntity();
			System.out.println(line);

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {

				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";
			}

			System.out.println("json");
			System.out.println(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

	public JSONObject updatedeviceInfo(String device_name, String device_id,
			String device_status, String clinic_id) {

		JSONObject data = new JSONObject();
		try {

			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/updatedeviceinfo?device_name="
					+ URLEncoder.encode(device_name)
					+ "&device_id="
					+ device_id
					+ "&device_status="
					+ device_status
					+ "&clinic_id=" + clinic_id;

			System.out.println(path);
			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			String line = br.readLine();

			HttpEntity entity = httpresponse.getEntity();
			System.out.println(line);

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {

				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";
			}

			System.out.println("json" + data.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public JSONObject deletedeviceclient(String clinic_id) {

		JSONObject data = new JSONObject();
		String line = "";
		try {
			String path = "http://localhost:8080/jioecg_1401/rest/jioecg/deletedevice?clinic_id="
					+ clinic_id;

			HttpPost post = new HttpPost(path);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpresponse.getEntity().getContent()));

			line = br.readLine();
			System.out.println("line" + line);
			HttpEntity entity = httpresponse.getEntity();

			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replaceAll("\"", "");

			String a[] = new String[2];
			for (String retval : line.split(",", 0)) {

				String a1 = retval;
				int i = 0;
				for (String a2 : a1.split(":", 0)) {
					a[i] = a2;
					i++;
				}
				data.put(a[0], a[1]);
				a[0] = "";
				a[1] = "";

			}

			System.out.println("json");
			System.out.println(data.toString());

		} catch (Exception e) {
		
		}
		System.out.println("data" + data);
		return data;
	}

	
}
