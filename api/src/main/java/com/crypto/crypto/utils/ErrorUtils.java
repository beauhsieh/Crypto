package com.crypto.crypto.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtils {
	public static String errorTrackString(Throwable ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
