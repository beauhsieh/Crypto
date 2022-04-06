package com.crypto.crypto.utils;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {

	public static boolean isNullOrWhiteSpace(String value) {
		if (value == null)
			return true;
		else if (value.isEmpty())
			return true;
		else if (value.trim().length() == 0)
			return true;

		return false;
	}

	public static String htmlToText(String html) {
		String text = html.replaceAll("\\<.*?\\>", "");
		return text;
	}

	public static Set<String> split(String value) {
		if (TextUtils.isBlank(value))
			return new HashSet<String>(0);

		Set<String> result = new HashSet<String>(0);
		String[] items = value.split(",");
		for (String item : items) {

			if (TextUtils.isBlank(item))
				continue;

			if (result.contains(item))
				continue;

			result.add(item);
		}

		return result;
	}

	public static List<String> splitAsList(String value, String delimiter) {
		if (TextUtils.isBlank(value))
			return new ArrayList<String>(0);

		List<String> result = new ArrayList<String>(0);
		String[] items = value.split(delimiter.trim());
		for (String item : items) {

			item = item.trim();
			if (TextUtils.isBlank(item))
				continue;

			if (result.contains(item))
				continue;

			result.add(item);
		}

		return result;
	}

	public static List<String> splitAsList(String value) {
		if (TextUtils.isBlank(value))
			return new ArrayList<String>(0);

		List<String> result = new ArrayList<String>(0);
		String[] items = value.split(",");
		for (String item : items) {

			item = item.trim();
			if (TextUtils.isBlank(item))
				continue;

			if (result.contains(item))
				continue;

			result.add(item);
		}

		return result;
	}

	public static Boolean stringToBoolean(String s) {
		if(s.equalsIgnoreCase("0") || s.equalsIgnoreCase("no") || s.equalsIgnoreCase("false"))
		{
			return false;
		}
		else if(s.equalsIgnoreCase("1") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static String stringToDbBoolean(String s) {
		s = s.trim().toLowerCase();
		if(s.equals("0") || s.equals("no") || s.equals("false"))
		{
			return "0";
		}
		else if(s.equals("1") || s.equals("yes") || s.equals("true"))
		{
			return "1";
		}
		else
		{
			throw new IllegalArgumentException("Invalid string: " + s + " can't be converted into true or false!");
		}
	} 

	public static Integer BooleanToInteger(Boolean b) {
		String booleanString = b.toString().toLowerCase();
		if(booleanString.equals("0") || booleanString.equals("false"))
		{
			return 0;
		}
		else if(booleanString.equals("1") || booleanString.equals("true"))
		{
			return 1;
		}
		else
		{
			throw new IllegalArgumentException("Invalid string: " + booleanString + " can't be converted into 1 or 0!");
		}
	}
}
