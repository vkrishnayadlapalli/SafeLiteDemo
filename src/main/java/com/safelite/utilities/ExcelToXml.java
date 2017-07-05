package com.safelite.utilities;

public class ExcelToXml {

	public static void main(String[] args) throws Exception {
		try {
			ExcelUtility.createXml("Browsers");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
