package com.nabil.maven.plugins.hudsonbuildnumber;

import java.util.ArrayList;

import org.json.JSONObject;



public class TestHudson {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HudsonRestful spyRest = new HudsonRestful();
		ArrayList<JSONObject> jobs = spyRest.retrieveJSONArray("http://10.212.1.20:8180/hudson/job/AFT/api/json");
		//ArrayList<JSONObject> jobs = spyRest.retrieveJSONArray("http://10.212.1.20:8180/hudson/api/json");

	}
}
