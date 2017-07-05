package com.safelite.support;

/*
 * Objective Of Utilities.java: To keep all utilities methods in this file
 * Date: 21-Jan-2015 By Debasish
 * Additions: Overloaded WriteExceptions To write specific exceptions to Logger
 */

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;

public class ExceptionHandler {
	private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

	public static void WriteException(Logger LOG, FileNotFoundException e) {
		LOG.error("FileNotFoundException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, IOException e) {

		LOG.error("IOException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, NullPointerException e) {

		LOG.error("NullPointerException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, PatternSyntaxException e) {

		LOG.error("PatternSyntaxException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, IllegalArgumentException e) {

		LOG.error("IllegalArgumentException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, NoSuchFieldException e) {

		LOG.error("NoSuchFieldException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, SecurityException e) {

		LOG.error("SecurityException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, IllegalAccessException e) {

		LOG.error("IllegalAccessException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, NoSuchMethodException e) {

		LOG.error("NoSuchMethodException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, InvocationTargetException e) {

		LOG.error("InvocationTargetException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG,
									  ArrayIndexOutOfBoundsException e) {

		LOG.error("ArrayIndexOutOfBoundsException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, AssertionError e) {

		LOG.error("AssertionError : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, ClassNotFoundException e) {

		LOG.error("ClassNotFoundException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, InterruptedException e) {

		LOG.error("InterruptedException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, NoSuchElementException e) {

		LOG.error("NoSuchElementException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, NumberFormatException e) {

		LOG.error("NumberFormatException : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	public static void WriteException(Logger LOG, Exception e) {

		LOG.error("Exception : " + e.getMessage());
		WriteExceptionStack(LOG, e);
	}

	private static void WriteExceptionStack(Logger LOG, Throwable e) {
		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		LOG.error("StackTrace : " + writer.toString());
	}
}
