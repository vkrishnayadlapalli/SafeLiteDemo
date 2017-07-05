package com.safelite.utilities;

import java.util.Hashtable;

import com.safelite.accelerators.ActionEngine;

public class DataBean extends ActionEngine {

	public String value;

	/**
	 * @return : None
	 * @Behavior : Function to get the value from map object or from data source
	 * @param1 : object of type collection
	 * @param2 : value of type String
	 * @author : Ranjith Kumar Putta
	 */
	public String getValue(Hashtable<String, String> obj, String key) {
		if (mapObj.get(key) == null) {
			value = obj.get(key);
		} else {
			value = mapObj.get(key);
		}
		return value;
	}

	/**
	 * @return : None
	 * @Behavior : Function to set the value to mapobject
	 * @param1 : key of type String
	 * @param2 : value of type String
	 * @author : Ranjith Kumar Putta
	 */
	public void setValue(String key, String value) {
		mapObj.put(key, value);
	}

	/**
	 * @param : None
	 * @return : None
	 * @Behavior : Function to clear the elements inside a mapobject
	 * @author : Ranjith Kumar Putta
	 */
	public void cleanMapObj() {
		mapObj.clear();
	}

	/**
	 * @return : None
	 * @Behavior : Function to get the value from map object or from data source
	 * @param1 : object of type collection
	 * @param2 : value of type String
	 * @author : Ranjith Kumar Putta
	 */
	public String getValue(String key) {
		value = mapObj.get(key);
		return value;
	}
}
