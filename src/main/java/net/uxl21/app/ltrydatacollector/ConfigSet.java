package net.uxl21.app.ltrydatacollector;

import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigSet {
	
	private static ConfigSet instance = null;
	
	
	private JsonElement rootElement = null;
	private JsonObject rootObj = null;
	
	

	/*
	 * Constructor 
	 */
	private ConfigSet() {
    	InputStreamReader inReader = new InputStreamReader(this.getClass().getResourceAsStream("/config/config.json"));    	
    	this.rootElement = JsonParser.parseReader(inReader);
    	this.rootObj = this.rootElement.getAsJsonObject();
	}
	
	
	public static ConfigSet getInstance() {
		if( instance == null ) {
			instance = new ConfigSet();
		}
		
		return instance;
	}
	
	
	
	
	public String getString(String property) {
		return this.rootObj.get(property).getAsString();
	}
	
	
	public String getString(String property, String defaultValue) {
		String value = this.rootObj.get(property).getAsString();
		
		return value == null ? defaultValue : value;
	}

	
	public String getChildString(String property, String childProperty) {
		JsonObject propObj = this.rootObj.getAsJsonObject(property);
		
		if( propObj == null ) {
			return "";

		} else {
			return propObj.get(childProperty).getAsString();
		}
	}
	
	
	
	public boolean getBoolean(String property) {
		return this.rootObj.get(property).getAsBoolean();
	}
	
	
	public boolean getChildBoolean(String property, String childProperty) {
		JsonObject propObj = this.rootObj.getAsJsonObject(property);
		
		if( propObj == null ) {
			return false;

		} else {
			return propObj.get(childProperty).getAsBoolean();
		}
	}
	
	
	public int getInteger(String property) {
		return this.rootObj.get(property).getAsInt();
	}
	
	public int getChildInteger(String property, String childProperty) {
		JsonObject propObj = this.rootObj.getAsJsonObject(property);
		
		return propObj.get(childProperty).getAsInt();
	}
	
	
	
	public String[] getList(String property) {
		JsonArray propArr = this.rootObj.getAsJsonArray(property);
		int size = propArr.size();
		String[] values = new String[size];
		
		for( int i=0; i<size; i++ ) {
			values[i] = propArr.get(i).getAsString();
		}
		
		return values;
	}
	
}
