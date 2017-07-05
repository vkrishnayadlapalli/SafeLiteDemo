package com.safelite.report;

import com.safelite.accelerators.TestEngineMobile;

import io.appium.java_client.AppiumDriver;

public class Mobiledriver extends TestEngineMobile{
	public AppiumDriver aptempdrivermain=null;
	
	public AppiumDriver test(AppiumDriver apptest){
	if(apptest!=null){
		AppiumDriver aptempdriver=apptest;
		aptempdrivermain=aptempdriver;
	}
	
	return aptempdrivermain;
	}
}
