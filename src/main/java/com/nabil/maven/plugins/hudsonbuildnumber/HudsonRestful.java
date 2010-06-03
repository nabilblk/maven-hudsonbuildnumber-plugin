package com.nabil.maven.plugins.hudsonbuildnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HudsonRestful {
public static Logger logger=Logger.getLogger(HudsonRestful.class); 
public Integer retrieveBuildNumber(String urlString) {
	String result = queryRESTurl(urlString);
	if (result != null) {
		try {
			JSONObject json = new JSONObject(result);
			//JSONArray jobsArray = json.getJSONArray("jobs");
			JSONArray jobsArray = json.getJSONArray("builds");

				JSONObject jobitem = jobsArray.getJSONObject(0);
				Integer buildNumber=(Integer)jobitem.get("number");

			return buildNumber;
		} catch (JSONException e) {
			logger.error("JSON There was an error parsing the JSON", e);				
		}			
	}

return null;
}


	public ArrayList<JSONObject> retrieveJSONArray(String urlString) {
		String result = queryRESTurl(urlString);
		ArrayList<JSONObject> JOBS = new ArrayList<JSONObject>();
		if (result != null) {

			try {
				JSONObject json = new JSONObject(result);
				//JSONArray jobsArray = json.getJSONArray("jobs");
				JSONArray jobsArray = json.getJSONArray("builds");
				for (int a = 0; a < jobsArray.length(); a++) {
					JSONObject jobitem = jobsArray.getJSONObject(a);
					Integer buildNumber=(Integer)jobitem.get("number");
					JOBS.add(jobitem);
				}
				return JOBS;
			} catch (JSONException e) {
				logger.error("JSON There was an error parsing the JSON", e);				
			}			
		}
		JSONObject myObject = new JSONObject();
		try {
			myObject.put("name","No hudson Jobs found");
			myObject.put("color", "grey");
			JOBS.add(myObject);
		} catch (JSONException e1) {
			logger.error("JSON There was an error creating the JSONObject", e1);
		}
		return JOBS;
	}

	private String queryRESTurl(String url) {
		// URLConnection connection;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		
		try {			
			response = httpclient.execute(httpget);
			
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				
				instream.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			logger.error("REST There was a protocol based error", e);
		} catch (IOException e) {
			logger.error("REST There was an IO Stream related error", e);			
		}
		return null;
	}

	/**
	 * To convert the InputStream to String we use the
	 * BufferedReader.readLine() method. We iterate until the BufferedReader
	 * return null which means there's no more data to read. Each line will
	 * appended to a StringBuilder and returned as String.
	 */
	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
