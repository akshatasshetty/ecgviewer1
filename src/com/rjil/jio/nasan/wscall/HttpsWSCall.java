package com.rjil.jio.nasan.wscall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class HttpsWSCall {

	// String prodURL = "https://49.40.4.171/jioecg_1401/rest/jioecg/";
	String serverURL = "https://localhost/jioecg_1401/rest/jioecg/";
	//String localhostURL = "http://localhost:8080/jioecg_1401/rest/jioecg/";

	HttpsTestClient secureClient = new HttpsTestClient();
	String response = "";
	org.json.simple.JSONObject data = new org.json.simple.JSONObject();

	public static void main(String[] args) throws JSONException {
		HttpsWSCall l = new HttpsWSCall();
		// l.verify("ril1000", "111111");
		// l.getClinicData("rf1025", "0");
		// l.getRegClinic();
		// l.getPassword("ril1000");
		// l.getDeviceData("888");
		// l.devreg("8800700234351745", "nokia987", "1", "ril1000", "reliance");
		// l.clinicreg("RF12323", "111111", "RF", "Raja",
		// "Mumbai","Mumbai","9664286130", "222545789",
		// "sdfs@fortis.com","jjfjjf@fortis.com","0");
		// l.changeDeviceStatus("ril1000", "88007002343517", "Disabled");

		//l.updateClinicInfo("rf1025", "Reliance", "Ganesh", "sanpada",
				//"navi mumbai", "988428613", "", "sdfs@fortis.com", "");
		// l.updatedeviceInfo("lenovo","126787","1","rf1025");
		// l.getEcgResultCount("16");
		// l.deletedeviceclient("56","323232");
	}

	/* Updates clinic info after edit clinic */
	public org.json.simple.JSONObject updateClinicInfo(String clinic_id,
			String organisation_name, String organisation_head,
			String organisation_address, String city, String contact_no,
			String alternate_contact_no, String email_id,
			String alternate_email_id) {

		try {

			String path = serverURL + "updateclinicinformation?clinic_id="
					+ URLEncoder.encode(clinic_id) + "&organisation_name="
					+ URLEncoder.encode(organisation_name)
					+ "&organisation_head="
					+ URLEncoder.encode(organisation_head)
					+ "&organisation_address="
					+ URLEncoder.encode(organisation_address) + "&city="
					+ URLEncoder.encode(city) + "&contact_no="
					+ URLEncoder.encode(contact_no) + "&alternate_contact_no="
					+ URLEncoder.encode(alternate_contact_no) + "&email_id="
					+ URLEncoder.encode(email_id) + "&alternate_email_id="
					+ URLEncoder.encode(alternate_email_id) + "";

			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

	/* Verifies web portal login ws call */
	public org.json.simple.JSONObject verify(String clinic_id, String passwoord)
			throws JSONException {

		try {
			String path = serverURL + "weblogin?clinic_id=" + clinic_id
					+ "&password=" + passwoord;
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);
			System.out.println(data.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/* Device registration ws call */
	public org.json.simple.JSONObject devreg(String device_id,
			String device_name, String device_status, String clinic_id,
			String organisation_name) throws JSONException {

		try {
			String path = serverURL + "deviceregistration?device_id="
					+ device_id + "&device_name=" + device_name
					+ "&device_status=" + device_status + "&clinic_id="
					+ clinic_id + "&organisation_name=" + organisation_name
					+ "";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);
			System.out.println(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/* Retrieve clinic data for editing clinic info ws call */
	public JSONArray getClinicData(String clinic_id, String user_type)
			throws JSONException {

		// List<Map<String, String>> datalist = new ArrayList<Map<String,
		// String>>();
		if (user_type == null) {
			user_type = "0";
		}

		List<org.json.simple.JSONObject> datalist1 = new ArrayList<org.json.simple.JSONObject>();

		JSONArray array = new JSONArray();
		org.json.simple.JSONObject data = new org.json.simple.JSONObject();
		try {
			String path = serverURL + "clinicdata?clinic_id=" + clinic_id
					+ "&user_type=" + user_type + "";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			Map<String, String> datamap = new HashMap<String, String>();
			String line = response;
			line = line.replace("{", "");
			line = line.replaceAll("\"", "");
			line = line.replaceAll(" ", "");
			line = line.replace("[", "").replace("]", "");

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
					System.out.println("dd");
					System.out.println(dd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	/* Register clinic ws call */
	public org.json.simple.JSONObject clinicreg(String clinic_id,
			String password, String organisation_name,
			String organisation_head, String organisation_address, String city,
			String contact_no, String alternate_contact_no, String email_id,
			String alternate_email_id, String user_type) throws JSONException {

		try {
			System.out.println(user_type);
			String path = serverURL + "clinicregistration?clinic_id="
					+ URLEncoder.encode(clinic_id) + "&password="
					+ URLEncoder.encode(password) + "&organisation_name="
					+ URLEncoder.encode(organisation_name)
					+ "&organisation_head="
					+ URLEncoder.encode(organisation_head)
					+ "&organisation_address="
					+ URLEncoder.encode(organisation_address) + "&city="
					+ URLEncoder.encode(city) + "&contact_no="
					+ URLEncoder.encode(contact_no) + "&alternate_contact_no="
					+ URLEncoder.encode(alternate_contact_no) + "&email_id="
					+ URLEncoder.encode(email_id) + "&alternate_email_id="
					+ URLEncoder.encode(alternate_email_id) + "&user_type="
					+ URLEncoder.encode(user_type) + "";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/* Retrieve Device info ws call */
	public JSONArray getDeviceData(String clinic_id) throws JSONException {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			String path = serverURL + "devicedata?clinic_id=" + clinic_id + "";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			Map<String, String> datamap = new TreeMap<String, String>();
			String line = response;
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

	/* Retrieve password incase of forgot password ws call */
	public org.json.simple.JSONObject getPassword(String clinic_id)
			throws JSONException {

		try {
			String path = serverURL + "forgotpassword?clinic_id=" + clinic_id
					+ "";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);

			String line = response;

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

	/* retrieve all registered clinic */
	public JSONArray getRegClinic() throws JSONException {
		List<String> dl = new ArrayList<String>();
		List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();

		JSONArray array = new JSONArray();
		try {
			String path = serverURL + "regclinic";
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			Map<String, String> datamap = new TreeMap<String, String>();
			String line = response;
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

	/* Changing device status ws call */
	public org.json.simple.JSONObject changeDeviceStatus(String clinic_id,
			String device_id, String device_status) throws JSONException {

		String line = "";
		try {
			String path = serverURL + "activatedeactivate?clinic_id="
					+ clinic_id + "&device_id=" + device_id + "&device_status="
					+ device_status;
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			if (response.equalsIgnoreCase("1"))
				data.put("status", response);
			else
				data.put("status", "Failed");

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("data" + data);
		return data;
	}

	public org.json.simple.JSONObject updatedeviceInfo(String device_name,
			String device_id, String device_status, String clinic_id) {

		try {

			String path = serverURL + "updatedeviceinfo?device_name="
					+ URLEncoder.encode(device_name) + "&device_id="
					+ device_id + "&device_status=" + device_status
					+ "&clinic_id=" + clinic_id;

			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public JSONObject getEcgResultCount(String clinic_id) throws JSONException {

		JSONObject data1 = new JSONObject();
		String line = "";
		try {
			String path = serverURL + "testcount?clinic_id=" + clinic_id;
			response = secureClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);
			data1.put("count", data.get("count"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("data" + data1);
		return data1;
	}

	public org.json.simple.JSONObject deletedeviceclient(String clinic_id,
			String device_id) {

		org.json.simple.JSONObject data = new org.json.simple.JSONObject();
		String line = "";
		try {
			String path = serverURL + "deletedevice?clinic_id=" + clinic_id
					+ "&device_id=" + device_id;

			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);

		} catch (Exception e) {

		}
		System.out.println("data" + data);
		return data;
	}

	public org.json.simple.JSONObject putclinicdata(String clinic_id) throws JSONException {

		JSONObject json = new JSONObject();
		String line = "";
		try {
			String path = serverURL + "editclinic?clinic_id="
					+ clinic_id;
			response = HttpsTestClient.getHttpsServiceCallResponse(path);
			data = secureClient.ConvertResponseToJSONObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		json.put("", line);

		return data;

	}
}
