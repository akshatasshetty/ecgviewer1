package com.rjil.jio.nasan.wscall;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpsTestClient 
{
	public static String getHttpsServiceCallResponse(String url) throws Exception 
	{
		String response = null;
		try
		{
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() 
			{
				@Override
				public X509Certificate[] getAcceptedIssuers() 
				{
					return null;
				}
				
				@Override
				public void checkServerTrusted( java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
				{
					return;
				}
				
				@Override
				public void checkClientTrusted( java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
				{
					return;
				}
			}
			};
			
			final SSLContext sslContext = SSLContext.getInstance( "SSL" );
			sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			ClientConfig config=new DefaultClientConfig();
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(new HostnameVerifier() {
			
			@Override
			public boolean verify( String s, SSLSession sslSession ) 
			{
							// whatever your matching policy states
							return true;
						}
					}, sslContext));
			Client client = Client.create(config); // create jersey client
			System.out.println("Creating a SSL Socket ");
			WebResource resource = client.resource(url);
			System.out.println("Resource Created");
			response = resource.post(String.class); // for making post calls
													// change the code
													// accordingly here
			System.out.println("Got The Response");
		} catch (Exception e) {
			System.out.println("Error in getting Response" + e.getMessage());
			throw e;
		}
		
		System.out.println(response);
		
		return response;
	}
	
	public JSONObject ConvertResponseToJSONObject(String response) throws ParseException
	{
		JSONParser parse = new JSONParser();
		JSONObject job = (JSONObject) parse.parse(response.toString());
		String status = (String) job.get("status");
		//System.out.println("status"+status);
		return job;
	}

}
