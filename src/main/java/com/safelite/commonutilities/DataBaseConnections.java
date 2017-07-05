package com.safelite.commonutilities;

/**
 * DB
 * @Description This method is used to store all the data base configurations.
 * @author Cigniti Technologies 
 * @return NA
 * @throws Throwable the throwable
 * @LastModifiedDate 02 Aug 2016
 */
public enum DataBaseConnections {

	FRAN001MARGQA_2_2("fran001margqa_2_2.user",
			"fran001margqa_2_2.password",
			"fran001margqa_2_2.host",
			"fran001margqa_2_2.port",
			"fran001margqa_2_2.servicename",
			"fran001margqa_2_2.sid"),

	FRAN002MARGQA_2_2("fran002margqa_2_2.user",
			"fran002margqa_2_2.password",
			"fran002margqa_2_2.host",
			"fran002margqa_2_2.port",
			"fran002margqa_2_2.servicename",
			"fran002margqa_2_2.sid"),

	MARGQA_2_2("margqa_2_2.user",
			"margqa_2_2.password",
			"margqa_2_2.host",
			"margqa_2_2.port",
			"margqa_2_2.servicename",
			"margqa_2_2.sid"),

	MARGQA_2_5("margqa_2_5.user",
			"margqa_2_5.password",
			"margqa_2_5.host",
			"margqa_2_5.port",
			"margqa_2_5.servicename",
			"margqa_2_5.sid");

	public String user() {
		return user;
	}

	public String password() {
		return password;
	}

	public String host() {
		return host;
	}

	public String port() {
		return port;
	}

	public String servicename() {
		return servicename;
	}

	public String sid() {
		return sid;
	}

	private final String user;
	private final String password;
	private final String host;
	private final String port;
	private final String servicename;
	private final String sid;

	DataBaseConnections(String user, String password, String host, String port, String servicename, String sid) {
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.servicename = servicename;
		this.sid = sid;
	}

}